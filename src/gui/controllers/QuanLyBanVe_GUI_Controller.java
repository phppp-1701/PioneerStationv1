package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.TuyenTau_DAO;
import entity.ChuyenTau;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.TuyenTau;
import gui.Home_GUI;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class QuanLyBanVe_GUI_Controller implements Initializable{
	private String maNhanVien;
	public QuanLyBanVe_GUI_Controller(String maNhanVien) {
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
	
	//Thành phần chức năng chính
	@FXML
	private ComboBox<Ga> cboGaDi; 
	@FXML
	private ComboBox<Ga> cboGaDen;
	@FXML
	private DatePicker dpNgayKhoiHanh;
	@FXML
	private DatePicker dpNgayVe;
	@FXML
	private Tab tabNgayKhoiHanh;
	@FXML
	private Tab tabNgayTroVe;
	@FXML
	private TabPane pnTabDanhSachChuyenTau;
	@FXML
	private Button btnTimChuyenTau;
	@FXML
	private AnchorPane pnChuyenTauKhoiHanh;
	
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
	
	//Chuyển sang giao diện home
	public void chuyenSangGiaoDienHome() throws IOException {
		Stage primaryStage = (Stage)imgAnhNhanVien.getScene().getWindow();
		new Home_GUI(primaryStage, maNhanVien);
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
	//Đẩy danh sách ga đi combobox
	public void dayDanhSachGaDiLenCbo() {
		Ga_DAO ga_DAO = new Ga_DAO();
		List<Ga> dsga = ga_DAO.layToanBoGa();
		ObservableList<Ga> dsgaChuyenDoi = FXCollections.observableArrayList(dsga);
		cboGaDi.setItems(dsgaChuyenDoi);
	}
	//Đẩy danh sách ga đến combobox
	public void dayDanhSachGaDenLenCbo() {
		Ga_DAO ga_DAO = new Ga_DAO();
		List<Ga> dsga = ga_DAO.layToanBoGa();
		ObservableList<Ga> dsgaChuyenDoi = FXCollections.observableArrayList(dsga);
		cboGaDen.setItems(dsgaChuyenDoi);
	}
	
	//Sự kiện khi nhấn tìm chuyến tàu
	@FXML
	public void nhanTimChuyenTau() {
		if(cboGaDi.getValue() == null) {
			hienThiLoi("Chưa đủ thông tin", "Vui lòng chọn ga đi!");
			return;
		}
		if(cboGaDen.getValue() == null) {
			hienThiLoi("Chưa đủ thông tin", "Vui lòng chọn ga đến!");
			return;
		}
		if(dpNgayKhoiHanh.getValue() == null) {
			hienThiLoi("Chưa đủ thông tin", "Vui lòng chọn ngày khởi hành!");
			return;
		}
		//Tìm tuyến tàu
		TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
		TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheo(cboGaDi.getValue().getMaGa(), cboGaDen.getValue().getMaGa());
		//Tìm chuyến tàu
		ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
		List<ChuyenTau> dsct = chuyenTau_DAO.timChuyenTauTheoTuyenTauVaNgayKhoiHanh(tuyenTau.getMaTuyenTau(), dpNgayKhoiHanh.getValue());
		taoPaneChuyenTau(dsct);
		return;
	}
	
	//hiển thị lỗi
	public void hienThiLoi(String tenLoi, String noiDungLoi) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Lỗi");
		alert.setHeaderText(tenLoi);
		alert.setContentText(noiDungLoi);
		alert.showAndWait();
	}
	
	//Tạo các pane chuyến tàu
	public void taoPaneChuyenTau(List<ChuyenTau> dsct) {
		int soLuongChuyenTau = dsct.size();
		int khoangCach = 8;
		int chieuCao = 134;
		int chieuRong = 574;
		int kichThuocPane = chieuCao*soLuongChuyenTau + khoangCach*(soLuongChuyenTau+1);
		pnChuyenTauKhoiHanh.setPrefHeight(kichThuocPane);
		int x = 8;//cố định
		int y = 8;//thay đổi
		for(int i = 0; i<soLuongChuyenTau;i++) {
			AnchorPane pnChuyenTau = new AnchorPane();
			pnChuyenTau.setPrefWidth(chieuRong);
			pnChuyenTau.setPrefHeight(chieuCao);
			
			Rectangle recChuyenTau = new Rectangle(chieuRong,chieuCao);
			recChuyenTau.setLayoutX(0);
			recChuyenTau.setLayoutY(0);
			recChuyenTau.setFill(Color.WHITE);
			
			DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(10); 
			dropShadow.setOffsetX(1); 
			dropShadow.setOffsetY(1); 
			dropShadow.setColor(Color.gray(0.4));

			recChuyenTau.setEffect(dropShadow);
			pnChuyenTau.getChildren().add(recChuyenTau);
			pnChuyenTau.setLayoutX(x);
			pnChuyenTau.setLayoutY(y);
			
			//Đặt nội dung vào rec
			Hyperlink link = new Hyperlink();
			pnChuyenTauKhoiHanh.getChildren().add(pnChuyenTau);
			y = y+8+chieuCao;
		}
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
		
		//Phần tra cứu
		dpNgayKhoiHanh.setEditable(false);
		dpNgayVe.setEditable(false);
		
			//Kiểm tra lúc đầu
		if(dpNgayKhoiHanh.getValue() == null) {
			dpNgayVe.setDisable(true);
		}
		dpNgayKhoiHanh.valueProperty().addListener(event->{
			if(dpNgayKhoiHanh.getValue() != null) {
				dpNgayVe.setDisable(false);
			}
		});
			//Đẩy ga lên combobox ga đi
		dayDanhSachGaDiLenCbo();
		dayDanhSachGaDenLenCbo();
		cboGaDen.valueProperty().addListener(event->{
			if(cboGaDen.getValue() != null) {
				String maDangChon = cboGaDen.getValue().getMaGa();
				Ga_DAO ga_DAO = new Ga_DAO();
				List<Ga> dsga = ga_DAO.timGaKhacMa(maDangChon);
				ObservableList<Ga> dsgaChuyenDoi = FXCollections.observableArrayList(dsga);
				cboGaDi.setItems(dsgaChuyenDoi);
			}
		});
		cboGaDi.valueProperty().addListener(event->{
			if(cboGaDi.getValue() != null) {
				String maDangChon = cboGaDi.getValue().getMaGa();
				Ga_DAO ga_DAO = new Ga_DAO();
				List<Ga> dsga = ga_DAO.timGaKhacMa(maDangChon);
				ObservableList<Ga> dsgaChuyenDoi = FXCollections.observableArrayList(dsga);
				cboGaDen.setItems(dsgaChuyenDoi);
			}
		});
			//thiết lập cho ngày khởi hành không được chọn ngày quá khứ
		dpNgayKhoiHanh.setDayCellFactory(p -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
		dpNgayVe.setDayCellFactory(p -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(dpNgayKhoiHanh.getValue()));
            }
        });
		
		//Thiết lập cho phần hiển thị chuyến tàu
		pnTabDanhSachChuyenTau.getTabs().remove(tabNgayTroVe);
		
	}
}
