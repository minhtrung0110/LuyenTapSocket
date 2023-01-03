package tuan5;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
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

    private String getAbsolutePathFileText(){
        String path= Paths.get("").toAbsolutePath().toString().split(":")[1];
        String localPath= Paths.get("\\src\\main\\java\\tuan5\\data.txt").toAbsolutePath().toString().split(":")[1];
        return path+localPath;

    }
    private String translate(String input) throws FileNotFoundException {
       // final String url= "D:\\OneDrive\\Learning Data Important Private\\Learning\\LastYear\\HKI\\LapTrinhMang\\BaiTap\\LuyenTapSocket\\src\\main\\java\\tuan5\\data.txt";

        File file = new File(this.getAbsolutePathFileText());
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
        StringTokenizer searchword  = new StringTokenizer(input, " ");
        LinkedHashMap<String, String> translateword = new LinkedHashMap<>();
//            while(searchword.hasMoreTokens()) {
        String search = searchword.nextToken();
        String newString = search + ":";
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
    private  String findIPAddress(String ipAddress  ) throws UnknownHostException {
        String location="";
        String API="http://ip-api.com/json/";
        if(ipAddress.equals("Hello")){
            InetAddress localhost = InetAddress.getLocalHost();
            location+= "System IP Address : " + (localhost.getHostAddress()).trim();
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
            location+= " and Public IP Address: " + systemipaddress;
        }else
            if(ipAddress.split(" ")[0].equals("req")) {
                String ip = ipAddress.split(" ")[1];
                if (isValidIPAddress(ip)) {
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
                        e.printStackTrace();
                    }
                } else location = "Ip Address is invalid !";

            }
            else location = "Please type \"Hello\" and type syntax: req X - X is Ip Address Public (Do not type Ip Address System). ";

        return location;
    }

    private String findInfoProfile(String id){
        String URL="https://masothue.com/Search/?q="+id+"&type=auto&force-search=1";
        StringBuilder info = new StringBuilder();
        try {
           Connection.Response con =Jsoup.connect(URL)
                   .method(Connection.Method.GET)
                   .execute();
           String newurl = con.url().toString();

           Document doc =Jsoup.connect(newurl).method(Connection.Method.GET).execute().parse();

            Elements elementList = doc.getElementsByAttributeValue("class","copy");
            if(elementList==null) return "Don't find information about this CCCD ";
            for(Element element : elementList){
                System.out.println(element.text());
                info.append(" - "+element.text());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return info.toString();
    }

    private String findPiNumber(String line){
        String result = "";
        long startTime = Instant.now().toEpochMilli();
        if (isStringInt(line)) {
            int INTERVAL = Integer.parseInt(line);

            if (INTERVAL >= 1000000) {
                double randX, randY, originDist, pi = 0;
                int circlePoints = 0, squarePoints = 0;

                for (int i = 0; i < (INTERVAL); i++) {
                    randX = Math.random() * 2 - 1;
                    randY = Math.random() * 2 - 1;

                    originDist = Math.pow(randX, 2) + Math.pow(randY, 2);

                    if (originDist <= 1) circlePoints++;

                    squarePoints++;

                    pi = ((4.0 * circlePoints) / squarePoints);
                }
               result="Final Estimation of PI = " + pi;

            } else {
                result="Input < 1000000";

            }
        } else {
            result="Input is not a number";
        }
        long endTime = Instant.now().toEpochMilli();
        long totalTime = endTime - startTime;
        return result=result+ "  -  Execution time in : "+totalTime +" milliseconds";
    }
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
               newline = sr.translate(line);
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
