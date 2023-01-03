package tuan6;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader input = null;
    private static BufferedWriter output = null;
    public static boolean isValidIPAddress(String ip)
    {

        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);

        if (ip == null) {
            return false;
        }
        Matcher m = p.matcher(ip);
        return m.matches();
    }
    public static boolean isStringInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nu) {
            return false;
        }
    }

   /*private String getInfoReview(String url) {
        String idSP=
       StringBuilder info = new StringBuilder();
       try {
           Document doc = Jsoup.connect(API + ip)
                   .method(Connection.Method.GET)
                   .ignoreContentType(true)
                   .execute()
                   .parse();
           JSONObject json = new JSONObject(doc.text());
           location = "Location: " + (String) json.get("country") + " - " + json.get("city").toString();
           System.out.println(json.get("status"));
           System.out.println(json.get("country"));

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       return info.toString();

   }*/
    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connected...");
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(true) {
                // Server nhận dữ liệu từ client qua stream
                String line = input.readLine();
                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                String newline = "";
                Server sr=new Server();
                /*-------cau 1--------------------------------------------------------*/
              //  newline = sr.getInfoReview(line);
                /*-------cau 2--------------------------------------------------------*/
               // newline=sr.findIPAddress(line);

                /*-------cau 3--------------------------------------------------------*/
              // newline=sr.findPiNumber(line);

                /*-------cau 4--------------------------------------------------------*/
                //    newline=sr.findInfoProfile(line);


                /*-------cau 5--------------------------------------------------------*/


                //----------------------------------------------------------------//




                //   line = newline.reverse().toString();
                output.write(newline);
                output.newLine();
                output.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            input.close();
            output.close();
            socket.close();
            server.close();
        } catch (IOException e) { System.err.println(e); }
    }

}
