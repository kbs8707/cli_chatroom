package server;

import java.net.*;
import java.util.*;

public class ChatroomServer {
    private int port;
    private Set<String> usernames = new HashSet<>();
    private Set<ChatroomThread> threads = new HashSet<>();
<<<<<<< HEAD
    private Set<String> rooms = new HashSet<>();

    //Associates username with the thread that is created for them
    private Map<String, ChatroomThread> userMap = new HashMap<>();
    private HashMap<String, Set<String>> roomMap = new HashMap<>();
=======
    private Map<String, ChatroomThread> userMap = new HashMap<>();
>>>>>>> parent of afa04c3... added comments

    public ChatroomServer (int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket client = server.accept();
<<<<<<< HEAD
                                
                //Creates a thread to handle every new client connection
=======

                // System.out.println("Client IP: "+client.getRemoteSocketAddress().toString()+" has connected");

>>>>>>> parent of afa04c3... added comments
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
            System.out.println("Usage: java server.ChatroomServer <port number>");
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
        dest.sendMessage(msg);
    }

    void addUser(String username, ChatroomThread source) {
        usernames.add(username);
        userMap.put(username, source);
    }

    void removeUser(String username, ChatroomThread source) {
        usernames.remove(username);
        threads.remove(source);
        userMap.remove(username);
    }

    boolean nameAvailable(String username) {
        return !usernames.contains(username);
    }

    Set<String> getUserList() {
        return this.usernames;
    }

<<<<<<< HEAD
    HashMap<String, Set<String>> getRoomMap() {
        return this.roomMap;
    }

    void addToRoom(String roomName, String username) {
        rooms.add(roomName);
        if (roomMap.containsKey(roomName)) {
            Set<String> update = roomMap.get(roomName);
            update.add(username);
            roomMap.replace(roomName, update);
        }
        else {
            Set<String> val = new HashSet<>();
            val.add(username);
            roomMap.put(roomName, val);
        }
    }
    
    //Given username returns the thread associated with it, this enables direct messaging
=======
>>>>>>> parent of afa04c3... added comments
    ChatroomThread getThreadByUsername(String username) {
        return userMap.get(username);
    }

}