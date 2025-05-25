package dao;

import entity.ChiTietCho;
import entity.ChiTietCho.TrangThaiCho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class ChiTietCho_DAO {
//	// Tìm ChiTietCho theo maVe
//	public ChiTietCho timChiTietChoTheoMaVe(String maVe) {
//		ChiTietCho chiTietCho = null;
//		Connection con = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		try {
//			con = ConnectDB.getConnection();
//			String sql = "SELECT * FROM ChiTietCho WHERE maVe = ?";
//			preparedStatement = con.prepareStatement(sql);
//			preparedStatement.setString(1, maVe);
//			resultSet = preparedStatement.executeQuery();
//			if (resultSet.next()) {
//				chiTietCho = new ChiTietCho();
//				chiTietCho.setMaCho(resultSet.getString("maCho"));
//				chiTietCho.setMaChuyenTau(resultSet.getString("maChuyenTau"));
//				chiTietCho.setMaVe(resultSet.getString("maVe"));
//				chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
//				chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectDB.getInstance().disconnect();
//		}
//		return chiTietCho;
//	}

	// Lấy tất cả danh sách ChiTietCho
	public List<ChiTietCho> layTatCaChiTietCho() {
		List<ChiTietCho> danhSachChiTietCho = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM ChiTietCho";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ChiTietCho chiTietCho = new ChiTietCho();
				chiTietCho.setMaCho(resultSet.getString("maCho"));
				chiTietCho.setMaChuyenTau(resultSet.getString("maChuyenTau"));
				chiTietCho.setMaVe(resultSet.getString("maVe"));
				chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
				chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
				danhSachChiTietCho.add(chiTietCho);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachChiTietCho;
	}

	public ChiTietCho timChiTietChoTheoMaVe(String maVe) {
        ChiTietCho chiTietCho = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChiTietCho WHERE maVe = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, maVe);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chiTietCho = new ChiTietCho();
                chiTietCho.setMaCho(resultSet.getString("maCho"));
                chiTietCho.setMaChuyenTau(resultSet.getString("maChuyenTau"));
                chiTietCho.setMaVe(resultSet.getString("maVe"));
                chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
                chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }
        return chiTietCho;
    }
	
}
