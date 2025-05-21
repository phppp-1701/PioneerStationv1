package entity;

import java.util.Objects;

public class Cho {
	private String maCho;
	private int soThuTuCho;
	private String maToaTau;
	
	public Cho() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cho(String maCho, int soThuTuCho, String maToaTau) {
		super();
		this.maCho = maCho;
		this.soThuTuCho = soThuTuCho;
		this.maToaTau = maToaTau;
	}
	public String getMaCho() {
		return maCho;
	}
	public void setMaCho(String maCho) {
		this.maCho = maCho;
	}
	public int getSoThuTuCho() {
		return soThuTuCho;
	}
	public void setSoThuTuCho(int soThuTuCho) {
		this.soThuTuCho = soThuTuCho;
	}
	public String getMaToaTau() {
		return maToaTau;
	}
	public void setMaToaTau(String maToaTau) {
		this.maToaTau = maToaTau;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maCho, maToaTau, soThuTuCho);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cho other = (Cho) obj;
		return Objects.equals(maCho, other.maCho) && Objects.equals(maToaTau, other.maToaTau)
				&& soThuTuCho == other.soThuTuCho;
	}
	
}
