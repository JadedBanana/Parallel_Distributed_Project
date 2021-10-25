package src;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientVideo {
	
	public static void main(String[] args) throws IOException {
     	
		// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Client machine's IP
      	String routerName = "192.168.0.123"; // ServerRouter host name
		int SockNum = 5555; // port number
			
		// Tries to connect to the ServerRouter
        try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        } 
        catch (UnknownHostException e) {
        	System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerName);
            System.exit(1);
        }
				
      	// Variables for message passing	
		InputStream input = Client.class.getResourceAsStream("/audio8.wav");
		Scanner reader = new Scanner(input);
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
		String address ="192.168.0.149"; // destination IP (Server)
		long t0, t1, t;
			
		// Communication process (initial sends/receives
		out.println(address);// initial send (IP of the destination Server)
		fromServer = in.readLine();//initial receive from router (verification of connection)
		System.out.println("ServerRouter: " + fromServer);
		out.println(host); // Client sends the IP of its machine as initial send
		t0 = System.currentTimeMillis();
      	
		// Communication while loop
		while ((fromServer = in.readLine()) != null) {
			System.out.println("Server: " + fromServer);
			t1 = System.currentTimeMillis();
            if (fromServer.equals("Bye.")) // exit statement
            	break;
			t = t1 - t0;
			System.out.println("Cycle time: " + t);
          
            fromUser = reader.nextLine(); // reading strings from a file
            if (fromUser != null) {
            	System.out.println("Client: " + fromUser);
                out.println(fromUser); // sending the strings to the Server via ServerRouter
            }
        }
      	
		// closing connections
		reader.close();
        out.close();
        in.close();
        Socket.close();
	}
}
