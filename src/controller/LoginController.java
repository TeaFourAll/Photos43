package controller;
import java.io.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

/**
 * class handles user control and logic for login screen
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class LoginController {
	/**
	 * text field for username
	 */
	@FXML
	TextField usernameTextField;
	/**
	 * button to login
	 */
	@FXML
	Button login;
	



	
	/**
	 * The main start method for this class
	 * @param mainStage this stage is executing
	 * @throws IOException IOException
	 */
	public void start(Stage mainStage) throws IOException{

	  
	 login.setOnAction(e -> {
		  try {
			  login();
			  mainStage.hide(); //if login works
		  }catch (IOException e1) {
			
		  }
	  });
	}
	/**
	 * checks to see if username is in list
	 * @throws IOException IOException
	 */
	public void login() throws IOException {
		String username=usernameTextField.getText();
		File data = new File("data/data.dat");

		if (!data.exists()) {
			try {
				data.createNewFile();
				User stock = new User("stock");
				Album stockAlbum = new Album("stockAlbum");
				for (int i = 0; i <5; i++) {
					File photoFile = new File("data/photos/samplePic" + Integer.toString(i+1) + ".jpg");
					if (photoFile != null) {
						photoSaveDetails(photoFile,stockAlbum);
					}
					
				}

				stock.getAlbums().add(stockAlbum);
				List<User> users= new ArrayList<>();
				users.add(stock);
				
				autoSave(users);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		
		try {
			FileInputStream fileInputStream = new FileInputStream("data/data.dat");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			List<User> users = (List<User>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();

			User user = null;

			for (User currentUser : users) {
				if (currentUser.getUsername().equals(username)) {
					user = currentUser;
					break;
				}
			}

			if (username.equals("admin") ) {
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Admin.fxml"));
					AnchorPane root = (AnchorPane)loader.load();
					AdminController adminController = loader.getController();
					adminController.start(stage,users);
					Scene scene = new Scene(root,350,400);
					stage.setScene(scene);
					stage.show();	
					
			}
		
			else if (user!=null) {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				UserController userController = loader.getController();
				userController.start(stage,user,users);
				Scene scene = new Scene(root,923,671);
				stage.setScene(scene);
				stage.show();	
			}
			else {

				Alert erroralert = new Alert(AlertType.ERROR);
				erroralert.setHeaderText("Error");
				erroralert.setContentText("Invalid username entered!! Please enter a valid username");
				erroralert.showAndWait();
				
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/view/Login.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				LoginController loginControl = loader.getController();
				Stage mainStage=new Stage();
				loginControl.start(mainStage);
				Scene scene = new Scene(root, 600, 400); //resize
				mainStage.setScene(scene);
				mainStage.setTitle("User Login");
				mainStage.show(); 

			
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
			
		}
	/**
	 * auto save admin data
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
	/**
	 * save photo details to album
	 * @param photoFile the file tobe saved
	 * @param stockAlbum album of stock photos
	 */
	public static void photoSaveDetails(File photoFile, Album stockAlbum) {
		String photoName = photoFile.getName();
		Calendar photoDate = Calendar.getInstance();
		photoDate.setTimeInMillis(photoFile.lastModified());
		Photo newStockPhoto = new Photo(photoName, "", photoDate, photoFile.toURI().toString());
		stockAlbum.getPhotos().add(newStockPhoto);
	}
		
		
		
		
		
	
	
}
