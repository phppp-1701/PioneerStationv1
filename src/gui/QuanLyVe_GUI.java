package gui;

import java.io.File;
import java.io.IOException;

import gui.controllers.QuanLyVe_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyVe_GUI {
	private String maNhanVien;
	private Stage primaryStage;
	public QuanLyVe_GUI(Stage primaryStage, String maNhanvien) throws IOException {
		this.maNhanVien = maNhanvien;
		this.primaryStage = primaryStage;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/QuanLyVe_GUI.fxml"));
		QuanLyVe_GUI_Controller controller = new QuanLyVe_GUI_Controller(maNhanvien);
		loader.setController(controller);
		Scene scene = new Scene(loader.load());
		
		//Thiết lập cửa sổ
		primaryStage.setTitle("Pioneer station - Quản lý vé");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		//Load icon
		try {
            File iconFile = new File("image/icon.png");
            Image icon = new Image(iconFile.toURI().toString());
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }	
		primaryStage.show();
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
}
