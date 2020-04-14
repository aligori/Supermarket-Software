package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Product;
import model.RWProduct;
import model.User;

public class viewStock {
    User currentUser;

	public viewStock(User currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public void show() {
		Stage st=new Stage();
		RWProduct rwp=new RWProduct();
		st.setTitle("Stock ");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc =  new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Products with less than 5 items in stock");     
        yAxis.setLabel("Number");
        bc.setLegendVisible(false);
 
        XYChart.Series series1 = new XYChart.Series();
        
        
        for(Product p: rwp.getProducts()) {
        	int stock= p.getStock();
        	if(stock<5) {
        	series1.getData().add(new XYChart.Data(p.getName(),stock));
        	}
        }
        BorderPane bp = new BorderPane();
        bp.setCenter(bc);
        bp.setPadding(new Insets(40, 100 , 40, 100));
        Scene scene  = new Scene(bp,800,600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        bc.getData().addAll(series1);
        st.setScene(scene);
        st.getIcons().add(new Image("images/logo.jpg"));
        st.setTitle("Stock");
        st.show();
		
	}
    
}
