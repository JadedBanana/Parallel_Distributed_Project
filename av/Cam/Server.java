package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread {
    final static int TCP_SERVER_PORT = 5555;
    private Socket socket;

    public Server(Socket sock) {
        socket = sock;
    }
    // Server is always on waits for clients
    public void run() {
        System.out.println(this.socket.getPort() + " working or sleeping for 5 seconds");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInputStream clientinp;
        DataOutputStream clientout;

        try {
            // Setting inputs and outputs as variables
            clientinp = new DataInputStream(socket.getInputStream());
            clientout = new DataOutputStream(socket.getOutputStream());

            int counter = 0;

            // First Count, checking connection and verifying with Hello
            counter++;
            if (counter == 1) {
                System.out.println("Reading Client Data");
                try {
                    String sentence = clientinp.readUTF();
                    System.out.println("Client Said " + sentence);
                    clientout.writeUTF(String.format(" Received " + sentence));
                }catch (Exception e){
                    System.out.println("Error Unable to connect");
                }
                }
            counter++;
            System.out.println(counter);
            // After Connection is set, file is read converted back into whatever file it was to begin with.
            if (counter == 2) {
                // Determining size of file of bytes
                byte[] byteData = clientinp.readAllBytes();
                System.out.println("Bytes Received " + byteData);
                //Creates File in local folder, can be addressed elsewhere
                try (FileOutputStream fileOutputStream = new FileOutputStream("TestTwelve.mp4")){
                    fileOutputStream.write(byteData);
                }
                System.out.println("File Created Succefully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println("Server Active and Listing");
        ServerSocket serversocket;
        serversocket = new ServerSocket(TCP_SERVER_PORT);
        while (true) {
            Socket clientsocket = serversocket.accept();
            new Server(clientsocket).start();
        }
    }
}