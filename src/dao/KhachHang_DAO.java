package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;

public class KhachHang_DAO {

	// Thêm một khách hàng mới
	public boolean themKhachHang(KhachHang kh) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		boolean themKH = false;
		try {
			con = ConnectDB.getConnection();
			String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, kh.getMaKhachHang());
			preparedStatement.setString(2, kh.getTenKhachHang());
			preparedStatement.setString(3, kh.getCccd_HoChieu());
			preparedStatement.setString(4, kh.getSoDienThoai());
			preparedStatement.setString(5, kh.getLoaiKhachHang().name());
			preparedStatement.setString(6, kh.getTrangThaiKhachHang().name());
			preparedStatement.setString(7, kh.getEmail());

			int rows = preparedStatement.executeUpdate();
			themKH = rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return themKH;
	}

//	// Tìm
//	public KhachHang timKhachHangTheoMa1(String maKhachHang) {
//		KhachHang khachHang = null;
//		Connection con = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		try {
//			con = ConnectDB.getConnection();
//			String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
//			preparedStatement = con.prepareStatement(sql);
//			preparedStatement.setString(1, maKhachHang);
//			resultSet = preparedStatement.executeQuery();
//			if (resultSet.next()) {
//				khachHang = new KhachHang();
//				khachHang.setMaKhachHang(resultSet.getString(1));
//				khachHang.setTenKhachHang(resultSet.getString(2));
//				khachHang.setCccd_HoChieu(resultSet.getString(3));
//				khachHang.setSoDienThoai(resultSet.getString(4));
//				LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
//				khachHang.setLoaiKhachHang(loaiKhachHang);
//				TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
//				khachHang.setTrangThaiKhachHang(trangThaiKhachHang);
//				khachHang.setEmail(resultSet.getString(7));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (preparedStatement != null) {
//				try {
//					preparedStatement.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			ConnectDB.getInstance().disconnect();
//		}
//		return khachHang;
//	}

	// Lấy danh sách tất cả khách hàng
	public List<KhachHang> layTatCaKhachHang() {
		List<KhachHang> danhSachKhachHang = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKhachHang(resultSet.getString(1));
				kh.setTenKhachHang(resultSet.getString(2));
				kh.setCccd_HoChieu(resultSet.getString(3));
				kh.setSoDienThoai(resultSet.getString(4));
				LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
				kh.setLoaiKhachHang(loaiKhachHang);
				TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
				kh.setTrangThaiKhachHang(trangThaiKhachHang);
				kh.setEmail(resultSet.getString(10));
				danhSachKhachHang.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return danhSachKhachHang;
	}

	// Tìm khách hàng theo tên
	public List<KhachHang> timKhachHangTheoTen(String tenKH) {
		List<KhachHang> danhSachKhachHang = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang WHERE tenKhachHang LIKE ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, "%" + tenKH + "%"); // Tìm kiếm gần đúng
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKhachHang(resultSet.getString(1));
				kh.setTenKhachHang(resultSet.getString(2));
				kh.setCccd_HoChieu(resultSet.getString(3));
				kh.setSoDienThoai(resultSet.getString(4));
				LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
				kh.setLoaiKhachHang(loaiKhachHang);
				TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
				kh.setTrangThaiKhachHang(trangThaiKhachHang);
				kh.setEmail(resultSet.getString(10));
				danhSachKhachHang.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return danhSachKhachHang;
	}

	// Tìm khách hàng theo số điện thoại
	public List<KhachHang> timKhachHangTheoSoDienThoai(String soDT) {
		List<KhachHang> danhSachKhachHang = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang WHERE soDienThoai LIKE ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, "%" + soDT + "%"); // Tìm kiếm gần đúng
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				KhachHang kh = new KhachHang();
				kh.setMaKhachHang(resultSet.getString(1));
				kh.setTenKhachHang(resultSet.getString(2));
				kh.setCccd_HoChieu(resultSet.getString(3));
				kh.setSoDienThoai(resultSet.getString(4));
				LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
				kh.setLoaiKhachHang(loaiKhachHang);
				TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
				kh.setTrangThaiKhachHang(trangThaiKhachHang);
				kh.setEmail(resultSet.getString(10));
				danhSachKhachHang.add(kh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return danhSachKhachHang;
	}

	// Cập nhật khách hàng
	public boolean capNhatKhachHang(KhachHang kh) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		boolean ketQua = false;
		try {
			con = ConnectDB.getConnection();
			String sql = "UPDATE KhachHang SET tenKhachHang = ?, cccd_HoChieu = ?, soDienThoai = ?, loaiKhachHang = ?, trangThaiKhachHang = ?, email = ? WHERE maKhachHang = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, kh.getTenKhachHang());
			preparedStatement.setString(2, kh.getCccd_HoChieu());
			preparedStatement.setString(3, kh.getSoDienThoai());
			preparedStatement.setString(4, kh.getLoaiKhachHang().name());
			preparedStatement.setString(5, kh.getTrangThaiKhachHang().name());
			preparedStatement.setString(6, kh.getEmail());
			preparedStatement.setString(7, kh.getMaKhachHang());

			int rowsAffected = preparedStatement.executeUpdate();
			ketQua = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return ketQua;
	}

	// Tìm khách hàng theo mã khách hàng
	public KhachHang timKhachHangTheoMa(String maKH) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		KhachHang kh = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maKH);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				kh = new KhachHang();
				kh.setMaKhachHang(resultSet.getString("maKhachHang"));
				kh.setTenKhachHang(resultSet.getString("tenKhachHang"));
				kh.setCccd_HoChieu(resultSet.getString("cccd_HoChieu"));
				kh.setSoDienThoai(resultSet.getString("soDienThoai"));
				kh.setLoaiKhachHang(KhachHang.LoaiKhachHang.valueOf(resultSet.getString("loaiKhachHang")));
				kh.setTrangThaiKhachHang(
						KhachHang.TrangThaiKhachHang.valueOf(resultSet.getString("trangThaiKhachHang")));
				kh.setEmail(resultSet.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectDB.getInstance().disconnect();
		}
		return kh;
	}

}