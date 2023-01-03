package Tuan9_Cau2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1235;
    private static final int MAX_CLIENTS = 2;
    private static final int TIMEOUT = 600000;

    private static ClientHandler[] clients = new ClientHandler[MAX_CLIENTS];
    private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            int clientCount = 0;
            while (true) {
                Socket socket = serverSocket.accept();
                if (clientCount < MAX_CLIENTS) {
                    ClientHandler client = new ClientHandler(socket, clientCount);
                    clients[clientCount] = client;
                    threadPool.execute(client);
                    clientCount++;
                } else {
                    System.out.println("Cannot accept more clients");
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private int clientId;
        private String clientName;
        private BufferedReader in;
        private PrintWriter out;
        public ClientHandler(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error creating input/output streams: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                clientName = in.readLine();
                System.out.println("Client " + clientName + " connected");
                out.println("Welcome, you are client " + clientId + ".");

                if (clientId == 0) {
                    out.println("Waiting for other client to connect...");
                    socket.setSoTimeout(TIMEOUT);
                    String message = in.readLine();
                    if (message != null) {
                        clients[1].out.println(clientName + ": " + message);
                        clients[1].out.println("Type bye to end the chat");
                    } else {
                        clients[1].out.println("Other client did not connect within the timeout");
                        return;
                    }
                } else {
                    out.println("Type bye to end the chat");
                }

                while (true) {
                    String message = in.readLine();
                    if (message == null) {
                        break;
                    } else if (message.equals("bye")) {
                        // Neu thang gui ma la id = 0 thi se gui message cho
                        // thang co id = 1
                        clients[clientId == 0 ? 1 : 0].out.println("Other client has left the chat");
                        break;
                    }
                    // Neu thang gui ma la id = 0 thi se gui message cho
                    // thang co id = 1
                    clients[clientId == 0 ? 1 : 0].out.println(clientName + ": " + message);
                }
            } catch (IOException e) {
                System.out.println("Error handling client " + clientId + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client " + clientId + " socket: " + e.getMessage());
                }
            }
        }
    }
}