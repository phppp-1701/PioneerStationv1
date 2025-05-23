package dao;

import connectDB.ConnectDB;
import entity.Ve;
import entity.Ve.TrangThaiVe;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ve_DAO {

	// Thêm vé mới
	public boolean themVe(Ve ve) {
		String sql = "INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, CCCD_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = ConnectDB.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, ve.getMaVe());
			stmt.setDate(2, Date.valueOf(ve.getNgayTaoVe()));
			stmt.setString(3, ve.getTrangThaiVe().name());
			stmt.setString(4, ve.getTenKhachHang());
			stmt.setString(5, ve.getCccd_HoChieu());
			stmt.setDate(6, ve.getNgaySinh() != null ? Date.valueOf(ve.getNgaySinh()) : null);
			stmt.setString(7, ve.getLoaiVe());
			stmt.setDouble(8, ve.getGiaVe());
			stmt.setDouble(9, ve.getPhanTramGiamGiaCoDinh());
			stmt.setString(10, ve.getMaHoaDon());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Lấy tất cả vé
	public List<Ve> layDanhSachVe() {
		List<Ve> ds = new ArrayList<>();
		String sql = "SELECT * FROM Ve";
		try (Connection connection = ConnectDB.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				ds.add(taoVeTuResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	// Cập nhật vé
	public boolean capNhatVe(Ve ve) {
		String sql = "UPDATE Ve SET ngayTaoVe=?, trangThaiVe=?, tenKhachHang=?, CCCD_HoChieu=?, ngaySinh=?, loaiVe=?, giaVe=?, phanTramGiamGiaCoDinh=?, maHoaDon=? "
				+ "WHERE maVe=?";
		try (Connection connection = ConnectDB.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, Date.valueOf(ve.getNgayTaoVe()));
			stmt.setString(2, ve.getTrangThaiVe().name());
			stmt.setString(3, ve.getTenKhachHang());
			stmt.setString(4, ve.getCccd_HoChieu());
			stmt.setDate(5, ve.getNgaySinh() != null ? Date.valueOf(ve.getNgaySinh()) : null);
			stmt.setString(6, ve.getLoaiVe());
			stmt.setDouble(7, ve.getGiaVe());
			stmt.setDouble(8, ve.getPhanTramGiamGiaCoDinh());
			stmt.setString(9, ve.getMaHoaDon());
			stmt.setString(10, ve.getMaVe());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Xóa vé
	public boolean xoaVe(String maVe) {
		String sql = "DELETE FROM Ve WHERE maVe = ?";
		try (Connection connection = ConnectDB.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, maVe);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Tìm vé theo mã hóa đơn
	public List<Ve> timVeTheoMaHoaDon(String maHoaDon) {
		List<Ve> ds = new ArrayList<>();
		String sql = "SELECT * FROM Ve WHERE maHoaDon = ?";
		try (Connection connection = ConnectDB.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, maHoaDon);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ds.add(taoVeTuResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	// Tìm theo tên khách hàng
	public List<Ve> timVeTheoTenKhachHang(String tenKhachHang) {
		List<Ve> list = new ArrayList<>();
		String sql = "SELECT * FROM Ve WHERE tenKhachHang LIKE ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "%" + tenKhachHang + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(taoVeTuResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/// Tìm theo ngày tạo
	public List<Ve> timVeTheoNgayTao(LocalDate ngayTao) {
		List<Ve> list = new ArrayList<>();
		String sql = "SELECT * FROM Ve WHERE ngayTaoVe = ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, Date.valueOf(ngayTao));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(taoVeTuResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Tạo mã vé mới theo mã chỗ, ngày và giờ
	public String taoMaVeMoiTheoMaChoGioNgay(String maChoNgoi, LocalDate ngay, LocalTime gio) {
		String d = ngay.format(DateTimeFormatter.ofPattern("ddMMyy"));
		String t = gio.format(DateTimeFormatter.ofPattern("HHmm"));
		return maChoNgoi + d + t;
	}

	// Tạo đối tượng Ve từ ResultSet
	private Ve taoVeTuResultSet(ResultSet rs) throws SQLException {
		return new Ve(rs.getString("maVe"), rs.getDate("ngayTaoVe").toLocalDate(), rs.getString("tenKhachHang"),
				rs.getString("CCCD_HoChieu"),
				rs.getDate("ngaySinh") != null ? rs.getDate("ngaySinh").toLocalDate() : null, rs.getString("loaiVe"),
				rs.getDouble("giaVe"), rs.getDouble("phanTramGiamGiaCoDinh"), rs.getString("maHoaDon"),
				TrangThaiVe.valueOf(rs.getString("trangThaiVe")));
	}
}
