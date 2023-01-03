package tuan5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ServerV2 {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static String translate(String n) throws FileNotFoundException{
        String url = "D:\\thuchanh\\dictionary.txt";

        File file = new File(url);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, String> dic = new HashMap<>();


        try {
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer word  = new StringTokenizer(line, " ");
                dic.put(word.nextToken(), word.nextToken());
//                System.out.println(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.ALL.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
                // file.close();
            } catch (IOException ex) {
                Logger.getLogger(BufferedReader.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
//        String newString = "hi";
        StringTokenizer searchword  = new StringTokenizer(n, " ");
        LinkedHashMap<String, String> translateword = new LinkedHashMap<>();
//            while(searchword.hasMoreTokens()) {
        String search = searchword.nextToken();
        String newString = search + ": ";
        for (Map.Entry<String, String> entry : dic.entrySet()) {
            if(entry.getKey().equals(search)) {
                translateword.put(search, entry.getValue());
                break;
            }else if(entry.getValue().equals(search)) {
                translateword.put(search, entry.getValue());
                break;
            }
        }
//            }
        for (Map.Entry<String, String> entry1 : translateword.entrySet()) {
            newString += entry1.getValue() + " ";
        }
        return newString;
    }
    public static String tracuuip(String n){
        String result = "";
        try {
            if(n.equals("Hello")){
                InetAddress localhost = InetAddress.getLocalHost();
                result+= "System IP Address : " + (localhost.getHostAddress()).trim();
                String systemipaddress = "";
                try
                {
                    URL ip = new URL("https://checkip.amazonaws.com");
                    BufferedReader br = new BufferedReader(new InputStreamReader(ip.openStream()));
                    systemipaddress = br.readLine();
                }
                catch (Exception e)
                {
                    systemipaddress = "Cannot find public ip";
                }
                result+= " and Public IP Address: " + systemipaddress;
            }else if(n.split(" ")[0].equals("req")){
                String ip = n.split(" ")[1];
                URL url = new URL("http://ip-api.com/json/" + ip);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int status = con.getResponseCode();
                BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                Object obj = JSONValue.parse(content.toString());
                JSONObject jsonObject = (JSONObject) obj;
                String thanhpho = (String) jsonObject.get("city");
                String quocgia = (String) jsonObject.get("country");
                result += "thanh pho: " + thanhpho + ", quoc gia: " + quocgia;

                in.close();
            }
        } catch (Exception e) {
            result += "giá trị nhập vào không hợp lệ";
        }
        return result;
    }
    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connected...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true) {
                // Server nhận dữ liệu từ client qua stream
                String line = in.readLine();
                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
//                StringBuilder newline = new StringBuilder();
//                newline.append(line);
//                line = newline.reverse().toString();
                String newline= "";
//Cau1
//                newline= translate(line);
                newline = tracuuip(line);
                out.write(newline);
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            // Đóng kết nối
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) { System.err.println(e); }
    }

}
