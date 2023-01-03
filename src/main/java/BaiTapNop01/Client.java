package BaiTapNop01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Client {
	private static Socket socket = null;
	private static BufferedReader in = null;
	private static BufferedWriter out = null;
	private static BufferedReader stdIn = null;

	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 5000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				// Client nhận dữ liệu từ keyboard và gửi vào stream -> server
				System.out.print("Nhập từ cần tra : ");
				String line = stdIn.readLine();
//				line = removeAccent(line);
				out.write(line);
				out.newLine();
				out.flush();
				if (line.equals("bye"))
					break;
				// Client nhận phản hồi từ server
				line = in.readLine();
				System.out.println("Client received: " + line);
			}
			System.out.println("Client closed connection");
			in.close();
			out.close();
			stdIn.close();
			socket.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private static String removeAccent(String s) {
		// TODO Auto-generated method stub
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
}
