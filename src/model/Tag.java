package model;
import java.io.*;
/**
 * <name, value> tags for each photo
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class Tag implements Serializable {
	/**
	 * ID of class
	 */
	private static final long serialVersionUID = 3430529772963736249L;
	/**
	 * name of tag
	 */
	public String tagName;
	/**
	 * value of tag
	 */
	public String tagValue;
	/**
	 * Constuctor with 2 arguments
	 * @param name name of tag
	 * @param value value of tag
	 */
	public Tag(String name, String value) {
		this.tagName=name;
		this.tagValue=value;
	}
	/**
	 * gets name of tag
	 * @return name of tag
	 */
	public String getTagName() {
		return this.tagName;
	}
	/**
	 * gets value of tag
	 * @return the value of tag
	 */
	public String getTagValue() {
		return this.tagValue;
	}
	@Override
	public String toString() {
		return this.tagName + ":" + this.tagValue;
	}
}
