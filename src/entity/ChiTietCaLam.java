package entity;

import java.time.LocalTime;
import java.util.*;

public class ChiTietCaLam {
	private Date ngayLamViec;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private String ghiChu;
	private TrangThaiCaLam trangThaiCaLam;
	private CaLam caLam;
	private NhanVien nhanVien;

	public enum TrangThaiCaLam {
		danglam, daLam, chuaDenCa, khongLam;
	}

	public ChiTietCaLam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChiTietCaLam(Date ngayLamViec, LocalTime gioBatDau, LocalTime gioKetThuc, String ghiChu,
			TrangThaiCaLam trangThaiCaLam, CaLam caLam, NhanVien nhanVien) {
		super();
		this.ngayLamViec = ngayLamViec;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
		this.ghiChu = ghiChu;
		this.trangThaiCaLam = trangThaiCaLam;
		this.caLam = caLam;
		this.nhanVien = nhanVien;
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

	public CaLam getCaLam() {
		return caLam;
	}

	public void setCaLam(CaLam caLam) {
		this.caLam = caLam;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setMaNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	
}
