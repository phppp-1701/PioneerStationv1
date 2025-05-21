package gui;

import java.io.File;
import java.io.IOException;

import gui.controllers.QuanLyLichSu_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyLichSu_GUI {
	private Stage primaryStage;
	private String maNhanVien;
	public QuanLyLichSu_GUI(Stage primaryStage, String maNhanVien) throws IOException {
		this.primaryStage = primaryStage;
		this.maNhanVien = maNhanVien;
		
		//Mở file fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/QuanLyLichSu_GUI.fxml"));
		QuanLyLichSu_GUI_Controller controller = new QuanLyLichSu_GUI_Controller(maNhanVien);
		loader.setController(controller);
		Scene scene = new Scene(loader.load());
		
		//Thiết lập cửa sổ
		primaryStage.setTitle("Pioneer station - Quản lý lịch sử");
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
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	
}
