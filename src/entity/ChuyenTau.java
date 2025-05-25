package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ChuyenTau {
	private String maChuyenTau;
	private LocalDate ngayKhoiHanh;
	private LocalTime gioKhoiHanh;
	private LocalDate ngayDuKien;
	private LocalTime gioDuKien;
	private TrangThaiChuyenTau trangThaiChuyenTau;
	private Tau tau;
	private TuyenTau tuyenTau;
	public enum TrangThaiChuyenTau {
		hoatDong, khongHoatDong;
	}
	
	public ChuyenTau() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChuyenTau(String maChuyenTau, LocalDate ngayKhoiHanh, LocalTime gioKhoiHanh, LocalDate ngayDuKien,
			LocalTime gioDuKien, TrangThaiChuyenTau trangThaiChuyenTau, Tau tau, TuyenTau tuyenTau) {
		super();
		this.maChuyenTau = maChuyenTau;
		this.ngayKhoiHanh = ngayKhoiHanh;
		this.gioKhoiHanh = gioKhoiHanh;
		this.ngayDuKien = ngayDuKien;
		this.gioDuKien = gioDuKien;
		this.trangThaiChuyenTau = trangThaiChuyenTau;
		this.tau = tau;
		this.tuyenTau = tuyenTau;
	}
	public String getMaChuyenTau() {
		return maChuyenTau;
	}
	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}
	public LocalDate getNgayKhoiHanh() {
		return ngayKhoiHanh;
	}
	public void setNgayKhoiHanh(LocalDate ngayKhoiHanh) {
		this.ngayKhoiHanh = ngayKhoiHanh;
	}
	public LocalTime getGioKhoiHanh() {
		return gioKhoiHanh;
	}
	public void setGioKhoiHanh(LocalTime gioKhoiHanh) {
		this.gioKhoiHanh = gioKhoiHanh;
	}
	public LocalDate getNgayDuKien() {
		return ngayDuKien;
	}
	public void setNgayDuKien(LocalDate ngayDuKien) {
		this.ngayDuKien = ngayDuKien;
	}
	public LocalTime getGioDuKien() {
		return gioDuKien;
	}
	public void setGioDuKien(LocalTime gioDuKien) {
		this.gioDuKien = gioDuKien;
	}
	public TrangThaiChuyenTau getTrangThaiChuyenTau() {
		return trangThaiChuyenTau;
	}
	public void setTrangThaiChuyenTau(TrangThaiChuyenTau trangThaiChuyenTau) {
		this.trangThaiChuyenTau = trangThaiChuyenTau;
	}
	
	public Tau getTau() {
		return tau;
	}
	public void setTau(Tau tau) {
		this.tau = tau;
	}
	public TuyenTau getTuyenTau() {
		return tuyenTau;
	}
	public void setTuyenTau(TuyenTau tuyenTau) {
		this.tuyenTau = tuyenTau;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maChuyenTau);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChuyenTau other = (ChuyenTau) obj;
		return Objects.equals(maChuyenTau, other.maChuyenTau);
	}
	
	
}
