package gui;

import java.io.File;
import java.io.IOException;
import gui.controllers.QuanLyTaiKhoan_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QuanLyTaiKhoan_GUI {
	private String maNhanVien;
	private Stage primaryStage;
	public QuanLyTaiKhoan_GUI(String maNhanVien, Stage primaryStage) throws IOException {
		this.maNhanVien = maNhanVien;
		this.primaryStage = primaryStage;
		//Tải file fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/QuanLyTaiKhoan_GUI.fxml"));
		// Lấy controller và thiết lập maNhanVien
        QuanLyTaiKhoan_GUI_Controller controller = new QuanLyTaiKhoan_GUI_Controller(maNhanVien);
        loader.setController(controller);
        //Đưa vào scene
		Scene scene = new Scene(loader.load());
		
		//Thiết lập cửa sổ
		primaryStage.setTitle("Pioneer station - Quản lý tài khoản");
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
