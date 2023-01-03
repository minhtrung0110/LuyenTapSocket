package tuan04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Server {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static BufferedReader in = null;
    private static BufferedWriter out = null;
    public static boolean isNumberic(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public  static float caculator(String input){

        StringTokenizer st = new StringTokenizer(input, "+-/*",true);
        float Result=0;
        int flag=0;
        float temp = 0;
        // 9-5-6*2
        while (st.hasMoreTokens()) {
            String x=st.nextToken();
            if(Server.isNumberic(x)){
                switch(flag){
                    case 0:
                        Result+=Float.parseFloat(x);
                        temp=Float.parseFloat(x);
                        break;
                    case 1:
                        Result-=Float.parseFloat(x);
                        temp=-Float.parseFloat(x);
                        break;
                    case 2:
                        Result=(Result-temp)+(temp*Float.parseFloat(x)); //*=Integer.parseInt(x);
                        break;
                    case 3:

                        //if(Integer.parseInt(x)<0) Result=(Result-temp)-(temp/Float.parseFloat(x));
                       // else
                            Result=(Result-temp)+(temp/Float.parseFloat(x));
                        break;

                }
            }
            else
                switch((String)x) {
                    case "+":
                        flag=0;
                        break;
                    case "-":
                        flag=1;
                        break;
                    case "*":
                        flag=2;
                        break;
                    case "/":
                        flag=3;
                        break;
                    default:
                }
        }
        return Result;
    }


    public static List<String> infixToPostfixConvert(String input) {

        int priority = 0;
        String postfixBuffer = "";
        Stack<Character> stack = new Stack<Character>();
        List<String> postfixArray = new ArrayList<String>();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {

                if (postfixBuffer.length() > 0) {
                    postfixArray.add(postfixBuffer);
                }
                postfixBuffer = "";
                // check the precedence
                if (stack.size() <= 0)
                    stack.push(ch);
                else {
                    Character chTop = (Character) stack.peek();
                    if (chTop == '*' || chTop == '/')
                        priority = 1;
                    else
                        priority = 0;
                    if (priority == 1) {
                        if (ch == '+' || ch == '-') {
                            postfixArray.add(String.valueOf(stack.pop()));
                            i--;
                        } else { // Same
                            postfixArray.add(String.valueOf(stack.pop()));
                            i--;
                        }
                    } else {
                        if (ch == '+' || ch == '-') {
                            postfixArray.add(String.valueOf(stack.pop()));
                            stack.push(ch);
                        } else
                            stack.push(ch);
                    }
                }
            } else {
                postfixBuffer += ch;
            }
        }
        postfixArray.add(postfixBuffer);
        int len = stack.size();
        for (int j = 0; j < len; j++)
            postfixArray.add(stack.pop().toString());

        return postfixArray;
    }
    public  static int evaluatePostfix(String exp)
    {
        //create a stack
        Stack<Integer> stack = new Stack<>();

        // Scan all characters one by one
        for(int i = 0; i < exp.length(); i++)
        {
            char c = exp.charAt(i);

            if(c == ' ')
                continue;

                // If the scanned character is an operand
                // (number here),extract the number
                // Push it to the stack.
            else if(Character.isDigit(c))
            {
                int n = 0;

                //extract the characters and store it in num
                while(Character.isDigit(c))
                {
                    n = n*10 + (int)(c-'0');
                    i++;
                    c = exp.charAt(i);
                }
                i--;

                //push the number in stack
                stack.push(n);
            }

            // If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return stack.pop();
    }
    private static boolean isPerfect(int num) {
        int sum=1;
        for (int i=2; i<=num/2; i++){
            if(num%i==0) sum+=i;
                    }
            return sum==num;
    }
    private static int findPerfect(String input){
        try{
            int num=Integer.parseInt(input);
            if(isPerfect(num)) return num;
            for(int i=num+1; ; i++){
                if(isPerfect(i)) return i;
            }
        }
        catch (Exception e){
            return 0;
        }
    }
    private static String  achievementsOfPrimeNumbers(int n){
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
                System.out.println(Server.isNumberic(line));
                /*-------cau 2--------------------------------------------------------
                if(Server.isNumberic(line)){
                    if(Server.isPerfect(Integer.parseInt(line))){
                        line=line+" is Perfect number !";
                    }
                    else {
                       line="The next Perfect number is "+ Server.findPerfect(line) ;
                    }
                }*/
                /*-------cau 3--------------------------------------------------------
                if(Server.isNumberic(line)){
                        line="Result: "+Server.achievementsOfPrimeNumbers(Integer.parseInt(line));
                }*/
                /*-------cau 4--------------------------------------------------------*/
                Random random = new Random();
                int number = random.nextInt(100);
                int solandoan = 1;
                Instant start = Instant.now();
                try {

                    if(Integer.parseInt(line) == number){
                        Instant end = Instant.now();
                        Duration timeElapsed = Duration.between(start, end);
                        line = "so lan doan " + solandoan + ", thoi gian doan " + timeElapsed.toMillis() + " ms";

                    }else if(Integer.parseInt(line) > number){
                        line = "so can tim be hon " + line + number;
                        solandoan +=1;
                    }else{
                        line = "so can tim lon hon " + line + number;
                        solandoan+=1;
                    }
                } catch (Exception e) {
                    line = "gia tri nhap vao khong hop le";
                }
//                /*-------cau 5--------------------------------------------------------*/
//                if(!Server.isNumberic(line)){
//                    List<String> exp=Server.infixToPostfixConvert(line);
//                    String input="";
//                    for(String s:exp){
//                        input+=" "+s;
//                    }
//                    line=Server.evaluatePostfix(input)+"";
//                }

                //----------------------------------------------------------------//

                if (line.equals("bye"))
                    break;
                System.out.println("Server received: " + line);
                // Server gửi phản hồi ngược lại cho client (chuỗi đảo ngược)
                StringBuilder newline = new StringBuilder();
                newline.append(line);
             //   line = newline.reverse().toString();
                out.write(line);
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
