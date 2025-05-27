package gui.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import dao.Ve_DAO;
import entity.HoaDon;
import entity.KhachHang.LoaiKhachHang;
import entity.Ve;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HoaDon_GUI_Controller implements Initializable {
	private HoaDon hoaDon;
	private String maNhanVien;
	private Stage primaryStage;
	public HoaDon_GUI_Controller(HoaDon hoaDon, Stage primaryStage, String maNhanVien) {
		this.hoaDon = hoaDon;
		this.maNhanVien = maNhanVien;
		this.primaryStage = primaryStage;
	}
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}



	//Khai báo
	@FXML
	private Label lblTenNhanVien;
	@FXML
	private Label lblChucVu;
	@FXML
	private Label lblMaHoaDon;
	@FXML
	private Label lblNgayTaoHoaDon;
	@FXML
	private Label lblTenKhachHang;
	@FXML
	private Label lblSoDienThoai;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblLoaiThanhVien;
	@FXML
	private TableView<Ve> tbDanhSachVe;
	@FXML
	private TableColumn<Ve, String> colSTT;
	@FXML
	private TableColumn<Ve, String> colMaVe;
	@FXML
	private TableColumn<Ve, String> colNgayKhoiHanh;
	@FXML
	private TableColumn<Ve, String> colGioKhoiHanh;
	@FXML
	private TableColumn<Ve, String> colTenDichVu;
	@FXML
	private TableColumn<Ve, String> colLoaiVe;
	@FXML
	private TableColumn<Ve, String> colGiaVe;
	@FXML
	private Label lblTongTien;
	@FXML
	private Label lblKhuyenMai;
	@FXML
	private Label lblPhuongThucThanhToan;
	@FXML
	private Label lblThanhTien;
	@FXML
	private Label lblTienKhachDua;
	@FXML
	private Label lblTienTraLai;
	@FXML
	private Button btnQuayVe;
	@FXML
	public void nhanBtnQuayVe() {
	    // Get the Stage (window) from the button's scene
	    Stage stage = (Stage) tbDanhSachVe.getScene().getWindow();
	    // Close the current window
	    stage.close();
	}
	public void hienThiLenTable() {
	    Ve_DAO ve_DAO = new Ve_DAO();
	    List<Ve> dsve = ve_DAO.timDanhSachVeTheoHoaDon(hoaDon, false);
	    double tinhTongTien = 0;
	    for(Ve ve : dsve) {
	    	tinhTongTien+=ve.tinhGiaVe();
	    }
	    DecimalFormat decimalFormat = new DecimalFormat("#,000");
		lblTongTien.setText("Tổng tiền: "+decimalFormat.format(tinhTongTien));
		lblKhuyenMai.setText(String.format("Khuyến mãi: %s giảm %.2f%%", hoaDon.getKhuyenMai().getTenKhuyenMai(), hoaDon.getPhanTramGiamGia()));
	    lblThanhTien.setText("Thành tiền "+decimalFormat.format(hoaDon.getThanhTien()));
	    lblPhuongThucThanhToan.setText(String.format("Phương thức thanh toán: %s", hoaDon.getPhuongThucThanhToan()));
	    lblTienKhachDua.setText("Tiền khách đưa: "+decimalFormat.format(hoaDon.getTienKhachDua()));
	    lblTienTraLai.setText("Tiền trả lại: "+decimalFormat.format(hoaDon.getTienTraLai()));
		// Set up cell value factories for each column
	    colSTT.setCellValueFactory(cellData -> new SimpleStringProperty(
	        String.valueOf(tbDanhSachVe.getItems().indexOf(cellData.getValue()) + 1)));
	    colMaVe.setCellValueFactory(cellData -> new SimpleStringProperty(
	        cellData.getValue().getMaVe()));
	    colNgayKhoiHanh.setCellValueFactory(cellData -> {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        return new SimpleStringProperty(
	            formatter.format(cellData.getValue().getChuyenTau().getNgayKhoiHanh()));
	    });
	    colGioKhoiHanh.setCellValueFactory(cellData -> {
	        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm");
	        return new SimpleStringProperty(
	            f.format(cellData.getValue().getChuyenTau().getGioKhoiHanh()));
	    });
	    colTenDichVu.setCellValueFactory(cellData -> {
	        Ve ve = cellData.getValue();
	        String tenDichVu = "Chuyến tàu " + ve.getChuyenTau().getTau().getTenTau() + " " +
	                          "toa " + ve.getCho().getToaTau().getThuTuToa() + " " +
	                          "loại " + ve.getCho().getToaTau().getLoaiToa().toString() + " " +
	                          "chỗ " + ve.getCho().getSoThuTuCho();
	        return new SimpleStringProperty(tenDichVu);
	    });
	    colLoaiVe.setCellValueFactory(cellData -> new SimpleStringProperty(
	        cellData.getValue().getLoaiVe().toString()));
	    colGiaVe.setCellValueFactory(cellData -> {
	        DecimalFormat formatter = new DecimalFormat("#,###");
	        return new SimpleStringProperty(
	            formatter.format(cellData.getValue().tinhGiaVe()));
	    });

	    // Clear existing items and add new data to TableView
	    tbDanhSachVe.getItems().clear();
	    tbDanhSachVe.getItems().addAll(dsve);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hienThiLenTable();
		lblTenNhanVien.setText(String.format("Tên nhân viên: %s", hoaDon.getNhanVien().getTenNhanVien()));
		lblChucVu.setText(String.format("Chức vụ: %s", hoaDon.getNhanVien().getChucVu()));
		lblMaHoaDon.setText(String.format("Mã hóa đơn: %s", hoaDon.getMaHoaDon()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		lblNgayTaoHoaDon.setText(formatter.format(hoaDon.getNgayTaoHoaDon()));
		
		if(hoaDon.getKhachHang().getLoaiKhachHang().equals(LoaiKhachHang.vangLai)) {
			lblTenKhachHang.setText("Họ và tên khách hàng: trống");
			lblSoDienThoai.setText("Số điện thoại: trống");
			lblEmail.setText("Email: trống");
			lblLoaiThanhVien.setText("Loại thành viên: Khách hàng vãng lai");
		}else {
			lblTenKhachHang.setText(String.format("Họ và tên khách hàng: %s", hoaDon.getKhachHang().getTenKhachHang()));
			lblSoDienThoai.setText(String.format("Số điện thoại: %s", hoaDon.getKhachHang().getSoDienThoai()));
			lblEmail.setText(String.format("Email: %s", hoaDon.getKhachHang().getEmail()));
			lblLoaiThanhVien.setText(String.format("Loại khách hàng: %s", hoaDon.getKhachHang().getLoaiKhachHang()));
		}
	}	
}