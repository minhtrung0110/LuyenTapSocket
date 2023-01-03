package Tuan2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Tuan2 {

    public boolean findWordInList( LinkedHashMap<Integer, String> list,String input){
        Set<Integer> keySet = list.keySet();
        for (Integer key : keySet) {
                if(list.get(key).equalsIgnoreCase(input)) return true;
        }
        return false;
    }
    public void cau2(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập chuỗi: ");
        String input = scanner.nextLine();
        StringTokenizer st = new StringTokenizer(input," ",true);
        LinkedHashMap<Integer, String> list = new LinkedHashMap<Integer, String>();
        int visit=0;
        while (st.hasMoreTokens()) {
            String word=st.nextToken();
            if(!findWordInList(list,word) && !Objects.equals(word, " ")) {
                list.put(visit, word);
                visit++;
            }
        }
        StringBuilder result= new StringBuilder();
        Set<Integer> keySet = list.keySet();
        for (Integer key : keySet) {
            result.append(list.get(key)).append(" ");
        }
        System.out.println(result);

    }
    public void cau3() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập từ cần tra: ");
        String input = scanner.nextLine();
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan2/dictionary.txt"));
        HashMap<String, String> dataDictionary = new HashMap<>();
        String line;
        // doc file luu mảng
        while ((line = reader.readLine()) != null) {
            String[] temp=line.split(";");
                dataDictionary.put(temp[0], temp[1]);
        }
        boolean isEN=false;
        boolean isResult=false;
        for (String key : dataDictionary.keySet()) {
            if(key.equalsIgnoreCase(input)){
                System.out.println("=> "+dataDictionary.get(key));
                isEN=true;
                isResult=true;
            }
        }
        if(!isEN){
            for (String key : dataDictionary.keySet()) {

                if(dataDictionary.get(key).equalsIgnoreCase(input)){
                    System.out.println("=> "+key);
                    isEN=true;
                    isResult=true;
                }
            }
        }
        if(!isResult) System.out.println("Không tìm thấy kết quả.");
    }
    public static void main(String[] args) throws IOException {
        Tuan2 t = new Tuan2();
       // t.cau2();
        t.cau3();

    }
}
