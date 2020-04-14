package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MyDate;
import model.Product;
import model.PurchasedProduct;
import model.RWSellsPurchases;
import model.SoldProduct;
import model.User;

public class SellsPurchasesChart {
    User currentUser;
    ArrayList<SoldProduct> soldList;
    ArrayList<PurchasedProduct> purchasedList;

	public SellsPurchasesChart(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void show(Stage st, MyDate chosenDate1, MyDate chosenDate2) {
		RWSellsPurchases rwsp = new RWSellsPurchases();
		soldList= rwsp.getSoldList();
		purchasedList= rwsp.getPurchasedList();
		double income=0;
		int nrSold=0;
		SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
		for(SoldProduct i: soldList) {
			try {
		           Date startDate = formatter.parse(chosenDate1.toString());
		           Date endDate = formatter.parse(chosenDate2.toString());
		           Date sellDate = formatter.parse(i.getSellDate().toString());
		           if(sellDate.compareTo(startDate)>=0 && sellDate.compareTo(endDate)<=0) {
		   			 income+=i.getSellprice()*i.getQuantity();
					 nrSold+=i.getQuantity();
					 System.out.println(i.getQuantity()+" "+i.getSellprice());
		           }
		
	            } catch (ParseException e) {

		        e.printStackTrace();
	            }

		}
		double purchaseExp=0;
		int nrbought=0;
		for(PurchasedProduct j: purchasedList) {
			try {
		           Date startDate = formatter.parse(chosenDate1.toString());
		           Date endDate = formatter.parse(chosenDate2.toString());
		           Date sellDate = formatter.parse(j.getPurchasedDate().toString());
		           if(sellDate.compareTo(startDate)>=0 && sellDate.compareTo(endDate)<=0) {
		   			purchaseExp+=j.getPurchasedprice()*j.getQuantity();
		   			nrbought+=j.getQuantity();
		           }
		
	            } catch (ParseException e) {

		        e.printStackTrace();
	            }

		}
		System.out.println(income+" "+nrSold+" "+purchaseExp+" "+nrbought);
		barchart(income,nrSold,purchaseExp,nrbought,st);
	}
	
	public void showTotal(Stage st) {
		RWSellsPurchases rwsp = new RWSellsPurchases();
		soldList= rwsp.getSoldList();
		purchasedList= rwsp.getPurchasedList();
		double income=0;
		int nrSold=0;
		for(SoldProduct i: soldList) {
		   			 income+=i.getSellprice()*i.getQuantity();
					 nrSold+=i.getQuantity();
					 System.out.println(i.getQuantity()+" "+i.getSellprice());
		}
		double purchaseExp=0;
		int nrbought=0;
		for(PurchasedProduct j: purchasedList) {
		   			purchaseExp+=j.getPurchasedprice()*j.getQuantity();
		   			nrbought+=j.getQuantity();
		}
		System.out.println(income+" "+nrSold+" "+purchaseExp+" "+nrbought);
		barchart(income,nrSold,purchaseExp,nrbought,st);
	}

	private void barchart(double income, int nrsold, double cost, int nrbought,Stage st) {
		
		TabPane tabPane = new TabPane();
		
		//TABI I PARE
		Tab tab = new Tab();
		tab.setText("Statistics");
		tabPane.getTabs().add(tab);
		tab.setClosable(false);
		
		CategoryAxis xAxis = new CategoryAxis();   
        
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Amount of Money"))); 
		xAxis.setLabel("category");  

		//Defining the y axis 
		NumberAxis yAxis = new NumberAxis(); 
		yAxis.setLabel("ALL");
		yAxis.setAutoRanging(true);
		
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);  
		barChart.setMaxSize(200, 600);
		
		XYChart.Series<String, Number> series1 = new XYChart.Series<>(); 
		series1.setName("Sales"); 
		series1.getData().add(new XYChart.Data<>("Amount of Money", income)); 
  

		XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
		series2.setName("Purchases"); 
		series2.getData().add(new XYChart.Data<>("Amount of Money",cost));  
        
		barChart.getData().addAll(series1, series2);
		
        CategoryAxis xAxi = new CategoryAxis();   
        
		xAxi.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Number of items"))); 
		xAxi.setLabel("category");  

		//Defining the y axis 
		NumberAxis yAxi = new NumberAxis(); 
		yAxi.setLabel("Pieces");
		yAxi.setAutoRanging(true);
		
		BarChart<String, Number> barChart1 = new BarChart<>(xAxi, yAxi);  
		barChart1.setMaxSize(200, 600);
		
		XYChart.Series<String, Number> series11 = new XYChart.Series<>(); 
		series11.setName("Sales"); 
		series11.getData().add(new XYChart.Data<>("Number of items", nrsold)); 
  

		XYChart.Series<String, Number> series22 = new XYChart.Series<>(); 
		series22.setName("Purchases"); 
		series22.getData().add(new XYChart.Data<>("Number of items",nrbought));  
        
		barChart1.getData().addAll(series11, series22);
		
		Label label = new Label("Sales vs Purchases");
		label.setAlignment(Pos.CENTER);
		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(60);
		hb.getChildren().addAll(barChart,barChart1);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(label,hb);
		vb.setAlignment(Pos.CENTER);
		
		tab.setContent(vb);
	
		//TABI I DYTE
		
		Tab tab2 = new Tab();
		tab2.setText("View Items");
		tabPane.getTabs().add(tab2);
		tab2.setClosable(false);
		
		//Tabela e produkteve te blera
        ObservableList<PurchasedProduct> products = FXCollections.observableArrayList(purchasedList);
		
		TableView boughtTable = new TableView();
		
		TableColumn barcode=new TableColumn("Barcode");
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		TableColumn q = new TableColumn("Quantity");
		q.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		TableColumn d=new TableColumn("Date");
	    d.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
		
	    boughtTable.setItems(products);
		boughtTable.getColumns().addAll(barcode,q,d);
		
		//Tabela e produkteve te shitura
        ObservableList<SoldProduct> soldproducts = FXCollections.observableArrayList(soldList);
		
		TableView soldTable = new TableView();
		
		TableColumn bc=new TableColumn("Barcode");
		bc.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		TableColumn quantity = new TableColumn("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		TableColumn date=new TableColumn("Sell Date");
	    date.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
		
	    soldTable.setItems(soldproducts);
		soldTable.getColumns().addAll(bc,quantity,date);
		
		BorderPane bp = new BorderPane();
		HBox hbTables = new HBox();
		hbTables.setAlignment(Pos.CENTER);
		hbTables.setSpacing(40);
		
		Label l1 = new Label("Bought Products");
		l1.setAlignment(Pos.CENTER);
		Label l2 = new Label("Sold Products");
		l2.setAlignment(Pos.CENTER);
		
		Label textincome = new Label("Total money earned: "+ income+" ALL");
		Label textnrsold = new Label("Total items sold: "+ nrsold);
		Label textcost = new Label("Total money spent: "+ cost+" ALL");
		Label textnrbought = new Label("Total items bought: "+ nrbought);
		
		VBox left = new VBox();
		left.setSpacing(20);
		left.getChildren().addAll(l1,boughtTable,textcost,textnrbought);
		
		VBox right = new VBox();
		right.setSpacing(20);
		right.getChildren().addAll(l2,soldTable,textincome,textnrsold);
		
		hbTables.getChildren().addAll(left,right);
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setCenter(hbTables);
	    
		tab2.setContent(bp);
		
		Scene scene = new Scene(tabPane,700, 500);
		st.setScene(scene);
		st.getIcons().add(new Image("images/logo.jpg"));
		st.setTitle("Sales VS Purchases");
	}
}
