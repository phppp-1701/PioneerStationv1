package entity;

import java.time.LocalTime;
import java.util.*;

public class HoaDon {
	private String maHoaDon;
	private Date ngayTaoHoaDon;
	private LocalTime gioTaoHoaDon;
	private PhuongThucThanhToan phuongThucThanhToan;
	private double phanTramGiamGia;
	private double thanhTien;
	private double tienKhachDua;
	private double tienTraLai;
	private String maKhachHang;
	private String maKhuyenMai;
	private Date ngayLamViec;
	private String maCaLam;
	private String maNhanVien;

	public enum PhuongThucThanhToan {
		tienMat, chuyenKhoan;
	}

	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HoaDon(String maHoaDon, Date ngayTaoHoaDon, LocalTime gioTaoHoaDon, PhuongThucThanhToan phuongThucThanhToan,
			double phanTramGiamGia, double thanhTien, double tienKhachDua, double tienTraLai, String maKhachHang,
			String maKhuyenMai, Date ngayLamViec, String maCaLam, String maNhanVien) {
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

	public Date getNgayTaoHoaDon() {
		return ngayTaoHoaDon;
	}

	public void setNgayTaoHoaDon(Date ngayTaoHoaDon) {
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

	public Date getNgayLamViec() {
		return ngayLamViec;
	}

	public void setNgayLamViec(Date ngayLamViec) {
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
		return Objects.hash(gioTaoHoaDon, maCaLam, maHoaDon, maKhachHang, maKhuyenMai, maNhanVien, ngayLamViec,
				ngayTaoHoaDon, phanTramGiamGia, phuongThucThanhToan, thanhTien, tienKhachDua, tienTraLai);
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
		return Objects.equals(gioTaoHoaDon, other.gioTaoHoaDon) && Objects.equals(maCaLam, other.maCaLam)
				&& Objects.equals(maHoaDon, other.maHoaDon) && Objects.equals(maKhachHang, other.maKhachHang)
				&& Objects.equals(maKhuyenMai, other.maKhuyenMai) && Objects.equals(maNhanVien, other.maNhanVien)
				&& Objects.equals(ngayLamViec, other.ngayLamViec) && Objects.equals(ngayTaoHoaDon, other.ngayTaoHoaDon)
				&& Double.doubleToLongBits(phanTramGiamGia) == Double.doubleToLongBits(other.phanTramGiamGia)
				&& phuongThucThanhToan == other.phuongThucThanhToan
				&& Double.doubleToLongBits(thanhTien) == Double.doubleToLongBits(other.thanhTien)
				&& Double.doubleToLongBits(tienKhachDua) == Double.doubleToLongBits(other.tienKhachDua)
				&& Double.doubleToLongBits(tienTraLai) == Double.doubleToLongBits(other.tienTraLai);
	}
	
}
