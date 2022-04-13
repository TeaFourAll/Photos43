package src.controlller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.model.Album;
import src.model.Photo;
import src.model.User;
import javafx.event.ActionEvent;

public class UserController {

    
    User currentUser;
	List<User> users;
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");


	public void start(Stage mainStage, User user, List<User> users) throws IOException {

		welcome.setText("Welcome " + user.getUsername());
		
		this.users = users;
		this.currentUser=user;

		logout.setOnAction(e-> {
			try {
				  logout();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});

	}
		

    public void logout() throws IOException{
    	System.out.println("logout");
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/loginpage.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController loginControl = loader.getController();
		loginControl.start(stage);
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle("User Login");
		stage.show(); 
	}
	
}