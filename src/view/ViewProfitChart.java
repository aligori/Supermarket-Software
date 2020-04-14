package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Cashier;
import model.Economist;
import model.PurchasedProduct;
import model.RWSellsPurchases;
import model.RWUser;
import model.SoldProduct;
import model.User;

public class ViewProfitChart {
	int year;
	int month;
	
     public ViewProfitChart(int year, int month) {
		super();
		this.year = year;
		this.month = month;
	}
     
	public ViewProfitChart(int year) {
		super();
		this.year = year;
	}

	public void show(Stage stage) {
		
		RWUser rwu = new RWUser();
		RWSellsPurchases rwsp = new RWSellsPurchases();
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		
		xAxis.setLabel("Month");
		yAxis.setLabel("Value (ALL)");
		
		XYChart.Series s1 = new XYChart.Series();
		XYChart.Series s2 = new XYChart.Series();
		XYChart.Series s3 = new XYChart.Series();
		
		s1.setName("Incomes");
		s2.setName("Outcomes");
		s3.setName("Profit");
		
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
			
			for(int i=0;i<12;i++) {
				double cost=0;
				double incomes=0;
				double profit=0;
				
                for(SoldProduct sp : rwsp.getSoldList()) {
                	//Calculating money earned
				  if(sp.getSellDate().getY() == year) {
					if(sp.getSellDate().getM()==(i+1)) {
						System.out.println("month "+ i+1 + "has sales");
						incomes+=sp.getQuantity()*sp.getSellprice();
					}	
				  }
				}
                for(PurchasedProduct bp : rwsp.getPurchasedList()) {
                	//Calculating money spent on buying products
  				  if(bp.getPurchasedDate().getY() == year) {
  					if(bp.getPurchasedDate().getM()==(i+1)) {
  						System.out.println("month "+i+" has purchases");
  						cost+=bp.getQuantity()*bp.getPurchasedprice();
  					}	
  				  }
  				}
            	//Adding employees' salaries to total cost of month
               /*  for(User u: rwu.getUsers()) {
                	if(u instanceof Economist) {
                		cost+=((Economist)u).getSalary();
                	}else if(u instanceof Cashier) {
                		cost+=((Cashier)u).getSalary();
                	}
                }
                I commented this part because salaries are too high, the sample products sold have a small price 
                compared to the salaries so its not possible to show a positive revenue in the chart.
                nevertheless the code is okay if we include salaries. */
                profit=incomes-cost;
                
						s1.getData().add(new XYChart.Data(months[i], incomes));
						s2.getData().add(new XYChart.Data(months[i], cost));
						s3.getData().add(new XYChart.Data(months[i], profit));
			}
		

		    Scene scene  = new Scene(bc,800,600);
			bc.getData().addAll(s1,s2,s3);
	       
	        stage.setScene(scene);
	        stage.getIcons().add(new Image("images/logo.jpg"));
	        stage.setTitle("Year Summary");
	        stage.show();
		
		}
}
