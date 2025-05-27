package gui.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import dao.ChiTietCho_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.NhanVien_DAO;
import dao.Ve_DAO;
import entity.ChiTietCho;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.Ve.LoaiVe;
import gui.HoaDon_GUI;
import entity.Ve;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThanhToan_GUI_Controller implements Initializable {

    private String maNhanVien;
    private List<Ve> danhSachVe;

    public ThanhToan_GUI_Controller(String maNhanVien, List<Ve> danhSachVe) {
        this.maNhanVien = maNhanVien;
        this.danhSachVe = danhSachVe != null ? danhSachVe : new ArrayList<>();
        System.out.println("danhSachVe size: " + this.danhSachVe.size());
    }

    // Khai báo đối tượng
    @FXML private TextField txtTimSoDienThoai;
    @FXML private Button btnTim;
    @FXML private TableView<KhachHang> tbDanhSachKhachHang;
    @FXML private TableColumn<KhachHang, String> colSoThuTu;
    @FXML private TableColumn<KhachHang, String> colMaKhachHang;
    @FXML private TableColumn<KhachHang, String> colCCCDHoChieu;
    @FXML private TableColumn<KhachHang, String> colTenKhachHang;
    @FXML private TableColumn<KhachHang, String> colSoDienThoai;
    @FXML private TableColumn<KhachHang, String> colEmail;
    @FXML private TableColumn<KhachHang, String> colLoaiThanhVien;
    @FXML private TextField txtMaKhachHang;
    @FXML private TextField txtTenKhachHang;
    @FXML private TextField txtSoDienThoai;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCCCD_HoChieu;
    @FXML private ComboBox<LoaiKhachHang> cboLoaiKhachHang;
    @FXML private CheckBox ckcKhachVangLai;
    @FXML private Button btnLamRong;
    @FXML private Button btnThemKhachHang;
    @FXML private Button btnCapNhatKhachHang;
    @FXML private AnchorPane pnGioVe;
    @FXML private ComboBox<KhuyenMai> cboKhuyenMai;
    @FXML private TextField txtGiamGia;
    @FXML private TextField txtThanhTien;
    @FXML private Tab tabTienMat;
    @FXML private Tab tabATM;
    @FXML private Tab tabInternetBanking;
    @FXML private Button btnBanVe;
    @FXML private Button btnQuayLai;
    @FXML private Button btnGia1;
    @FXML private Button btnGia2;
    @FXML private Button btnGia3;
    @FXML private TextField txtTienKhachDua;
    @FXML private TextField txtTienTraLai;
    @FXML
    private Button btnThanhToan;

    // Mệnh giá tiền VNĐ
    private final double[] MENH_GIA_VND = {
        1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000
    };
    //Nhấn nút thanh toán
    @FXML
    private void nhanBtnThanhToan() {
        if (!ckcKhachVangLai.isSelected() && txtMaKhachHang.getText().isEmpty()) {
        	hienThiLoi("Lỗi thiếu thông tin khách hàng", "Hãy chọn checkbox vãng lai hoặc chọn khách hàng!");
            return;
        }

        HoaDon hoaDon = new HoaDon();
        HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
        hoaDon.setMaHoaDon(hoaDon_DAO.taoMaHoaDonMoi(maNhanVien));
        hoaDon.setNgayTaoHoaDon(LocalDate.now());
        hoaDon.setGioTaoHoaDon(LocalTime.now());
        hoaDon.setPhanTramGiamGia(cboKhuyenMai.getValue().getPhanTramGiamGiaSuKien());
        hoaDon.setKhuyenMai(cboKhuyenMai.getValue());

        double giaTamTinh = 0;
        for (Ve ve : danhSachVe) {
            giaTamTinh += ve.tinhGiaVe();
        }
        if (cboKhuyenMai.getValue() != null) {
            giaTamTinh = giaTamTinh * (1 - cboKhuyenMai.getValue().getPhanTramGiamGiaSuKien());
        }
        hoaDon.setThanhTien(giaTamTinh);

        if (tabTienMat.isSelected()) {
            hoaDon.setPhuongThucThanhToan(HoaDon.PhuongThucThanhToan.tienMat);
            hoaDon.setTienKhachDua(Double.parseDouble(txtTienKhachDua.getText().replace(",", "").trim()));
        } else {
            hoaDon.setPhuongThucThanhToan(HoaDon.PhuongThucThanhToan.chuyenKhoan);
            hoaDon.setTienKhachDua(giaTamTinh);
        }

        if (ckcKhachVangLai.isSelected()) {
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            KhachHang kh = khachHang_DAO.timKhachHangTheoMa("KHVL", true);
            hoaDon.setKhachHang(kh);
        } else {
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            KhachHang kh = khachHang_DAO.timKhachHangTheoMa(txtMaKhachHang.getText(), true);
            hoaDon.setKhachHang(kh);
        }

        NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
        NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien, true);
        hoaDon.setNhanVien(nv);
        
        if(hoaDon_DAO.themHoaDon(hoaDon)) {
            for (int i = 0; i < danhSachVe.size(); i++) {
                danhSachVe.get(i).setHoaDon(hoaDon);
                ChiTietCho_DAO chiTietCho_DAO = new ChiTietCho_DAO();
                ChiTietCho chiTietCho = chiTietCho_DAO.timChiTietChoTheoChoVaChuyenTau(danhSachVe.get(i).getCho(), danhSachVe.get(i).getChuyenTau(), true);
                chiTietCho_DAO.capNhatChiTietChoDaDat(chiTietCho, true);
                System.out.println("Cập nhật chi tiết chỗ thành công!");
                Ve_DAO ve_DAO = new Ve_DAO();
                if (!ve_DAO.themVe(danhSachVe.get(i))) {
                    System.out.println("Thêm vé: " + danhSachVe.get(i).getMaVe() + " thất bại!");
                } else {
                    System.out.println("Thêm vé: " + danhSachVe.get(i).getMaVe() + " thành công!");
                }
            }
            // Đóng ThanhToan_GUI và mở HoaDon_GUI
            Stage stage = (Stage)txtEmail.getScene().getWindow();
            new HoaDon_GUI(hoaDon, stage);
        }else {
        	System.out.println("Lỗi khi thêm hóa đơn!");
        }
    }
    // Cập nhật giá gợi ý
    private void capNhatMenhGiaGoiY() {
        double thanhTien = 0;
        try {
            for (Ve ve : danhSachVe) {
                thanhTien += ve.tinhGiaVe();
            }
            if (cboKhuyenMai.getValue() != null) {
                thanhTien = thanhTien * (1 - cboKhuyenMai.getValue().getPhanTramGiamGiaSuKien());
            }
            System.out.println("Calculated thanhTien: " + thanhTien);
        } catch (Exception e) {
            System.err.println("Lỗi tính toán thành tiền: " + e.getMessage());
            List<Button> buttons = List.of(btnGia1, btnGia2, btnGia3);
            buttons.forEach(btn -> {
                btn.setText("");
                btn.setDisable(true);
            });
            return;
        }

        List<Button> buttons = List.of(btnGia1, btnGia2, btnGia3);
        // Xóa nội dung cũ và vô hiệu hóa các nút
        buttons.forEach(btn -> {
            btn.setText("");
            btn.setDisable(true);
        });

        List<Double> suitableDenominations = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,##0");

        if (thanhTien <= 0) {
            // Nếu thanhTien <= 0, hiển thị 3 mệnh giá nhỏ nhất
            for (int i = 0; i < Math.min(3, MENH_GIA_VND.length); i++) {
                suitableDenominations.add(MENH_GIA_VND[i]);
            }
        } else {
            // Tìm mệnh giá gần nhất lớn hơn hoặc bằng thanhTien
            double baseDenomination = Math.ceil(thanhTien / 100000) * 100000; // Làm tròn lên đến 100,000
            suitableDenominations.add(baseDenomination);
            suitableDenominations.add(baseDenomination + 100000);
            suitableDenominations.add(baseDenomination + 200000);
        }

        // Điền nội dung cho các nút và in log để kiểm tra
        for (int i = 0; i < Math.min(suitableDenominations.size(), buttons.size()); i++) {
            String formattedText = formatter.format(suitableDenominations.get(i));
            buttons.get(i).textProperty().set(formattedText); // Sử dụng textProperty để đảm bảo cập nhật
            buttons.get(i).setDisable(false);
            buttons.get(i).getParent().requestLayout(); // Buộc cập nhật UI
            System.out.println("Set button " + (i + 1) + " text: " + formattedText);
        }
    }

    // Tính tiền trả lại
    private void tinhTienTraLai() {
        double thanhTien = 0;
        double tienKhachDua = 0;

        try {
            String thanhTienStr = txtThanhTien.getText().replace(" VNĐ", "").replace(",", "").trim();
            thanhTien = Double.parseDouble(thanhTienStr);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi chuyển đổi thành tiền: " + e.getMessage());
            txtTienTraLai.setText("Lỗi");
            return;
        }

        try {
            String tienKhachDuaStr = txtTienKhachDua.getText().replace(",", "").trim();
            tienKhachDua = Double.parseDouble(tienKhachDuaStr);
        } catch (NumberFormatException e) {
            txtTienTraLai.setText("0 VNĐ");
            return;
        }

        double tienTraLai = tienKhachDua - thanhTien;
        if (tienTraLai < 0) {
            txtTienTraLai.setText("Thiếu " + new DecimalFormat("#,##0 VNĐ").format(Math.abs(tienTraLai)));
        } else {
            txtTienTraLai.setText(new DecimalFormat("#,##0 VNĐ").format(tienTraLai));
        }
    }

    // Nhấn giá gợi ý
    @FXML
    private void handleMenhGiaButton(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String menhGiaText = clickedButton.getText().replace(",", "").trim();
        if (menhGiaText.isEmpty()) {
            System.err.println("Lỗi: Button text is empty");
            return;
        }
        try {
            double menhGia = Double.parseDouble(menhGiaText);
            txtTienKhachDua.setText(new DecimalFormat("#,##0").format(menhGia));
            tinhTienTraLai();
            System.out.println("Button clicked, set txtTienKhachDua to: " + txtTienKhachDua.getText());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi chuyển đổi mệnh giá: " + e.getMessage());
        }
    }

    // Đổ dữ liệu khuyến mãi lên combobox
    public void doDuLieuLenKhuyenMai() {
        LoaiKhachHang loai;
        KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
        if (ckcKhachVangLai.isSelected()) {
            loai = LoaiKhachHang.vangLai;
            System.out.println("Checkbox 'KhachVangLai' selected. LoaiKhachHang set to: " + loai);
        } else {
            loai = cboLoaiKhachHang.getValue();
            System.out.println("Checkbox 'KhachVangLai' not selected. LoaiKhachHang from ComboBox: " + loai);
        }

        if (loai != null) {
            List<KhuyenMai> dskm = khuyenMai_DAO.timKhuyenMaiTheoLoaiKhachHang(loai, true);
            System.out.println("Found " + dskm.size() + " promotions for type " + loai);
            cboKhuyenMai.getItems().clear();
            cboKhuyenMai.getItems().addAll(dskm);
            if (!dskm.isEmpty()) {
                cboKhuyenMai.setValue(dskm.get(0));
                System.out.println("Set default promotion: " + dskm.get(0).getTenKhuyenMai());
            } else {
                cboKhuyenMai.setValue(null);
                System.out.println("No promotions found for " + loai + ". ComboBox cleared.");
            }
        } else {
            cboKhuyenMai.getItems().clear();
            cboKhuyenMai.setValue(null);
            System.out.println("LoaiKhachHang is null. ComboBox cleared.");
        }
    }

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
        pnGioVe.getChildren().clear();
        pnGioVe.setPrefHeight(danhSachVe.size() * 300 + 20 * (danhSachVe.size() + 1));

        for (int i = 0; i < danhSachVe.size(); i++) {
            VBox veContainer = new VBox(10);
            veContainer.setLayoutX(6);
            veContainer.setLayoutY(20 + 310 * i + i * 20);
            veContainer.setPrefWidth(450);
            veContainer.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10; -fx-border-radius: 5;");

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

            HBox header = new HBox(20);
            header.setAlignment(Pos.CENTER);
            Label lblTuyen = new Label(gaDi + " → " + gaDen);
            lblTuyen.getStyleClass().add("label-header");
            Label lblGio = new Label(thoiGianDi + "\n" + ngayDi);
            lblGio.getStyleClass().add("label-header");
            header.getChildren().addAll(lblTuyen, lblGio);

            GridPane details = new GridPane();
            details.setHgap(10);
            details.setVgap(5);
            details.setPadding(new Insets(5, 0, 5, 0));

            String[] labels = {"Tàu:", "Toa:", "Chỗ:", "Giá:", "Loại vé:", "Khách hàng:", "Ngày sinh:", "CCCD/Hộ chiếu:"};
            String[] values = {tenTau, tenToa, tenCho, gia, loaiVe, tenKhachHang, ngaySinh, CCCD_hoChieu};

            for (int j = 0; j < labels.length; j++) {
                Label lblKey = new Label(labels[j]);
                lblKey.getStyleClass().add("label-key");
                Label lblValue = new Label(values[j]);
                lblValue.getStyleClass().add("label-value");
                details.add(lblKey, 0, j);
                details.add(lblValue, 1, j);
            }

            HBox footer = new HBox(10);
            footer.setAlignment(Pos.CENTER_RIGHT);
            Label lblTongTien = new Label("Tổng tiền: ");
            lblTongTien.getStyleClass().add("label-footer");
            Label lblGia = new Label(gia + " đ");
            lblGia.getStyleClass().add("label-footer");
            footer.getChildren().addAll(lblTongTien, lblGia);

            veContainer.getChildren().addAll(header, details, footer);
            pnGioVe.getChildren().add(veContainer);
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
        ObservableList<KhachHang> dsKhachHang = FXCollections.observableArrayList(dskh);
        tbDanhSachKhachHang.setItems(dsKhachHang);
    }
    @FXML
    private void nhanBtnQuayLai() {
        // Get the Stage from the button's scene
        Stage stage = (Stage) btnQuayLai.getScene().getWindow();
        // Close the popup
        stage.close();
    }

    @FXML
    private void nhanBtnLamRong() {
        txtMaKhachHang.clear();
        txtTenKhachHang.clear();
        txtSoDienThoai.clear();
        txtEmail.clear();
        txtCCCD_HoChieu.clear();
        txtTimSoDienThoai.clear();
        cboLoaiKhachHang.setValue(null);
        ckcKhachVangLai.setSelected(false);
        tbDanhSachKhachHang.getSelectionModel().clearSelection();
        doDuLieuLenKhuyenMai();
    }

    @FXML
    private void nhanBtnThemKhachHang() {
        if (ckcKhachVangLai.isSelected()) {
            hienThiLoi("Lỗi thêm khách hàng", "Không thể thêm khách hàng khi chọn chế độ khách vãng lai!");
            return;
        }
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
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
        String maKhachHang = "KH" + UUID.randomUUID().toString().substring(0, 8);
        KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, cccdHoChieu, soDienThoai, loaiKhachHang, TrangThaiKhachHang.hoatDong, email);
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        if (khachHang_DAO.themKhachHang(khachHang, true)) {
            hienThiThongTin("Thêm khách hàng", "Thêm khách hàng thành công!");
            nhanBtnLamRong();
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
        if (ckcKhachVangLai.isSelected()) {
            hienThiLoi("Lỗi cập nhật khách hàng", "Không thể cập nhật khách hàng khi chọn chế độ khách vãng lai!");
            return;
        }
        KhachHang selectedKhachHang = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
        if (selectedKhachHang == null) {
            hienThiLoi("Lỗi cập nhật", "Vui lòng chọn một khách hàng từ danh sách!");
            return;
        }
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
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
        KhachHang khachHang = new KhachHang(selectedKhachHang.getMaKhachHang(), tenKhachHang, cccdHoChieu, soDienThoai, loaiKhachHang, TrangThaiKhachHang.hoatDong, email);
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        if (khachHang_DAO.capNhatKhachHang(khachHang, true)) {
            hienThiThongTin("Cập nhật khách hàng", "Cập nhật khách hàng thành công!");
            List<KhachHang> dskh = khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai, true);
            tbDanhSachKhachHang.setItems(FXCollections.observableArrayList(dskh));
            nhanBtnLamRong();
        } else {
            hienThiLoi("Lỗi cập nhật khách hàng", "Không thể cập nhật khách hàng vào cơ sở dữ liệu!");
        }
    }

    private void capNhatTxtGiamGia() {
        KhuyenMai selectedKhuyenMai = cboKhuyenMai.getSelectionModel().getSelectedItem();
        if (selectedKhuyenMai != null) {
            double phanTramGiamGia = selectedKhuyenMai.getPhanTramGiamGiaSuKien();
            double giaTriGiamGia = phanTramGiamGia * 100;
            txtGiamGia.setText(String.format("%.2f %%", giaTriGiamGia));
        } else {
            txtGiamGia.setText("0.00 %%");
        }
    }

    private void capNhatThanhTien() {
        double giaTamTinh = 0;
        for (Ve ve : danhSachVe) {
            giaTamTinh += ve.tinhGiaVe();
        }
        if (cboKhuyenMai.getValue() != null) {
            giaTamTinh = giaTamTinh * (1 - cboKhuyenMai.getValue().getPhanTramGiamGiaSuKien());
        }
        DecimalFormat formatter = new DecimalFormat("#,##0 VNĐ");
        txtThanhTien.setText(formatter.format(giaTamTinh));
        System.out.println("Updated txtThanhTien: " + txtThanhTien.getText());
        Platform.runLater(this::capNhatMenhGiaGoiY); // Gọi trên UI thread để đảm bảo cập nhật
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTienTraLai.setEditable(false);
        txtGiamGia.setEditable(false);
        txtThanhTien.setEditable(false);

        hienThiVeLen();
        capNhatThanhTien();

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

        cboLoaiKhachHang.setItems(FXCollections.observableArrayList(LoaiKhachHang.values()));

        tbDanhSachKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtMaKhachHang.setText(newSelection.getMaKhachHang());
                txtTenKhachHang.setText(newSelection.getTenKhachHang());
                txtSoDienThoai.setText(newSelection.getSoDienThoai());
                txtEmail.setText(newSelection.getEmail() != null ? newSelection.getEmail() : "");
                txtCCCD_HoChieu.setText(newSelection.getCccd_HoChieu() != null ? newSelection.getCccd_HoChieu() : "");
                cboLoaiKhachHang.setValue(newSelection.getLoaiKhachHang());
            } else {
                txtMaKhachHang.clear();
                txtTenKhachHang.clear();
                txtSoDienThoai.clear();
                txtEmail.clear();
                txtCCCD_HoChieu.clear();
                cboLoaiKhachHang.setValue(null);
            }
            doDuLieuLenKhuyenMai();
        });

        ckcKhachVangLai.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                txtMaKhachHang.clear();
                txtTenKhachHang.clear();
                txtSoDienThoai.clear();
                txtEmail.clear();
                txtCCCD_HoChieu.clear();
                cboLoaiKhachHang.setValue(LoaiKhachHang.vangLai);
                txtMaKhachHang.setDisable(true);
                txtTenKhachHang.setDisable(true);
                txtSoDienThoai.setDisable(true);
                txtEmail.setDisable(true);
                txtCCCD_HoChieu.setDisable(true);
            } else {
                txtMaKhachHang.setDisable(false);
                txtTenKhachHang.setDisable(false);
                txtSoDienThoai.setDisable(false);
                txtEmail.setDisable(false);
                txtCCCD_HoChieu.setDisable(false);
                cboLoaiKhachHang.setValue(null);
            }
            doDuLieuLenKhuyenMai();
        });

        cboLoaiKhachHang.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!ckcKhachVangLai.isSelected()) {
                doDuLieuLenKhuyenMai();
            }
        });

        doDuLieuLenKhuyenMai();

        cboKhuyenMai.valueProperty().addListener((obs, oldVal, newVal) -> {
            capNhatTxtGiamGia();
            capNhatThanhTien();
        });

        tabTienMat.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                Platform.runLater(this::capNhatMenhGiaGoiY);
                System.out.println("Tab Tiền Mặt selected, updating denominations");
            }
        });

        txtTienKhachDua.textProperty().addListener((obs, oldVal, newVal) -> {
            String cleanedText = newVal.replaceAll("[^\\d.]", "");
            txtTienKhachDua.setText(cleanedText);
            tinhTienTraLai();
        });

        // Gán handler cho các nút mệnh giá và xóa text mặc định
        List.of(btnGia1, btnGia2, btnGia3).forEach(btn -> {
            btn.textProperty().set(""); // Xóa text mặc định từ FXML
            btn.setOnAction(this::handleMenhGiaButton);
        });
    }
}