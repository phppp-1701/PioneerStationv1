package entity;

public class ChiTietCho {
	private Cho cho;
	private ChuyenTau chuyenTau;
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
	public ChiTietCho(Cho cho, ChuyenTau chuyenTau, TrangThaiCho trangThaiCho, Double giaCho) {
		super();
		this.cho = cho;
		this.chuyenTau = chuyenTau;
		this.trangThaiCho = trangThaiCho;
		this.giaCho = giaCho;
	}
}
