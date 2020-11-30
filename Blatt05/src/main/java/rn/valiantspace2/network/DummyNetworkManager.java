package rn.valiantspace2.network;

import rn.valiantspace2.objects.InputEvents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Dummy network manager which just randomly generates input locally
 */
public class DummyNetworkManager implements NetworkManager {

    private String secondClientAddress;
    private int secondClientPort;
    private int localPort;

    private ServerSocket serverSocket;
    private Socket connectedClientSocket;
    private Socket connectedServerSocket;
    private volatile PrintWriter out;
    private volatile BufferedReader in;


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

        Runnable serverTask = () -> {
            try {
                serverSocket = new ServerSocket(localPort);
                connectedClientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(connectedClientSocket.getInputStream()));

                System.out.println("Connection from other client");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

    }

    @Override
    public boolean tryConnectingToOtherClient() {

        boolean connectionEstablished = false;

        while (!connectionEstablished) {

            try {
                connectedServerSocket = new Socket(secondClientAddress, secondClientPort);
                out = new PrintWriter(connectedServerSocket.getOutputStream(), true);
                connectionEstablished = true;

                System.out.println("Connection to other client");

            } catch (IOException e) {
                /* e.printStackTrace(); */
            }
        }

        return connectionEstablished;
    }

    @Override
    public void stopServer() {
        try {
            in.close();
            out.close();
            connectedServerSocket.close();
            connectedClientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendInput(InputEvents inputEvents) {

        String input = inputEvents.isFire()
                + ";" + inputEvents.isForward()
                + ";" + inputEvents.isTurnLeft()
                + ";" + inputEvents.isTurnRight()
                + ";" + inputEvents.getStartX()
                + ";" + inputEvents.getStartZ()
                + ";" + inputEvents.getStartRy();

        out.println(input);
    }

    @Override
    public void getNetworkInput(InputEvents inputEvents) {

        inputEvents.reset();

        try {
            String inputMessage = in.readLine();

            System.out.println("Response: " + inputMessage);

            String[] inputs = inputMessage.split(";");

            inputEvents.setFire(Boolean.parseBoolean(inputs[0]));
            inputEvents.setForward(Boolean.parseBoolean(inputs[1]));
            inputEvents.setTurnLeft(Boolean.parseBoolean(inputs[2]));
            inputEvents.setTurnRight(Boolean.parseBoolean(inputs[3]));
            inputEvents.setStartX(Float.parseFloat(inputs[4]));
            inputEvents.setStartZ(Float.parseFloat(inputs[5]));
            inputEvents.setStartRy(Float.parseFloat(inputs[6]));

        } catch (Exception e) {
            /* e.printStackTrace(); */
        }
    }
}
