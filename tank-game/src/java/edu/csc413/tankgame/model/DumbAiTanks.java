package edu.csc413.tankgame.model;

public class DumbAiTanks extends Tank {
    public DumbAiTanks(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    //we will pass everything to DumbAI
    //This is defining things unique to this AI
    @Override
    public void move(GameState gameState) {
        moveForward();
        turnRight();

    }
}
