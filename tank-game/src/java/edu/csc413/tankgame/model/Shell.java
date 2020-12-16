package edu.csc413.tankgame.model;

import edu.csc413.tankgame.model.GameState;

import java.util.Iterator;

/**
 * Model class representing a shell that has been fired by a tank. A shell has a position and an angle, as well as a
 * speed. Shells by default should be unable to turn and only move forward.
 */
// TODO: Notice that Shell has a lot in common with Tank. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.  (Look at the note here,
    //it is great hint on how to do the shell
    //Very similar to tank class , because class is so similar we need to leverage the similarity

    //A shell will probably only use the forward movement

public class Shell extends Entity{
    private static final String SHELL_ID_PREFIX = "shell-";

    //TODO: We need to find a way to incoporate the movement speed and will be left up to me
    private static final double MOVEMENT_SPEED = 4.0;

    private static long uniqueId = 0L;



    public Shell(double x, double y, double angle) {

        super(getUniqueId(),x, y, angle);


    }

    @Override
    public void move(GameState gameState) {
        moveForward();
    }


    //Shells aren't smart and simply move forward
    //Thus will be Overriding the move method
//    @Override
//    public double getXBound() {
//        return getX() + 24.0;
//    }
//
//    @Override
//    public double getYBound() {
//        return getY() + 24.0;
//    }



    private static String getUniqueId() {
        return SHELL_ID_PREFIX + uniqueId++;
    }


}
