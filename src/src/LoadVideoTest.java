package src;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LoadVideoTest {
	
	public static void main(String[] args) throws IOException {
		
      	// Variables for message passing	
		InputStream input = Client.class.getResourceAsStream("/test_video.mp4");
		Scanner reader = new Scanner(input);
		
		while(reader.hasNextLine())
			System.out.println(reader.nextLine().getBytes());
	}
}
