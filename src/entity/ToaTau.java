package entity;

import java.util.Objects;

public class ToaTau {
	private String maToaTau;
    private int thuTuToa;
    private int soLuongChoDaBan;
    private int soLuongChoDangDat;
    private int soLuongChoConTrong;
    private String maTau;
	public ToaTau() {
		
	}
	public ToaTau(String maToaTau, int thuTuToa, int soLuongChoDaBan, int soLuongChoDangDat, int soLuongChoConTrong,
			String maTau) {
		super();
		this.maToaTau = maToaTau;
		this.thuTuToa = thuTuToa;
		this.soLuongChoDaBan = soLuongChoDaBan;
		this.soLuongChoDangDat = soLuongChoDangDat;
		this.soLuongChoConTrong = soLuongChoConTrong;
		this.maTau = maTau;
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
	public int getSoLuongChoDaBan() {
		return soLuongChoDaBan;
	}
	public void setSoLuongChoDaBan(int soLuongChoDaBan) {
		this.soLuongChoDaBan = soLuongChoDaBan;
	}
	public int getSoLuongChoDangDat() {
		return soLuongChoDangDat;
	}
	public void setSoLuongChoDangDat(int soLuongChoDangDat) {
		this.soLuongChoDangDat = soLuongChoDangDat;
	}
	public int getSoLuongChoConTrong() {
		return soLuongChoConTrong;
	}
	public void setSoLuongChoConTrong(int soLuongChoConTrong) {
		this.soLuongChoConTrong = soLuongChoConTrong;
	}
	public String getMaTau() {
		return maTau;
	}
	public void setMaTau(String maTau) {
		this.maTau = maTau;
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
