package rn.valiantspace2.logic;

public class InputEvents {

    boolean forward = false;
    boolean turnLeft = false;
    boolean turnRight = false;
    boolean fire = false;

    public InputEvents() {

    }

    public void reset() {
        this.forward = false;
        this.turnLeft = false;
        this.turnRight = false;
        this.fire = false;
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

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }
}
