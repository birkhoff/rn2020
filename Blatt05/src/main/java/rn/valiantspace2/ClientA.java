package rn.valiantspace2;

import rn.valiantspace2.objects.ValiantSpace2;

/**
 * Use client A to communicate with client B
 */
public class ClientA {

    /**
     * main method for client a
     */

    public static String IP_CLIENT_B = "localhost";
    public static int PORT_CLIENT_B = 4321;
    public static int LOCAL_PORT = 1234;

    public static void main(String[] args) {
        ValiantSpace2 game = new ValiantSpace2();
        game.runGame(LOCAL_PORT, IP_CLIENT_B, PORT_CLIENT_B);
    }

}
