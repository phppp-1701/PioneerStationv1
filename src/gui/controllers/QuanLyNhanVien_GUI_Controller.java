package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyChuyenTau_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import gui.ThongKe_GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyNhanVien_GUI_Controller implements Initializable{
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
	
	//Chuyển sang giao diện trang chủ
	public void chuyenSangGiaoDienHome() throws IOException {
		Stage stage = (Stage) imgAnhNhanVien.getScene().getWindow();
		new Home_GUI(stage, maNhanVien);
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
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	}

}
