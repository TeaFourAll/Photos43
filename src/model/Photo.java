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
	 * The serial ID for this class
	 */
	private static final long serialVersionUID = 6955723612371190680L;
	/**
	 * the caption of the Photo
	 */
	public String photoCaption;
	/**
	 * the name of the Photo
	 */
	public String photoName;
	/**
	 * the date the Photo was taken
	 */
	public Calendar photoDate;
	/**
	 * the List of Tags tagged to the Photo
	 */
	public List<Tag> photoTags;
	/**
	 * the filepath of the Photo
	 */
	public String source;
  //	private File pics;

	/**
	 * Constructor with 4 arguments
	 * 
	 * @param name  the filename of the Photo
	 * @param caption  the caption of the Photo 
	 * @param date  the date the Photo was taken
	 * @param source  the filepath of the Photo
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
	 * gets the date the Photo was taken
	 * @return the date the Photo was taken
	 */
	public Calendar getPhotoDate() {
		return this.photoDate;
	}
	/**
	 * gets the List of Tags belonging to this Photo
	 * @return the List of Tags belonging to this Photo
	 */
	public List<Tag> getPhotoTags() {
		return this.photoTags;
	}
	/**
	 * gets the caption of this Photo
	 * @return the String caption for this Photo
	 */
	public String getPhotoCaption() {
		return this.photoCaption;
	}
	/**
	 * gets the name of this Photo
	 * @return the filename of this Photo
	 */
	public String getPhotoName(){
		return this.photoName;
	}
	/**
	 * sets the caption of the photo to specified String
	 * @param caption the caption to save under the Photo
	 */
	public void setPhotoCaption(String caption) {
		this.photoCaption=caption;
	}
	/**
	 * gets the filepath for this Photo
	 * @return String this.source the filepath for this Photo
	 */
	public String getPhotoSource(){
		return this.source;
	}
	@Override
	public String toString() {
		return this.getPhotoName();
	}
}
