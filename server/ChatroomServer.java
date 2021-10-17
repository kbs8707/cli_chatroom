package server;

import java.net.*;
import java.util.*;

public class ChatroomServer {
    private int port;
    private Set<String> usernames = new HashSet<>();
    private Set<ChatroomThread> threads = new HashSet<>();

    public ChatroomServer (int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket client = server.accept();

                // System.out.println("Client IP: "+client.getRemoteSocketAddress().toString()+" has connected");

                ChatroomThread thread = new ChatroomThread(client, this);
                new Thread(thread).start();
                
                threads.add(thread);

            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChatroomServer <port number>");
            System.exit(1);
        }
        
        int port = Integer.parseInt(args[0]);

        ChatroomServer server = new ChatroomServer(port);
        System.out.println("Server has started");

        server.run();

    }

    void boradcast(String msg, ChatroomThread source) {
        for (ChatroomThread user : threads) {
            if (user != source) {
                user.sendMessage(msg);
            }
        }
    }

    void directMessage(String msg, ChatroomThread source, ChatroomThread dest) {
        
    }

    void addUser(String username) {
        usernames.add(username);
    }

    void removeUser(String username, ChatroomThread source) {
        usernames.remove(username);
        threads.remove(source);
    }

    boolean nameAvailable(String username) {
        return !usernames.contains(username);
    }

    Set<String> getUserList() {
        return this.usernames;
    }

}