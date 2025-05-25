package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		new QuanLyChuyenTau_GUI(primaryStage, "2023NV000001");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
