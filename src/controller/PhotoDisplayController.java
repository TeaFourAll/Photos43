package controller;

import java.io.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;

import java.text.*;
/**
 * handles user control and logic for photo display
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class PhotoDisplayController {
	/**
	 * current album 
	 */
	private Album currentAlbum;
	/**
	 * current user
	 */
	private User currentUser;
	/**
	 * list of users
	 */
	private List<User> users;
	
	/**
	 * button to add a tag
	 */
	@FXML
	Button addTag;
	/**
	 * button to delete a tag
	 */
	@FXML
	Button deleteTag;
	/**
	 * button to move photo from an album
	 */
	@FXML
	Button movePhotoP;
	/**
	 * button to copy a photo to an album
	 */
	@FXML
	Button copyPhoto;
	/**
	 * button that takes back to album display screen
	 */
	@FXML
	Button backToAlbum;
	/**
	 * button the cancels captioning photo
	 */
	@FXML
	Button captionCancel;
	/**
	 * listview of current photos in album
	 */
	@FXML
	ListView<Photo> photoList;
	/**
	 * textfield with date of photo
	 */
	@FXML
	TextField dateCapturedField;
	/**
	 * textfield with caption of photo
	 */
	@FXML
	TextField caption;
	/**
	 * textfield with current album name
	 */
	@FXML
	TextField albumHeader;
	/**
	 * textfield to put a name for a tag
	 */
	@FXML
	TextField tagNameField;
	/**
	 * textfield to put a value for a tag
	 */
	@FXML
	TextField tagValueField;
	/**
	 * listview of tags
	 */
	@FXML
	ListView<Tag> tagListView;
	/**
	 * button to caption a photo
	 */
	@FXML
	Button addCaptionButton;
	/**
	 * button to confirm the captioning
	 */
	@FXML	
	Button confirmCaption;

	/**
	 * imageview of current photo
	 */

	@FXML
	ImageView photoView;
	/**
	 * button to confirm tags
	 */
	@FXML	
	Button confirmTag;
	/**
	 * button to cancel the making of a tag
	 */
	@FXML	
	Button cancelTag;
	/**
	 * text field for album
	 */
	@FXML
	TextField albumField;
	/**
	 *   button that confirms the move of a photo
	 */
	@FXML 
	Button confirmMove;
	/**
	 *   button that identifies a photo
	 */
	@FXML
	Button identifier;
	
	
	
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy '@' hh:mm a");

	List<Photo> list=new ArrayList<>();
	/**
	 * the last made caption
	 */
	String lastCaption = "";
	
	/**
	 * observable list of photos
	 */
	private ObservableList<Photo> obsList;  


	/**
	 * starting method of this class
	 * @param mainStage this stage is executing
	 * @param album current album
	 * @param user current user
	 * @param users list of users
	 * @param photoList list of photos for current album
	 * @throws IOException IOException
	 */
	public void start(Stage mainStage,Album album, User user,List<User> users, List<Photo> photoList) throws IOException {
		this.list=photoList;
		displayList();

		
		this.currentAlbum=album;
		this.currentUser=user;
		this.users=users;
		confirmCaption.setVisible(false);
		cancelTag.setVisible(false);
		captionCancel.setVisible(false);
		albumHeader.setText(album.albumName);
		albumField.setVisible(false);
		confirmMove.setVisible(false);
		

		tagNameField.setVisible(false);
		tagValueField.setVisible(false);
		confirmTag.setVisible(false);

		
		backToAlbum.setOnAction(e -> {
			  try {
				  backToAlbum();
				  mainStage.hide();
			  }catch (IOException e1) {
			  }
		  });
		

		this.photoList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				photoDetail(mainStage));
		
		
	}
	/**
	 * shows the image, caption, date taken, and tags of the current photo after picking a listview item
	 * @param mainStage this stage is executing
	 */
	private void photoDetail(Stage mainStage) { 
		if (!photoList.getSelectionModel().isEmpty()) {
			photoView.setVisible(true);
			addCaptionButton.setVisible(true);
			Photo photo=photoList.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.source);
			photoView.setImage(p);
			String cap=photo.getPhotoCaption();
			Calendar date=photo.getPhotoDate();
			caption.setText(cap);
			dateCapturedField.setText(dateTimeformat.format(date.getTime()));
			this.tagListView.setItems(FXCollections.observableArrayList(this.photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
		}
	}
	/**
	 * shows the image, caption, date taken, and tags of the current photo w/o picking a listview item
	 * @param mainStage this stage is executing
	 */
	private void photoDetailV2() {
		if (!photoList.getSelectionModel().isEmpty()) {
			photoView.setVisible(true);
			addCaptionButton.setVisible(true);
			Photo photo=photoList.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.source);
			photoView.setImage(p);
			String cap=photo.getPhotoCaption();
			Calendar date=photo.getPhotoDate();
			caption.setText(cap);

			List<Tag> tags=photo.getPhotoTags();
			caption.setText(cap);
			dateCapturedField.setText(dateTimeformat.format(date.getTime()));
			this.tagListView.setItems(FXCollections.observableArrayList(this.photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
		}
	}
	/**
	 * goes back to the albumdisplay screen
	 * @throws IOException IOException
	 */
	public void backToAlbum() throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		UserController userController = loader.getController();
		userController.start(stage,currentUser,users);
		Scene scene = new Scene(root,923,671);
		stage.setScene(scene);
		root.getScene().getWindow().hide();
		stage.show();
		
	}
	/**
	 * if all is good will allow usser to add a new photo to the current album
	 * @param e activate addPhoto()
	 */
	public void addPhoto(ActionEvent e) {
		FileChooser photoPicker = new FileChooser();
		photoPicker.setTitle("Please choose an image to import");
		photoPicker.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png", "*.PNG"));	
		File chosenPicture = photoPicker.showOpenDialog(null);
		
		if (chosenPicture != null) {
			Image image = new Image(chosenPicture.toURI().toString());
			String name = chosenPicture.getName();
			Calendar photoDate = Calendar.getInstance();
			photoDate.setTimeInMillis(chosenPicture.lastModified());
			
			
			Photo photoToBeAdded = new Photo(name, "", photoDate, chosenPicture.toURI().toString());
			
			for (Photo currentPhoto : currentAlbum.getPhotos()) {
				if ( (currentPhoto.getPhotoName().equals(photoToBeAdded.getPhotoName())) && (currentPhoto.getPhotoSource().equals(photoToBeAdded.getPhotoSource()))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error in Adding Photo");
					alert.setContentText("This photo already exists in this album");
					alert.showAndWait();
					return;

				}
	
			}
		
	
			
			this.photoList.getItems().add(photoToBeAdded);
			
			currentAlbum.getPhotos().add(photoToBeAdded);	
			autoSave(users);
	
			

		}
		
	}
	/**
	 * delets photo from album
	 * @param e activates delPhoto()
	 */
	public void delPhoto(ActionEvent e) {
	if (photoList.getSelectionModel().getSelectedItem() != null) {
    		
    		Photo photoToBeRemoved = photoList.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + photoList.getSelectionModel().getSelectedItem().getPhotoName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {

				currentAlbum.getPhotos().remove(photoToBeRemoved);
				this.photoList.getItems().remove(photoToBeRemoved);
				photoView.setVisible(false);
	
				caption.clear();
				dateCapturedField.clear();

				tagListView.setItems(null);

				tagNameField.clear();
				tagValueField.clear(); 
				
				autoSave(users);
				

			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No album chosen to delete");
			alert2.showAndWait();
		}
	
	}
	/**
	 * moves a photo from one album to another
	 * @throws IOException IOException
	 */

	public void movePhoto(ActionEvent e) throws IOException {
		identifier=(Button) e.getSource();
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("No photo chosen to move");
			errorAlert.showAndWait();
			return;
		}
		albumField.setVisible(true);
		confirmMove.setVisible(true);
	}
	/**
	 * cpies a photo from one album to another
	 * @throws IOException IOException
	 */
	public void copyPhoto(ActionEvent e) throws IOException{
		identifier=(Button) e.getSource();
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("No photo chosen to move");
			errorAlert.showAndWait();
			return;
		}
		
		albumField.setVisible(true);
		confirmMove.setVisible(true);
		
	
			


	}
	/** 
	 * Enables the addition of Tags to the selected Photo
	 * @param e the ActionEvent to activate addTag()
	 */
	public void addTag(ActionEvent e) {
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No photo chosen to add tags");
			alert.showAndWait();
		}
		else {
		tagNameField.setVisible(true);
		tagValueField.setVisible(true);
		confirmTag.setVisible(true);
		cancelTag.setVisible(true);
		}
	}
	/**
	 * deletes tag from photo
	 * @param e activate deleteTag()
	 */
	public void deleteTag(ActionEvent e) {
		if (tagListView.getSelectionModel().getSelectedItem() != null) {
			Photo photoToBeRemoved = photoList.getSelectionModel().getSelectedItem();
    		Tag tagToBeRemoved = tagListView.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + tagToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				photoToBeRemoved.getPhotoTags().remove(tagToBeRemoved);
				tagListView.getItems().remove(tagToBeRemoved);
				tagListView.refresh();
				autoSave(users);
			}
    	} else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No tag name-value chosen to delete");
			alert2.showAndWait();
		}
		
	}
	/**
	 * chooses previous photo in album
	 * @param e activate prevPhoto()
	 */
	public void prevPhoto(ActionEvent e) {
		
		if (!photoList.getSelectionModel().isEmpty() && photoList.getSelectionModel().getSelectedIndex() != 0) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()-1);
			photoDetailV2();
		}
	}
	/**
	 * chooses next photo in album
	 * @param e activates nextPhoto()
	 */
	public void nextPhoto(ActionEvent e) {
		
		if (!photoList.getSelectionModel().isEmpty() /*&& not greater than size of available list*/ ) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()+1);
			photoDetailV2();
		}
	}
	/**
	 * confirms new tags to be added to picture
	 * @param e activates confirmAddTag()
	 */
	public void confirmAddTag(ActionEvent e) {
		if ((tagNameField.getText().trim().length()==0 || tagNameField.getText()==null) ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Tag name can't be empty, please try again");
			alert.showAndWait();
		}
		else {
	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Confirmation on this tag name-value pair?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Photo selectedPhoto=photoList.getSelectionModel().getSelectedItem();
					Tag tag=new Tag(tagNameField.getText(),tagValueField.getText());
					List<Tag> photoTags=selectedPhoto.getPhotoTags();
					for (Tag ptag:photoTags) {
						if ((ptag.tagName.equals(tag.tagName)) && (ptag.tagValue.equals(tag.tagValue))) {
							Alert erroralert = new Alert(AlertType.ERROR);
							erroralert.setHeaderText("This tag name-value pair already exists in this album");
							erroralert.showAndWait();
							return;
						}
					}
					photoTags.add(tag);
					tagListView.getItems().add(tag);
					
					tagListView.refresh();
					autoSave(users);
					tagNameField.clear();
					tagValueField.clear();
					
					tagNameField.setVisible(false);
					tagValueField.setVisible(false);
					confirmTag.setVisible(false);
				
				}
	
			
		}


	
	}
	/**
	 * captions photo
	 * @param e activates addCaption()
	 */
	public void addCaption(ActionEvent e) {
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No photo chosen to add a Caption");
			alert.showAndWait();
		}
		else {
			lastCaption = caption.getText();
			caption.setEditable(true);
			confirmCaption.setVisible(true);
			captionCancel.setVisible(true);
		}
	}
	/**
	 * confirms captioning of photo
	 * @param e activates confirm()
	 */
	public void confirm(ActionEvent e) {
		
	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Confirm " + caption.getText() + " as caption for this Photo?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
	
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
		
					Photo selectedPhoto=photoList.getSelectionModel().getSelectedItem();
					selectedPhoto.setPhotoCaption(caption.getText());
					autoSave(users);
					displayList();
					caption.setEditable(false);
					confirmCaption.setVisible(false);
					captionCancel.setVisible(false);

				
				}
	}
	/**
	 * cancels attempted caption
	 * @param e activates captionCancel()
	 */
	public void captionCancel(ActionEvent e) {
		caption.setEditable(false);
		confirmCaption.setVisible(false);
		captionCancel.setVisible(false);
		caption.setText(lastCaption);
	}

	/**
	 * cancels new tag
	 * 	 * @param e activates tagCancel()

	 */
	public void tagCancel(ActionEvent e) {
		tagNameField.setVisible(false);
		tagValueField.setVisible(false);
		confirmTag.setVisible(false);
		cancelTag.setVisible(false);
	}
	/**
	 * shows the current albums photos and captions
	 */
	public void displayList() {
		obsList = FXCollections.observableArrayList(this.list); 

		photoList.setItems(obsList); 
		photoList.setCellFactory(listView -> new ListCell<Photo>() {
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
		            setText(photo.getPhotoCaption());
		            setGraphic(photoView);
		        }
		    }
		});
		
	}

	/**
	 * confrims moving photo
	 */
	public void moveConfirm() {
			Photo photoToBeMoved=photoList.getSelectionModel().getSelectedItem();
			String newAlbum=albumField.getText();
			for (Album alb:this.currentUser.getAlbums()) {
				System.out.println(alb.getAlbumName());
				if (alb.getAlbumName().equals(newAlbum)) {
				
					for (Photo photo:alb.getPhotos()) {
						if ((photo.getPhotoName().equals(photoToBeMoved.getPhotoName())) && (photo.getPhotoSource().equals(photoToBeMoved.getPhotoSource()))) {
							Alert palert = new Alert(AlertType.ERROR);
							palert.setHeaderText("This photo is already exists in the other album");
							palert.showAndWait();
							return;
						}
					}
					
					alb.getPhotos().add(photoToBeMoved);
					autoSave(users);
					if (identifier.equals(movePhotoP)) {
						this.currentAlbum.getPhotos().remove(photoToBeMoved);
						photoList.getItems().remove(photoToBeMoved);
				
					}
					albumField.setVisible(false);
					confirmMove.setVisible(false);
					albumField.clear();
					autoSave(users);
					return;
				}
			}
			Alert palert = new Alert(AlertType.ERROR);
			palert.setHeaderText("This album doesn't exist");
			palert.showAndWait();
			albumField.clear();
			
		
	}
	/**
	 * autosaves admin data
	 * @param users the users data to b saved
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
