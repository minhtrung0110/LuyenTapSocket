package org.example;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class ServerLeNhan {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    public static String phantichthanhsonguyento(int n){
        String result = "";
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();

        int key = 2;
        int value = 0;
        while(n > 1) {
            while(n%key == 0) {
                value +=1;
                n=n/key;
            }
//			System.out.println(n);
            linkedHashMap.put(key, value);
            key +=1;
            value = 0;
        }
        for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            if(entry.getValue() != 0) {
                if(result == "") {
                    result += entry.getKey().toString() + "^" + entry.getValue();
                }else {
                    result += "x" + entry.getKey().toString() + "^" + entry.getValue();
                }
            }
        }
        return result;
    }
    public static float tinhtoan(String n){
        StringTokenizer st = new StringTokenizer(n, "+-*/",true);
        float result = 0;
        float a = Float.parseFloat(st.nextToken());
        String operator = st.nextToken();
        float b = Float.parseFloat(st.nextToken());
        switch (operator) {
            case "+":
                result = a + b ;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                break;
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
            Random random = new Random();
            int number = random.nextInt(100);
            int solandoan = 1;
            Instant start = Instant.now();
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
                String newline="";
//Cau 1
//                try {
//                    newline= phantichthanhsonguyento(Integer.parseInt(line));
//                } catch (Exception e) {
//                    newline = "gia tri nhap vao khong hop le";
//                }

//Cau2

                try {
                    if(Integer.parseInt(line) == number){
                        Instant end = Instant.now();
                        Duration timeElapsed = Duration.between(start, end);
                        newline = "so lan doan " + solandoan + ", thoi gian doan " + timeElapsed.toMillis() + " ms";

                    }else if(Integer.parseInt(line) > number){
                        newline = "so can tim be hon " + line + number;
                        solandoan +=1;
                    }else{
                        newline = "so can tim lon hon " + line + number;
                        solandoan+=1;
                    }
                } catch (Exception e) {
                    newline = "gia tri nhap vao khong hop le";
                }
//Cau 3
//                try {
//                    newline= String.valueOf(tinhtoan(line));
//                } catch (Exception e) {
//                    newline = "bieu thuc nhap vao khong hop le";
//                }
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
