package src.Photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import src.controlller.*;


public class Photos extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
//main
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/Loginpage.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController loginControl = loader.getController();
		loginControl.start(primaryStage);
		Scene scene = new Scene(root, 600, 400); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Login");
		primaryStage.show(); 
		
	}

}