package rn.valiantspace2;

import rn.valiantspace2.objects.ValiantSpace2;

/**
 * Use client A to communicate with client B
 */
public class ClientA {

    /**
     * main method for client a
     *
     * @param args
     */
    public static void main(String[] args) {
        ValiantSpace2 game = new ValiantSpace2();
        game.runGame();
    }

}
