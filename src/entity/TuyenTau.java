package entity;

import java.util.Objects;

public class TuyenTau {
	private String maTuyenTau;
	private String tenTuyenTau;
	private double khoangCach;
	private String gaDi;
	private String gaDen;

	public TuyenTau() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TuyenTau(String maTuyenTau, String tenTuyenTau, double khoangCach, String gaDi, String gaDen) {
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

	public String getGaDi() {
		return gaDi;
	}

	public void setGaDi(String gaDi) {
		this.gaDi = gaDi;
	}

	public String getGaDen() {
		return gaDen;
	}

	public void setGaDen(String gaDen) {
		this.gaDen = gaDen;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gaDen, gaDi, khoangCach, maTuyenTau, tenTuyenTau);
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
		return Objects.equals(gaDen, other.gaDen) && Objects.equals(gaDi, other.gaDi)
				&& Objects.equals(khoangCach, other.khoangCach) && Objects.equals(maTuyenTau, other.maTuyenTau)
				&& Objects.equals(tenTuyenTau, other.tenTuyenTau);
	}

}
