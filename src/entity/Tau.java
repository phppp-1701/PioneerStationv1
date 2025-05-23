package entity;

import java.util.Objects;

public class Tau {
	private String maTau;
	private String tenTau;
	public enum TrangThaiTau{
		hoatDong, khongHoatDong;
	}
	private TrangThaiTau trangThaiTau;
	public enum LoaiTau{
		tauChatLuong, tauThongNhat, tauDiaPhuong;
	}
	private LoaiTau loaiTau;
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
	public LoaiTau getLoaiTau() {
		return loaiTau;
	}
	public void setLoaiTau(LoaiTau loaiTau) {
		this.loaiTau = loaiTau;
	}
	public Tau() {
		
	}
	public Tau(String maTau, String tenTau, TrangThaiTau trangThaiTau, LoaiTau loaiTau) {
		this.maTau = maTau;
		this.tenTau = tenTau;
		this.trangThaiTau = trangThaiTau;
		this.loaiTau = loaiTau;
	}
	@Override
	public int hashCode() {
		return Objects.hash(loaiTau, maTau, tenTau, trangThaiTau);
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
		return loaiTau == other.loaiTau && Objects.equals(maTau, other.maTau) && Objects.equals(tenTau, other.tenTau)
				&& trangThaiTau == other.trangThaiTau;
	}
}
