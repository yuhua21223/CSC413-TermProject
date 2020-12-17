package edu.csc413.tankgame.model;

import static edu.csc413.tankgame.model.GameState.*;
import static edu.csc413.tankgame.model.GameState.TANK_Y_UPPER_BOUND;

//TODO: The move mehtod is according to me
public class PlayerTank extends Tank {

    public PlayerTank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    int counter = 100;

    @Override
    public void move(GameState gameState) {



        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);
        if(playerTank.getX() < TANK_X_LOWER_BOUND )
        {
            playerTank.setX(TANK_X_LOWER_BOUND);

        }
        if(playerTank.getX() > TANK_X_UPPER_BOUND) {
            playerTank.setX(TANK_X_UPPER_BOUND);
        }
        if(playerTank.getY() < TANK_Y_LOWER_BOUND) {
            playerTank.setY(TANK_Y_LOWER_BOUND);
        }
        if(playerTank.getY() > TANK_Y_UPPER_BOUND){
            playerTank.setY(TANK_Y_UPPER_BOUND);
        }


//        //A tank colliding with a tank
//        private boolean entitiesOverlap(Entity entity1, Entity entity2) {
//            return entity1.getX() < entity2.getXBound()
//                    && entity1.getXBound() > entity2.getX()
//                    && entity1.getY() < entity2.getYBound()
//                    && entity1.getYBound() > entity2.getY();
//        }


        // TODO: This part is left to me
        //The methods need to be implemented for this to work
        //Method located in GameState.java

        if (gameState.upPressed()) {
            moveForward();
        }

        if (gameState.downPressed()) {
            moveBackward();
        }

        if (gameState.rightPressed()) {
            turnRight();
        }

        if (gameState.leftPressed()) {
            turnLeft();
        }

        if (gameState.shootPressed()) {


            if(counter == 0) {
                counter = 100;
            }
            if (counter == 100) {
                shoot(gameState);
            }counter --;


        }
    }

}
