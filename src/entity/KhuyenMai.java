package entity;

import java.time.LocalDate;
import java.util.Objects;

public class KhuyenMai {
	private String maKhuyenMai;
	private String tenKhuyenMai;
	private LocalDate ngayBatDauSuKien;
	private LocalDate ngayKetThucSuKien;
	private LoaiKhachHang loaiKhachHang;
	private double phanTramGiamGiaSuKien;

	public enum LoaiKhachHang {
		thanThiet, vip, vangLai;
	}
	public KhuyenMai() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, LocalDate ngayBatDauSuKien,
			LocalDate ngayKetThucSuKien, LoaiKhachHang loaiKhachHang, double phanTramGiamGiaSuKien) {
		super();
		this.maKhuyenMai = maKhuyenMai;
		this.tenKhuyenMai = tenKhuyenMai;
		this.ngayBatDauSuKien = ngayBatDauSuKien;
		this.ngayKetThucSuKien = ngayKetThucSuKien;
		this.loaiKhachHang = loaiKhachHang;
		this.phanTramGiamGiaSuKien = phanTramGiamGiaSuKien;
	}
	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}
	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}
	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}
	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}
	public LocalDate getNgayBatDauSuKien() {
		return ngayBatDauSuKien;
	}
	public void setNgayBatDauSuKien(LocalDate ngayBatDauSuKien) {
		this.ngayBatDauSuKien = ngayBatDauSuKien;
	}
	public LocalDate getNgayKetThucSuKien() {
		return ngayKetThucSuKien;
	}
	public void setNgayKetThucSuKien(LocalDate ngayKetThucSuKien) {
		this.ngayKetThucSuKien = ngayKetThucSuKien;
	}
	public LoaiKhachHang getLoaiKhachHang() {
		return loaiKhachHang;
	}
	public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
		this.loaiKhachHang = loaiKhachHang;
	}
	public double getPhanTramGiamGiaSuKien() {
		return phanTramGiamGiaSuKien;
	}
	public void setPhanTramGiamGiaSuKien(double phanTramGiamGiaSuKien) {
		this.phanTramGiamGiaSuKien = phanTramGiamGiaSuKien;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKhuyenMai);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhuyenMai other = (KhuyenMai) obj;
		return Objects.equals(maKhuyenMai, other.maKhuyenMai);
	}
}
