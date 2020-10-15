package rn.valiantspace2;

import rn.valiantspace2.logic.InputEvents;
import rn.valiantspace2.logic.ValiantSpace2Logic;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.StdDraw;

import java.awt.event.KeyEvent;

public class Main {


    public static void main(String[] args) {
        SoftwareRenderer renderer = new SoftwareRenderer();


//        renderer.add_renderable(level);

        ValiantSpace2Logic logic = new ValiantSpace2Logic(renderer);
        logic.setUp();

        logic.setUpControls();

        InputEvents input = new InputEvents();

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
