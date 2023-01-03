package tuan8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cau2 {

    // Hàm sắp xếp một mảng số nguyên dương
    private static int[] sort(int[] arr) {
        Arrays.sort(arr);
        return arr;
    }

    // Lớp chứa hàm sắp xếp một phần của mảng số
    public static class SortTask implements Runnable {
        private int[] arr;
        private int start;
        private int end;

        public SortTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {

            int[] sorted = sort(Arrays.copyOfRange(arr, start, end));
            System.arraycopy(sorted, 0, arr, start, end - start);
        }
    }
    public static void main(String[] args) throws IOException, IOException, InterruptedException {
        List<Integer> data = new ArrayList<>();
        for(int i=0; i<1000700; i++) {
            Random rd=new Random();
            data.add(rd.nextInt(752));

        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/java/tuan8/data.txt"));
        for (int i : data) {
            writer.write(String.valueOf(i));
            writer.newLine();
        }
//        // Đọc dữ liệu từ file data.txt
//        long b = System.currentTimeMillis();
//        BufferedReader reader = new BufferedReader(new FileReader("./src/main/java/tuan8/data.txt"));
//        List<Integer> data = new ArrayList<>();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            data.add(Integer.parseInt(line));
//        }
//        reader.close();
//
//        // Chuyển dữ liệu thành mảng số nguyên và sắp xếp
//        int[] arr = sort(data.stream().mapToInt(i -> i).toArray());
//
//
//        // tao 4 thread sắp xếp
//        int chunkSize =arr.length/3;
//        SortTask sortTask1= new SortTask(arr,0, chunkSize);
//        SortTask sortTask2= new SortTask(arr,chunkSize+1, chunkSize*2);
//        SortTask sortTask3= new SortTask(arr,chunkSize*2+1,arr.length);
//
//        Thread t1= new Thread(sortTask1 );
//        Thread t2= new Thread(sortTask2 );
//        Thread t3= new Thread(sortTask3 );
//        t1.start();
//        t2.start();
//        t3.start();
//
//        t1.join();
//        t2.join();
//        t3.join();
//
//        // Ghi kết quả vào file out.txt
//        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/java/tuan8/out.txt"));
//        for (int i : arr) {
//            writer.write(String.valueOf(i));
//            writer.newLine();
//        }
//        writer.close();
//        b = System.currentTimeMillis() - b;
//        System.out.println("Totaltime: "+b+ " miliseconds");
    }
}

