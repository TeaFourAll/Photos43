package model;

import java.io.*;
import java.util.*;
/**
 * each user w their own albums
 * @author Michelle Dong
 * @author Julio Johnson Lopez
 *
 */
public class User implements Serializable {
	/**
	 * The serial ID for this class
	 */
	private static final long serialVersionUID = -7351729135012380019L;
	/**
	 * the username of the User
	 */
	String username;
	/**
	 * The List of Albums belonging to the User
	 */
	List<Album> userAlbums;
	
	/**
	 * Constructor with 1 argument
	 * @param user the name of the User
	 */
	public User(String user) {
		this.username=user;
		this.userAlbums=new ArrayList<>();
	}

	/**
	 * gets the username of the User
	 * @return the String username of the User
	 */

	public String toString() {
		return this.getUsername();
	}
	/**
	 * gets the username for this User
	 * @return String the username for this User
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * gets the List of Albums belonging to this User
	 * @return the List of Albums belonging to this User
	 */
	public List<Album> getAlbums(){
		return this.userAlbums;
	}

}
