package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ga;

public class Ga_DAO {
	public List<Ga> layToanBoGa() {
		List<Ga> dsga = new ArrayList<Ga>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Ga";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Ga ga = new Ga();
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
				dsga.add(ga);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsga;
	}

	public List<Ga> timGaKhacMa(String maGa) {
		List<Ga> dsga = new ArrayList<Ga>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "Select * from Ga where maGa != ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maGa);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Ga ga = new Ga();
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
				dsga.add(ga);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsga;
	}

	public Ga timGaTheoMa(String maGa) {
		Ga ga = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Ga where maGa = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maGa);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ga = new Ga();
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return ga;
	}
}