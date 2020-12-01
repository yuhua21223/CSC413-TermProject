package edu.csc413.tankgame.model;

//import edu.csc413.tankgame.GameKeyListener;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.GameDriver;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static edu.csc413.tankgame.view.StartMenuView.START_BUTTON_ACTION_COMMAND;

/**
 * GameState represents the state of the game "world." The GameState object tracks all of the moving entities like tanks
 * and shells, and provides the controller of the program (i.e. the GameDriver) access to whatever information it needs
 * to run the game. Essentially, GameState is the "data context" needed for the rest of the program.
 */
public class GameState {
    public static final double TANK_X_LOWER_BOUND = 30.0;
    public static final double TANK_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width - 100.0;
    public static final double TANK_Y_LOWER_BOUND = 30.0;
    public static final double TANK_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height - 120.0;

    public static final double SHELL_X_LOWER_BOUND = -10.0;
    public static final double SHELL_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width;
    public static final double SHELL_Y_LOWER_BOUND = -10.0;
    public static final double SHELL_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height;

    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    // TODO: Feel free to add more tank IDs if you want to support multiple AI tanks! Just make sure they're unique.

    // TODO: Implement.
    // There's a lot of information the GameState will need to store to provide contextual information. Add whatever
    // instance variables, constructors, and methods are needed.

    //An arrayList of tanks, but we will be using entities instead
    private final List<Entity> entities = new ArrayList<>();

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    //if tank takes too much shells from the shells, it should be removed from this list


    //=========================================================================CAN BE HANDLED BETTER, SEPERATE THE CLASS

    //Methods for player tank (edited by me)
    //TODO: These , need to set up key listener to do this
    public boolean upPressed() {
        return movingForward;
    }

    public boolean downPressed() {
        return movingBackward;
    }

    public boolean rightPressed() {
        return turnRight;
    }

    public boolean leftPressed() {
        return turnLeft;
    }

    private static boolean movingForward = false;
    private static boolean movingBackward = false;
    private static boolean turnLeft = false;
    private static boolean turnRight = false;
    

    public static class GameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
//            System.out.println("w was pressed");
                movingForward = true;
            }if (keyCode == KeyEvent.VK_S) {
//            System.out.println("S was pressed");
                movingBackward = true;
            }if (keyCode == KeyEvent.VK_A) {
//            System.out.println("A was pressed");
                turnLeft = true;
            }if (keyCode == KeyEvent.VK_D) {
//            System.out.println("D was pressed");
                turnRight = true;
            } if (keyCode == KeyEvent.VK_SPACE) {
//            System.out.println("space was pressed");
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
//            System.out.println("w was released");
                movingForward = false;
            }if (keyCode == KeyEvent.VK_S) {
//            System.out.println("S was released");
                movingBackward = false;

            } if (keyCode == KeyEvent.VK_A) {
//            System.out.println("A was released");
                turnLeft = false;
            }if (keyCode == KeyEvent.VK_D) {
//            System.out.println("D was released");
                turnRight = false;
                if (keyCode == KeyEvent.VK_SPACE) {
//                System.out.println("space was released");
                }
            }

        }

    }



}
