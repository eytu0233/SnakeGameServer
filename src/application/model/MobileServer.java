package application.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MobileServer {
	
	public static final int MOBILE_PORT = 9527;
	public static final int GAME_PORT = 9526;

	private String localIP;	
	private ServerSocket mobileServer;
	private Socket mobileSocket;
	
	public Socket getMobileSocket() {
		return mobileSocket;
	}

	public String getLocalIP() {
		return localIP;
	}

	public MobileServer(){
		try {
			localIP = java.net.InetAddress.getLocalHost().getHostAddress();
			mobileServer = new ServerSocket(MOBILE_PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void accept() throws IOException{
		mobileSocket =  mobileServer.accept();
	}
}

class Listener implements Runnable {

	ObjectInputStream ois;

	public Listener(ObjectInputStream ois) {
		this.ois = ois;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				System.out.println(ois.readObject());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
