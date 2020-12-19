package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Iterator;

//These are imported to test , DELETE LATER or after adjustment
import static edu.csc413.tankgame.view.StartMenuView.EXIT_BUTTON_ACTION_COMMAND;
import static edu.csc413.tankgame.view.StartMenuView.START_BUTTON_ACTION_COMMAND;

/**
 * GameDriver is the primary controller class for the tank game. The game is launched from GameDriver.main, and
 * GameDriver is responsible for running the game loop while coordinating the views and the data models.
 */
public class GameDriver {
    // TODO: Implement.
    // Add the instance variables, constructors, and other methods needed for this class. GameDriver is the centerpiece
    // for the tank game, and should store and manage the other components (i.e. the views and the models). It also is
    // responsible for running the game loop.
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameState gameState;


    public GameDriver() {
        mainView = new MainView();
        runGameView = mainView.getRunGameView();
        gameState = new GameState();


    }

    public void start() {
//        // TODO: Implement.
//        // This should set the MainView's screen to the start menu screen.
//        //Just by implementing this, we can start the menu
        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);


    }

    //Got it working, now I just need to implement it like he wants it to
    //I am still passing it through the startMenuView class at this point
    public static class PrintListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent event) {

            GameDriver mainViewTemp = new GameDriver();

            String actionCommand = event.getActionCommand();
            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
                mainViewTemp.mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
                mainViewTemp.runGame();
            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
                mainViewTemp.mainView.closeGame();
            }

        }
    }

    //This is important for the graphical images
    private void runGame() {
        //we will have a initial location of where the tank will be starting out


        for (WallImageInfo addWall : WallImageInfo.readWalls()) {
            gameState.addEntity(
                    new Wall(addWall.getX() + "stringID" + addWall.getY(), addWall.getX(), addWall.getY(),
                            0));
            runGameView.addDrawableEntity(
                    addWall.getX() + "stringID" + addWall.getY(), addWall.getImageFile(), addWall.getX(),
                    addWall.getY(), 0);
        }

        Tank playerTank =
                new PlayerTank(
                        GameState.PLAYER_TANK_ID,
                        RunGameView.PLAYER_TANK_INITIAL_X,
                        RunGameView.PLAYER_TANK_INITIAL_Y,
                        RunGameView.PLAYER_TANK_INITIAL_ANGLE
                );

        Tank aiTank =
                new CushionAITank(
                        GameState.AI_TANK_ID,
                        RunGameView.AI_TANK_INITIAL_X,
                        RunGameView.AI_TANK_INITIAL_Y,
                        RunGameView.AI_TANK_INITIAL_ANGLE
                );


        //Adding my third tank
        Tank aiTank2 =
                new turretAI(GameState.AI_TANK_2_ID,
                        RunGameView.AI_TANK_2_INITIAL_X,
                        RunGameView.AI_TANK_2_INITIAL_Y,
                        RunGameView.AI_TANK_2_INITIAL_ANGLE);


        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);
        gameState.addEntity(aiTank2);


        //This helps us draw images on the screen
        runGameView.addDrawableEntity(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());

        runGameView.addDrawableEntity(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle()
        );

        runGameView.addDrawableEntity(
                GameState.AI_TANK_2_ID,
                RunGameView.AI_TANK_2_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle()
        );

        Runnable gameRunner = () -> {
            while (update()) {
                runGameView.repaint();
                try {
                    //Each step of the game , is 8 mili seconds apart
                    Thread.sleep(8L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };
        new Thread(gameRunner).start();
    }

    // TODO: Implement.
    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.



    //Because Shell, Tank and Wall extends Entity, they will inherit over this method use
    //This is called when object is created
    //Entity being the super class and extended are the subclass
    private boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    } //Will return true if any of the following

    int Entity1;
    int Entity2;
    private boolean update() {



        // Check collisions
        // Ask gameState -- any new shells to draw?
        //if so then call addDrawableEntity (


        //TODO: Check as well, prob from over here
        // Ask gameState -- any new shells to remove?
        //if so then call removeDrawableEntity (
//        for (Entity entity: gameState.getEntities()) {
//            entity.move(gameState); //
//        }
        //For everything in the game that is drawn
        for (Entity entity : gameState.getEntities()) {
            entity.move(gameState);
            runGameView.setDrawableEntityLocationAndAngle(
                    entity.getId(), entity.getX(), entity.getY(), entity.getAngle());

        }

        //TODO: Game is slowing down because entities are being created in for loop
        //

        //Traverse the  ShellList
        //Creating tempShell var
        for (Entity tempShell : gameState.getShellList()) {
            //Using Entity Data type to create
            //for (dataType var : array/List)  //This will then loop through var[i]
            //Then can be used for statement

            gameState.addEntity(tempShell);
            //The addEntity from gameState class will take add shell to list
            // The .add() method will do that

            //While traversing , Entity tempShell[i]
            //we call add Draw Entity to add shell to
            runGameView.addDrawableEntity(
                    tempShell.getId(), //returns String
                    runGameView.SHELL_IMAGE_FILE, //returns String
                    tempShell.getX(),
                    tempShell.getY(),
                    tempShell.getAngle()
            );
        }
       gameState.getShellList().clear();
        //We can always check by using System.out to check items
       // System.out.println(gameState.getShellList().size());


        //Check if any entity colids with other any entity { tell }
        //if anything colids with itself ignore (do nothing}
        for (Entity1 =0 ; Entity1 < gameState.getEntities().size() ; Entity1++)
        {

           for (Entity2 = Entity1 +1 ; Entity2 <gameState.getEntities().size() ; Entity2++)
           {
            if(entitiesOverlap(gameState.getEntities().get(Entity1),
                     gameState.getEntities().get(Entity2)))
            {

                if (gameState.getEntities().get(Entity2) instanceof Shell && gameState.getEntities().get(Entity1)instanceof Shell )
                {

                    //Concurrent ConcurrentModificationException
//                    runGameView.removeDrawableEntity(gameState.getEntities().get(Entity1).getId());
//                    runGameView.removeDrawableEntity(gameState.getEntities().get(Entity2).getId());
                   gameState.GameStateShellRemoval(gameState.getEntities().get(Entity1));
                   gameState.GameStateShellRemoval(gameState.getEntities().get(Entity2));

                    //I get IndexOutOfBoundsException: Index 53 out of bounds for length 53
//                    gameState.getEntities().remove(gameState.getEntities().get(Entity1));
//                    gameState.getEntities().remove(gameState.getEntities().get(Entity2));


//                    I can just not simply remove it right away, I must call GameStateShellRemoval()
//                    The reason is for the NullPointerException
//                        This occurs because the Entity is not removed in Program State
//                            REMEMBER: Watch out for in the future- Shifts all element in the future foward


                    //add animation of explosion
                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY
                    ,gameState.getEntities().get(Entity1).getX(),gameState.getEntities().get(Entity1).getY());
                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                            gameState.getEntities().get(Entity2).getX(),gameState.getEntities().get(Entity2).getY());


                }
                if (gameState.getEntities().get(Entity1) instanceof Shell && gameState.getEntities().get(Entity2)instanceof Tank )
                {

                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY
                            ,gameState.getEntities().get(Entity1).getX(),gameState.getEntities().get(Entity1).getY());
                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                            gameState.getEntities().get(Entity2).getX(),gameState.getEntities().get(Entity2).getY());



                }
                if (gameState.getEntities().get(Entity2) instanceof Shell && gameState.getEntities().get(Entity1)instanceof Tank )
                {
                    double tankbX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankbX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankbY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankbY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();



                    double minMove = Math.min(Math.min(Math.min(tankbX1,tankbX2),tankbY2),tankbY1);

                    if(tankbX1 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() - minMove);
                    }else if (tankbX2 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() + minMove);
                    }else if (tankbY1 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() - minMove);
                    }else if (tankbY2 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() + minMove);
                    }

                    //left
                    double tankaX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankaX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankaY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankaY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();


                    double minMove2 = Math.min(Math.min(Math.min(tankaX1,tankaX2),tankaY2),tankaY1);


                    if(tankbX1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() - minMove );
                    }else if (tankbX2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() + minMove );
                    }else if (tankbY1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() - minMove);
                    }else if (tankbY2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() + minMove);
                    }

                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY
                            ,gameState.getEntities().get(Entity1).getX(),gameState.getEntities().get(Entity1).getY());
                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                            gameState.getEntities().get(Entity2).getX(),gameState.getEntities().get(Entity2).getY());



                }
                if (gameState.getEntities().get(Entity1) instanceof Wall && gameState.getEntities().get(Entity2)instanceof Shell )
                {

                    double tankbX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankbX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankbY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankbY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();



                    double minMove = Math.min(Math.min(Math.min(tankbX1,tankbX2),tankbY2),tankbY1);

                    if(tankbX1 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() - minMove);
                    }else if (tankbX2 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() + minMove);
                    }else if (tankbY1 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() - minMove);
                    }else if (tankbY2 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() + minMove);
                    }

                    //left
                    double tankaX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankaX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankaY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankaY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();


                    double minMove2 = Math.min(Math.min(Math.min(tankaX1,tankaX2),tankaY2),tankaY1);


                    if(tankbX1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() - minMove );
                    }else if (tankbX2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() + minMove );
                    }else if (tankbY1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() - minMove);
                    }else if (tankbY2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() + minMove);
                    }

                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY
                            ,gameState.getEntities().get(Entity1).getX(),gameState.getEntities().get(Entity1).getY());
                    runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                            gameState.getEntities().get(Entity2).getX(),gameState.getEntities().get(Entity2).getY());


                }

                if (gameState.getEntities().get(Entity1) instanceof Tank && gameState.getEntities().get(Entity2)instanceof Wall )
                {
                }
                if (gameState.getEntities().get(Entity2) instanceof Tank && gameState.getEntities().get(Entity1)instanceof Wall )
                {

                    //tankb is player tank
                    //left
                    double tankbX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankbX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankbY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankbY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();



                    double minMove = Math.min(Math.min(Math.min(tankbX1,tankbX2),tankbY2),tankbY1);

                    if(tankbX1 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() - minMove);
                    }else if (tankbX2 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() + minMove);
                    }else if (tankbY1 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() - minMove);
                    }else if (tankbY2 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() + minMove);
                    }

                    //left
                    double tankaX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankaX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankaY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankaY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();


                    double minMove2 = Math.min(Math.min(Math.min(tankaX1,tankaX2),tankaY2),tankaY1);


                    if(tankbX1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() - minMove );
                    }else if (tankbX2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() + minMove );
                    }else if (tankbY1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() - minMove);
                    }else if (tankbY2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() + minMove);
                    }



                }
                if (gameState.getEntities().get(Entity2) instanceof Tank && gameState.getEntities().get(Entity1)instanceof Tank )
                {
                    //tankb is player tank
                    //left
                    double tankbX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankbX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankbY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankbY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();



                    double minMove = Math.min(Math.min(Math.min(tankbX1,tankbX2),tankbY2),tankbY1);

                    if(tankbX1 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() - minMove);
                    }else if (tankbX2 == minMove ) {
                        gameState.getEntities().get(Entity1).setX(gameState.getEntities().get(Entity1).getX() + minMove);
                    }else if (tankbY1 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() - minMove);
                    }else if (tankbY2 == minMove ) {
                        gameState.getEntities().get(Entity1).setY(gameState.getEntities().get(Entity1).getY() + minMove);
                    }

                    //left
                    double tankaX1 = gameState.getEntities().get(Entity1).getXBound() - gameState.getEntities().get(Entity2).getX();
                    //right
                    double tankaX2 = gameState.getEntities().get(Entity2).getXBound() - gameState.getEntities().get(Entity1).getX();
                    //up
                    double tankaY1 = gameState.getEntities().get(Entity1).getYBound() - gameState.getEntities().get(Entity2).getY();
                    //down
                    double tankaY2 = gameState.getEntities().get(Entity2).getYBound() - gameState.getEntities().get(Entity1).getY();


                    double minMove2 = Math.min(Math.min(Math.min(tankaX1,tankaX2),tankaY2),tankaY1);


                    if(tankbX1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() - minMove );
                    }else if (tankbX2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setX(gameState.getEntities().get(Entity2).getX() + minMove );
                    }else if (tankbY1 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() - minMove);
                    }else if (tankbY2 == minMove2 ) {
                        gameState.getEntities().get(Entity2).setY(gameState.getEntities().get(Entity2).getY() + minMove);
                    }


                }
            }
           }

        }


        //Simplier solution to this





        //This slowed the bullets down
        gameState.getShellList().clear();
    //This is to clear the list of shells
        //gameState.getEntities().clear(); //This will clear the tanks lol


        //  Boundary for Shell - shells  removed if reached that point
        for (Iterator<Shell> iter = gameState.getShellList().iterator(); iter.hasNext(); ) {
            Shell oobShells = iter.next();

            if (gameState.OOBShell(oobShells)) {
                iter.remove();
            }
        }

        for (Shell shell: gameState.getShellList()) {
            if (gameState.OOBShell(shell)) {
                runGameView.removeDrawableEntity(shell.getId());
            }
        }
        return true;

    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}