package application.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class BridgeServer {

	public static final int MOBILE_PORT = 9527;
	public static final int GAME_PORT = 9526;

	private String localIP;
	private ServerSocket mobileServer, gameServer;
	private Socket mobileSocket, gameSocket;

	public InputStream getMobileServerInputStream() throws IOException {
		if (mobileSocket != null && mobileSocket.isConnected()) {
			return mobileSocket.getInputStream();
		}

		return null;
	}
	
	public OutputStream getGameServerOutputStream() throws IOException {
		if (gameSocket != null && gameSocket.isConnected()) {
			return gameSocket.getOutputStream();
		}

		return null;
	}

	public String getLocalIP() {
		return localIP;
	}

	public boolean mobileIsConnected() {
		return mobileSocket.isConnected();
	}
	
	public boolean gameIsConnected() {
		return mobileSocket.isConnected();
	}

	public BridgeServer() {
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

	public void mobileAccept() throws IOException {
		if (mobileServer != null && mobileSocket == null)
			mobileSocket = mobileServer.accept();
	}
	
	public void gameAccept() throws IOException {
		if (gameServer != null && gameSocket == null)
			gameSocket = gameServer.accept();
	}

	public void closeAllConnection() {
		if (mobileSocket != null) {
			if (!mobileSocket.isClosed()) {
				try {
					mobileSocket.close();
					mobileSocket = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				mobileSocket = null;
			}
		}

		if (gameSocket != null) {
			if (!gameSocket.isClosed()) {
				try {
					gameSocket.close();
					gameSocket = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				gameSocket = null;
			}
		}
	}

}
