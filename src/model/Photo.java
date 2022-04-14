package model;
import java.io.*;
import java.util.*;
/**
 * photo thats getting stored in the album
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class Photo implements Serializable {
	/**
	 * ID of class
	 */
	private static final long serialVersionUID = 6955723612371190680L;
	/**
	 * caption for photo
	 */
	public String photoCaption;
	/**
	 * name of photo
	 */
	public String photoName;
	/**
	 * date of photo
	 */
	public Calendar photoDate;
	/**
	 * list of tags for photo
	 */
	public List<Tag> photoTags;
	/**
	 * filepath of photo
	 */
	public String source;


	/**
	 * Constructor with 4 arguments
	 * 
	 * @param name filename of photo
	 * @param caption  caption of photo 
	 * @param date  date photo was taken
	 * @param source  filepath of photo
	 */
	public Photo(String name,String caption, Calendar date, String source) {
		this.photoName=name;
		this.photoCaption=caption;
		this.photoDate=date;
		this.photoTags=new ArrayList<>();
		this.photoDate.set(Calendar.MILLISECOND, 0);
		this.source = source;
	}
	/**
	 * gets date photo was taken
	 * @return date photo was taken
	 */
	public Calendar getPhotoDate() {
		return this.photoDate;
	}
	/**
	 * gets list of tags for this photo
	 * @return list of tags for this photo
	 */
	public List<Tag> getPhotoTags() {
		return this.photoTags;
	}
	/**
	 * gets caption of photo
	 * @return the caption for photo
	 */
	public String getPhotoCaption() {
		return this.photoCaption;
	}
	/**
	 * gets name of photo
	 * @return filename of photo
	 */
	public String getPhotoName(){
		return this.photoName;
	}
	/**
	 * sets caption of photo to new String
	 * @param caption caption to save under photo
	 */
	public void setPhotoCaption(String caption) {
		this.photoCaption=caption;
	}
	/**
	 * gets filepath for photo
	 * @return filepath for photo
	 */
	public String getPhotoSource(){
		return this.source;
	}
	@Override
	public String toString() {
		return this.getPhotoName();
	}
}
