package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import dao.NhanVien_DAO;

import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Ve;
import gui.HoaDon_GUI;
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
import gui.Ve_GUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyHoaDon_GUI_Controller implements Initializable {
	private String maNhanVien;

	public QuanLyHoaDon_GUI_Controller(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	// Khởi tạo các thành phần
	@FXML
	private ImageView imgAnhNhanVien;
	@FXML
	private Label lblMaNhanVien;
	@FXML
	private Label lblTenNhanVien;
	@FXML
	private Label lblChucVu;
	@FXML
	private Label lblMenuHome;
	@FXML
	private Label lblMenuQuanLyBanVe;
	@FXML
	private Label lblMenuQuanLyLichSu;
	@FXML
	private Label lblMenuQuanLyVe;
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
	// danh sach hoa don
	@FXML
	private TableView<HoaDon> tbDanhSachHoaDon;
	@FXML
	private TableColumn<HoaDon, String> colMaHoaDon;
	@FXML
	private TableColumn<HoaDon, String> colStt;
	@FXML
	private TableColumn<HoaDon, String> colTenKhachHang;
	@FXML
	private TableColumn<HoaDon, LocalDate> colNgayLapHoaDon;
	@FXML
	private TableColumn<HoaDon, String> colLoaiKhachHang;
	@FXML
	private TableColumn<HoaDon, String> colTenNhanVien;
	@FXML
	private TableColumn<HoaDon, String> colPTTT;
	@FXML
	private TableColumn<HoaDon, String> colThanhTien;
	@FXML
	private Button btnTimHoaDon;
	@FXML
	private TextField txtTimMaHoaDon;
	@FXML
	private TextField txtMaHoaDon;
	@FXML
	private TextField txtNgayLapHoaDon;
	@FXML
	private TextField txtTenKhachHang;
	@FXML
	private TextField txtLoaiKhachHang;
	@FXML
	private TextField txtTenNhanVien;
	@FXML
	private TextField txtKhuyenMai;
	@FXML
	private TextField txtPhanTramGiamGia;
	@FXML
	private TextField txtThanhTien;
	@FXML
	private TextField txtPTTT;
	@FXML
	private TextField txtTienKhachDua;
	@FXML
	private TextField txtTienTraLai;
	// khởi tạo bẳng và cột cho vé
	@FXML
	private TableView<Ve> tbDanhSachVe;
	@FXML
	private TableColumn<Ve, String> colMaVe;
	@FXML
	private TableColumn<Ve, String> colSttVe;
	@FXML
	private TableColumn<Ve, String> colTenKhachHangVe;
	@FXML
	private TableColumn<Ve, String> colLoaiVe;
	@FXML
	private TableColumn<Ve, Double> colGiaVe;
	@FXML
	private TableColumn<Ve, String> colTrangThaiVe;
	@FXML
	private TableColumn<Ve, String> colGaDi;
	@FXML
	private TableColumn<Ve, String> colGaDen;
	@FXML
	private TableColumn<Ve, String> colTenTau;
	@FXML
	private TableColumn<Ve, LocalDate> colNgayKhoiHanh;

	// Chuyển sang giao diện trang chủ
	public void chuyenSangGiaoDienHome() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new Home_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý bán vé
	public void chuyenSangQuanLyBanVe() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyBanVe_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý lịch sử
	public void chuyenSangQuanLyLichSu() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyLichSu_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý vé
	public void chuyenSangQuanLyVe() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyVe_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý hóa đơn
	public void chuyenSangQuanLyHoaDon() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyHoaDon_GUI(maNhanVien, primaryStage);
	}

	// Chuyển sang giao diện quản lý khách hàng
	public void chuyenSangQuanLyKhachHang() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyKhachHang_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý nhân viên
	public void chuyenSangQuanLyNhanVien() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyNhanVien_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý chuyến tàu
	public void chuyenSangQuanLyChuyenTau() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyChuyenTau_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện thống kê
	public void chuyenSangThongKe() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new ThongKe_GUI(primaryStage, maNhanVien);
	}

	// Chuyển sang giao diện quản lý tài khoản
	public void chuyenSangQuanLyTaiKhoan() throws IOException {
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyTaiKhoan_GUI(maNhanVien, primaryStage);
	}

	// hiển thị lỗi
	public void hienThiLoi(String tenLoi, String noiDungLoi) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Lỗi");
		alert.setHeaderText(tenLoi);
		alert.setContentText(noiDungLoi);
		alert.showAndWait();
	}

	public void hienThiThongBao(String tenLoi, String noiDungLoi) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(tenLoi);
		alert.setContentText(noiDungLoi);
		alert.showAndWait();
	}

	// Phương thức đưa thông tin nhân viên lên theo mã nhân viên
	public void hienThiThongTinNhanVien() {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien, true);
		if (nv != null) {
			lblMaNhanVien.setText(nv.getMaNhanVien());
			lblTenNhanVien.setText(nv.getTenNhanVien());
			// Đưa đường dẫn ảnh vào image
			String urlAnh = nv.getUrlAnh();
			try {
				File imgFile = new File(urlAnh);
				Image image = new Image(imgFile.toURI().toString());
				imgAnhNhanVien.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (nv.getChucVu().equals(ChucVu.banVe)) {
				lblChucVu.setText("Bán vé");
			} else {
				lblChucVu.setText("Quản lý");
			}
		}
	}
	@FXML
    private void btnInHoaDonClicked() {
        HoaDon hoaDonDuocChon = tbDanhSachHoaDon.getSelectionModel().getSelectedItem();
        if (hoaDonDuocChon == null) {
            hienThiLoi("Chưa chọn vé để thực hiện!", "Vui lòng chọn một hóa đơn trong bảng danh sách.");
            return;
        }
        Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
        new HoaDon_GUI(hoaDonDuocChon, primaryStage, maNhanVien);
    }
	
	@FXML
	public void btnTimHoaDonClicked() {
	    String maHoaDon = txtTimMaHoaDon.getText().trim();
	    // Kiểm tra xem có ít nhất một tiêu chí không rỗng
	    if (maHoaDon.isEmpty()) {
	        hienThiLoi("Bạn chưa nhập thông tin để tìm!", "Vui lòng nhập ít nhất một trong các trường: Mã hóa đơn.");
	        txtTimMaHoaDon.requestFocus();
	        return;
	    }

	    HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
	    List<HoaDon> danhSachHoaDon;
	    if (!maHoaDon.isEmpty()) {
	        danhSachHoaDon = hoaDonDAO.timHoaDonTheoMaHoaDon(maHoaDon, true);
	    } else {
	        danhSachHoaDon = new ArrayList<>();
	    }

	    if (danhSachHoaDon.isEmpty()) {
	        hienThiLoi("Không tìm thấy hóa đơn theo thông tin được nhập!", "");
	        tbDanhSachHoaDon.getItems().clear();
	        txtTimMaHoaDon.requestFocus();
	        txtTimMaHoaDon.selectAll();
	    } else {
	        tbDanhSachHoaDon.getItems().clear();
	        ObservableList<HoaDon> data = FXCollections.observableArrayList(danhSachHoaDon);

	        // Thiết lập giá trị cho các cột
	        colStt.setCellValueFactory(cellData -> new SimpleStringProperty(
	                String.valueOf(tbDanhSachHoaDon.getItems().indexOf(cellData.getValue()) + 1)));
	        colMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
	        colNgayLapHoaDon.setCellValueFactory(new PropertyValueFactory<>("ngayTaoHoaDon"));
	        colPTTT.setCellValueFactory(cellData -> {
	            HoaDon hoaDon = cellData.getValue();
	            return new SimpleStringProperty(hoaDon.getPhuongThucThanhToan() != null ? hoaDon.getPhuongThucThanhToan().toString() : "");
	        });
	        colThanhTien.setCellValueFactory(cellData -> {
	            HoaDon hoaDon = cellData.getValue();
	            return new SimpleStringProperty(hoaDon.getThanhTien() != 0 ? String.format("%,.0f VNĐ", hoaDon.getThanhTien()) : "0 VNĐ");
	        });
	        
	        colTenKhachHang.setCellValueFactory(cellData -> {
	            HoaDon hoaDon = cellData.getValue();
	            KhachHang khachHang = hoaDon.getKhachHang();
	            return new SimpleStringProperty(khachHang != null && khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
	        });
	        colLoaiKhachHang.setCellValueFactory(cellData -> {
	            HoaDon hoaDon = cellData.getValue();
	            KhachHang khachHang = hoaDon.getKhachHang();
	            return new SimpleStringProperty(khachHang != null && khachHang.getLoaiKhachHang() != null ? khachHang.getLoaiKhachHang().toString() : "");
	        });
	        
	        colTenNhanVien.setCellValueFactory(cellData -> {
	            HoaDon hoaDon = cellData.getValue();
	            NhanVien nhanVien = hoaDon.getNhanVien();
	            return new SimpleStringProperty(nhanVien != null && nhanVien.getTenNhanVien() != null ? nhanVien.getTenNhanVien() : "");
	        });

	        // Đặt dữ liệu vào table
	        tbDanhSachHoaDon.setItems(data);
	    }
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hienThiThongTinNhanVien();
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyKhachHang.setOnMouseClicked(event -> {
			try {
				chuyenSangQuanLyKhachHang();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyNhanVien.setOnMouseClicked(event -> {
			try {
				chuyenSangQuanLyNhanVien();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyChuyenTau.setOnMouseClicked(event -> {
			try {
				chuyenSangQuanLyChuyenTau();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuThongKe.setOnMouseClicked(event -> {
			try {
				chuyenSangThongKe();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyTaiKhoan.setOnMouseClicked(event -> {
			try {
				chuyenSangQuanLyTaiKhoan();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		tbDanhSachHoaDon.setOnMouseClicked(event -> {
		    HoaDon hoaDon = tbDanhSachHoaDon.getSelectionModel().getSelectedItem();
		    if (hoaDon == null) return;

		    txtMaHoaDon.setText(hoaDon.getMaHoaDon() != null ? hoaDon.getMaHoaDon() : "");
		    txtNgayLapHoaDon.setText(hoaDon.getNgayTaoHoaDon() != null ? hoaDon.getNgayTaoHoaDon().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		    txtPTTT.setText(hoaDon.getPhuongThucThanhToan() != null ? hoaDon.getPhuongThucThanhToan().toString() : "");
		    txtPhanTramGiamGia.setText(hoaDon.getPhanTramGiamGia() != 0 ? String.format("%.2f%%", hoaDon.getPhanTramGiamGia()*100) : "0%");
		    txtThanhTien.setText(hoaDon.getThanhTien() != 0 ? String.format("%,.0f VNĐ", hoaDon.getThanhTien()) : "0 VNĐ");
		    txtTienKhachDua.setText(hoaDon.getTienKhachDua() != 0 ? String.format("%,.0f VNĐ", hoaDon.getTienKhachDua()) : "0 VNĐ");
		    txtTienTraLai.setText(hoaDon.getTienTraLai() != 0 ? String.format("%,.0f VNĐ", hoaDon.getTienTraLai()) : "0 VNĐ");

		    KhachHang khachHang = hoaDon.getKhachHang();
		    if (khachHang != null) {
		        txtTenKhachHang.setText(khachHang.getTenKhachHang() != null ? khachHang.getTenKhachHang() : "");
		        txtLoaiKhachHang.setText(khachHang.getLoaiKhachHang() != null ? khachHang.getLoaiKhachHang().toString() : "");
		    } else {
		        txtTenKhachHang.setText("");
		        txtLoaiKhachHang.setText("");
		    }

		    KhuyenMai khuyenMai = hoaDon.getKhuyenMai();
		    if (khuyenMai != null) {
		        txtKhuyenMai.setText(khuyenMai.getTenKhuyenMai() != null ? khuyenMai.getTenKhuyenMai() : "");
		    } else {
		        txtKhuyenMai.setText("");
		    }

		    NhanVien nhanVien = hoaDon.getNhanVien();
		    if (nhanVien != null) {
		        txtTenNhanVien.setText(nhanVien.getTenNhanVien() != null ? nhanVien.getTenNhanVien() : "");
		    } else {
		        txtTenNhanVien.setText("");
		    }
		});
	}
}
