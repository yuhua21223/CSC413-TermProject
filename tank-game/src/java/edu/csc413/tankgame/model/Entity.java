package edu.csc413.tankgame.model;


//"Without saying what the entity is, mysteriose, we want entity to collide with entity.

//moving common codes/ methods here from shell and tank

public abstract class Entity {

    //Added in just so program runs
    private static final double MOVEMENT_SPEED = 2.0; //2.0 is same as tank
    private static final double TURN_SPEED = Math.toRadians(3.0); //same as tank



    private final String id;
    private double x;
    private double y;
    private double angle;

    public Entity(String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    //By making move abstract we will not be bound to a specific way of moving
    public abstract void move(GameState gameState);
    //how is it for tank know when to move
    //move() is different for AI and Player



    // TODO: The methods below are provided so you don't have to do the math for movement. However, note that they are
    // protected. You should not be calling these methods directly from outside the Tank class hierarchy. Instead,
    // consider how to design your Tank class(es) so that a Tank can represent both a player-controlled tank and an AI
    // controlled tank.

    //Issues with Movement speed, and Turn Speed, because depending on what kind of Entity it is
    //The entity will have that speed
    //You will need a way to store it, either instance variable , abstract method or so forth



    protected void moveForward() {
        x += MOVEMENT_SPEED * Math.cos(angle);
        y += MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void moveBackward() {
        x -= MOVEMENT_SPEED * Math.cos(angle);
        y -= MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void turnLeft() {
        angle -= TURN_SPEED;
    }

    protected void turnRight() {
        angle += TURN_SPEED;
    }

    protected void doNothing(){
        x = getX();
        y = getY();
    }
}
