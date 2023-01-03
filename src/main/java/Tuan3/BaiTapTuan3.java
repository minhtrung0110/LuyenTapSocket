package Tuan3;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaiTapTuan3 {
    public static final String URL="D:\\OneDrive\\Learning Data Important Private\\Learning\\LastYear\\HKI\\LapTrinhMang\\BaiTap\\";
    public static  void baitap1() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan3/domain.txt"));

        try {
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                try {
                    InetAddress add = InetAddress.getByName(line);
                    System.out.println("Domain name " + line + " has IP " + add.getHostAddress());
                }catch (UnknownHostException e) {
                    System.out.println("Domain name " + line + " is not valid");
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
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
    }



    public static  void baitap2() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan3/domain.txt"));
        try {
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                try {
                    InetAddress add = InetAddress.getByName(line);
                    System.out.println("Domain name " + line + " has IP " + add.getHostAddress());
                }catch (Exception e) {
                    System.out.println("Domain name " + line + " is not valid");
                }
                line = reader.readLine();
            }
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

    }
    public static void baitap3() throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan3/domain.txt"));
        try {
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                try {
                    InetAddress add = InetAddress.getByName(line);
                    boolean reachable = add.isReachable(10000);
                    if(reachable) {
                        System.out.println("IP " + line + " is reachable ");
                    }else {
                        System.out.println("IP " + line + " is not reachable ");
                    }

                }catch (Exception e) {
                    System.out.println("IP " + line + " is not valid");
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BufferedReader.class.getName())
                    .log(Level.SEVERE, null, ex);
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


}
    public static void main(String[] arg) throws Exception {
        //BaiTapTuan3.baitap1();
       // BaiTapTuan3.baitap2();
         BaiTapTuan3.baitap3();

    }

}
