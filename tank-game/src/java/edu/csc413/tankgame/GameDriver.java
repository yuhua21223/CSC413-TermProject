package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;
import edu.csc413.tankgame.view.StartMenuView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionListener;
import java.util.Iterator;

//These are imported to test , DELETE LATER or after adjustment
import static edu.csc413.tankgame.model.GameState.*;
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

        Tank playerTank =
                new PlayerTank(
                        GameState.PLAYER_TANK_ID,
                        RunGameView.PLAYER_TANK_INITIAL_X,
                        RunGameView.PLAYER_TANK_INITIAL_Y,
                        RunGameView.PLAYER_TANK_INITIAL_ANGLE
                );

        Tank aiTank =
                new DumbAiTanks(
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
    private boolean update() {


        //Boundary for Shell
//        for (Iterator<Shell> iter = gameState.getOOBShellList().iterator(); iter.hasNext(); ) {
//            Shell shell = iter.next();
//            if (gameState.OOBShell(shell)) {
//                iter.remove();
//            }
//        }

//        for (Shell shell: gameState.getOOBShellList()) {
//            if (gameState.OOBShell(shell)) {
//                runGameView.removeDrawableEntity(shell.getId());
//            }
////            if (gameState.entitiesOverlap(shell,shell)) {
////                runGameView.removeDrawableEntity(shell.getId());
////            }
//        }



        // Check collisions
        // Ask gameState -- any new shells to draw?
        //if so then call addDrawableEntity (





        // Ask gameState -- any new shells to remove?
            //if so then call removeDrawableEntity (
        for (Entity entity: gameState.getEntities()) {
            entity.move(gameState); //
        }
        //For everything in the game that is drawn
        for(Entity entity: gameState.getEntities() ) {
            runGameView.setDrawableEntityLocationAndAngle(
                    entity.getId(), entity.getX(), entity.getY(), entity.getAngle());
        }

        //Traverse the List of tempShells
        for(Entity shell: gameState.getShells()) {
            //The for each loop
            //gameState.getShells() is the the list that return a shell String

            gameState.addEntity(shell);
            //The addEntity from gameState class will take add shell to list
            // The .add() method will do that

            runGameView.addDrawableEntity(
                    shell.getId(), //returns String
                    runGameView.SHELL_IMAGE_FILE, //returns String
                    shell.getX(),
                    shell.getY(),
                    shell.getAngle()
                    //gameState.removeTempShells() //Not needed
            );
        }

        gameState.getShells().clear();


        //adding walls
        for( WallImageInfo wall: WallImageInfo.readWalls()) {
            gameState.addEntity(
                    new Wall(
                            wall.getX() + "," + wall.getY(),
                            wall.getX(), wall.getY(), 0));

            runGameView.addDrawableEntity(
                    wall.getX() + "," + wall.getY(),
                    wall.getImageFile(),
                    wall.getX(),
                    wall.getY(),
                    0
            );
        }


        return true;

    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
