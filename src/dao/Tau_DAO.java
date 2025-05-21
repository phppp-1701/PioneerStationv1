package dao;

import connectDB.ConnectDB;
import entity.Tau;
import entity.Tau.TrangThaiTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tau_DAO {

	// Thêm tàu mới
	public boolean themTau(Tau tau) {
		String sql = "INSERT INTO Tau (maTau, tenTau, trangThaiTau) VALUES (?, ?, ?)";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tau.getMaTau());
			stmt.setString(2, tau.getTenTau());
			stmt.setString(3, tau.getTrangThaiTau().name());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Cập nhật thông tin tàu
	public boolean capNhatTau(Tau tau) {
		String sql = "UPDATE Tau SET tenTau = ?, trangThaiTau = ? WHERE maTau = ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, tau.getTenTau());
			stmt.setString(2, tau.getTrangThaiTau().name());
			stmt.setString(3, tau.getMaTau());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Xóa tàu theo mã
	public boolean xoaTau(String maTau) {
		String sql = "DELETE FROM Tau WHERE maTau = ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maTau);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Tìm tàu theo mã
	public Tau timTauTheoMa(String maTau) {
		String sql = "SELECT * FROM Tau WHERE maTau = ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maTau);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Tau(rs.getString("maTau"), rs.getString("tenTau"),
						TrangThaiTau.valueOf(rs.getString("trangThaiTau")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Lấy danh sách tất cả tàu
	public List<Tau> layDanhSachTau() {
		List<Tau> danhSach = new ArrayList<>();
		String sql = "SELECT * FROM Tau";
		try (Connection conn = ConnectDB.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Tau tau = new Tau(rs.getString("maTau"), rs.getString("tenTau"),
						TrangThaiTau.valueOf(rs.getString("trangThaiTau")));
				danhSach.add(tau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

	// Tìm tàu theo tên (tìm kiếm gần đúng)
	public List<Tau> timTauTheoTen(String tenTau) {
		List<Tau> danhSach = new ArrayList<>();
		String sql = "SELECT * FROM Tau WHERE tenTau LIKE ?";
		try (Connection conn = ConnectDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, "%" + tenTau + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Tau tau = new Tau(rs.getString("maTau"), rs.getString("tenTau"),
						Tau.TrangThaiTau.valueOf(rs.getString("trangThaiTau")));
				danhSach.add(tau);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

}
