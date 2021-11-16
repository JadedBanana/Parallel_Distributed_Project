package av;

import java.io.*;
import java.net.*;
import java.io.File;
import java.nio.file.Files;

import javax.xml.crypto.Data;

public class ServerVideo {
	
	public static void main(String[] args) throws IOException {
    
		// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        DataInputStream in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Server machine's IP			
		String routerName = "10.100.90.91"; // ServerRouter host name
		int SockNum = 5555; // port number
			
		// Tries to connect to the ServerRouter
        try {
        	Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new DataInputStream(Socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerName);
            System.exit(1);
        }
				
      	// Variables for message passing			
        String fromServer; // messages sent to ServerRouter
        String fromClient; // messages received from ServerRouter      
 		String address = "10.100.65.206"; // destination IP (Client)
			
		// Communication process (initial sends/receives)
		out.println(address);// initial send (IP of the destination Client)
		fromClient = in.readUTF();// initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromClient);
        fromClient = in.readUTF();// initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromClient);
        //File outputFile = new File("out.mp4");
        FileOutputStream output = new FileOutputStream("out.mp4", true);

        byte[] recieved = in.readAllBytes();
		output.write(recieved);
			
		// closing connections
        out.close();
        output.close();
        in.close();
        Socket.close();
	}
}
