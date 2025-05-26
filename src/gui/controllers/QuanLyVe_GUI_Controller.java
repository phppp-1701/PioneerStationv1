package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.NhanVien_DAO;
import dao.ToaTau_DAO;
import dao.TuyenTau_DAO;
import dao.Ve_DAO;
import entity.Cho;
import entity.ChuyenTau;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.ToaTau;
import entity.TuyenTau;
import entity.Ve;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyNhanVien_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.ThongKe_GUI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyVe_GUI_Controller implements Initializable{
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
	//Khởi tạo các thành phần
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
    private TableView<Ve> tbDanhSachVe;
    @FXML
    private TableColumn<Ve, String> colSTT;
    @FXML
    private TableColumn<Ve, String> colMaVe;
    @FXML
    private TableColumn<Ve, LocalDate> colNgayKhoiHanh;
    @FXML
    private TableColumn<Ve, LocalTime> colGioKhoiHanh;
    @FXML
    private TableColumn<Ve, String> colTenDichVu;
    @FXML
    private TableColumn<Ve, String> colLoaiKhachHang;
    @FXML
    private TableColumn<Ve, String> colGiaVe;
    @FXML
    private TextField txtMaVe;
    @FXML
    private TextField txtThongTinMaHoaDon;
    @FXML
    private TextField txtThongTinNgayTaoVe;
    @FXML
    private TextField txtThongTinTrangThaiVe;
    @FXML
    private TextField txtThongTinTenKhachHang;
    @FXML
    private TextField txtThongTinLoaiKhachHang;
    @FXML
    private TextField txtThongTinCCCD_HoChieu;
    @FXML
    private TextField txtThongTinNgaySinh;
    @FXML
    private TextField txtThongTinChuyenTau;
    @FXML
    private TextField txtThongTinToa;
    @FXML
    private TextField txtThongTinLoaiToa;
    @FXML
    private TextField txtThongTinCho;
    @FXML
    private TextField txtThongTinGaDi;
    @FXML
    private TextField txtThongTinGaDen;
    @FXML
    private TextField txtThongTinNgayKhoiHanh;
    @FXML
    private TextField txtThongTinGioKhoiHanh;
    @FXML
    private TextField txtThongTinGiaVe;
    @FXML
    private TextField txtTimMaVe;
    @FXML
    private TextField txtTimTenKhachHang;
    @FXML
    private DatePicker dpTimNgayTaoVe;
    @FXML
    private Button btnCapNhatVe;
    @FXML
    private Button btnHoan_HuyVe;
    @FXML
    private Button btnDoiVe;
    @FXML
    private Button btnTimVe;
    
    @FXML
    private void btnTimVeClicked() {
    	String maVe = txtTimMaVe.getText().trim();
        // Kiểm tra xem có ít nhất một tiêu chí không rỗng
        if (maVe.isEmpty()) {
            hienThiLoi("Bạn chưa nhập thông tin để tìm!", "Vui lòng nhập ít nhất một trong các trường: Mã vé.");
            txtTimMaVe.requestFocus();
            return;
        }

        // Gọi DAO để tìm kiếm dựa trên tổ hợp các tiêu chí
        Ve_DAO veDAO = new Ve_DAO();
        List<Ve> danhSachVe;
        
        

        if (!maVe.isEmpty()) {
            danhSachVe = veDAO.timDanhSachVeTheoMa(maVe, true);
        }else {
            danhSachVe = new ArrayList<>();
        }

        // Xử lý kết quả
        if (danhSachVe.isEmpty()) {
            hienThiLoi("Không tìm thấy vé theo thông tin được nhập!", "");
            tbDanhSachVe.getItems().clear();
            txtTimMaVe.requestFocus();
            txtTimMaVe.selectAll();
        } else {
            tbDanhSachVe.getItems().clear();
            ObservableList<Ve> data = FXCollections.observableArrayList(danhSachVe);

            // Thiết lập cột cho TableView
            colSTT.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(tbDanhSachVe.getItems().indexOf(cellData.getValue()) + 1)));
            colMaVe.setCellValueFactory(new PropertyValueFactory<>("maVe"));
            colNgayKhoiHanh.setCellValueFactory(cellData -> {
                Ve ve = cellData.getValue();
                ChuyenTau chuyenTau = ve.getChuyenTau();
                return new SimpleObjectProperty<>(chuyenTau != null ? chuyenTau.getNgayKhoiHanh() : null);
            });
            colGioKhoiHanh.setCellValueFactory(cellData -> {
                Ve ve = cellData.getValue();
                ChuyenTau chuyenTau = ve.getChuyenTau();
                return new SimpleObjectProperty<>(chuyenTau != null ? chuyenTau.getGioKhoiHanh() : null);
            });

            colTenDichVu.setCellValueFactory(cellData -> {
                Ve ve = cellData.getValue();
                ChuyenTau chuyenTau = ve.getChuyenTau();
                if (chuyenTau != null) {
                    TuyenTau tuyenTau = chuyenTau.getTuyenTau();
                    if (tuyenTau != null) {
                        String gaDi = tuyenTau.getGaDi() != null ? tuyenTau.getGaDi().getTenGa() : "";
                        String gaDen = tuyenTau.getGaDen() != null ? tuyenTau.getGaDen().getTenGa() : "";
                        return new SimpleStringProperty(gaDi + " - " + gaDen);
                    }
                }
                return new SimpleStringProperty("");
            });
            colLoaiKhachHang.setCellValueFactory(cellData -> {
                Ve ve = cellData.getValue();
                return new SimpleStringProperty(ve.getLoaiVe() != null ? ve.getLoaiVe().toString() : "");
            });
            colGiaVe.setCellValueFactory(new PropertyValueFactory<>("giaVe"));

            // Đặt dữ liệu vào table
            tbDanhSachVe.setItems(data);
        }
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
	
	//Chuyển sang giao diện home
	public void chuyenSangGiaoDienHome() throws IOException{
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new Home_GUI(primaryStage, maNhanVien);
	}
	
	//Chuyển sang giao diện quản lý bán vé
	public void chuyenSangQuanLyBanVe() throws IOException {
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new QuanLyBanVe_GUI(primaryStage, maNhanVien);
	}
	
	//Chuyển sang giao diện quản lý lịch sử
	public void chuyenSangQuanLyLichSu() throws IOException {
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new QuanLyLichSu_GUI(primaryStage, maNhanVien);
	}
	
	//Chuyển sang giao diện quản lý hóa đơn
	public void chuyenSangQuanLyHoaDon() throws IOException {
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new QuanLyHoaDon_GUI(maNhanVien, primaryStage);
	}
	//Chuyển sang giao diện quản lý khách hàng
	public void chuyenSangQuanLyKhachHang() throws IOException{
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new QuanLyKhachHang_GUI(primaryStage, maNhanVien);
	}
	//Chuyển sang giao diện quản lý nhân viên
	public void chuyenSangQuanLyNhanVien() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyNhanVien_GUI(primaryStage, maNhanVien);
	}
	//Chuyển sang giao diện quản lý chuyến tàu
	public void chuyenSangQuanLyChuyenTau() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyChuyenTau_GUI(primaryStage, maNhanVien);
	}
	//Chuyển sang giao diện thống kê
	public void chuyenSangThongKe() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new ThongKe_GUI(primaryStage, maNhanVien);
	}
	//Chuyển sang giao diện quản lý tài khoản
	public void chuyenSangQuanLyTaiKhoan() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyTaiKhoan_GUI(maNhanVien, primaryStage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		hienThiThongTinNhanVien();
		lblMenuHome.setOnMouseClicked(event->{
			try {
				chuyenSangGiaoDienHome();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyBanVe.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyBanVe();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyLichSu.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyLichSu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyHoaDon.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyHoaDon();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyKhachHang.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyKhachHang();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyNhanVien.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyNhanVien();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyChuyenTau.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyChuyenTau();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuThongKe.setOnMouseClicked(event->{
			try {
				chuyenSangThongKe();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuQuanLyTaiKhoan.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyTaiKhoan();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		ToaTau_DAO toaTauDAO = new ToaTau_DAO();
		TuyenTau_DAO tuyenTauDAO = new TuyenTau_DAO();
        
		tbDanhSachVe.setOnMouseClicked(event -> {
		    // Bước 1: Lấy đối tượng Ve (vé) được chọn từ bảng
		    Ve ve = tbDanhSachVe.getSelectionModel().getSelectedItem();
		    if (ve == null) {
		        return; // Nếu không có vé nào được chọn, thoát khỏi sự kiện
		    }

		    // Bước 2: Gán thông tin cơ bản của vé vào các TextField
		    txtMaVe.setText(ve.getMaVe() != null ? ve.getMaVe() : ""); // Mã vé
		    txtThongTinMaHoaDon.setText(ve.getHoaDon() != null ? ve.getHoaDon().getMaHoaDon() : ""); // Mã hóa đơn
		    txtThongTinNgayTaoVe.setText(ve.getNgayTaoVe() != null ? ve.getNgayTaoVe().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		    txtThongTinTrangThaiVe.setText(ve.getTrangThaiVe() != null ? ve.getTrangThaiVe().toString() : "");
		    txtThongTinTenKhachHang.setText(ve.getTenKhachHang() != null ? ve.getTenKhachHang() : "");
		    txtThongTinLoaiKhachHang.setText(ve.getLoaiVe() != null ? ve.getLoaiVe().toString() : "");
		    txtThongTinCCCD_HoChieu.setText(ve.getCccd_HoChieu() != null ? ve.getCccd_HoChieu() : "");
		    txtThongTinNgaySinh.setText(ve.getNgaySinh() != null ? ve.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		    txtThongTinGiaVe.setText(ve.getGiaVe() != 0 ? String.format("%.2f", ve.getGiaVe()) : "");

		    // Bước 3: Lấy thông tin chuyến tàu (ChuyenTau) liên quan đến vé
		    ChuyenTau chuyenTau = ve.getChuyenTau();
		    if (chuyenTau != null) {
		        // Nếu có ChuyenTau, gán thông tin liên quan đến chuyến tàu
		        txtThongTinChuyenTau.setText(chuyenTau.getMaChuyenTau() != null ? chuyenTau.getMaChuyenTau() : ""); // Mã chuyến tàu
		        txtThongTinNgayKhoiHanh.setText(chuyenTau.getNgayKhoiHanh() != null ? chuyenTau.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""); // Ngày khởi hành
		        txtThongTinGioKhoiHanh.setText(chuyenTau.getGioKhoiHanh() != null ? chuyenTau.getGioKhoiHanh().format(DateTimeFormatter.ofPattern("HH:mm")) : ""); // Giờ khởi hành

		        // Bước 4: Lấy thông tin tuyến tàu (TuyenTau) để lấy ga đi và ga đến
		        TuyenTau tuyenTau = null;
		        if (chuyenTau.getTuyenTau() != null) {
		            tuyenTau = tuyenTauDAO.timTuyenTauTheoMaTuyenTau(chuyenTau.getTuyenTau().getMaTuyenTau(), false);
		        }
		        if (tuyenTau != null) {
		            txtThongTinGaDi.setText(tuyenTau.getGaDi() != null ? tuyenTau.getGaDi().getTenGa() : ""); // Tên ga đi
		            txtThongTinGaDen.setText(tuyenTau.getGaDen() != null ? tuyenTau.getGaDen().getTenGa() : ""); // Tên ga đến
		        } else {
		            txtThongTinGaDi.setText(""); // Nếu không có tuyến tàu, để trống
		            txtThongTinGaDen.setText("");
		        }
		    } else {
		        // Nếu không có ChuyenTau, để trống các trường liên quan
		        txtThongTinChuyenTau.setText("");
		        txtThongTinNgayKhoiHanh.setText("");
		        txtThongTinGioKhoiHanh.setText("");
		        txtThongTinGaDi.setText("");
		        txtThongTinGaDen.setText("");
		    }

		    // Bước 5: Lấy thông tin chỗ ngồi (Cho) và toa tàu (ToaTau)
		    Cho cho = ve.getCho();
		    if (cho != null) {
		        txtThongTinCho.setText(cho.getMaCho() != null ? cho.getMaCho() : ""); // Mã chỗ ngồi

		        // Lấy thông tin toa tàu từ mã toa
		        ToaTau toaTau = toaTauDAO.timToaTauTheoMaToa(cho.getToaTau().getMaToaTau(), false);
		        if (toaTau != null) {
		        	System.out.println(toaTau.getLoaiToa().toString());
		            txtThongTinToa.setText(toaTau.getMaToaTau() != null ? toaTau.getMaToaTau() : ""); // Mã toa tàu
		            txtThongTinLoaiToa.setText(toaTau.getLoaiToa() != null ? toaTau.getLoaiToa().toString() : ""); // Loại toa
		        } else {
		            txtThongTinToa.setText("");
		            txtThongTinLoaiToa.setText("");
		        }
		    } else {
		        // Nếu không có Cho, để trống các trường liên quan
		        txtThongTinCho.setText("");
		        txtThongTinToa.setText("");
		        txtThongTinLoaiToa.setText("");
		    }
		});
	}

	
}
