package server;

import java.net.*;
import java.util.*;

public class ChatroomServer {
    private int port;
    private Set<String> usernames = new HashSet<>();
    private Set<ChatroomThread> threads = new HashSet<>();
    private Set<String> rooms = new HashSet<>();

    //Associates username with the thread that is created for them
    private Map<String, ChatroomThread> userMap = new HashMap<>();
    private HashMap<String, Map<String, ChatroomThread>> roomMap = new HashMap<>();

    public ChatroomServer (int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket client = server.accept();
                //Creates a thread to handle every new client connection

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

    void boradcast(String msg, ChatroomThread source, String roomName, String username) {
        for (Map.Entry<String, ChatroomThread> entry : roomMap.get(roomName).entrySet()) {
            if (entry.getKey() != username) {
                entry.getValue().sendMessage(msg);
            }
        }
    }

    void directMessage(String msg, ChatroomThread source, ChatroomThread dest, String roomName, String username) {
        dest.sendMessage(msg);
    }

    void removeUser(String username, ChatroomThread source, String roomName) {
        usernames.remove(username);
        threads.remove(source);
        userMap.remove(username);
        roomMap.get(roomName).remove(username);
    }

    boolean nameAvailable(String username, String roomName) {
        return !roomMap.get(roomName).containsKey(username);
    }

    Set<String> getUserList() {
        return this.usernames;
    }

    HashMap<String, Map<String, ChatroomThread>> getRoomMap() {
        return this.roomMap;
    }

    void addToRoom(String roomName, String username, ChatroomThread source) {
        usernames.add(username);
        rooms.add(roomName);
        if (roomMap.containsKey(roomName)) {
            Map<String, ChatroomThread> update = roomMap.get(roomName);
            update.put(username, source);
            roomMap.replace(roomName, update);
        }
        else {
            Map<String, ChatroomThread> val = new HashMap<>();
            val.put(username, source);
            roomMap.put(roomName, val);
        }
    }
    
    //Given username returns the thread associated with it, this enables direct messaging
    ChatroomThread getThreadByUsername(String username, String roomName) {
        return roomMap.get(roomName).get(username);
    }

}