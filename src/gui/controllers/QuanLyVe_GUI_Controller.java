package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import entity.Ve.TrangThaiVe;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
    private Button btnCapNhatVe;
    @FXML
    private Button btnHoan_HuyVe;
    @FXML
    private Button btnDoiVe;
    @FXML
    private Button btnTimVe;
    @FXML
    private Button btnInVe;
    
    @FXML
    private void btnTimVeClicked() {
    	String maVe = txtTimMaVe.getText().trim();
        // Kiểm tra xem có ít nhất một tiêu chí không rỗng
        if (maVe.isEmpty()) {
            hienThiLoi("Bạn chưa nhập thông tin để tìm!", "Vui lòng nhập ít nhất một trong các trường: Mã vé.");
            txtTimMaVe.requestFocus();
            return;
        }
        Ve_DAO veDAO = new Ve_DAO();
        List<Ve> danhSachVe;
        if (!maVe.isEmpty()) {
            danhSachVe = veDAO.timDanhSachVeTheoMa(maVe, true);
        }else {
            danhSachVe = new ArrayList<>();
        }
        if (danhSachVe.isEmpty()) {
            hienThiLoi("Không tìm thấy vé theo thông tin được nhập!", "");
            tbDanhSachVe.getItems().clear();
            txtTimMaVe.requestFocus();
            txtTimMaVe.selectAll();
        } else {
            tbDanhSachVe.getItems().clear();
            ObservableList<Ve> data = FXCollections.observableArrayList(danhSachVe);
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
            colGiaVe.setCellValueFactory(cellData -> {
                Ve ve = cellData.getValue();
                return new SimpleStringProperty(ve.tinhGiaVe() != 0 ? String.format("%.2f", ve.tinhGiaVe()) : "");
            });

            // Đặt dữ liệu vào table
            tbDanhSachVe.setItems(data);
        }
    }
    
    @FXML
    private void btnInVeClicked() {
    	Ve veDuocChon = tbDanhSachVe.getSelectionModel().getSelectedItem();
        if (veDuocChon == null) {
            hienThiLoi("Chưa chọn vé để thực hiện!", "Vui lòng chọn một vé trong bảng danh sách.");
            return;
        }
    	
        
    }
    
//    public void chuyenSangGiaoDienVe() throws IOException{
//		Stage primaryStage = (Stage)getScene().getWindow();
//		new Ve(primaryStage);
//	}
    
    @FXML
    private void btnHoanHuyVeClicked() {
        Ve veDuocChon = tbDanhSachVe.getSelectionModel().getSelectedItem();
        if (veDuocChon == null) {
            hienThiLoi("Chưa chọn vé để thực hiện!", "Vui lòng chọn một vé trong bảng danh sách.");
            return;
        }

        TrangThaiVe trangThaiHienTai = veDuocChon.getTrangThaiVe();

        // Chỉ xử lý nếu vé còn hiệu lực
        if (trangThaiHienTai != TrangThaiVe.hieuLuc) {
            hienThiLoi("Không thể thao tác", "Chỉ có thể hoàn hoặc hủy vé khi vé còn hiệu lực.");
            return;
        }

        // Kiểm tra ngày khởi hành
        LocalDate ngayKhoiHanh = veDuocChon.getChuyenTau().getNgayKhoiHanh();
        LocalDate ngayHienTai = LocalDate.now();

        if (ngayKhoiHanh == null) {
            hienThiLoi("Lỗi dữ liệu", "Ngày khởi hành không hợp lệ.");
            return;
        }

        // Kiểm tra điều kiện hoàn vé: hiện tại phải nhỏ hơn hoặc cách ngày khởi hành ít nhất 7 ngày
        boolean coTheHoan = ngayHienTai.isBefore(ngayKhoiHanh.minusDays(1));

        // Hỏi người dùng muốn làm gì: hoàn hay hủy
        Alert alertLuaChon = new Alert(Alert.AlertType.CONFIRMATION);
        alertLuaChon.setTitle("Chọn hành động");
        alertLuaChon.setHeaderText("Bạn muốn làm gì với vé?");
        alertLuaChon.setContentText("Mã vé: " + veDuocChon.getMaVe());

        ButtonType btnHoan = new ButtonType("Hoàn vé");
        ButtonType btnHuy = new ButtonType("Hủy vé");
        ButtonType btnHuyBo = new ButtonType("Thoát", ButtonData.CANCEL_CLOSE);

        if (coTheHoan) {
            alertLuaChon.getButtonTypes().setAll(btnHoan, btnHuy, btnHuyBo);
        } else {
            alertLuaChon.setHeaderText("Không thể hoàn vé vì thời gian không hợp lệ!\nBạn vẫn có thể hủy vé.");
            alertLuaChon.getButtonTypes().setAll(btnHuy, btnHuyBo);
        }

        Optional<ButtonType> luaChon = alertLuaChon.showAndWait();

        if (luaChon.isEmpty() || luaChon.get() == btnHuyBo) {
            return;
        }

        TrangThaiVe trangThaiMoi;
        String hanhDong;

        if (luaChon.get() == btnHoan) {
            trangThaiMoi = TrangThaiVe.hetHan;
            hanhDong = "hoàn";
        } else {
            trangThaiMoi = TrangThaiVe.daHuy;
            hanhDong = "hủy";
        }

        // Xác nhận lần cuối
        Alert alertXacNhan = new Alert(Alert.AlertType.CONFIRMATION);
        alertXacNhan.setTitle("Xác nhận " + hanhDong + " vé");
        alertXacNhan.setHeaderText("Bạn có chắc chắn muốn " + hanhDong + " vé này?");
        alertXacNhan.setContentText("Mã vé: " + veDuocChon.getMaVe());

        Optional<ButtonType> xacNhan = alertXacNhan.showAndWait();
        if (xacNhan.isPresent() && xacNhan.get() == ButtonType.OK) {
            // Cập nhật trong CSDL
            Ve_DAO veDAO = new Ve_DAO();
            boolean thanhCong;
            if (hanhDong.equals("hoàn")) {
                thanhCong = veDAO.capNhatTrangThaiVe(veDuocChon.getMaVe(),trangThaiMoi, true);
            } else {
                thanhCong = veDAO.capNhatTrangThaiVe(veDuocChon.getMaVe(),trangThaiMoi, true);
            }

            if (thanhCong) {
                veDuocChon.setTrangThaiVe(trangThaiMoi); // Cập nhật trong model
                tbDanhSachVe.refresh(); // Làm mới bảng
                hienThiThongBao("Thành công", "Đã " + hanhDong + " vé thành công.");
            } else {
                hienThiLoi("Thất bại", "Không thể " + hanhDong + " vé. Vui lòng thử lại.");
            }
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
 		Alert alert = new Alert(AlertType.INFORMATION);
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
		    Ve ve = tbDanhSachVe.getSelectionModel().getSelectedItem();
		    if (ve == null) return;

		    txtMaVe.setText(ve.getMaVe() != null ? ve.getMaVe() : "");
		    txtThongTinNgayTaoVe.setText(ve.getNgayTaoVe() != null ? ve.getNgayTaoVe().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		    txtThongTinTrangThaiVe.setText(ve.getTrangThaiVe() != null ? ve.getTrangThaiVe().toString() : "");
		    txtThongTinTenKhachHang.setText(ve.getTenKhachHang() != null ? ve.getTenKhachHang() : "");
		    txtThongTinLoaiKhachHang.setText(ve.getLoaiVe() != null ? ve.getLoaiVe().toString() : "");
		    txtThongTinCCCD_HoChieu.setText(ve.getCccd_HoChieu() != null ? ve.getCccd_HoChieu() : "");
		    txtThongTinNgaySinh.setText(ve.getNgaySinh() != null ? ve.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		    txtThongTinGiaVe.setText(ve.tinhGiaVe() != 0 ? String.format("%.2f", ve.tinhGiaVe()) : "");

		    ChuyenTau chuyenTau = ve.getChuyenTau();
		    if (chuyenTau != null) {
		        txtThongTinChuyenTau.setText(chuyenTau.getTuyenTau() != null ? chuyenTau.getTuyenTau().getTenTuyenTau() : "");
		        txtThongTinNgayKhoiHanh.setText(chuyenTau.getNgayKhoiHanh() != null ? chuyenTau.getNgayKhoiHanh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "");
		        txtThongTinGioKhoiHanh.setText(chuyenTau.getGioKhoiHanh() != null ? chuyenTau.getGioKhoiHanh().format(DateTimeFormatter.ofPattern("HH:mm")) : "");

		        TuyenTau tuyenTau = chuyenTau.getTuyenTau() != null
		            ? tuyenTauDAO.timTuyenTauTheoMaTuyenTau(chuyenTau.getTuyenTau().getMaTuyenTau(), false)
		            : null;

		        if (tuyenTau != null) {
		            txtThongTinGaDi.setText(tuyenTau.getGaDi() != null ? tuyenTau.getGaDi().getTenGa() : "");
		            txtThongTinGaDen.setText(tuyenTau.getGaDen() != null ? tuyenTau.getGaDen().getTenGa() : "");
		        } else {
		            txtThongTinGaDi.setText("");
		            txtThongTinGaDen.setText("");
		        }
		    } else {
		        txtThongTinChuyenTau.setText("");
		        txtThongTinNgayKhoiHanh.setText("");
		        txtThongTinGioKhoiHanh.setText("");
		        txtThongTinGaDi.setText("");
		        txtThongTinGaDen.setText("");
		    }

		    Cho cho = ve.getCho();
		    if (cho != null) {
		        txtThongTinCho.setText(cho.getMaCho() != null ? cho.getMaCho() : "");
		        txtThongTinCho.setText(String.valueOf(cho.getSoThuTuCho()));

		        ToaTau toaTau = toaTauDAO.timToaTauTheoMaToa(cho.getToaTau().getMaToaTau(), true);
		        if (toaTau != null) {
		            txtThongTinToa.setText(String.valueOf(toaTau.getThuTuToa()));
		            txtThongTinLoaiToa.setText(toaTau.getLoaiToa() != null ? toaTau.getLoaiToa().toString() : "");
		        } else {
		            txtThongTinToa.setText("");
		            txtThongTinLoaiToa.setText("");
		        }
		    } else {
		        txtThongTinCho.setText("");
		        txtThongTinCho.setText("");
		        txtThongTinToa.setText("");
		        txtThongTinLoaiToa.setText("");
		    }
		});
	}

	
}
