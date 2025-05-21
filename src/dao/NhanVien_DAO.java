package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;

public class NhanVien_DAO {
	public List<NhanVien> xuatToanBoDanhSachNhanVien() {
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
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsnv;
	}

	public NhanVien timNhanVienTheoMa(String maNV) {
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
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return nv;
	}

	// Tim nhan vien theo ten
	public List<NhanVien> timNhanVienTheoTen(String tenNhanVien) {
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
				nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				// Xử lý enum GioiTinh
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));

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

		return dsNhanVien;
	}

	/// Tim theo so dien thoai
	public List<NhanVien> timNhanVienTheoSoDienThoai(String soDienThoai) {
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
				nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));

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

				// Thêm ánh xạ cho cột linkAnh
				nv.setUrlAnh(rs.getString("UrlAnh"));

				dsNhanVien.add(nv);
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

		return dsNhanVien;
	}

	public List<NhanVien> timNhanVienTheoTenVaSoDienThoai(String tenNhanVien, String soDienThoai) {
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
				nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
				nv.setSoDienThoai(rs.getString("soDienThoai"));
				nv.setEmail(rs.getString("email"));
				// Xử lý enum GioiTinh
				String gioiTinh = rs.getString("gioiTinh");
				nv.setGioiTinh("nu".equals(gioiTinh) ? GioiTinh.nu : GioiTinh.nam);

				nv.setCccd_HoChieu(rs.getString("CCCD_HoChieu"));

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
				nv.setUrlAnh(rs.getString("UrlAnh"));
				dsNhanVien.add(nv);
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

		return dsNhanVien;
	}
	 // Thêm nhân viên mới
    public boolean themNhanVien(NhanVien nv) throws SQLException {
        String sql = "INSERT INTO NhanVien(maNhanVien, tenNhanVien, cccd_HoChieu, soDienThoai, ngaySinh, "
                   + "chucVu, gioiTinh, urlAnh, trangThaiNhanVien, email) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getTenNhanVien());
            stmt.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));
            stmt.setString(4, nv.getSoDienThoai());
            stmt.setString(5, nv.getEmail());
            
            // Xử lý giới tính (phải là 'nam' hoặc 'nu')
            String gioiTinh = (nv.getGioiTinh() == NhanVien.GioiTinh.nam) ? "nam" : "nu";
            stmt.setString(6, gioiTinh);
            
            stmt.setString(7, nv.getCccd_HoChieu());
            
            // Xử lý chức vụ (phải là 'quanLy' hoặc 'banVe')
            String chucVu = (nv.getChucVu() == NhanVien.ChucVu.quanLy) ? "quanLy" : "banVe";
            stmt.setString(8, chucVu);

            // Xử lý trạng thái (phải là 'hoatDong' hoặc 'voHieuHoa')
            String trangThai = (nv.getTrangThaiNhanVien() == NhanVien.TrangThaiNhanVien.hoatDong) 
                              ? "hoatDong" : "voHieuHoa";
            stmt.setString(9, trangThai);
            
            // Xử lý link ảnh (có thể null)
            stmt.setString(10, nv.getUrlAnh());
            
            return stmt.executeUpdate() > 0;
        }
    }
 // Cập nhật thông tin nhân viên
    public boolean capNhatNhanVien(NhanVien nv) {
        Connection con = null;
        PreparedStatement stmt = null;
        boolean thanhCong = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE NhanVien SET tenNhanVien = ?, ngaySinh = ?, soDienThoai = ?, email = ?, gioiTinh = ?, "
                       + "cccd_HoChieu = ?, chucVu = ?, trangThaiNhanVien = ?, UrlAnh = ? WHERE maNhanVien = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, nv.getTenNhanVien());
            stmt.setDate(2, java.sql.Date.valueOf(nv.getNgaySinh()));
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
            String trangThai = (nv.getTrangThaiNhanVien() == NhanVien.TrangThaiNhanVien.hoatDong) 
                              ? "hoatDong" : "voHieuHoa";
            stmt.setString(8, trangThai);
            
            // Xử lý link ảnh (có thể null)
            stmt.setString(9, nv.getUrlAnh());
            
            stmt.setString(10, nv.getMaNhanVien());

            int rowsAffected = stmt.executeUpdate();
            thanhCong = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return thanhCong;
    }

}
