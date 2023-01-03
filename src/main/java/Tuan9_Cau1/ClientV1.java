package Tuan9_Cau1;

import java.io.*;
import java.net.Socket;

public class ClientV1 {
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    private static BufferedReader stdIn = null;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 1234);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                // Client nhận dữ liệu từ keyboard và gửi vào stream -> server
                System.out.print("Client input: ");
                String line = stdIn.readLine();
                out.write(line);
                out.newLine();
                out.flush();
                if(line.equals("bye"))
                    break;
                // Client nhận phản hồi từ server
                line = in.readLine();
                System.out.println("Client received: " + line);
            }
            System.out.println("Client closed connection");
            in.close();
            out.close();
            stdIn.close();
            socket.close();
        } catch (IOException e) { System.err.println(e); }
    }
}
