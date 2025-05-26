package gui.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import dao.ChiTietCho_DAO;
import dao.KhachHang_DAO;
import entity.ChiTietCho;
import entity.KhachHang;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;
import entity.Ve.LoaiVe;
import entity.Ve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ThanhToan_GUI_Controller implements Initializable {
    private String maNhanVien;
    private List<Ve> danhSachVe;

    public ThanhToan_GUI_Controller(String maNhanVien, List<Ve> danhSachVe) {
        this.maNhanVien = maNhanVien;
        this.danhSachVe = danhSachVe != null ? danhSachVe : new ArrayList<>();
    }
    
    // Khai báo đối tượng
    @FXML
    private TextField txtTimSoDienThoai;
    @FXML 
    private Button btnTim;
    @FXML
    private TableView<KhachHang> tbDanhSachKhachHang;
    @FXML
    private TableColumn<KhachHang, String> colSoThuTu;
    @FXML
    private TableColumn<KhachHang, String> colMaKhachHang;
    @FXML
    private TableColumn<KhachHang, String> colCCCDHoChieu;
    @FXML
    private TableColumn<KhachHang, String> colTenKhachHang;
    @FXML
    private TableColumn<KhachHang, String> colSoDienThoai;
    @FXML
    private TableColumn<KhachHang, String> colEmail;
    @FXML
    private TableColumn<KhachHang, String> colLoaiThanhVien;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtSoDienThoai;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCCCD_HoChieu;
    @FXML
    private ComboBox<LoaiKhachHang> cboLoaiKhachHang;
    @FXML
    private CheckBox ckcKhachVangLai;
    @FXML
    private Button btnLamRong;
    @FXML
    private Button btnThemKhachHang;
    @FXML
    private Button btnCapNhatKhachHang;
    @FXML
    private AnchorPane pnGioVe;
    // Phương thức
    
    // Hiển thị lỗi
    public void hienThiLoi(String tenLoi, String noiDungLoi) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(tenLoi);
        alert.setContentText(noiDungLoi);
        alert.showAndWait();
    }
    // Hiển thị thông báo thành công
    public void hienThiThongTin(String tenThongTin, String noiDungThongTin) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Thông tin");
        alert.setHeaderText(tenThongTin);
        alert.setContentText(noiDungThongTin);
        alert.showAndWait();
    }
    
    public void hienThiVeLen() {
        // Xóa nội dung cũ để tránh chồng lấn
        pnGioVe.getChildren().clear();

        // Tính chiều cao tổng dựa trên số vé
        pnGioVe.setPrefHeight(danhSachVe.size() * 250 + 10 * (danhSachVe.size() + 1));
        int x = 6;
        int y = 7;

        for (int i = 0; i < danhSachVe.size(); i++) {
            // Tạo Rectangle với chiều cao linh hoạt
            Rectangle recVe = new Rectangle();
            recVe.setFill(Color.WHITE);
            recVe.getStyleClass().add("rec-thongTin");
            recVe.setWidth(448);
            recVe.setHeight(220); // Chiều cao mặc định, có thể mở rộng
            recVe.setLayoutX(x);
            recVe.setLayoutY(y + 250 * i + i * 10);
            pnGioVe.getChildren().add(recVe);

            // Lấy thông tin vé
            Ve ve = danhSachVe.get(i);
            String tenTau = ve.getChuyenTau().getTau().getTenTau() != null ? ve.getChuyenTau().getTau().getTenTau() : "Không xác định";
            String tenToa = ve.getCho().getToaTau().getThuTuToa()+"" != null ? ve.getCho().getToaTau().getThuTuToa() + "" : "N/A";
            String gaDi = ve.getChuyenTau().getTuyenTau().getGaDi().getTenGa() != null ? ve.getChuyenTau().getTuyenTau().getGaDi().getTenGa() : "N/A";
            String gaDen = ve.getChuyenTau().getTuyenTau().getGaDen().getTenGa() != null ? ve.getChuyenTau().getTuyenTau().getGaDen().getTenGa() : "N/A";
            DateTimeFormatter timef = DateTimeFormatter.ofPattern("hh:mm a");
            String thoiGianDi = ve.getChuyenTau().getGioKhoiHanh() != null ? timef.format(ve.getChuyenTau().getGioKhoiHanh()) : "N/A";
            DateTimeFormatter ngayf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayDi = ve.getChuyenTau().getNgayKhoiHanh() != null ? ngayf.format(ve.getChuyenTau().getNgayKhoiHanh()) : "N/A";
            String tenCho = ve.getCho().getSoThuTuCho()+"" != null ? ve.getCho().getSoThuTuCho() + "" : "N/A";
            String gia = String.format("%.0fk", ve.tinhGiaVe() / 1000);
            String loaiVe = ve.getLoaiVe() != null ? ve.getLoaiVe().toString() : "N/A";
            String tenKhachHang = ve.getTenKhachHang() != null ? ve.getTenKhachHang() : "N/A";
            String ngaySinh = ve.getNgaySinh() != null ? ngayf.format(ve.getNgaySinh()) : "N/A";
            String CCCD_hoChieu = ve.getLoaiVe() != null && ve.getLoaiVe().equals(LoaiVe.treEm) ? "Không có" : (ve.getCccd_HoChieu() != null ? ve.getCccd_HoChieu() : "N/A");

            // Định nghĩa các cặp nhãn và giá trị
            String[] labels = {"Tàu:", "Toa:", "Ga đi:", "Ga đến:", "Giờ đi:", "Ngày đi:", "Chỗ:", "Giá:", "Loại vé:", "Khách hàng:", "Ngày sinh:", "CCCD/Hộ chiếu:"};
            String[] values = {tenTau, tenToa, gaDi, gaDen, thoiGianDi, ngayDi, tenCho, gia, loaiVe, tenKhachHang, ngaySinh, CCCD_hoChieu};

            // Tọa độ bắt đầu cho nhãn và giá trị
            double labelX = x + 10; // Lề trái trong Rectangle
            double valueX = labelX + 150; // Cách nhãn 150px để giá trị bên phải
            double startY = y + 250 * i + i * 10 + 10; // Lề trên của Rectangle
            double lineHeight = 18; // Khoảng cách giữa các dòng

            // Thêm nhãn và giá trị vào Rectangle
            for (int j = 0; j < labels.length; j++) {
                Label lblKey = new Label(labels[j]);
                lblKey.setLayoutX(labelX);
                lblKey.setLayoutY(startY + j * lineHeight);
                lblKey.getStyleClass().add("label-key");

                Label lblValue = new Label(values[j]);
                lblValue.setLayoutX(valueX);
                lblValue.setLayoutY(startY + j * lineHeight);
                lblValue.getStyleClass().add("label-value");

                // Đảm bảo chiều cao Rectangle đủ chứa nội dung
                if (j * lineHeight + lineHeight > recVe.getHeight() - 20) {
                    recVe.setHeight(recVe.getHeight() + lineHeight);
                    pnGioVe.setPrefHeight(pnGioVe.getPrefHeight() + lineHeight);
                }

                pnGioVe.getChildren().addAll(lblKey, lblValue);
            }
        }
    }    
    @FXML
    private void nhanBtnTim() {
        String soDienThoai = txtTimSoDienThoai.getText();
        if (soDienThoai.equals("")) {
            hienThiLoi("Lỗi tìm kiếm", "Bạn chưa nhập số điện thoại để tìm kiếm!");
            txtTimSoDienThoai.requestFocus();
            return;
        }
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        List<KhachHang> dskh = khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai, true);
        if (dskh.isEmpty()) {
            hienThiLoi("Lỗi tìm kiếm", "Số điện thoại chưa tạo khách hàng thân thiết!");
            txtTimSoDienThoai.requestFocus();
            txtTimSoDienThoai.selectAll();
            return;
        }
        
        // Hiển thị danh sách khách hàng lên TableView
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList(dskh);
        tbDanhSachKhachHang.setItems(dsKhachHang);
    }

    @FXML
    private void nhanBtnLamRong() {
        // Xóa các TextField
        txtMaKhachHang.clear();
        txtTenKhachHang.clear();
        txtSoDienThoai.clear();
        txtEmail.clear();
        txtCCCD_HoChieu.clear();
        txtTimSoDienThoai.clear();
        // Đặt ComboBox về null
        cboLoaiKhachHang.setValue(null);
        // Bỏ chọn CheckBox
        ckcKhachVangLai.setSelected(false);
        // Bỏ chọn dòng trong TableView
        tbDanhSachKhachHang.getSelectionModel().clearSelection();
    }

    @FXML
    private void nhanBtnThemKhachHang() {
        // Kiểm tra CheckBox khách vãng lai
        if (ckcKhachVangLai.isSelected()) {
            hienThiLoi("Lỗi thêm khách hàng", "Không thể thêm khách hàng khi chọn chế độ khách vãng lai!");
            return;
        }

        // Lấy dữ liệu từ các trường
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();

        // Kiểm tra dữ liệu hợp lệ
        if (tenKhachHang.isEmpty()) {
            hienThiLoi("Lỗi nhập liệu", "Tên khách hàng không được để trống!");
            txtTenKhachHang.requestFocus();
            return;
        }
        if (soDienThoai.isEmpty()) {
            hienThiLoi("Lỗi nhập liệu", "Số điện thoại không được để trống!");
            txtSoDienThoai.requestFocus();
            return;
        }
        if (!soDienThoai.matches("\\d{10}")) {
            hienThiLoi("Lỗi nhập liệu", "Số điện thoại phải có 10 chữ số!");
            txtSoDienThoai.requestFocus();
            return;
        }
        if (!cccdHoChieu.isEmpty() && !cccdHoChieu.matches("\\d{12}")) {
            hienThiLoi("Lỗi nhập liệu", "CCCD/Hộ chiếu phải có 12 chữ số nếu được nhập!");
            txtCCCD_HoChieu.requestFocus();
            return;
        }
        if (loaiKhachHang == null) {
            hienThiLoi("Lỗi nhập liệu", "Vui lòng chọn loại khách hàng!");
            cboLoaiKhachHang.requestFocus();
            return;
        }
        if (loaiKhachHang == LoaiKhachHang.vangLai) {
            hienThiLoi("Lỗi nhập liệu", "Không thể thêm khách hàng với loại khách vãng lai!");
            cboLoaiKhachHang.requestFocus();
            return;
        }

        // Tạo mã khách hàng (UUID hoặc từ DB)
        String maKhachHang = "KH" + UUID.randomUUID().toString().substring(0, 8);

        // Tạo đối tượng KhachHang
        KhachHang khachHang = new KhachHang(
            maKhachHang,
            tenKhachHang,
            cccdHoChieu,
            soDienThoai,
            loaiKhachHang,
            TrangThaiKhachHang.hoatDong,
            email
        );

        // Thêm vào cơ sở dữ liệu
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        if (khachHang_DAO.themKhachHang(khachHang,true)) {
            hienThiThongTin("Thêm khách hàng", "Thêm khách hàng thành công!");
            nhanBtnLamRong(); // Làm rỗng sau khi thêm
            // Cập nhật TableView
            if (!txtTimSoDienThoai.getText().isEmpty()) {
                List<KhachHang> dskh = khachHang_DAO.timKhachHangTheoSoDienThoai(txtTimSoDienThoai.getText(), true);
                tbDanhSachKhachHang.setItems(FXCollections.observableArrayList(dskh));
            }
        } else {
            hienThiLoi("Lỗi thêm khách hàng", "Không thể thêm khách hàng vào cơ sở dữ liệu!");
        }
    }

    @FXML
    private void nhanBtnCapNhatKhachHang() {
        // Kiểm tra CheckBox khách vãng lai
        if (ckcKhachVangLai.isSelected()) {
            hienThiLoi("Lỗi cập nhật khách hàng", "Không thể cập nhật khách hàng khi chọn chế độ khách vãng lai!");
            return;
        }

        // Kiểm tra có khách hàng được chọn
        KhachHang selectedKhachHang = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
        if (selectedKhachHang == null) {
            hienThiLoi("Lỗi cập nhật", "Vui lòng chọn một khách hàng từ danh sách!");
            return;
        }

        // Lấy dữ liệu từ các trường
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();

        // Kiểm tra dữ liệu hợp lệ
        if (tenKhachHang.isEmpty()) {
            hienThiLoi("Lỗi nhập liệu", "Tên khách hàng không được để trống!");
            txtTenKhachHang.requestFocus();
            return;
        }
        if (soDienThoai.isEmpty()) {
            hienThiLoi("Lỗi nhập liệu", "Số điện thoại không được để trống!");
            txtSoDienThoai.requestFocus();
            return;
        }
        if (!soDienThoai.matches("\\d{10}")) {
            hienThiLoi("Lỗi nhập liệu", "Số điện thoại phải có 10 chữ số!");
            txtSoDienThoai.requestFocus();
            return;
        }
        if (!cccdHoChieu.isEmpty() && !cccdHoChieu.matches("\\d{12}")) {
            hienThiLoi("Lỗi nhập liệu", "CCCD/Hộ chiếu phải có 12 chữ số nếu được nhập!");
            txtCCCD_HoChieu.requestFocus();
            return;
        }
        if (loaiKhachHang == null) {
            hienThiLoi("Lỗi nhập liệu", "Vui lòng chọn loại khách hàng!");
            cboLoaiKhachHang.requestFocus();
            return;
        }
        if (loaiKhachHang == LoaiKhachHang.vangLai) {
            hienThiLoi("Lỗi nhập liệu", "Không thể cập nhật khách hàng với loại khách vãng lai!");
            cboLoaiKhachHang.requestFocus();
            return;
        }

        // Tạo đối tượng KhachHang với thông tin cập nhật
        KhachHang khachHang = new KhachHang(
                selectedKhachHang.getMaKhachHang(),
                tenKhachHang,
                cccdHoChieu,
                soDienThoai,
                loaiKhachHang,
                TrangThaiKhachHang.hoatDong,
                email
            );

        // Cập nhật vào cơ sở dữ liệu
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        if (khachHang_DAO.capNhatThongTinKhachHang(khachHang)) {
            hienThiThongTin("Cập nhật khách hàng", "Cập nhật khách hàng thành công!");
            // Cập nhật TableView
            List<KhachHang> dskh = khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai, true);
            tbDanhSachKhachHang.setItems(FXCollections.observableArrayList(dskh));
            nhanBtnLamRong(); // Làm rỗng sau khi cập nhật
        } else {
            hienThiLoi("Lỗi cập nhật khách hàng", "Không thể cập nhật khách hàng vào cơ sở dữ liệu!");
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hienThiVeLen();
    	// Cấu hình cellValueFactory cho các cột
        colSoThuTu.setCellValueFactory(cellData -> {
            int index = tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1;
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(index));
        });
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        colCCCDHoChieu.setCellValueFactory(new PropertyValueFactory<>("cccd_HoChieu"));
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLoaiThanhVien.setCellValueFactory(new PropertyValueFactory<>("loaiKhachHang"));

        // Khởi tạo ComboBox LoaiKhachHang
        cboLoaiKhachHang.setItems(FXCollections.observableArrayList(LoaiKhachHang.values()));

        // Xử lý sự kiện chọn dòng trong TableView
        tbDanhSachKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtMaKhachHang.setText(newSelection.getMaKhachHang());
                txtTenKhachHang.setText(newSelection.getTenKhachHang());
                txtSoDienThoai.setText(newSelection.getSoDienThoai());
                txtEmail.setText(newSelection.getEmail() != null ? newSelection.getEmail() : "");
                txtCCCD_HoChieu.setText(newSelection.getCccd_HoChieu() != null ? newSelection.getCccd_HoChieu() : "");
                cboLoaiKhachHang.setValue(newSelection.getLoaiKhachHang());
            } else {
                // Xóa các trường nếu không có dòng nào được chọn
                txtMaKhachHang.clear();
                txtTenKhachHang.clear();
                txtSoDienThoai.clear();
                txtEmail.clear();
                txtCCCD_HoChieu.clear();
                cboLoaiKhachHang.setValue(null);
            }
        });
        // Xử lý sự kiện CheckBox ckcKhachVangLai
        ckcKhachVangLai.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                // Xóa nội dung các TextField
                txtMaKhachHang.clear();
                txtTenKhachHang.clear();
                txtSoDienThoai.clear();
                txtEmail.clear();
                txtCCCD_HoChieu.clear();
                // Đặt ComboBox về VANG_LAI
                cboLoaiKhachHang.setValue(LoaiKhachHang.vangLai);
                // Vô hiệu hóa các TextField
                txtMaKhachHang.setDisable(true);
                txtTenKhachHang.setDisable(true);
                txtSoDienThoai.setDisable(true);
                txtEmail.setDisable(true);
                txtCCCD_HoChieu.setDisable(true);
            } else {
                // Kích hoạt các TextField
                txtMaKhachHang.setDisable(false);
                txtTenKhachHang.setDisable(false);
                txtSoDienThoai.setDisable(false);
                txtEmail.setDisable(false);
                txtCCCD_HoChieu.setDisable(false);
            }
        });
    }
}