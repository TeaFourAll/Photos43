package controller;

import java.io.*;
import java.text.*;
import java.util.*;
import model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * handles user control and logic for search display 
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 * 
 */
public class SearchController {

	/**
	 * current user
	 */
	User currentUser;
	/**
	 * bchoice box for tag
	 */
	@FXML
	ChoiceBox<String> TypeChoiceBox;
	/**
	 * choice box for value of tag
	 */
	@FXML
    ChoiceBox<String> ValueChoiceBox;
	/**
	 * listview of tags
	 */
	@FXML
	ListView<Tag> tagListView;
	/**
	 * listview of photos
	 */
	@FXML
	ListView<Photo> photoListView;
	/**
	 * datepicker of dates
	 */
	@FXML
	DatePicker from;
	/**
	 * datepicker of dates
	 */
	@FXML
	DatePicker to;
	/**
	 * button to delete tag
	 */
	
	@FXML
	Button delTag;
	/**
	 * button to search tag
	 */
	@FXML
	Button search;
	/**
	 * button to create album from search
	 */
	@FXML
    Button sCreateAlbum;
	/**
	 * button to go back to album from search
	 */
	@FXML
    Button sBacktoAlbums;
	/**
	 * button to add a tag
	 */
	@FXML
	Button addTag;
	/**
	 * button to clear dates
	 */
	@FXML
	Button clearDates;


   /**
	 * list of users
	 */
	List<User> users;
	List<String> tagtype = new ArrayList<String>();
	List<String> tagvalue = new ArrayList<String>();
	List<Photo> list=new ArrayList<>();
	Set<String> dupSet=new HashSet<>();
	/**
	 * starting method of this class
	 * @param mainStage this stage is executing
	 * @param user current user
	 * @param users list of users
	 */
	public void start(Stage mainStage, User user, List<User> users) {
				
		this.currentUser=user;
		this.users=users;
		
		sBacktoAlbums.setOnAction(e-> {
			try {
				  backToAlbum();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});
		

		List<Album> userAlbums = this.currentUser.getAlbums();
		for (Album current : userAlbums) {
			List<Photo> albumPhotos = current.getPhotos();
			for (Photo photo : albumPhotos) {
				List<Tag> photoTags = photo.getPhotoTags();
				for (Tag t : photoTags) {
					if (!tagtype.contains(t.getTagName()))
						tagtype.add(t.getTagName());
					if (!tagvalue.contains(t.getTagValue()))
						tagvalue.add(t.getTagValue());
				}

			}

		}
		ObservableList<String> obsList= FXCollections.observableArrayList(tagtype); 
		TypeChoiceBox.setItems(obsList);
		obsList= FXCollections.observableArrayList(tagvalue); 
		ValueChoiceBox.setItems(obsList);

		
	}
	
	/**
	 * creates album from search
	 * @param e activates sCreateAlbum
	 */
	public void sCreateAlbum(ActionEvent e) {
		Set<String> dupSet=new HashSet<>();
		if (photoListView.getItems().size()==0 || photoListView==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Search had no results to create an album");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Confirm creating an album from the search results?" , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd '@' HH:mm:ss");
				Calendar date = Calendar.getInstance();
				Album newAlbum = new Album(dateFormat.format(date.getTime()));
				this.currentUser.getAlbums().add(newAlbum);

				for (Photo photo : photoListView.getItems()) {
					if (dupSet.contains(photo.getPhotoName()+"-" + photo.getPhotoSource())) {
						continue;
					}
					dupSet.add(photo.getPhotoName()+"-" + photo.getPhotoSource());
					
					newAlbum.getPhotos().add(photo);
					autoSave(users);
				}
			}
		}
	}
	/**
	 * confirms the tag selection criteria
	 * @param e activates confirm()
	 */
	public void confirm(ActionEvent e) {
	
			String tagType=null;
			try {
			 tagType=TypeChoiceBox.getSelectionModel().getSelectedItem().toString();
			}
			catch(Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Please choose the right tag type");
			alert.showAndWait();
			return;
			}
	
		String tagValue=null;
		try {
		 tagValue=ValueChoiceBox.getSelectionModel().getSelectedItem().toString();
		}
		catch(Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Please choose the right tag tag value");
			alert.showAndWait();
			return;
		}
		if (tagValue!=null) {
		Tag tagToBeAdded=new Tag(tagType,tagValue);
		if (!this.dupSet.contains(tagToBeAdded.getTagName() + "-" + tagToBeAdded.getTagValue())) {
		tagListView.getItems().add(tagToBeAdded);
		this.dupSet.add(tagToBeAdded.getTagName() + "-" + tagToBeAdded.getTagValue());
		TypeChoiceBox.setValue(null);
		ValueChoiceBox.setValue(null);
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("This tag already exists");
			alert.showAndWait();
			
		}
		}
		
			
		
	}
	/**
	 * Goes back to the AlbumDisplay screen 
	 * @throws IOException IOException
	 */
	public void backToAlbum() throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		UserController userController = loader.getController();
		userController.start(stage,this.currentUser,this.users);
		Scene scene = new Scene(root,923,671);
		stage.setScene(scene);
		stage.show();	
		
	}
	/**
	 * confirms the deletion of a tag to search by
	 */
	public void confirmDel() {
		if (tagListView.getSelectionModel().getSelectedItem() != null) {
    		
    		Tag tagToBeRemoved = tagListView.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + tagToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				tagListView.getItems().remove(tagToBeRemoved);	
				photoListView.getItems().clear();
				list.clear();
				this.dupSet.remove(tagToBeRemoved.getTagName()+ "-" + tagToBeRemoved.getTagValue());

			}
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No tag chose to delete");
			alert.showAndWait();
		}
	}
	/**
	 * searches all Albumsfor user and adds phots from search
	 * @throws ParseException ParseException
	 */
	public void searchP() throws ParseException {
		Set<String> dupSet=new HashSet<>();
		list.clear();
		photoListView.getItems().clear();
		
		List<Album> userAlbums = this.currentUser.getAlbums();
		for (Album current : userAlbums) {
			List<Photo> albumPhotos = current.getPhotos();
			for (Photo photo : albumPhotos) {
				
				 SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
				 Calendar photoDate=photo.getPhotoDate();
				 if ((from.getValue() != null && to.getValue() == null) ||(from.getValue() == null && to.getValue() != null) ) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Please enter both dates");
						alert.showAndWait();
						return;
				 }
				else if (from.getValue() != null && to.getValue() != null) {
				 String fromDateVal=from.getValue().toString();
				 String[] split=fromDateVal.split("-");
				 String fromDate= split[1] + "/" + split[2] + "/" + split[0];
				 String toDateVal=to.getValue().toString();
				 String[] split2=toDateVal.split("-");
				 String toDate= split2[1] + "/" + split2[2] + "/" + split2[0];
				 if (SDF.format(photoDate.getTime()).compareTo(fromDate)>=0 && SDF.format(photoDate.getTime()).compareTo(toDate)<=0 ) {
					 if (dupSet.contains(photo.getPhotoName()+ "-" + photo.getPhotoSource())) {
							continue;
						}
							else {
								dupSet.add(photo.getPhotoName()+ "-" + photo.getPhotoSource());
								list.add(photo);
						
							}
				  }
			    }
	
				
        List<Tag> photoTags = photo.getPhotoTags();
				
				if (tagListView.getItems() != null && photoTags != null ) {
					for (Tag currTag : tagListView.getItems()) {
						for (Tag pTag : photoTags) {
							if (currTag.getTagName().equals(pTag.getTagName()) && currTag.getTagValue().equals(pTag.getTagValue())) {
								if (dupSet.contains(photo.getPhotoName()+ "-" + photo.getPhotoSource())) {
									continue;
								}
									else {
										dupSet.add(photo.getPhotoName()+ "-" + photo.getPhotoSource());
										list.add(photo);
										
									}
								}
			

						}
					}

				}

			}

		}
		displayList();
	}
	/**
	 * displays searched photos
	 */
	public void displayList() {
		photoListView.getItems().clear();
		ObservableList<Photo> obsList= FXCollections.observableArrayList(this.list); 

		photoListView.setItems(obsList); 
		photoListView.setCellFactory(listView -> new ListCell<Photo>() {
	    private ImageView photoView = new ImageView();
	    @Override
	    public void updateItem(Photo photo, boolean empty) {
	        super.updateItem(photo, empty);
	        if (empty) {
	            setText(null);
	            setGraphic(null);
	        } else {
	            Image image = new Image(photo.source);
	            photoView.setFitHeight(50);
	            photoView.setFitWidth(70);
	            photoView.setImage(image);
	            setText(photo.getPhotoName());
	            setGraphic(photoView);
	        }
	    }
	});
	}
	/**
	 * auto saves admin data
	 * @param users users to save data for
	 */
	private static void autoSave(List<User> users) {
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
	/**
	 * clears the searched date
	 */
	public void clear() {
		from.setValue(null);
		to.setValue(null);
	}
}
