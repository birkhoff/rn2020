package rn.valiantspace2;

import rn.valiantspace2.logic.ValiantSpace2Logic;
import rn.valiantspace2.objects.InputEvents;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.StdDraw;

import java.awt.event.KeyEvent;

public class Main {


    public static void main(String[] args) {
        SoftwareRenderer renderer = new SoftwareRenderer();


        InputEvents input = new InputEvents();

        StdDraw.text(0, 0, "Press Space to start");
        StdDraw.text(0, 0.3, "control the spaceship with W, A, S, D");
        StdDraw.text(0, 0.2, "press Space to fire");
        StdDraw.show();

        while (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {


        }


        ValiantSpace2Logic logic = new ValiantSpace2Logic(renderer);
        logic.setUp();


        // Get input
        while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {

            input.reset();
            // process input
            if (StdDraw.isKeyPressed(KeyEvent.VK_W))
                input.setForward(true);
            if (StdDraw.isKeyPressed(KeyEvent.VK_A))
                input.setTurnLeft(true);
            if (StdDraw.isKeyPressed(KeyEvent.VK_D))
                input.setTurnRight(true);
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
                input.setFire(true);

            logic.update(input);
        }

    }

}
