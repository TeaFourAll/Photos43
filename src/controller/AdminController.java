package controller;
import java.io.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.*;
import model.*;
//import controller.LoginController;

import javafx.event.*;

/**
 * handles user control and logic in the admin screen
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class AdminController {
	
	/**
	 * listview of users
	 */
	@FXML
	ListView<User> userList;
	/**
	 * textfield to add user
	 */
	@FXML
	TextField userField;
	
	/**
	 * button to logout
	 */
	@FXML
	Button aLogout;
	/**
	 * button to add new user
	 */
	@FXML
	Button addUser;
	/**
	 * button to delete user
	 */
	@FXML
	Button delUser;
	/** 
	 * button to confirm a new user
	 */
	@FXML
	Button confirmAdd;
	/**
	 * list of users who can login
	 */
	List<String> list=new ArrayList<>();

 
	/**
	 * The main start method for this class
	 * @param mainStage this stage is executing
	 * @throws IOException
	 */
	
	List<User> usersList=new ArrayList<>();
	  
	
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
	/**
	 * new user to be added to the list, textField popup to enter info
	 * @param e ActionEvent to activate addUser()
	 * @throws IOException
	 */
	public void addUser(ActionEvent e) throws IOException {
		userField.setDisable(false);
		confirmAdd.setDisable(false);
	}
	/**
	 * deletes a user from the list of available users
	 * @param e the ActionEvent to activate delUser()
	 * @throws IOException
	 */
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
	/**
	 * confirms a new user, checks for duplicates/bad entries
	 * @param e the ActionEvent to activate confirm()
	 */
	public void confirm(ActionEvent e) {
		if (userField.getText().trim().length()==0 || userField.getText()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No username entered! Please enter a username");
			alert.showAndWait();
		}
		else {
			String newUser=userField.getText();
			//if (LoginController.users.containsKey(newUser)) {
			for (User user:this.usersList) {
				if (user.getUsername().equals(newUser)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This username already exists");
					alert.showAndWait();
					return;
				}
			}
				
		
		//	else {
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
	
			//}
		}
	}
	/**
	 * log out of the app
	 * @throws IOException
	 */
	public void logout() throws IOException{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController loginControl = loader.getController();
		loginControl.start(stage);
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle("User Login");
		stage.show(); 
	}
	/**
	 * auto save admin data
	 */
	private void autoSave() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(new ArrayList<>(Arrays.asList(userList.getItems().toArray())));
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	
}
