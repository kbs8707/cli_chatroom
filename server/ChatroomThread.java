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
            
            list();
            
            String username = "";
            
            // while (true) {
                username = in.readLine();
                //     if (server.nameAvailable(username)) {
                    //         break;
                    //     }
                    //     else {
                        //         out.println("Name taken, please enter a different name");
                        //         out.println("Enter your username");
                        //     }
                        // }
                        System.out.println(username + " has connected");
                        
                        server.addUser(username, this);
                        server.boradcast(username + " has entered the chat", this);
                        
                        String input;
                        while ((input = in.readLine()) != null) {
                            String operation = commands(input, username);
                            if (operation != null) {
                                if (operation.equals("quit")) {
                                    break;
                                }
                            }
                            else {
                                String msg = "[" + username + "]: " + input;
                                server.boradcast(msg, this);
                            }
                        }

                        socket.close();
                    }
                    catch(IOException e) {
                        System.err.println(e.getMessage());
                    }
                    
                }
                
                void sendMessage(String msg) {
                    out.println(msg);
                }
                
                void list() {
                    if (server.getUserList().size() != 0) {
                        out.println("Current users in chat room");
                        for (String user : server.getUserList()) {
                            out.println(user);
                        }
                    }
                    else {
                        out.println("Chat room is empty");
                    }
                }
                
                String commands(String input, String username) {
                    if (input.contains("/")) {
                        if (input.equals("/list")) {
                            this.list();
                            return "list";
                        }
                        else if (input.equals("/quit")) {
                            server.removeUser(username, this);
                            server.boradcast(username + " has left the chat", this);
                            System.out.println(username + " has disconnected");
                            return "quit";
                        }
                        else {
                            String arr[] = input.split(" ", 2);
                            String dest = arr[0].substring(1);
                            if (!server.nameAvailable(dest)) {
                                String msg = "[" + username + "]: " + arr[1];
                                server.directMessage(msg, this, server.getThreadByUsername(dest));
                                return "dm";
                            }
                        }
                        return null;
                    }
                    return null;
                }
                
            }
            