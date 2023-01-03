package tuan10;

import tuan10.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.*;
import java.util.Vector;

/**
 * @author ADMIN
 */
public class server {

    public static int port = 1234;
    public static int numThread = 10;
    public static Vector<ClientHandler> clientList = new Vector<>();
    public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThread);
    public static ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    public static CheckTime ct = new CheckTime();
    public static ServerSocket server;

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            int i = 1;
            ScheduledFuture<?> scheduledFuture = schedule.scheduleAtFixedRate(ct, 600, 1, TimeUnit.SECONDS);
                while (true) {
                    Socket socket = server.accept();
                    ClientHandler client = new ClientHandler(socket, Integer.toString(i++));
                    clientList.add(client);
                    executor.execute(client);
                    scheduledFuture.cancel(true);
                }

        } catch (IOException e) {
            System.out.println(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
        }
        finally {
            if (server != null) {
                server.close();
            }
        }
    }

}

class CheckTime implements Runnable {
    @Override
    public void run() {
        System.out.println("stop time. Time: " + Instant.now());
        System.exit(0);
    }
}
