package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Bill;
import model.Cashier;
import model.MyDate;
import model.RWBill;
import model.RWUser;
import model.SoldProduct;
import model.User;

public class CashiersStatistics {
	User currentUser;

	public CashiersStatistics(User currentUser) {
		super();
		this.currentUser = currentUser;
	}
	
	public void show(Stage stage,MyDate chosenDate1, MyDate chosenDate2) {
		TabPane tabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("Sales Rate");
		tabPane.getTabs().add(tab);
		tab.setClosable(false);
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Bills");
		bc.setLegendVisible(false);
		
		bc.setTitle("Number of bills for each cashier");
		xAxis.setLabel("Cashiers");
		yAxis.setLabel("Number of Bills");
		
		RWBill rwb = new RWBill();
		RWUser rwu = new RWUser();
		SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
		
		for(Cashier c : rwu.getCashiers()) {
		   int bills=0; 
		   double total=0; 
		   int itemsSold=0; 
		   ArrayList<SoldProduct> sp = new ArrayList<SoldProduct>();
		   for(Bill i : rwb.getBills()) {
			   if(i.getCashierUsername().equals(c.getUsername())) {
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
		   pieChartData.add(new PieChart.Data(c.getUsername(), total));
		   series1.getData().add(new XYChart.Data(c.getUsername(), bills));
		   
		   /*TESTING
		      System.out.println(c.getUsername()+" "+bills+" "+total+" "+itemsSold);
		      System.out.println(rwb.getBills().size()); */
		}
        
		final PieChart chart = new PieChart(pieChartData);
		
		final Label caption = new Label("Cashiers' percentages of sales for the given period of time");
		caption.setTextFill(Color.DARKBLUE);
		caption.setStyle("-fx-font: 20 arial;");
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(30, 20, 30, 20));
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(caption,chart);

		tab.setContent(vb);

		Tab tab2 = new Tab();
		tab2.setText("Total Bills");
		tabPane.getTabs().add(tab2);
		tab2.setClosable(false);

		bc.getData().add(series1);
		bc.setPadding(new Insets(20, 80, 20, 80));
		
		VBox vb2 = new VBox();
		vb2.setAlignment(Pos.CENTER);
		vb2.getChildren().addAll(bc);

		tab2.setContent(vb2);

		Scene scene = new Scene(tabPane);
		
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
	}
}
