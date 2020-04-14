package view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MyDate;
import model.User;

public class EnterDates {
    User currentUser;

	public EnterDates(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void show() {
		Button day = new Button("View by date");
		Button period = new Button("View for a period of time");
		Button back = new Button("Close");
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.addColumn(0, day,period,back);
		gp.setVgap(10);
		Scene sc = new Scene(gp,400,300);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(sc);
		stage.setTitle("Choose");
		stage.show();
		day.setOnAction(new  EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				Label date = new Label("Enter date");
				DatePicker pickedDate = new DatePicker();
				Button ok = new Button("Submit");
				GridPane gp = new GridPane();
				gp.addRow(0, date,pickedDate,ok);
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(10);gp.setVgap(10);
				Scene scene = new Scene(gp,400,200);
				stage.setScene(scene);	
				stage.setTitle("View by date");
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try { 
							int day = pickedDate.getValue().getDayOfMonth();
							int month = pickedDate.getValue().getMonthValue();
							int year = pickedDate.getValue().getYear();
							
							MyDate chosenDate = new MyDate(day, month, year);
							(new CashiersStatistics(currentUser)).show(stage,chosenDate,chosenDate);
						}catch(Exception e) {
							new Alert(AlertType.ERROR,"Please enter a date", ButtonType.OK).show();
						}
						
					}
				});
			}
		});
		period.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Label d1 = new Label("From");
				Label d2 = new Label("To");
				DatePicker date1 = new DatePicker();
				DatePicker date2 = new DatePicker();
				Button ok = new Button("Submit");
				GridPane gp = new GridPane();
				gp.addRow(0, d1,date1);
				gp.addRow(1, d2,date2);
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(10);gp.setVgap(10);
				gp.add(ok, 1, 2);
				Scene scene = new Scene(gp,400,300);
				stage.setScene(scene);	
				scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
				stage.setTitle("View for a period of time");
				
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try { 
							SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
							int day = date1.getValue().getDayOfMonth();
							int month = date1.getValue().getMonthValue();
							int year = date1.getValue().getYear();
							MyDate chosenDate1 = new MyDate(day, month, year);
							int day2 = date2.getValue().getDayOfMonth();
							int month2 = date2.getValue().getMonthValue();
							int year2 = date2.getValue().getYear();
							MyDate chosenDate2 = new MyDate(day2, month2, year2);
							Date startDate = formatter.parse(chosenDate1.toString());
					        Date endDate = formatter.parse(chosenDate2.toString());
					        if(startDate.before(endDate)) {
							     (new CashiersStatistics(currentUser)).show(stage,chosenDate1,chosenDate2);
					        }else {
					        	new Alert(AlertType.ERROR,"Date 2 must be after Date 1", ButtonType.OK).show();;
					        }
						}catch(Exception e) {
							new Alert(AlertType.ERROR,"Please enter both dates", ButtonType.OK).show();
						}
					}
				});
			}
			
		});
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		
		});
		
	}
	
	public void view3Options() {
		
		ToggleGroup position = new ToggleGroup();
		RadioButton day = new RadioButton("Day");
		RadioButton period = new RadioButton("Period");
		RadioButton total = new RadioButton("Total");
		position.getToggles().addAll(day, total, period);
		position.selectToggle(day);
		
		DatePicker data = new DatePicker();
		DatePicker startdate = new DatePicker();
		DatePicker endDate = new DatePicker();
		
		Button view = new Button("View");
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		gp.addColumn(0, day,data,period,startdate,endDate,total,view);
		Scene sc = new Scene(gp,400,300);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		Stage st = new Stage();
		st.setTitle("Dates");
		st.setScene(sc);
		st.show();
		
		view.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String posit = ((RadioButton) position.getSelectedToggle()).getText(); 
				if(posit.equals("Day")) {
					try { 
						int day = data.getValue().getDayOfMonth();
						int month = data.getValue().getMonthValue();
						int year = data.getValue().getYear();
						
						MyDate chosenDate = new MyDate(day, month, year);
						(new SellsPurchasesChart(currentUser)).show(st,chosenDate,chosenDate);
					}catch(Exception e) {
						new Alert(AlertType.ERROR,"Please enter a date", ButtonType.OK).show();
					}
				}else if(posit.equals("Period")) {
					try { 
						SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
						int day = startdate.getValue().getDayOfMonth();
						int month = startdate.getValue().getMonthValue();
						int year = startdate.getValue().getYear();
						MyDate chosenDate1 = new MyDate(day, month, year);
						int day2 = endDate.getValue().getDayOfMonth();
						int month2 = endDate.getValue().getMonthValue();
						int year2 = endDate.getValue().getYear();
						MyDate chosenDate2 = new MyDate(day2, month2, year2);
						Date startDate = formatter.parse(chosenDate1.toString());
				        Date endDate = formatter.parse(chosenDate2.toString());
				        if(startDate.before(endDate)) {
				           (new SellsPurchasesChart(currentUser)).show(st,chosenDate1,chosenDate2);
				        }else {
				        	new Alert(AlertType.ERROR,"Date 2 must be after Date 1", ButtonType.OK).show();;
				        }
					}catch(Exception e) {
						new Alert(AlertType.ERROR,"Please enter both dates", ButtonType.OK).show();
					}
				}else if(posit.equals("Total")) {
					(new SellsPurchasesChart(currentUser)).showTotal(st);
				}
				
			}
		});
	}
    
    
}
