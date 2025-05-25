package entity;

import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String tenKhachHang;
	private String cccd_HoChieu;
	private String soDienThoai;
	private LoaiKhachHang loaiKhachHang;
	private TrangThaiKhachHang trangThaiKhachHang;
	private String email;

	public enum LoaiKhachHang {
		thanThiet, vip, vangLai;
	}

	public enum TrangThaiKhachHang {
		hoatDong, voHieuHoa;
	}
	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KhachHang(String maKhachHang, String tenKhachHang, String cccdHoChieu, String soDienThoai, 
            String email, LoaiKhachHang loaiKhachHang, TrangThaiKhachHang trangThaiKhachHang) {
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.cccd_HoChieu = cccdHoChieu;
		this.soDienThoai = soDienThoai;
		this.email = email;
		this.loaiKhachHang = loaiKhachHang;
		this.trangThaiKhachHang = trangThaiKhachHang;
}

	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
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
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public LoaiKhachHang getLoaiKhachHang() {
		return loaiKhachHang;
	}
	public void setLoaiKhachHang(LoaiKhachHang loaiKhachHang) {
		this.loaiKhachHang = loaiKhachHang;
	}
	public TrangThaiKhachHang getTrangThaiKhachHang() {
		return trangThaiKhachHang;
	}
	public void setTrangThaiKhachHang(TrangThaiKhachHang trangThaiKhachHang) {
		this.trangThaiKhachHang = trangThaiKhachHang;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}
	
	
}