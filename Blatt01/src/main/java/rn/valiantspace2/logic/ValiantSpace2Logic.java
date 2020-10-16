package rn.valiantspace2.logic;

import rn.valiantspace2.objects.InputEvents;
import rn.valiantspace2.objects.SpaceShip;
import rn.valiantspace2.renderer.Camera;
import rn.valiantspace2.renderer.MathBib;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.data.SceneNode;
import rn.valiantspace2.renderer.parser.StanfordTriangleParser;

import java.util.ArrayList;

/**
 * Logic for Valiant Space 2
 *
 * @author Mike
 */
public class ValiantSpace2Logic {

    private static final String PLAYER_MODEL = "src/main/resources/ship.ply";
    private static final String OPPONENT_MODEL = "src/main/resources/ship_enemy.ply";

    private static double HIT_DISTANCE = 5.0;

    private SoftwareRenderer renderer;

    private SpaceShip shipPlayer;
    private SpaceShip shipOpponent;
    private Camera cam;

    private ArrayList<SceneNode> LasersIdle = new ArrayList();
    private ArrayList<SceneNode> Lasers = new ArrayList();

    long timeSinceLastFrame = 0;


    /**
     * Constructor for game logic expects a renderer
     *
     * @param renderer renderer to use
     */
    public ValiantSpace2Logic(SoftwareRenderer renderer) {

        this.renderer = renderer;
    }


    /**
     * setup game state
     * make camera look down upon players
     * load ships
     */
    public void setUp(InputEvents inputLocalPlayer, InputEvents inputNetworkPlayer) {

        StanfordTriangleParser parser = new StanfordTriangleParser();

        shipPlayer = new SpaceShip(renderer, parser, PLAYER_MODEL);
        shipOpponent = new SpaceShip(renderer, parser, OPPONENT_MODEL);

        this.placeSpaceship(shipPlayer, inputLocalPlayer);
        this.placeSpaceship(shipOpponent, inputNetworkPlayer);

        cam = renderer.get_camera();
        cam.set_position(0, 40.0f, 0);
        cam.add_rotation_x(-MathBib.PI / 2);
    }


    /**
     * Updates game logic
     * check for input events, updates ship movement and check for hits
     *
     * @param inputLocalPlayer   input from local device
     * @param inputNetworkPlayer input from network player
     */
    public void update(InputEvents inputLocalPlayer, InputEvents inputNetworkPlayer) {

        shipPlayer.updateShip(timeSinceLastFrame, inputLocalPlayer, renderer);
        shipOpponent.updateShip(timeSinceLastFrame, inputNetworkPlayer, renderer);
        // check collision
        checkCollision(shipPlayer, shipOpponent);
        checkCollision(shipOpponent, shipPlayer);
        // draw
        timeSinceLastFrame = renderer.render();
    }

    /**
     * Set initial position in space for a ship
     *
     * @param ship
     * @param inputEvents
     */
    private void placeSpaceship(SpaceShip ship, InputEvents inputEvents) {
        ship.getNode().setTranslate(inputEvents.getStartX(), 0, inputEvents.getStartZ());
        ship.getNode().setRy(inputEvents.getStartRy());
    }

    /**
     * Check for collision
     *
     * @param shipFired ship that has fires lasers
     * @param shipHit   ship that is checked for a hit
     */
    private void checkCollision(SpaceShip shipFired, SpaceShip shipHit) {
        if (shipHit.canBeHit()) {
            for (SceneNode laser : shipFired.getLasers()) {
                double distance = MathBib.getDistance(laser.getTranslate(), shipHit.getNode().getTranslate());
                if (distance < HIT_DISTANCE) {
                    shipHit.hit();
                }
            }
        }
    }

}
