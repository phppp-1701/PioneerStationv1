package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.util.List;
import java.util.ResourceBundle;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Ve_DAO;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.NhanVien.ChucVu;

import entity.Ve;

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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.DatePicker;
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
	private TableColumn<HoaDon, Double> colThanhTien;
	@FXML
	private Button btnTimHoaDon;
	@FXML
	private TextField txtTimTenKhachHang;
	@FXML
	private TextField txtTimSoDienThoai;
	@FXML
	private DatePicker dpTimNgayLapHoaDon;
	// Chi tiết hóa đơn
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
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Thông báo");
		alert.setHeaderText(tenLoi);
		alert.setContentText(noiDungLoi);
		alert.showAndWait();
	}

	// Khởi tạo bảng hóa đơn
	private void khoiTaoBangHoaDon() {
		colMaHoaDon.setCellValueFactory(new PropertyValueFactory<>("maHoaDon"));
		colNgayLapHoaDon.setCellValueFactory(new PropertyValueFactory<>("ngayTaoHoaDon"));
		colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
		colPTTT.setCellValueFactory(new PropertyValueFactory<>("phuongThucThanhToan"));

		// Cột STT
		colStt.setCellValueFactory(cellData -> {
			int rowIndex = tbDanhSachHoaDon.getItems().indexOf(cellData.getValue()) + 1;
			return new SimpleStringProperty(String.valueOf(rowIndex));
		});

		// Cột tên khách hàng
		colTenKhachHang.setCellValueFactory(cellData -> {
			KhachHang khachHang = cellData.getValue().getKhachHang();
			return new SimpleStringProperty(khachHang != null ? khachHang.getTenKhachHang() : "");
		});

		// Cột loại khách hàng
		colLoaiKhachHang.setCellValueFactory(cellData -> {
			KhachHang khachHang = cellData.getValue().getKhachHang();
			return new SimpleStringProperty(khachHang != null ? khachHang.getLoaiKhachHang().toString() : "");
		});

		// Cột tên nhân viên
		colTenNhanVien.setCellValueFactory(cellData -> {
			NhanVien nhanVien = cellData.getValue().getNhanVien();
			return new SimpleStringProperty(nhanVien != null ? nhanVien.getTenNhanVien() : "");
		});

		// Hiển thị chi tiết hóa đơn khi chọn một dòng trong bảng
		tbDanhSachHoaDon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				hienThiChiTietHoaDon(newSelection);
			} else {
				xoaChiTietHoaDon();
			}
		});
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

	// Xử lý sự kiện nhấn nút tìm kiếm
	@FXML
	public void nhanNutTimKiem() {
		String tenKhachHang = txtTimTenKhachHang.getText().trim();
		String soDienThoai = txtTimSoDienThoai.getText().trim();

		// Kiểm tra dữ liệu đầu vào
		if (tenKhachHang.isEmpty() && soDienThoai.isEmpty()) {
			hienThiLoi("Lỗi tìm kiếm",
					"Vui lòng nhập ít nhất một tiêu chí tìm kiếm (tên khách hàng hoặc số điện thoại).");
			return;
		}

		// Nếu không nhập tên hoặc số điện thoại, gán null để tìm kiếm linh hoạt
		tenKhachHang = tenKhachHang.isEmpty() ? null : tenKhachHang;
		soDienThoai = soDienThoai.isEmpty() ? null : soDienThoai;

		HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
		List<HoaDon> danhSachHoaDon = hoaDonDAO.traCuuHoaDonTheoSDT_Ten(soDienThoai, tenKhachHang, true);

		// Xóa dữ liệu cũ trong bảng
		tbDanhSachHoaDon.getItems().clear();

		// Thêm dữ liệu mới vào bảng
		if (danhSachHoaDon.isEmpty()) {
			hienThiThongBao("Kết quả tìm kiếm", "Không tìm thấy hóa đơn phù hợp với tiêu chí.");
		} else {
			tbDanhSachHoaDon.getItems().addAll(danhSachHoaDon);
			hienThiThongBao("Kết quả tìm kiếm", "Đã tìm thấy " + danhSachHoaDon.size() + " hóa đơn.");
		}
	}

	// Hiển thị chi tiết hóa đơn
	private void hienThiChiTietHoaDon(HoaDon hoaDon) {
		txtMaHoaDon.setText(hoaDon.getMaHoaDon());
		txtNgayLapHoaDon.setText(hoaDon.getNgayTaoHoaDon().toString());
		txtThanhTien.setText(String.valueOf(hoaDon.getThanhTien()));
		txtPTTT.setText(hoaDon.getPhuongThucThanhToan().toString());
		txtPhanTramGiamGia.setText(String.valueOf(hoaDon.getPhanTramGiamGia()));
		txtTienKhachDua.setText(String.valueOf(hoaDon.getTienKhachDua()));
		txtTienTraLai.setText(String.valueOf(hoaDon.getTienTraLai()));
		txtKhuyenMai.setText(hoaDon.getKhuyenMai().getMaKhuyenMai() != null ? hoaDon.getKhuyenMai().getMaKhuyenMai() : "");

		KhachHang_DAO khachHangDAO = new KhachHang_DAO();
		KhachHang khachHang = khachHangDAO.timKhachHangTheoMa(hoaDon.getKhachHang().getMaKhachHang(), true);
		txtTenKhachHang.setText(khachHang != null ? khachHang.getTenKhachHang() : "");
		txtLoaiKhachHang.setText(khachHang != null ? khachHang.getLoaiKhachHang().toString() : "");

		NhanVien_DAO nhanVienDAO = new NhanVien_DAO();
		NhanVien nhanVien = nhanVienDAO.timNhanVienTheoMa(hoaDon.getNhanVien().getMaNhanVien(), true);
		txtTenNhanVien.setText(nhanVien != null ? nhanVien.getTenNhanVien() : "");
		hienThiDanhSachVe(hoaDon);
	}

	// Xóa chi tiết hóa đơn
	private void xoaChiTietHoaDon() {
		txtMaHoaDon.setText("");
		txtNgayLapHoaDon.setText("");
		txtTenKhachHang.setText("");
		txtLoaiKhachHang.setText("");
		txtTenNhanVien.setText("");
		txtKhuyenMai.setText("");
		txtPhanTramGiamGia.setText("");
		txtThanhTien.setText("");
		txtPTTT.setText("");
		txtTienKhachDua.setText("");
		txtTienTraLai.setText("");
	}

	private void loadDanhSachHoaDon() {
		HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
		try {
			List<HoaDon> danhSachHoaDon = hoaDonDAO.layDanhSachHoaDon(true);
			tbDanhSachHoaDon.getItems().clear();
			if (danhSachHoaDon != null && !danhSachHoaDon.isEmpty()) {
				tbDanhSachHoaDon.getItems().addAll(danhSachHoaDon);
			} else {
				hienThiThongBao("Danh sách hóa đơn", "Không có hóa đơn nào trong hệ thống.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			hienThiLoi("Lỗi tải dữ liệu", "Không thể tải danh sách hóa đơn: " + e.getMessage());
		}
	}

	// hiển thị danh sach vé theo hóa đơn
	private void hienThiDanhSachVe(HoaDon hoaDon) {
		Ve_DAO veDAO = new Ve_DAO();
		List<Ve> danhSachVe = veDAO.timDanhSachVeTheoHoaDon(hoaDon, false);
		tbDanhSachVe.getItems().clear();
		if (danhSachVe != null && !danhSachVe.isEmpty()) {
			tbDanhSachVe.setItems(FXCollections.observableArrayList(danhSachVe));
		} else {
			hienThiThongBao("Danh sách vé", "Không có vé nào thuộc hóa đơn này.");
		}
	}

//	private void khoiTaoBangVe() {
//		colMaVe.setCellValueFactory(new PropertyValueFactory<>("maVe"));
////		colLoaiVe.setCellValueFactory(cellData -> {
////			LoaiVe loaiVe = cellData.getValue().getLoaiVe();
////			return new SimpleStringProperty(loaiVe != null ? loaiVe.toString() : "");
////		});
//		colGiaVe.setCellValueFactory(new PropertyValueFactory<>("giaVe"));
//		colTrangThaiVe.setCellValueFactory(cellData -> {
//			TrangThaiVe trangThai = cellData.getValue().getTrangThaiVe();
//			return new SimpleStringProperty(trangThai != null ? trangThai.toString() : "");
//		});
//
//		// Cột STT
//		colSttVe.setCellValueFactory(cellData -> {
//			int rowIndex = tbDanhSachVe.getItems().indexOf(cellData.getValue()) + 1;
//			return new SimpleStringProperty(String.valueOf(rowIndex));
//		});
//
//		// Các cột thông tin khác
//		colTenKhachHangVe.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
//		colGaDi.setCellValueFactory(cellData -> {
//			// Lấy thông tin ga đi từ chuyến tàu (cần implement thêm)
//			return new SimpleStringProperty("Ga Hà Nội"); // Ví dụ
//		});
//		colGaDen.setCellValueFactory(cellData -> {
//			// Lấy thông tin ga đến từ chuyến tàu (cần implement thêm)
//			return new SimpleStringProperty("Ga Sài Gòn"); // Ví dụ
//		});
//		colNgayKhoiHanh.setCellValueFactory(cellData -> {
//			// Lấy ngày khởi hành từ chuyến tàu (cần implement thêm)
//			return new SimpleObjectProperty<>(LocalDate.now()); // Ví dụ
//		});
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		khoiTaoBangHoaDon();
		hienThiThongTinNhanVien();
//		khoiTaoBangVe();
//		loadDanhSachHoaDon();
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
		// Gán sự kiện cho nút tìm kiếm
		btnTimHoaDon.setOnAction(event -> nhanNutTimKiem());
	}
}
