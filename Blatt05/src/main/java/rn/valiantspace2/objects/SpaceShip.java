package rn.valiantspace2.objects;

import rn.valiantspace2.renderer.MathBib;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.data.PlyObject;
import rn.valiantspace2.renderer.data.SceneNode;
import rn.valiantspace2.renderer.parser.StanfordTriangleParser;

import java.util.ArrayList;

public class SpaceShip {

    private static float MOVE_SPEED = 0.01f;
    private static float BOOST_SPEED = 0.02f;
    private static float ROTATION_SPEED = 0.0025f;
    private static int PRELOADED_LASERS = 3;
    private static float LASER_OFFSET = 10.f;
    private static float LASER_SPEED = 0.05f;
    private static float LADER_RADIUS = 50.f;
    private static float FIRE_RATE = 750.f;
    private float BLINK_RATE = 100.f;
    private float BLINK_DURATION = 1000.f;
    private float ROLL_SPEED = 0.01f;
    private float ROLL_UNDO_SPEED = 0.95f;

    private float roll = 0.f;
    private boolean rolling = false;
    private float timeSinceLastFire = 0.0f;
    private float timeSinceLastBlink = 0.0f;
    private float totalBlinkTime = 0.0f;
    private Boolean hit = false;


    /**
     * This is the node we use to move the ship in the scene
     */
    private SceneNode shipBase;
    /**
     * This is the node where we can apply rolls and where we actually store the ship mesh
     */
    private SceneNode shipNode;

    private ArrayList<SceneNode> LasersIdle = new ArrayList();
    private ArrayList<SceneNode> Lasers = new ArrayList();


    /**
     * Spaceship constructor sets up all the required nodes and adds them to the scenegraph
     *
     * @param renderer
     */
    public SpaceShip(SoftwareRenderer renderer, StanfordTriangleParser parser, String modelPath) {

        PlyObject shipPly = parser.parsePlyFile(modelPath);
        shipNode = StanfordTriangleParser.fill_ply_object(shipPly, renderer);
        shipBase = new SceneNode();
        shipBase.addChild(shipNode);
        renderer.add_renderable(shipBase);

        // preload lasers
        PlyObject laserPly = parser.parsePlyFile("src/main/resources/laser.ply");
        for (int i = 0; i < PRELOADED_LASERS; i++) {
            SceneNode laser = StanfordTriangleParser.fill_ply_object(laserPly, renderer);
            LasersIdle.add(laser);
        }
    }


    public SceneNode getNode() {
        return this.shipBase;
    }

    public ArrayList<SceneNode> getLasers() {
        return Lasers;
    }

    public boolean canBeHit() {
        return !hit;
    }

    public void hit() {
        this.hit = true;
    }

    public void flyForward(float timeSinceLastFrame, float speed) {
        float[][] forward = shipBase.getForwardVector();
        float x = forward[0][0] * speed * timeSinceLastFrame;
        float y = forward[1][0] * speed * timeSinceLastFrame;
        float z = forward[2][0] * speed * timeSinceLastFrame;

        shipBase.setTranslate(shipBase.getX() - x,
                shipBase.getY() + y,
                shipBase.getZ() + z);
    }

    public void turnLeft(float timeSinceLastFrame) {
        shipBase.setRy(shipBase.getRy() - ROTATION_SPEED * timeSinceLastFrame);

        if (roll > -MathBib.PI / 3.f) {
            this.roll = shipNode.getRz() - ROLL_SPEED;
        }
        rolling = true;

    }

    public void turnRight(float timeSinceLastFrame) {
        shipBase.setRy(shipBase.getRy() + ROTATION_SPEED * timeSinceLastFrame);
        if (roll < MathBib.PI / 3.f) {
            this.roll = shipNode.getRz() + ROLL_SPEED;
        }
        rolling = true;
    }


    public void fire(SoftwareRenderer renderer) {

        if (timeSinceLastFire > FIRE_RATE) {
            timeSinceLastFire = 0.f;
            float[][] forward = shipBase.getForwardVector();
            float x = shipBase.getX() - forward[0][0] * LASER_OFFSET;
            float y = shipBase.getY() + forward[1][0] * LASER_OFFSET;
            float z = shipBase.getZ() + forward[2][0] * LASER_OFFSET;

            if (!LasersIdle.isEmpty()) {
                SceneNode laser = LasersIdle.remove(0);
                Lasers.add(laser);
                laser.setTranslate(x, y, z);
                laser.setRx(shipBase.getRx());
                laser.setRy(shipBase.getRy());
                laser.setRz(shipBase.getRz());
                renderer.add_renderable(laser);
            }
        }
    }

    public void updateShip(float timeSinceLastFrame, InputEvents inputEvents, SoftwareRenderer renderer) {

        this.timeSinceLastFire += timeSinceLastFrame;
        // reset roll
        rolling = false;
        // turn left or right
        if (inputEvents.isTurnLeft())
            this.turnLeft(timeSinceLastFrame);
        if (inputEvents.isTurnRight())
            this.turnRight(timeSinceLastFrame);
        // if forward button is pushed use Boost speed
        if (inputEvents.isForward()) {
            this.flyForward(timeSinceLastFrame, BOOST_SPEED);
        } else {
            this.flyForward(timeSinceLastFrame, MOVE_SPEED);
        }
        // fire lasers
        if (inputEvents.isFire()) {
            this.fire(renderer);
        }
        // update laser nodes and remove them afterwards
        this.updateLasers(timeSinceLastFrame, renderer);
        // place ship on the opposite screen edge after it flies out of bounds
        this.checkForBounds();
        // cosmetics
        this.roll();
        if (this.hit) {
            this.hitSequence(timeSinceLastFrame);
        }
    }

    private void roll() {
        if (!rolling) {
            this.roll = roll * ROLL_UNDO_SPEED;
        }
        shipNode.setRz(roll);
    }

    private void hitSequence(float timeSinceLastFrame) {

        timeSinceLastBlink += timeSinceLastFrame;
        totalBlinkTime += timeSinceLastFrame;

        if (timeSinceLastBlink > BLINK_RATE) {
            timeSinceLastBlink = 0;
            shipNode.setVisibility(!shipNode.isVisible());
        }
        if (totalBlinkTime > BLINK_DURATION) {
            totalBlinkTime = 0;
            hit = false;
            shipNode.setVisibility(true);
        }
    }

    private void checkForBounds() {
        if (shipBase.getX() > ValiantSpace2.ARENA_BOUNDS) {
            shipBase.setX(-ValiantSpace2.ARENA_BOUNDS);
        }

        if (shipBase.getX() < -ValiantSpace2.ARENA_BOUNDS) {
            shipBase.setX(ValiantSpace2.ARENA_BOUNDS);
        }

        if (shipBase.getZ() > ValiantSpace2.ARENA_BOUNDS) {
            shipBase.setZ(-ValiantSpace2.ARENA_BOUNDS);
        }

        if (shipBase.getZ() < -ValiantSpace2.ARENA_BOUNDS) {
            shipBase.setZ(ValiantSpace2.ARENA_BOUNDS);
        }
    }

    private void updateLasers(float timeSinceLastFrame, SoftwareRenderer renderer) {

        ArrayList<SceneNode> nodesToRemove = new ArrayList<>();
        for (SceneNode laser : Lasers) {
            // check if laser travelled its maximum distance and shall be removed
            if (MathBib.getDistance(laser.getTranslate(), shipBase.getTranslate()) > LADER_RADIUS) {
                nodesToRemove.add(laser);
                LasersIdle.add(laser);
                renderer.remove_renderable(laser);
            } else {
                float[][] forward = MathBib.multiply_vec3(laser.getLastCalculatedForwardVector(),
                        LASER_SPEED * timeSinceLastFrame);
                laser.setTranslate(laser.getX() - forward[0][0],
                        laser.getY() + forward[1][0],
                        laser.getZ() + forward[2][0]);
            }
        }
        // remove lasers which surpassed their radius
        for (SceneNode remove : nodesToRemove) {
            Lasers.remove(remove);
        }
    }

}
