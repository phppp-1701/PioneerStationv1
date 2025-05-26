package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import dao.ChuyenTau_DAO;
import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.Tau_DAO;
import dao.ToaTau_DAO;
import dao.TuyenTau_DAO;
import entity.ChuyenTau;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.ToaTau;
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
import javafx.geometry.NodeOrientation;
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
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
	@FXML
	private AnchorPane pnChuyenTauKhuHoi;
	private Button btnDangChon = null;
	@FXML
	private AnchorPane pnToaTau;
	
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
		taoPaneChuyenTau(dsct, pnChuyenTauKhoiHanh, cboGaDi.getValue(), cboGaDen.getValue());
		if(dpNgayVe.getValue() != null) {
			taoPaneChuyenTau(dsct, pnChuyenTauKhuHoi, cboGaDi.getValue(), cboGaDen.getValue());
		}
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
	
	public void hienThiThongTin(String tenThongTin, String noiDungThongTin) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Thông tin");
	    alert.setHeaderText(tenThongTin);
	    
	    // Tạo TextArea để hiển thị nội dung dài
	    TextArea textArea = new TextArea(noiDungThongTin);
	    textArea.setEditable(false); // Không cho phép chỉnh sửa
	    textArea.setWrapText(true); // Tự động xuống dòng
	    textArea.setMaxWidth(500); // Giới hạn chiều rộng
	    textArea.setMaxHeight(400); // Giới hạn chiều cao
	    
	    // Đặt TextArea vào DialogPane
	    VBox content = new VBox(textArea);
	    alert.getDialogPane().setContent(content);
	    
	    // Tùy chỉnh kích thước Alert (tùy chọn)
	    alert.getDialogPane().setMinWidth(600);
	    alert.getDialogPane().setMinHeight(200);
	    
	    alert.showAndWait();
	}
	
	//Thêm ảnh đầu vào pane toa
	public void themAnhToaDau() {
		String imgURL = "file:image/QuanLyBanVe_ToaTauLai.png";
		Image image = new Image(imgURL);
		ImageView imgView = new ImageView(image);
		imgView.setLayoutX(0);
		imgView.setLayoutY(27);
		imgView.setFitWidth(155);
		imgView.setFitHeight(149);
		imgView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		pnToaTau.getChildren().add(imgView);
	}
	
	//Tạo pane toa tàu
	public void taoPaneToaTau(List<ToaTau> dstt) {
		int soLuongToaTau = dstt.size();
		int chieuRong = 160 + soLuongToaTau*280 + 10*soLuongToaTau;
		pnToaTau.setPrefWidth(chieuRong);
		System.out.println("Toa có "+soLuongToaTau+" toa tàu");
		themAnhToaDau();
		for(int i = 0; i < soLuongToaTau; i++) {
			AnchorPane pnToa = new AnchorPane();
			pnToa.setPrefWidth(280);
			pnToa.setPrefHeight(110);
			Rectangle recNen = new Rectangle();
			recNen.setWidth(280);
			recNen.setHeight(110);
			recNen.setLayoutX(0);
			recNen.setLayoutY(0);
			recNen.setFill(Color.WHITE);
			DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(10); 
			dropShadow.setOffsetX(1);
			dropShadow.setOffsetY(1); 
			dropShadow.setColor(Color.gray(0.4)); 
			recNen.setEffect(dropShadow);
			Rectangle recTrangThai = new Rectangle();
			recTrangThai.setWidth(280);
			recTrangThai.setHeight(15);
			recTrangThai.setLayoutX(0);
			recTrangThai.setLayoutY(0);
			recTrangThai.setFill(Color.valueOf("#ccdaf5"));
			pnToa.getChildren().addAll(recNen, recTrangThai);
			pnToa.setLayoutX(160+280*i+i*10);
			pnToa.setLayoutY(46);
			pnToaTau.getChildren().add(pnToa);
		}
	}
	
	//Tạo các pane chuyến tàu
	public void taoPaneChuyenTau(List<ChuyenTau> dsct, AnchorPane pnChuyenTauA, Ga gaDi, Ga gaDen) {
		pnChuyenTauA.getChildren().clear();
		int soLuongChuyenTau = dsct.size();
		int khoangCach = 8;
		int chieuCao = 134;
		int chieuRong = 574;
		int kichThuocPane = chieuCao*soLuongChuyenTau + khoangCach*(soLuongChuyenTau+1);
		pnChuyenTauA.setPrefHeight(kichThuocPane);
		int x = 8;//cố định
		int y = 8;//thay đổi
		for(int i = 0; i<soLuongChuyenTau;i++) {
			ChuyenTau ct = dsct.get(i);
			Tau_DAO tau_DAO = new Tau_DAO();
			Tau tau = tau_DAO.timTauTheoMa(dsct.get(i).getMaTau());
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
			Hyperlink link = new Hyperlink(tau.getTenTau());
			link.setFont(Font.font("Tahoma", 14));
			link.setStyle("-fx-text-fill: #2e7d32;");
			link.setLayoutX(14);
			link.setLayoutY(55);
			pnChuyenTau.getChildren().add(link);
			pnChuyenTauA.getChildren().add(pnChuyenTau);
			
			Label lblTenTau = new Label();
			lblTenTau.setFont(Font.font("Tahoma",14));
			lblTenTau.setLayoutX(16);
			lblTenTau.setLayoutY(24);
			if(tau.getLoaiTau().equals(LoaiTau.tauChatLuong)) {
				lblTenTau.setText("Tàu chất lượng");
			}else if(tau.getLoaiTau().equals(LoaiTau.tauThongNhat)) {
				lblTenTau.setText("Tàu thống nhất");
			}else if(tau.getLoaiTau().equals(LoaiTau.tauDiaPhuong)) {
				lblTenTau.setText("Tàu địa phương");
			}else {
				lblTenTau.setText("Tàu du lịch");
			}
			link.setOnAction(event->{
				if(tau.getLoaiTau().equals(LoaiTau.tauChatLuong)) {
					hienThiThongTin("Tàu chất lượng", "Đây được đánh giá là loại tàu chất lượng bậc nhất trong các loại tàu SE. Với thiết kế các toa rộng rãi, tiện nghi, mang đến sự thoải mái và hiện đại cho hành khách. Trên tường có lớp đệm tựa, phù hợp cho bạn ngồi hoặc nằm nghỉ. Nội thất toa xe với độ bền cao và khả năng chống cháy, chống bám bụi. Và trên các tuyến SE1/SE2 có cung cấp khoang 2 giường VIP cho những du khách cần không gian tiện nghi và riêng tư hơn.");
				}else if(tau.getLoaiTau().equals(LoaiTau.tauThongNhat)) {
					hienThiThongTin("Tàu thống nhất", "Tàu Thống Nhất (ký hiệu TN) là loại tàu hỏa phục vụ tuyến đường sắt Bắc – Nam (Hà Nội – TP. Hồ Chí Minh), dừng tại nhiều ga hơn so với tàu SE, với thời gian di chuyển lâu hơn nhưng giá vé tiết kiệm hơn. Tàu TN hiện ít được vận hành, nhường chỗ cho các tàu SE chất lượng cao, được trang bị tiện nghi như điều hòa, ghế xoay 180 độ, giường nằm rộng rãi, Wi-Fi miễn phí, và toa ăn uống phục vụ hành khách.");
				}else if(tau.getLoaiTau().equals(LoaiTau.tauDiaPhuong)) {
					hienThiThongTin("Tàu địa phương", "Một trong các loại tàu hỏa trên đường sắt Việt Nam tiếp theo là tàu địa phương và tàu du lịch. Đây là các chuyến tàu phục vụ hành khách trên các tuyến ngắn, kết nối các tỉnh và thành phố lân cận. Tàu sẽ thường dừng tại nhiều ga nhỏ, tạo điều kiện thuận lợi cho việc di chuyển giữa các địa phương. Các chuyến tàu này phù hợp cho những lịch trình ngắn hoặc trải nghiệm du lịch.");
				}else {
					hienThiThongTin("Tàu du lịch", "Một trong các loại tàu hỏa trên đường sắt Việt Nam tiếp theo là tàu địa phương và tàu du lịch. Đây là các chuyến tàu phục vụ hành khách trên các tuyến ngắn, kết nối các tỉnh và thành phố lân cận. Tàu sẽ thường dừng tại nhiều ga nhỏ, tạo điều kiện thuận lợi cho việc di chuyển giữa các địa phương. Các chuyến tàu này phù hợp cho những lịch trình ngắn hoặc trải nghiệm du lịch.");
				}
			});
			pnChuyenTau.getChildren().add(lblTenTau);
			Label lblGaDi = new Label(gaDi.getTenGa());
			Label lblGaDen = new Label(gaDen.getTenGa());
			lblGaDi.setLayoutX(182);
			lblGaDi.setLayoutY(24);
			lblGaDen.setLayoutX(340);
			lblGaDen.setLayoutY(24);
			lblGaDi.setFont(Font.font("Tahoma", 14));
			lblGaDen.setFont(Font.font("Tahoma", 14));
			pnChuyenTau.getChildren().add(lblGaDi);
			pnChuyenTau.getChildren().add(lblGaDen);
			Label lblGioKhoiHanh = new Label(dsct.get(i).getGioKhoiHanh().toString());
			lblGioKhoiHanh.setLayoutX(196);
			lblGioKhoiHanh.setLayoutY(58);
			lblGioKhoiHanh.setFont(Font.font("Tahoma", FontWeight.BOLD ,14));
			Label lblDuKien = new Label(dsct.get(i).getGioDuKien().toString());
			lblDuKien.setLayoutX(351);
			lblDuKien.setLayoutY(58);
			lblDuKien.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
			pnChuyenTau.getChildren().addAll(lblGioKhoiHanh, lblDuKien);
			
			Label lblDen = new Label("Đến");
			lblDen.setLayoutX(275);
			lblDen.setLayoutY(58);
			lblDen.setFont(Font.font("Times new roman", FontPosture.ITALIC,14));
			pnChuyenTau.getChildren().add(lblDen);
			
			Label lblNgayKhoiHanh = new Label();
			LocalDate ngayKhoiHanh = dsct.get(0).getNgayKhoiHanh();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String ngayKhoiHanhChuyenDoi = ngayKhoiHanh.format(dateTimeFormatter);
			lblNgayKhoiHanh.setText(ngayKhoiHanhChuyenDoi);
			lblNgayKhoiHanh.setLayoutX(168);
			lblNgayKhoiHanh.setLayoutY(93);
			lblNgayKhoiHanh.setFont(Font.font("Tahoma",14));
			
			Label lblNgayDuKien = new Label();
			LocalDate ngayDuKien = dsct.get(0).getNgayDuKien();
			String ngayDuKienChuyenDoi = ngayDuKien.format(dateTimeFormatter);
			lblNgayDuKien.setText(ngayDuKienChuyenDoi);
			lblNgayDuKien.setLayoutX(330);
			lblNgayDuKien.setLayoutY(93);
			lblNgayDuKien.setFont(Font.font("Tahoma",14));
			pnChuyenTau.getChildren().addAll(lblNgayKhoiHanh, lblNgayDuKien);
			
			Button btnChon = new Button("Chọn chuyến tàu");
			btnChon.setLayoutX(440);
			btnChon.setLayoutY(79);
			btnChon.setFont(Font.font("Tahoma", 14));
			btnChon.setStyle("-fx-text-fill: #ffffff;");
			btnChon.getStyleClass().add("btn-chonChuyenTau");
			btnChon.setUserData(ct);
			btnChon.setOnAction(event->{
				ToaTau_DAO toaTau_DAO = new ToaTau_DAO();
				List<ToaTau> dstt = toaTau_DAO.timToaTauTheoMaTau(ct.getMaTau());
				if(btnDangChon != null) {
					if(btnChon == btnDangChon) {
						btnChon.getStyleClass().remove("btn-chonChuyenTauDangChon");
						btnChon.getStyleClass().add("btn-chonChuyenTau");
						pnToaTau.getChildren().clear();
						btnDangChon = null;
					}else {
						btnDangChon.getStyleClass().remove("btn-chonChuyenTauDangChon");
						btnDangChon.getStyleClass().add("btn-chonChuyenTau");
						btnChon.getStyleClass().remove("btn-chonChuyenTau");
						btnChon.getStyleClass().add("btn-chonChuyenTauDangChon");
						btnDangChon = btnChon;
						taoPaneToaTau(dstt);
					}
				}else {
					btnChon.getStyleClass().remove("btn-chonChuyenTau");
					btnChon.getStyleClass().add("btn-chonChuyenTauDangChon");
					btnDangChon = btnChon;
					taoPaneToaTau(dstt);
				}
			});
			pnChuyenTau.getChildren().add(btnChon);
			//Cập nhật trục y
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
		dpNgayVe.valueProperty().addListener(event->{
			if(dpNgayVe.getValue() != null) {
				pnTabDanhSachChuyenTau.getTabs().add(tabNgayTroVe);
			}else {
				pnTabDanhSachChuyenTau.getTabs().remove(tabNgayTroVe);
			}
		});
	}
}
