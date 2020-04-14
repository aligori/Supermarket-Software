package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Admin;
import model.Cashier;
import model.Economist;
import model.RWUser;
import model.User;

public class ViewUsers {
    User currentUser;
    int viewType=1; //1 to view all employers, 2 for economists only, and 3 for Cashiers
    
	public ViewUsers(User currentUser) {
		this.currentUser = currentUser;
	}
    
	public void show(Stage st) {
		RWUser rwu =new RWUser();
		ObservableList<User> users = FXCollections.observableArrayList(rwu.getUsers());
		if(viewType==1) { //remove the admin
			st.setTitle("Employees");
			ArrayList<User> employees=new ArrayList();
			for(User x: rwu.getUsers()) {
				if(x instanceof Admin) ;
				else
					employees.add(x);				
			}
			users= FXCollections.observableArrayList(employees);
		}else if(viewType==2) {
			st.setTitle("Economists");
			ArrayList<Economist> economists=new ArrayList();
			for(User x: rwu.getUsers()) {
				if(x instanceof Economist) {
					economists.add((Economist)x);
				}
			}
			users= FXCollections.observableArrayList(economists);
		}else if(viewType==3) {
			st.setTitle("Cashiers");
			ArrayList<Cashier> cashiers=new ArrayList();
			for(User x: rwu.getUsers()) {
				if(x instanceof Cashier) {
					cashiers.add((Cashier)x);
				}
			}
			users= FXCollections.observableArrayList(cashiers);
		}
		TableView table = new TableView();
		
		TableColumn id=new TableColumn("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn username = new TableColumn("Username");
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		TableColumn name=new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn surname=new TableColumn("Surname");
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		
		TableColumn birthday=new TableColumn("Birthday");
		birthday.setCellValueFactory(new PropertyValueFactory<>("bday"));
		
		TableColumn role=new TableColumn("Role");
		role.setCellValueFactory(new PropertyValueFactory<>("level"));
		
		TableColumn salary=new TableColumn("Salary");
		salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		
		TableColumn email = new TableColumn("E-mail");
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		TableColumn phone = new TableColumn("Mobile");
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		table.setItems(users);
		table.setEditable(true);
		table.getColumns().addAll(id,username,name,surname,birthday,role,salary,email,phone);
		
		Text err  = new Text("Please select an employee!");
		err.setVisible(false);
		Button add = new Button("Add");
		Button edit = new Button("Edit");
		Button delete = new Button("Delete");
		Button back = new Button("Back");
		HBox hb = new HBox();
		hb.getChildren().addAll(add,edit,delete,back,err);
		hb.setPadding(new Insets(10,10,10,10));
		hb.setSpacing(10);
		
		BorderPane bp=new BorderPane();
		bp.setCenter(table);
		bp.setBottom(hb);
	    bp.setStyle("-fx-background-color: #eef1fd;");
		Scene sc=new Scene(bp,800,600);
		st.setScene(sc);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new AddUserView(currentUser)).show(st,viewType);
			}
			
		});
		edit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(table.getSelectionModel().getSelectedItems().isEmpty()) {
					err.setVisible(true);
				}else {
					User emp = (User) table.getSelectionModel().getSelectedItem();
					(new EditUserView(currentUser,emp)).show(st,viewType);	
			    }
			}			
		});
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(table.getSelectionModel().getSelectedItems().isEmpty()) {
					err.setVisible(true);
				}else {
					rwu.delete((User)table.getSelectionModel().getSelectedItem());
					if(viewType==1)
						(new ViewUsers(currentUser)).show(st);
					else if(viewType==2)
						(new ViewUsers(currentUser)).showEconomists(st);
					else
						(new ViewUsers(currentUser)).showCashiers(st);
				}
				
			}
			
		});
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewAdmin(currentUser)).view(st);
				
			}
		});
	}
	
    public void showEconomists(Stage st) {
    	viewType=2;
    	show(st);
    }
    public void showCashiers(Stage st) {
    	viewType=3;
    	show(st);
    }
}
