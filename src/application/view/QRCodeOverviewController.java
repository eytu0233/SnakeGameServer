package application.view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class QRCodeOverviewController {
	@FXML
	private TextArea log;
	@FXML
	private ImageView qrcodeImage;
	
	// Reference to the main application.
    private MainApp mainApp;
	
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	
}
