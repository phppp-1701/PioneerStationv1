package entity;

import java.util.Objects;

public class ChiTietCho {
	private String maCho;
	private String maChuyenTau;
	private String maVe;
	private TrangThaiCho trangThaiCho;
	private Double giaCho;

	public enum TrangThaiCho {
		daBan, dangDat, conTrong;
	}

	public String getMaCho() {
		return maCho;
	}

	public void setMaCho(String maCho) {
		this.maCho = maCho;
	}

	public String getMaChuyenTau() {
		return maChuyenTau;
	}

	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}

	public String getMaVe() {
		return maVe;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}

	public TrangThaiCho getTrangThaiCho() {
		return trangThaiCho;
	}

	public void setTrangThaiCho(TrangThaiCho trangThaiCho) {
		this.trangThaiCho = trangThaiCho;
	}

	public Double getGiaCho() {
		return giaCho;
	}

	public void setGiaCho(Double giaCho) {
		this.giaCho = giaCho;
	}

	public ChiTietCho(String maCho, String maChuyenTau, String maVe, TrangThaiCho trangThaiCho, Double giaCho) {
		super();
		this.maCho = maCho;
		this.maChuyenTau = maChuyenTau;
		this.maVe = maVe;
		this.trangThaiCho = trangThaiCho;
		this.giaCho = giaCho;
	}

	public ChiTietCho() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ChiTietCho [maCho=" + maCho + ", maChuyenTau=" + maChuyenTau + ", maVe=" + maVe + ", trangThaiCho="
				+ trangThaiCho + ", giaCho=" + giaCho + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maCho, maChuyenTau, trangThaiCho);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietCho other = (ChiTietCho) obj;
		return Objects.equals(maCho, other.maCho) && Objects.equals(maChuyenTau, other.maChuyenTau)
				&& trangThaiCho == other.trangThaiCho;
	}

	
	

}
