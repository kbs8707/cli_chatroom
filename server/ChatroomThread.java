package server;

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatroomThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private ChatroomServer server;
    
    public ChatroomThread(Socket socket, ChatroomServer server) {
        this.socket = socket;
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            //Displays a list of connected users in the room, useful for checking for duplicate usernames
            list();
            
            String username = "";
            
            username = in.readLine();
            System.out.println(username + " has connected");
            
            //Prompts the client to join a room, or create one
            listRoom();
            String roomName = "";
            roomName = in.readLine();
            
            server.addToRoom(roomName, username, this);
            server.boradcast("[System]: " + username + " has entered the chat", this, roomName, username);
            
            String input;

            //Polls for communications from the client
            while ((input = in.readLine()) != null) {
                //Scans for client message for function calls
                String operation = commands(input, username, roomName);
                
                if (operation != null) {
                    //If a quit command is issued, terminates the infinite loop, and closes the socket
                    if (operation.equals("quit")) {
                        break;
                    }
                }
                //If the message is not calling for server functions then it must be a broadcast
                else {
                    String msg = "[" + username + "]: " + input;
                    server.boradcast(msg, this, roomName, username);
                }
            }
            
            socket.close();
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    //Sends message to the client via socket
    void sendMessage(String msg) {
        out.println(msg);
    }
    
    //Lists all users in server
    void list() {
        out.println("----------------------------------");
        if (server.getUserList().size() != 0) {
            out.println("Current users in chat room");
            for (String user : server.getUserList()) {
                out.println(user);
            }
        }
        else {
            out.println("Chat room is empty");
        }
        out.println("----------------------------------");
    }
    
    //Lists all users in room
    void listRoom() {
        out.println("----------------------------------");
        if (server.getRoomMap().size() != 0) {
            out.println("Current open rooms");
            for (String room : server.getRoomMap().keySet()) {
                out.println(room);
            }
        }
        else {
            out.println("No open rooms");
        }
        out.println("----------------------------------");
    }
    
    void roomMemberList(String roomName) {
        out.println("----------------------------------");
        if (server.getRoomMap().get(roomName).size() != 0) {
            out.println("Current users in chat room: " + roomName);
            for (Map.Entry<String, ChatroomThread> entry : server.getRoomMap().get(roomName).entrySet()) {
                out.println(entry.getKey());
            }
        }
        else {
            out.println("Chat room is empty");
        }
        out.println("----------------------------------");
    }   

    void showHelp(){
        out.println("----------------------------------");
        out.println("<message> - broadcast message to all users in same room");
        out.println("/list - displays list of users in current room");
        out.println("/<username> <message> - message specified user directly");
        out.println("/quit - exit program");
        out.println("----------------------------------");
    }
    
    //All communications from client will be scanned for commands
    String commands(String input, String username, String roomName) {
        if (input.contains("/")) {
            if (input.equals("/list")) {
                this.roomMemberList(roomName);
                return "list";
            }
            else if (input.equals("/quit")) {
                server.removeUser(username, this, roomName);
                server.boradcast(username + " has left the chat", this, roomName, username);
                System.out.println(username + " has disconnected");
                return "quit";
            }
            else if (input.equals("/help")) {
                this.showHelp();
                return "help";
            }
            else {
                String arr[] = input.split(" ", 2);
                String dest = arr[0].substring(1);
                if (!server.nameAvailable(dest, roomName)) {
                    String msg = "[" + username + "]: " + arr[1];
                    server.directMessage(msg, this, server.getThreadByUsername(dest, roomName), roomName, dest);
                    return "dm";
                }
            }
            return null;
        }
        return null;
    }
    
}
