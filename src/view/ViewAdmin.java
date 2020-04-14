package view;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import sun.nio.cs.Surrogate;

public class ViewAdmin {
	User currentUser;
	
	public ViewAdmin(User currentUser) {
		this.currentUser = currentUser;
	}

	public void view(Stage st) {
		
		BorderPane mainPane = new BorderPane();
		Accordion panes = new Accordion(); 
		panes.getStyleClass().add("titledpane");

		ArrayList<TitledPane> tipanes = new ArrayList<TitledPane>();
		ArrayList<Menu> items = new ArrayList<Menu>();
		
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: linear-gradient(#cee9fb,#90CCF4);");

		Label changepass = new Label("Change Password");
		Label logout = new Label("Log Out");
		
		Menu out = new Menu("",logout);
		Menu pass = new Menu("",changepass);
		Menu employee = new Menu("Employees");
		
		MenuItem register = new MenuItem("Add Employee");
		MenuItem viewUsers = new MenuItem("View Employees");
		MenuItem statistics= new MenuItem("Statistics");
		
		changepass.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				
				(new NewPassword(currentUser)).show(st);
				
			}
		});
		logout.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				try {
					(new LogIn()).start(st);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		
		
		register.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			    (new AddUserView(currentUser)).show(st, 1);;
			}
		});
		
		viewUsers.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewUsers(currentUser)).show(st);
			}
		});
		
		statistics.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new EnterDates(currentUser)).view3Options();
			}
		});
		
		
		employee.getItems().addAll(register, viewUsers, statistics);
		
		items.add(employee);
		items.add(pass);
		items.add(out);

	    menuBar.getMenus().addAll(items);

		VBox vb=new VBox();
		Button viewAll=new Button("View All");
		viewAll.getStyleClass().add("biggerbutton");
		
		viewAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewUsers(currentUser)).show(st);
				
			}
			
		});
	
		Button ec=new Button("Economists");
		ec.getStyleClass().add("biggerbutton");
		
		ec.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewUsers(currentUser)).showEconomists(st);
				
			}
			
		});
		Button cash=new Button("Cashiers");
		cash.getStyleClass().add("biggerbutton");
		
		cash.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewUsers(currentUser)).showCashiers(st);
				
			}
			
		});
		vb.getChildren().addAll(viewAll,ec,cash);
		vb.setId("bg");
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(10);
		TitledPane emp=new TitledPane("Manage Employees",vb); 
		tipanes.add(emp);
		
		
		
		VBox vb1=new VBox();

		Button sup=new Button("Suppliers");
        sup.getStyleClass().add("biggerbutton");
		
		sup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
                (new ViewSuppliers(currentUser)).show(st);
			}
			
		});
	
		Button buy=new Button("Purchase products");
		buy.getStyleClass().add("biggerbutton");
		
		buy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
		        (new PurchaseQuantity(currentUser)).show(st);
				
			}
			
		});
		Button stock=new Button("View stock");
		stock.getStyleClass().add("biggerbutton");
		
		stock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			(new viewStock(currentUser)).show();
				
			}
			
		});

		
		vb1.getChildren().addAll(sup,buy,stock);
		vb1.setAlignment(Pos.CENTER);
		vb1.setSpacing(10);
		
		TitledPane prod=new TitledPane("Products",vb1);
		tipanes.add(prod);
		
		VBox vb2=new VBox();
		
		Button statistic1=new Button("Sales and Purchases");
		statistic1.getStyleClass().add("biggerbutton");
		
		statistic1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new EnterDates(currentUser)).view3Options();
			}
			
		});
		
		Button statistic2=new Button("View Profit");
		statistic2.getStyleClass().add("biggerbutton");
		
		statistic2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				
				Label labelyear = new Label("Enter year");
			
				TextField tfyear = new TextField();
				tfyear.setPromptText("Enter Year");
				Button ok = new Button("Submit");
				
				GridPane gp = new GridPane();
				gp.addColumn(0,labelyear, tfyear,ok);
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(10);gp.setVgap(10);
				Scene scene = new Scene(gp,400,200);
				scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
				stage.setScene(scene);	
				stage.setTitle("Monthly Summaries");
				stage.show();
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
							if(tfyear.getText().toString().matches("20\\d\\d")) {
								int year = Integer.parseInt(tfyear.getText().toString());
								System.out.println(Integer.parseInt(tfyear.getText().toString()));
								(new ViewProfitChart(year)).show(stage);
							}else {
								tfyear.clear();
								tfyear.setPromptText("Invalid year");
								tfyear.setStyle("-fx-prompt-text-fill: red");
							}
						
						
					}
				});
			}
			
		});

		
	
		vb2.getChildren().addAll(statistic1,statistic2);
		vb2.setAlignment(Pos.CENTER);
		vb2.setSpacing(10);
		
		TitledPane sales=new TitledPane("Sales",vb2);
		tipanes.add(sales);
		
		
		
		panes.getPanes().addAll(tipanes);
		panes.setExpandedPane(emp);
		mainPane.setCenter(panes);
		mainPane.setTop(menuBar);
		
		Scene scene = new Scene(mainPane,700,500);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		st.setScene(scene);
		st.setTitle("Admin View");
		
	}

}
