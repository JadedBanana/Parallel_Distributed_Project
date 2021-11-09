package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public Main() {
    }

    public static void main(String args[]) throws UnknownHostException, IOException {
        //Host is servers ip
        Socket socket = new Socket("localhost", 5555);
        InputStream is = null;
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        /*
        Okay So I dropped the format all together instead of reading line by line im just using a counter to synchronize the
        initial connection with the server, then im using the counter to let the client and server be on the same page.
        The First count, count 1 creates and makes sure the connection works, The next connection then send the file by itself.
        The file is then saved as whatever was written on the client side.
         */

        int counter = 0;

        // First Count, checking connection and verifying with Hello
        counter++;
        if (counter == 1) {
            System.out.println("client: hello");
            //Sending Hello to server
            output.writeUTF("hello");
            System.out.println("client: waiting For Response");
            //Response from server
            String response = input.readUTF();
            System.out.println("Server:" + response);
        }
        counter++;
        // After Connection is set, file is read converted in to bytes and sent to server
        if (counter == 2) {
            System.out.println("client: Preparing file to send");
            //File to send
            byte[] bytes = Files.readAllBytes(Paths.get("C:/Users/camer/Documents/GitHub/Parallel_Distributed_Project/src/test_video.mp4"));
            //for validation purposes serves no function
            is = new FileInputStream("C:/Users/camer/Documents/GitHub/Parallel_Distributed_Project/src/test_video.mp4");
            // count the available bytes form the input stream
            int count = is.available();
            System.out.println("client: Number of Bytes " + count);
            System.out.println("client: Bytes " + bytes);
            System.out.println("client: Sending bytes now");
            output.write(bytes);
            System.out.println("client: Sent bytes");
        }
        socket.close();

    }
}