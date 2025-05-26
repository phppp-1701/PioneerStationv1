package entity;

import entity.Tau.LoaiTau;
import entity.ToaTau.LoaiToa;

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
	}

	public ChiTietCho(Cho cho, ChuyenTau chuyenTau, TrangThaiCho trangThaiCho) {
		super();
		this.cho = cho;
		this.chuyenTau = chuyenTau;
		this.trangThaiCho = trangThaiCho;
		giaCho = tinhGiaCho();
	}
	
	public double tinhGiaCho() {
	    // Kiểm tra null cho các đối tượng liên quan
	    if (cho == null || cho.getToaTau() == null || cho.getToaTau().getTau() == null) {
	        throw new IllegalArgumentException("Thông tin tàu hoặc toa tàu không hợp lệ");
	    }

	    Tau tau = cho.getToaTau().getTau();
	    LoaiTau loaiTau = tau.getLoaiTau();
	    double khoangCach = chuyenTau.getTuyenTau().getKhoangCach();

	    if (loaiTau == LoaiTau.tauChatLuong) {
	        return (cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) ? 900 * khoangCach : 500 * khoangCach;
	    } else if (loaiTau.equals(LoaiTau.tauThongNhat)) {
	        return (cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) ? 700 * khoangCach : 400 * khoangCach;
	    } else if (loaiTau.equals(LoaiTau.tauDuLich)) {
	        return (cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) ? 1000 * khoangCach : 600 * khoangCach;
	    } else {
	        return (cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) ? 500 * khoangCach : 300 * khoangCach;
	    }
	}

	public void setToa(ToaTau toa) {
		// TODO Auto-generated method stub
		
	}
	
//	public double tinhGiaCho() {
//		if(cho.getToaTau().getTau().getLoaiTau().equals(LoaiTau.tauChatLuong)) {
//			if(cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) {
//				return 900*chuyenTau.getTuyenTau().getKhoangCach();
//			}else {
//				return 500*chuyenTau.getTuyenTau().getKhoangCach();
//			}
//		}else if(cho.getToaTau().getTau().getLoaiTau().equals(LoaiTau.tauThongNhat)) {
//			if(cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) {
//				return 700*chuyenTau.getTuyenTau().getKhoangCach();
//			}else {
//				return 400*chuyenTau.getTuyenTau().getKhoangCach();
//			}
//		}else if(cho.getToaTau().getTau().getLoaiTau().equals(LoaiTau.tauDuLich)) {
//			if(cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) {
//				return 1000*chuyenTau.getTuyenTau().getKhoangCach();
//			}else {
//				return 600*chuyenTau.getTuyenTau().getKhoangCach();
//			}
//		}else {
//			if(cho.getToaTau().getLoaiToa().equals(LoaiToa.giuongNam)) {
//				return 500*chuyenTau.getTuyenTau().getKhoangCach();
//			}else {
//				return 300*chuyenTau.getTuyenTau().getKhoangCach();
//			}
//		}
//	}
}