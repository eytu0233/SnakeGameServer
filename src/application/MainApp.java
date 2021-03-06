package application;

import java.io.IOException;

import application.model.BridgeServer;
import application.view.QRCodeOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private boolean closed = false;
	
	public boolean isClosed() {
		return closed;
	}

	private Stage primaryStage;
	private BorderPane rootLayout;

	private BridgeServer bridgeServer;

	public BridgeServer getBridgeServer() {
		return bridgeServer;
	}

	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data
		bridgeServer = new BridgeServer();
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SnakeGameServer");
		this.primaryStage.getIcons().add(
				new Image("file:resources/images/snake.png"));
		this.primaryStage.setOnCloseRequest((event) -> {
			if (bridgeServer != null)
				bridgeServer.closeAllConnection();
			closed = true;
			System.exit(0);
		});

		initRootLayout();

		showQRCodeOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showQRCodeOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/QRCodeOverview.fxml"));
			AnchorPane qrCodeOverview = loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(qrCodeOverview);

			// Give the controller access to the main app.
			QRCodeOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
