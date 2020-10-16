package rn.valiantspace2.objects;

import rn.valiantspace2.logic.ValiantSpace2Logic;
import rn.valiantspace2.network.DummyNetworkManager;
import rn.valiantspace2.network.NetworkManager;
import rn.valiantspace2.renderer.MathBib;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.StdDraw;

import java.awt.event.KeyEvent;

/**
 * class that runs the game
 */
public class ValiantSpace2 {

    public static final float ARENA_BOUNDS = 45.0f;

    private NetworkManager networkManager;
    private InputEvents inputLocalPlayer = new InputEvents();
    private InputEvents inputNetworkPlayer = new InputEvents();
    private SoftwareRenderer renderer;

    public ValiantSpace2() {
    }

    /**
     * Game loop
     */
    public void runGame(String secondClientAddress, int secondClientPort) {

        this.setUpGame(secondClientAddress, secondClientPort);
        this.waitForPlayersLoop();
        ValiantSpace2Logic logic = new ValiantSpace2Logic(renderer);
        logic.initializeGameState(inputLocalPlayer, inputNetworkPlayer);
        this.runGameLoop(logic);
    }

    private void setUpGame(String secondClientAddress, int secondClientPort) {
        // set up network handler
        networkManager = new DummyNetworkManager();
        networkManager.setAddressAndPort(secondClientAddress, secondClientPort);
        // set up input
        renderer = new SoftwareRenderer();
        inputLocalPlayer = new InputEvents();
        inputNetworkPlayer = new InputEvents();
        // display additional text to explain user input
        this.displayStartText();
        // randomize start position and send it to network player
        this.randomizeStartPosition(inputLocalPlayer);
        networkManager.sendInput(inputLocalPlayer);
    }

    private void waitForPlayersLoop() {
        // Both Players have to trigger the fire command to start the game
        boolean waitForLocalPlayer = true;
        boolean waitForNetworkPlayer = true;
        while (waitForLocalPlayer || waitForNetworkPlayer) {
            // get local input
            this.getLocalInput(inputLocalPlayer);
            // send and receive
            networkManager.getNetworkInput(inputNetworkPlayer);
            networkManager.sendInput(inputLocalPlayer);

            if (inputLocalPlayer.isFire())
                waitForLocalPlayer = false;

            if (inputNetworkPlayer.isFire())
                waitForNetworkPlayer = false;
        }
    }

    private void runGameLoop(ValiantSpace2Logic logic) {

        while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            // get local input
            this.getLocalInput(inputLocalPlayer);
            // send and receive
            networkManager.getNetworkInput(inputNetworkPlayer);
            networkManager.sendInput(inputLocalPlayer);
            // update logic with input from local and network player
            logic.update(inputLocalPlayer, inputNetworkPlayer);
        }
    }

    private void getLocalInput(InputEvents inputLocalPlayer) {
        inputLocalPlayer.reset();
        // process input
        if (StdDraw.isKeyPressed(KeyEvent.VK_W))
            inputLocalPlayer.setForward(true);
        if (StdDraw.isKeyPressed(KeyEvent.VK_A))
            inputLocalPlayer.setTurnLeft(true);
        if (StdDraw.isKeyPressed(KeyEvent.VK_D))
            inputLocalPlayer.setTurnRight(true);
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
            inputLocalPlayer.setFire(true);
    }

    private void displayStartText() {
        StdDraw.text(0, 0, "Press Space to start");
        StdDraw.text(0, 0.3, "control the spaceship with W, A, D");
        StdDraw.text(0, 0.2, "press Space to fire");
        StdDraw.show();
    }

    private void randomizeStartPosition(InputEvents inputEvents) {

        double startX = Math.random() * ARENA_BOUNDS * 2 - ARENA_BOUNDS;
        double startY = Math.random() * ARENA_BOUNDS * 2 - ARENA_BOUNDS;
        double startRy = Math.random() * MathBib.PI * 4;

        inputEvents.setStartX((float) startX);
        inputEvents.setStartX((float) startY);
        inputEvents.setStartRy((float) startRy);
    }

}

