//package edu.csc413.tankgame;
//
//import edu.csc413.tankgame.model.GameState;
//import edu.csc413.tankgame.GameDriver;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
////TODO: set so it changes booleans inside the gamestate file on
//// press and on release then inside the PlayerTank move,
//// if the corresponding boolean expression is true,
////  moveForward/Backward or turn left/right
//
//
//public class GameKeyListener implements KeyListener {
//
//    private static boolean movingForward = false;
//    private static boolean movingBackward = false;
//    private static boolean turnLeft = false;
//    private static boolean turnRight = false;
//
//
//    GameState tankMovement = new GameState();
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        if (keyCode == KeyEvent.VK_W) {
////            System.out.println("w was pressed");
//                movingForward = true;
//        } else if (keyCode == KeyEvent.VK_S) {
////            System.out.println("S was pressed");
//            movingBackward = true;
//        } else if (keyCode == KeyEvent.VK_A) {
////            System.out.println("A was pressed");
//            turnLeft = true;
//        } else if (keyCode == KeyEvent.VK_D) {
////            System.out.println("D was pressed");
//            turnRight = true;
//        } else if (keyCode == KeyEvent.VK_SPACE) {
////            System.out.println("space was pressed");
//        }
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        if (keyCode == KeyEvent.VK_W) {
////            System.out.println("w was released");
//            movingForward = false;
//        } else if (keyCode == KeyEvent.VK_S) {
////            System.out.println("S was released");
//            movingBackward = false;
//
//        } else if (keyCode == KeyEvent.VK_A) {
////            System.out.println("A was released");
//            turnLeft = false;
//        } else if (keyCode == KeyEvent.VK_D) {
////            System.out.println("D was released");
//            turnRight = false;
//            if (keyCode == KeyEvent.VK_SPACE) {
////                System.out.println("space was released");
//            }
//        }
//
//    }
//
//}