package application.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameServer {
	
	public static final int MOBILE_PORT = 9527;
	public static final int GAME_PORT = 9526;

	private String localIP;	
	private ServerSocket gameServer; 
	
	public String getLocalIP() {
		return localIP;
	}

	public GameServer(){
		try {
			localIP = java.net.InetAddress.getLocalHost().getHostAddress();
			gameServer = new ServerSocket(MOBILE_PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket accept() throws IOException{
		return gameServer.accept();
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
