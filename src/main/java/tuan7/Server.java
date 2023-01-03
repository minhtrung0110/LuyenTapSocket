package tuan7;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Server {

    public static int buffsize = 512;
    public static int port = 1234;

    private String getMD5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String reverseWord(String s) {
        StringBuilder newline = new StringBuilder();
        newline.append(s);
        String line = newline.reverse().toString();
        return line;
    }

    private String convertBinary(String input) {
        String result = "";
        String[] data = input.split(":");
        String x = data[0];
        String y = data[1];

        switch (x ) {
            case "2":
                int number =Integer.parseInt(data[2]);
                if(y=="10") {
                    int decimal = 0;
                    int p = 0;
                    while (true) {
                        if (number == 0) {
                            break;
                        } else {
                            int temp = number % 10;
                            decimal += temp * Math.pow(2, p);
                            number = number / 10;
                            p++;
                        }
                    }
                    result=String.valueOf(decimal);
                }
                else if(y=="8")
                result=Integer.toOctalString(number);
                else   result=Integer.toHexString(number);
                break;
            case "10":
                int num =Integer.parseInt(data[2]);
                int b=Integer.parseInt(y);
                if (num < 0 || b < 2 || b > 16 ) {
                    return "";
                }

                StringBuilder sb = new StringBuilder();
                int m;
                int remainder = num;

                while (remainder > 0) {
                    if (b > 10) {
                        m = remainder % b;
                        if (m >= 10) {
                            sb.append((char) (55 + m));
                        } else {
                            sb.append(m);
                        }
                    } else {
                        sb.append(remainder % b);
                    }
                    remainder = remainder / b;
                }
                result= sb.reverse().toString();

                break;
            case "16":
                int number2 =Integer.parseInt(data[2]);
                if(y=="2") result=Integer.toBinaryString(number2);
                else if(y=="8") result=Integer.toOctalString(number2);
                //else result=Integer.to

            default:
                break;
        }

        return result;
    }
    public static String convertNumber(int n, int b) {
        // từ cơ số 10 sang cơ số B
        if (n < 0 || b < 2 || b > 16 ) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int m;
        int remainder = n;

        while (remainder > 0) {
            if (b > 10) {
                m = remainder % b;
                if (m >= 10) {
                    sb.append((char) (55 + m));
                } else {
                    sb.append(m);
                }
            } else {
                sb.append(remainder % b);
            }
            remainder = remainder / b;
        }
        return sb.reverse().toString();
    }
    private String findHashKey(String input) {
        String result = "";
        String[] data = input.split(":");
        String action = data[0];
        String s = data[1];
        switch (action) {
            case "ENC":
                String APIENC = "https://hashtoolkit.com/generate-hash/?text=" + s + "";
                result = this.getMD5(s);

                break;
            case "DEC":
                String APIDEC = "https://hashtoolkit.com/decrypt-hash/?hash=" + s + "";
                try {
                    Document doc = Jsoup.connect(APIDEC)
                            .method(Connection.Method.GET)
                            .ignoreContentType(true)
                            .execute()
                            .parse();
                    //Elements e=doc.getElementsByAttributeValue("class","res-text");
                    String e = doc.select(".res-header code").last().text();
                    return e;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


        return result;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /* This function takes last element as pivot, places
       the pivot element at its correct position in sorted
       array, and places all smaller (smaller than pivot)
       to left of pivot and all greater elements to right
       of pivot */
    static int partition(int[] arr, int low, int high) {

        // pivot
        int pivot = arr[high];

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr[j] < pivot) {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
              arr[] --> Array to be sorted,
              low --> Starting index,
              high --> Ending index
     */
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static String Cau2(String input) throws IOException {
        //doc file
        int counter = 0;
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan7/data.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            int num = Integer.parseInt(line);
            int sum = 0;
            while (num > 0) {
                int balance = num % 10;
                sum += balance;
                num /= 10;
            }
            if (sum == Integer.parseInt(input)) {
                counter++;
            }

        }
        reader.close();
        return "Có tất cả " + counter + " có tổng các chữ số = " + input;

    }

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

                //tmp=server.reverseWord(tmp);
                /*-------cau2--------------------------------*/
                //   tmp= CaDu2(tmp);


                /*-------cau3--------------------------------*/
                tmp = server.convertBinary(tmp);


                /*-------cau4--------------------------------*/


                /*-------cau5--------------------------------*/
                // tmp=server.findHashKey(tmp);

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
/*    List<Integer> data = new ArrayList<>();

        for(int i=0; i<100000; i++) {
            Random rd=new Random();
            data.add(rd.nextInt(452));

        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/java/tuan7/data.txt"));
        for (int i : data) {
            writer.write(String.valueOf(i));
            writer.newLine();
        }
        writer.close();

 */
/*private  String convertBinary(String input){
       String result="";
        String[] data= input.split(":");
        String x=data[0];
        String y=data[1];
        String number=data[2];
        String API="https://networkcalc.com/api/binary/"+number+"?from="+x+"&to="+y+"";
        try {
            Document doc = Jsoup.connect(API)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .parse();
            JSONObject json = new JSONObject(doc.text());
            result = "Convert "+number+" from "+x+" to "+y+" = "  + json.get("converted").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

 */
