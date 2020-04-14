package view;



import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import model.RWCategory;
import model.RWProduct;
import model.RWSuppliers;
import model.RWUser;
import model.Supplier;
import model.User;
import model.ValidateInput;

public class AddProduct {
    User currentUser;
    int view=0;
    String supplierName;
    String address;
    String email;
    String phone;
    
	public AddProduct(User currentUser, String supplierName) { //kur shtyp add product
		this.currentUser = currentUser;
		this.supplierName=supplierName;
	}

		public AddProduct(User currentUser, String supplierName, String address, String email, String phone) {
		super();
		this.currentUser = currentUser;
		this.supplierName = supplierName;
		this.address = address;
		this.email = email;
		this.phone = phone;
		view=1; //kur krijon produktin e pare te supplierit
	}

		public void show(Stage st) {	
		Text text = new Text("Register a product you get from this supplier");
		GridPane gp = new GridPane();
		gp.setHgap(10); gp.setVgap(10);
		gp.setPadding(new Insets(10,10,10,10));
		gp.setAlignment(Pos.CENTER);
		
		Label supplier = new Label("Supplier");
		TextField tfsupplier = new TextField(supplierName);
		tfsupplier.setDisable(true);
		
		Label barcode = new Label("Barcode");
		TextField tfBarcode = new TextField();
		
		Label name = new Label("Name");
		TextField tfname = new TextField();
		
		Label category = new Label("Category");		
		RWCategory rwc = new RWCategory();
		ChoiceBox tfcategory = new ChoiceBox(FXCollections.observableArrayList(rwc.getCategories()));
		tfcategory.getSelectionModel().select(0);
					
		Label cost = new Label("Cost");
		TextField tfcost = new TextField();
		
		Label sellprice = new Label("Sell price");
		TextField tfsellprice = new TextField();
		
		gp.addColumn(0, supplier, barcode, name, category, cost, sellprice);
		gp.addColumn(1,tfsupplier, tfBarcode, tfname, tfcategory, tfcost, tfsellprice);
		
        Button create = new Button("Create");
        Button back = new Button("Cancel");
        
        HBox hb = new HBox();
		hb.setSpacing(5);
        hb.getChildren().addAll(create,back);
        
        gp.add(hb, 1, 6);
        VBox vb= new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(5);
        vb.getChildren().addAll(text,gp);
        vb.setId("vbox");
		BorderPane bp = new BorderPane();
		bp.setCenter(vb);
		bp.setPadding(new Insets(100, 140, 100, 140));
		st.setTitle("Add Product");
        Scene scene = new Scene(bp,600,600);
		st.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				(new ViewSuppliers(currentUser)).show(st);	
			}
		});
        
        create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			RWProduct rwp = new RWProduct();
			RWSuppliers rws = new RWSuppliers();
				try {
				boolean validBarcode=false;
				boolean validname=false;
				//boolean validcategory=false;
				boolean validcost=false;
				boolean validsellprice=false;
				
				if((tfBarcode.getText()).isEmpty()) {
					tfBarcode.setPromptText("Please enter username");
					tfBarcode.setStyle("-fx-prompt-text-fill: red");
				}else {
					if(tfBarcode.getText().toString().matches("\\d{6,}")) {
						if(rwp.barcodeInUse(tfBarcode.getText().toString())) {
							tfBarcode.clear();
							tfBarcode.setPromptText("Product with this barcode already exists");
							tfBarcode.setStyle("-fx-prompt-text-fill: red");
						}else {
							validBarcode=true;
						}
					}else {
						tfBarcode.clear();
						tfBarcode.setPromptText("Invalid barcode format");
						tfBarcode.setStyle("-fx-prompt-text-fill: red");
					}
				}
				if(!tfcost.getText().isEmpty()) {
					if(tfcost.getText().toString().matches("\\d{1,}\\.{0,}\\d{0,}")) {
					validcost=true;
				}else {
					tfcost.clear();
					tfcost.setPromptText("Invalid price format");
					tfcost.setStyle("-fx-prompt-text-fill: red");
			    }
				}else {
					tfcost.clear();
					tfcost.setPromptText("Enter purchase price");
					tfcost.setStyle("-fx-prompt-text-fill: red");
				}
				if(!tfsellprice.getText().isEmpty()) {
				  if(tfsellprice.getText().toString().matches("\\d{1,}\\.{0,}\\d{0,}")) {
					  if(Double.parseDouble(tfsellprice.getText().toString()) > Double.parseDouble(tfcost.getText().toString()))
					        validsellprice = true;
					  else{
						    new Alert(AlertType.WARNING,"Don't you think you should sell it with a higher price?! :)", ButtonType.YES).show();
					  }
				  }else {
					tfsellprice.clear();
					tfsellprice.setPromptText("Invalid price format");
					tfsellprice.setStyle("-fx-prompt-text-fill: red");
			      }
				}else {
					tfsellprice.clear();
					tfsellprice.setPromptText("Enter sell price");
					tfsellprice.setStyle("-fx-prompt-text-fill: red");
				}
				
				if(tfname.getText().isEmpty()){
					tfname.clear();
					tfname.setPromptText("Please enter name");
					tfname.setStyle("-fx-prompt-text-fill: red");
				}else {
					validname=true;
				}
				
				if(validBarcode && validcost && validname && validsellprice) {
					double costi=Double.parseDouble(tfcost.getText().toString());
					double sp=Double.parseDouble(tfsellprice.getText().toString());
					rwp.addProduct(new Product(tfBarcode.getText(), tfname.getText(),tfcategory.getSelectionModel().getSelectedItem().toString(), supplierName, costi, sp));
					if(view==1) {
						rws.addSupplier(new Supplier(supplierName, address, email, phone));
					}
					(new ViewSuppliers(currentUser)).show(st);
				}
				}catch(Exception e) {
					System.out.println("Category cannot be cast to class");
				}
			}
		});
        
		
	}

}
