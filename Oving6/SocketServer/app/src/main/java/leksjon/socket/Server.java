package leksjon.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

public class Server extends Thread{
	private final static String TAG = "ServerThread";
	private final static int PORT = 12345;
	
	public void run() {
		ServerSocket ss 	= null;
		Socket s 			= null;
		PrintWriter out 	= null;
		BufferedReader in 	= null;
		do {
			try {
				Log.i(TAG, "start server....");
				ss = new ServerSocket(PORT);
				Log.i(TAG, "serversocket created, wait for client....");
				s = ss.accept();
				Log.v(TAG, "client connected...");
				out = new PrintWriter(s.getOutputStream(), true);
				in = new BufferedReader(
						new InputStreamReader(s.getInputStream()));

				out.println("Welcome client...");//send text to client

				String res = in.readLine();//receive text from client
				String[] split = res.split(" ");
				for (int i = 0; i < split.length; i++) {
					Log.i(TAG, "Message from client: " + split[i]);
				}
				int tall1 = Integer.parseInt(split[0]);
				int tall2 = Integer.parseInt(split[1]);
				out.println((tall1 + tall2));

			} catch (IOException e) {
				e.printStackTrace();
			} finally {//close sockets!!
				try {
					out.close();
					in.close();
					s.close();
					ss.close();
				} catch (Exception e) {
				}
			}
		}while(true);
	}
}
