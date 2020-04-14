package view;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cashier;
import model.Economist;
import model.MyDate;
import model.RWUser;
import model.User;
import model.ValidateInput;

public class EditUserView {
    User currentUser;
    User editUser;
    
	public EditUserView(User currentUser,User emp) {
		this.currentUser = currentUser;
		editUser = emp;
	}

	public void show(Stage st, int viewType) {
 
		Label name = new Label("Name");
		Label surname = new Label("Surname");
		Label birthday = new Label("Birthday");
		Label email = new Label("E-mail");
		Label phone = new Label("Mobile");
		Label salary = new Label("Salary");
		
		TextField tfname = new TextField();
		TextField tfSurname = new TextField();
		DatePicker bday = new DatePicker();
		TextField tfEmail = new TextField();
		TextField tfphone = new TextField();
		TextField tfsalary  = new TextField();
		if(editUser instanceof Economist) {
			tfname.setText(((Economist)editUser).getName());
			tfSurname.setText(((Economist)editUser).getSurname());		
			bday.setValue(LocalDate.of(((Economist)editUser).getBday().getY(),((Economist)editUser).getBday().getM(),((Economist)editUser).getBday().getD()));
			tfEmail.setText(((Economist)editUser).getEmail());
			tfphone.setText(((Economist)editUser).getPhone());
			Double s=((Economist)editUser).getSalary();
			tfsalary.setText(s.toString());
		}else if (editUser instanceof Cashier) {
			tfname.setText(((Cashier)editUser).getName());
			tfSurname.setText(((Cashier)editUser).getSurname());		
			bday.setValue(LocalDate.of(((Cashier)editUser).getBday().getY(),((Cashier)editUser).getBday().getM(),((Cashier)editUser).getBday().getD()));
			tfEmail.setText(((Cashier)editUser).getEmail());
			tfphone.setText(((Cashier)editUser).getPhone());
			Double s=((Cashier)editUser).getSalary();
			tfsalary.setText(s.toString());
		}

		tfname.setTooltip(new Tooltip("Name should start with an upercase letter."));
		tfSurname.setTooltip(new Tooltip("Surname should start with an upercase letter."));
		tfphone.setTooltip(new Tooltip("Phone should be in the format 069 XX XX XXX or 069XXXXXXX."));
		//tfEmail.setTooltip( new Tooltip("Email"))
				
		GridPane gp = new GridPane();
		gp.setHgap(20);
		gp.setVgap(10);
		gp.setPadding(new Insets(10,10,10,10));
		gp.addColumn(0, new Label("Username"), name, surname, birthday, email, phone,salary);
		gp.addColumn(1, new Text(editUser.getUsername()), tfname, tfSurname, bday, tfEmail, tfphone, tfsalary);
		
		Button update = new Button("Update");
		Button back = new Button("<<");
		Button help = new Button("Help");
		
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(5);
		hb.getChildren().addAll(update,help);
		gp.add(hb, 1, 9);

		BorderPane bp = new BorderPane();
		bp.setCenter(gp);
		bp.setBottom(back);
		bp.setPadding(new Insets(30, 40, 40, 40));
		Scene scene = new Scene(bp,400,450);
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
		
		help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new Alert(AlertType.INFORMATION, "Hold mouse over field for tips on which data is valid!",ButtonType.OK).show();
			}
			
		});
		
		update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RWUser rwu = new RWUser();
				
				boolean validname=false;
				boolean validsurname=false;
				boolean validemail=false;
				boolean validphone=false;
				boolean validbday=false;
				boolean validSalary = false;
				

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
                System.out.println(new ValidateInput().checkPhone(tfphone.getText().toString()));
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
				if((tfsalary.getText()).isEmpty()) {
					tfsalary.setPromptText("Please enter salary");
					tfsalary.setStyle("-fx-prompt-text-fill: red");
				}else {
					if(new ValidateInput().checksalary(tfsalary.getText().toString())) {
						validSalary = true;
					}else {
						tfphone.clear();
						tfphone.setPromptText("Invalid phone");
						tfphone.setStyle("-fx-prompt-text-fill: red");
					}
				}
				
				if(bday.getValue()==null) {
					Alert al = new Alert(AlertType.WARNING, "Please enter birthday!", ButtonType.OK);
		               al.show();
				}else {
					validbday = true;
				}
				
				if(validemail && validname && validphone && validsurname && validbday && validSalary) {
					int day = bday.getValue().getDayOfMonth();
					int month = bday.getValue().getMonthValue();
					int year = bday.getValue().getYear();
					
					MyDate bdate = new MyDate(day, month, year);
                    int i = rwu.getPosition(editUser);
                    if(editUser instanceof Economist) {
                    	rwu.edit(i, tfname.getText(), tfSurname.getText(), bdate, tfEmail.getText(), tfphone.getText(), Double.parseDouble(tfsalary.getText()));
                    }else if(editUser instanceof Cashier) {
                    	rwu.edit(i, tfname.getText(), tfSurname.getText(), bdate, tfEmail.getText(), tfphone.getText(), Double.parseDouble(tfsalary.getText()));
                    }
					if(viewType==1)
						(new ViewUsers(currentUser)).show(st);
					else if(viewType==2)
						(new ViewUsers(currentUser)).showEconomists(st);
					else
						(new ViewUsers(currentUser)).showCashiers(st);
						 
				}	
			 }
			
		});
	}

}
