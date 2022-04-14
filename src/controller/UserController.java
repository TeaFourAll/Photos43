package controller;
import java.io.*;
import java.text.*;
import java.util.*;
import model.*;


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
import javafx.event.ActionEvent;


/**
 * handles user control and logic for album display
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 * 
 */

public class UserController {
	/**
	 * button to logout
	 */
	@FXML
	Button logout;
	/**
	 * button to create new album
	 */
	@FXML
	Button newAlbum;
	/**
	 * button to rename album
	 */
	@FXML
	Button renameAlbum;
	/**
	 * button to delete album
	 */
	@FXML
	Button delAlbum;
	/**
	 * button to open an album
	 */
	@FXML
	Button openAlbum;
	/**
	 * button to search
	 */
	@FXML
	Button searchB;

	/**
	 * button to confrim rename
	 */
	@FXML
	Button renameConfirm;
	/**
	 * button to cancel rename
	 */
	@FXML
	Button renameCancel;
	/**
	 * listview of albums
	 */
	@FXML
	ListView<Album> albumListView;
	/**
	 * listview of search results
	 */
	@FXML
	ListView<Photo> searchResults;
	/**
	 * textfield to name album
	 */
	@FXML
	TextField albumField;
	/**
	 * textfield with welcome message
	 */
	@FXML
	TextField welcome;
	/**
	 * textfield to display album name
	 */
	@FXML 
	TextField albumNameDetail;
	/**
	 * textfield showing total number of photos
	 */
	@FXML
	TextField albumNumPhotoDetail;
	/**
	 * textfield showing earliest date
	 */
	@FXML
	TextField albumDate1Detail;
	/**
	 * textfield displaying albums latest date
	 */
	@FXML
	TextField albumDate2Detail;
	/**
	 * button to confirm adding an album
	 */
	@FXML
	Button confirmAdd;
	/**
	 * button to cancel adding an album
	 */
	@FXML
	Button newAlbumCancel;
	/**
	 * current user
	 */
	User currentUser;
	List<User> users;
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");
	/**
	 * observable list of albums
	 */
	private ObservableList<Album> obsList; 
	/**
	 * list of albums
	 */
	List<Album> AlbumList = new ArrayList<Album>();
	@FXML
	Button sLogout;
	@FXML
	Button sCreateAlbum;
	@FXML
	Button sBacktoAlbums;

	
	List<String> albumStringList = new ArrayList<String>();
	List<Photo> photoLister = new ArrayList<Photo>();
	/**
	 * starting method of this class
	 * @param mainStage this stage is executing
	 * @param user current user
	 * @param users list of users
	 * @throws IOException IOException
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
	 * shows name, number of photos, and date of album
	 * @param mainStage this stage is executing
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
	 * shows the name, number of photos, and date of album 
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
	 * logs out
	 * @throws IOException IOException
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
     * creates new album
     * @param e activates newAlbum()
     * @throws IOException IOException
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
     * renames album
     * @param e activates renameAlbum()
     * @throws IOException IOException
     */
    

    public void renameAlbum(ActionEvent e) throws IOException {

    	//System.out.println("Rename Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		albumNameDetail.setOpacity(1);
    		albumNameDetail.setPromptText("New Album name");
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
    		alert.setHeaderText("No album to edit");
    		alert.showAndWait();
    	}
	}

    /**
     * confrim rename of album
     * @param e activates renameConfirm()
     * @throws IOException IOException
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
     * cancels rename of album
     * @param e activate renameCancel()
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
     * deletes an album
     * @param e the ActionEvent to activate delAlbum()
     * @throws IOException IOException
     */
    public void delAlbum(ActionEvent e) throws IOException {
    	System.out.println("Delete album");
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
			alert2.setHeaderText("No album to delete");
			alert2.showAndWait();
		}
    }
    /**
     * opens album 
     * @return an error if no album to open
     * @throws IOException IOException
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
			alert.setHeaderText("No chosen album to open");
			alert.showAndWait();
			return "error";
    	}
    }
    /**
     * searches for tag criteria
     * @param e activates search()
     * @throws IOException IOException
     */
    public void search() throws IOException{
    	if (albumListView.getItems().size()==0 || albumListView==null ) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("There are no albums to search");
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
     * creates a new album off search results
     * @param e activates sCreateAlbum()
     * @throws IOException IOException
     */
    public void sCreateAlbum(ActionEvent e) throws IOException{
    	System.out.println("Create Album from search results");
    }
    /**
     * confirm addition of new album
     * @param e activates confirm()
     */
    public void confirm(ActionEvent e) {
 
    	if ((albumField.getText().trim().length()==0 || albumField.getText()==null) ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Album name can't be empty, Please enter something");
			alert.showAndWait();
			return;
    	}
    	for (Album al : currentUser.getAlbums()) {
    		if (al.getAlbumName().equals(albumField.getText())) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setHeaderText("Album name already exists");
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
     * cancels adding new album
     * @param e activates albumCancel()
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
     * displays albums
     */
    public void displayList() {
    	obsList = FXCollections.observableArrayList(currentUser.getAlbums());
		albumListView.setItems(obsList);
		
	}
    /**
	 * auto saves admin data
	 * @param users users to save data for
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
