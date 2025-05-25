package entity;

import java.util.Objects;

public class Cho {
	private String maCho;
	private int soThuTuCho;
	private ToaTau toaTau;
	
	public Cho() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cho(String maCho, int soThuTuCho, ToaTau toaTau) {
		super();
		this.maCho = maCho;
		this.soThuTuCho = soThuTuCho;
		this.toaTau = toaTau;
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
	public ToaTau getToaTau() {
		return toaTau;
	}
	public void setToaTau(ToaTau toaTau) {
		this.toaTau = toaTau;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maCho);
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
		return Objects.equals(maCho, other.maCho);
	}
	
	
}
