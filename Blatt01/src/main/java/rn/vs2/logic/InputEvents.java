package rn.vs2.logic;

public class InputEvents {

    boolean forward = false;
    boolean turnLeft = false;
    boolean turnRight = false;

    public InputEvents() {

    }

    public void reset() {
        this.forward = false;
        this.turnLeft = false;
        this.turnRight = false;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isTurnLeft() {
        return turnLeft;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }

    public boolean isTurnRight() {
        return turnRight;
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }
}
