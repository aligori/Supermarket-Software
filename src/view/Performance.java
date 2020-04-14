package view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.Cashier;
import model.MyDate;
import model.RWUser;

public class Performance {
    User currentUser;
    
	public Performance(User currentUser) {
		this.currentUser = currentUser;
	}

	public void show(Stage st) {
		
		RWUser rwu = new RWUser();
		
		ObservableList<Cashier> cashiers= FXCollections.observableArrayList(rwu.getCashiers());
		
		TableView<Cashier> table=new TableView<Cashier>();
		table.setEditable(true);
		
		TableColumn un=new TableColumn("Username");
		un.setCellValueFactory(new PropertyValueFactory<>("username"));
		un.setMinWidth(150);
		
		TableColumn name=new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(150);
		
		TableColumn surname=new TableColumn("Surname");
		surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		surname.setMinWidth(150);
		
		TableColumn phone=new TableColumn("Phone");
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		phone.setMinWidth(150);
		
		 TableColumn money=new TableColumn("Salary");
		 money.setCellValueFactory(new PropertyValueFactory<>("salary"));
		 money.setMinWidth(150);
		
		table.setItems(cashiers);
		table.getColumns().addAll(un,name,surname,phone,money);
		
		Label l1 = new Label("From");
		Label l2 = new Label("To");
		DatePicker date1 = new DatePicker();
		DatePicker date2 = new DatePicker();
		
		Button back = new Button("<<");
		Button view = new Button("View");
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
	
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(l1,date1,l2,date2);
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(20);
		vb.getChildren().addAll(table,hb);
		
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(30, 30, 30, 30));
	    bp.setCenter(vb);
	    BorderPane bottom = new BorderPane();
	    bottom.setLeft(back);
	    bottom.setRight(view);
	    bp.setBottom(bottom);
		Scene sc = new Scene(bp,800,500);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		st.setResizable(true);
		st.setScene(sc);
		
		//Veprimet e Butonave
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				(new EconomistView(currentUser)).view(st);
				
			}
		});
		
		view.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(table.getSelectionModel().getSelectedItems().isEmpty()) {
					new Alert(AlertType.ERROR,"Please select a cashier", ButtonType.OK).show();
				}else {
					Cashier cash = (Cashier) table.getSelectionModel().getSelectedItem();
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
				        
				        if(startDate.compareTo(endDate)<=0) {
						     (new ViewTotalSales(currentUser)).show(st,cash,chosenDate1,chosenDate2);
				        }else {
				        	new Alert(AlertType.ERROR,"Date 2 must be after Date 1", ButtonType.OK).show();;
				        }
					}catch(Exception e) {
						new Alert(AlertType.ERROR,"Please enter a date", ButtonType.OK).show();
					}
			    }
				
			}
		});
	}

}
