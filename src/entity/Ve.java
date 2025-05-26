package entity;

import java.time.LocalDate;
import java.util.*;

import dao.ChiTietCho_DAO;

public class Ve {
    private String maVe;
    private LocalDate ngayTaoVe;
    private TrangThaiVe trangThaiVe;
    private String tenKhachHang;
    private String cccd_HoChieu;
    private LocalDate ngaySinh;
    private LoaiVe loaiVe;
    private double giaVe;
    public double phanTramGiamGiaCoDinh;
    private HoaDon hoaDon;
    private Cho cho;
    private ChuyenTau chuyenTau;

    public enum TrangThaiVe {
        hieuLuc, daHuy, daDoi, hetHan;
    	@Override
    	public String toString() {
    		switch (this) {
			case hieuLuc: {
				return "hiệu lực";
			}
			case daHuy:{
				return "đã hủy";
			}
			case daDoi:{
				return "đã đổi";
			}
			case hetHan:{
				return "hết hạn";
			}
			default:
				return super.toString();
			}
    	}
    }

    public enum LoaiVe {
        treEm, nguoiLon, sinhVien, nguoiCaoTuoi;

        @Override
        public String toString() {
            switch (this) { // 'this' refers to the current enum constant (e.g., treEm, nguoiLon)
                case treEm:
                    return "Trẻ em";
                case nguoiLon:
                    return "Người lớn";
                case sinhVien:
                    return "Sinh viên";
                case nguoiCaoTuoi:
                    return "Người cao tuổi";
                default:
                    return super.toString(); // Fallback in case a new constant is added without updating this switch
            }
        }
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public LocalDate getNgayTaoVe() {
        return ngayTaoVe;
    }

    public void setNgayTaoVe(LocalDate ngayTaoVe) {
        this.ngayTaoVe = ngayTaoVe;
    }

    public TrangThaiVe getTrangThaiVe() {
        return trangThaiVe;
    }

    public void setTrangThaiVe(TrangThaiVe trangThaiVe) {
        this.trangThaiVe = trangThaiVe;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getCccd_HoChieu() {
        return cccd_HoChieu;
    }

    public void setCccd_HoChieu(String cccd_HoChieu) {
        this.cccd_HoChieu = cccd_HoChieu;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LoaiVe getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(LoaiVe loaiVe) {
        this.loaiVe = loaiVe;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
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

    
    public double getPhanTramGiamGiaCoDinh() {
		return phanTramGiamGiaCoDinh;
	}

	public void setPhanTramGiamGiaCoDinh(double phanTramGiamGiaCoDinh) {
		this.phanTramGiamGiaCoDinh = phanTramGiamGiaCoDinh;
	}

	public Ve() {
    }
    
    public Ve(String maVe, LocalDate ngayTaoVe, TrangThaiVe trangThaiVe, String tenKhachHang, String cccd_HoChieu,
			LocalDate ngaySinh, LoaiVe loaiVe, HoaDon hoaDon, Cho cho, ChuyenTau chuyenTau) {
		this.maVe = maVe;
		this.ngayTaoVe = ngayTaoVe;
		this.trangThaiVe = trangThaiVe;
		this.tenKhachHang = tenKhachHang;
		this.cccd_HoChieu = cccd_HoChieu;
		this.ngaySinh = ngaySinh;
		this.loaiVe = loaiVe;
		//giá vé
		phanTramGiamGiaCoDinh = tinhPhanTramGiamGia();
		giaVe = tinhGiaVe();
		this.hoaDon = hoaDon;
		this.cho = cho;
		this.chuyenTau = chuyenTau;
	}
    public double tinhPhanTramGiamGia() {
    	if(loaiVe.equals(LoaiVe.treEm)){
    		return 0.25;
    	}
    	if(loaiVe.equals(LoaiVe.sinhVien)) {
    		return 0.10;
    	}
    	if(loaiVe.equals(LoaiVe.nguoiCaoTuoi)) {
    		return 0.15;
    	}
    	return 0;
    }
    
    public double tinhGiaVe() {
    	ChiTietCho_DAO chiTietCho_DAO = new ChiTietCho_DAO();
		ChiTietCho chiTietCho = chiTietCho_DAO.timChiTietChoTheoChoVaChuyenTau(cho, chuyenTau, true);
    	return chiTietCho.tinhGiaCho() * (1 - phanTramGiamGiaCoDinh);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(maVe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ve other = (Ve) obj;
        return Objects.equals(maVe, other.maVe);
    }

    @Override
    public String toString() {
        return "Ve{" +
               "maVe='" + maVe + '\'' +
               ", ngayTaoVe=" + ngayTaoVe +
               ", trangThaiVe=" + trangThaiVe +
               ", tenKhachHang='" + tenKhachHang + '\'' +
               ", cccd_HoChieu='" + cccd_HoChieu + '\'' +
               ", ngaySinh=" + ngaySinh +
               ", loaiVe=" + loaiVe +
               ", giaVe=" + giaVe +
               ", phanTramGiamGiaCoDinh=" + phanTramGiamGiaCoDinh +
               ", maHoaDon=" + (hoaDon != null ? hoaDon.getMaHoaDon() : null) +
               ", maCho=" + (cho != null ? cho.getMaCho() : null) +
               ", maChuyenTau=" + (chuyenTau != null ? chuyenTau.getMaChuyenTau() : null) +
               '}';
    }
}