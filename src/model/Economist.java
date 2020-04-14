package model;

public class Economist extends User implements Checker {

	private String name;
	private String surname;
    private MyDate bday;
    private String email;
	private String phone;
	private double salary;
	
	public Economist(String username, String password, Level level, String name,String surname, MyDate bday, String email,
			String phone, double salary) {
		super(username, password, level);
		this.name = name;
		this.surname=surname;
		this.bday = bday;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
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

	public MyDate getBday() {
		return bday;
	}

	public void setBday(MyDate bday) {
		this.bday = bday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public boolean checkUser(String username, String password) {
		return username.equals(this.getUsername())&&password.equals(this.getPassword());
	}
		
}
