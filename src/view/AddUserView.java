package view;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Cashier;
import model.Economist;
import model.Level;
import model.MyDate;
import model.RWUser;
import model.User;
import model.ValidateInput;

public class AddUserView {
    User currentUser;
    
	public AddUserView(User currentUser) {
		this.currentUser = currentUser;
	}

	public void show(Stage st, int viewType) {
		
		Label level = new Label("Role");
		Label uname = new Label("Username");
		Label pass = new Label("Password");
		Label confirmPass = new Label("Confirm Password");
		Label name = new Label("Name");
		Label surname = new Label("Surname");
		Label birthday = new Label("Birthday");
		Label email = new Label("E-mail");
		Label phone = new Label("Mobile");
		
		ChoiceBox chooselevel = new ChoiceBox(FXCollections.observableArrayList("Economist", "Cashier"));
		chooselevel.getSelectionModel().select(0);
		if(viewType==3) {
			chooselevel.getSelectionModel().select(1);
		}
		
		TextField tfUsername = new TextField();
		PasswordField tfpass = new PasswordField();
		PasswordField tfconfirmPass = new PasswordField();
		TextField tfname = new TextField();
		TextField tfSurname = new TextField();
		DatePicker bday = new DatePicker();
		TextField tfEmail = new TextField();
		TextField tfphone = new TextField();

		tfUsername.setTooltip(new Tooltip(
				"Username should contain" +
			    "at least three consecutive lowercase letters.\n"
			    + " It may also contain digits and underscore.\n")
				);
		tfpass.setTooltip(new Tooltip("Password can contain only letters and at least 1 digit."));
		tfname.setTooltip(new Tooltip("Name should start with an upercase letter."));
		tfSurname.setTooltip(new Tooltip("Surname should start with an upercase letter."));
		tfphone.setTooltip(new Tooltip("Phone should be in the format 069 XX XX XXX or 069XXXXXXX. or +355 XX XX XX XXX"));
		//tfEmail.setTooltip( new Tooltip(""))
		bday.setValue(LocalDate.now().minusYears(20));
				
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(10,10,10,10));
		gp.addColumn(0, level, uname, pass, confirmPass, name, surname, birthday, email, phone);
		gp.addColumn(1, chooselevel, tfUsername, tfpass, tfconfirmPass, tfname, tfSurname, bday, tfEmail, tfphone);
		
		Button add = new Button("Add");
		Button back = new Button("Back");
		Button help = new Button("Help");
		
		help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new Alert(AlertType.INFORMATION, "Hold mouse over field for tips on which data is valid!",ButtonType.OK).show();
			}
			
		});
		
		HBox hb = new HBox();
		hb.setSpacing(5);
		hb.getChildren().addAll(add,back,help);
		gp.add(hb, 1, 10);

		BorderPane bp = new BorderPane();
		bp.setCenter(gp);
		Scene scene = new Scene(bp,400,500);
		st.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
					if(viewType==1)
						(new ViewUsers(currentUser)).show(st);
					else if(viewType==2)
						(new ViewUsers(currentUser)).showEconomists(st);
					else
						(new ViewUsers(currentUser)).showCashiers(st);
				}
				
		});
		
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RWUser rwu = new RWUser();
				
				boolean validPass=false;
				boolean validUsername=false;
				boolean validConfirm=false;
				boolean validname=false;
				boolean validsurname=false;
				boolean validemail=false;
				boolean validphone=false;
				boolean validbday=false;
				
				if((tfUsername.getText()).isEmpty()) {
					tfUsername.setPromptText("Please enter username");
					tfUsername.setStyle("-fx-prompt-text-fill: red");
				}else {
					if(new ValidateInput().checkUsername(tfUsername.getText().toString())) {
						if(rwu.usernameInUse(tfUsername.getText().toString())) {
							tfUsername.clear();
							tfUsername.setPromptText("Username in use");
							tfUsername.setStyle("-fx-prompt-text-fill: red");
						}else {
							validUsername=true;
						}
					}else {
						tfUsername.clear();
						tfUsername.setPromptText("Invalid username");
						tfUsername.setStyle("-fx-prompt-text-fill: red");
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
				if((tfname.getText().toString()).isEmpty()) {
					tfname.setPromptText("Please enter name");
					tfname.setStyle("-fx-prompt-text-fill: red");
				}else {
					if((tfname.getText().toString()).matches("[A-Z][a-z]{2,}")) {
						validname=true;
					}else {
						tfname.clear();
						tfname.setPromptText("Invalid name");
						tfname.setStyle("-fx-prompt-text-fill: red");
					}
				}
				if((tfSurname.getText()).isEmpty()) {
					tfSurname.setPromptText("Please enter surname");
					tfSurname.setStyle("-fx-prompt-text-fill: red");
				}else {
					if((tfSurname.getText().toString()).matches("[A-Z][a-z]+")) {
						validsurname=true;
					}else {
						tfSurname.clear();
						tfSurname.setPromptText("Invalid surname");
						tfSurname.setStyle("-fx-prompt-text-fill: red");
					}
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
				//System.out.println(validphone);
				if(bday.getValue()==null) {
					Alert al = new Alert(AlertType.WARNING, "Please enter birthday!", ButtonType.OK);
		               al.show();
				}else {
					validbday = true;
				}
				//CHECK MIRE EMAILIN
				//CHECK MIRE PHONE
				if(validUsername && validPass && validConfirm && validemail && validname && validphone && validsurname && validbday) {
					int day = bday.getValue().getDayOfMonth();
					int month = bday.getValue().getMonthValue();
					int year = bday.getValue().getYear();
					
					MyDate bdate = new MyDate(day, month, year);
					if(chooselevel.getSelectionModel().getSelectedItem().equals("Economist")) {
						rwu.add(new Economist(tfUsername.getText(), tfpass.getText(), Level.ECONOMIST, tfname.getText(),tfSurname.getText(), bdate, tfEmail.getText(), tfphone.getText(), 50000));
					}else {
						rwu.add(new Cashier(tfUsername.getText(), tfpass.getText(), Level.CASHIER, tfname.getText(),tfSurname.getText(), bdate, tfEmail.getText(), tfphone.getText(),  25000));
					}
					if(viewType==1)
						(new ViewUsers(currentUser)).show(st);
					else if(viewType==2)
						(new ViewUsers(currentUser)).showEconomists(st);
					else
						(new ViewUsers(currentUser)).showCashiers(st);
						 
				}	
				System.out.println(chooselevel.getSelectionModel().getSelectedItem());
			}
			
		});
	}

}
