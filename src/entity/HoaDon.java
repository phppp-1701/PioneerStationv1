package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class HoaDon {
	private String maHoaDon;
	private LocalDate ngayTaoHoaDon;
	private LocalTime gioTaoHoaDon;
	private PhuongThucThanhToan phuongThucThanhToan;
	private double phanTramGiamGia;
	private double thanhTien;
	private double tienKhachDua;
	private double tienTraLai;
	private String maKhachHang;
	private String maKhuyenMai;
	private LocalDate ngayLamViec;
	private String maCaLam;
	private String maNhanVien;

	public enum PhuongThucThanhToan {
		tienMat, chuyenKhoan;
	}

	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDon(String maHoaDon, LocalDate ngayTaoHoaDon, LocalTime gioTaoHoaDon,
			PhuongThucThanhToan phuongThucThanhToan, double phanTramGiamGia, double thanhTien, double tienKhachDua,
			double tienTraLai, String maKhachHang, String maKhuyenMai, LocalDate ngayLamViec, String maCaLam,
			String maNhanVien) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
		this.gioTaoHoaDon = gioTaoHoaDon;
		this.phuongThucThanhToan = phuongThucThanhToan;
		this.phanTramGiamGia = phanTramGiamGia;
		this.thanhTien = thanhTien;
		this.tienKhachDua = tienKhachDua;
		this.tienTraLai = tienTraLai;
		this.maKhachHang = maKhachHang;
		this.maKhuyenMai = maKhuyenMai;
		this.ngayLamViec = ngayLamViec;
		this.maCaLam = maCaLam;
		this.maNhanVien = maNhanVien;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public LocalDate getNgayTaoHoaDon() {
		return ngayTaoHoaDon;
	}
	public void setNgayTaoHoaDon(LocalDate ngayTaoHoaDon) {
		this.ngayTaoHoaDon = ngayTaoHoaDon;
	}
	public LocalTime getGioTaoHoaDon() {
		return gioTaoHoaDon;
	}
	public void setGioTaoHoaDon(LocalTime gioTaoHoaDon) {
		this.gioTaoHoaDon = gioTaoHoaDon;
	}
	public PhuongThucThanhToan getPhuongThucThanhToan() {
		return phuongThucThanhToan;
	}
	public void setPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan) {
		this.phuongThucThanhToan = phuongThucThanhToan;
	}
	public double getPhanTramGiamGia() {
		return phanTramGiamGia;
	}
	public void setPhanTramGiamGia(double phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	public double getTienKhachDua() {
		return tienKhachDua;
	}
	public void setTienKhachDua(double tienKhachDua) {
		this.tienKhachDua = tienKhachDua;
	}
	public double getTienTraLai() {
		return tienTraLai;
	}
	public void setTienTraLai(double tienTraLai) {
		this.tienTraLai = tienTraLai;
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getMaKhuyenMai() {
		return maKhuyenMai;
	}
	public void setMaKhuyenMai(String maKhuyenMai) {
		this.maKhuyenMai = maKhuyenMai;
	}
	public LocalDate getNgayLamViec() {
		return ngayLamViec;
	}
	public void setNgayLamViec(LocalDate ngayLamViec) {
		this.ngayLamViec = ngayLamViec;
	}
	public String getMaCaLam() {
		return maCaLam;
	}
	public void setMaCaLam(String maCaLam) {
		this.maCaLam = maCaLam;
	}
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}
	
	
}
