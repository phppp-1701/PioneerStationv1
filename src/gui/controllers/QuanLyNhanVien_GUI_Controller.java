package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import gui.ThongKe_GUI;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class QuanLyNhanVien_GUI_Controller implements Initializable {
	private String maNhanVien;

	public QuanLyNhanVien_GUI_Controller(String maNhanVien) {
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
	private TextField txtTimTenNhanVien;
	@FXML
	private Button btnTim;
	@FXML
	private TextField txtTimSoDienThoai;

	@FXML
	private TableView<NhanVien> tbDanhSachNhanVien;

	// Phương thức đưa thông tin nhân viên lên theo mã nhân viên
	public void hienThiThongTinNhanVien() {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(maNhanVien);
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

	// Chuyển sang giao diện trang chủ
	public void chuyenSangGiaoDienHome() throws IOException {
		Stage stage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new Home_GUI(stage, maNhanVien);
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

	// sự kiện khi ấn nút tìm kiếm

	@FXML
	private TableColumn<NhanVien, String> colStt;

	@FXML
	private TableColumn<NhanVien, String> colMaNV;

	@FXML
	private TableColumn<NhanVien, String> colTenNV;

	@FXML
	private TableColumn<NhanVien, String> colSoDienThoai;

	@FXML
	private TableColumn<NhanVien, String> colEmail;

	@FXML
	private TableColumn<NhanVien, String> colGioiTinh;

	@FXML
	private TableColumn<NhanVien, String> colChucVu;

	@FXML
	private TableColumn<NhanVien, String> colTrangThai;

	private void thietLapGiaTriCot() {
		colStt.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
		colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
		colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
		colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		colGioiTinh.setCellValueFactory(cellData -> {
			GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
			return new SimpleStringProperty(gioiTinh == GioiTinh.nam ? "Nam" : "Nữ");
		});
		colChucVu.setCellValueFactory(cellData -> {
			ChucVu chucVu = cellData.getValue().getChucVu();
			return new SimpleStringProperty(chucVu == ChucVu.quanLy ? "Quản lý" : "Bán vé");
		});
		colTrangThai.setCellValueFactory(cellData -> {
			TrangThaiNhanVien trangThai = cellData.getValue().getTrangThaiNhanVien();
			return new SimpleStringProperty(trangThai == TrangThaiNhanVien.hoatDong ? "Hoạt động" : "Vô hiệu hóa");
		});
	}

	@FXML
	public void nhanTimKiem() {
		String ten = txtTimTenNhanVien.getText().toString();
		String sdt = txtTimSoDienThoai.getText().toString();
		if (ten.equals("") && sdt.equals("")) {
			// Nếu không nhập gì thì hiện tất cả nhân viên
			hienThiLoi("Thiếu thông tin tìm kiếm", "Vui lòng nhập tên hoặc số điện thoại nhân viên cần tìm kiếm");
			txtTimTenNhanVien.requestFocus();
			return;

		}
//		if (!ten.equals("") && sdt.equals("")) {
//			List<NhanVien> dsNhanVien = new NhanVien_DAO().timNhanVienTheoTen(ten);
//			if (dsNhanVien.isEmpty()) {
//				hienThiLoi("Không tìm thấy nhân viên", "Không tìm thấy nhân viên nào có tên là " + ten);
//				txtTimTenNhanVien.requestFocus();
//				return;
//			}
//		}
//		// xóa dữ liệu cũ trong tableview (nếu có)
//		tbDanhSachNhanVien.getItems().clear();
//		// thêm dữ liêu mới vào tableview
//		List<NhanVien> dsNhanVien = new NhanVien_DAO().timNhanVienTheoTen(ten);
//		tbDanhSachNhanVien.getItems().addAll(dsNhanVien);
//		// thiết lập lại các cột trong tableview
//		thietLapGiaTriCot();
		try {
			NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
			List<NhanVien> dsNhanVien;
			// kiểm tra các trường hợp nhập vào và thực hiện tìm kiếm theo yêu cầu
			if (!sdt.isEmpty() && ten.isEmpty()) {
				dsNhanVien = nhanVien_DAO.timNhanVienTheoSoDienThoai(sdt);
			} else if (sdt.isEmpty() && !ten.isEmpty()) {
				dsNhanVien = nhanVien_DAO.timNhanVienTheoTen(ten);
			} else {
				dsNhanVien = nhanVien_DAO.timNhanVienTheoTenVaSoDienThoai(ten, sdt);
			}
			// kiem tra ket qua
			if (dsNhanVien == null || dsNhanVien.isEmpty()) {
				hienThiLoi("Không tìm thấy nhân viên",
						"Không tìm thấy nhân viên nào có tên là " + ten + " và số điện thoại là " + sdt);
				txtTimTenNhanVien.requestFocus();
				tbDanhSachNhanVien.scrollTo(0);
				return;
			}
			// hien thi ket qua tren tableview
			tbDanhSachNhanVien.getItems().clear();
			tbDanhSachNhanVien.setItems(FXCollections.observableArrayList(dsNhanVien));
			thietLapGiaTriCot();
		} catch (Exception e) {
			e.printStackTrace();
			hienThiLoi("Lỗi tìm kiếm", "Có lỗi xảy ra trong quá trình tìm kiếm nhân viên");
		}

	}

	@FXML
	private Button btnThem;
	@FXML
	private Button btnXoa;
	@FXML
	private Button btnCapNhat;
	@FXML
	private TextField txtMaNV;
	@FXML
	private TextField txtTenNV;
	@FXML
	private DatePicker datePickerNgaySinh;
	@FXML
	private TextField txtSoDienThoai;
	@FXML
	private TextField txtEmail;
	@FXML
	private ComboBox<GioiTinh> cboGioiTinh; // ComboBox thay cho TextField
	@FXML
	private TextField txtCCCD;
	@FXML
	private ComboBox<ChucVu> cboChucVu; // ComboBox thay cho TextField
	@FXML
	private ComboBox<TrangThaiNhanVien> cboTrangThaiNhanVien; // ComboBox cho trạng thái nhân viên

	@FXML
	private ImageView imgNhanVien;

	@FXML
	private Label lblDangXuat;

	@FXML
	private AnchorPane pnHome;

	@FXML
	private Label lblQuanLyBanVe;

	@FXML
	private Label lblQuanLyVe;

	@FXML
	private Label lblQuanLyHoaDon;

	@FXML
	private Label lblQuanLyKhachHang;

	@FXML
	private Label lblQuanLyNhanVien;

	@FXML
	private Label lblThongKe;

	@FXML
	private Label lblTrangChu;

	@FXML
	private Label lblQuanLyChuyenTau;
	@FXML
	private String urlAnh = "";

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

	@FXML
	private Button btnThemNhanVien;

	@FXML
	public void nhanBtnThemNhanVien() throws SQLException {
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		if (!txtMaNV.getText().toString().isEmpty()) {
			hienThiLoi("Hãy nhấn làm rỗng ", "nếu muốn thêm nhân viên mới!");
			return;
		}
		if (nhanVien_DAO.kiemTraCCCD(txtCCCD.getText().toString())) {
			hienThiLoi("CCCD/Hộ chiếu đã tồn tại!", "Vui lòng nhập lại!");
			txtCCCD.requestFocus();
			txtCCCD.selectAll();
			List<NhanVien> danhSachNhanVien = nhanVien_DAO.timNhanVienTheoCCCD_HoChieu(txtCCCD.getText().toString());
			// Xóa dữ liệu cũ trong table (nếu có)
			tbDanhSachNhanVien.getItems().clear();

			// Thêm dữ liệu mới vào table
			tbDanhSachNhanVien.getItems().addAll(danhSachNhanVien);
			// Thiết lập giá trị cho các cột
			colStt.setCellValueFactory(cellData -> new SimpleStringProperty(
					String.valueOf(tbDanhSachNhanVien.getItems().indexOf(cellData.getValue()) + 1)));
			colMaNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNhanVien()));
			colTenNV.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenNhanVien()));
			colSoDienThoai
					.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
			colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
			colGioiTinh.setCellValueFactory(cellData -> {
				GioiTinh gioiTinh = cellData.getValue().getGioiTinh();
				String displayValue = "";
				if (gioiTinh == GioiTinh.nam) {
					displayValue = "Nam";
				} else if (gioiTinh == GioiTinh.nu) {
					displayValue = "Nữ";
				}
				return new SimpleStringProperty(displayValue);
			});
			colChucVu.setCellValueFactory(cellData -> {
				ChucVu chucVu = cellData.getValue().getChucVu();
				String displayValue = "";
				if (chucVu == ChucVu.banVe) {
					displayValue = "Bán vé";
				} else if (chucVu == ChucVu.quanLy) {
					displayValue = "Quản lý";
				}
				return new SimpleStringProperty(displayValue);
			});
			colTrangThai.setCellValueFactory(cellData -> {
				TrangThaiNhanVien trangThai = cellData.getValue().getTrangThaiNhanVien();
				String displayValue = "";
				if (trangThai == TrangThaiNhanVien.hoatDong) {
					displayValue = "Hoạt động";
				} else if (trangThai == TrangThaiNhanVien.voHieuHoa) {
					displayValue = "Vô hiệu hóa";
				}
				return new SimpleStringProperty(displayValue);
			});
			return;
		}
		String tenNhanVien = txtTenNV.getText();
		String CCCD_HoChieu = txtCCCD.getText();
		LocalDate ngaySinh = datePickerNgaySinh.getValue();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();
		ChucVu chucVu;
		Object chucVuValue = cboChucVu.getValue();
		TrangThaiNhanVien trangthainhanvien;
		Object trangThaiValue = cboTrangThaiNhanVien.getValue();
		GioiTinh gioiTinh;
		Object gioiTinhValue = cboGioiTinh.getValue();

		String decodedPath = URLDecoder.decode(urlAnh.replace("file:/", ""), StandardCharsets.UTF_8);
		if (kiemTraTxt()) {
//			String tenNhanVien = txtTenNV.getText();
//			String CCCD_HoChieu = txtCCCD.getText();
//			LocalDate ngaySinh = datePickerNgaySinh.getValue();
//			String soDienThoai = txtSoDienThoai.getText();
//			String email = txtEmail.getText();

			if (chucVuValue instanceof ChucVu) {
				// Nếu getValue() trả về enum
				chucVu = (ChucVu) chucVuValue;
			} else {
				// Nếu getValue() trả về String (toString() của enum)
				String chucVuStr = chucVuValue != null ? chucVuValue.toString().trim() : "Bán vé";
				if (chucVuStr.equals("Quản lý")) {
					chucVu = ChucVu.quanLy;
				} else {
					chucVu = ChucVu.banVe; // Mặc định là Bán vé
				}
			}

			if (trangThaiValue instanceof TrangThaiNhanVien) {
				// Nếu getValue() trả về enum
				trangthainhanvien = (TrangThaiNhanVien) trangThaiValue;
			} else {
				// Nếu getValue() trả về String (toString() của enum)
				String trangThaiStr = trangThaiValue != null ? trangThaiValue.toString().trim() : "Hoạt động";

				if (trangThaiStr.equalsIgnoreCase("Hoạt động") || trangThaiStr.equalsIgnoreCase("hoatDong")) {
					trangthainhanvien = TrangThaiNhanVien.hoatDong;
				} else if (trangThaiStr.equalsIgnoreCase("Vô hiệu hóa") || trangThaiStr.equalsIgnoreCase("voHieuHoa")) {
					trangthainhanvien = TrangThaiNhanVien.voHieuHoa;
				} else {
					trangthainhanvien = TrangThaiNhanVien.hoatDong; // Mặc định là Hoạt động

				}
			}

			if (gioiTinhValue instanceof GioiTinh) {
				// Nếu getValue() trả về enum
				gioiTinh = (GioiTinh) gioiTinhValue;
			} else {
				// Nếu getValue() trả về String (từ toString() của enum hoặc hiển thị trên GUI)
				String gioiTinhStr = gioiTinhValue != null ? gioiTinhValue.toString().trim() : "Nam"; // Mặc định là
																										// "Nam"

				if (gioiTinhStr.equalsIgnoreCase("Nam") || gioiTinhStr.equalsIgnoreCase("nam")) {
					gioiTinh = GioiTinh.nam;
				} else if (gioiTinhStr.equalsIgnoreCase("Nữ") || gioiTinhStr.equalsIgnoreCase("nu")) {
					gioiTinh = GioiTinh.nu;
				} else {
					gioiTinh = GioiTinh.nam; // Mặc định là "Nam" nếu không khớp
				}
			}

			File sourceFile = new File(decodedPath);
			try {
				String fileName = sourceFile.getName();
				File destFile = new File("image/" + fileName);
				Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

				// Cập nhật linkAnh với đường dẫn tương đối
				urlAnh = "image/" + fileName;
				System.out.println("Ảnh đã được sao chép đến: " + urlAnh);

			} catch (IOException e) {
				e.printStackTrace();
				hienThiLoi("Không thể sao chép ảnh", "Vui lòng thử lại");
				return;
			}

			NhanVien nv = new NhanVien(nhanVien_DAO.taoMaNhanVienMoi(), tenNhanVien, CCCD_HoChieu, soDienThoai,
					ngaySinh, chucVu, gioiTinh, urlAnh, trangthainhanvien, email);
			nhanVien_DAO.themNhanVien(nv);
		}
	}

	@FXML
	public void nhanBtnCapNhatNV() throws SQLException {
		// Kiểm tra đã chọn nhân viên từ bảng chưa
		if (txtMaNV.getText().isEmpty()) {
			hienThiLoi("Chưa chọn nhân viên cần cập nhật", "Vui lòng chọn nhân viên cần cập nhật từ bảng");
			return;

		}
		// Kiểm tra dữ liệu hợp lệ
		if (!kiemTraTxt()) {
			return;
		}
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		// Lấy thông tin từ form
		String maNhanVien = txtMaNV.getText();
		String tenNhanVien = txtTenNV.getText();
		LocalDate ngaySinh = datePickerNgaySinh.getValue();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();
		String CCCD_HoChieu = txtCCCD.getText();
		// Xử lý giới tính
		GioiTinh gioiTinh = cboGioiTinh.getValue();
		// Xử lý chức vụ
		ChucVu chucVu = cboChucVu.getValue();
		// Xử lý trạng thái
		TrangThaiNhanVien trangThai = cboTrangThaiNhanVien.getValue();
		// Xử lý ảnh
		if (!urlAnh.isEmpty()) {
			String decodedPath = URLDecoder.decode(urlAnh.replace("file:/", ""), StandardCharsets.UTF_8);
			File sourceFile = new File(decodedPath);
			try {

				String fileName = sourceFile.getName();
				File destFile = new File("image/" + fileName);
				Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				hienThiLoi("Không thể sao chép ảnh ", "Vui lòng thử lại");
			}
		}
		// Hiển thị hộp thoại xác nhận
		Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Xác nhận cập nhật");
		confirmationAlert.setHeaderText("Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?");
		confirmationAlert.setContentText("Mã NV: " + txtMaNV.getText() + "\nTên NV: " + txtTenNV.getText());
		// Tạo đối tượng nhân viên mới với thông tin cập nhật
		NhanVien nv = new NhanVien(maNhanVien, tenNhanVien, CCCD_HoChieu, soDienThoai, ngaySinh, chucVu, gioiTinh,
				urlAnh, trangThai, email);
		if (nhanVien_DAO.capNhatNhanVien(nv)) {
			hienThiThongBao("Bạn đã cập nhật thành công", "Cập nhật thành công");
			// Lấy vị trí của nhân viên đã cập nhật trong bảng
			int selectedIndex = tbDanhSachNhanVien.getSelectionModel().getSelectedIndex();
			// Cập nhật lại dòng đã sửa trong bảng
			if (selectedIndex >= 0) {
				tbDanhSachNhanVien.getItems().set(selectedIndex, nv);
			}
			// Làm rỗng form
			btnLamRongClicked();
		} else {
			hienThiLoi("Cập nhật nhân viên không thành công", "Vui lòng thử lại");
		}
	}

	@FXML
	private Button btnThemTaiKhoan;

	@FXML
	public void nhanBtnThemTaiKhoan() throws SQLException, IOException{
		if (txtMaNV.getText().toString().trim().equals("")) {
			hienThiLoi("Chọn 1 nhân viên trước khi thêm tài khoản!", "Nếu chưa thêm vui lòng thử lại");

		} else {
			String maNhanVien = txtMaNV.getText().toString().trim();
			TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_DAO();
			if (taiKhoan_DAO.kiemTraTaiKhoanTheoMaNhanVien(maNhanVien)) {
				hienThiThongBao("Nhân viên đã có tài khoản",
						"Vui lòng chọn nhân viên khác hoặc sang chức năng quản lý tài khoản");
				return;
			}
			// Tạo một Stage mới cho cửa sổ modal
			Stage stage = new Stage();
			new QuanLyTaiKhoan_GUI(maNhanVien, stage);
		}
	}

	@FXML
	private Button btnThemAnh;

	@FXML
	private void btnThemAnh() {
		// Tạo FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Chọn ảnh nhân viên");

		// Chỉ cho phép chọn file ảnh (JPG, PNG)
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

		// Mở hộp thoại chọn file
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			try {
				// Tải ảnh từ file và hiển thị lên ImageView
				urlAnh = selectedFile.toURI().toString();
				Image image = new Image(selectedFile.toURI().toString());
				imgNhanVien.setImage(image);
				imgNhanVien.setFitWidth(100);
				imgNhanVien.setFitHeight(100);
			} catch (Exception e) {
				hienThiLoi("Không hiển thị được ảnh", "Vui lòng thử lại");
				imgNhanVien.setImage(null);
			}
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
		lblMenuQuanLyHoaDon.setOnMouseClicked(event -> {
			try {
				chuyenSangQuanLyHoaDon();
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
		// Thiết lập ComboBox chức vụ và trạng thái nhân viên
		cboGioiTinh.getItems().setAll(GioiTinh.values());
		cboGioiTinh.setValue(GioiTinh.nam);

		cboChucVu.getItems().setAll(ChucVu.values());
		cboChucVu.setValue(ChucVu.banVe);

		txtMaNV.setDisable(true);
		txtMaNV.setEditable(false);

		Platform.runLater(() -> {
			txtTimTenNhanVien.requestFocus();
		});

		cboTrangThaiNhanVien.getItems().setAll(TrangThaiNhanVien.values());
		cboTrangThaiNhanVien.setValue(TrangThaiNhanVien.hoatDong);
		cboTrangThaiNhanVien.setEditable(false);
		cboTrangThaiNhanVien.setDisable(false);

		// thiết lập hành vi khi click vào bảng danh sách nhân viên
		tbDanhSachNhanVien.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				NhanVien nhanVien = tbDanhSachNhanVien.getSelectionModel().getSelectedItem();
				if (nhanVien != null) {
					txtMaNV.setText(nhanVien.getMaNhanVien());
					txtTenNV.setText(nhanVien.getTenNhanVien());
					txtSoDienThoai.setText(nhanVien.getSoDienThoai());
					txtEmail.setText(nhanVien.getEmail());
					txtCCCD.setText(nhanVien.getCccd_HoChieu());
					datePickerNgaySinh.setValue(nhanVien.getNgaySinh());
					cboGioiTinh.setValue(nhanVien.getGioiTinh());
					cboChucVu.setValue(nhanVien.getChucVu());
					cboTrangThaiNhanVien.setValue(nhanVien.getTrangThaiNhanVien());

					String imagePath = nhanVien.getUrlAnh();
					Image image = new Image("file:" + imagePath);
					// gán ảnh vào ImageView
					imgNhanVien.setImage(image);
					cboChucVu.setEditable(true);
					cboChucVu.setDisable(false);
					cboTrangThaiNhanVien.setEditable(true);
					cboTrangThaiNhanVien.setDisable(false);
					urlAnh = nhanVien.getUrlAnh();
				}
			}
		});

	}

	@FXML
	private boolean kiemTraTxt() {
		String tenNhanVien = txtTenNV.getText();
		String CCCD_HoChieu = txtCCCD.getText();
		LocalDate ngaySinh = datePickerNgaySinh.getValue();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();

		String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
		String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
		String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
		String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
		LocalDate ngayHomNay = LocalDate.now();

		if (tenNhanVien.trim().equals("")) {
			hienThiLoi("Tên nhân viên không được rỗng!", "Vui lòng nhập lại");
			txtTenNV.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexTen);
			Matcher mc = pt.matcher(tenNhanVien);
			if (!mc.matches()) {
				hienThiLoi("Lỗi cú pháp khi viết tên nhân viên",
						"Tên nhân viên phải có 2 từ trở lên, viết hoa chữ cái đầu!");
				txtTenNV.requestFocus();
				txtTenNV.selectAll();
				return false;
			}
		}
		if (CCCD_HoChieu.trim().equals("")) {
			hienThiLoi("CCCD/Hộ chiếu của nhân viên không được rỗng!", "Vui lòng nhập lại");
			txtCCCD.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexCCCD_HoChieu);
			Matcher mc = pt.matcher(CCCD_HoChieu);
			if (!mc.matches()) {

				hienThiLoi("Lỗi cú pháp khi nhập CCCD",
						"CCCD phải là dãy 12 chữ số trở lên. Hộ chiếu phải bắt đầu bằng 1 kí tự in hoa và dãy 7 chữ số!");
				txtCCCD.requestFocus();
				txtCCCD.selectAll();
				return false;
			}
		}
		if (ngaySinh == null) {

			hienThiLoi("Ngày sinh của nhân viên không được rỗng", "Vui lòng nhập lại");
			datePickerNgaySinh.requestFocus();
			return false;
		} else {
			ChucVu chucVu;
			Object chucVuValue = cboChucVu.getValue();
			if (chucVuValue instanceof ChucVu) {
				// Nếu getValue() trả về enum
				chucVu = (ChucVu) chucVuValue;
			} else {
				// Nếu getValue() trả về String (toString() của enum)
				String chucVuStr = chucVuValue != null ? chucVuValue.toString().trim() : "Bán vé";
				if (chucVuStr.equals("Quản lý")) {
					chucVu = ChucVu.quanLy;
				} else {
					chucVu = ChucVu.banVe; // Mặc định là Bán vé
				}
			}
			// Tính khoảng cách giữa ngayHienTai và ngaySinh
			Period period = Period.between(ngaySinh, ngayHomNay);

			// Kiểm tra tuổi cho chức vụ Bán vé (phải lớn hơn 16 tuổi)
			if (chucVu.equals(ChucVu.banVe)) {
				if (period.getYears() < 16
						|| (period.getYears() == 16 && period.getMonths() == 0 && period.getDays() == 0)) {
					hienThiLoi("Nhân viên bán vé phải lớn hơn 16 tuổi", "Vui lòng nhập lại");
					datePickerNgaySinh.requestFocus();
					datePickerNgaySinh.getEditor().selectAll();
					return false;
				}
			}

			// Kiểm tra tuổi cho chức vụ Quản lý (phải lớn hơn 20 tuổi)
			if (chucVu.equals(ChucVu.quanLy)) {
				if (period.getYears() < 20
						|| (period.getYears() == 20 && period.getMonths() == 0 && period.getDays() == 0)) {
					hienThiLoi("Nhân viên quản lý phải lướn hơn 20 tuổi", "Vui lòng nhập lại");
					datePickerNgaySinh.requestFocus();
					datePickerNgaySinh.getEditor().selectAll();
					return false;
				}
			}
		}

		if (soDienThoai.trim().equals(email)) {
			hienThiLoi("SDT không được rỗng", "Vui lòng nhập lại");
			txtSoDienThoai.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexSoDienThoai);
			Matcher mc = pt.matcher(soDienThoai);
			if (!mc.matches()) {
				hienThiLoi("Số điện thoại phải là dãy số (03|05|07|08|09) và 8 chữ số ngẫu nhiên!", "Không hợp lệ");
				txtSoDienThoai.requestFocus();
				txtSoDienThoai.selectAll();
				return false;
			}
		}
		if (email.trim().equals("")) {
			hienThiLoi("Email của nhân viên không được rỗng", "Vui lòng nhập lại");
			txtEmail.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexEmail);
			Matcher mc = pt.matcher(email);
			if (!mc.matches()) {
				hienThiLoi("Email sai định dạng", "Vui lòng kiểm tra lại");
				txtEmail.requestFocus();
				txtEmail.selectAll();
				return false;
			}
		}
		if (urlAnh.trim().equals("")) {
			hienThiLoi("Chưa hiển thị ảnh", "Kiểm tra lại");
			return false;
		}
		return true;
	}

	@FXML
	private Button btnLamRong;

	@FXML
	private void btnLamRongClicked() {
		txtMaNV.setText("");
		txtTenNV.setText("");
		txtCCCD.setText("");
		txtEmail.setText("");
		txtSoDienThoai.setText("");
		datePickerNgaySinh.setValue(null);
		cboTrangThaiNhanVien.setValue(TrangThaiNhanVien.hoatDong);
		cboGioiTinh.setValue(GioiTinh.nam);
		cboChucVu.setValue(ChucVu.banVe);
		imgNhanVien.setImage(null);
		cboTrangThaiNhanVien.setEditable(false);
		cboTrangThaiNhanVien.setDisable(true);
		txtTenNV.requestFocus();
		urlAnh = "";
	}
}
