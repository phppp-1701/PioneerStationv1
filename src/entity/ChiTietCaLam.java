package entity;

import java.time.LocalTime;
import java.util.*;

public class ChiTietCaLam {
	private Date ngayLamViec;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private String ghiChu;
	private TrangThaiCaLam trangThaiCaLam;
	private String maCaLam;
	private String maNhanVien;

	public enum TrangThaiCaLam {
		danglam, daLam, chuaDenCa, khongLam;
	}

	public ChiTietCaLam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChiTietCaLam(Date ngayLamViec, LocalTime gioBatDau, LocalTime gioKetThuc, String ghiChu,
			TrangThaiCaLam trangThaiCaLam, String maCaLam, String maNhanVien) {
		super();
		this.ngayLamViec = ngayLamViec;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
		this.ghiChu = ghiChu;
		this.trangThaiCaLam = trangThaiCaLam;
		this.maCaLam = maCaLam;
		this.maNhanVien = maNhanVien;
	}

	public Date getNgayLamViec() {
		return ngayLamViec;
	}

	public void setNgayLamViec(Date ngayLamViec) {
		this.ngayLamViec = ngayLamViec;
	}

	public LocalTime getGioBatDau() {
		return gioBatDau;
	}

	public void setGioBatDau(LocalTime gioBatDau) {
		this.gioBatDau = gioBatDau;
	}

	public LocalTime getGioKetThuc() {
		return gioKetThuc;
	}

	public void setGioKetThuc(LocalTime gioKetThuc) {
		this.gioKetThuc = gioKetThuc;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public TrangThaiCaLam getTrangThaiCaLam() {
		return trangThaiCaLam;
	}

	public void setTrangThaiCaLam(TrangThaiCaLam trangThaiCaLam) {
		this.trangThaiCaLam = trangThaiCaLam;
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
		return Objects.hash(maCaLam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietCaLam other = (ChiTietCaLam) obj;
		return Objects.equals(maCaLam, other.maCaLam);
	}

	
}
