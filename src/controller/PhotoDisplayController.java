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
 * handles  user control and logic for photo display
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class PhotoDisplayController {
	/**
	 * The current Album accessed
	 */
	private Album currentAlbum;
	/**
	 * The User that is currently logged in
	 */
	private User currentUser;
	/**
	 * The List of Users
	 */
	private List<User> users;
	
	/**
	 * The FXML Button to enable the addition of a new Tag
	 */
	@FXML
	Button addTag;
	/**
	 * The FXML Button to delete an existing Tag
	 */
	@FXML
	Button delTag;
	/**
	 * The FXML Button to start moving a photo from one Album to another
	 */
	@FXML
	Button movePhotoP;
	/**
	 * The FXML Button to start copying a photo from one Album to another
	 */
	@FXML
	Button copyPhoto;
	/**
	 * The FXML Button to go back to the AlbumDisplay screen
	 */
	@FXML
	Button backToAlbum;
	/**
	 * The FXML Button to cancel (re)captioning a Photo
	 */
	@FXML
	Button captionCancel;
	/**
	 * The FXML ListView displaying the current Photos in the Album
	 */
	@FXML
	ListView<Photo> photoList;
	/**
	 * The FXML TextField displaying the date that the selected Photo was captured on
	 */
	@FXML
	TextField dateCapturedField;
	/**
	 * The FXML TextField displaying the caption of the selected Photo
	 */
	@FXML
	TextField caption;
	/**
	 * The FXML TextField displaying the name of the accessed Album
	 */
	@FXML
	TextField albumHeader;
	/**
	 * The FXML TextField allowing the User to insert a NAME parameter for a Tag
	 */
	@FXML
	TextField tagNameField;
	/**
	 * The FXML TextField allowing the User to insert a VALUE parameter for a Tag
	 */
	@FXML
	TextField tagValueField;
	/**
	 * The FXML ListView displaying the Tags of the selected Photo
	 */
	@FXML
	ListView<Tag> tagListView;
	/**
	 * The FXML Button to enable the (re)captioning of a Photo
	 */
	@FXML
	Button addCaptionButton;
	/**
	 * The FXML Button to confirm the (re)captioning of a Photo
	 */
	@FXML	
	Button confirmCaption;

	/**
	 * The FXML ImageView to display the currently selected Photo
	 */

	@FXML
	ImageView photoView;
	/**
	 * The FXML Button to confirm the addition of new Tags to a Photo
	 */
	@FXML	
	Button confirmTag;
	/**
	 * The FXML Button to cancel an attempted Tag add
	 */
	@FXML	
	Button cancelTag;
	
	@FXML
	TextField albumField;
	
	
	
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy '@' hh:mm a");

	List<Photo> list=new ArrayList<>();
	/**
	 * The most recently used caption 
	 */
	String lastCaption = "";
	
	/**
	 * The ObservableList displaying the List of Photos
	 */
	private ObservableList<Photo> obsList;  

	/**
	 *   Button to confirm move/copy of a photo to a specific album
	 */
	@FXML 
	Button confirmMove;
	
	@FXML
	Button identifier;

	/**
	 * The main start method of PhotoDisplayController
	 * @param mainStage the Stage to execute on
	 * @param album the currently accessed Album
	 * @param user the User that's currently logged in
	 * @param users the list of available Users
	 * @param photoList the list of photos in this Album
	 * @throws IOException
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
		
//<<<<<<< Updated upstream
		/*movePhoto.setOnAction(e -> {
			try {
				movePhoto(e);
			}catch (IOException e1) {
				//System.out.println("bruh exception");
			}
		});*/
		
		/*copyPhoto.setOnAction(e -> {
			try {
				copyPhoto(e);
			}catch (IOException e1) {
				//System.out.println("bruh exception");
			}
		});*/
		


		
		this.photoList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				photoDetail(mainStage));
	
		//this.photoList.setItems(FXCollections.observableArrayList(photoList));
		
		
		
	}
	/**
	 * Displays the image, caption, date taken, and Tags of the current Photo on the bottom display area upon selection of a ListView item
	 * @param mainStage the stage to execute on
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
/*<<<<<<< Updated upstream
			List<Tag> tags=photo.getPhotoTags();
			tagListView.setItems(FXCollections.observableArrayList(photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
=======*/
			this.tagListView.setItems(FXCollections.observableArrayList(this.photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
			

		}
	}
	/**
	 * Displays the image, caption, date taken, and Tags of the current Photo on the bottom display area without selection of a ListView item
	 * @param mainStage the stage to execute on
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
	 * Redirects back to the AlbumDisplay screen
	 * @throws IOException
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
	 * Opens up a FileChooser to allow the User to add a new Photo, then add it into currentAlbum if the conditions are satisfied
	 * @param e the ActionEvent to activate addPhoto()
	 */
	public void addPhoto(ActionEvent e) {
		FileChooser photoPicker = new FileChooser();
		photoPicker.setTitle("Please select an image to import");
		photoPicker.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png", "*.PNG"));	
		File chosenPicture = photoPicker.showOpenDialog(null);
		
		if (chosenPicture != null) {
			Image image = new Image(chosenPicture.toURI().toString());
			String name = chosenPicture.getName();
			Calendar photoDate = Calendar.getInstance();
			photoDate.setTimeInMillis(chosenPicture.lastModified());
			
			//photoDate.set(Calendar.MILLISECOND,0);
			Photo photoToBeAdded = new Photo(name, "", photoDate, chosenPicture.toURI().toString());
			
			for (Photo currentPhoto : currentAlbum.getPhotos()) {
				if ( (currentPhoto.getPhotoName().equals(photoToBeAdded.getPhotoName())) && (currentPhoto.getPhotoSource().equals(photoToBeAdded.getPhotoSource()))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error in Adding New Photo");
					//alert.setHeaderText("Photo Add Error.");
					alert.setContentText("This photo already exists in the album");

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
	 * Deletes a Photo from the current Album 
	 * @param e the ActionEvent to activate delPhoto()
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
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
	
	}
	/**
	 * Enables the moving of a Photo from this Album to another Album
	 * @throws IOException
	 */

	public void movePhoto(ActionEvent e) throws IOException {
		identifier=(Button) e.getSource();
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("No photo selected to move/transfer");
			errorAlert.showAndWait();
			return;
		}
		
	
		albumField.setVisible(true);
		confirmMove.setVisible(true);


	}
	/**
	 * Enables the copying of a Photo from this Album to another Album
	 * @throws IOException
	 */
	public void copyPhoto(ActionEvent e) throws IOException{
		identifier=(Button) e.getSource();
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("No photo selected to move/transfer");
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
			alert.setHeaderText("No photo selected to add tags");
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
	 * Deletes the currently selected Tag from the selected Photo.
	 * @param e the ActionEvent to activate delTag()
	 */
	public void delTag(ActionEvent e) {
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
			alert2.setHeaderText("No selected tag name-value to delete!");
			alert2.showAndWait();
		}
		
	}
	/**
	 * Selects the preceding Photo in the Album
	 * @param e the ActionEvent to activate prevPhoto
	 */
	public void prevPhoto(ActionEvent e) {
		//System.out.println("yuh");
		if (!photoList.getSelectionModel().isEmpty() && photoList.getSelectionModel().getSelectedIndex() != 0) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()-1);
			photoDetailV2();
		}
	}
	/**
	 * Selects the next Photo in the Album
	 * @param e the ActionEvent to activate nextPhoto
	 */
	public void nextPhoto(ActionEvent e) {
		//System.out.println("thank u next");
		if (!photoList.getSelectionModel().isEmpty() /*&& not greater than size of available list*/ ) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()+1);
			photoDetailV2();
		}
	}
	/**
	 * Confirms the addition of new Tags to the selected Photo should it meet the criteria
	 * @param e the ActionEvent to activate confirmAddTag()
	 */
	public void confirmAddTag(ActionEvent e) {
		if ((tagNameField.getText().trim().length()==0 || tagNameField.getText()==null) ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Tag name can't be empty! Please enter legitimate values");
			alert.showAndWait();
		}
		else {
	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add this tag name-value pair?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Photo selectedPhoto=photoList.getSelectionModel().getSelectedItem();
					Tag tag=new Tag(tagNameField.getText(),tagValueField.getText());
					List<Tag> photoTags=selectedPhoto.getPhotoTags();
					for (Tag ptag:photoTags) {
						if ((ptag.tagName.equals(tag.tagName)) && (ptag.tagValue.equals(tag.tagValue))) {
							Alert erroralert = new Alert(AlertType.ERROR);
							erroralert.setHeaderText("This tag name-value pair already exists!");
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
	 * Enables the captioning (or recaptioning) the currently selected Photo.
	 * @param e the ActionEvent to activate addCaption()
	 */
	public void addCaption(ActionEvent e) {
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No photo selected to add Caption");
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
	 * Confirms the captioning (or recaptioning) of the currently selected Photo
	 * @param e the ActionEvent to activate confirm()
	 */
	public void confirm(ActionEvent e) {
		
	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Set " + caption.getText() + " as caption for this picture?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
	
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
	
			
		//}
	}
	/**
	 * Cancels the current attempted caption/recaptioning of the currently selected Photo, and sets the caption to the last known caption
	 * @param e the ActionEvent to activate captionCancel()
	 */
	public void captionCancel(ActionEvent e) {
		caption.setEditable(false);
		confirmCaption.setVisible(false);
		captionCancel.setVisible(false);
		caption.setText(lastCaption);
	}

	/**
	 * Cancels the addition of a new Tag by enabling respective buttons
	 */
	public void tagCancel(ActionEvent e) {
		tagNameField.setVisible(false);
		tagValueField.setVisible(false);
		confirmTag.setVisible(false);
		cancelTag.setVisible(false);
	}
	/**
	 * Displays the current Album's Photos and their captions
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
	 * Confirms the moving of a Photo from one Album to another, using the identifier to determine whether to copy or move
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
							palert.setHeaderText("This photo is already present in the other album");
							palert.showAndWait();
							return;
						//	return "error";
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
			palert.setHeaderText("This album doesn't exist in your account");
			palert.showAndWait();
			albumField.clear();
			
		
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
