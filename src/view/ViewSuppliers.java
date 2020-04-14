package view;


import java.util.ArrayList;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Economist;
import model.Product;
import model.RWProduct;
import model.RWSuppliers;
import model.Supplier;
import model.User;

public class ViewSuppliers {
    User currentUser;
	private Supplier chosenSup;
	ObservableList<Product> products;
	int mode=0;
    
    
	public ViewSuppliers(User currentUser) {
		this.currentUser = currentUser;
	}

	public void show(Stage st) {
		
		RWSuppliers rws =new RWSuppliers();
		ObservableList<Supplier> suppliers = FXCollections.observableArrayList(rws.getSuppliers());
		
		VBox left = new VBox();
		left.setAlignment(Pos.CENTER);
		left.setSpacing(10);
		Label supp = new Label("Suppliers");
		supp.setAlignment(Pos.CENTER);
		supp.setStyle("-fx-font-size:23px");
		//supp.setId("h1");
		
        TableView table = new TableView();
		
		TableColumn name=new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn address = new TableColumn("Address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		TableColumn email=new TableColumn("Email");
	    email.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		TableColumn phone=new TableColumn("Mobile");
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		
		table.setItems(suppliers);
		table.setEditable(true);
		table.getColumns().addAll(name,address,email,phone);
		
		table.setRowFactory(tv -> {
            TableRow<Supplier> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Supplier readsup = row.getItem();
                    showAfterDoubleClick(st,readsup);
                }
            });
            return row;
        });
		
		HBox hb = new HBox();
		Button add = new Button("Add Supplier");
		Button del = new Button("Delete Supplier");
		Button back = new Button("<<");
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(add,del);
		hb.setPadding(new Insets(10,10,10,10));
		hb.setSpacing(10);
		
		left.getChildren().addAll(supp,table,hb);
		
        TableView productTable = new TableView();
		
		TableColumn barcode=new TableColumn("Barcode");
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		TableColumn product = new TableColumn("Product");
		product.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn category=new TableColumn("Category");
	    category.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		TableColumn purchasedPrice=new TableColumn("Supplier price(ALL)");
		purchasedPrice.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));
		
		
		if(mode==0) {
		   productTable.setDisable(true);
		}else {
		   productTable.setDisable(false);
		}
		
		productTable.setItems(products);
		productTable.setEditable(false);
		productTable.getColumns().addAll(barcode,product,category,purchasedPrice);
		
		HBox hb1 = new HBox();
		Button addProduct = new Button("Add Product to the list");
		hb1.getChildren().addAll(addProduct);
		hb1.setPadding(new Insets(10,10,10,10));
		hb1.setSpacing(10);
		hb1.setAlignment(Pos.CENTER);
		
		Label pr = new Label("Products from this supplier");
	    pr.setStyle("-fx-font-size:22px");
	    
		VBox right = new VBox();
		right.setAlignment(Pos.CENTER);
		right.setSpacing(10);
		right.getChildren().addAll(pr,productTable,hb1);
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(40); gp.setVgap(10);
		gp.setPadding(new Insets(30,30,30,30));
		gp.addColumn(0, left);
		gp.addColumn(1, right);
		
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(30,30,30,30));
		bp.setCenter(gp);
		bp.setBottom(back);
		bp.setStyle("-fx-background-color: #f5f9fa ;");
		
		Scene sc=new Scene(bp,900,600);
		st.setScene(sc);
		st.setTitle("Suppliers & Products");
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		addProduct.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(table.getSelectionModel().getSelectedItems().isEmpty()) {
					new Alert(AlertType.ERROR,"Please select a supplier!!",ButtonType.OK).show();;
					
				}else {
					Supplier sup = (Supplier) table.getSelectionModel().getSelectedItem();
	                (new AddProduct(currentUser, sup.getName())).show(st);
			    }
				
			}
			
		});
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				(new AddSupplier(currentUser)).show(st);
				
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
		
		del.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				RWSuppliers rws = new RWSuppliers();
				RWProduct rwp = new RWProduct();
				if(table.getSelectionModel().getSelectedItems().isEmpty()) {
					new Alert(AlertType.ERROR,"Please select a supplier!!",ButtonType.OK).show();;
					
				}else {
					Supplier sup = (Supplier)table.getSelectionModel().getSelectedItem();
					ArrayList<Product> p = rwp.getProducts();
					for(int i=0;i<p.size();i++) {
						if( (p.get(i).getSupplier()).equals(sup.getName()))
							rwp.deleteProduct(p.get(i));
					}
					int position = rws.getPosition(sup);
					rws.deleteSupplier(position);
					(new ViewSuppliers(currentUser)).show(st);
			    }
				
				
			}
			
		});
	}

     void showAfterDoubleClick(Stage st, Supplier readsup) {
		this.chosenSup=readsup;
		RWProduct rwp = new RWProduct();
		ArrayList<Product> suplierproducts = new ArrayList<Product>();
		for(Product i: rwp.getProducts()) {
			if(i.getSupplier().equals(readsup.getName())){
				suplierproducts.add(i);
			}
		}
		products = FXCollections.observableArrayList(suplierproducts);
		mode=1;
		show(st);
		
	}

}
