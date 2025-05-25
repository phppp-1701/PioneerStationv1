package entity;

import java.util.Objects;

public class TuyenTau {
	private String maTuyenTau;
	private String tenTuyenTau;
	private double khoangCach;
	private Ga gaDi;
	private Ga gaDen;

	public TuyenTau() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TuyenTau(String maTuyenTau, String tenTuyenTau, double khoangCach, Ga gaDi, Ga gaDen) {
		super();
		this.maTuyenTau = maTuyenTau;
		this.tenTuyenTau = tenTuyenTau;
		this.khoangCach = khoangCach;
		this.gaDi = gaDi;
		this.gaDen = gaDen;
	}

	public String getMaTuyenTau() {
		return maTuyenTau;
	}

	public void setMaTuyenTau(String maTuyenTau) {
		this.maTuyenTau = maTuyenTau;
	}

	public String getTenTuyenTau() {
		return tenTuyenTau;
	}

	public void setTenTuyenTau(String tenTuyenTau) {
		this.tenTuyenTau = tenTuyenTau;
	}

	public double getKhoangCach() {
		return khoangCach;
	}

	public void setKhoangCach(double khoangCach) {
		this.khoangCach = khoangCach;
	}

	public Ga getGaDi() {
		return gaDi;
	}

	public void setGaDi(Ga gaDi) {
		this.gaDi = gaDi;
	}

	public Ga getGaDen() {
		return gaDen;
	}

	public void setGaDen(Ga gaDen) {
		this.gaDen = gaDen;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maTuyenTau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TuyenTau other = (TuyenTau) obj;
		return Objects.equals(maTuyenTau, other.maTuyenTau);
	}

	

}
