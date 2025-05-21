package entity;

import java.util.*;

public class Ve {
	private String maVe;
	private Date ngayTaoVe;
	private String tenKhachHang;
	private String cccd_HoChieu;
	private Date ngaySinh;
	private String loaiVe;
	private double giaVe;
	private double phanTramGiamGia;
	private String maHoaDon;
	private TrangThaiVe trangThaiVe;
	
	public enum TrangThaiVe {
		hieuLuc, daHuy, daDoi,hetHan;
	}
		public Ve() {
		super();
		// TODO Auto-generated constructor stub
	}			
	public Ve(String maVe, Date ngayTaoVe, String tenKhachHang, String cccd_HoChieu, Date ngaySinh, String loaiVe,
			double giaVe, double phanTramGiamGia, String maHoaDon, TrangThaiVe trangThaiVe) {
		super();
		this.maVe = maVe;
		this.ngayTaoVe = ngayTaoVe;
		this.tenKhachHang = tenKhachHang;
		this.cccd_HoChieu = cccd_HoChieu;
		this.ngaySinh = ngaySinh;
		this.loaiVe = loaiVe;
		this.giaVe = giaVe;
		this.phanTramGiamGia = phanTramGiamGia;
		this.maHoaDon = maHoaDon;
		this.trangThaiVe = trangThaiVe;
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
	public double getPhanTramGiamGia() {
		return phanTramGiamGia;
	}
	public void setPhanTramGiamGia(double phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
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
		return Objects.hash(cccd_HoChieu, giaVe, loaiVe, maHoaDon, maVe, ngaySinh, ngayTaoVe, phanTramGiamGia,
				tenKhachHang, trangThaiVe);
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
		return Objects.equals(cccd_HoChieu, other.cccd_HoChieu)
				&& Double.doubleToLongBits(giaVe) == Double.doubleToLongBits(other.giaVe)
				&& Objects.equals(loaiVe, other.loaiVe) && Objects.equals(maHoaDon, other.maHoaDon)
				&& Objects.equals(maVe, other.maVe) && Objects.equals(ngaySinh, other.ngaySinh)
				&& Objects.equals(ngayTaoVe, other.ngayTaoVe)
				&& Double.doubleToLongBits(phanTramGiamGia) == Double.doubleToLongBits(other.phanTramGiamGia)
				&& Objects.equals(tenKhachHang, other.tenKhachHang) && trangThaiVe == other.trangThaiVe;
	}
	
}
