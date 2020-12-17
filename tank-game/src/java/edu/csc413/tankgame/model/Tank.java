package edu.csc413.tankgame.model;

import edu.csc413.tankgame.model.Shell;
import edu.csc413.tankgame.model.*;

/**
 * Model class representing a tank in the game. A tank has a position and an angle. It has a movement speed and a turn
 * speed, both represented below as constants.
 */
// TODO: Notice that Tank has a lot in common with Shell. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.

    //Removing abstract

public abstract class Tank extends Entity {
    private static final double MOVEMENT_SPEED = 2.0;
    private static final double TURN_SPEED = Math.toRadians(3.0);

    public Tank(String id, double x, double y, double angle){
        super (id, x, y, angle);
    }


    //Calling GameState gameState will pass on the data
    public void shoot(GameState gameState) {

        //When I was shoot Player playertank = new Playertank i was making a new playerTank object
        //

        Shell shellCreated = new Shell(getShellX(),getShellY(),getAngle());
        gameState.ShellList.add(shellCreated);
    }




    @Override
    public double getXBound() {
        return getX() + 55.0;
    }

    @Override
    public double getYBound() {
        return getY() + 55.0;
    }



    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.



    private double getShellX() {
        return getX() + 30.0 * (Math.cos(getAngle()) + 0.5);
    }

    private double getShellY() {
        return getY() + 30.0 * (Math.sin(getAngle()) + 0.5);
    }


}
