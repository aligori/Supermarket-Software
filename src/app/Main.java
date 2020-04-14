package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.LogIn;

public class Main extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		(new LogIn()).start(stage);
		stage.getIcons().add(new Image("images/logo.jpg"));
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
