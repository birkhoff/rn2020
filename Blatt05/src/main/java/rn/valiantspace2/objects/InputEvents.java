package rn.valiantspace2.objects;

public class InputEvents {

    boolean forward = false;
    boolean turnLeft = false;
    boolean turnRight = false;
    boolean fire = false;

    float startX = 0.f;
    float startZ = 0.f;
    float startRy = 0.f;

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

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartZ() {
        return startZ;
    }

    public void setStartZ(float startZ) {
        this.startZ = startZ;
    }

    public float getStartRy() {
        return startRy;
    }

    public void setStartRy(float startR) {
        this.startRy = startR;
    }
}
