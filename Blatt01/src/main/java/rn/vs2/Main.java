package rn.vs2;

import rn.vs2.logic.InputEvents;
import rn.vs2.logic.ValiantSpace2Logic;
import rn.vs2.renderer.SoftwareRenderer;
import rn.vs2.renderer.StdDraw;

import java.awt.event.KeyEvent;

public class Main {

    static float move_speed = 0.008f;
    static float rotation_speed = 0.04f;
    static float cam_x = 0, cam_y = 0, cam_z = 0;


    public static void main(String[] args) {
        SoftwareRenderer renderer = new SoftwareRenderer();


//        renderer.add_renderable(level);

        ValiantSpace2Logic logic = new ValiantSpace2Logic(renderer);
        logic.setUp();

        logic.setUpControls();

        InputEvents input = new InputEvents();

        // Get input
        while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {

            // process input
            if (StdDraw.isKeyPressed(KeyEvent.VK_W))
                input.setForward(true);
            if (StdDraw.isKeyPressed(KeyEvent.VK_A))
                input.setTurnLeft(true);
            if (StdDraw.isKeyPressed(KeyEvent.VK_D))
                input.setTurnRight(true);

            logic.control(input);
        }

    }

}
