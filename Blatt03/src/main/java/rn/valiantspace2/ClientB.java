package rn.valiantspace2;

import rn.valiantspace2.objects.ValiantSpace2;

/**
 * Client B is used to test two clients communicating on the same machine
 */
public class ClientB {

    /**
     * main method for client b
     */

    public static String IP_CLIENT_A = "localhost";
    public static int PORT_CLIENT_A = 1234;
    public static int LOCAL_PORT = 4321;

    public static void main(String[] args) {
        ValiantSpace2 game = new ValiantSpace2();
        game.runGame(LOCAL_PORT, IP_CLIENT_A, PORT_CLIENT_A);
    }
}
