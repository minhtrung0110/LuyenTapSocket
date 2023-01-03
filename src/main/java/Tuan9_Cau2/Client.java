package Tuan9_Cau2;

//Client Code



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 1235;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // tai sao lai dùng Print Writer mà ko dùng như thầy
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String name = keyboard.nextLine();
            out.println(name);

            System.out.println(in.readLine());
            // Tai sao phải tao 1 thread
            Thread inputThread = new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readLine();
                        if (message == null || message.equals("Other client has left the chat")) {
                            System.out.println(message);
                            break;
                        }
                        System.out.println(message);
                    } catch (IOException e) {
                        System.out.println("Error reading from server: " + e.getMessage());
                        break;
                    }
                }
            });
            inputThread.start();

            while (true) {
                String message = keyboard.nextLine();
                if (message.equals("bye")) {
                    break;
                }
                out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
