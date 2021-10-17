package client;

import java.io.*;
import java.net.*;

public class ClientReadThread extends Thread{
    private Socket socket;
    private BufferedReader in;
    private ChatroomClient client;
    
    public ClientReadThread(Socket socket, ChatroomClient client) {
        this.socket = socket;
        this.client = client;
    }
    
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            while (true) {
                String msg = in.readLine();
                System.out.println(msg);
                
                // if (client.getUsername() != null) {
                //     System.out.print("[me]: ");
                // }
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
