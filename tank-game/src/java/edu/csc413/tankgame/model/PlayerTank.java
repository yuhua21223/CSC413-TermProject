package edu.csc413.tankgame.model;

//TODO: The move mehtod is according to me
public class PlayerTank extends Tank{

    public PlayerTank(String id, double x, double y, double angle) {
        super (id, x, y, angle);
    }


    @Override
    public void move(GameState gameState) {


        // TODO: This part is left to me
        //The methods need to be implemented for this to work
        //Method located in GameState.java

        if(gameState.upPressed()){
            moveForward();
        }

        if( gameState.downPressed()){
            moveBackward();
        }

        if (gameState.rightPressed()){
            turnRight();
        }

        if (gameState.leftPressed()) {
            turnLeft();
        }


    }


}
