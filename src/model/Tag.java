package src.model;
import java.util.*;
import java.io.*;

public class Tag implements Serializable {

	private static final long serialVersionUID = 3430529772963736249L;

	public String tagName;
	public String tagValue;
	//constructor for tag
	public Tag(String name, String value) {
		this.tagName=name;
		this.tagValue=value;
	}
	//get tag name
	public String getTagName() {
		return this.tagName;
	}
	//get tag value
	public String getTagValue() {
		return this.tagValue;
	}
	@Override
	public String toString() {
		return this.tagName + ":" + this.tagValue;
	}
}