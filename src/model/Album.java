package model;
import java.io.*;
import java.util.*;
/**
 * album with the photos
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class Album implements Serializable {
	
	/**
	 * The serial ID of the class
	 */
	private static final long serialVersionUID = -4143935150417416554L;
	/**
	 * the name of the Album
	 */
	public String albumName;
	/**
	 * the list of photos in this Album
	 */
	public List<Photo> photos;
	/**
	 * constructor with 1 argument
	 * @param name of the Album
	 */
	public Album(String name) {
		this.albumName=name;
		this.photos=new ArrayList<>();
	}
	/**
	 * gets the name of the Album
	 * @return Album name
	 */
	public String getAlbumName() {
		return this.albumName;
	}
	/**
	 * gets the list of Photos in this Album
	 * @return the list of Photos
	 */
	public List<Photo> getPhotos(){
		return this.photos;
	}
	/**
	 * gets the size of this Album
	 * @return the length of the Photo list
	 */
	public int getAlbumSize() {
		return this.photos.size();
	}
	/**
	 * adds a Photo object into the list of Photos
	 * @param photo the photo to be added to the Album
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	@Override
	public String toString() {
		return this.albumName;
	}
	

	
	

}
