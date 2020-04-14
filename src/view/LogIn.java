package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Admin;
import model.Cashier;
import model.Economist;
import model.Product;
import model.RWProduct;
import model.RWUser;
import model.User;

public class LogIn extends Application{

	@Override
	public void start(Stage st) throws Exception {
		
		Label login = new Label("Login");
		login.setId("h1");
		Text empty = new Text("Please fill in the empty boxes!");
		empty.setVisible(false);
		Text invalid = new Text("The credentials you entered are not correct!");
		invalid.setId("text");
		invalid.setVisible(false);
		
		GridPane gp=new GridPane();
		gp.setAlignment(Pos.CENTER);
		
		TextField userfield=new TextField();
		userfield.getStyleClass().add("text-field1");
		userfield.setPromptText("Enter username");
		
		PasswordField passfield=new PasswordField();
		passfield.getStyleClass().add("text-field1");
		passfield.setPromptText("Password");

		gp.add(userfield, 1, 0);
		gp.add(passfield, 1, 1);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		
		//Buttons
		HBox hb=new HBox();
		Button ok=new Button("Log in ");
		ok.getStyleClass().add("button2");
		Button cancel=new Button("Cancel");
		cancel.getStyleClass().add("button1");
		
		//Events
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ev) {
				System.exit(0);
			}
		});
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RWUser rwu=new RWUser();
				String username=userfield.getText();
				String password=passfield.getText();
				User perdoruesi=rwu.checkLogin(username, password);
				if(username.equals("") || password.equals("")){
					invalid.setVisible(false);
			        empty.setVisible(true);
		        }else if(perdoruesi instanceof Admin) {
			        (new ViewAdmin(perdoruesi)).view(st);
					//System.out.println("Admin loged in");
				} else if(perdoruesi instanceof Economist) {
					//Economist View
					(new EconomistView(perdoruesi)).view(st);
					RWProduct rwp = new RWProduct();
					String s="";
					for(Product i: rwp.getProducts()) {
						if(i.getStock()<5) {
							s+=i.getStock()+" "+i.getName()+" left in stock\n";
						}
					}
					if(!s.equals("")) {
						Alert al = new Alert(AlertType.WARNING, s, ButtonType.OK);
						al.show();
					}
					System.out.println("Economist loged in");
				}else if(perdoruesi instanceof Cashier) {
					//Cashier View
					System.out.println("Cashier loged in");
				    (new CashierView(perdoruesi)).view(st);
				} else {
					empty.setVisible(false);
					invalid.setVisible(true);
				}
				
			}
		});
		
		
		hb.getChildren().addAll(ok,cancel);
		hb.setSpacing(5);
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(10,10,10,10));
		
		VBox vb=new VBox();
		vb.getChildren().addAll(login,gp,hb,invalid,empty);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(10,10,10,10));
		vb.setId("vbox");
		
		BorderPane pane=new BorderPane();
		pane.setPadding(new Insets(120, 200, 120, 200));
		pane.setCenter(vb);

		Scene sc=new Scene(pane,700,500);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		pane.setStyle("-fx-background-image: url('images/skynews-germany-food_4115739.jpg');"
				+ "-fx-background-repeat: stretch;");
		st.setScene(sc);
		st.setTitle("SoftMarket");
		st.show();	
	}
}
