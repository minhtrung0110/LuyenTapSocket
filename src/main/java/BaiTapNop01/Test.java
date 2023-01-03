package BaiTapNop01;

import java.io.*;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) throws Exception {
        Test.removeRecord(Test.getAbsolutePathFileText(Test.getAbsolutePathFileText("\\src\\main\\java\\BaiTapNop01\\text.txt")),
                "darling",1,";");
       // String tempFile1 = Test.getAbsolutePathFileText("\\src\\main\\java\\BaiTapNop01\\temp1.txt");
       // File newFile1 = new File(tempFile1);
    }
    static String getAbsolutePathFileText(String url){
        String path= Paths.get("").toAbsolutePath().toString().split(":")[1];
        String localPath= Paths.get(url).toAbsolutePath().toString().split(":")[1];
        return path+localPath;

    }
    public static void removeRecord(String filepath, String removeTern, int positionofTerm, String delimiter) throws IOException {
        int position = positionofTerm - 1;
        String tempFile = Test.getAbsolutePathFileText(filepath);
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
    }

    }
}