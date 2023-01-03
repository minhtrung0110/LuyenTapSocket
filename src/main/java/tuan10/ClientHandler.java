package tuan10;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

public class ClientHandler implements Runnable {

    private Socket socket;
    private String name;
    BufferedReader in;
    BufferedWriter out;

    public ClientHandler(Socket s, String n) throws IOException {
        this.name = n;
        this.socket = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public void handleSendData(Vector<ClientHandler> clientList, String inputId, String inputData) throws IOException {
        int error = 0;
        for (ClientHandler client : clientList) {
            //Nếu người dùng nhập exit
            if (inputId.equals("") && inputData.equals("")) {
                if (!name.equals(client.name)) {
                    client.out.write("User " + name + " has been disconnected \n");
                    client.out.flush();
                }
            }
            //Nếu người dùng bắt đầu với cú pháp all
            if (inputId.equals("all")) {
                if (!name.equals(client.name)) {
                    client.out.write(inputData + " \n");
                    client.out.flush();
                }
            } else {
                //Nếu người dùng bắt đầu với cú pháp có id

                if (client.name.equals(inputId)) {

                    client.out.write(inputData + "\n");
                    client.out.flush();
                    System.out.println("Server sent '" + inputData + "' from Client " + name + "--> Client " + inputId);

                } else {
                    if (name.equals(client.name)) {
                        //Nếu người dùng nhập id không tồn tại
                        if (Integer.parseInt(inputId) > clientList.size()) {
                            client.out.write("User doesn't exist \n");
                            client.out.flush();
                        }

                    }
                }
            }

        }

    }

    public void run() {

        System.out.println("Client " + socket.toString() + " accepted");
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println(threadSet);
        try {
            String inputId = null, inputData = null;
            while (true) {
                String input = in.readLine();
                //Handle if user's input is bye
                if (input.equals("bye")) {
                    handleSendData(server.clientList, "", "");
                    System.out.println("Name " + name);
                    server.clientList.remove(this);

                    System.out.println("Test cleint list");
                    System.out.println(server.clientList);
                    server.executor.remove(this);
                    break;
                }
                if (input.contains("#")) {
                    String[] listInputs = input.split("#");
                    inputId = listInputs[0];
                    inputData = listInputs[1];
                } else {
                    break;
                }
                System.out.println("Server received '" + inputData + "' from Client " + name + "' with ID of Receiver: '" + inputId);
                handleSendData(server.clientList, inputId, inputData);
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
