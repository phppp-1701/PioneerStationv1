package gui;

import java.io.File;
import java.io.IOException;
import gui.controllers.Home_GUI_Controller;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Home_GUI {
    private String maNhanVien;
    private Stage primaryStage;

    public Home_GUI(Stage primaryStage, String maNhanVien) throws IOException {
        this.maNhanVien = maNhanVien;
        this.primaryStage = primaryStage;

        // Tải file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/Home_GUI.fxml"));
        // Lấy controller và thiết lập maNhanVien
        Home_GUI_Controller controller = new Home_GUI_Controller(maNhanVien);
        loader.setController(controller);

        // === PHẦN SCALE THEO MÀN HÌNH ===
        double originalWidth = 1540;
        double originalHeight = 795;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        double scaleX = screenWidth / originalWidth;
        double scaleY = screenHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        // Tạo scene với kích thước tương ứng
        Scene scene = new Scene(loader.load(), originalWidth * scale, originalHeight * scale);
        // Áp dụng scale transform cho root node
        scene.getRoot().getTransforms().add(new Scale(scale, scale, 0, 0));
        // === HẾT PHẦN SCALE ===

        // CSS
        scene.getStylesheets().add(getClass().getResource("/gui/resources/css/Home_GUI.css").toExternalForm());

        // Thiết lập cửa sổ
        primaryStage.setTitle("PIONEER STATION - Trang chủ");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Giao diện full màn hình

        // Load icon
        try {
            File iconFile = new File("image/icon.png");
            Image icon = new Image(iconFile.toURI().toString());
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Không tải được icon: " + e.getMessage());
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
