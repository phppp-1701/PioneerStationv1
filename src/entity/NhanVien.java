package entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
	private String maNhanVien;
	private String tenNhanVien;
	private String cccd_HoChieu;
	private String soDienThoai;
	private LocalDate ngaySinh;
	private ChucVu chucVu;
	private GioiTinh gioiTinh;
	private String urlAnh;
	private TrangThaiNhanVien trangThaiNhanVien;
	private String email;

	public enum ChucVu {
		banVe, quanLy;

		@Override
		public String toString() {
			switch (this) {
			case banVe:
				return "Bán vé";
			case quanLy:
				return "Quản lý";
			default:
				return super.toString();
			}
		}
	}

	public enum GioiTinh {
		nam, nu;
		@Override
		public String toString() {
			switch (this) {
			case nam:
				return "Nam";
			case nu:
				return "Nữ";
			default:
				return super.toString();
			}
		}
	}

	public enum TrangThaiNhanVien {
		hoatDong, voHieuHoa;
		@Override
		public String toString() {
			switch (this) {
			case voHieuHoa:
				return "Vô hiệu hóa";
			case hoatDong:
				return "Hoạt động";
			default:
				return super.toString();
			}
		}
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	public GioiTinh getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinh gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getUrlAnh() {
		return urlAnh;
	}

	public void setUrlAnh(String urlAnh) {
		this.urlAnh = urlAnh;
	}

	public TrangThaiNhanVien getTrangThaiNhanVien() {
		return trangThaiNhanVien;
	}

	public void setTrangThaiNhanVien(TrangThaiNhanVien trangThaiNhanVien) {
		this.trangThaiNhanVien = trangThaiNhanVien;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NhanVien() {

	}

	public NhanVien(String maNhanVien, String tenNhanVien, String cccd_HoChieu, String soDienThoai, LocalDate ngaySinh,
			ChucVu chucVu, GioiTinh gioiTinh, String urlAnh, TrangThaiNhanVien trangThaiNhanVien, String email) {
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.cccd_HoChieu = cccd_HoChieu;
		this.soDienThoai = soDienThoai;
		this.ngaySinh = ngaySinh;
		this.chucVu = chucVu;
		this.gioiTinh = gioiTinh;
		this.urlAnh = urlAnh;
		this.trangThaiNhanVien = trangThaiNhanVien;
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}

	
}
