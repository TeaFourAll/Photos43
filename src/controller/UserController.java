package controller;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Album;
import model.Photo;
import model.User;

/**
 * handles user control and logic for album display
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 * 
 */

public class UserController {
	/**
	 * The FXML Button to logout of the application
	 */
	@FXML
	Button logout;
	/**
	 * The FXML Button to enable creation of a new Album
	 */
	@FXML
	Button newAlbum;
	/**
	 * The FXML Button to enable renaming of an existing Album
	 */
	@FXML
	Button renameAlbum;
	/**
	 * The FXML Button to delete an existing Album
	 */
	@FXML
	Button delAlbum;
	/**
	 * The FXML Button to open an existing Album
	 */
	@FXML
	Button openAlbum;
	/**
	 * The FXML Button to search for Photos that meet certain criteria
	 */
	@FXML
	Button searchB;
	@FXML
	Button sLogout;
	@FXML
	Button sCreateAlbum;
	@FXML
	Button sBacktoAlbums;

	/**
	 * The FXML Button to confirm the renaming of an Album
	 */
	@FXML
	Button renameConfirm;
	/**
	 * The FXML Button to cancel a renaming of an existing Album
	 */
	@FXML
	Button renameCancel;
	/**
	 * The FXML ListView for the list of Albums belonging to this User
	 */
	@FXML
	ListView<Album> albumListView;
	/**
	 * The FXML ListView displaying the results of the search
	 */
	@FXML
	ListView<Photo> searchResults;
	/**
	 * The FXML TextField to name a new Album
	 */
	@FXML
	TextField albumField;
	/**
	 * The FXML TextField displaying a welcome message to the User
	 */
	@FXML
	TextField welcome;
	/**
	 * The FXML TextField to display the selected Album's name
	 */
	@FXML 
	TextField albumNameDetail;
	/**
	 * The FXML TextField to display the selected Album's total number of photos
	 */
	@FXML
	TextField albumNumPhotoDetail;
	/**
	 * The FXML TextField to display the selected Album's earliest date
	 */
	@FXML
	TextField albumDate1Detail;
	/**
	 * The FXML TextField to display the selected Album's latest date
	 */
	@FXML
	TextField albumDate2Detail;
	/**
	 * The FXML Button to confirm the addition of a new Album
	 */
	@FXML
	Button confirmAdd;
	/**
	 * The FXML Button to cancel the addition of a new Album
	 */
	@FXML
	Button newAlbumCancel;
	/**
	 * The current User logged in to the application
	 */
	User currentUser;
	List<User> users;
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");
	/**
	 * The ObservableList displaying the List of Albums
	 */
	private ObservableList<Album> obsList; 
	/**
	 * the List of Albums belonging to this User
	 */
	List<Album> AlbumList = new ArrayList<Album>();

	

	//do we need these two?
	List<String> albumStringList = new ArrayList<String>();
	List<Photo> photoLister = new ArrayList<Photo>();
	/**
	 * The main start method for UserController
	 * @param mainStage the Stage to execute on 
	 * @param user the current User that's logged in
	 * @param users the list of available Users
	 * @throws IOException
	 */
	public void start(Stage mainStage, User user, List<User> users) throws IOException {

		welcome.setText("Welcome " + user.getUsername() + ", Please select an Album: ");
		
		albumField.setDisable(true);
		confirmAdd.setDisable(true);
		
		this.users = users;
		this.currentUser=user;
		
		displayList();
		
		logout.setOnAction(e-> {
			try {
				  logout();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});
		openAlbum.setOnAction(e-> {
			try {
				  if (!openAlbum().equals("error")){
				  mainStage.hide();
				  }
			}catch (IOException e1) {
			}
		});
		searchB.setOnAction(e-> {
			try {
				  search();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});
		
		albumListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				albumDetail(mainStage));
	}
		
		
	/**
	 * Displays the name, number of Photos, and date range of the current Album on the side display area upon selection of a ListView item
	 * @param mainStage the stage to execute on
	 */
	private void albumDetail(Stage mainStage) {
		if (!albumListView.getSelectionModel().isEmpty()) {
			albumNameDetail.setVisible(true);
			albumNumPhotoDetail.setVisible(true);
			albumDate1Detail.setVisible(true);
			albumDate2Detail.setVisible(true);
			Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
			
			albumNameDetail.setText(selectedAlbum.albumName);
			albumNumPhotoDetail.setText(Integer.toString(selectedAlbum.photos.size()));
			
			
			String earliestDate="";
			String latestDate="";
			if (selectedAlbum.getPhotos().size()!=0) {
				Calendar date=selectedAlbum.getPhotos().get(0).getPhotoDate();
				 earliestDate=dateTimeformat.format(date.getTime());
				 latestDate=dateTimeformat.format(date.getTime());
			}
			for (Photo photo:selectedAlbum.getPhotos()) {
				Calendar date=photo.getPhotoDate();
		
				if (dateTimeformat.format(date.getTime()).compareTo(earliestDate)<0) {
					earliestDate=dateTimeformat.format(date.getTime());
				}
				if (dateTimeformat.format(date.getTime()).compareTo(latestDate)>0) {
					 latestDate=dateTimeformat.format(date.getTime());
				}
				
			}
			albumDate1Detail.setText(earliestDate);
			albumDate2Detail.setText(latestDate);
		}
	}
	/**
	 * Displays the name, number of Photos, and date range of the current Album on the side display area without selection of a ListView item
	 */
	private void albumDetailV2() {
		if (!albumListView.getSelectionModel().isEmpty()) {
			albumNameDetail.setVisible(true);
			albumNumPhotoDetail.setVisible(true);
			albumDate1Detail.setVisible(true);
			albumDate2Detail.setVisible(true);
			Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
			
			albumNameDetail.setText(selectedAlbum.albumName);
			albumNumPhotoDetail.setText(Integer.toString(selectedAlbum.photos.size()));
			String earliestDate="";
			String latestDate="";
			if (selectedAlbum.getPhotos().size()!=0) {
				Calendar date=selectedAlbum.getPhotos().get(0).getPhotoDate();
				 earliestDate=dateTimeformat.format(date.getTime());
				 latestDate=dateTimeformat.format(date.getTime());
			}
			for (Photo photo:selectedAlbum.getPhotos()) {
				Calendar date=photo.getPhotoDate();
		
				if (dateTimeformat.format(date.getTime()).compareTo(earliestDate)<0) {
					earliestDate=dateTimeformat.format(date.getTime());
				}
				if (dateTimeformat.format(date.getTime()).compareTo(latestDate)>0) {
					 latestDate=dateTimeformat.format(date.getTime());
				}
				
			}
			albumDate1Detail.setText(earliestDate);
			albumDate2Detail.setText(latestDate);
		}
	}
	/**
	 * Logs out of the application, allowing another User to log in
	 * @throws IOException
	 */
    public void logout() throws IOException{
    	System.out.println("logout pushed!");
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
     * Allows the creation of a new Album by disabling and enabling certain Buttons
     * @param e the ActionEvent to activate newAlbum()
     * @throws IOException
     */
    public void newAlbum(ActionEvent e) throws IOException{
    	albumField.setDisable(false);
		confirmAdd.setDisable(false);
		confirmAdd.setVisible(true);
		newAlbumCancel.setDisable(false);
		newAlbumCancel.setVisible(true);
		albumField.setVisible(true);
		renameAlbum.setDisable(true);
		delAlbum.setDisable(true);
		openAlbum.setDisable(true);
		searchB.setDisable(true);
	}

    /**
     * Allows the renaming of an existing Album by disabling and enabling certain Buttons
     * @param e the ActionEvent to activate renameAlbum()
     * @throws IOException
     */
    

    public void renameAlbum(ActionEvent e) throws IOException {

    	//System.out.println("Rename Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		albumNameDetail.setOpacity(1);
    		albumNameDetail.setPromptText("Enter new Album name here");
    		albumNameDetail.setEditable(true);
    		renameConfirm.setVisible(true);
    		renameConfirm.setDisable(false);
    		renameCancel.setVisible(true);
    		renameCancel.setDisable(false);
    		albumNumPhotoDetail.setDisable(true);
    		albumDate1Detail.setDisable(true);
    		albumDate2Detail.setDisable(true);
    		
    		delAlbum.setDisable(true);
    		openAlbum.setDisable(true);
    		searchB.setDisable(true);
    		newAlbum.setDisable(true);
    		
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("No selected album to edit!");
    		alert.showAndWait();
    	}
	}

    /**
     * Confirms the renaming of an existing Album
     * @param e the ActionEvent that will activate renameConfirm()
     * @throws IOException
     */
    
    public void renameConfirm(ActionEvent e) throws IOException {

    	Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
    	selectedAlbum.albumName = albumNameDetail.getText();
    	
    	autoSave(users);
    	
    	displayList();
    	albumDetailV2();
    	
		albumNameDetail.setEditable(false);
		renameConfirm.setVisible(false);
		renameConfirm.setDisable(true);
		renameCancel.setVisible(false);
		renameCancel.setDisable(true);
		albumNumPhotoDetail.setDisable(false);
		albumDate1Detail.setDisable(false);
		albumDate2Detail.setDisable(false);
		
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		searchB.setDisable(false);
		newAlbum.setDisable(false);
    }
    /**
     * Cancels the renaming of an existing Album
     * @param e the ActionEvent to activate renameCancel()
     */
    public void renameCancel(ActionEvent e) {
    	albumDetailV2();
    	albumNameDetail.setEditable(false);
		renameConfirm.setVisible(false);
		renameConfirm.setDisable(true);
		renameCancel.setVisible(false);
		renameCancel.setDisable(true);
		albumNumPhotoDetail.setDisable(false);
		albumDate1Detail.setDisable(false);
		albumDate2Detail.setDisable(false);
		
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		searchB.setDisable(false);
		newAlbum.setDisable(false);
    }
    /**
     * Deletes an existing Album from the List of Albums
     * @param e the ActionEvent to activate delAlbum()
     * @throws IOException
     */
    public void delAlbum(ActionEvent e) throws IOException {
    	System.out.println("Delete Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		
    		String albumToBeRemoved = albumListView.getSelectionModel().getSelectedItem().getAlbumName();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + albumToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int currIndex = albumListView.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				currentUser.getAlbums().remove(albumListView.getSelectionModel().getSelectedItem());
				albumListView.getItems().remove(albumListView.getSelectionModel().getSelectedItem());

				autoSave(users);
				displayList();
				
				if (albumListView.getItems().size()==0 || albumListView==null) {
					albumNameDetail.clear();
					albumNumPhotoDetail.clear();
					albumDate1Detail.clear();
					albumDate2Detail.clear();
			
					
				}
			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
    }
    /**
     * Opens the currently selected Album, redirecting to a new screen 
     * @return an error String if there is no selected album to open
     * @throws IOException
     */
    public String openAlbum() throws IOException{

    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
    		Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			PhotoDisplayController photoController = loader.getController();
			photoController.start(stage, selectedAlbum,currentUser,users,selectedAlbum.getPhotos());
			Scene scene = new Scene(root,923,671);
			stage.setScene(scene);
			root.getScene().getWindow().hide(); 
			stage.show();
			return "";
			
			
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No selected album to open");
			alert.showAndWait();
			return "error";
    	}
    }
    /**
     * Searches all Albums to find Photos that fit the Tag criteria
     * @param e the ActionEvent to activate search()
     * @throws IOException
     */
    public void search() throws IOException{
    	//System.out.println("logout pushed!");
    	if (albumListView.getItems().size()==0 || albumListView==null ) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("There are no albums in your account to search on");
			alert.showAndWait();
			return;
    	}
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/SearchDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		SearchController searchControl = loader.getController();
		searchControl.start(stage,this.currentUser,this.users);
		Scene scene = new Scene(root, 700, 700);
		stage.setScene(scene);
		stage.setTitle("Search Photos");
		stage.show(); 
    	}
    	
	
    /**
     * Creates a new Album based off the current search results
     * @param e the ActionEvent to activate sCreateAlbum()
     * @throws IOException
     */
    public void sCreateAlbum(ActionEvent e) throws IOException{
    	System.out.println("Create Album by Search Results pushed!");
    }
    /**
     * Confirms the addition of a new Album into the List of Albums
     * @param e the ActionEvent to activate confirm()
     */
    public void confirm(ActionEvent e) {
 
    	if ((albumField.getText().trim().length()==0 || albumField.getText()==null) ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Album name can't be empty! Please enter legitimate values");
			alert.showAndWait();
			return;
    	}
    	for (Album al : currentUser.getAlbums()) {
    		if (al.getAlbumName().equals(albumField.getText())) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Duplicate Album name!");
    			alert.showAndWait();
    			return;
    		}
    	}
    	currentUser.getAlbums().add(new Album(albumField.getText()));
		albumListView.getItems().add(new Album(albumField.getText()));
    	autoSave(users);
		displayList();
		
		albumField.clear();
		albumField.setDisable(true);
		confirmAdd.setDisable(true);
		confirmAdd.setVisible(false);
		newAlbumCancel.setDisable(true);
		newAlbumCancel.setVisible(false);
		albumField.setVisible(false);
		renameAlbum.setDisable(false);
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		searchB.setDisable(false);
    }
    /**
     * Cancels the addition of a new Album into the List of Albums
     * @param e the ActionEvent to activate albumCancel
     */
    public void albumCancel(ActionEvent e) {
    	albumField.clear();
    	albumField.setDisable(true);
		confirmAdd.setDisable(true);
		confirmAdd.setVisible(false);
		newAlbumCancel.setDisable(true);
		newAlbumCancel.setVisible(false);
		albumField.setVisible(false);
		renameAlbum.setDisable(false);
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		searchB.setDisable(false);
    }
    /**
     * Displays the current List of Albums in the ListView
     */
    public void displayList() {
    	obsList = FXCollections.observableArrayList(currentUser.getAlbums());
		albumListView.setItems(obsList);
		
	}
    /**
	 * Auto saves the current user data for future opening
	 * @param users the Users to save data to
	 */
	public static void autoSave(List<User> users) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(users);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
}
