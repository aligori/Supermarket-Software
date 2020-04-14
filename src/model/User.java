package model;

import java.io.Serializable;

public abstract class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4880969810622843467L;
	
	private String username;
	private String password;
	private final int id;
	private static int nrOfUsers=0;
	private Level level;
	
	public User(String username, String password, Level level) {
		this.id = nrOfUsers++;
		this.username = username;
		this.password = password;
		this.level = level;
		
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public static int getNrOfUsers() { 
		return nrOfUsers;
	}
	
	public static void setNrOfUsers(int x) {
		User.nrOfUsers = x;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", id=" + id + "]";
	}


}
