package entity;

import java.util.Objects;

public class ToaGiuongNam {
	private String maToaTau;

    private int soHieuKhoang;
    private int soHieuTang;
    private int soLuongGiuong;
	public ToaGiuongNam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ToaGiuongNam(String maToaTau, int soHieuKhoang, int soHieuTang, int soLuongGiuong) {
		super();
		this.maToaTau = maToaTau;
		this.soHieuKhoang = soHieuKhoang;
		this.soHieuTang = soHieuTang;
		this.soLuongGiuong = soLuongGiuong;
	}
	public String getMaToaTau() {
		return maToaTau;
	}
	public void setMaToaTau(String maToaTau) {
		this.maToaTau = maToaTau;
	}
	public int getSoHieuKhoang() {
		return soHieuKhoang;
	}
	public void setSoHieuKhoang(int soHieuKhoang) {
		this.soHieuKhoang = soHieuKhoang;
	}
	public int getSoHieuTang() {
		return soHieuTang;
	}
	public void setSoHieuTang(int soHieuTang) {
		this.soHieuTang = soHieuTang;
	}
	public int getSoLuongGiuong() {
		return soLuongGiuong;
	}
	public void setSoLuongGiuong(int soLuongGiuong) {
		this.soLuongGiuong = soLuongGiuong;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maToaTau);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToaGiuongNam other = (ToaGiuongNam) obj;
		return Objects.equals(maToaTau, other.maToaTau);
	}
	
    
}
