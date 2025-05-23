package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	private TrangThaiTau trangThaiTau;

	public enum TrangThaiTau {
		hoatDong, khongHoatDong;
	}

	public Tau() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tau(String maTau, String tenTau, TrangThaiTau trangThaiTau) {
		super();
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.trangThaiTau = trangThaiTau;
	}

	public String getMaTau() {
		return maTau;
	}

	public void setMaTau(String maTau) {
		this.maTau = maTau;
	}

	public String getTenTau() {
		return tenTau;
	}

	public void setTenTau(String tenTau) {
		this.tenTau = tenTau;
	}

	public TrangThaiTau getTrangThaiTau() {
		return trangThaiTau;
	}

	public void setTrangThaiTau(TrangThaiTau trangThaiTau) {
		this.trangThaiTau = trangThaiTau;
	}

	@Override
	public String toString() {
		return "Tau [maTau=" + maTau + ", tenTau=" + tenTau + ", trangThaiTau=" + trangThaiTau + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tau other = (Tau) obj;
		return Objects.equals(maTau, other.maTau);
	}

	
	
}
