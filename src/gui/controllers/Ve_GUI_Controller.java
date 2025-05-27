package gui.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import entity.Cho;
import entity.ChuyenTau;
import entity.HoaDon;
import entity.TuyenTau;
import entity.ToaTau;
import entity.Ve;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Ve_GUI_Controller implements Initializable {
    private Ve ve;
    private String maNhanVien;
    private Stage primaryStage;

    public Ve_GUI_Controller(Ve ve, Stage primaryStage, String maNhanVien) {
        this.ve = ve;
        this.maNhanVien = maNhanVien;
        this.primaryStage = primaryStage;
    }

    // Getters và Setters
    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
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

    // Khai báo các Label
    @FXML
    private Label lbMaVe;
    @FXML
    private Label lbGaDi;
    @FXML
    private Label lbGaDen;
    @FXML
    private Label lbMaTau;
    @FXML
    private Label lbNgayDi;
    @FXML
    private Label lbToa;
    @FXML
    private Label lbCho;
    @FXML
    private Label lbGio;
    @FXML
    private Label lbLoaiCho;
    @FXML
    private Label lbLoaiVe;
    @FXML
    private Label lbHoTen;
    @FXML
    private Label lbLoaiKhachHang;
    @FXML
    private Label lbCccd;
    @FXML
    private Label lbGiaVe;
    @FXML
    private Label lbNgayIn;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Định dạng ngày giờ
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Hiển thị thông tin vé
        lbMaVe.setText(ve.getMaVe() != null ? ve.getMaVe() : "");
        lbHoTen.setText(ve.getTenKhachHang() != null ? ve.getTenKhachHang() : "");
        lbLoaiVe.setText(ve.getLoaiVe() != null ? ve.getLoaiVe().toString() : "");
        lbCccd.setText(ve.getCccd_HoChieu() != null ? ve.getCccd_HoChieu() : "");
        lbGiaVe.setText(ve.tinhGiaVe() != 0 ? String.format("%,.0f VNĐ", ve.tinhGiaVe()) : "0 VNĐ");
        lbNgayIn.setText(java.time.LocalDate.now().format(dateFormatter));

        // Hiển thị thông tin chuyến tàu
        ChuyenTau chuyenTau = ve.getChuyenTau();
        if (chuyenTau != null) {
            lbMaTau.setText(chuyenTau.getTau().getTenTau() != null ? chuyenTau.getTau().getTenTau() : ""); // Thay getTau().getTenTau() nếu không có
            lbNgayDi.setText(chuyenTau.getNgayKhoiHanh() != null ? chuyenTau.getNgayKhoiHanh().format(dateFormatter) : "");
            lbGio.setText(chuyenTau.getGioKhoiHanh() != null ? chuyenTau.getGioKhoiHanh().format(timeFormatter) : "");

            TuyenTau tuyenTau = chuyenTau.getTuyenTau();
            if (tuyenTau != null) {
                lbGaDi.setText(tuyenTau.getGaDi() != null ? tuyenTau.getGaDi().getTenGa() : "");
                lbGaDen.setText(tuyenTau.getGaDen() != null ? tuyenTau.getGaDen().getTenGa() : "");
            } else {
                lbGaDi.setText("");
                lbGaDen.setText("");
            }
        } else {
            lbMaTau.setText("");
            lbNgayDi.setText("");
            lbGio.setText("");
            lbGaDi.setText("");
            lbGaDen.setText("");
        }

        // Hiển thị thông tin toa và chỗ
        Cho cho = ve.getCho();
        if (cho != null) {
        	lbCho.setText(String.valueOf(cho.getSoThuTuCho()));
            ToaTau toaTau = cho.getToaTau();
            if (toaTau != null) {
            	lbToa.setText(String.valueOf(toaTau.getThuTuToa()));
                lbLoaiCho.setText(cho.getToaTau().getLoaiToa() != null ? cho.getToaTau().getLoaiToa().toString() : "");
            } else {
                lbToa.setText("");
                lbCho.setText("");
            }
        } else {
            lbCho.setText("");
            lbLoaiCho.setText("");
            lbToa.setText("");
        }

        HoaDon hoaDon = ve.getHoaDon();
        if (hoaDon != null && hoaDon.getKhachHang() != null) {
            lbLoaiKhachHang.setText(hoaDon.getKhachHang().getLoaiKhachHang() != null ? hoaDon.getKhachHang().getLoaiKhachHang().toString() : "");
        } else {
            lbLoaiKhachHang.setText("");
        }
    }
}