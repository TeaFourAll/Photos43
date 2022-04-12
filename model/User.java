package model;

import java.io.*;
import java.util.*;

public class User implements Serializable {

	private static final long serialVersionUID = -7351729135012380019L;

	String username;

	List<Album> userAlbums;
	

	public User(String user) {
		this.username=user;
		this.userAlbums=new ArrayList<>();
	}

	public String toString() {
		return this.getUsername();
	}

	public String getUsername() {
		return this.username;
	}

	public List<Album> getAlbums(){
		return this.userAlbums;
	}

}