package rn.valiantspace2.network;

import rn.valiantspace2.objects.InputEvents;

public interface NetworkManager {

    /**
     * Set the ip and address and port we want to send packages to
     *
     * @param ipAddress ip address of network player
     * @param port      port of network player
     */
    void setDestinationAddressAndPort(String ipAddress, int port);

    /**
     * Set the local port we want to use to receive packages
     *
     * @param localPort port on this computer which receives packets
     */
    void setLocalPort(int localPort);

    /**
     * Set up the server and start listening
     */
    void setUpServer();

    /**
     * Stops the server
     */
    void stopServer();

    /**
     * Try to establish a connection with the other client
     *
     * @return true if success
     */
    boolean tryConnectingToOtherClient();

    /**
     * send own input events over the network
     *
     * @param inputEvents input events from local player
     */
    void sendInput(InputEvents inputEvents);

    /**
     * Get input events received over the network
     *
     * @param inputEvents object to add events from network player into
     */
    void getNetworkInput(InputEvents inputEvents);
}
