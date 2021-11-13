package av;

import java.io.*;
import java.net.*;
import java.io.File;
import java.nio.file.Files;

public class ClientVideo {
	
	public static void main(String[] args) throws IOException {
     	
		// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Client machine's IP
      	String routerName = "172.22.5.83"; // ServerRouter host name
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
		File file = new File(".\\test_video.mp4"); // Declare Video File
		byte[] videoBytes = Files.readAllBytes(file.toPath()); // Store Video as byte[]
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
		String address ="127.0.0.1"; // destination IP (Server)
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
			t = t1 - t0;
			System.out.println("Cycle time: " + t);
          
            /* fromUser = reader.nextLine(); // reading strings from a file
            if (fromUser != null) {
            	System.out.println("Client: " + fromUser);
                out.println(fromUser); // sending the strings to the Server via ServerRouter
            } */

			int count = 0;
			byte[] buff = new byte[1000];
			System.out.println("made it first");
			while (count < videoBytes.length) {
				System.out.println("made it second");
				buff[(count % buff.length)] = videoBytes[count];
				if (count % buff.length == 999) {
					fromUser = new String(buff);
					System.out.println(fromUser);
					out.println(fromUser);
				}
				count++;
			}
        }
      	
		// closing connections
        out.close();
        in.close();
        Socket.close();
	}
}
