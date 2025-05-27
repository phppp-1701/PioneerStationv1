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
    private TextField txtSoDienThoai;
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
    private TableColumn<KhachHang, String> colTenKhachHang;
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
    
	//Phương thức đưa thông tin nhân viên lên theo mã nhân viên
	public void hienThiThongTinNhanVien() {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien, true);
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
	
	@FXML
    public void btnTimKhachHangClicked() {
        String tenKhachHang = txtTimTenKhachHang.getText().trim();
        String soDienThoai = txtTimSoDienThoai.getText().trim();

        // Kiểm tra nếu cả hai trường đều rỗng
        if (tenKhachHang.equals("") && soDienThoai.equals("")) {
            hienThiLoi("Bạn chưa nhập tiêu chí để tìm!", "Vui lòng nhập ít nhất một trong các trường: tên hoặc số điện thoại.");
            txtTimTenKhachHang.requestFocus();
        } else {
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            List<KhachHang> dsKhachHang;

            // Tìm kiếm dựa trên tiêu chí
            if (!tenKhachHang.equals("") && !soDienThoai.equals("")) {
                // Tìm theo cả tên và số điện thoại
                dsKhachHang = khachHang_DAO.timKhachHangTheoTenVaSdt(tenKhachHang, soDienThoai, true);
            } else if (!tenKhachHang.equals("")) {
                // Tìm theo tên
                dsKhachHang = khachHang_DAO.timKhachHangTheoTen(tenKhachHang, true);
            } else {
                // Tìm theo số điện thoại
                dsKhachHang = khachHang_DAO.timKhachHangTheoSoDienThoai(soDienThoai, true);
            }

            if (dsKhachHang.isEmpty()) {
                hienThiLoi("Không tìm thấy khách hàng theo tiêu chí được nhập!", "");
                if (!tenKhachHang.equals("")) {
                    txtTimTenKhachHang.requestFocus();
                    txtTimTenKhachHang.selectAll();
                } else {
                    txtTimSoDienThoai.requestFocus();
                    txtTimSoDienThoai.selectAll();
                }
            } else {
                tbDanhSachKhachHang.getItems().clear();

                // Tạo ObservableList từ danh sách khách hàng
                ObservableList<KhachHang> data = FXCollections.observableArrayList(dsKhachHang);

                // Đặt dữ liệu vào table
                tbDanhSachKhachHang.setItems(data);
            }
        }
    }

    @FXML
    public void btnThemKhachHangClicked() {
        String tenKhachHang = txtTenKhachHang.getText().trim();
        String cccd_HoChieu = txtCCCD_HoChieu.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
        TrangThaiKhachHang trangThai = cboTrangThai.getValue();
        String email = txtEmail.getText().trim();

        // Kiểm tra các trường bắt buộc (loại bỏ kiểm tra mã vì tự động tạo)
        if (tenKhachHang.equals("") || cccd_HoChieu.equals("") || 
            soDienThoai.equals("") || loaiKhachHang == null || trangThai == null) {
            hienThiLoi("Bạn chưa nhập đầy đủ thông tin!", "Vui lòng nhập tên, CCCD, số điện thoại, loại và trạng thái khách hàng.");
            if (tenKhachHang.equals("")) txtTenKhachHang.requestFocus();
            else if (cccd_HoChieu.equals("")) txtCCCD_HoChieu.requestFocus();
            else if (soDienThoai.equals("")) txtSoDienThoai.requestFocus();
            else if (loaiKhachHang == null) cboLoaiKhachHang.requestFocus();
            else if (trangThai == null) cboTrangThai.requestFocus();
            return;
        }
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        // Kiểm tra định dạng CCCD/Hộ chiếu
        if (khachHang_DAO.kiemTraTrungCCCD(cccd_HoChieu)) {
            hienThiLoi("CCCD/Hộ chiếu đã tồn tại!", "Vui lòng nhập CCCD/Hộ chiếu khác.");
            txtCCCD_HoChieu.requestFocus();
            txtCCCD_HoChieu.selectAll();
            return;
        }
        KhachHang khachHang = new KhachHang();
        // Tự động tạo mã mới
        String maKhachHangMoi;
        try {
            maKhachHangMoi = khachHang_DAO.taoMaKhachHangMoi();
            khachHang.setMaKhachHang(maKhachHangMoi);
        } catch (Exception e) {
            hienThiLoi("Lỗi tạo mã khách hàng!", "Không thể tạo mã mới. Vui lòng thử lại.");
            txtTenKhachHang.requestFocus();
            return;
        }
        khachHang.setMaKhachHang(maKhachHangMoi);
        khachHang.setTenKhachHang(tenKhachHang);
        khachHang.setCccd_HoChieu(cccd_HoChieu);
        khachHang.setSoDienThoai(soDienThoai);
        khachHang.setLoaiKhachHang(loaiKhachHang);
        khachHang.setTrangThaiKhachHang(trangThai);
        khachHang.setEmail(email);

        boolean thanhCong = khachHang_DAO.themKhachHang(khachHang, true);

        if (thanhCong) {
            hienThiThongBao("Thành công", "Đã thêm khách hàng thành công!");
            tbDanhSachKhachHang.getItems().add(khachHang); // Thêm vào TableView
            btnLamRongKhachHang();
        } else {
            hienThiLoi("Thất bại", "Không thể thêm khách hàng. Vui lòng kiểm tra lại!");
            txtTenKhachHang.requestFocus();
        }
    }

    // Nút cập nhật khách hàng
    @FXML
    public void btnCapNhatKhachHangClicked() {
        KhachHang khachHang = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
        if (khachHang == null) {
            hienThiLoi("Chưa chọn khách hàng!", "Vui lòng chọn một khách hàng trong bảng để cập nhật.");
            return;
        }

        String tenKhachHang = txtTenKhachHang.getText().trim();
        String cccd_HoChieu = txtCCCD_HoChieu.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
        TrangThaiKhachHang trangThai = cboTrangThai.getValue();
        String email = txtEmail.getText().trim();

        // Kiểm tra các trường bắt buộc (không cần kiểm tra mã vì không thay đổi)
        if (tenKhachHang.equals("") || cccd_HoChieu.equals("") || 
            soDienThoai.equals("") || loaiKhachHang == null || trangThai == null) {
            hienThiLoi("Bạn chưa nhập đầy đủ thông tin!", "Vui lòng nhập tên, CCCD, số điện thoại, loại và trạng thái khách hàng.");
            if (tenKhachHang.equals("")) txtTenKhachHang.requestFocus();
            else if (cccd_HoChieu.equals("")) txtCCCD_HoChieu.requestFocus();
            else if (soDienThoai.equals("")) txtSoDienThoai.requestFocus();
            else if (loaiKhachHang == null) cboLoaiKhachHang.requestFocus();
            else if (trangThai == null) cboTrangThai.requestFocus();
            return;
        }
        
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
        khachHang.setTenKhachHang(tenKhachHang);
        khachHang.setCccd_HoChieu(cccd_HoChieu);
        khachHang.setSoDienThoai(soDienThoai);
        khachHang.setLoaiKhachHang(loaiKhachHang);
        khachHang.setTrangThaiKhachHang(trangThai);
        khachHang.setEmail(email);
        boolean thanhCong = khachHang_DAO.capNhatKhachHang(khachHang, true);

        if (thanhCong) {
            hienThiThongBao("Thành công", "Đã cập nhật thông tin khách hàng thành công!");
            tbDanhSachKhachHang.refresh(); // Làm mới TableView
            btnLamRongKhachHang(); // Xóa các trường nhập liệu
        } else {
            hienThiLoi("Thất bại", "Không thể cập nhật khách hàng. Vui lòng kiểm tra lại!");
            txtTenKhachHang.requestFocus();
        }
    }
	

    // Xóa nội dung các TextField và ComboBox
    public void btnLamRongKhachHang() {
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
        // Khởi tạo danh sách cho Combo
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
        lblMenuQuanLyNhanVien.setOnMouseClicked(event -> {
            try {
                chuyenSangQuanLyNhanVien();
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
        
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colLoaiKhachHang.setCellValueFactory(new PropertyValueFactory<>("loaiKhachHang"));
        colTrangThaiKhachHang.setCellValueFactory(new PropertyValueFactory<>("trangThaiKhachHang"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Khởi tạo ChoiceBox
        cboLoaiKhachHang.getItems().setAll(LoaiKhachHang.values());
        cboTrangThai.getItems().setAll(TrangThaiKhachHang.values());
        
        tbDanhSachKhachHang.setOnMouseClicked(event -> {
            KhachHang khachHang = tbDanhSachKhachHang.getSelectionModel().getSelectedItem();
            if (khachHang == null) return;

            txtMaKhachHang.setText(khachHang.getMaKhachHang() != null ? khachHang.getMaKhachHang() : "");
            txtTenKhachHang.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
            txtCCCD_HoChieu.setText(khachHang.getCccd_HoChieu() != null ? khachHang.getCccd_HoChieu() : "");
            txtSoDienThoai.setText(khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "");
            cboLoaiKhachHang.setValue(khachHang.getLoaiKhachHang() != null ? khachHang.getLoaiKhachHang() : null);
            cboTrangThai.setValue(khachHang.getTrangThaiKhachHang() != null ? khachHang.getTrangThaiKhachHang() : null);
            txtEmail.setText(khachHang.getEmail() != null ? khachHang.getEmail() : "");
        });
        
        
    }
}