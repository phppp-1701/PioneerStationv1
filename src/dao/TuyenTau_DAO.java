package dao;

import entity.TuyenTau;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class TuyenTau_DAO {
	public List<TuyenTau> layToanBoTuyenTau(){
		List<TuyenTau> dstt = new ArrayList<TuyenTau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from TuyenTau";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				TuyenTau tuyenTau = new TuyenTau();
				tuyenTau.setMaTuyenTau(resultSet.getString(1));
				tuyenTau.setTenTuyenTau(resultSet.getString(2));
				tuyenTau.setKhoangCach(resultSet.getDouble(3));
				tuyenTau.setGaDi(resultSet.getString(4));
				tuyenTau.setGaDen(resultSet.getString(5));
				dstt.add(tuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
	public TuyenTau timTuyenTauTheo(String gaDi, String gaDen){
		TuyenTau tuyenTau = new TuyenTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from TuyenTau where gaDi = ? and gaDen = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, gaDi);
			preparedStatement.setString(2, gaDen);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				tuyenTau.setMaTuyenTau(resultSet.getString(1));
				tuyenTau.setTenTuyenTau(resultSet.getString(2));
				tuyenTau.setKhoangCach(resultSet.getDouble(3));
				tuyenTau.setGaDi(resultSet.getString(4));
				tuyenTau.setGaDen(resultSet.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return tuyenTau;
	}
}
