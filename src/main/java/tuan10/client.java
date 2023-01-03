package tuan10;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ADMIN
 */
class Send implements Runnable {

    private Socket socket;
    private BufferedWriter out;

    public Send(Socket s, BufferedWriter o) {
        this.socket = s;
        this.out = o;

    }

    public void run() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
//                System.out.println("Input your ID want to send: ");
//                String id = stdIn.readLine();
                System.out.println("Input: ");
                String data = stdIn.readLine();
//                out.write(id + "\n");
                out.write(data + "\n");
                out.flush();
                if (data.equals("bye")) {
                    break;
                }
            }
            this.socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Receive implements Runnable {

    private Socket socket;
    private BufferedReader in;

    public Receive(Socket s, BufferedReader r) {
        this.socket = s;
        this.in = r;
    }

    public void run() {
        try {
            while (true) {
                String data = in.readLine();
                System.out.println("Received: " + data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class client {

    private static String host = "localhost";
    private static int port = 1234;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        try {
            socket = new Socket(host, port);
            System.out.println("Client connected");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Thắc mắc chỗ này
            Send send = new Send(socket, out);
            Receive recv = new Receive(socket, in);
            ExecutorService excutor = Executors.newFixedThreadPool(2);
            excutor.execute(send);
            excutor.execute(recv);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
