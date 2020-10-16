package rn.valiantspace2.network;

import rn.valiantspace2.objects.InputEvents;

public interface NetworkManager {

    /**
     * Set the ip and address and port we want to send packages to
     *
     * @param ipAddress ip address of network player
     * @param port      port of network player
     */
    void setAddressAndPort(String ipAddress, int port);

    /**
     * send own input events over the network
     *
     * @param inputEvents
     */
    void sendInput(InputEvents inputEvents);

    /**
     * Get input events received over the network
     *
     * @param inputEvents object to add events from network player into
     */
    void getNetworkInput(InputEvents inputEvents);
}
