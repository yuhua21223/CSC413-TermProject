package edu.csc413.tankgame.model;

import edu.csc413.tankgame.model.GameState;
import edu.csc413.tankgame.model.Entity;


//Just try to add it onto the screen first
public class AiTank2  extends Tank{
    public AiTank2(String id, double x, double y, double angle) {
        super(id, x, y, angle);

    }

    int counter = 200;
    @Override
    public void move(GameState gameState) {


        //Entity playerTank = gamestate.getEntities().getEnt(GameState.PLAYER_TANK_ID);
        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);
        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY();

        // atan2 applies arctangent to the ratio of the two provided values.
        double angleToPlayer = Math.atan2(dy, dx);
        double angleDifference = getAngle() - angleToPlayer;

        // We want to keep the angle difference between -180 degrees and 180 degrees
        // for the next step. This ensures that anything outside of that range
        // is adjusted by 360 degrees at a time until it is, so that the angle is
        // still equivalent.
angleDifference -=
        Math.floor(angleDifference / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);

        // The angle difference being positive or negative determines if we turn
        // left or right. However, we don’t want the Tank to be constantly bouncing // back and forth around 0 degrees, alternating between left and right // turns, so we build in a small margin of error.
        if (angleDifference < -Math.toRadians(3.0)) {     turnRight();
            }   else if (angleDifference > Math.toRadians(3.0)) {     turnLeft();

        }
        if(counter == 0) {
            counter = 200;
        }
        if (counter == 1) {
            shoot(gameState);
        }counter --;
    }
}
