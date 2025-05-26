package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import entity.Ve;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gui.controllers.ThanhToan_GUI_Controller;

public class ThanhToan_GUI {
    private String maNhanVien;
    private List<Ve> danhSachVe;
    private Stage popupStage;

    public ThanhToan_GUI(String maNhanVien, Stage ownerStage, List<Ve> danhSachVe) throws IOException {
        this.maNhanVien = maNhanVien;
        this.danhSachVe = danhSachVe;
        hienThiGiaoDien(ownerStage);
    }

    private void hienThiGiaoDien(Stage ownerStage) throws IOException {
        // Kiểm tra và tải tệp FXML
        URL fxmlLocation = getClass().getResource("/gui/resources/fxml/ThanhToan_GUI.fxml");
        if (fxmlLocation == null) {
            throw new IOException("Không tìm thấy ThanhToan_GUI.fxml trong /gui/");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        // Khởi tạo controller với maNhanVien và danhSachVe
        ThanhToan_GUI_Controller controller = new ThanhToan_GUI_Controller(maNhanVien, danhSachVe);
        loader.setController(controller);
        Parent root = loader.load();

        // Tạo scene và áp dụng CSS
        Scene scene = new Scene(root);
        URL cssLocation = getClass().getResource("/gui/ThanhToan_GUI.css");
        if (cssLocation != null) {
            scene.getStylesheets().add(cssLocation.toExternalForm());
        }

        // Tạo Stage mới cho pop-up
        popupStage = new Stage();
        popupStage.setTitle("Pioneer station - Thanh Toán");
        popupStage.setScene(scene);

        // Thiết lập pop-up là modal và liên kết với ownerStage
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);

        // Hiển thị pop-up
        popupStage.showAndWait();
    }

    // Phương thức để đóng pop-up (nếu cần từ controller)
    public void close() {
        if (popupStage != null) {
            popupStage.close();
        }
    }
}