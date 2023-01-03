package tuan9;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    static class Message implements Runnable{
        public Socket socket=null;

        public Message() {
        }
        public Message(Socket socket) throws IOException {
            this.socket=socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        @Override
        public void run() {
            while(true) {
                try {
                    String line = in.readLine();
                    System.out.println("Server received: " + line);
                    line=line.toUpperCase();
                    out.write(line);
                    if (line.equals("bye")) {

                        out.newLine();
                        out.flush();
                        System.out.println("Server closed connection");
                        break;
                    }

                }
                catch (Exception e) {
                e.printStackTrace();}
            }
        }
    }
     static class CheckTime implements Runnable{

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);
        CheckTime ct = new CheckTime();
        try {
            int port=1234;
            server = new ServerSocket(port);
            System.out.println("Server started...");

//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ScheduledFuture<?>scheduledFuture=scheduledExecutorService.scheduleAtFixedRate(ct,600,1,TimeUnit.SECONDS);
            while(true) {
              try{
                  socket = server.accept();
                  executor.execute(new Message(socket));

               //   scheduledExecutorService.shutdown();
              }
              catch (Exception e){
            socket.close();
            server.close();
              }

             //   line = newline.reverse().toString();
//                out.write(line);
//                out.newLine();
//                out.flush();
            }

            // Đóng kết nối
//            in.close();
//            out.close();
//            socket.close();
//            server.close();
        } catch (IOException e) { System.err.println(e); }
    }

}
