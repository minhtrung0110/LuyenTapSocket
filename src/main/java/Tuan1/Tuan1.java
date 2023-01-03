package Tuan1;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Tuan1 {
    public void cau1(){
        int sum=0;
            Scanner scanner = new Scanner(System.in);
                int n=scanner.nextInt();
                for (int i=0; i<n; i++){
                    sum+=i;
                }
    }
    public  int cau1Dequi(int n){
        if(n==0) return 0;
        return n+ cau1Dequi(n-1);
    }
    public void cau2(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập N: ");
        int n=scanner.nextInt();
        int sum=0;
        for (int i=1; i<n; i++){
            sum+=(n%i==0)? i:0;
        }
        System.out.println("Tong các ước của "+n+" là :"+sum);
    }
    public static int USCLN(int a, int b) {
        if (b == 0) return a;
        return USCLN(b, a % b);
    }
    public static int BSCNN(int a, int b) {
        return (a * b) / USCLN(a, b);
    }
    public int UCLNEuclid(int a, int b) {
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }
        return a;
    }
    public  boolean isPrime(int n)
    {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }
    public void cau3(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cau 3_ Nhập N: ");
        int n=scanner.nextInt();
        int sum=0;
        for (int i=1; i<n; i++){
            sum+=isPrime(i)? i:0;
        }
        System.out.println("Tong các số nguyên tố của "+n+" là :"+sum);
    }
    private static void   cau4(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập N để phân tích: ");
        int n=scanner.nextInt();
        String result="";
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();

        int key = 2;
        int value = 0;
        while(n > 1) {
            while(n % key == 0) {
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
        System.out.println("Phân tích số  "+n+" thành :"+result);
    }
    public static void main(String[] args){
        Tuan1 t=new Tuan1();
        System.out.println(t.cau1Dequi(1));
        t.cau2();
        t.cau3();
        t.cau4();
    }
}
