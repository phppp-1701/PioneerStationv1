package gui;

import entity.Ve;
import gui.controllers.HoaDon_GUI_Controller;
import gui.controllers.Ve_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Ve_GUI {
	private Ve ve;
	private Stage primaryStage;
    private String maNhanVien;
	public Ve getVe() {
		return ve;
	}
	public void setVe(Ve ve) {
		this.ve = ve;
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
	public Ve_GUI(Ve ve, Stage primaryStage, String maNhanVien) {
		super();
		this.ve = ve;
		this.primaryStage = primaryStage;
		this.maNhanVien = maNhanVien;
		hienThiGiaoDien();
	}
	
	private void hienThiGiaoDien() {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/Ve_GUI.fxml"));
            if (loader.getLocation() == null) {
                throw new RuntimeException("Không tìm thấy tệp FXML: /gui/resources/fxml/Ve_GUI.fxml");
            }
            // Gán controller thủ công
            Ve_GUI_Controller controller = new Ve_GUI_Controller(ve, primaryStage, maNhanVien);
            loader.setController(controller);
            Parent root = loader.load();

            // Tạo Stage mới cho popup
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Thông tin vé");
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể hiển thị giao diện vé");
            alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
