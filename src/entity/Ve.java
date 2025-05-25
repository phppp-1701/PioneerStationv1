package entity;

import java.util.*;

public class Ve {
	private String maVe;
	private Date ngayTaoVe;
	private String tenKhachHang;
	private String cccd_HoChieu;
	private Date ngaySinh;
	private LoaiVe loaiVe;
	private double giaVe;
	private double phanTramGiamGia;
	private HoaDon hoaDon;
	private TrangThaiVe trangThaiVe;
	
	public enum TrangThaiVe {
		hieuLuc, daHuy, daDoi,hetHan;
	}
	
	public enum LoaiVe {
		giuongNam, ngoiMem;
	}

	public String getMaVe() {
		return maVe;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}

	public Date getNgayTaoVe() {
		return ngayTaoVe;
	}

	public void setNgayTaoVe(Date ngayTaoVe) {
		this.ngayTaoVe = ngayTaoVe;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getCccd_HoChieu() {
		return cccd_HoChieu;
	}

	public void setCccd_HoChieu(String cccd_HoChieu) {
		this.cccd_HoChieu = cccd_HoChieu;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public LoaiVe getLoaiVe() {
		return loaiVe;
	}

	public void setLoaiVe(LoaiVe loaiVe) {
		this.loaiVe = loaiVe;
	}

	public double getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(double giaVe) {
		this.giaVe = giaVe;
	}

	public double getPhanTramGiamGia() {
		return phanTramGiamGia;
	}

	public void setPhanTramGiamGia(double phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public TrangThaiVe getTrangThaiVe() {
		return trangThaiVe;
	}

	public void setTrangThaiVe(TrangThaiVe trangThaiVe) {
		this.trangThaiVe = trangThaiVe;
	}

	public Ve() {
	}

	public Ve(String maVe, Date ngayTaoVe, String tenKhachHang, String cccd_HoChieu, Date ngaySinh, LoaiVe loaiVe,
			double giaVe, double phanTramGiamGia, HoaDon hoaDon, TrangThaiVe trangThaiVe) {
		super();
		this.maVe = maVe;
		this.ngayTaoVe = ngayTaoVe;
		this.tenKhachHang = tenKhachHang;
		this.cccd_HoChieu = cccd_HoChieu;
		this.ngaySinh = ngaySinh;
		this.loaiVe = loaiVe;
		this.giaVe = giaVe;
		this.phanTramGiamGia = phanTramGiamGia;
		this.hoaDon = hoaDon;
		this.trangThaiVe = trangThaiVe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maVe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ve other = (Ve) obj;
		return Objects.equals(maVe, other.maVe);
	}
	
	
}
