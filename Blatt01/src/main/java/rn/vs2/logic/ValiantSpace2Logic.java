package rn.vs2.logic;

import rn.vs2.renderer.Camera;
import rn.vs2.renderer.SoftwareRenderer;
import rn.vs2.renderer.StdDraw;
import rn.vs2.renderer.data.PlyObject;
import rn.vs2.renderer.data.SceneNode;
import rn.vs2.renderer.parser.StanfordTriangleParser;

import java.awt.event.KeyEvent;

/**
 * Logic for Valiant Space 2
 *
 * @author Mike
 */
public class ValiantSpace2Logic {

    private SoftwareRenderer renderer;

    private Camera cam;
    private SceneNode node;

    static float move_speed = 0.008f;
    static float rotation_speed = 0.04f;
    static float cam_x = 0, cam_y = 0, cam_z = 0;

    float[][] translation = {{0}, {0}, {0}};
    double mouseX;
    double mouseY;
    boolean pressed_moused;


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
        PlyObject ship = parser.parsePlyFile("src/main/resources/ship.ply");


        node = StanfordTriangleParser.fill_ply_object(ship, renderer);
        node.setTranslate(0, -2, -15);

        cam = renderer.get_camera();
        cam.set_position(cam_x, cam_y + 0.75f, cam_z); // position camera above ground
    }

    public void setUpControls() {

        mouseX = -1;
        mouseY = -1;
        pressed_moused = false;
    }

    public void control(InputEvents inputEvents) {
        float move_speed_z = 0;

        if (StdDraw.isKeyPressed(KeyEvent.VK_W))
            move_speed_z = move_speed;
        if (StdDraw.isKeyPressed(KeyEvent.VK_S))
            move_speed_z = -move_speed;

        translation[2][0] = move_speed_z;

        float move_speed_x = 0;

        if (StdDraw.isKeyPressed(KeyEvent.VK_A))
            move_speed_x = move_speed;
        if (StdDraw.isKeyPressed(KeyEvent.VK_D))
            move_speed_x = -move_speed;

        translation[0][0] = move_speed_x;

        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            cam.set_rotation_y(rotation_speed);
        else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            cam.set_rotation_y(-rotation_speed);

        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
            renderer.toggle_draw_raster();


        if (StdDraw.mousePressed()) {
            if (!pressed_moused) {
                mouseX = StdDraw.mouseX();
                mouseY = StdDraw.mouseY();

                pressed_moused = true;
            }

            cam.set_rotation_values(-((float) (mouseY - StdDraw.mouseY()) * 1.5f),
                    ((float) (mouseX - StdDraw.mouseX())) * 4.f);
            mouseX = StdDraw.mouseX();
            mouseY = StdDraw.mouseY();

        } else {
            pressed_moused = false;
        }

        // draw

        long timeSinceLastFrame = renderer.render();

        // rotate
        node.setRy(node.getRy() + 0.0025f * timeSinceLastFrame);
        node.setRz(node.getRz() + 0.00125f * timeSinceLastFrame);
        node.setRx(node.getRx() + 0.000625f * timeSinceLastFrame);

        // move
        translation[0][0] = translation[0][0] * timeSinceLastFrame;
        translation[2][0] = translation[2][0] * timeSinceLastFrame;
        cam.move(translation);

    }
}
