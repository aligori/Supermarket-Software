package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.RWCategory;
import model.User;

public class EconomistView {
    User currentUser;
    
	public EconomistView(User currentUser) {
		this.currentUser = currentUser;
	}

	public void view(Stage st) {
		BorderPane mainPane = new BorderPane();

		ArrayList<Menu> items = new ArrayList<Menu>();
		
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: linear-gradient(#cee9fb,#90CCF4);");

		Label changepass = new Label("Change Password");
		Label logout = new Label("Log Out");
		Label stock = new Label("Stock");
		
		
		Menu stocku = new Menu("",stock);
		Menu supplier = new Menu("Suppliers");
		Menu pass = new Menu("",changepass);		
		Menu out = new Menu("",logout);

		MenuItem register = new MenuItem("Register Suppliers");
		MenuItem viewSuppliers = new MenuItem("View Suppliers");
		
		items.add(stocku);
		items.add(supplier);
		items.add(pass);
		items.add(out);

	    menuBar.getMenus().addAll(items);
		
		register.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new AddSupplier(currentUser)).show(st,1);
			}
		});
		
		viewSuppliers.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewSuppliers(currentUser)).show(st);
			}
		});
		
		stock.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				
				(new viewStock(currentUser)).show();
				
			}
		});
		supplier.getItems().addAll(register, viewSuppliers);

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
			
		

		VBox vb=new VBox();
		
		Button viewSup=new Button("View Suppliers");
		Button category=new Button("Add Category");
		Button purchase=new Button("Purchase Quantity");
		Button cstatistic=new Button("Cashier Statistics");
		Button performance=new Button("Performance");
		Button sellsbuys = new Button("Sales and Purchases");
		
		viewSup.getStyleClass().add("biggerbutton");
		category.getStyleClass().add("biggerbutton");
		purchase.getStyleClass().add("biggerbutton");
		cstatistic.getStyleClass().add("biggerbutton");
		performance.getStyleClass().add("biggerbutton");
		sellsbuys.getStyleClass().add("biggerbutton");
		
		viewSup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new ViewSuppliers(currentUser)).show(st);
				
			}
			
		});
		
		
		category.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showAddCategory();
				
			}

		});
		
	    purchase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new PurchaseQuantity(currentUser)).show(st);
				
			}
		});
		
		cstatistic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			  (new EnterDates(currentUser)).show();
				
			}
		});
		
		performance.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			  (new Performance(currentUser)).show(st);
				
			}
		});
		
		sellsbuys.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new EnterDates(currentUser)).view3Options();
				
			}
		});
		
		vb.getChildren().addAll(viewSup,purchase,category,performance,cstatistic,sellsbuys);
		vb.setId("bg");
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(10);
		vb.setPadding(new Insets(30,250, 30, 250));
		VBox.setVgrow(viewSup, Priority.ALWAYS);
		VBox.setVgrow(purchase, Priority.ALWAYS);
		VBox.setVgrow(category, Priority.ALWAYS);
		VBox.setVgrow(performance, Priority.ALWAYS);
		VBox.setVgrow(cstatistic, Priority.ALWAYS);
		VBox.setVgrow(sellsbuys, Priority.ALWAYS);
		
		viewSup.setMaxWidth(Double.MAX_VALUE);
		purchase.setMaxWidth(Double.MAX_VALUE);
		category.setMaxWidth(Double.MAX_VALUE);
		performance.setMaxWidth(Double.MAX_VALUE);
		cstatistic.setMaxWidth(Double.MAX_VALUE);
		sellsbuys.setMaxWidth(Double.MAX_VALUE);

		
		mainPane.setCenter(vb);
		mainPane.setTop(menuBar);
		
		Scene scene = new Scene(mainPane,700,500);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		st.setScene(scene);
		st.setResizable(false);
		st.setTitle("Economist View");
		
	}
	
	private void showAddCategory() {
			Stage addC = new Stage();
			addC.initModality(Modality.APPLICATION_MODAL);
			
			Label newC = new Label("New Category: ");
			
			TextField tfcategory = new TextField();
			
			Button add = new Button("Add");
			Button cancel = new Button("Cancel");
			HBox hb = new HBox();
			hb.setSpacing(10);
			hb.getChildren().addAll(add,cancel);
			
			GridPane gp = new GridPane();
			gp.setHgap(10);
			gp.setVgap(20);
			gp.setAlignment(Pos.CENTER);
			gp.addRow(0, newC, tfcategory);
            gp.add(hb, 1, 1);
			
			Scene scene = new Scene(gp,400,300);
			addC.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			addC.setTitle("Add New Category");
			addC.show();
			addC.setResizable(false);
			
			add.setOnAction(e -> {
				RWCategory rwc = new RWCategory();
				if(tfcategory.getText().toString().matches("[A-Z][a-z]*\\s?[a-z]*")) {
				    rwc.addCategory(new Category(tfcategory.getText()));
				    addC.close();
				    new Alert(AlertType.CONFIRMATION,"Category added! Go and add Products!", ButtonType.OK).show();
				}else {
					tfcategory.clear();
					tfcategory.setPromptText("Invalid Category");
					tfcategory.setStyle("-fx-prompt-text-fill: red");
				}
			});
			
			cancel.setOnAction(e -> {
				addC.close();
			});
		}

}
