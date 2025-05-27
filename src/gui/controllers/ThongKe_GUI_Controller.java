package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import dao.Ve_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import gui.DangNhap_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyNhanVien_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ThongKe_GUI_Controller implements Initializable{
	private String maNhanVien;
	public ThongKe_GUI_Controller(String maNhanVien) {
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
	private DatePicker dpNgay;
	@FXML
	private ComboBox<String> cboLuaChon;
	@FXML
	private Button btnThongKe;
	@FXML
	private Label lblTongDoanhThu;
	@FXML
	private Label lblTongSoVe;
	@FXML
	private Label lblSoLuongHoaDon;
	@FXML
	private Label lblSoLuongVeHuy;
	
	//thông báo
	private void hienThiLoi(String tenLoi, String noiDungLoi) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(tenLoi);
        alert.setContentText(noiDungLoi);
        alert.showAndWait();
    }
	
    //Phương thức tính tổng doanh thu
    public void hienThiThongTin(LocalDate ngay) {
    	HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
    	Ve_DAO veDao = new Ve_DAO();
    	DecimalFormat decimalFormat = new DecimalFormat("#,000 VNĐ");
    	if(cboLuaChon.getValue().equals("Ngày")) {
    		lblTongDoanhThu.setText("Tổng doanh thu: "+decimalFormat.format(hoaDon_DAO.tinhTongDoanhThuTheoNgay(ngay, true)));
    		lblTongSoVe.setText("Số lượng vé bán: "+veDao.tinhSoLuongVeBanTheoNgay(ngay, true));
    		lblSoLuongVeHuy.setText("Số lượng vé hủy: "+veDao.tinhSoLuongVeHuyTheoNgay(ngay, true));
    		lblSoLuongHoaDon.setText("Số lượng hóa đơn: "+hoaDon_DAO.demHoaDonTheoNgay(ngay, true));
    	}else if(cboLuaChon.getValue().equals("Tháng")) {
    		lblTongDoanhThu.setText("Tổng doanh thu: "+decimalFormat.format(hoaDon_DAO.tinhTongDoanhThuTheoThang(ngay.getMonthValue(), ngay.getYear(), true)));
    		lblTongSoVe.setText("Số lượng vé bán: "+veDao.tinhSoLuongVeBanTheoThang(ngay.getMonthValue(), ngay.getYear(), true));
    		lblSoLuongVeHuy.setText("Số lượng vé hủy: "+veDao.tinhSoLuongVeHuyTheoThang(ngay.getMonthValue(), ngay.getYear(), true));
    		lblSoLuongHoaDon.setText("Số lượng hóa đơn: "+hoaDon_DAO.demHoaDonTheoThang(ngay.getMonthValue(), ngay.getYear(), true));
    	}else {
    		lblTongDoanhThu.setText("Tổng doanh thu: "+decimalFormat.format(hoaDon_DAO.tinhTongDoanhThuTheoNam(ngay.getYear(), true)));
    		lblTongSoVe.setText("Số lượng vé bán: "+veDao.tinhSoLuongVeBanTheoNam(ngay.getYear(), true));
    		lblSoLuongVeHuy.setText("Số lượng vé hủy: "+veDao.tinhSoLuongVeHuyTheoNam(ngay.getYear(), true));
    		lblSoLuongHoaDon.setText("Số lượng hóa đơn: "+hoaDon_DAO.demHoaDonTheoNam(ngay.getYear(), true));
    	}
    }
    
	//Phương thức cho button thống kê
	@FXML
	public void nhanBtnThongKe() {
		if(dpNgay.getValue() == null) {
			hienThiLoi("Lỗi thiếu thông tin!", "Hãy chọn ngày trước khi nhấn thống kê!");
			return;
		}
		LocalDate ngayDuocChon = dpNgay.getValue();
		hienThiThongTin(ngayDuocChon);
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
	
	//Chuyển sang giao diện quản lý trang chủ
	public void chuyenSangGiaoDienHome() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyBanVe_GUI(primaryStage, maNhanVien);
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
	
	//Chuyển sang giao diện quản lý vé
	public void chuyenSangQuanLyVe() throws IOException {
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new QuanLyVe_GUI(primaryStage, maNhanVien);
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
	
	//Chuyển sang giao diện quản lý tài khoản
	public void chuyenSangQuanLyTaiKhoan() throws IOException{
		Stage primaryStage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new QuanLyTaiKhoan_GUI(maNhanVien, primaryStage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		hienThiThongTinNhanVien();
        if (cboLuaChon != null) {
        	cboLuaChon.setItems(FXCollections.observableArrayList("Ngày", "Tháng", "Năm"));
        	cboLuaChon.getSelectionModel().selectFirst(); // Optional: Select "Ngày" by default
        }
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
		lblMenuQuanLyVe.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyVe();
			}catch (Exception e) {
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
		lblMenuQuanLyTaiKhoan.setOnMouseClicked(event->{
			try {
				chuyenSangQuanLyTaiKhoan();
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		lblMenuDangXuat.setOnMouseClicked(event -> {
            // Tạo hộp thoại xác nhận
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận đăng xuất");
            alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
            alert.setContentText("Chọn OK để đăng xuất và quay lại màn hình đăng nhập.");
         // Thêm icon cho Alert
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            File iconFile = new File("image/hoi.png");
            if (iconFile.exists()) {
                Image icon = new Image(iconFile.toURI().toString());
                alertStage.getIcons().add(icon);
            } else {
                System.err.println("Không tìm thấy file icon: " + iconFile.getAbsolutePath());
            }
            // Hiển thị hộp thoại và chờ phản hồi
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Người dùng xác nhận đăng xuất");
                    try {
                        // Tạo Stage mới cho DangNhap_GUI
                        Stage loginStage = new Stage();
                        new DangNhap_GUI(loginStage);

                        // Đóng cửa sổ hiện tại
                        Stage currentStage = (Stage) lblMenuHome.getScene().getWindow();
                        currentStage.close();
                    } catch (Exception e) {
                        System.err.println("Lỗi khi mở DangNhap_GUI: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Người dùng hủy đăng xuất");
                }
            });
        });
	}
}
