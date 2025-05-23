package entity;

import java.util.Objects;

public class ToaGiuongNam extends ToaTau{

    private int soHieuKhoang;
    private int soHieuTang;
    private int soLuongGiuong;
    
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
	public ToaGiuongNam() {
		super();
		
	}
	public ToaGiuongNam(String maToaTau, int thuTuToa, int soLuongChoDaBan, int soLuongChoDangDat,
			int soLuongChoConTrong, String maTau) {
		super(maToaTau, thuTuToa, soLuongChoDaBan, soLuongChoDangDat, soLuongChoConTrong, maTau);
		
		this.soHieuKhoang = soHieuKhoang;
		this.soHieuTang = soHieuTang;
		this.soLuongGiuong = soLuongGiuong;
	}
	
	
}
