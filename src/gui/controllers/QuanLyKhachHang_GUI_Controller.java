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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
    //THêm khách hàng mới
    private void themKhachHangMoi() throws SQLException {
        KhachHang_DAO khachHang_DAO = new KhachHang_DAO();

		if (!txtMaKhachHang.getText().isEmpty()) {
		    hienThiLoi("Hãy nhấn làm rỗng", "nếu muốn thêm khách hàng mới!");
		    return;
		}

		String cccdHoChieu = txtCCCD_HoChieu.getText().trim();
		if (khachHang_DAO.kiemTraCCCD(cccdHoChieu)) {
		    hienThiLoi("CCCD/Hộ chiếu đã tồn tại!", "Vui lòng nhập lại!");
		    txtCCCD_HoChieu.requestFocus();
		    txtCCCD_HoChieu.selectAll();

		    List<KhachHang> danhSachKhachHang = khachHang_DAO.timKhachHangTheoCCCD_HoChieu(cccdHoChieu);
		    tbDanhSachKhachHang.getItems().clear();
		    tbDanhSachKhachHang.getItems().addAll(danhSachKhachHang);

		    colStt.setCellValueFactory(cellData ->new ReadOnlyObjectWrapper<>(tbDanhSachKhachHang.getItems().indexOf(cellData.getValue()) + 1));
		    colMaKhachHang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaKhachHang()));
		    colTen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenKhachHang()));
		    colSoDienThoai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSoDienThoai()));
		    colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		    colLoaiKhachHang.setCellValueFactory(cellData ->new ReadOnlyObjectWrapper<>(cellData.getValue().getLoaiKhachHang()));
		    colTrangThaiKhachHang.setCellValueFactory(cellData ->new ReadOnlyObjectWrapper<>(cellData.getValue().getTrangThaiKhachHang()));
		    return;
		}

		if (!kiemTraTxtKhachHang())
		    return;

		String tenKhachHang = txtTenKhachHang.getText().trim();
		String soDienThoai = txtSoDienThoai.getText().trim();
		String email = txtEmail.getText().trim();

		LoaiKhachHang loaiKH = cboLoaiKhachHang.getValue();
		LoaiKhachHang loaiKhachHang;
		if (loaiKH instanceof LoaiKhachHang) {
		    loaiKH = (LoaiKhachHang) loaiKH;
		} else {
		    String loaiKHStr = loaiKH != null ? loaiKH.toString() : "Vãng lai";
		    loaiKH = switch (loaiKHStr.trim()) {
		        case "Vip" -> LoaiKhachHang.vip;
		        case "Thân thiết" -> LoaiKhachHang.thanThiet;
		        default -> LoaiKhachHang.vangLai;
		    };
		}

		TrangThaiKhachHang trangThaiKH = cboTrangThai.getValue();
		TrangThaiKhachHang trangThaiKhachHang;
		if (trangThaiKH instanceof TrangThaiKhachHang) {
		    trangThaiKH = (TrangThaiKhachHang) trangThaiKH;
		} else {
		    String trangThaiStr = trangThaiKH != null ? trangThaiKH.toString() : "Hoạt động";
		    trangThaiKH = trangThaiStr.equalsIgnoreCase("Vô hiệu hóa") ? TrangThaiKhachHang.voHieuHoa : TrangThaiKhachHang.hoatDong;
		}

		String maKH = khachHang_DAO.taoMaKhachHangMoi();
		KhachHang kh = new KhachHang(maKH, tenKhachHang, cccdHoChieu, soDienThoai, loaiKH, trangThaiKH, email);
		boolean themThanhCong = khachHang_DAO.themKhachHang(kh);

		if (themThanhCong) {
		    tbDanhSachKhachHang.getItems().add(kh);
		    hienThiThongBao("Thêm khách hàng thành công!", "");
		} else {
		    hienThiLoi("Thêm khách hàng thất bại!", "Vui lòng kiểm tra lại dữ liệu.");
		}
    }
    
    //Cập nhật thông tin khách hàng
    @FXML
    public void capNhatThongTinKhachHang() throws SQLException {
        // Kiểm tra đã chọn khách hàng từ bảng chưa
        if (txtMaKhachHang.getText().isEmpty()) {
            hienThiLoi("Chưa chọn khách hàng cần cập nhật", "Vui lòng chọn khách hàng cần cập nhật từ bảng");
            return;
        }
        // Kiểm tra dữ liệu hợp lệ
        if (!kiemTraTxtKhachHang()) {
            return;
        }

        // Hiển thị hộp thoại xác nhận
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận cập nhật");
        confirmationAlert.setHeaderText("Bạn có chắc chắn muốn cập nhật thông tin khách hàng này?");
//        confirmationAlert.setContentText("Mã Khách hàng: " + txtMaKhachHang.getText() + "\nTên NV: " + txtTenKhachHang.getText());

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Người dùng chọn OK, tiếp tục cập nhật
            KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
            String maKhachHang = txtMaKhachHang.getText();
            String tenKhachHang = txtTenKhachHang.getText();
            String CCCD_HoChieu = txtCCCD_HoChieu.getText();
            String soDienThoai = txtSoDienThoai.getText();
            LoaiKhachHang loaiKhachHang = cboLoaiKhachHang.getValue();
            TrangThaiKhachHang trangThaiKH = cboTrangThai.getValue();
            String email = txtEmail.getText();

            KhachHang kh = new KhachHang(maKhachHang, tenKhachHang, CCCD_HoChieu, soDienThoai, loaiKhachHang, trangThaiKH, email);

            if (khachHang_DAO.capNhatThongTinKhachHang(kh)) {
                int selectedIndex = tbDanhSachKhachHang.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    tbDanhSachKhachHang.getItems().set(selectedIndex, kh);
                }
                hienThiThongBao("Bạn đã cập nhật thành công", "Cập nhật thành công");
            } else {
                hienThiLoi("Cập nhật khách hàng không thành công", "Vui lòng thử lại");
            }
        } else {
            // Người dùng chọn Cancel hoặc đóng hộp thoại, không làm gì
            return;
        }
    }


    
    @FXML
	private boolean kiemTraTxtKhachHang() {
		String tenKhachHang = txtTenKhachHang.getText();
		String CCCD_HoChieu = txtCCCD_HoChieu.getText();
		String soDienThoai = txtSoDienThoai.getText();
		String email = txtEmail.getText();

		String regexTen = "^[A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*(?: [A-ZÀÁẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬĐÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ][a-zàáảãạăằắẳẵặâầấẩẫậđèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ]*)+$";
		String regexCCCD_HoChieu = "^(\\d{12}|[A-Z]\\d{7})$";
		String regexSoDienThoai = "^(0|\\+84)(3|5|7|8|9)\\d{8}$";
		String regexEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
		LocalDate ngayHomNay = LocalDate.now();

		if (tenKhachHang.trim().equals("")) {
			hienThiLoi("Tên khách hàng không được rỗng!", "Vui lòng nhập lại");
			txtTenKhachHang.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexTen);
			Matcher mc = pt.matcher(tenKhachHang);
			if (!mc.matches()) {
				hienThiLoi("Lỗi cú pháp khi viết tên nhân viên",
						"Tên khách hàng phải có 2 từ trở lên, viết hoa chữ cái đầu!");
				txtTenKhachHang.requestFocus();
				txtTenKhachHang.selectAll();
				return false;
			}
		}
		if (CCCD_HoChieu.trim().equals("")) {
			hienThiLoi("CCCD/Hộ chiếu của nhân viên không được rỗng!", "Vui lòng nhập lại");
			txtCCCD_HoChieu.requestFocus();
			return false;
		} else {
			Pattern pt = Pattern.compile(regexCCCD_HoChieu);
			Matcher mc = pt.matcher(CCCD_HoChieu);
			if (!mc.matches()) {

				hienThiLoi("Lỗi cú pháp khi nhập CCCD",
						"CCCD phải là dãy 12 chữ số trở lên. Hộ chiếu phải bắt đầu bằng 1 kí tự in hoa và dãy 7 chữ số!");
				txtCCCD_HoChieu.requestFocus();
				txtCCCD_HoChieu.selectAll();
				return false;
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
		return true;
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
        new QuanLyHoaDon_GUI( maNhanVien, primaryStage);
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
        new QuanLyTaiKhoan_GUI( maNhanVien, primaryStage);
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
        btnThemKhachHang.setOnAction(event -> {
			try {
				themKhachHangMoi();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
        btnCapNhat.setOnAction(event -> {
			try {
				capNhatThongTinKhachHang();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

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