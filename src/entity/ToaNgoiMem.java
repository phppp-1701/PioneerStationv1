package entity;

import java.util.Objects;

public class ToaNgoiMem extends ToaTau{
    private int soLuongGhe;

	public int getSoLuongGhe() {
		return soLuongGhe;
	}

	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}

	public ToaNgoiMem() {
		super();
	}

	public ToaNgoiMem(String maToaTau, int thuTuToa, int soLuongChoDaBan, int soLuongChoDangDat, int soLuongChoConTrong,
			String maTau) {
		super(maToaTau, thuTuToa, soLuongChoDaBan, soLuongChoDangDat, soLuongChoConTrong, maTau);
		this.soLuongGhe = soLuongGhe;
	}
	
    
}
