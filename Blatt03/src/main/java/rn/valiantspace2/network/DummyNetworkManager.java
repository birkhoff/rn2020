package rn.valiantspace2.network;

import rn.valiantspace2.objects.InputEvents;
import rn.valiantspace2.renderer.MathBib;

/**
 * Dummy network manager which just randomly generates input locally
 */
public class DummyNetworkManager implements NetworkManager {

    private String secondClientAddress;
    private int secondClientPort;
    private int localPort;
    

    /**
     * Constructor which receives the ip address of the second client to connect to
     */
    public DummyNetworkManager() {
    }

    public void setDestinationAddressAndPort(String ipAddress, int port) {
        this.secondClientAddress = ipAddress;
        this.secondClientPort = port;
    }

    @Override
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    @Override
    public void setUpServer() {

        /*TODO
         * Initialisieren Sie einen Server für den Versand und das Empfangen von Paketen
         * Nuzten Sie den ServerSocket! Dieser blockiert wenn er Verbindungen akzeptiert,
         * also lassen Sie ihn in einem extra Thread laufen!
         * */

    }

    @Override
    public boolean tryConnectingToOtherClient() {

        boolean connectionEstablished = false;

        /*Todo
         * Verbinden Sie sich mit einem anderen Peer
         * Nutzen Sie hierfür einen weiteren Socket der als Parameter die IP und Port des
         * Servers entgegennimmt
         * */

        return connectionEstablished;
    }

    @Override
    public void stopServer() {
        /*Todo
         * Schließen Sie alle Verbindungen
         */
    }

    @Override
    public void sendInput(InputEvents inputEvents) {

        /*TODO
         * Implementieren Sie den Versand der Eingaben
         * */

        System.out.println("VS2 Packet"
                + "\n" + inputEvents.isFire()
                + "\n" + inputEvents.isForward()
                + "\n" + inputEvents.isTurnLeft()
                + "\n" + inputEvents.isTurnRight()
                + "\n" + inputEvents.getStartX()
                + "\n" + inputEvents.getStartZ()
                + "\n" + inputEvents.getStartRy()
        );

    }

    @Override
    public void getNetworkInput(InputEvents inputEvents) {

        /*TODO
         * Implementieren Sie die Verarbeitung der Eingaben
         * */

        inputEvents.reset();

        // dummy input values
        if (Math.random() > 0.5)
            inputEvents.setFire(true);

        if (Math.random() > 0.5)
            inputEvents.setForward(true);

        if (Math.random() > 0.7)
            if (Math.random() > 0.5)
                inputEvents.setTurnLeft(true);
            else
                inputEvents.setTurnRight(true);

        // dummy starting position values
        inputEvents.setStartX((float) Math.random() * 10);
        inputEvents.setStartZ((float) Math.random() * 10);
        inputEvents.setStartRy((float) Math.random() * MathBib.PI);
    }
}
