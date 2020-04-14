package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.RWSuppliers;
import model.User;
import model.ValidateInput;

public class AddSupplier {
    User currentUser;
    int returnView=0;
    
	public AddSupplier(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void show(Stage st) {
		
		Label name = new Label("Name");
		Label address = new Label("Address");
		Label email = new Label("E-mail");
		Label phone = new Label("Mobile");
		
		TextField tfname = new TextField();
		TextField tfaddress = new TextField();
		TextField tfEmail = new TextField();
		TextField tfphone = new TextField();


		tfphone.setTooltip(new Tooltip("Phone should be in the format 069 XX XX XXX or 069XXXXXXX or +355 XX XX XX XXX."));
		
				
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10,10,10,10));
		gp.addColumn(0,name, address, email, phone);
		gp.addColumn(1,tfname, tfaddress, tfEmail, tfphone);
		
		Button next = new Button("Next"); //kur klikon next te kerkon qe te besh add first product
		Button back = new Button("Cancel");
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
		hb.getChildren().addAll(next,back);
		gp.add(hb, 1, 4);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10,10,10,10));
		gp.setAlignment(Pos.CENTER);

		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setId("vbox");
		vb.getChildren().add(gp);
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(100, 130, 100, 130));
		bp.setCenter(vb);
        Scene scene = new Scene(bp,600,500);
		st.setScene(scene);
		st.setTitle("Add Supplier");
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(returnView==0) {
					(new ViewSuppliers(currentUser)).show(st);
				}else if(returnView==1) {
						(new EconomistView(currentUser)).view(st);
				}
			}
				
		});
		
		next.setOnAction(new EventHandler<ActionEvent>() {
     
			@Override
			public void handle(ActionEvent event) {
				RWSuppliers rws = new RWSuppliers();
				boolean validname=false;
				boolean validaddress=false;
				boolean validemail=false;
				boolean validphone=false;
				
				if((tfname.getText()).isEmpty()) {
					tfname.setPromptText("Please enter supplier");
					tfname.setStyle("-fx-prompt-text-fill: red");
				}else {
						if(rws.supplierRegistered(tfname.getText().toString())) {
							tfname.clear();
							tfname.setPromptText("Username in use");
							tfname.setStyle("-fx-prompt-text-fill: red");
						}else {
							validname=true;
						}
				}
				
				if((tfaddress.getText()).isEmpty()) {
					tfaddress.setPromptText("Please enter Address");
					tfaddress.setStyle("-fx-prompt-text-fill: red");
				}else {
						validaddress=true;
				}
				
				if((tfEmail.getText()).isEmpty()) {
					tfEmail.setPromptText("Please enter E-mail");
					tfEmail.setStyle("-fx-prompt-text-fill: red");
				}else {
					if(!(new ValidateInput().checkEmail(tfEmail.getText().toString()))){
						tfEmail.clear();
						tfEmail.setPromptText("Invalid email");
						tfEmail.setStyle("-fx-prompt-text-fill: red");
					}else {
						validemail=true;
					}
				}
				if((tfphone.getText()).isEmpty()) {
					tfphone.setPromptText("Please enter mobile phone");
					tfphone.setStyle("-fx-prompt-text-fill: red");
				}else {
					if(new ValidateInput().checkPhone(tfphone.getText().toString())) {
						validphone = true;
					}else {
						tfphone.clear();
						tfphone.setPromptText("Invalid phone");
						tfphone.setStyle("-fx-prompt-text-fill: red");
					}
				}
				if(validname && validaddress && validemail && validphone) {
					(new AddProduct(currentUser,tfname.getText(),tfaddress.getText(),tfEmail.getText(),tfphone.getText())).show(st);
				}
			}
			
		});
		
	}

	public void show(Stage st, int i) {
		returnView=1;
		show(st);
	}

}
