package gui;

import java.io.File;
import java.io.IOException;
import gui.controllers.ThongKe_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ThongKe_GUI {
	private String maNhanVien;
	private Stage primaryStage;
	public ThongKe_GUI(Stage primaryStage, String maNhanVien) throws IOException {
		this.maNhanVien = maNhanVien;
		this.primaryStage = primaryStage;
		//Tải file fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/ThongKe_GUI.fxml"));
		// Lấy controller và thiết lập maNhanVien
        ThongKe_GUI_Controller controller = new ThongKe_GUI_Controller(maNhanVien);
        loader.setController(controller);
        //Đưa vào scene
		Scene scene = new Scene(loader.load());
		
		//Thiết lập cửa sổ
		primaryStage.setTitle("Pioneer station - Thống kê");
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
