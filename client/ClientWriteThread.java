package client;

import java.io.*;
import java.net.*;


public class ClientWriteThread extends Thread{
    private Socket socket;
    private PrintWriter out;
    private ChatroomClient client;
    
    //Socket handles communication with the server threads, client indicates which ChatroomClient this thread belongs to
    public ClientWriteThread(Socket socket, ChatroomClient client) {
        this.socket = socket;
        this.client = client;
    }
    
    public void run() {
        try {
            //PrintWriter handles communication with the server threads
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Console console = System.console();

            //Prompts for user name for the server to associate this client with an identifier
            System.out.println("Enter your username");
            String username = console.readLine();

            //Sends the user name identifier to the server
            out.println(username);

            //Client remembers their own identifier
            client.setUsername(username);

            //Sends to server for the room client wishes to join, or create a new room
            System.out.println("Enter the name of the room you wish to join, or enter a name to create a room");
            String roomName = console.readLine();

            //Sends the room name to the server
            out.println(roomName);

            //Client remembers their own room name
            client.setroomName(roomName);

            //Runs forever to get command console inputs and send it to server
            while(true) {
                String msg = console.readLine();
                out.println(msg);

                //Exits the infinite loop and closes the socket once user enters "/quit"
                if (msg.equals("/quit")) {
                    break;
                }
            }
            socket.close();
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
