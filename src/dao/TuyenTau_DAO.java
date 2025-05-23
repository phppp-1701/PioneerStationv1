package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ga;
import entity.TuyenTau;

public class TuyenTau_DAO {

	/**
	 * Lấy danh sách tuyến tàu khác mã tuyến được chỉ định
	 */
	public List<TuyenTau> getDanhSachTuyenTauKhacMa(String maTuyenTau) {
		List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT t.maTuyenTau, t.tenTuyenTau, t.khoangCach, t.gaDi, t.gaDen, "
					+ "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " + "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen "
					+ "FROM TuyenTau t " + "INNER JOIN Ga g1 ON t.gaDi = g1.maGa "
					+ "INNER JOIN Ga g2 ON t.gaDen = g2.maGa " + "WHERE t.maTuyenTau != ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Ga gaDi = new Ga(rs.getString("gaDi"), rs.getString("tenGaDi"), rs.getString("diaChiDi"));
				Ga gaDen = new Ga(rs.getString("gaDen"), rs.getString("tenGaDen"), rs.getString("diaChiDen"));
				TuyenTau tuyenTau = new TuyenTau();
				tuyenTau.setMaTuyenTau(rs.getString("maTuyenTau"));
				tuyenTau.setTenTuyenTau(rs.getString("tenTuyenTau"));
				danhSachTuyenTau.add(tuyenTau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return danhSachTuyenTau;
	}

	/**
	 * Tìm tuyến tàu theo mã tuyến
	 */
	public TuyenTau timTuyenTauTheoMaTuyen(String maTuyenTau) {
		TuyenTau tuyenTau = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT t.maTuyenTau, t.tenTuyenTau, t.khoangCach, t.gaDi, t.gaDen, "
					+ "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " + "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen "
					+ "FROM TuyenTau t " + "INNER JOIN Ga g1 ON t.gaDi = g1.maGa "
					+ "INNER JOIN Ga g2 ON t.gaDen = g2.maGa " + "WHERE t.maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Ga gaDi = new Ga(rs.getString("gaDi"), rs.getString("tenGaDi"), rs.getString("diaChiDi"));
				Ga gaDen = new Ga(rs.getString("gaDen"), rs.getString("tenGaDen"), rs.getString("diaChiDen"));
				tuyenTau = new TuyenTau(rs.getString("maTuyenTau"), rs.getString("tenTuyenTau"),
						rs.getDouble("khoangCach"), gaDi, gaDen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tuyenTau;
	}

	/**
	 * Lấy danh sách ga đến theo ga đi
	 */
	public List<Ga> getDanhSachGaDenTheoGaDi(String maGaDi) {
		List<Ga> danhSachGaDen = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT g.maGa, g.tenGa, g.diaChi " + "FROM TuyenTau t "
					+ "INNER JOIN Ga g ON t.gaDen = g.maGa " + "WHERE t.gaDi = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maGaDi);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Ga ga = new Ga(rs.getString("maGa"), rs.getString("tenGa"), rs.getString("diaChi"));
				danhSachGaDen.add(ga);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return danhSachGaDen;
	}

	/**
	 * Lấy danh sách ga đi theo ga đến
	 */
	public List<Ga> getDanhSachGaDiTheoGaDen(String maGaDen) {
		List<Ga> danhSachGaDi = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT g.maGa, g.tenGa, g.diaChi " + "FROM TuyenTau t "
					+ "INNER JOIN Ga g ON t.gaDi = g.maGa " + "WHERE t.gaDen = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maGaDen);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Ga ga = new Ga(rs.getString("maGa"), rs.getString("tenGa"), rs.getString("diaChi"));
				danhSachGaDi.add(ga);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return danhSachGaDi;
	}

	/**
	 * Lấy mã ga đi theo mã tuyến
	 */
	public String getMaGaDiTheoMaTuyen(String maTuyenTau) {
		String maGaDi = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT gaDi FROM TuyenTau WHERE maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				maGaDi = rs.getString("gaDi");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maGaDi;
	}

	/**
	 * Lấy mã ga đến theo mã tuyến
	 */
	public String getMaGaDenTheoMaTuyen(String maTuyenTau) {
		String maGaDen = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT gaDen FROM TuyenTau WHERE maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				maGaDen = rs.getString("gaDen");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maGaDen;
	}

	/**
	 * Lấy mã tuyến theo ga đi và ga đến
	 */
	public String getMaTuyenTheoGa(String maGaDi, String maGaDen) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String maTuyenTau = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT maTuyenTau FROM TuyenTau WHERE gaDi = ? AND gaDen = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maGaDi);
			pstmt.setString(2, maGaDen);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				maTuyenTau = rs.getString("maTuyenTau");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return maTuyenTau;
	}

	/**
	 * Lấy tất cả tuyến tàu
	 */
	public List<TuyenTau> getDanhSachTuyenTau() {
		List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT t.maTuyenTau, t.tenTuyenTau, t.khoangCach, t.gaDi, t.gaDen, "
					+ "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " + "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen "
					+ "FROM TuyenTau t " + "INNER JOIN Ga g1 ON t.gaDi = g1.maGa "
					+ "INNER JOIN Ga g2 ON t.gaDen = g2.maGa " + "ORDER BY t.maTuyenTau";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Ga gaDi = new Ga(rs.getString("gaDi"), rs.getString("tenGaDi"), rs.getString("diaChiDi"));
				Ga gaDen = new Ga(rs.getString("gaDen"), rs.getString("tenGaDen"), rs.getString("diaChiDen"));
				TuyenTau tuyenTau = new TuyenTau(rs.getString("maTuyenTau"), rs.getString("tenTuyenTau"),
						rs.getDouble("khoangCach"), gaDi, gaDen);
				danhSachTuyenTau.add(tuyenTau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return danhSachTuyenTau;
	}

	/**
	 * Thêm tuyến tàu mới
	 */
	public boolean themTuyenTau(TuyenTau tuyenTau) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = ConnectDB.getConnection();
			String sql = "INSERT INTO TuyenTau (maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tuyenTau.getMaTuyenTau());
			pstmt.setString(2, tuyenTau.getTenTuyenTau());
			pstmt.setDouble(3, tuyenTau.getKhoangCach());
			pstmt.setString(4, tuyenTau.getGaDi().getMaGa());
			pstmt.setString(5, tuyenTau.getGaDen().getMaGa());

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Cập nhật thông tin tuyến tàu
	 */
	public boolean capNhatTuyenTau(TuyenTau tuyenTau) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = ConnectDB.getConnection();
			String sql = "UPDATE TuyenTau SET tenTuyenTau = ?, khoangCach = ?, gaDi = ?, gaDen = ? WHERE maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tuyenTau.getTenTuyenTau());
			pstmt.setDouble(2, tuyenTau.getKhoangCach());
			pstmt.setString(3, tuyenTau.getGaDi().getMaGa());
			pstmt.setString(4, tuyenTau.getGaDen().getMaGa());
			pstmt.setString(5, tuyenTau.getMaTuyenTau());

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Xóa tuyến tàu
	 */
	public boolean xoaTuyenTau(String maTuyenTau) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = ConnectDB.getConnection();
			String sql = "DELETE FROM TuyenTau WHERE maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Kiểm tra tuyến tàu có tồn tại không
	 */
	public boolean kiemTraTonTaiTuyenTau(String maTuyenTau) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean exists = false;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) FROM TuyenTau WHERE maTuyenTau = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maTuyenTau);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exists;
	}

	/**
	 * Tìm kiếm tuyến tàu theo tên
	 */
	public List<TuyenTau> timKiemTuyenTauTheoTen(String tenTuyenTau) {
		List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT t.maTuyenTau, t.tenTuyenTau, t.khoangCach, t.gaDi, t.gaDen, "
					+ "g1.tenGa AS tenGaDi, g1.diaChi AS diaChiDi, " + "g2.tenGa AS tenGaDen, g2.diaChi AS diaChiDen "
					+ "FROM TuyenTau t " + "INNER JOIN Ga g1 ON t.gaDi = g1.maGa "
					+ "INNER JOIN Ga g2 ON t.gaDen = g2.maGa " + "WHERE t.tenTuyenTau LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + tenTuyenTau + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Ga gaDi = new Ga(rs.getString("gaDi"), rs.getString("tenGaDi"), rs.getString("diaChiDi"));
				Ga gaDen = new Ga(rs.getString("gaDen"), rs.getString("tenGaDen"), rs.getString("diaChiDen"));
				TuyenTau tuyenTau = new TuyenTau();
				 tuyenTau.setMaTuyenTau(rs.getString("maTuyenTau"));
				 tuyenTau.setTenTuyenTau(rs.getString("tenTuyenTau"));
				 tuyenTau.setKhoangCach(rs.getDouble("khoangCach"));
				 
				danhSachTuyenTau.add(tuyenTau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return danhSachTuyenTau;
	}
}