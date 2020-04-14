package model;

public class Admin extends User implements Checker {
	private String name;
	private String surname;

	public Admin(String username, String password, Level level, String name, String surname) {
		super(username, password, level);
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public boolean checkUser(String username, String password) {
		return username.equals(this.getUsername())&&password.equals(this.getPassword());
	}
    
}
