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

            //Runs forever to get command console inputs and send it to server
            while(true) {
                // String msg = console.readLine("[me]: ");
                String msg = console.readLine();
                // msg = msg.replace("[me]: ", "");
                out.println(msg);
                
                //Thread terminates once the user enters the quit command
                if (msg.equals("/quit")) {
                    break;
                }
            }
            //Exit from the loop means thread termination
            socket.close();
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
