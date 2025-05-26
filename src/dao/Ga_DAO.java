package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ga;

public class Ga_DAO {
	public List<Ga> layToanBoGa(boolean dongKetNoi){
		List<Ga> dsga = new ArrayList<Ga>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Ga";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Ga ga = new Ga();
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
				dsga.add(ga);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsga;
	}
	
	public List<Ga> timGaKhacMa(String maGa, boolean dongKetNoi){
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
			while(resultSet.next()) {
				Ga ga = new Ga();
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
				dsga.add(ga);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsga;
	}
	
	public Ga timGaTheoMa(String maGa, boolean dongKetNoi){
		Ga ga = new Ga();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "Select * from Ga where maGa != ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maGa);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ga.setMaGa(resultSet.getString(1));
				ga.setTenGa(resultSet.getString(2));
				ga.setDiaChi(resultSet.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return ga;
	}
}