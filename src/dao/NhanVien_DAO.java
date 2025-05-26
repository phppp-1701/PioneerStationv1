package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;

public class NhanVien_DAO {
	public List<NhanVien> xuatToanBoDanhSachNhanVien(boolean dongKetNoi) {
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from NhanVien";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(resultSet.getString(1));
				nv.setTenNhanVien(resultSet.getString(2));
				nv.setCccd_HoChieu(resultSet.getString(3));
				nv.setSoDienThoai(resultSet.getString(4));
				Date ngaySinhDate = resultSet.getDate(5);
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				ChucVu chucVu = ChucVu.valueOf(resultSet.getString(6));
				GioiTinh gioiTinh = GioiTinh.valueOf(resultSet.getString(7));
				nv.setChucVu(chucVu);
				nv.setGioiTinh(gioiTinh);
				nv.setUrlAnh(resultSet.getString(8));
				TrangThaiNhanVien trangThaiNhanVien = TrangThaiNhanVien.valueOf(resultSet.getString(9));
				nv.setTrangThaiNhanVien(trangThaiNhanVien);
				nv.setEmail(resultSet.getString(10));
				dsnv.add(nv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsnv;
	}

	public NhanVien timNhanVienTheoMa(String maNV, boolean dongKetNoi) {
		NhanVien nv = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from NhanVien where maNhanVien = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maNV);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				nv = new NhanVien();
				nv.setMaNhanVien(resultSet.getString(1));
				nv.setTenNhanVien(resultSet.getString(2));
				nv.setCccd_HoChieu(resultSet.getString(3));
				nv.setSoDienThoai(resultSet.getString(4));
				Date ngaySinhDate = resultSet.getDate(5);
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				ChucVu chucVu = ChucVu.valueOf(resultSet.getString(6));
				GioiTinh gioiTinh = GioiTinh.valueOf(resultSet.getString(7));
				nv.setChucVu(chucVu);
				nv.setGioiTinh(gioiTinh);
				nv.setUrlAnh(resultSet.getString(8));
				TrangThaiNhanVien trangThaiNhanVien = TrangThaiNhanVien.valueOf(resultSet.getString(9));
				nv.setTrangThaiNhanVien(trangThaiNhanVien);
				nv.setEmail(resultSet.getString(10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(dongKetNoi){
			ConnectDB.getInstance().disconnect();
		}
		return nv;
	}

	// Tim nhan vien theo ten
	public List<NhanVien> timNhanVienTheoTen(String tenNhanVien, boolean dongKetNoi) {
		List<NhanVien> dsNhanVien = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + tenNhanVien + "%"); // Tìm kiếm gần đúng
			rs = stmt.executeQuery();

			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(rs.getString("maNhanVien"));
				nv.setTenNhanVien(rs.getString("tenNhanVien"));
				Date ngaySinhDate = rs.getDate("ngaySinh");
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				// Xử lý enum GioiTinh
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("cccd_HoChieu"));

				// Xử lý enum ChucVu
				String chucVu = rs.getString("chucVu");
				if ("quanLy".equals(chucVu)) {
					nv.setChucVu(ChucVu.quanLy);
				} else {
					nv.setChucVu(ChucVu.banVe);
				}

				String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
				if ("hoatDong".equals(trangThaiNhanVien)) {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
				} else {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
				}
				nv.setUrlAnh(rs.getString("urlAnh"));
				dsNhanVien.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}

		return dsNhanVien;
	}

	/// Tim theo so dien thoai
	public List<NhanVien> timNhanVienTheoSoDienThoai(String soDienThoai, boolean dongKetNoi) {
		List<NhanVien> dsNhanVien = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien WHERE soDienThoai LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + soDienThoai + "%");
			rs = stmt.executeQuery();

			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(rs.getString("maNhanVien"));
				nv.setTenNhanVien(rs.getString("tenNhanVien"));
				Date ngaySinhDate = rs.getDate("ngaySinh");
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("cccd_HoChieu"));

				String chucVu = rs.getString("chucVu");
				if ("quanLy".equals(chucVu)) {
					nv.setChucVu(ChucVu.quanLy);
				} else {
					nv.setChucVu(ChucVu.banVe);
				}

				String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
				if ("hoatDong".equals(trangThaiNhanVien)) {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
				} else {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
				}

				// Thêm ánh xạ cho cột urlAnh
				nv.setUrlAnh(rs.getString("urlAnh"));

				dsNhanVien.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}

		return dsNhanVien;
	}

	public List<NhanVien> timNhanVienTheoTenVaSoDienThoai(String tenNhanVien, String soDienThoai, boolean dongKetNoi) {
		List<NhanVien> dsNhanVien = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien WHERE tenNhanVien LIKE ? AND soDienThoai LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + tenNhanVien + "%");
			stmt.setString(2, "%" + soDienThoai + "%");
			rs = stmt.executeQuery();

			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(rs.getString("maNhanVien"));
				nv.setTenNhanVien(rs.getString("tenNhanVien"));
				Date ngaySinhDate = rs.getDate("ngaySinh");
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				// Xử lý enum GioiTinh
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("cccd_HoChieu"));

				// Xử lý enum ChucVu
				String chucVu = rs.getString("chucVu");
				if ("quanLy".equals(chucVu)) {
					nv.setChucVu(ChucVu.quanLy);
				} else {
					nv.setChucVu(ChucVu.banVe);
				}

				String trangThaiNhanVien = rs.getString("trangThaiNhanVien");
				if ("hoatDong".equals(trangThaiNhanVien)) {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.hoatDong);
				} else {
					nv.setTrangThaiNhanVien(TrangThaiNhanVien.voHieuHoa);
				}
				nv.setUrlAnh(rs.getString("urlAnh"));
				dsNhanVien.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		ConnectDB.getInstance().disconnect();
		return dsNhanVien;
	}

	// Thêm nhân viên mới
	public boolean themNhanVien(NhanVien nv) throws SQLException {
		return true;
	}

	// Cập nhật thông tin nhân viên
	public boolean capNhatNhanVien(NhanVien nv) {
		Connection con = null;
		PreparedStatement stmt = null;
		boolean thanhCong = false;

		try {
			con = ConnectDB.getConnection();
			String sql = "UPDATE NhanVien SET tenNhanVien = ?, ngaySinh = ?, soDienThoai = ?, email = ?, gioiTinh = ?, "
					+ "cccd_HoChieu = ?, chucVu = ?, trangThaiNhanVien = ?, urlAnh = ? WHERE maNhanVien = ?";

			stmt = con.prepareStatement(sql);
			stmt.setString(1, nv.getTenNhanVien());
			stmt.setDate(2, nv.getNgaySinh() != null ? Date.valueOf(nv.getNgaySinh()) : null);
			stmt.setString(3, nv.getSoDienThoai());
			stmt.setString(4, nv.getEmail());

			// Xử lý giới tính (phải là 'nam' hoặc 'nu')
			String gioiTinh = (nv.getGioiTinh() == NhanVien.GioiTinh.nam) ? "nam" : "nu";
			stmt.setString(5, gioiTinh);

			stmt.setString(6, nv.getCccd_HoChieu());

			// Xử lý chức vụ (phải là 'quanLy' hoặc 'banVe')
			String chucVu = (nv.getChucVu() == NhanVien.ChucVu.quanLy) ? "quanLy" : "banVe";
			stmt.setString(7, chucVu);

			// Xử lý trạng thái (phải là 'hoatDong' hoặc 'voHieuHoa')
			String trangThai = (nv.getTrangThaiNhanVien() == NhanVien.TrangThaiNhanVien.hoatDong) ? "hoatDong"
					: "voHieuHoa";
			stmt.setString(8, trangThai);

			// Xử lý link ảnh (có thể null)
			stmt.setString(9, nv.getUrlAnh());

			stmt.setString(10, nv.getMaNhanVien());

			int rowsAffected = stmt.executeUpdate();
			thanhCong = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}

		return thanhCong;
	}

	// xóa nhân viên
	public boolean xoaNhanVien(String maNhanVien) {
		Connection con = null;
		PreparedStatement stmt = null;
		boolean thanhCong = false;

		try {
			con = ConnectDB.getConnection();
			String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, maNhanVien);

			int rowsAffected = stmt.executeUpdate();
			thanhCong = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}

		return thanhCong;
	}

	public boolean kiemTraCCCD(String cccd) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) FROM NhanVien WHERE cccd_HoChieu = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cccd);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return false;
	}

	// tim nhan vien theo CCCD_HOCHIEU
	public List<NhanVien> timNhanVienTheoCCCD_HoChieu(String CCCD_HoChieu, boolean dongKetNoi) {
		List<NhanVien> dsNhanVien = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM NhanVien WHERE cccd_HoChieu LIKE ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, "%" + CCCD_HoChieu + "%"); // Tìm kiếm gần đúng
			rs = stmt.executeQuery();

			while (rs.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTenNhanVien(rs.getString(2));
				nv.setCccd_HoChieu(rs.getString(3));
				nv.setSoDienThoai(rs.getString(4));
				Date ngaySinhDate = rs.getDate(5);
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate() : null);
				ChucVu chucVu = ChucVu.valueOf(rs.getString(6));
				GioiTinh gioiTinh = GioiTinh.valueOf(rs.getString(7));
				nv.setChucVu(chucVu);
				nv.setGioiTinh(gioiTinh);
				nv.setUrlAnh(rs.getString(8));
				TrangThaiNhanVien trangThaiNhanVien = TrangThaiNhanVien.valueOf(rs.getString(9));
				nv.setTrangThaiNhanVien(trangThaiNhanVien);
				nv.setEmail(rs.getString(10));
				dsNhanVien.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsNhanVien;
	}

	public String layMaNhanVienCuoiCung() {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String maCuoiCung = null;

		try {
			con = ConnectDB.getConnection();
			// Sửa thành TOP 1 cho SQL Server
			String sql = "SELECT TOP 1 maNhanVien FROM NhanVien ORDER BY maNhanVien DESC";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				maCuoiCung = rs.getString("maNhanVien");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return maCuoiCung;
	}

	public String taoMaNhanVienMoi() {
		String maCuoiCung = layMaNhanVienCuoiCung();
		int namHienTai = Year.now().getValue();
		if (maCuoiCung.isEmpty()) {
			return namHienTai + "NV" + "000001";
		} else {
			int namTrongMa = Integer.parseInt(maCuoiCung.substring(0, 4));
			if (namTrongMa != namHienTai) {
				return namHienTai + "NV" + "000001";
			} else {
				int soCuoi = Integer.parseInt(maCuoiCung.substring(6));
				soCuoi++;
				String soCuoiFormat = String.format("%06d", soCuoi);
				return namHienTai + "NV" + soCuoiFormat;
			}
		}
	}
}
