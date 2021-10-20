package client;

import java.io.*;
import java.net.*;

public class ChatroomClient {
    private String username;
    private int port;

    public ChatroomClient(int port) {
        this.port = port;
    }

    public void run() {
        try{
            Socket echoSocket = new Socket("localhost", port);

            new ClientReadThread(echoSocket, this).start();
            new ClientWriteThread(echoSocket, this).start();

        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }

    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(
            "Usage: java client.ChatroomClient <port number>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        ChatroomClient client = new ChatroomClient(port);
        client.run();
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }


}
