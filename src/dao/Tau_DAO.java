package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;

public class Tau_DAO {
	public List<Tau> layToanBoDanhSach() {
		List<Tau> dst = new ArrayList<Tau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from tau";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tau tau = new Tau();
				tau.setMaTau(resultSet.getString(1));
				tau.setTenTau(resultSet.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(resultSet.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(resultSet.getString(4)));
				dst.add(tau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return dst;
	}

	public List<Tau> timTauTheoMa(String maTau) {
		List<Tau> dst = new ArrayList<Tau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from tau where maTau like ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTau);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tau tau = new Tau();
				tau.setMaTau(resultSet.getString(1));
				tau.setTenTau(resultSet.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(resultSet.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(resultSet.getString(4)));
				dst.add(tau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return dst;
	}

	public Tau timTauTheoMaTau(String maTau) {
		Tau tau = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM tau WHERE maTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTau);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				tau = new Tau();
				tau.setMaTau(resultSet.getString(1));
				tau.setTenTau(resultSet.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(resultSet.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(resultSet.getString(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return tau;
	}
}
