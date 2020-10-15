package rn.valiantspace2.logic;

import rn.valiantspace2.renderer.Camera;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.data.PlyObject;
import rn.valiantspace2.renderer.data.SceneNode;
import rn.valiantspace2.renderer.parser.StanfordTriangleParser;

import java.util.ArrayList;

/**
 * Logic for Valiant Space 2
 *
 * @author Mike
 */
public class ValiantSpace2Logic {

    private float PI = 3.14159265359f;

    private SoftwareRenderer renderer;

    private Camera cam;
    private SceneNode ship1;

    private ArrayList<SceneNode> LasersIdle = new ArrayList();
    private ArrayList<SceneNode> Lasers = new ArrayList();

    long timeSinceLastFrame = 0;

    static float MOVE_SPEED = 0.0075f;
    static float LASER_SPEED = 0.015f;
    static float LASER_OFFSET = 10.f;
    static float ROTATION_SPEED = 0.0025f;


    /**
     * Constructor for game logic expects a renderer
     *
     * @param renderer renderer to use
     */
    public ValiantSpace2Logic(SoftwareRenderer renderer) {

        this.renderer = renderer;
    }


    public void setUp() {

        StanfordTriangleParser parser = new StanfordTriangleParser();
        PlyObject shipPly = parser.parsePlyFile("src/main/resources/ship.ply");

        ship1 = StanfordTriangleParser.fill_ply_object(shipPly, renderer);
        renderer.add_renderable(ship1);
        ship1.setTranslate(0, 0, 0);

        cam = renderer.get_camera();
        cam.set_position(0, 40.0f, 0);
        cam.add_rotation_x(-PI / 2);

        // preload lasers
        PlyObject laserPly = parser.parsePlyFile("src/main/resources/laser.ply");
        for (int i = 0; i < 10; i++) {
            SceneNode laser = StanfordTriangleParser.fill_ply_object(laserPly, renderer);
            LasersIdle.add(laser);
        }

    }

    public void setUpControls() {


    }

    public void update(InputEvents inputEvents) {


        if (inputEvents.isTurnLeft())
            ship1.setRy(ship1.getRy() + ROTATION_SPEED * timeSinceLastFrame);
        if (inputEvents.isTurnRight())
            ship1.setRy(ship1.getRy() - ROTATION_SPEED * timeSinceLastFrame);

        if (inputEvents.isForward()) {
            float[][] forward = ship1.getForwardVector();
            float x = forward[0][0] * MOVE_SPEED * timeSinceLastFrame;
            float y = forward[1][0] * MOVE_SPEED * timeSinceLastFrame;
            float z = forward[2][0] * MOVE_SPEED * timeSinceLastFrame;

            ship1.setTranslate(ship1.getX() - x,
                    ship1.getY() + y,
                    ship1.getZ() + z);
        }

        if (inputEvents.isFire()) {
            float[][] forward = ship1.getForwardVector();
            float x = ship1.getX() - forward[0][0] * LASER_OFFSET;
            float y = ship1.getY() + forward[1][0] * LASER_OFFSET;
            float z = ship1.getZ() + forward[2][0] * LASER_OFFSET;

            if (!LasersIdle.isEmpty()) {
                SceneNode laser = LasersIdle.remove(0);
                Lasers.add(laser);
                laser.setTranslate(x, y, z);
                laser.setRx(ship1.getRx());
                laser.setRy(ship1.getRy());
                laser.setRz(ship1.getRz());
                renderer.add_renderable(laser);
            }
        }

        // draw
        timeSinceLastFrame = renderer.render();


    }
}
