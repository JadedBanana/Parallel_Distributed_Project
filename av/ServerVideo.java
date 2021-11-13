package av;

import java.io.*;
import java.net.*;
import java.io.File;
import java.nio.file.Files;

public class ServerVideo {
	
	public static void main(String[] args) throws IOException {
    
		// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Server machine's IP			
		String routerName = "127.0.0.1"; // ServerRouter host name
		int SockNum = 5555; // port number
			
		// Tries to connect to the ServerRouter
        try {
        	Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
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
 		String address = "172.22.8.224"; // destination IP (Client)
			
		// Communication process (initial sends/receives)
		out.println(address);// initial send (IP of the destination Client)
		fromClient = in.readLine();// initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromClient);
        byte[] bytes = new byte[1000];
        File outputFile = new File("out.mp4");
			         
		// Communication while loop
      	while ((fromClient = in.readLine()) != null) {
            System.out.println("Client said: " + fromClient);
            //fromClient = in.readLine();
            bytes = fromClient.getBytes();
            Files.write(outputFile.toPath(), bytes);
        }
			
		// closing connections
        out.close();
        in.close();
        Socket.close();
	}
}
