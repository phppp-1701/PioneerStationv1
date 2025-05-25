package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import dao.NhanVien_DAO;
import dao.Ve_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Ve;
import entity.Ve.LoaiVe;
import entity.Ve.TrangThaiVe;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import gui.ThongKe_GUI;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyVe_GUI_Controller implements Initializable {
    private String maNhanVien;

    public QuanLyVe_GUI_Controller(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // FXML-injected fields
    @FXML
    private ImageView imgAnhNhanVien;
    @FXML
    private Label lblMaNhanVien;
    @FXML
    private Label lblTenNhanVien;
    @FXML
    private Label lblChucVu;
    @FXML
    private Label lblTimTenKhachHang;
    @FXML
    private TextField txtTimTenKhachHang;
    @FXML
    private Button btnTimVe;
    @FXML
    private TextField txtMaVe;
    @FXML
    private TextField txtNgayTaoVe;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtCCCD_HoChieu;
    @FXML
    private TextField txtNgaySinh;
    @FXML
    private ComboBox<String> cboLoaiVe;
    @FXML
    private TextField txtGiaVe;
    @FXML
    private TextField txtPhanTramGiamGia;
    @FXML
    private TextField txtMaHoaDon;
    @FXML
    private ComboBox<String> cboTrangThai;
    @FXML
    private TableView<Ve> tbDanhSachVe;
    @FXML
    private TableColumn<Ve, Integer> colSTT;
    @FXML
    private TableColumn<Ve, String> colMaVe;
    @FXML
    private TableColumn<Ve, String> colTenKhachHang;
    @FXML
    private TableColumn<Ve, String> colLoaiVe;
    @FXML
    private TableColumn<Ve, Double> colPhanTramGiamGiaCoDinh;
    @FXML
    private TableColumn<Ve, String> colCho;
    @FXML
    private TableColumn<Ve, Double> colGiaVe;
    @FXML
    private Label lblMenuHome;
    @FXML
    private Label lblMenuQuanLyBanVe;
    @FXML
    private Label lblMenuQuanLyLichSu;
    @FXML
    private Label lblMenuQuanLyVe;
    @FXML
    private Label lblMenuQuanLyHoaDon;
    @FXML
    private Label lblMenuQuanLyKhachHang;
    @FXML
    private Label lblMenuQuanLyNhanVien;
    @FXML
    private Label lblMenuQuanLyChuyenTau;
    @FXML
    private Label lblMenuThongKe;
    @FXML
    private Label lblMenuQuanLyTaiKhoan;
    @FXML
    private Label lblMenuDangXuat;

    private ObservableList<Ve> danhSachVe = FXCollections.observableArrayList();

    private static final DecimalFormat currencyFormat = new DecimalFormat("#,##0 VNĐ");
    private static final DecimalFormat percentFormat = new DecimalFormat("0.00%");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        hienThiThongTinNhanVien();

        cboTrangThai.setItems(FXCollections.observableArrayList(
            TrangThaiVe.hieuLuc.name(),
            TrangThaiVe.daHuy.name(),
            TrangThaiVe.daDoi.name(),
            TrangThaiVe.hetHan.name()
        ));
        cboLoaiVe.setItems(FXCollections.observableArrayList(
            LoaiVe.giuongNam.name(),
            LoaiVe.ngoiMem.name()
        ));

        hienLenDanhSachVe();

        btnTimVe.setOnAction(event -> timVeTheoTenKhachHang());

        tbDanhSachVe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            hienThiThongTinVe(newSelection);
        });

        lblMenuHome.setOnMouseClicked(event -> {
            try {
                chuyenSangGiaoDienHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyBanVe.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyBanVe();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyLichSu.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyLichSu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyVe.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyVe();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyHoaDon.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyHoaDon();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyKhachHang.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyKhachHang();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyChuyenTau.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyChuyenTau();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuThongKe.setOnMouseClicked(event -> {
            try {
                chuyenSangThongKe();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuQuanLyTaiKhoan.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyTaiKhoan();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lblMenuDangXuat.setOnMouseClicked(event -> {
            try {
                chuyenSangGiaoDienHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void hienThiThongTinNhanVien() {
        NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
        NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
        if (nv != null) {
            lblMaNhanVien.setText(nv.getMaNhanVien());
            lblTenNhanVien.setText(nv.getTenNhanVien());
            String urlAnh = nv.getUrlAnh();
            try {
                File imgFile = new File(urlAnh);
                Image image = new Image(imgFile.toURI().toString());
                imgAnhNhanVien.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            lblChucVu.setText(nv.getChucVu() == ChucVu.banVe ? "Bán vé" : "Quản lý");
        }
    }

    public void timVeTheoTenKhachHang() {
        if (tbDanhSachVe == null || txtTimTenKhachHang == null) {
            hienThiLoiVe("Lỗi", "TableView hoặc TextField tìm kiếm chưa được khởi tạo!");
            return;
        }

        String tenKhachHang = txtTimTenKhachHang.getText().trim();
        if (tenKhachHang.isEmpty()) {
            hienThiLoiVe("Lỗi", "Vui lòng nhập tên khách hàng để tìm kiếm!");
            return;
        }

        Ve_DAO ve_DAO = new Ve_DAO();
        List<Ve> veList = ve_DAO.timVeTheoTenKhachHang(tenKhachHang);

        danhSachVe.clear();
        if (veList != null && !veList.isEmpty()) {
            danhSachVe.addAll(veList); // Sửa: Thêm veList
            hienThiDanhSachVeLenBang(danhSachVe);
        } else {
            hienThiLoiVe("Thông báo", "Không tìm thấy vé nào cho khách hàng: " + tenKhachHang);
            tbDanhSachVe.getItems().clear();
        }
    }

    private void hienThiDanhSachVeLenBang(List<Ve> danhSachVe) {
        tbDanhSachVe.getItems().clear();
        ObservableList<Ve> dsVe = FXCollections.observableArrayList(danhSachVe);
        tbDanhSachVe.setItems(dsVe);
        tbDanhSachVe.refresh();
    }

    private void hienLenDanhSachVe() {
        colSTT.setCellValueFactory(col -> new ReadOnlyObjectWrapper<>(tbDanhSachVe.getItems().indexOf(col.getValue()) + 1));
        colMaVe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaVe()));
        colTenKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
        colLoaiVe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoaiVe().name()));
        colPhanTramGiamGiaCoDinh.setCellValueFactory(cellData ->
            new SimpleDoubleProperty(cellData.getValue().getPhanTramGiamGiaCoDinh()).asObject());
        colPhanTramGiamGiaCoDinh.setCellFactory(column -> new TableCell<Ve, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(percentFormat.format(item / 100)); // Phần trắm
                }
            }
        });

        // Configure Chỗ
        colCho.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCho() != null
                ? cellData.getValue().getCho().getMaCho() : ""));

        // Configure Giá Vé
        colGiaVe.setCellValueFactory(cellData ->
            new SimpleDoubleProperty(cellData.getValue().getGiaVe()).asObject());
        colGiaVe.setCellFactory(column -> new TableCell<Ve, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(currencyFormat.format(item)); // Format as currency (e.g., 1,000,000 VNĐ)
                }
            }
        });
    }

    private void hienThiThongTinVe(Ve ve) {
        if (ve == null) {
            txtMaVe.setText("");
            txtNgayTaoVe.setText("");
            txtTenKhachHang.setText("");
            txtCCCD_HoChieu.setText("");
            txtNgaySinh.setText("");
            cboLoaiVe.getSelectionModel().clearSelection();
            txtGiaVe.setText("");
            txtPhanTramGiamGia.setText("");
            txtMaHoaDon.setText("");
            cboTrangThai.getSelectionModel().clearSelection();
            return;
        }
        txtMaVe.setText(ve.getMaVe());
        txtNgayTaoVe.setText(ve.getNgayTaoVe() != null ? ve.getNgayTaoVe().format(dateFormatter) : "");
        txtTenKhachHang.setText(ve.getTenKhachHang());
        txtCCCD_HoChieu.setText(ve.getCccd_HoChieu());
        txtNgaySinh.setText(ve.getNgaySinh() != null ? ve.getNgaySinh().format(dateFormatter) : "");
        cboLoaiVe.getSelectionModel().select(ve.getLoaiVe().name());
        txtGiaVe.setText(ve.getGiaVe() > 0 ? currencyFormat.format(ve.getGiaVe()) : "");
        txtPhanTramGiamGia.setText(ve.getPhanTramGiamGiaCoDinh() > 0 ? percentFormat.format(ve.getPhanTramGiamGiaCoDinh() / 100) : "");
        txtMaHoaDon.setText(ve.getHoaDon() != null ? ve.getHoaDon().getMaHoaDon() : "");
        cboTrangThai.getSelectionModel().select(ve.getTrangThaiVe().name());
    }

    public void hienThiThongBao(String tieuDe, String noiDung) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(tieuDe);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    public void hienThiLoiVe(String tenLoi, String noiDungLoi) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(tenLoi);
        alert.setContentText(noiDungLoi);
        alert.showAndWait();
    }

    // Chuyển giao diện
    public void chuyenSangGiaoDienHome() throws IOException {
        Stage stage = (Stage) txtTenKhachHang.getScene().getWindow();
        new Home_GUI(stage, maNhanVien);
    }

    public void chuyenSangQuanLyBanVe() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyBanVe_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangQuanLyLichSu() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyLichSu_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangQuanLyVe() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyVe_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangQuanLyHoaDon() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyHoaDon_GUI(maNhanVien, primaryStage);
    }

    public void chuyenSangQuanLyKhachHang() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyKhachHang_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangQuanLyChuyenTau() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyChuyenTau_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangThongKe() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new ThongKe_GUI(primaryStage, maNhanVien);
    }

    public void chuyenSangQuanLyTaiKhoan() throws IOException {
        Stage primaryStage = (Stage) txtTenKhachHang.getScene().getWindow();
        new QuanLyTaiKhoan_GUI(maNhanVien, primaryStage);
    }
}