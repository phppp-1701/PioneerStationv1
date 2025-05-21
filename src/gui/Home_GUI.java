package gui;

import java.io.File;
import java.io.IOException;
import gui.controllers.Home_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Home_GUI{
	private String maNhanVien;
	private Stage primaryStage;
	public Home_GUI(Stage primaryStage, String maNhanVien) throws IOException {
		this.maNhanVien = maNhanVien;
		this.primaryStage = primaryStage;
		//Tải file fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/Home_GUI.fxml"));
		// Lấy controller và thiết lập maNhanVien
        Home_GUI_Controller controller = new Home_GUI_Controller(maNhanVien);
        loader.setController(controller);
        //Đưa vào scene
		Scene scene = new Scene(loader.load());
		
		//Thiết lập cửa sổ
		primaryStage.setTitle("Pioneer station - Trang chủ");
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
