package entity;

import java.util.Objects;

public class ChiTietCho {
	private Cho cho;
	private ChuyenTau chuyenTau;
	private Ve ve;
	private TrangThaiCho trangThaiCho;
	private Double giaCho;

	public enum TrangThaiCho {
		daBan, dangDat, conTrong;
	}

	public Cho getCho() {
		return cho;
	}

	public void setCho(Cho cho) {
		this.cho = cho;
	}

	public ChuyenTau getChuyenTau() {
		return chuyenTau;
	}

	public void setChuyenTau(ChuyenTau chuyenTau) {
		this.chuyenTau = chuyenTau;
	}

	public Ve getVe() {
		return ve;
	}

	public void setVe(Ve ve) {
		this.ve = ve;
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

	public ChiTietCho() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ChiTietCho(Cho cho, ChuyenTau chuyenTau, Ve ve, TrangThaiCho trangThaiCho, Double giaCho) {
		super();
		this.cho = cho;
		this.chuyenTau = chuyenTau;
		this.ve = ve;
		this.trangThaiCho = trangThaiCho;
		this.giaCho = giaCho;
	}

	@Override
	public String toString() {
		return "ChiTietCho [maCho=" + cho + ", maChuyenTau=" + chuyenTau + ", maVe=" + ve + ", trangThaiCho="
				+ trangThaiCho + ", giaCho=" + giaCho + "]";
	}


	

	
	

}
