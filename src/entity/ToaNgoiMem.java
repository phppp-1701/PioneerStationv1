package entity;

import java.util.Objects;

public class ToaNgoiMem {
	private String maToaTau;
    private int soLuongGhe;
	public ToaNgoiMem(String maToaTau, int soLuongGhe) {
		super();
		this.maToaTau = maToaTau;
		this.soLuongGhe = soLuongGhe;
	}
	public ToaNgoiMem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaToaTau() {
		return maToaTau;
	}
	public void setMaToaTau(String maToaTau) {
		this.maToaTau = maToaTau;
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
		ToaNgoiMem other = (ToaNgoiMem) obj;
		return Objects.equals(maToaTau, other.maToaTau);
	}
	
    
}
