package Tuan9_Cau1;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class ClientHandler implements Runnable {

    private Socket socket;
    private String clientID;
    BufferedReader in;
    BufferedWriter out;

    public ClientHandler(Socket s, String n) throws IOException {
        this.clientID = n;
        this.socket = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public void handleSendData(Vector<ClientHandler> clientList, String inputData,String clientID) throws IOException {
        int error = 0;
        for (ClientHandler client : clientList) {
            if(Objects.equals(clientID, client.clientID)){
                if(Objects.equals(inputData, "bye")){

                    client.out.write("User " + clientID + " has been disconnected \n");
                    client.out.flush();
                    server.clientList.remove(this);
                    server.executor.remove(this);
                }
                else {
                    System.out.println("vao day: "+inputData.toLowerCase());
                    client.out.write(inputData.toUpperCase() +"\n");
                    client.out.flush();
                }
            }

        }

    }

    public void run() {

        System.out.println("Client " + socket.toString() + " accepted");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println(threadSet);
        try {
            while (true) {
                String inputData = in.readLine();
                //Handle if user's input is bye
                if (inputData.equals("bye")) {
                    handleSendData(server.clientList,  inputData,clientID);
                    System.out.println("Name " + clientID);
                    server.clientList.remove(this);
                    System.out.println("Test cleint list");
                    System.out.println(server.clientList);
                    server.executor.remove(this);
                    break;
                }
                else {
                    handleSendData(server.clientList, inputData,clientID);
                }
              //  System.out.println("Server received '" + inputData + "' from Client " + name  );

            }
            System.out.println("Closed socket for client " + socket.toString());
            in.close();
            out.close();
            socket.close();

            System.out.println(server.executor.remove(this));
            System.out.println(threadSet);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
