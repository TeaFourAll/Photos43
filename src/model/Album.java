package src.model;
import java.io.*;
import java.util.*;

public class Album implements Serializable {
	
	//The serial ID of the class
	private static final long serialVersionUID = -4143935150417416554L;
	public String albumName;
	public List<Photo> photos;
	public String getAlbumName() {
		return this.albumName;
	}
	//constructor with 1 argument of the name
	public Album(String name) {
		this.albumName=name;
		this.photos=new ArrayList<>();
	}

	// geter for list of Photos in this Album
	public List<Photo> getPhotos(){
		return this.photos;
	}
	//geter for size of this Album
	public int getAlbumSize() {
		return this.photos.size();
	}
	//add Photo object into the list of Photos

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	@Override
	public String toString() {
		return this.albumName;
	}
	

	
	

}