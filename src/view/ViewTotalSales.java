package view;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bill;
import model.Cashier;
import model.MyDate;
import model.RWBill;
import model.SoldProduct;
import model.User;

public class ViewTotalSales {
    User currentUser;

	public ViewTotalSales(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void show(Stage st, Cashier cash, MyDate chosenDate1, MyDate chosenDate2) {
		   RWBill rwb = new RWBill();
		   int bills=0; 
		   double total=0; 
		   int itemsSold=0; 
		   
		   SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
		   ArrayList<SoldProduct> sp = new ArrayList<SoldProduct>();
		   
		   for(Bill i : rwb.getBills()) {
			   if(i.getCashierUsername().equals(cash.getUsername())) {
		           try {
			           Date startDate = formatter.parse(chosenDate1.toString());
			           Date endDate = formatter.parse(chosenDate2.toString());
			           Date billDate = formatter.parse(i.getBillDate().toString());
			           if(billDate.compareTo(startDate)>=0 && billDate.compareTo(endDate)<=0) {
			        	   bills++;
			        	   total+=i.getTotal();
			        	   for(SoldProduct p : i.getSoldProducts()) {
			        		   itemsSold +=p.getQuantity();
			        		   sp.add(p);
			        	   }
			           }
			
		            } catch (ParseException e) {

			        e.printStackTrace();
		            }
			   }
		   }
		   
		   
		    ObservableList<SoldProduct> soldp= FXCollections.observableArrayList(sp);
			
		    TableView<SoldProduct> table=new TableView<SoldProduct>();
			table.setEditable(true);
			
			TableColumn bc=new TableColumn("Barcode");
			bc.setCellValueFactory(new PropertyValueFactory<>("barcode"));
			bc.setMinWidth(150);
			
			TableColumn name=new TableColumn("Name");
			name.setCellValueFactory(new PropertyValueFactory<>("name"));
			name.setMinWidth(150);
			
			TableColumn q=new TableColumn("Quantity");
			q.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			q.setMinWidth(150);
			
			TableColumn price=new TableColumn("Price");
			price.setCellValueFactory(new PropertyValueFactory<>("sellprice"));
			price.setMinWidth(150);
			
			TableColumn date=new TableColumn("Date");
			date.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
			date.setMinWidth(150);
			
			table.setItems(soldp);
			table.getColumns().addAll(bc,name,q,price,date);
			
			Label c = new Label("Products sold from "+chosenDate1.toString()+" to "+chosenDate2.toString());
			Label l1 = new Label("Total no of bills");
			Label l2 = new Label("Total money made");
			   
			TextField tfbill = new TextField();
			TextField tfmoney = new TextField();
			   
			tfbill.setEditable(false);
			tfmoney.setEditable(false);
			   
			tfbill.setText(Integer.toString(bills));
			tfmoney.setText(Double.toString(total)+" ALL");
			   
			Button back = new Button("<<");
		   
			HBox hb = new HBox();
			hb.setSpacing(10);
			hb.setAlignment(Pos.CENTER);
			hb.getChildren().addAll(l1,tfbill,l2,tfmoney);
			VBox vb = new VBox();
			vb.setAlignment(Pos.CENTER);
			vb.setSpacing(20);
			vb.getChildren().addAll(new Label("Cashier "+cash.getName()),c,table,hb);
			
			BorderPane bp = new BorderPane();
			bp.setPadding(new Insets(30, 30, 30, 30));
		    bp.setCenter(vb);
		    BorderPane bottom = new BorderPane();
		    bottom.setLeft(back);
		    bp.setBottom(bottom);
			Scene sc = new Scene(bp,800,500);
			sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			st.setResizable(true);
			st.getIcons().add(new Image("images/logo.jpg"));
			st.setScene(sc);
			st.setTitle("Cashier Performance");
			
			back.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					(new Performance(currentUser)).show(st);
					
				}
			});
	}
    
}
