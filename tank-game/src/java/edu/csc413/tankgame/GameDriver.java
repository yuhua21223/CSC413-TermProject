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
                    new Wall(addWall.getX() + "," + addWall.getY(), addWall.getX(), addWall.getY(),
                            0));
            runGameView.addDrawableEntity(
                    addWall.getX() + "," + addWall.getY(), addWall.getImageFile(), addWall.getX(),
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
                new ChasingAiTanks(
                        GameState.AI_TANK_ID,
                        RunGameView.AI_TANK_INITIAL_X,
                        RunGameView.AI_TANK_INITIAL_Y,
                        RunGameView.AI_TANK_INITIAL_ANGLE
                );


        //Adding my third tank
        Tank aiTank2 =
                new AiTank2(GameState.AI_TANK_2_ID,
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

    int index;
    int innerIndexPlusOne;
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

        //we need to check if any entity colids with other any entity { tell }
        //if anything colids with itself ignore (do nothing}


        for (index=0 ; index< gameState.getEntities().size() ;index++)
        {
           for (innerIndexPlusOne=index+1 ;innerIndexPlusOne<gameState.getEntities().size(); innerIndexPlusOne++)
           {
            if(entitiesOverlap(gameState.getEntities().get(index),
                     gameState.getEntities().get(innerIndexPlusOne)))
            {
                if (gameState.getEntities().get(index) instanceof Shell && gameState.getEntities().get(innerIndexPlusOne)instanceof Shell )
                {
                    //We just remove shell if collided
                    //TODO: removing shells when bounds checking so that their
                    // corresponding images are also removed from the RunGameView.

                    //removing shells when bounds checking so that their
                    // corresponding images are also removed from the RunGameView.


                    //I can just not simply remove it right away
//                    runGameView.removeDrawableEntity(gameState.getEntities().get(i).getId());
//                    runGameView.removeDrawableEntity(gameState.getEntities().get(j).getId());

                    //Problem not removed from gameState
                    //Gonna to move them again //inside
                    //remove specific shell
                    //will shift all element shell around
                        //Shift forward

                    //index move
                    //A possible solution is
                    //not remove right away

                    System.out.println("shell and Shell has colided ");

                }
//                if (gameState.getEntities().get(i) instanceof Shell && gameState.getEntities().get(j)instanceof Tank )
//                {
//
//                    System.out.println("shell and Tank has colided ");
//
//                }
//                if (gameState.getEntities().get(i) instanceof Shell && gameState.getEntities().get(j)instanceof Wall )
//                {
//
//                    System.out.println("shell and Wall has colided ");
//
//                }
//                if (gameState.getEntities().get(i) instanceof Tank && gameState.getEntities().get(j)instanceof Wall )
//                {
//
//                    System.out.println("Tank and Wall has colided ");
//
//                }
//                if (gameState.getEntities().get(i) instanceof Tank && gameState.getEntities().get(j)instanceof Tank )
//                {
//
//                    System.out.println("Tank and Tank has colided ");
//
//                }
            }
           }

        }


        //Simplier solution to this





        //This slowed the bullets down
        gameState.getShellList().clear();//This is to clear the list of shells
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


//        //This would check if tanks overlap,
//        for (shellIndex = 0; shellIndex<gameState.getEntities().size()-1;shellIndex ++ ){
//            Entity shell1 = gameState.getEntities().get(shellIndex);
//            Entity shell2 = gameState.getEntities().get(shellIndex+1);
//            if(gameState.entitiesOverlap(shell1,shell2)){
//                //gameState.shellCollision();
//                System.out.println("Tanks has collided");
//            }
//        }






        return true;

    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}