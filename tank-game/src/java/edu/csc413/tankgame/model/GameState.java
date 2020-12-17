package edu.csc413.tankgame.model;

//import edu.csc413.tankgame.GameKeyListener;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
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

    //Creating String for different entities/ tanks
    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    public static final String AI_TANK_2_ID = "ai-tank2";

    //String for Shell
    public static final String SHELL_ID = "shell";


    //Testing out if method work
    //Creating shell id for AI
    //This is to seperate the shells
    public static final String AI_SHELL_ID = "shell";

    // TODO: Feel free to add more tank IDs if you want to support multiple AI tanks! Just make sure they're unique.

    // TODO: Implement.
    // There's a lot of information the GameState will need to store to provide contextual information. Add whatever
    // instance variables, constructors, and methods are needed.

    //An arrayList of tanks, but we will be using entities instead
    private final List<Entity> entities = new ArrayList<>();
    //this has many entites
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    // I need method to get the playerTank entity inside the list
    //This is the playerTank: GameState.PLAYER_TANK_ID
    //getEntities is the list


    public Entity getEntity( String id  ) {
        Entity temp=null;
        for (Entity entity : entities) {
            if(entity.getId()==id)
            {
                temp=entity;
            }
        }
        return temp;

    }

    //This portion is for Shell ====================================================================

    //We create a Temporary List to store shells
    public final List<Entity> TempShells = new ArrayList<>();

    //Method returns a List with < Entity datatypes > to foreach loop in Gamedrive.update()
    //This is a getter
    public List<Entity> getShells(){

        return TempShells;
    }




    //This part will help remove shells that are out of bounds
    public final List<Shell> OOBShellList = new ArrayList<>();


    //TODO: this may need to actually access TempShells and take out all those shells
    public List<Shell> getOOBShellList(){
        return OOBShellList;
    }

//Objective: Remove all shells that reach out of bounds
//    public boolean OOBShell( Shell shell) {
//        return shell.getX() < SHELL_X_LOWER_BOUND
//                || shell.getX() > SHELL_X_UPPER_BOUND
//                || shell.getY() < SHELL_Y_LOWER_BOUND
//                || shell.getY() > SHELL_Y_UPPER_BOUND;
//    }
public boolean OOBShell( Shell shell) {
        return shell.getX() < SHELL_X_LOWER_BOUND
                || shell.getX() > SHELL_X_UPPER_BOUND
                || shell.getY() < SHELL_Y_LOWER_BOUND
                || shell.getY() > SHELL_Y_UPPER_BOUND;
    }



        //Because Shell, Tank and Wall extends Entity, they will inherit over this method use
        //This is called when object is created
        //Entity being the super class and extended are the subclass
    public boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    } //Will return true if any of the following



    //===========================================CAN BE HANDLED BETTER, SEPERATE THE CLASS

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

    //Shoot initial is false
    //Keylistener will do the rest
    public boolean shootPressed() {
        return shoot;
    }


    private static boolean movingForward = false;
    private static boolean movingBackward = false;
    private static boolean turnLeft = false;
    private static boolean turnRight = false;


    public static boolean shoot = false;

    public static void setSpace(boolean b) {
        shoot = b;
    }



    public static class GameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_SPACE) {
                setSpace(true);

                //Unsure why, but turnLeft is needed here inorder for tank to shoot
                //turnRight will help counter the turnLeft movement
                turnLeft = true;
                turnRight = true;
//                System.out.println("space was pressed");
            }if (keyCode == KeyEvent.VK_W) {
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
            }



        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_SPACE) {
                shoot = false;

                //Unsure why, but I need turnLeft and turnRight here inorder for tank to shoot
                turnLeft = false;
                turnRight = false;
//          System.out.println("space was released");
            }if (keyCode == KeyEvent.VK_W) {
//            System.out.println("w was released");
                movingForward = false;
            }if (keyCode == KeyEvent.VK_S) {
//            System.out.println("S was released");
                movingBackward = false;

            } if (keyCode == KeyEvent.VK_A) {
//            System.out.println("A was released");
                turnLeft = false;
            }if (keyCode == KeyEvent.VK_D) {
//                System.out.println("D was released");
                turnRight = false;

            }

        }

    }



}
