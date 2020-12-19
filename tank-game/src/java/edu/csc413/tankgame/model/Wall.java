package edu.csc413.tankgame.model;

public class Wall extends Entity {

    private static final String WALL_ID_PREFIX = "wall-";
    private static long uniqueId1 = 0L;



    public Wall(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    //Override is necesarry because for move
    @Override
    public void move(GameState gameState) {

    }

    @Override
    public double getXBound() {
        return getX() + 32.0;
    }

    @Override
    public double getYBound() {
        return getY() + 32.0;
    }

//    //Pretty sure ID is not needed for now
    private static String getUniqueId() {
        return WALL_ID_PREFIX + uniqueId1++;
    }

}
