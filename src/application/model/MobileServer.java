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
	private ServerSocket mobileServer, gameServer;
	private Socket mobileSocket, gameSocket;
	
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
			gameServer = new ServerSocket(GAME_PORT);
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
	
	public void listenFromGame() throws IOException{
		mobileSocket =  mobileServer.accept();
	}
}
