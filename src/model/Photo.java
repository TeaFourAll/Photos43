package src.model;
import java.io.*;
import java.util.*;
public class Photo implements Serializable {

	private static final long serialVersionUID = 6955723612371190680L;

	public String photoCaption;
	public String photoName;
	public Calendar photoDate;
	public List<Tag> photoTags;

	public String source;
  //	private File pics;

	//constructor
	public Photo(String name,String caption, Calendar date, String source) {
        this.photoCaption=caption;
        this.photoName=name;
        this.photoDate=date;
        this.photoDate.set(Calendar.MILLISECOND, 0);
		this.photoTags=new ArrayList<>();
		this.source = source;
	}
	//photo date getter
	public Calendar getPhotoDate() {
		return this.photoDate;
	}
	//photo tag getter
	public List<Tag> getPhotoTags() {
		return this.photoTags;
	}
	//photo caption getter
	public String getPhotoCaption() {
		return this.photoCaption;
	}
	//photo name getter
	public String getPhotoName(){
		return this.photoName;
	}
	//photo caption setter
	public void setPhotoCaption(String caption) {
		this.photoCaption=caption;
	}
	//photo filepath getter
	public String getPhotoSource(){
		return this.source;
	}
	@Override
	public String toString() {
		return this.getPhotoName();
	}
}