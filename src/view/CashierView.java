package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cashier;
import model.User;

public class CashierView {
    User currentUser;
    
	public CashierView(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void view(Stage st) {
		BorderPane mainPane = new BorderPane();
        ArrayList<Menu> menus = new ArrayList<Menu>();
		
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: linear-gradient(#cee9fb,#90CCF4);");
		
		Label changepass = new Label("Change Password");
		Label logout = new Label("Log Out");
		
		Menu pass = new Menu("",changepass);		
		Menu out = new Menu("",logout);
		
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
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menus.add(pass);
		menus.add(out);

	    menuBar.getMenus().addAll(menus);
	    
		Label h = new Label("Welcome "+ ((Cashier)currentUser).getName());
		h.setAlignment(Pos.CENTER);
		h.setStyle("-fx-font-size:25px");
	    Button prepare = new Button("Prepare Bill");
		Button log = new Button("Log Out");
		log.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
		prepare.getStyleClass().add("biggerbutton");
		log.getStyleClass().add("biggerbutton");
		
		VBox vb = new VBox();
		vb.setSpacing(15);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(h,prepare,log);
		mainPane.setCenter(vb);
		mainPane.setTop(menuBar);
		
		Scene scene = new Scene(mainPane,700,500);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		st.setScene(scene);
		st.setTitle("Cashier View");
		
		prepare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				(new PrepareBill(currentUser)).show(st);
			}
		});
		log.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					(new LogIn()).start(st);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
