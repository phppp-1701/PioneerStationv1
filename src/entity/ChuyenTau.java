package entity;

import java.time.LocalTime;
import java.util.*;

public class ChuyenTau {
	private String maChuyenTau;
	private Date ngayKhoiHanh;
	private LocalTime gioKhoiHanh;
	private Date ngayDuKien;
	private LocalTime gioDuKien;
	private TrangThaiChuyenTau trangThaiChuyenTau;
	private String maTau;
	private String maTuyenTau;
	public enum TrangThaiChuyenTau {
		hoatDong, khongHoatDong;
	}
	
	public ChuyenTau() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ChuyenTau(String maChuyenTau, Date ngayKhoiHanh, LocalTime gioKhoiHanh, Date ngayDuKien, LocalTime gioDuKien,
			TrangThaiChuyenTau trangThaiChuyenTau, String maTau, String maTuyenTau) {
		super();
		this.maChuyenTau = maChuyenTau;
		this.ngayKhoiHanh = ngayKhoiHanh;
		this.gioKhoiHanh = gioKhoiHanh;
		this.ngayDuKien = ngayDuKien;
		this.gioDuKien = gioDuKien;
		this.trangThaiChuyenTau = trangThaiChuyenTau;
		this.maTau = maTau;
		this.maTuyenTau = maTuyenTau;
	}

	public String getMaChuyenTau() {
		return maChuyenTau;
	}
	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}
	public Date getNgayKhoiHanh() {
		return ngayKhoiHanh;
	}
	public void setNgayKhoiHanh(Date ngayKhoiHanh) {
		this.ngayKhoiHanh = ngayKhoiHanh;
	}
	public LocalTime getGioKhoiHanh() {
		return gioKhoiHanh;
	}
	public void setGioKhoiHanh(LocalTime gioKhoiHanh) {
		this.gioKhoiHanh = gioKhoiHanh;
	}
	public Date getNgayDuKien() {
		return ngayDuKien;
	}
	public void setNgayDuKien(Date ngayDuKien) {
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
	public String getMaTau() {
		return maTau;
	}
	public void setMaTau(String maTau) {
		this.maTau = maTau;
	}
	public String getMaTuyenTau() {
		return maTuyenTau;
	}
	public void setMaTuyenTau(String maTuyenTau) {
		this.maTuyenTau = maTuyenTau;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gioDuKien, gioKhoiHanh, maChuyenTau, maTau, maTuyenTau, ngayDuKien, ngayKhoiHanh,
				trangThaiChuyenTau);
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
		return Objects.equals(gioDuKien, other.gioDuKien) && Objects.equals(gioKhoiHanh, other.gioKhoiHanh)
				&& Objects.equals(maChuyenTau, other.maChuyenTau) && Objects.equals(maTau, other.maTau)
				&& Objects.equals(maTuyenTau, other.maTuyenTau) && Objects.equals(ngayDuKien, other.ngayDuKien)
				&& Objects.equals(ngayKhoiHanh, other.ngayKhoiHanh) && trangThaiChuyenTau == other.trangThaiChuyenTau;
	}

	
}
