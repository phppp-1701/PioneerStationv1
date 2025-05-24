package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.HoaDon.PhuongThucThanhToan;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

	// Thêm hóa đơn mới
	public boolean themHoaDon(HoaDon hoaDon) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, "
					+ "phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, "
					+ "ngayLamViec, maCaLam, maNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, hoaDon.getMaHoaDon());
			stmt.setDate(2, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
			stmt.setTime(3, Time.valueOf(hoaDon.getGioTaoHoaDon()));
			stmt.setString(4, hoaDon.getPhuongThucThanhToan().toString());
			stmt.setDouble(5, hoaDon.getPhanTramGiamGia());
			stmt.setDouble(6, hoaDon.getThanhTien());
			stmt.setDouble(7, hoaDon.getTienKhachDua());
			stmt.setDouble(8, hoaDon.getTienTraLai());
			stmt.setString(9, hoaDon.getMaKhachHang());
			stmt.setString(10, hoaDon.getMaKhuyenMai());

			// Xử lý ngayLamViec có thể null
			if (hoaDon.getNgayLamViec() != null) {
				stmt.setDate(11, Date.valueOf(hoaDon.getNgayLamViec()));
			} else {
				stmt.setNull(11, Types.DATE);
			}

			stmt.setString(12, hoaDon.getMaCaLam());
			stmt.setString(13, hoaDon.getMaNhanVien());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Tìm hóa đơn theo mã
	public HoaDon timHoaDonTheoMa(String maHoaDon) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, maHoaDon);
			rs = stmt.executeQuery();

			if (rs.next()) {
				// Xử lý ngayLamViec có thể null
				LocalDate ngayLamViec = null;
				Date ngayLamViecDB = rs.getDate("ngayLamViec");
				if (ngayLamViecDB != null) {
					ngayLamViec = ngayLamViecDB.toLocalDate();
				}

				return new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTaoHoaDon").toLocalDate(),
						rs.getTime("gioTaoHoaDon").toLocalTime(),
						PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
						rs.getDouble("phanTramGiamGia"), rs.getDouble("thanhTien"), rs.getDouble("tienKhachDua"),
						rs.getDouble("tienTraLai"), rs.getString("maKhachHang"), rs.getString("maKhuyenMai"),
						ngayLamViec, rs.getString("maCaLam"), rs.getString("maNhanVien"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// Lấy danh sách tất cả hóa đơn
	public List<HoaDon> layDanhSachHoaDon() {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon ORDER BY ngayTaoHoaDon DESC, gioTaoHoaDon DESC";
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Xử lý ngayLamViec có thể null
				LocalDate ngayLamViec = null;
				Date ngayLamViecDB = rs.getDate("ngayLamViec");
				if (ngayLamViecDB != null) {
					ngayLamViec = ngayLamViecDB.toLocalDate();
				}

				HoaDon hoaDon = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTaoHoaDon").toLocalDate(),
						rs.getTime("gioTaoHoaDon").toLocalTime(),
						PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
						rs.getDouble("phanTramGiamGia"), rs.getDouble("thanhTien"), rs.getDouble("tienKhachDua"),
						rs.getDouble("tienTraLai"), rs.getString("maKhachHang"), rs.getString("maKhuyenMai"),
						ngayLamViec, rs.getString("maCaLam"), rs.getString("maNhanVien"));
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return danhSachHoaDon;
	}

	// Cập nhật hóa đơn
	public boolean capNhatHoaDon(HoaDon hoaDon) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "UPDATE HoaDon SET ngayTaoHoaDon = ?, gioTaoHoaDon = ?, phuongThucThanhToan = ?, "
					+ "phanTramGiamGia = ?, thanhTien = ?, tienKhachDua = ?, tienTraLai = ?, "
					+ "maKhachHang = ?, maKhuyenMai = ?, ngayLamViec = ?, maCaLam = ?, maNhanVien = ? "
					+ "WHERE maHoaDon = ?";

			stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
			stmt.setTime(2, Time.valueOf(hoaDon.getGioTaoHoaDon()));
			stmt.setString(3, hoaDon.getPhuongThucThanhToan().toString());
			stmt.setDouble(4, hoaDon.getPhanTramGiamGia());
			stmt.setDouble(5, hoaDon.getThanhTien());
			stmt.setDouble(6, hoaDon.getTienKhachDua());
			stmt.setDouble(7, hoaDon.getTienTraLai());
			stmt.setString(8, hoaDon.getMaKhachHang());
			stmt.setString(9, hoaDon.getMaKhuyenMai());

			// Xử lý ngayLamViec có thể null
			if (hoaDon.getNgayLamViec() != null) {
				stmt.setDate(10, Date.valueOf(hoaDon.getNgayLamViec()));
			} else {
				stmt.setNull(10, Types.DATE);
			}

			stmt.setString(11, hoaDon.getMaCaLam());
			stmt.setString(12, hoaDon.getMaNhanVien());
			stmt.setString(13, hoaDon.getMaHoaDon());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Xóa hóa đơn
	public boolean xoaHoaDon(String maHoaDon) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, maHoaDon);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// tao mã hóa đơn mới
	public String taoMaHoaDonMoi() {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();

			// Lấy ngày hiện tại dưới dạng yyyyMMdd
			String ngayHienTai = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			// Tìm hóa đơn có mã lớn nhất trong ngày
			String sql = "SELECT MAX(maHoaDon) FROM HoaDon WHERE maHoaDon LIKE ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ngayHienTai + "HD%");
			rs = stmt.executeQuery();

			int soThuTu = 1;
			if (rs.next()) {
				String maMax = rs.getString(1);
				if (maMax != null) {
					// Lấy phần số từ mã hóa đơn (6 chữ số cuối)
					String soStr = maMax.substring(maMax.length() - 6);
					soThuTu = Integer.parseInt(soStr) + 1;
				}
			}

			// Định dạng số thứ tự thành 6 chữ số, thêm số 0 ở đầu nếu cần
			String soThuTuStr = String.format("%06d", soThuTu);

			// Tạo mã hóa đơn mới: yyyyMMdd + "HD" + 6 chữ số
			return ngayHienTai + "HD" + soThuTuStr;

		} catch (SQLException e) {
			e.printStackTrace();
			// Trường hợp lỗi, trả về mã mặc định
			return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "HD000001";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Tra cứu hóa đơn linh hoạt theo số điện thoại và tên khách hàng (không bắt
	// buộc ngày)
	public List<HoaDon> traCuuHoaDonTheoSDT_Ten(String soDienThoai, String tenKhachHang) {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT hd.* FROM HoaDon hd ");
			sql.append("JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang ");
			sql.append("WHERE 1=1 ");

			List<String> params = new ArrayList<>();

			if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
				sql.append("AND kh.soDienThoai LIKE ? ");
				params.add("%" + soDienThoai.trim() + "%");
			}

			if (tenKhachHang != null && !tenKhachHang.trim().isEmpty()) {
				sql.append("AND kh.tenKhachHang LIKE ? ");
				params.add("%" + tenKhachHang.trim() + "%");
			}

			sql.append("ORDER BY hd.ngayTaoHoaDon DESC, hd.gioTaoHoaDon DESC");

			stmt = connection.prepareStatement(sql.toString());

			// Set parameters
			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, params.get(i));
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				// Xử lý ngayLamViec có thể null
				LocalDate ngayLamViec = null;
				Date ngayLamViecDB = rs.getDate("ngayLamViec");
				if (ngayLamViecDB != null) {
					ngayLamViec = ngayLamViecDB.toLocalDate();
				}

				HoaDon hoaDon = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTaoHoaDon").toLocalDate(),
						rs.getTime("gioTaoHoaDon").toLocalTime(),
						PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
						rs.getDouble("phanTramGiamGia"), rs.getDouble("thanhTien"), rs.getDouble("tienKhachDua"),
						rs.getDouble("tienTraLai"), rs.getString("maKhachHang"), rs.getString("maKhuyenMai"),
						ngayLamViec, rs.getString("maCaLam"), rs.getString("maNhanVien"));
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return danhSachHoaDon;
	}

	// Tra cứu hóa đơn theo mã khách hàng
	public List<HoaDon> layDanhSachHoaDonTheoKhachHang(String maKhachHang) {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "SELECT * FROM HoaDon WHERE maKhachHang = ? ORDER BY ngayTaoHoaDon DESC, gioTaoHoaDon DESC";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, maKhachHang);
			rs = stmt.executeQuery();

			while (rs.next()) {
				// Xử lý ngayLamViec có thể null
				LocalDate ngayLamViec = null;
				Date ngayLamViecDB = rs.getDate("ngayLamViec");
				if (ngayLamViecDB != null) {
					ngayLamViec = ngayLamViecDB.toLocalDate();
				}

				HoaDon hoaDon = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTaoHoaDon").toLocalDate(),
						rs.getTime("gioTaoHoaDon").toLocalTime(),
						PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
						rs.getDouble("phanTramGiamGia"), rs.getDouble("thanhTien"), rs.getDouble("tienKhachDua"),
						rs.getDouble("tienTraLai"), rs.getString("maKhachHang"), rs.getString("maKhuyenMai"),
						ngayLamViec, rs.getString("maCaLam"), rs.getString("maNhanVien"));
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return danhSachHoaDon;
	}

	// tra cứu hóa đơn theo số điện thoại, tên khách hàng và ngày tạo hóa đơn
	public List<HoaDon> traCuuHoaDonTheoSDT_Ten_Ngay(String soDienThoai, String tenKhachHang, LocalDate ngayTaoHoaDon) {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "SELECT hd.* FROM HoaDon hd " + "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
					+ "WHERE kh.soDienThoai LIKE ? AND kh.tenKhachHang LIKE ? AND hd.ngayTaoHoaDon = ?";
			stmt = connection.prepareStatement(sql);

			// Thêm % để tìm kiếm gần đúng
			stmt.setString(1, "%" + soDienThoai + "%");
			stmt.setString(2, "%" + tenKhachHang + "%");
			stmt.setDate(3, Date.valueOf(ngayTaoHoaDon));

			rs = stmt.executeQuery();

			while (rs.next()) {
				// Xử lý ngayLamViec có thể null
				LocalDate ngayLamViec = null;
				Date ngayLamViecDB = rs.getDate("ngayLamViec");
				if (ngayLamViecDB != null) {
					ngayLamViec = ngayLamViecDB.toLocalDate();
				}

				HoaDon hoaDon = new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTaoHoaDon").toLocalDate(),
						rs.getTime("gioTaoHoaDon").toLocalTime(),
						PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
						rs.getDouble("phanTramGiamGia"), rs.getDouble("thanhTien"), rs.getDouble("tienKhachDua"),
						rs.getDouble("tienTraLai"), rs.getString("maKhachHang"), rs.getString("maKhuyenMai"),
						ngayLamViec, rs.getString("maCaLam"), rs.getString("maNhanVien"));
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return danhSachHoaDon;
	}
	

}
