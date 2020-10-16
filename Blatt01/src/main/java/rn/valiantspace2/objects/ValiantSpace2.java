package rn.valiantspace2.objects;

import rn.valiantspace2.logic.ValiantSpace2Logic;
import rn.valiantspace2.renderer.MathBib;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.StdDraw;

import java.awt.event.KeyEvent;

/**
 * class that runs the game
 */
public class ValiantSpace2 {

    public static final float ARENA_BOUNDS = 45.0f;

    public ValiantSpace2() {
    }

    /**
     * Game loop
     */
    public void runGame() {

        // set up
        SoftwareRenderer renderer = new SoftwareRenderer();
        InputEvents inputLocalPlayer = new InputEvents();
        InputEvents inputNetworkPlayer = new InputEvents();
        this.displayStartText();

        // randomize start position
        this.randomizeStartPosition(inputLocalPlayer);

        while (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {


        }

        ValiantSpace2Logic logic = new ValiantSpace2Logic(renderer);
        logic.setUp(inputLocalPlayer, inputNetworkPlayer);

        // game loop
        while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {

            this.receiveLocalInput(inputLocalPlayer);

            logic.update(inputLocalPlayer, inputNetworkPlayer);
        }

    }

    private void receiveLocalInput(InputEvents inputLocalPlayer) {
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
        StdDraw.text(0, 0.3, "control the spaceship with W, A, S, D");
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

