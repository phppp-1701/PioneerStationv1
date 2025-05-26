package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Home_GUI(primaryStage, "2023NV000001");
//		new ThanhToan_GUI("2023NV000001", primaryStage, null);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
