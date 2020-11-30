package edu.csc413.tankgame.model;

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

    //These won't be needed because Shell will be inheriting from Entity of these properties
//    private final String id;
//    private double x;
//    private double y;
//    private double angle;

    public Shell(double x, double y, double angle) {

        super(getUniqueId(),x, y, angle);

//        this.id = getUniqueId(); //generates its own Unique ID
//        this.x = x;
//        this.y = y;
//        this.angle = angle;
    }

    //Shells aren't smart and simply move forward
    //Thus will be Overriding the move method
    @Override
    public void move(GameState gameState) {
        moveForward();
    }


    private static String getUniqueId() {
        return SHELL_ID_PREFIX + uniqueId++;
    }
}
