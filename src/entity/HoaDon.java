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
	private KhachHang khachHang;
	private KhuyenMai khuyenMai;
	private LocalDate ngayLamViec;
	private CaLam caLam;
	private NhanVien nhanVien;

	public enum PhuongThucThanhToan {
		tienMat, chuyenKhoan;
	}

	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDon(String maHoaDon, LocalDate ngayTaoHoaDon, LocalTime gioTaoHoaDon,
			PhuongThucThanhToan phuongThucThanhToan, double phanTramGiamGia, double thanhTien, double tienKhachDua,
			double tienTraLai, KhachHang khachHang, KhuyenMai khuyenMai, LocalDate ngayLamViec, CaLam caLam,
			NhanVien nhanVien) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
		this.gioTaoHoaDon = gioTaoHoaDon;
		this.phuongThucThanhToan = phuongThucThanhToan;
		this.phanTramGiamGia = phanTramGiamGia;
		this.thanhTien = thanhTien;
		this.tienKhachDua = tienKhachDua;
		this.tienTraLai = tienTraLai;
		this.khachHang = khachHang;
		this.khuyenMai = khuyenMai;
		this.ngayLamViec = ngayLamViec;
		this.caLam = caLam;
		this.nhanVien = nhanVien;
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
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public KhuyenMai getKhuyenMai() {
		return khuyenMai;
	}
	public void setKhuyenMai(KhuyenMai khuyenMai) {
		this.khuyenMai = khuyenMai;
	}
	public LocalDate getNgayLamViec() {
		return ngayLamViec;
	}
	public void setNgayLamViec(LocalDate ngayLamViec) {
		this.ngayLamViec = ngayLamViec;
	}
	public CaLam getCaLam() {
		return caLam;
	}
	public void setCaLam(CaLam caLam) {
		this.caLam = caLam;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
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
