package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Bill;
import model.MyDate;
import model.Product;
import model.RWBill;
import model.RWProduct;
import model.RWSellsPurchases;
import model.SoldProduct;
import model.User;

public class PrepareBill {
    User currentUser;
    ArrayList<SoldProduct> billProducts;
    RWBill rwb;
    RWProduct rwp;
    int totali;
    TextField tfname;
    MyDate billDate;

    //PERDORET KUR HAPET NJE FAQE E RE OSE BEHET RESET GJITH FATURA
	public PrepareBill(User currentUser) { 
		this.currentUser = currentUser;
		billProducts = new ArrayList<>();
		totali=0;
	}
	
	//PERDORET KUR DO FSHIHET OSE DO SHTOHET NJE PRODUKT TE LISTA E BILLIT DHE DUHET SHFAQUR NE TABELE GJITHE LISTA
	public PrepareBill(User currentUser, ArrayList<SoldProduct> billproducts,int totali) {
		this.currentUser = currentUser;
		this.billProducts = billproducts;
		this.totali=totali;
	}

	public void show(Stage st) {
		
		rwb = new RWBill();
		//MENUBAR
        ArrayList<Menu> items = new ArrayList<Menu>();
		
		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: linear-gradient(#cee9fb,#90CCF4);");
		
		Label changepass = new Label("Change Password");
		Label logout = new Label("Log Out");
		
		Menu pass = new Menu("",changepass);		
		Menu out = new Menu("",logout);
		
		changepass.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				
				(new NewPassword(currentUser)).show(st);
				
			}
		});
		logout.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				try {
					(new LogIn()).start(st);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		items.add(pass);
		items.add(out);

	        menuBar.getMenus().addAll(items);
	    
		//BUTONAT DHE FIELDET LART
		Label billno = new Label("Bill No "+(rwb.getBills().size()+1));
		billno.setAlignment(Pos.CENTER);
		
		tfname= new TextField();
		tfname.setPromptText("Name of the product");
		
		TextField tfquantity = new TextField();
		tfquantity.setPromptText("Quantity");
		
		Button add = new Button("Add to basket");
		Image img1 = new Image("images/find-icon-10.png");
		ImageView iv = new ImageView(img1);
		iv.setFitHeight(15);
		iv.setFitWidth(15);
		Button search = new Button("",iv);
		search.setTooltip(new Tooltip("View all products"));
		
		GridPane gp = new GridPane();
		gp.setHgap(5);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.addRow(0, tfname,tfquantity,add,search);
		gp.setHgap(10);
		
		// TABLE
		
		ObservableList<SoldProduct> products = FXCollections.observableArrayList(billProducts);
		
		TableView billTable = new TableView();
		billTable.setPadding(new Insets(10, 10, 10, 10));
		
                TableColumn bc = new TableColumn("Barcode");
		bc.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		bc.setMinWidth(180);
		
		TableColumn name = new TableColumn("Name of Product");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		name.setMinWidth(180);
		
		TableColumn price=new TableColumn("Price (ALL)");
		price.setCellValueFactory(new PropertyValueFactory<>("sellprice"));
		price.setMinWidth(180);
		
		TableColumn quantity=new TableColumn("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantity.setMinWidth(180);
		
		billTable.setItems(products);
		billTable.setEditable(false);
		billTable.getColumns().addAll(bc,name,price,quantity);

		//BUTONAT POSHTE	
		GridPane gp1 = new GridPane();
		gp1.setPadding(new Insets(10, 10, 10, 10));
	        gp1.setHgap(200);
	        gp1.setVgap(10);
		TextField total = new TextField();
		total.setText(Integer.toString(totali));
		total.setEditable(false);
		Label shuma = new Label("Total");
		
		Button remove = new Button("Remove");
		Button reset = new Button("Reset");
		Button proceed = new Button("Proceed");
		Button back = new Button("<<");
		back.setStyle("-fx-background-color: linear-gradient(#FECEA8,#FF847C)");
		
		HBox hb2 = new HBox();
		hb2.getChildren().addAll(shuma,total,proceed);
		hb2.setSpacing(5);
	        hb2.setAlignment(Pos.BASELINE_LEFT);
	    
		HBox hb3 = new HBox();
		hb3.setSpacing(10);
		hb3.setAlignment(Pos.BASELINE_RIGHT);
		hb3.getChildren().addAll(back,remove,reset);
		gp1.addRow(0, hb3,hb2);
	    
		
		VBox vb = new VBox();
		vb.setSpacing(5);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(30, 40, 30, 40));
		vb.getChildren().setAll(billno,gp,billTable,gp1);
		BorderPane bp = new BorderPane();
		bp.setCenter(vb);
		bp.setTop(menuBar);
		Scene sc = new Scene(bp);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		st.setScene(sc);
		st.setTitle("Cashier");

		//VEPRIMET E BUTONAVE
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				rwp=new RWProduct();
				
				if(tfquantity.getText().toString().matches("\\d{1,}") && !tfquantity.getText().isEmpty()){
					
					boolean validName=false;
					for(Product i: rwp.getProducts()) {
						
						if(tfname.getText().toString().equals(i.getName())) {
							validName=true;
							int sasia=Integer.parseInt(tfquantity.getText().toString());
							
							if(i.getStock()<sasia) {
								new Alert(AlertType.ERROR,"Only "+i.getStock()+" pieces left in stock",ButtonType.OK).show();
							}else{
								if(sasia==0) {
									new Alert(AlertType.ERROR,"Enter a valid quantity!",ButtonType.OK).show();
								}else {
								    SoldProduct sp=new SoldProduct(i.getBarcode(), i.getName(), sasia, i.getSellPrice());
								    billProducts.add(sp);
									totali+=sp.getSellprice()*sp.getQuantity();
									//System.out.println(sp.toString());
								    (new PrepareBill(currentUser, billProducts,totali)).show(st);
								}
							}
							
						}
					}
					if(!validName) {
						new Alert(AlertType.ERROR,"No Product with such name!",ButtonType.OK).show();
					}
				}else {
					new Alert(AlertType.ERROR,"Enter a valid quantity!",ButtonType.OK).show();
				}
			}
				
		});
		
		search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				getProduct();		
			}
			
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new PrepareBill(currentUser)).show(st);
				
			}
		});
		
		remove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(billTable.getSelectionModel().getSelectedItems().isEmpty()) {
					new Alert(AlertType.WARNING, "Select product from table", ButtonType.OK).show();
				}else {
					SoldProduct delProd = (SoldProduct) billTable.getSelectionModel().getSelectedItem();
					//remove the price of the product you want to delete from total
					totali -= delProd.getSellprice()*delProd.getQuantity(); 
					billProducts.remove(delProd); 
					(new PrepareBill(currentUser, billProducts, totali)).show(st);;		
				}
				
			}
		
		});
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				(new CashierView(currentUser)).view(st);
			}
		});
		
		proceed.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(billProducts.isEmpty()) {
				  new Alert(AlertType.ERROR, "Bill cannot  be empty", ButtonType.OK).show();
			    }else{
			      //Data e fatures
				  RWSellsPurchases rwsp = new RWSellsPurchases();
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				  billDate = new MyDate(sdf.format(new Date()));
				  //Krijimi i fatures si objekt dhe shtimi tek arrylista e bill-eve
				  rwb.addBill(new Bill(currentUser.getUsername(), billDate, totali, billProducts)); 
				
				  for(SoldProduct i: billProducts) {
					rwp = new RWProduct();
					System.out.println(i.getBarcode());
					Product p=rwp.getProductByBarcode(i.getBarcode());	   
				    int pos=rwp.getPosition(p); //position of the product is needed to modify the quantity of this product
				    rwsp.addSoldProduct(pos, i); //Shtimi i produktit te shitur tek lista qe mban te dhena per shitjet
				  }
				  writetoFile(billProducts,rwb.getBills().size(),totali); //Shkrimi i fatures ne text file
				  new Alert(AlertType.INFORMATION, "Bill Saved", ButtonType.OK).show();
			      (new PrepareBill(currentUser)).show(st);
			   }
				
			}
		});
	}
	
	public void writetoFile(ArrayList<SoldProduct> sp,int billno, int totali){
		try {
			File folder=new File("bills");
			folder.mkdir();
			PrintWriter pw=new PrintWriter(new FileOutputStream(new File("bills"+"/"+"["+billno+"].txt"),true));
			pw.println("KUPON TATIMOR");
			for(SoldProduct i: sp) {
				pw.println(i.getQuantity()+" X "+i.getSellprice()+"\t"+i.getName()+"\t"+i.getQuantity()*i.getSellprice());
			}
			pw.println("TOTAL "+totali);
			pw.println("Date "+ billDate);
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("No writing rights on file!!!");
		}
	}
	
	private void getProduct() {
		
		Stage stage = new Stage();
		
		
		RWProduct rwp = new RWProduct();
		ObservableList<Product> allproducts = FXCollections.observableArrayList(rwp.getProducts());
		
		TableView productTable = new TableView();
		
		TableColumn barcode=new TableColumn("Barcode");
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
		
		TableColumn name = new TableColumn("Product");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn stock = new TableColumn("Stock");
		stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		
		TableColumn category=new TableColumn("Category");
	        category.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		TableColumn suplieri=new TableColumn("Supplier");
		suplieri.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		
		TableColumn sellprice=new TableColumn("Sell Price (ALL)");
		sellprice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
		
		productTable.setItems(allproducts);
		productTable.getColumns().addAll(barcode,name,stock,category,suplieri, sellprice);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(productTable);
		bp.setPadding(new Insets(30, 40, 30, 40));
		Scene scene = new Scene(bp,400,500);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Find & Double Click");
		stage.show();
		
		//Handle the double click
		productTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) 
		        {
		            Product p = (Product) productTable.getSelectionModel().getSelectedItem();        
		            stage.close();
					
					tfname.setText(p.getName());
					
		        }
		    }
		});
	}   
}
