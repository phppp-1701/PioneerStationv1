package gui;

import entity.HoaDon;
import gui.controllers.HoaDon_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HoaDon_GUI {

    private HoaDon hoaDon;
    private Stage primaryStage;

    public HoaDon_GUI(HoaDon hoaDon, Stage primaryStage) {
        this.hoaDon = hoaDon;
        this.primaryStage = primaryStage;
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


	private void hienThiGiaoDien() {
        try {
            // Tải file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/HoaDon_GUI.fxml"));
            Parent root = loader.load();

            // Lấy controller và truyền dữ liệu
            HoaDon_GUI_Controller controller = new HoaDon_GUI_Controller(hoaDon);
            loader.setController(controller);

            // Tạo Stage mới cho popup
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Thông tin hóa đơn");
            stage.initModality(Modality.APPLICATION_MODAL); // Popup chặn tương tác với các cửa sổ khác
            stage.setResizable(false);
            stage.showAndWait(); // Chờ người dùng đóng popup
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}