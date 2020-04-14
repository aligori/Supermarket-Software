package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Admin;
import model.Economist;
import model.RWUser;
import model.User;
import model.ValidateInput;

public class NewPassword {
    User currentUser;
    
	public NewPassword(User user) {
		currentUser = user;
	}

	public void show(Stage st) {
	 Label currPass = new Label("Current Password");
	 Label newPass = new Label("New Password");
	 Label confirm = new Label("Confirm password");
	 
	 TextField tfcurr = new TextField();
	 TextField tfpass = new TextField();
	 TextField tfconfirmPass = new TextField();
	 
	 Button change = new Button("Change");
	 Button back = new Button("Back");
	 back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
	 
	 HBox hb = new HBox();
	 hb.setSpacing(10);
	 hb.getChildren().addAll(change,back);
	 GridPane gp  =new GridPane();
	 gp.setAlignment(Pos.CENTER);
	 gp.setHgap(20);
	 gp.setVgap(10);
	 gp.addColumn(0, currPass, newPass, confirm);
	 gp.addColumn(1, tfcurr, tfpass, tfconfirmPass);
	 gp.add(hb, 1, 4);
	 gp.setId("vbox");
	 BorderPane bp = new BorderPane();
	 bp.setCenter(gp);
	 bp.setPadding(new Insets(140, 100, 140, 100));
	 Scene sc = new Scene(bp,700,600);
	 sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	 st.setTitle("Change Password");
	 st.setScene(sc);
	 
	 back.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			if(currentUser instanceof Admin) {
				(new ViewAdmin(currentUser)).view(st);
			}else if(currentUser instanceof Economist) {
				(new EconomistView(currentUser)).view(st);
			}else {
				(new CashierView(currentUser)).view(st);
			}
			
		}
	});
	 change.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			boolean validCurrent=false;
			boolean validPass=false;
			boolean validConfirm=false;
			
			if((tfcurr.getText().toString()).isEmpty()) {
				tfcurr.setPromptText("Please enter password");
				tfcurr.setStyle("-fx-prompt-text-fill: red");
			}else {
				if(currentUser.getPassword().matches(tfcurr.getText().toString())) {
				     validCurrent=true;
			    }else {
				     tfcurr.clear();
				     tfcurr.setPromptText("Invalid Password");
				     tfcurr.setStyle("-fx-prompt-text-fill: red");
			    }
		    }
			if((tfpass.getText().toString()).isEmpty()) {
				tfpass.setPromptText("Please enter password");
				tfpass.setStyle("-fx-prompt-text-fill: red");
			}else {
				if(new ValidateInput().checkPass(tfpass.getText().toString())) {
					validPass = true;
				}else {
					tfpass.clear();
					tfpass.setPromptText("Invalid Password");
					tfpass.setStyle("-fx-prompt-text-fill: red");
				}
			}
			if((tfconfirmPass.getText()).isEmpty()) {
				tfconfirmPass.setPromptText("Confirm password");
				tfconfirmPass.setStyle("-fx-prompt-text-fill: red");
			}
			
			if(!(tfconfirmPass.getText()).isEmpty() && !(tfpass.getText()).isEmpty()) {
				if(!(tfconfirmPass.getText().toString()).matches(tfpass.getText())){
			    tfconfirmPass.clear();
				tfconfirmPass.setPromptText("Password doesn't match");
				tfconfirmPass.setStyle("-fx-prompt-text-fill: red");
				}else {
					validConfirm=true;
				}
			}
			RWUser rwu = new RWUser();
			if(validCurrent && validPass && validConfirm) {
				rwu.changePass(currentUser, tfpass.getText());
				if(currentUser instanceof Admin) {
					(new ViewAdmin(currentUser)).view(st);
				}else if(currentUser instanceof Economist) {
					(new EconomistView(currentUser)).view(st);
				}else {
					(new CashierView(currentUser)).view(st);
				}
				new Alert(AlertType.INFORMATION, "Password Changed",ButtonType.OK).show();
			}else {
				System.out.println("Password not changed.");
			}
					
			
		}
	});
		
	}

}
