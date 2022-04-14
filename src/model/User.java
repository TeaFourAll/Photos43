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
	 * ID of class
	 */
	private static final long serialVersionUID = -7351729135012380019L;
	/**
	 * username
	 */
	String username;
	/**
	 * list of albums
	 */
	List<Album> userAlbums;
	
	/**
	 * Constructor with 1 argument
	 * @param user name of User
	 */
	public User(String user) {
		this.username=user;
		this.userAlbums=new ArrayList<>();
	}

	/**
	 * gets username
	 * @return username
	 */

	public String toString() {
		return this.getUsername();
	}
	/**
	 * gets username
	 * @return username 
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * gets list of albums for user
	 * @return list of albums for user
	 */
	public List<Album> getAlbums(){
		return this.userAlbums;
	}

}
