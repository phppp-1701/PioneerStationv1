package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ToaTau;

public class ToaTau_DAO {
	public List<ToaTau> layToanBoToa(){
		List<ToaTau> dstt = new ArrayList<ToaTau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from ToaTau";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ToaTau toaTau = new ToaTau();
				toaTau.setMaToaTau(resultSet.getString(1));
				toaTau.setThuTuToa(resultSet.getInt(2));
				toaTau.setSoLuongChoDaBan(resultSet.getInt(3));
				toaTau.setSoLuongChoDangDat(resultSet.getInt(4));
				toaTau.setSoLuongChoConTrong(resultSet.getInt(5));
				toaTau.setTau(resultSet.getString(6));
				dstt.add(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
	
	public List<ToaTau> timToaTauTheoMaTau(String maTau) {
		List<ToaTau> dstt = new ArrayList<ToaTau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from ToaTau where maTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTau);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ToaTau toaTau = new ToaTau();
				toaTau.setMaToaTau(resultSet.getString(1));
				toaTau.setThuTuToa(resultSet.getInt(2));
				toaTau.setSoLuongChoDaBan(resultSet.getInt(3));
				toaTau.setSoLuongChoDangDat(resultSet.getInt(4));
				toaTau.setSoLuongChoConTrong(resultSet.getInt(5));
				toaTau.setMaTau(resultSet.getString(6));
				dstt.add(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
}
