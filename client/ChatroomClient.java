package client;

import java.io.*;
import java.net.*;

public class ChatroomClient {
    private String username;
    private String roomName;
    private int port;

    public ChatroomClient(int port) {
        this.port = port;
    }

    public void run() {
        try{
            Socket echoSocket = new Socket("localhost", port);

            //Every newly connected client will be allocated a read thread and a write thread
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

    //Client keeps track of their own username and room name
    //username and room name are used as parameter when communicating to the server
    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }

    void setroomName(String roomName) {
        this.roomName = roomName;
    }

    String getRoomName() {
        return roomName;
    }

}
