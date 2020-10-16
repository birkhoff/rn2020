package rn.valiantspace2;

import rn.valiantspace2.objects.ValiantSpace2;

/**
 * Client B is used to test two clients communicating on the same machine
 */
public class ClientB {

    /**
     * main method for client b
     *
     * @param args
     */
    public static void main(String[] args) {
        ValiantSpace2 game = new ValiantSpace2();
        game.runGame();
    }
}
