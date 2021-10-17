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

            
            while(true) {
                // String msg = console.readLine("[me]: ");
                String msg = console.readLine();
                // msg = msg.replace("[me]: ", "");
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
