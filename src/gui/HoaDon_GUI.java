package gui;

import entity.HoaDon;
import gui.controllers.HoaDon_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HoaDon_GUI {

    private HoaDon hoaDon;
    private Stage primaryStage;
    private String maNhanVien;

    public HoaDon_GUI(HoaDon hoaDon, Stage primaryStage, String maNhanVien) {
        this.hoaDon = hoaDon;
        this.primaryStage = primaryStage;
        this.maNhanVien = maNhanVien;
        hienThiGiaoDien();
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
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

	private void hienThiGiaoDien() {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/HoaDon_GUI.fxml"));
            if (loader.getLocation() == null) {
                throw new RuntimeException("Không tìm thấy tệp FXML: /gui/resources/fxml/HoaDon_GUI.fxml");
            }
            // Gán controller thủ công
            HoaDon_GUI_Controller controller = new HoaDon_GUI_Controller(hoaDon, primaryStage, maNhanVien);
            loader.setController(controller);
            Parent root = loader.load();

            // Tạo Stage mới cho popup
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Thông tin hóa đơn");
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể hiển thị giao diện hóa đơn");
            alert.setContentText("Đã xảy ra lỗi: " + e.getMessage());
            alert.showAndWait();
        }
    }
}