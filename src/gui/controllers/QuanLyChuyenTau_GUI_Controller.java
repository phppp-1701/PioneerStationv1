package gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dao.Ga_DAO;
import dao.NhanVien_DAO;
import dao.TuyenTau_DAO;
import entity.Ga;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.TuyenTau;
import gui.Home_GUI;
import gui.QuanLyBanVe_GUI;
import gui.QuanLyHoaDon_GUI;
import gui.QuanLyKhachHang_GUI;
import gui.QuanLyLichSu_GUI;
import gui.QuanLyNhanVien_GUI;
import gui.QuanLyTaiKhoan_GUI;
import gui.QuanLyVe_GUI;
import gui.ThongKe_GUI;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class QuanLyChuyenTau_GUI_Controller implements Initializable{
	private String maNhanVien;
	public QuanLyChuyenTau_GUI_Controller(String maNhanVien) {
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
	
	private ObservableList<Ga> layDanhSachGa;
    private FilteredList<Ga> filteredGaDiList;
    private FilteredList<Ga> filteredGaDenList;

    public void dayDanhSachGaLenCbo() {
        if (layDanhSachGa == null) {
            Ga_DAO ga_DAO = new Ga_DAO();
            List<Ga> dsga = ga_DAO.layToanBoGa();
            layDanhSachGa = FXCollections.observableArrayList(dsga);
        }

        // Khởi tạo FilteredList
        filteredGaDiList = new FilteredList<>(layDanhSachGa);
        filteredGaDenList = new FilteredList<>(layDanhSachGa);

        // Binding predicates
        cboGaDi_TuyenTau.valueProperty().addListener((obs, oldVal, newVal) -> 
            filteredGaDenList.setPredicate(g -> !g.equals(newVal))
        );
        
        cboGaDen_TuyenTau.valueProperty().addListener((obs, oldVal, newVal) -> 
            filteredGaDiList.setPredicate(g -> !g.equals(newVal))
        );

        // Gán danh sách đã lọc
        cboGaDi_TuyenTau.setItems(filteredGaDiList);
        cboGaDen_TuyenTau.setItems(filteredGaDenList);
    }
	
	//Chuyển sang giao diện trang chủ
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
		dayDanhSachGaLenCbo();
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
		
		tbDanhSachGa.setOnMouseClicked(event -> {
            Ga ga = tbDanhSachGa.getSelectionModel().getSelectedItem();
            if (ga != null) {
                txtMaGa.setText(ga.getMaGa());
                txtTenGa.setText(ga.getTenGa());
                txtDiaChi.setText(ga.getDiaChi());
            }
        });
		
		tbDanhSachTuyenTau.setOnMouseClicked(event -> {
			TuyenTau tuyentau = tbDanhSachTuyenTau.getSelectionModel().getSelectedItem();
			if (tuyentau != null) {
			txtMaTuyenTau.setText(tuyentau.getMaTuyenTau());
			txtTenTuyenTau.setText(tuyentau.getTenTuyenTau());
			txtKhoangCach.setText(String.valueOf(tuyentau.getKhoangCach()));
			cboGaDi_TuyenTau.setValue(tuyentau.getGaDi());
			cboGaDen_TuyenTau.setValue(tuyentau.getGaDen());
			}
		});
		
	}
	
	
	private void hienThiLoi(String tenLoi, String noiDungLoi) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(tenLoi);
        alert.setContentText(noiDungLoi);
        alert.showAndWait();
    }
    

    private void hienThiThongBao(String tenThongBao, String noiDungThongBao) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(tenThongBao);
        alert.setContentText(noiDungThongBao);
        alert.showAndWait();
    }
	
    @FXML
    private TextField txtTimGa;
    
    @FXML
    private Button btnTimGa;
    
    @FXML
    private TableView<Ga> tbDanhSachGa; 
    
    @FXML
    private TableColumn<Ga, String> colSTTGa;
    
    @FXML
    private TableColumn<Ga, String> colMaGa;
    
    @FXML
    private TableColumn<Ga, String> colTenGa;
    
    @FXML
    private	TableColumn<Ga, String> colDiaChi;
    
    
    @FXML
    private void btnTimGaClicked() {
    	String tenGa = txtTimGa.getText().toString();
    	if(tenGa.trim().equals("")) {
    		hienThiLoi("Bạn chưa nhập tên ga để tìm!", "");
    		txtTimGa.requestFocus();
    	}else {
    		Ga_DAO ga_dao = new Ga_DAO();
    		List<Ga> dsga = ga_dao.timGaTheoTen(tenGa);
    		if(dsga.isEmpty()) {
    			hienThiLoi("Không tìm thấy ga theo tên được nhập!", "");
    			txtTimGa.requestFocus();
    			txtTimGa.selectAll();
    		}else {
                tbDanhSachGa.getItems().clear();
                
                // Tạo ObservableList từ danh sách ga
                ObservableList<Ga> data = FXCollections.observableArrayList(dsga);
                
                // Liên kết dữ liệu với các cột

                colSTTGa.setCellValueFactory(cellData -> 
	            new SimpleStringProperty(String.valueOf(tbDanhSachGa.getItems().indexOf(cellData.getValue()) + 1)));
                
                colMaGa.setCellValueFactory(new PropertyValueFactory<>("maGa"));
                colTenGa.setCellValueFactory(new PropertyValueFactory<>("tenGa"));
                colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
                
                // Đặt dữ liệu vào table
                tbDanhSachGa.setItems(data);
    		}
    	}
    }
    
	
	@FXML
    private TextField txtMaGa;
    
    @FXML
    private TextField txtTenGa;
    
    @FXML
    private TextArea txtDiaChi;
    
    @FXML
    private Button btnCapNhatGa;
    
    @FXML
    private Button btnThemGa;
    
    @FXML
    private Button btnThemGaClicked;
    
    
    @FXML
    private void btnThemGaClicked() {
        String tenGa = txtTenGa.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        
        if(!txtMaGa.getText().trim().equals("")) {
        	hienThiLoi("Bạn đang chọn một ga, vui lòng nhấn làm rỗng rồi mới thêm!", "");
        	return;
        }

        // Kiểm tra các trường nhập liệu không được để trống
        if (tenGa.isEmpty() || diaChi.isEmpty()) {
        	hienThiLoi("Tên ga và địa chỉ không được để trống!", "");
            if (tenGa.isEmpty()) {
                txtTenGa.requestFocus();
            } else {
                txtDiaChi.requestFocus();
            }
            return;
        }

        Ga_DAO ga_DAO = new Ga_DAO();

        // Kiểm tra xem tên ga đã tồn tại chưa
        if (ga_DAO.kiemTraTonTaiGaTheoTen(tenGa)) {
        	hienThiLoi("Tên ga '" + tenGa + "' đã tồn tại! Vui lòng chọn tên khác.", "");
            txtTenGa.requestFocus();
            txtTenGa.selectAll();
            return;
        }

        // Tạo mã ga mới
        String maGaMoi = ga_DAO.taoMaGaMoi();
        
        // Tạo đối tượng ga mới
        Ga ga = new Ga(maGaMoi, tenGa, diaChi);
        
        // Thêm ga vào cơ sở dữ liệu
        if (ga_DAO.themGa(ga)) {
        	hienThiThongBao("Thêm ga thành công! Mã ga: " + maGaMoi, "");
            txtMaGa.setText(maGaMoi); // Hiển thị mã ga mới lên txtMaGa
            btnTimGaClicked(); // Cập nhật lại danh sách ga trong TableView
        } else {
            hienThiLoi("Thêm ga thất bại!", "");
        }
    }
    
    @FXML
    private Button btnLamRongGa;
    
    @FXML
    private void btnLamRongGaClicked() {
    	txtMaGa.setText("");
    	txtTenGa.setText("");
    	txtDiaChi.setText("");
    }
    
    @FXML
    private void btnCapNhatGaClicked() {
        // Kiểm tra xem đã chọn ga để cập nhật chưa
        if (txtMaGa.getText().trim().isEmpty()) {
        	hienThiLoi("Vui lòng chọn một ga từ danh sách để cập nhật!", "");
            return;
        }

        // Lấy thông tin từ giao diện
        String maGa = txtMaGa.getText().trim();
        String tenGa = txtTenGa.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        // Kiểm tra các trường nhập liệu không được để trống
        if (tenGa.isEmpty() || diaChi.isEmpty()) {
        	hienThiLoi("Tên ga và địa chỉ không được để trống!", "");
            if (tenGa.isEmpty()) {
                txtTenGa.requestFocus();
            } else {
                txtDiaChi.requestFocus();
            }
            return;
        }

        Ga_DAO ga_DAO = new Ga_DAO();

        // Kiểm tra xem tên ga mới có trùng với ga khác không (ngoại trừ ga hiện tại)
        List<Ga> dsGa = ga_DAO.timGaTheoTen(tenGa);
        if (!dsGa.isEmpty() && dsGa.stream().anyMatch(ga -> ga.getTenGa().equals(tenGa) && !ga.getMaGa().equals(maGa))) {
        	hienThiLoi("Tên ga '" + tenGa + "' đã tồn tại! Vui lòng chọn tên khác.", "");
            txtTenGa.requestFocus();
            txtTenGa.selectAll();
            return;
        }

        // Tạo đối tượng Ga với thông tin cập nhật
        Ga ga = new Ga(maGa, tenGa, diaChi);

        // Cập nhật ga trong cơ sở dữ liệu
        if (ga_DAO.capNhatGa(ga)) {
        	hienThiThongBao("Cập nhật ga thành công!", "");
            btnTimGaClicked(); // Cập nhật lại danh sách ga trong TableView
        } else {
            hienThiLoi("Cập nhật ga thất bại!", "");
        }
    }
    
 // Tuyến tàu
    @FXML
    private TextField txtTimTuyenTau;
    @FXML
    private Button btnTimTuyenTau;
    @FXML
    private TableView<TuyenTau> tbDanhSachTuyenTau; 
    @FXML
    private TableColumn<TuyenTau, String> colSTTTuyenTau;
    @FXML
    private TableColumn<TuyenTau, String> colMaTuyenTau;
    @FXML
    private TableColumn<TuyenTau, String> colTenTuyenTau;
    @FXML
    private TableColumn<TuyenTau, String> colKhoangCach;
    @FXML
    private TableColumn<TuyenTau, String> colGaDi;
    @FXML
    private TableColumn<TuyenTau, String> colGaDen;
    @FXML
    private TextField txtMaTuyenTau;
    @FXML
    private TextField txtTenTuyenTau;
    @FXML
    private TextField txtKhoangCach;
    @FXML
    private ComboBox<Ga> cboGaDi_TuyenTau;
    @FXML
    private ComboBox<Ga> cboGaDen_TuyenTau;
    @FXML
    private Button btnThemTuyenTau;
    @FXML
    private Button btnCapNhatTuyenTau;
    @FXML
    private Button btnLamRongTuyenTau;
    @FXML
    private void btnTimTuyenTauClicked() {
    	String tenTuyenTau = txtTimTuyenTau.getText().toString();
    	if(tenTuyenTau.trim().equals("")) {
    		hienThiLoi("Bạn chưa nhập tên tuyến tàu để tìm!", "");
    		txtTimTuyenTau.requestFocus();
    	}else {
    		TuyenTau_DAO tuyentau_dao = new TuyenTau_DAO();
    		List<TuyenTau> dstuyentau = tuyentau_dao.timTuyenTauTheoTen(tenTuyenTau);
    		if(dstuyentau.isEmpty()) {
    			hienThiLoi("Không tìm thấy tuyến tàu theo tên được nhập!", "");
    			txtTimTuyenTau.requestFocus();
    			txtTimTuyenTau.selectAll();
    		}else {
    			tbDanhSachTuyenTau.getItems().clear();
    			// Tạo ObservableList từ danh sách ga
    			ObservableList<TuyenTau> data = FXCollections.observableArrayList(dstuyentau);
    			// Liên kết dữ liệu với các cột

    			colSTTTuyenTau.setCellValueFactory(cellData -> 
    			new SimpleStringProperty(String.valueOf(tbDanhSachTuyenTau.getItems().indexOf(cellData.getValue()) + 1)));
    			colMaTuyenTau.setCellValueFactory(new PropertyValueFactory<>("maTuyenTau"));
    			colTenTuyenTau.setCellValueFactory(new PropertyValueFactory<>("tenTuyenTau"));
    			colKhoangCach.setCellValueFactory(new PropertyValueFactory<>("khoangCach"));
    			colGaDi.setCellValueFactory(cellData -> {
    			    TuyenTau tuyenTau = cellData.getValue();
    			    if (tuyenTau != null && tuyenTau.getGaDi() != null) {
    			        return new SimpleStringProperty(tuyenTau.getGaDi().getTenGa());
    			    }
    			    return new SimpleStringProperty("");
    			});

    			colGaDen.setCellValueFactory(cellData -> {
    			    TuyenTau tuyenTau = cellData.getValue();
    			    if (tuyenTau != null && tuyenTau.getGaDen() != null) {
    			        return new SimpleStringProperty(tuyenTau.getGaDen().getTenGa());
    			    }
    			    return new SimpleStringProperty("");
    			});
			    // Đặt dữ liệu vào table
			    tbDanhSachTuyenTau.setItems(data);
			    }
    	}
    }
    
    @FXML
    private void btnThemTuyenTauClicked() {
        // Lấy thông tin từ giao diện
        String tenTuyenTau = txtTenTuyenTau.getText().trim();
        String khoangCachStr = txtKhoangCach.getText().trim();
        Ga gaDi = cboGaDi_TuyenTau.getValue();
        Ga gaDen = cboGaDen_TuyenTau.getValue();

        // Kiểm tra các trường nhập liệu không được để trống
        if ( tenTuyenTau.isEmpty() || khoangCachStr.isEmpty() || gaDi == null || gaDen == null) {
            hienThiLoi("Vui lòng nhập đầy đủ thông tin: mã tuyến, tên tuyến, khoảng cách, ga đi, ga đến!", "");
            if (tenTuyenTau.isEmpty()) {
                txtTenTuyenTau.requestFocus();
            } else if (khoangCachStr.isEmpty()) {
                txtKhoangCach.requestFocus();
            } else if (gaDi == null) {
                cboGaDi_TuyenTau.requestFocus();
            } else {
                cboGaDen_TuyenTau.requestFocus();
            }
            return;
        }

        // Chuyển đổi khoảng cách thành số
        double khoangCach;
        try {
            khoangCach = Double.parseDouble(khoangCachStr);
            if (khoangCach <= 0) {
                hienThiLoi("Khoảng cách phải lớn hơn 0!", "");
                txtKhoangCach.requestFocus();
                txtKhoangCach.selectAll();
                return;
            }
        } catch (NumberFormatException e) {
            hienThiLoi("Khoảng cách phải là số hợp lệ!", "");
            txtKhoangCach.requestFocus();
            txtKhoangCach.selectAll();
            return;
        }


        // Kiểm tra xem mã tuyến tàu đã tồn tại chưa
        TuyenTau_DAO tuyentau_dao = new TuyenTau_DAO();
        // Kiểm tra xem tên tuyến tàu đã tồn tại chưa
        List<TuyenTau> dsTuyenTau = tuyentau_dao.timTuyenTauTheoTen(tenTuyenTau);
        if (!dsTuyenTau.isEmpty()) {
            hienThiLoi("Tên tuyến tàu '" + tenTuyenTau + "' đã tồn tại! Vui lòng chọn tên khác.", "");
            txtTenTuyenTau.requestFocus();
            txtTenTuyenTau.selectAll();
            return;
        }
        String maTuyenTauMoi = tuyentau_dao.taoMaTuyenTauMoi();
        // Tạo đối tượng TuyenTau với thông tin mới
        TuyenTau tuyenTau = new TuyenTau(maTuyenTauMoi, tenTuyenTau, khoangCach, gaDi, gaDen);

        // Thêm tuyến tàu vào cơ sở dữ liệu
        if (tuyentau_dao.themTuyenTau(tuyenTau)) {
            hienThiThongBao("Thêm tuyến tàu thành công!", "");
            btnTimTuyenTauClicked(); // Cập nhật lại danh sách tuyến tàu trong TableView
            // Làm mới các trường nhập liệu
            txtMaTuyenTau.clear();
            txtTenTuyenTau.clear();
            txtKhoangCach.clear();
            cboGaDi_TuyenTau.getSelectionModel().clearSelection();
            cboGaDen_TuyenTau.getSelectionModel().clearSelection();
        } else {
            hienThiLoi("Thêm tuyến tàu thất bại!", "");
        }
    }
    
    @FXML
    private void btnLamRongTuyenTauClicked() {
        txtMaTuyenTau.setText("");;
        txtTenTuyenTau.setText("");
        txtKhoangCach.setText("");
        cboGaDi_TuyenTau.setValue(null);
        cboGaDen_TuyenTau.setValue(null);
    }
    
    @FXML
    private void btnCapNhatTuyenTauClicked() {
        // Kiểm tra xem đã chọn tuyến tàu để cập nhật chưa
        if (txtMaTuyenTau.getText().trim().isEmpty()) {
            hienThiLoi("Vui lòng chọn một tuyến tàu từ danh sách để cập nhật!", "");
            return;
        }

        // Lấy thông tin từ giao diện
        String maTuyenTau = txtMaTuyenTau.getText().trim();
        String tenTuyenTau = txtTenTuyenTau.getText().trim();
        String khoangCachStr = txtKhoangCach.getText().trim();
        Ga gaDi = cboGaDi_TuyenTau.getValue();
        Ga gaDen = cboGaDen_TuyenTau.getValue();

        // Kiểm tra các trường nhập liệu không được để trống
        if (tenTuyenTau.isEmpty() || khoangCachStr.isEmpty() || gaDi == null || gaDen == null) {
            hienThiLoi("Vui lòng nhập đầy đủ thông tin: tên tuyến, khoảng cách, ga đi, ga đến!", "");
            if (tenTuyenTau.isEmpty()) {
                txtTenTuyenTau.requestFocus();
            } else if (khoangCachStr.isEmpty()) {
                txtKhoangCach.requestFocus();
            } else if (gaDi == null) {
                cboGaDi_TuyenTau.requestFocus();
            } else {
                cboGaDen_TuyenTau.requestFocus();
            }
            return;
        }

        // Chuyển đổi khoảng cách thành số
        double khoangCach;
        try {
            khoangCach = Double.parseDouble(khoangCachStr);
            if (khoangCach <= 0) {
                hienThiLoi("Khoảng cách phải lớn hơn 0!", "");
                txtKhoangCach.requestFocus();
                txtKhoangCach.selectAll();
                return;
            }
        } catch (NumberFormatException e) {
            hienThiLoi("Khoảng cách phải là số hợp lệ!", "");
            txtKhoangCach.requestFocus();
            txtKhoangCach.selectAll();
            return;
        }


        // Kiểm tra xem tên tuyến tàu mới có trùng với tuyến khác không (ngoại trừ tuyến hiện tại)
        TuyenTau_DAO tuyentau_dao = new TuyenTau_DAO();
        List<TuyenTau> dsTuyenTau = tuyentau_dao.timTuyenTauTheoTen(tenTuyenTau);
        if (!dsTuyenTau.isEmpty() && dsTuyenTau.stream().anyMatch(tuyen -> 
                tuyen.getTenTuyenTau().equals(tenTuyenTau) && !tuyen.getMaTuyenTau().equals(maTuyenTau))) {
            hienThiLoi("Tên tuyến tàu '" + tenTuyenTau + "' đã tồn tại! Vui lòng chọn tên khác.", "");
            txtTenTuyenTau.requestFocus();
            txtTenTuyenTau.selectAll();
            return;
        }

        // Tạo đối tượng TuyenTau với thông tin cập nhật
        TuyenTau tuyenTau = new TuyenTau(maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen);

        // Cập nhật tuyến tàu trong cơ sở dữ liệu
        if (tuyentau_dao.capNhatTuyenTau(tuyenTau)) {
            hienThiThongBao("Cập nhật tuyến tàu thành công!", "");
            btnTimTuyenTauClicked(); // Cập nhật lại danh sách tuyến tàu trong TableView
            // Làm mới các trường nhập liệu
            txtMaTuyenTau.clear();
            txtTenTuyenTau.clear();
            txtKhoangCach.clear();
            cboGaDi_TuyenTau.getSelectionModel().clearSelection();
            cboGaDen_TuyenTau.getSelectionModel().clearSelection();
        } else {
            hienThiLoi("Cập nhật tuyến tàu thất bại!", "");
        }
    }

}
