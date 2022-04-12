package controlller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.plaf.ButtonUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import controlller.LoginController;

import javafx.event.ActionEvent;

public class AdminController {
//add new user button
	Button addUser;
	// delete an existing User button
	Button delUser;
	//delete user
	ListView<User> userList;
    //logout button 
	Button aLogout;
    //add new user 
	TextField userField;
    //confirming addition of new user button
	ButtonUI confirmAdd;
	//available Users to login  
	List<String> list=new ArrayList<>();

	 //Main start method for AdminController
	List<User> usersList=new ArrayList<>();
	
	private ObservableList<String> obsList;  
	
	public void start(Stage mainStage, List<User> users) throws IOException {
		this.usersList=users;
		userList.setItems(FXCollections.observableArrayList(users));

		userField.setDisable(true);
		confirmAdd.setDisable(true);
		
		aLogout.setOnAction(e -> {
			  try {
				  logout();
				  mainStage.hide();
			  }catch (IOException e1) {
			  }
		  });
	}
	//add new user
	public void addUser(ActionEvent e) throws IOException {
		userField.setDisable(false);
		confirmAdd.setDisable(false);
	}
	// Deletes a current User from the list of available Users

	public void delUser(ActionEvent e) throws IOException {
	
		if (userList.getSelectionModel().getSelectedItem() != null) {
	        User selectedUser=userList.getSelectionModel().getSelectedItem();
	        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedUser.getUsername() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.setTitle("Delete user");
			alert.showAndWait();
			int currIndex = userList.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				userList.getItems().remove(selectedUser);
				autoSave();
				
		
			}
	    }
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No selected user to delete!");
			alert.showAndWait();

		}
	}
	//New User, checking for duplicates and bad entries

	public void confirm(ActionEvent e) {
		if (userField.getText().trim().length()==0 || userField.getText()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No username entered! Please enter a username");
			alert.showAndWait();
		}
		else {
			String newUser=userField.getText();
			for (User user:this.usersList) {
				if (user.getUsername().equals(newUser)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This username already exists");
					alert.showAndWait();
					return;
				}
			}
				Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + newUser + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.setTitle("Add new user");
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					
					userList.getItems().add(new User(newUser));
					autoSave();
					userField.clear();
					userField.setDisable(true);
					confirmAdd.setDisable(true);
				
				}
	
		}
	}
	//logout
	public void logout() throws IOException{
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