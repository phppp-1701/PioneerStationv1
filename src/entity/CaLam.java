package entity;

import java.time.LocalTime;
import java.util.Objects;

public class CaLam {
	private String maCaLam;
	private LocalTime gioBatDau;
	private LocalTime gioKetThuc;
	private Ngay ngay;

	public enum Ngay {
		thuHai, thuBa, thuTu, thuNam, thuSau, thuBay, chuNhat;
	}
	public CaLam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CaLam(String maCaLam, LocalTime gioBatDau, LocalTime gioKetThuc, Ngay ngay) {
		super();
		this.maCaLam = maCaLam;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
		this.ngay = ngay;
	}
	public String getMaCaLam() {
		return maCaLam;
	}
	public void setMaCaLam(String maCaLam) {
		this.maCaLam = maCaLam;
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
	public Ngay getNgay() {
		return ngay;
	}
	public void setNgay(Ngay ngay) {
		this.ngay = ngay;
	}
	@Override
	public int hashCode() {
		return Objects.hash(gioBatDau, gioKetThuc, maCaLam, ngay);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaLam other = (CaLam) obj;
		return Objects.equals(gioBatDau, other.gioBatDau) && Objects.equals(gioKetThuc, other.gioKetThuc)
				&& Objects.equals(maCaLam, other.maCaLam) && ngay == other.ngay;
	}
	
}
