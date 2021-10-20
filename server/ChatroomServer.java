package server;

import java.net.*;
import java.util.*;

public class ChatroomServer {
    private int port;
    private Set<String> usernames = new HashSet<>();
    private Set<ChatroomThread> threads = new HashSet<>();

    //Associates username with the thread that is created for them
    private Map<String, ChatroomThread> userMap = new HashMap<>();
    
    public ChatroomServer (int port) {
        this.port = port;
    }
    
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            
            //Runs forever to ready to accept client connections
            while (true) {
                Socket client = server.accept();
                
                // System.out.println("Client IP: "+client.getRemoteSocketAddress().toString()+" has connected");
                
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
        
        //Creates an instance of the server
        ChatroomServer server = new ChatroomServer(port);
        System.out.println("Server has started");
        
        server.run();
        
    }
    
    //Sends message to everyone but the sender
    void boradcast(String msg, ChatroomThread source) {
        for (ChatroomThread user : threads) {
            if (user != source) {
                user.sendMessage(msg);
            }
        }
    }
    
    //Sends message to a specific user
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
    
    //Given username returns the thread associated with it, this enables direct messaging
    ChatroomThread getThreadByUsername(String username) {
        return userMap.get(username);
    }
    
}