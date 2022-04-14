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
	 * ID of class
	 */
	private static final long serialVersionUID = -4143935150417416554L;
	/**
	 * name of album
	 */
	public String albumName;
	/**
	 * list of photos
	 */
	public List<Photo> photos;

	/**
	 * constructor with 1 argument
	 * @param name of album
	 */
	public Album(String name) {
		this.albumName=name;
		this.photos=new ArrayList<>();
	}

	/**
	 * gets name of album
	 * @return Album name
	 */
	public String getAlbumName() {
		return this.albumName;
	}

	/**
	 * gets the list of photos in this album
	 * @return the list of photos
	 */
	public List<Photo> getPhotos(){
		return this.photos;
	}
	/**
	 * gets the size of this album
	 * @return the size of the photo list
	 */
	public int getAlbumSize() {
		return this.photos.size();
	}
	/**
	 * adds a photo into the list of photos
	 * @param photo the photo being added to album
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	@Override
	public String toString() {
		return this.albumName;
	}
	

	
	

}
