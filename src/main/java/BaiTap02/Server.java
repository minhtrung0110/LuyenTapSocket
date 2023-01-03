package BaiTap02;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Server {

    public static int buffsize = 512;
    public static int port = 1234;



    public static void main(String[] args) {
        Server server = new Server();
        DatagramSocket socket;
        DatagramPacket dpreceive, dpsend;
        try {
            socket = new DatagramSocket(1234);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            while (true) {
                socket.receive(dpreceive);
                assert dpreceive != null;
                String tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
                System.out.println("Server received: " + tmp + " from " +

                        dpreceive.getAddress().getHostAddress() + " at port" +
                        socket.getLocalPort());
                if (tmp.equals("bye")) {
                    System.out.println("Server socket closed");
                    socket.close();
                    break;

                } // Uppercase, sent back to client
                // tmp= tmp.toUpperCase();
                /*-------cau1--------------------------------*/




                dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,
                        dpreceive.getAddress(), dpreceive.getPort());
                System.out.println("Server sent back " + tmp + " to client");
                socket.send(dpsend);


            }
        } catch (IOException e) {
            System.err.println(e);
        }


    }

}
