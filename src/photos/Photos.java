package photos;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * main method here
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class Photos extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * starting method
	 * 
	 */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController loginControl = loader.getController();
		loginControl.start(primaryStage);
		Scene scene = new Scene(root, 600, 400); //resize as needed
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Login");
		primaryStage.show(); 
		
	}

}
