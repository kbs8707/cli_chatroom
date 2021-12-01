package client;

import java.io.*;
import java.net.*;


public class ClientWriteThread extends Thread{
    private Socket socket;
    private PrintWriter out;
    private ChatroomClient client;
    
    public ClientWriteThread(Socket socket, ChatroomClient client) {
        this.socket = socket;
        this.client = client;
    }
    
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Console console = System.console();

            System.out.println("Enter your username");
            String username = console.readLine();

            out.println(username);

            client.setUsername(username);

<<<<<<< HEAD
            //Sends to server for the room client wishes to join, or create a new room
            System.out.println("Enter the name of the room you wish to join, or enter a name to create a room");
            String room = console.readLine();
            out.println(room);

            //Runs forever to get command console inputs and send it to server
=======
            
>>>>>>> parent of afa04c3... added comments
            while(true) {
                String msg = console.readLine();
                out.println(msg);

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
