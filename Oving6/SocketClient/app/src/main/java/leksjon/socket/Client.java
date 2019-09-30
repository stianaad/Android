package leksjon.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;  
import java.net.Socket; 
import android.util.Log;   
  
public class Client extends Thread {   
	private final static String TAG = "Client";
	private final static String IP = "10.0.2.2";
	private final static int PORT = 12345;
	private int tall1;
    private int tall2;
    private volatile String svar;

	public void setTall(int tall1, int tall2) {
	    this.tall1 = tall1;
	    this.tall2 = tall2;
	    svar = "0";
    }
    
	public void run() {
    	Socket s 			= null;
    	PrintWriter out		= null;
    	BufferedReader in 	= null;
    	
        try {
            s = new Socket(IP, PORT);
            Log.v(TAG, "C: Connected to server" + s.toString());
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            String res = in.readLine();
            Log.i(TAG,res);
            out.println(tall1 + " " + tall2);
            res = in.readLine();
            svar = res;
            Log.i(TAG, res);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close socket!!
        	try{
	        	out.close();
	        	in.close();
	        	s.close();
        	}catch(IOException e){}
        }
    }

    public String getSvar() {
	    return svar;
    }
}