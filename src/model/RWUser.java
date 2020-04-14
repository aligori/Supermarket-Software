package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class RWUser {
	private ArrayList<User> users;
	private static final String file="users.bin";
	private File fi;
	public RWUser() {
		users=new ArrayList<>();
		fi=new File(file);
		if(fi.exists()) {
			readUsers();
			setNr();
		} else {
			createFile();
		}
	}
	private void setNr() {
		int max=0;
		for(User i:users) if(max<i.getId()) max=i.getId();
		User.setNrOfUsers(max+1);
	}
	private void createFile() {
		System.out.println("First usage -> Please register Admin Data");
		Scanner in = new Scanner(System.in);
		System.out.print("Username: ");	String username=in.next();
		System.out.print("Password: ");	String password=in.next();
		System.out.print("Name: ");	String name=in.next();
		System.out.print("Surname: ");	String surname=in.next();
		users.add(new Admin(username, password, Level.ADMIN, name, surname));
		writeUsers();
		in.close();
	}
	private void writeUsers() {
		try {
			FileOutputStream fos=new FileOutputStream(fi);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(users);
			oos.close();fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File cannot be written!!!");
		} catch (IOException e) {
			System.err.println("Problem with writing object");
		}
	}
	private void readUsers() {
		try {
			FileInputStream fis=new FileInputStream(fi);
			ObjectInputStream ois=new ObjectInputStream(fis);
			users=(ArrayList<User>)ois.readObject();
			ois.close();fis.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!!");
		} catch (IOException e) {
			System.err.println("File is corrupted");
		} catch (ClassNotFoundException e) {
			System.err.println("Class does not match");
		}
		
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void add(User x) {
		users.add(x);
		writeUsers();
	}
	public void delete(User x) {
		users.remove(x);
		writeUsers();
	}
	public User getUserById(int id) {
		for(User i:users)
			if(i.getId()==id) return i;
		return null;
	}
	public User checkLogin(String user,String pass) {
		for(User i:users)
			if( ((Checker) i).checkUser(user, pass))
				return i;
		return null;
	}
	public boolean usernameInUse(String s) { //used when registering a new user.
		for(User x:users) {
			if(x.getUsername().equals(s))
				return true;
		}
		return false;
	}
	public void update() {
		writeUsers();
	}
	
	public void changePass(User user,String pass) {
		int i =getPosition(user);
		users.get(i).setPassword(pass);
		writeUsers();
	}
	public void edit(int position, String name, String surname, MyDate bday, String email, String phone, double salary) {
        if(users.get(position) instanceof Economist) {
        	((Economist)users.get(position)).setName(name);
            ((Economist)users.get(position)).setSurname(surname);
            ((Economist)users.get(position)).setBday(bday);
            ((Economist)users.get(position)).setEmail(email);
            ((Economist)users.get(position)).setPhone(phone);
            ((Economist)users.get(position)).setSalary(salary);
        }else if(users.get(position) instanceof Cashier) {
        	((Cashier)users.get(position)).setName(name);
            ((Cashier)users.get(position)).setSurname(surname);
            ((Cashier)users.get(position)).setBday(bday);
            ((Cashier)users.get(position)).setEmail(email);
            ((Cashier)users.get(position)).setPhone(phone);
            ((Cashier)users.get(position)).setSalary(salary);
        }
		writeUsers();
	}
	
	public int getPosition(User ed) {
		
		for(int i=0; i<users.size(); i++)
		{
			if(users.get(i).getId()==ed.getId())
				return i;
		}	
		return -1;
	}
	public ArrayList<Cashier> getCashiers(){
		ArrayList<Cashier> cashiers=new ArrayList();
		for(User x: users) {
			if(x instanceof Cashier) {
				cashiers.add((Cashier)x);
			}
		}
		return cashiers;
	}
}
