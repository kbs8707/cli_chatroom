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
            
            //Polls for communication from the server and display it on the console
            while (true) {
                String msg = in.readLine();
                System.out.println(msg);
                
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
