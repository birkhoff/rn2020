package rn.meowmeowchat;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MeowMeowChat {

    private static final int PORT = 8000;
    private static final String ROUTE = "/meow";

    public static void main(String[] args)  {

        MeowMeowHttpHandler httpHandler = new MeowMeowHttpHandler();

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext(ROUTE, httpHandler);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
