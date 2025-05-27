package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tạo Stage cho màn hình loading
        Stage loadingStage = new Stage();
        loadingStage.initStyle(StageStyle.UNDECORATED); // Không có thanh tiêu đề

        // Tải file Loading_GUI.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/Loading_GUI.fxml"));
        Parent loadingRoot = loader.load();
        Scene loadingScene = new Scene(loadingRoot); // Kích thước có thể điều chỉnh

        // Thiết lập và hiển thị màn hình loading
        loadingStage.setScene(loadingScene);
        loadingStage.show();

        // Chạy tác vụ tải trong luồng nền
        new Thread(() -> {
            try {
                // Giả lập thời gian tải (thay bằng logic tải thực tế nếu cần)
                Thread.sleep(2000); // Tạm dừng 2 giây

                // Sau khi tải xong, đóng màn hình loading và mở DangNhap_GUI
                Platform.runLater(() -> {
                    loadingStage.close(); // Đóng màn hình loading
                    try {
                        new DangNhap_GUI(primaryStage); // Mở giao diện đăng nhập
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}