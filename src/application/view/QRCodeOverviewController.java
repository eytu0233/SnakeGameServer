package application.view;

import java.io.*;
import java.net.SocketException;

import application.MainApp;
import application.model.MobileServer;
import application.util.QRCodeUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QRCodeOverviewController {
	@FXML
	private TextArea log;
	@FXML
	private ImageView qrcodeImage;
	@FXML
	private ImageView gameConnectionImage;
	@FXML
	private ImageView mobileConnectionImage;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public QRCodeOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		try {
			mobileConnectionImage.setImage(new Image(
					"file:resources/images/android_gray.png"));
			gameConnectionImage.setImage(new Image(
					"file:resources/images/snake_gray.png"));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue,
					Object newValue) {
				log.setScrollTop(Double.MAX_VALUE); // this will scroll to the
													// bottom
				// use Double.MIN_VALUE to scroll to the top
			}
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		String localIP = mainApp.getMobileServer().getLocalIP();
		int mobilePort = mainApp.getMobileServer().MOBILE_PORT;

		try {
			File QRCodeImage = QRCodeUtil.getQRCodeImageFile(String.format(
					"%s:%d", localIP, mobilePort));
			qrcodeImage.setImage(new Image(new FileInputStream(QRCodeImage)));

			Thread transendListener = new Thread(() -> {
				Thread mobileServerListener = new Thread(
						new mobileServerListenerThread(
								mainApp.getMobileServer()));
				mobileServerListener.start();
				try {
					mobileServerListener.join();
					DataInputStream dis = new DataInputStream(mainApp
							.getMobileServer().getMobileServerInputStream());
					while (mainApp.getMobileServer().mobileIsConnected()) {
						Byte b = dis.readByte();
						Platform.runLater(() -> log.appendText(b + "\n"));
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {// mobile client disconnection
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketException e) {// closed socket
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			transendListener.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class mobileServerListenerThread implements Runnable {
		private MobileServer mobileServer;

		public mobileServerListenerThread(MobileServer mobileServer) {
			this.mobileServer = mobileServer;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			Platform.runLater(() -> log
					.appendText("MobileServer is listening...\n"));
			try {
				mobileServer.accept();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				log.appendText("MobileServer accepts connection from mobile phone...\n");
				mobileConnectionImage.setImage(new Image(
						"file:resources/images/android.png"));
			});
		}

	}
}
