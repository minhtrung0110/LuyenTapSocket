package BaiTapNop01;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;

    private static HashMap<String, String> dataDictionary = new HashMap<>();

    private static String removeAccent(String s) {
        // TODO Auto-generated method stub
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private static HashMap<String, String> readFileDictionary(String url) {
        HashMap<String, String> listData = new HashMap<>();
        try {
            File myObj = new File(url);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(";",2);
                System.out.println(data[1]);
                    String key = data[0];
                    String value = data[1];
               listData.put(key, value);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return listData;
    }
    private static String getURLFileText(){
        String path= Paths.get("").toAbsolutePath().toString().split(":")[1];
        String localPath= Paths.get("\\src\\main\\java\\BaiTapNop01\\dictionary.txt").toAbsolutePath().toString().split(":")[1];
        return path+localPath;

    }
    private static int findWordInFileDictionary(String key,HashMap<String, String> dataDictionary){
        for (String word : dataDictionary.keySet()) {
            if (word.equalsIgnoreCase(key))  return 1;
        }
        return 0;
    }
    public static int removeRecord(String filepath, String removeTern, int positionofTerm, String delimiter) throws IOException {
        int position = positionofTerm - 1;
        String tempFile = "temp.txt";

        File oldFile = new File(filepath);

        File newFile = new File(tempFile);

        String currentLine;

        String[] data;

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(";");

                if (!(data[position].equalsIgnoreCase(removeTern))){
                    System.out.println(currentLine);
                    pw.println(currentLine);
                }

            }
            pw.flush();

            pw.close();

            fr.close();

            br.close();

            bw.close();

            fw.close();

            oldFile.delete();
            File dump=new File(filepath);
            newFile.renameTo(dump);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return 1;
    }


    public static void main(String[] args) {
        String fileDictionarypath =  Server.getURLFileText();
        dataDictionary=readFileDictionary( fileDictionarypath);
      try {
            server = new ServerSocket(5000);
            System.out.println("Server started...");
            socket = server.accept();
            System.out.println("Client " + socket.getInetAddress() + " connected...");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String[] data = in.readLine().split(";");
                String line = data[0];

                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                switch (line.toUpperCase()) {
                    case "ADD":
                        String englishWord = null;
                        String vietnameseWord = null;
                        if (data.length == 3) {
                            englishWord = data[1];
                            vietnameseWord = data[2];

                            int checkADD = 0;
                            checkADD=Server.findWordInFileDictionary(englishWord, dataDictionary);
                            System.out.println(checkADD);
                            if(checkADD==1){
                                out.write("T??? n??y ???? t???n t???i trong t??? ??i???n. Vui l??ng th??m t??? kh??c!");
                            out.newLine();
                            out.flush();}
                            // check
                            if (checkADD==0) {
                                dataDictionary.putIfAbsent(englishWord, vietnameseWord);
                                // th??m v??o file
                                File file = new File(fileDictionarypath);
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                FileWriter fw = new FileWriter(file, true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter pw = new PrintWriter(bw);
                                pw.write(englishWord + ";" + vietnameseWord + "\n");
                                pw.flush();
                                out.write("Th??m Th??nh C??ng");
                                out.newLine();
                                out.flush();
                            }

                        } else {
                            out.write("Sai c?? ph??p! C?? ph??p ????ng: ADD;x;y - x ti???ng Anh,y ti???ng Vi???t. ");
                            out.newLine();
                            out.flush();
                            break;
                        }
                        break;
                    case "DEL":
                        String deleteWord = null;
                        if (data.length == 2) {
                            deleteWord = data[1];
                            int checkDEL =-1;
                            // check existance of deleteWord in dictionary file
                            for (String key : dataDictionary.keySet()) {
                                if (key.equals(deleteWord)) {
                                    dataDictionary.remove(key);
                                    checkDEL=1;
                                    // fucntion Delete line with pattern
                                    checkDEL=Server.removeRecord(
                                            fileDictionarypath,
                                            deleteWord,
                                            1,
                                            ";");
                                    out.write((checkDEL==1)?"Xo?? Th??nh C??ng":"X??a Th???t B???i");
                                    out.newLine();
                                    out.flush();
                                    break;
                                }
                            }
                            if (checkDEL==-1) {
                                out.write("Kh??ng t??m th???y t?? c???n xo??. V??i l??ng nh???p l???i !");
                                out.newLine();
                                out.flush();
                            }

                        } else {
                            out.write("Sai c?? ph??p! C?? ph??p ????ng: DEL;x - x l?? t??? c???n xo??. ");
                            out.newLine();
                            out.flush();
                            break;
                        }

                        break;
                    default:
                        //
                        boolean checkKeyENG = false;
                        for (String key : dataDictionary.keySet()) {
                            if (line.equalsIgnoreCase(key)) {
                                String value = dataDictionary.get(key);
                                out.write(value);
                                out.newLine();
                                out.flush();
                                checkKeyENG = true;
                                break;
                            }
                        }

                        if (!checkKeyENG) {
                            for (String key : dataDictionary.keySet()) {
                                String value = dataDictionary.get(key);
                                if (line.equalsIgnoreCase(value)) {
                                    out.write(key);
                                    out.newLine();
                                    out.flush();
                                    checkKeyENG = true;
                                    break;
                                }
                            }
                        }

                        if (!checkKeyENG) {
                            out.write("Kh??ng T??m Th???y T??? C???n Tra C???u. Vui L??ng Nh???p T??? Kh??c. ");
                            out.newLine();
                            out.flush();
                        }
                }
            }
            System.out.println("Server closed connection");
            // ????ng k???t n???i
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }


}

