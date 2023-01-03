package tuan8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;
import java.util.concurrent.*;

public class Server {
    public static int port = 1234;
    public static int numThread = 10;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            int i = 1;
            while (true) {
                Socket socket = server.accept();

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}