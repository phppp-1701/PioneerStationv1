package entity;

import java.time.LocalDate;
import java.util.*;

public class Ve {
	private String maVe;
	private LocalDate ngayTaoVe;
	private String tenKhachHang;
	private String cccd_HoChieu;
	private LocalDate ngaySinh;
	private String loaiVe;
	private double giaVe;
	private double phanTramGiamGiaCoDinh;
	private String maHoaDon;
	private TrangThaiVe trangThaiVe;
	
	public enum TrangThaiVe {
		hieuLuc, daHuy, daDoi,hetHan;
	}
		public Ve() {
		super();
		// TODO Auto-generated constructor stub
	}			
	public Ve(String maVe, LocalDate ngayTaoVe, String tenKhachHang, String cccd_HoChieu, LocalDate ngaySinh,
			String loaiVe, double giaVe, double phanTramGiamGiaCoDinh, String maHoaDon, TrangThaiVe trangThaiVe) {
		super();
		this.maVe = maVe;
		this.ngayTaoVe = ngayTaoVe;
		this.tenKhachHang = tenKhachHang;
		this.cccd_HoChieu = cccd_HoChieu;
		this.ngaySinh = ngaySinh;
		this.loaiVe = loaiVe;
		this.giaVe = giaVe;
		this.phanTramGiamGiaCoDinh = phanTramGiamGiaCoDinh;
		this.maHoaDon = maHoaDon;
		this.trangThaiVe = trangThaiVe;
	}
	public String getMaVe() {
		return maVe;
	}
	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	public LocalDate getNgayTaoVe() {
		return ngayTaoVe;
	}
	public void setNgayTaoVe(LocalDate ngayTaoVe) {
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
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getLoaiVe() {
		return loaiVe;
	}
	public void setLoaiVe(String loaiVe) {
		this.loaiVe = loaiVe;
	}
	public double getGiaVe() {
		return giaVe;
	}
	public void setGiaVe(double giaVe) {
		this.giaVe = giaVe;
	}
	public double getPhanTramGiamGiaCoDinh() {
		return phanTramGiamGiaCoDinh;
	}
	public void setPhanTramGiamGiaCoDinh(double phanTramGiamGiaCoDinh) {
		this.phanTramGiamGiaCoDinh = phanTramGiamGiaCoDinh;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public TrangThaiVe getTrangThaiVe() {
		return trangThaiVe;
	}
	public void setTrangThaiVe(TrangThaiVe trangThaiVe) {
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
