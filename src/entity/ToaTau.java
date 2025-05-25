package entity;

import java.util.Objects;

public class ToaTau {
	private String maToaTau;
    private int thuTuToa;
    private Tau tau;
    private LoaiToa loaiToa;
    private int soHieuKhoang;
    private int soHieuTang;
    private int soLuongGiuong;
    private int soLuongGhe;
    
    public enum LoaiToa {
		giuongNam, ngoiMem;
	}
    
	public ToaTau() {
		
	}
	
	public ToaTau(String maToaTau, int thuTuToa, Tau tau, LoaiToa loaiToa, int soHieuKhoang,
			int soHieuTang, int soLuongGiuong, int soLuongGhe) {
		super();
		this.maToaTau = maToaTau;
		this.thuTuToa = thuTuToa;
		this.tau = tau;
		this.loaiToa = loaiToa;
		this.soHieuKhoang = soHieuKhoang;
		this.soHieuTang = soHieuTang;
		this.soLuongGiuong = soLuongGiuong;
		this.soLuongGhe = soLuongGhe;
	}

	public String getMaToaTau() {
		return maToaTau;
	}

	public void setMaToaTau(String maToaTau) {
		this.maToaTau = maToaTau;
	}

	public int getThuTuToa() {
		return thuTuToa;
	}

	public void setThuTuToa(int thuTuToa) {
		this.thuTuToa = thuTuToa;
	}

	public Tau getTau() {
		return tau;
	}

	public void setTau(Tau tau) {
		this.tau = tau;
	}

	public LoaiToa getLoaiToa() {
		return loaiToa;
	}

	public void setLoaiToa(LoaiToa loaiToa) {
		this.loaiToa = loaiToa;
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

	public int getSoLuongGhe() {
		return soLuongGhe;
	}

	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
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
		ToaTau other = (ToaTau) obj;
		return Objects.equals(maToaTau, other.maToaTau);
	}
	
}
