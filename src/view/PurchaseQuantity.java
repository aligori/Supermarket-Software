package view;

import java.time.LocalDate;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Economist;
import model.MyDate;
import model.Product;
import model.RWProduct;
import model.RWSellsPurchases;
import model.User;

public class PurchaseQuantity {
    User currentUser;
    RWProduct rwp;
    Product chosenprod;
    
	public PurchaseQuantity(User currentUser) {
		super();
		this.currentUser = currentUser;
	}
	public void show(Stage st) {
		rwp = new RWProduct();
		ObservableList<Product> products = FXCollections.observableArrayList(rwp.getProducts());
		
		TableView productTable = new TableView();
		
		TableColumn barcode=new TableColumn("Barcode");
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		barcode.setMinWidth(120);
		
		TableColumn name = new TableColumn("Product");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn stock = new TableColumn("Stock");
		stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		stock.setMinWidth(90);
		
		TableColumn category=new TableColumn("Category");
	    category.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		TableColumn suplieri=new TableColumn("Supplier");
		suplieri.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		
		TableColumn purchasedPrice=new TableColumn("Purchase price(ALL)");
		purchasedPrice.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));
		
		TableColumn sellprice=new TableColumn("Sell Price (ALL)");
		sellprice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		
		productTable.setItems(products);
		productTable.getColumns().addAll(barcode,name,stock,category,suplieri, purchasedPrice, sellprice);
		
		TextField bcode = new TextField();
		bcode.setPromptText("Barcode");
		bcode.setEditable(false);
		
		TextField emri = new TextField();
		emri.setPromptText("Name of Product");
		emri.setEditable(false);
		
		TextField sasia = new TextField();
		sasia.setPromptText("Quantity");
		
		DatePicker expiry = new DatePicker();
		expiry.setValue(LocalDate.now().plusMonths(2));
		expiry.setTooltip(new Tooltip("Expiry Date"));
		
		
		Button buy = new Button("Purchase");
		Button back = new Button("<<");
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
	    productTable.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    chosenprod = row.getItem();
                    bcode.setText(chosenprod.getBarcode());
                    emri.setText(chosenprod.getName());
                }
            });
            return row;
        });

	    buy.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				boolean validbc = false;
				boolean validq=false;
				boolean validexp=false;
				
				if(bcode.getText().isEmpty()) {
					new Alert(AlertType.WARNING,"Please select a product from table!",ButtonType.OK).show();
				}else {
					validbc=true;
				}
				try {
					if(Integer.parseInt(sasia.getText()) <=0){
						sasia.clear();
						sasia.setPromptText("Enter a valid quantity");
				        sasia.setStyle("-fx-prompt-text-fill: red");
					}else {
					    validq=true;
					}
				}catch(NumberFormatException e){
					sasia.clear();
					sasia.setPromptText("Enter a valid quantity");
			        sasia.setStyle("-fx-prompt-text-fill: red");
				}
				if(expiry.getValue()==null) {
					Alert al = new Alert(AlertType.WARNING, "Please enter an expiry date!", ButtonType.OK);
		               al.show();
				}else {
					validexp = true;
				}
				
				if(validbc && validq && validexp) {
					int day = expiry.getValue().getDayOfMonth();
					int month = expiry.getValue().getMonthValue();
					int year = expiry.getValue().getYear();
					
					MyDate expiration = new MyDate(day, month, year);
					int pos=rwp.getPosition(chosenprod);
					RWSellsPurchases rwsp = new RWSellsPurchases();
					rwsp.addPurchasedProduct(pos,bcode.getText(), Integer.parseInt(sasia.getText()), chosenprod.getPurchasedPrice(), expiration);
					(new PurchaseQuantity(currentUser)).show(st);
				}
				
			}
		});
	    
	    back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(currentUser instanceof Economist) {
				(new EconomistView(currentUser)).view(st);
				}else {
					(new ViewAdmin(currentUser)).view(st);
				}
				
			}
		});
	    
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(20,20,20,20));
		hb.setSpacing(10);
		hb.getChildren().addAll(back,bcode,sasia,expiry,buy);
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(30, 30, 30, 30));
		vb.getChildren().addAll(productTable,hb);
		BorderPane bp = new BorderPane();
	    bp.setCenter(vb);
	    bp.setOpaqueInsets(new Insets(10,10,10,10));
	    bp.setStyle("-fx-background-color: #ebfef3 ;");
	    Scene sc = new Scene(bp,900,500);
	    st.setScene(sc);
	    st.setTitle("Purchase quantity");
	    st.setResizable(true);
	    st.setMaxWidth(1000);
	    st.setMaxHeight(700);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	    

	}


}
