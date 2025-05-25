package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ve;
import entity.Ve.LoaiVe;
import entity.Ve.TrangThaiVe;
import entity.KhachHang.LoaiKhachHang;

public class Ve_DAO {

	public boolean themVeMoi(Ve ve) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "INSERT INTO Ve (maVe, ngayTaoVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGia, maHoaDon, trangThaiVe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, ve.getMaVe());
			preparedStatement.setDate(2, new java.sql.Date(ve.getNgayTaoVe().getTime()));
			preparedStatement.setString(3, ve.getTenKhachHang());
			preparedStatement.setString(4, ve.getCccd_HoChieu());
			preparedStatement.setDate(5, new java.sql.Date(ve.getNgaySinh().getTime()));
			preparedStatement.setString(6, ve.getLoaiVe().name());
			preparedStatement.setDouble(7, ve.getGiaVe());
			preparedStatement.setDouble(8, ve.getPhanTramGiamGia());
			preparedStatement.setString(9, ve.getMaHoaDon());
			preparedStatement.setString(10, ve.getTrangThaiVe().name());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectDB.getInstance().disconnect();
		}
	}

	public List<Ve> timVeTheoTenKhachHang(String tenKH) {
		List<Ve> danhSachVe = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM Ve WHERE tenKhachHang LIKE ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, "%" + tenKH + "%"); // Tìm kiếm gần đúng
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Ve ve = new Ve();
				ve.setMaVe(resultSet.getString(1));
				ve.setNgayTaoVe(resultSet.getDate(2));
				ve.setTenKhachHang(resultSet.getString(3));
				ve.setCccd_HoChieu(resultSet.getString(4));
				ve.setNgaySinh(resultSet.getDate(5));
				LoaiVe loaiVe = LoaiVe.valueOf(resultSet.getString(6));
				ve.setLoaiVe(loaiVe);
				ve.setGiaVe(resultSet.getDouble(7));
				ve.setPhanTramGiamGia(resultSet.getDouble(8));
				ve.setMaHoaDon(resultSet.getString(9));
				TrangThaiVe trangThaiVe = TrangThaiVe.valueOf(resultSet.getString(10));
				ve.setTrangThaiVe(trangThaiVe);
				danhSachVe.add(ve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachVe;
	}

	public static List<Ve> layDanhSachTatCaVe() {
		List<Ve> danhSachVe = new ArrayList<>();
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM Ve";
			statement = con.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Ve ve = new Ve();
				ve.setMaVe(resultSet.getString(1));
				ve.setNgayTaoVe(resultSet.getDate(2));
				ve.setTenKhachHang(resultSet.getString(3));
				ve.setCccd_HoChieu(resultSet.getString(4));
				ve.setNgaySinh(resultSet.getDate(5));
				LoaiVe loaiVe = LoaiVe.valueOf(resultSet.getString(6));
				ve.setLoaiVe(loaiVe);
				ve.setGiaVe(resultSet.getDouble(7));
				ve.setPhanTramGiamGia(resultSet.getDouble(8));
				ve.setMaHoaDon(resultSet.getString(9));
				TrangThaiVe trangThaiVe = TrangThaiVe.valueOf(resultSet.getString(10));
				ve.setTrangThaiVe(trangThaiVe);
				danhSachVe.add(ve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachVe;
	}

	public boolean capNhatThongTinVe(Ve ve) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "UPDATE Ve SET ngayTaoVe = ?, tenKhachHang = ?, cccd_HoChieu = ?, ngaySinh = ?, loaiVe = ?, giaVe = ?, phanTramGiamGia = ?, maHoaDon = ?, trangThaiVe = ? WHERE maVe = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setDate(1, new java.sql.Date(ve.getNgayTaoVe().getTime()));
			preparedStatement.setString(2, ve.getTenKhachHang());
			preparedStatement.setString(3, ve.getCccd_HoChieu());
			preparedStatement.setDate(4, new java.sql.Date(ve.getNgaySinh().getTime()));
			preparedStatement.setString(5, ve.getLoaiVe().name());
			preparedStatement.setDouble(6, ve.getGiaVe());
			preparedStatement.setDouble(7, ve.getPhanTramGiamGia());
			preparedStatement.setString(8, ve.getMaHoaDon());
			preparedStatement.setString(9, ve.getTrangThaiVe().name());
			preparedStatement.setString(10, ve.getMaVe());

			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectDB.getInstance().disconnect();
		}
	}

	public List<Ve> layDanhSachVeTheoHoaDon(String maHoaDon) {
		List<Ve> danhSachVe = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM Ve WHERE maHoaDon = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maHoaDon);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Ve ve = new Ve();
				ve.setMaVe(resultSet.getString("maVe"));
				ve.setNgayTaoVe(resultSet.getDate("ngayTaoVe"));
				ve.setTenKhachHang(resultSet.getString("tenKhachHang"));
				ve.setCccd_HoChieu(resultSet.getString("cccd_HoChieu"));
				ve.setNgaySinh(resultSet.getDate("ngaySinh"));
				ve.setLoaiVe(Ve.LoaiVe.valueOf(resultSet.getString("loaiVe")));
				ve.setGiaVe(resultSet.getDouble("giaVe"));
				ve.setPhanTramGiamGia(resultSet.getDouble("phanTramGiamGiaCoDinh"));
				ve.setMaHoaDon(resultSet.getString("maHoaDon"));
				ve.setTrangThaiVe(Ve.TrangThaiVe.valueOf(resultSet.getString("trangThaiVe")));
				danhSachVe.add(ve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachVe;
	}
}
