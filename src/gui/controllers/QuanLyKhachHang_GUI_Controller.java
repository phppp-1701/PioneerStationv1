package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;
import entity.NhanVien.ChucVu;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyNhanVien_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import gui.ThongKe_GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyKhachHang_GUI_Controller implements Initializable {
    private String maNhanVien;

    public QuanLyKhachHang_GUI_Controller(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    // Khởi tạo các thành phần giao diện
    @FXML
    private ImageView imgAnhNhanVien;
    @FXML
    private Label lblMaNhanVien;
    @FXML
    private Label lblTenNhanVien;
    @FXML
    private Label lblChucVu;
    @FXML
    private Button btnTim; 
    @FXML
    private Button btnLamRong;
    @FXML
    private Button btnThemKhachHang;
    @FXML
    private Button btnCapNhat;
    @FXML
    private TextField txtTimTenKhachHang;
    @FXML
    private TextField txtTimSoDienThoai;
    @FXML
    private Label lblMaKhachHang;
    @FXML
    private Label lblTenKhachHang;
    @FXML
    private Label lblCCCD_HoChieu;
    @FXML
    private TextField txtSoDienThoai;
    @FXML
    private Label lblLoaiKhachHang;
    @FXML
    private Label lblTrangThai;
    @FXML
    private Label lblEmail;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private TextField txtCCCD_HoChieu;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<LoaiKhachHang> cboLoaiKhachHang;
    @FXML
    private ComboBox<TrangThaiKhachHang> cboTrangThai;
    @FXML
    private TableView<KhachHang> tbDanhSachKhachHang;
    @FXML
    private TableColumn<KhachHang, Integer> colStt;
    @FXML
    private TableColumn<KhachHang, String> colMaKhachHang;
    @FXML
    private TableColumn<KhachHang, String> colTen;
    @FXML
    private TableColumn<KhachHang, String> colSoDienThoai;
    @FXML
    private TableColumn<KhachHang, String> colEmail;
    @FXML
    private TableColumn<KhachHang, LoaiKhachHang> colLoaiKhachHang;
    @FXML
    private TableColumn<KhachHang, TrangThaiKhachHang> colTrangThaiKhachHang;
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
    @FXML
    private void hienThiThongTinKhachHang() {
    	
	}
    // Danh sách khách hàng để hiển thị trong TableView
    private ObservableList<KhachHang> danhSachKhachHang = FXCollections.observableArrayList();
    
	//Phương thức đưa thông tin nhân viên lên theo mã nhân viên
	public void hienThiThongTinNhanVien() {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
		if(nv!=null) {
			lblMaNhanVien.setText(nv.getMaNhanVien());
			lblTenNhanVien.setText(nv.getTenNhanVien());
			//Đưa đường dẫn ảnh vào image
			String urlAnh = nv.getUrlAnh();
			try {
	            File imgFile = new File(urlAnh);
	            Image image = new Image(imgFile.toURI().toString());
	            imgAnhNhanVien.setImage(image);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			if(nv.getChucVu().equals(ChucVu.banVe)) {
				lblChucVu.setText("Bán vé");
			}else {
				lblChucVu.setText("Quản lý");
			}
		}
	}

    // Phương thức tìm khách hàng theo tên và hiển thị trong TableView
    public void timKhachHangTheoTen() {
        if (tbDanhSachKhachHang == null || txtTimTenKhachHang== null) {
            System.err.println("TableView hoặc TextField tìm kiếm chưa được khởi tạo!");
            return;
        }

        String tenKhachHang = txtTimTenKhachHang.getText().trim();
        if (tenKhachHang.isEmpty()) {
            hienThiLoi("Lỗi", "Vui lòng nhập tên khách hàng để tìm kiếm!");
            return;
        }

        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        List<KhachHang> khList = khachHang_DAO.timKhachHangTheoTen(tenKhachHang);

        danhSachKhachHang.clear();
        if (khList != null && !khList.isEmpty()) {
            danhSachKhachHang.addAll(khList);
            tbDanhSachKhachHang.setItems(danhSachKhachHang);
            xoaRongKhachHang();
        } else {
            hienThiLoi("Lỗi", "Không tìm thấy khách hàng với tên: " + tenKhachHang);
            tbDanhSachKhachHang.getItems().clear();
            xoaRongKhachHang();
        }
    }

    // Phương thức tìm khách hàng theo số điện thoại và hiển thị trong TableView
    public void timKhachHangTheoSoDienThoai() {
        if (tbDanhSachKhachHang == null || txtTimSoDienThoai == null) {
            System.err.println("TableView hoặc TextField tìm kiếm số điện thoại chưa được khởi tạo!");
            return;
        }

        String soDienThoai = txtTimSoDienThoai.getText().trim();
        if (soDienThoai.isEmpty()) {
            hienThiLoi("Lỗi", "Vui lòng nhập số điện thoại để tìm kiếm!");
            return;
        }

        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        List<KhachHang> khList = khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai);

        danhSachKhachHang.clear();
        if (khList != null && !khList.isEmpty()) {
            danhSachKhachHang.addAll(khList);
            tbDanhSachKhachHang.setItems(danhSachKhachHang);
            xoaRongKhachHang();
        } else {
            hienThiLoi("Lỗi", "Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
            tbDanhSachKhachHang.getItems().clear();
            xoaRongKhachHang();
        }
    }

    // Phương thức tìm khách hàng bằng cả tên và số điện thoại
    public void timKhachHangTheoTenVaSdt() {
        if (tbDanhSachKhachHang == null || txtTimTenKhachHang == null || txtTimSoDienThoai == null) {
            System.err.println("TableView hoặc TextField tìm kiếm chưa được khởi tạo!");
            return;
        }

        String tenKhachHang = txtTimTenKhachHang.getText().trim();
        String soDienThoai = txtTimSoDienThoai.getText().trim();

        if (tenKhachHang.isEmpty() && soDienThoai.isEmpty()) {
            hienThiLoi("Lỗi", "Vui lòng nhập ít nhất một tiêu chí (tên hoặc số điện thoại) để tìm kiếm!");
            return;
        }

        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        List<KhachHang> khList = khachHang_DAO.timKhachHangTheoTenVaSdt(tenKhachHang, soDienThoai);

        danhSachKhachHang.clear();
        if (khList != null && !khList.isEmpty()) {
            danhSachKhachHang.addAll(khList);
            tbDanhSachKhachHang.setItems(danhSachKhachHang);
            xoaRongKhachHang();
        } else {
            hienThiLoi("Lỗi", "Không tìm thấy khách hàng với tiêu chí đã nhập!");
            tbDanhSachKhachHang.getItems().clear();
            xoaRongKhachHang();
        }
    }

    // Phương thức hiển thị thông tin chi tiết khách hàng khi nhấp vào hàng trong TableView
    private void hienThiThongTinKhachHang(KhachHang khachHang) {
        if (khachHang == null) {
            xoaRongKhachHang();
            return;
        }

        if (txtMaKhachHang == null || txtTenKhachHang == null || txtCCCD_HoChieu == null ||
            txtSoDienThoai == null || cboLoaiKhachHang == null || cboTrangThai == null || txtEmail == null) {
            System.err.println("Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        txtMaKhachHang.setText(khachHang.getMaKhachHang() != null ? khachHang.getMaKhachHang() : "");
        txtTenKhachHang.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
        txtCCCD_HoChieu.setText(khachHang.getCccd_HoChieu() != null ? khachHang.getCccd_HoChieu() : "");
        txtSoDienThoai.setText(khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "");
        cboLoaiKhachHang.setValue(khachHang.getLoaiKhachHang());
        cboTrangThai.setValue(khachHang.getTrangThaiKhachHang());
        txtEmail.setText(khachHang.getEmail() != null ? khachHang.getEmail() : "");
    }

    // Xóa nội dung các TextField và ComboBox
    private void xoaRongKhachHang() {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtCCCD_HoChieu.setText("");
        txtSoDienThoai.setText("");
        cboLoaiKhachHang.setValue(null);
        cboTrangThai.setValue(null);
        txtEmail.setText("");
    }

    // Hiển thị thông báo lỗi
    public void hienThiLoi(String tenLoi, String noiDungLoi) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(tenLoi);
        alert.setContentText(noiDungLoi);
        alert.showAndWait();
    }

    public void hienThiThongBao(String tieuDe, String noiDung) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(tieuDe);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
    @FXML
    private void capNhatThongTinKhachHang() {
        // Kiểm tra xem các thành phần giao diện đã được khởi tạo chưa
        if (txtMaKhachHang == null || txtTenKhachHang == null || txtCCCD_HoChieu == null ||
            txtSoDienThoai == null || txtEmail == null || cboLoaiKhachHang == null || cboTrangThai == null) {
            System.err.println("Một hoặc nhiều thành phần giao diện chưa được khởi tạo!");
            return;
        }

        // Lấy dữ liệu từ các trường nhập liệu
        String maKhachHang = txtMaKhachHang.getText().trim();
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
        TrangThaiKhachHang trangThaiKhachHang = cboTrangThai.getValue();

        // Kiểm tra ràng buộc dữ liệu
        if (maKhachHang.isEmpty() || tenKhachHang.isEmpty() || cccdHoChieu.isEmpty() || 
            soDienThoai.isEmpty() || email.isEmpty() || loaiKhachHang == null || trangThaiKhachHang == null) {
            hienThiLoi("Lỗi", "Vui lòng nhập đầy đủ thông tin khách hàng!");
            return;
        }

        // Tạo đối tượng khách hàng với thông tin mới
        KhachHang khachHangMoi = new KhachHang(maKhachHang, tenKhachHang, cccdHoChieu, soDienThoai, email, loaiKhachHang, trangThaiKhachHang);

        // Gọi DAO để cập nhật khách hàng trong cơ sở dữ liệu
        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        boolean thanhCong = khachHangDAO.capNhatKhachHang(khachHangMoi);

        if (thanhCong) {
            hienThiThongBao("Thành công", "Thông tin khách hàng đã được cập nhật!");
            capNhatThongTinKhachHang(); // Cập nhật danh sách khách hàng sau khi thay đổi
        } else {
            hienThiLoi("Lỗi", "Có lỗi xảy ra khi cập nhật khách hàng!");
        }
    }
    
    // Cập nhật lại danh sách khách hàng
    private void capNhatDanhSachKhachHang() {
        danhSachKhachHang.clear();
        KhachHang_DAO khachHangDAO = new KhachHang_DAO();
        List<KhachHang> danhSachMoi = khachHangDAO.layTatCaKhachHang(); // Lấy danh sách mới từ DB
        danhSachKhachHang.addAll(danhSachMoi);
        tbDanhSachKhachHang.setItems(danhSachKhachHang);
    }



    // Các phương thức chuyển giao diện
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
    
	//Chuyển sang giao diện quản lý nhân viên
	public void chuyenSangQuanLyNhanVien() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyNhanVien_GUI(primaryStage, maNhanVien);
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	hienThiThongTinNhanVien();
        // Khởi tạo danh sách cho ComboBox
        cboLoaiKhachHang.setItems(FXCollections.observableArrayList(LoaiKhachHang.values()));
        cboTrangThai.setItems(FXCollections.observableArrayList(TrangThaiKhachHang.values()));

        // Cấu hình các cột của TableView
        colStt.setCellValueFactory(cellData -> {
            int index = tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1;
            return javafx.beans.binding.Bindings.createObjectBinding(() -> index);
        });
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLoaiKhachHang.setCellValueFactory(new PropertyValueFactory<>("loaiKhachHang"));
        colTrangThaiKhachHang.setCellValueFactory(new PropertyValueFactory<>("trangThaiKhachHang"));

        // Gắn sự kiện cho các nút Tìm
        btnTim.setOnAction(event -> timKhachHangTheoTen());
        btnTim.setOnAction(event -> timKhachHangTheoSoDienThoai());
        btnTim.setOnAction(event -> timKhachHangTheoTenVaSdt());
        btnLamRong.setOnAction(event -> xoaRongKhachHang());
//        btnThemKhachHang.setOnAction(event -> themKhachHangMoi());
        btnCapNhat.setOnAction(event -> capNhatThongTinKhachHang());
        btnCapNhat.setOnAction(event -> capNhatDanhSachKhachHang());

        // Gắn sự kiện khi nhấp vào hàng trong TableView
        tbDanhSachKhachHang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            hienThiThongTinKhachHang(newSelection);
        });

        // Gắn sự kiện cho các menu
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
}