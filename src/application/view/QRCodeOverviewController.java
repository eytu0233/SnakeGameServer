package application.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.MainApp;
import application.model.MobileServer;
import application.util.QRCodeUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QRCodeOverviewController {
	@FXML
	private TextArea log;
	@FXML
	private ImageView qrcodeImage;
	
	// Reference to the main application.
    private MainApp mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public QRCodeOverviewController() {}
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
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
        	File QRCodeImage = QRCodeUtil.getQRCodeImageFile(String.format("%s:%d", localIP, mobilePort));
			qrcodeImage.setImage(new Image(new FileInputStream(QRCodeImage)));
			
			Thread mobileServerListener = new Thread(new mobileServerListenerThread(mainApp.getMobileServer(), log));
			mobileServerListener.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
}

class mobileServerListenerThread implements Runnable
{
	private MobileServer mobileServer;
	private TextArea log;
	
	public mobileServerListenerThread(MobileServer mobileServer, TextArea log){
		this.mobileServer = mobileServer;
		this.log = log;
	}	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Platform.runLater(()->log.setText(log.getText() + "MobileServer is listening...\n"));
		try {
			mobileServer.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Platform.runLater(()->log.setText(log.getText() + "MobileServer accepts connection from mobile phone...\n"));
	}
	
}

