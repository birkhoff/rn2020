package rn.meowmeowchat;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.stream.Collectors.joining;

public class MeowMeowHttpHandler implements HttpHandler {

    private final String HEADER = "Meow Meow Chat:\n<br>\n--------------\n<br><br>\n";

    private List<String> messages = new ArrayList<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String response = "";
        int returnCode = HTTP_BAD_REQUEST;

        switch(exchange.getRequestMethod()) {
            case "GET":
                response = getAllMessagesAsString();
                returnCode = HTTP_OK;
                break;

            case"POST":
                String newMessage = this.parseRequestBody(exchange);
                messages.add(newMessage);
                response = newMessage;
                returnCode = HTTP_OK;
                break;

            case "DELETE":
                this.deleteAllMessages();
                returnCode = HTTP_OK;
                break;

            default:
                break;
        }

        // create utf 8 buffer
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(response);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(returnCode, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    /**
     * creates a string from all messages received
     * @return
     */
    private String getAllMessagesAsString() {

        StringBuilder sb = new StringBuilder();
        sb.append(HEADER);
        for (String message : messages) {
            sb.append(message).append("\n<br><br>\n");
        }
        return sb.toString();
    }

    /**
     * Parse the input stream of an httpExchange request body
     * @param exchange
     * @return request body as string
     */
    private String parseRequestBody(HttpExchange exchange) {

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String parsedMessage = br.lines().collect(joining(lineSeparator()));

        return parsedMessage;
    }

    /**
     * Delete all Messages
     */
    private void deleteAllMessages() {
        messages.clear();
    }

}

