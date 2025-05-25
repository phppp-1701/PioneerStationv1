package dao;

import entity.Ga;
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
				Ga_DAO ga_DAO = new Ga_DAO();
				Ga gaDi = ga_DAO.timGaTheoMa(resultSet.getString(4));
				Ga gaDen = ga_DAO.timGaTheoMa(resultSet.getString(5));
				tuyenTau.setGaDi(gaDi);
				tuyenTau.setGaDen(gaDen);
				dstt.add(tuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
	public TuyenTau timTuyenTauTheoMa(String maTuyenTau){
		TuyenTau tuyenTau = new TuyenTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from TuyenTau where maTuyenTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTuyenTau);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				tuyenTau.setMaTuyenTau(resultSet.getString(1));
				tuyenTau.setTenTuyenTau(resultSet.getString(2));
				tuyenTau.setKhoangCach(resultSet.getDouble(3));
				Ga_DAO ga_DAO = new Ga_DAO();
				Ga gaDi = ga_DAO.timGaTheoMa(resultSet.getString(4));
				Ga gaDen = ga_DAO.timGaTheoMa(resultSet.getString(5));
				tuyenTau.setGaDi(gaDi);
				tuyenTau.setGaDen(gaDen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return tuyenTau;
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
				Ga gaDii = timGaTheoMa(resultSet.getString(4));
				Ga gaDenn = timGaTheoMa(resultSet.getString(5));
				tuyenTau.setGaDi(gaDii);
				tuyenTau.setGaDen(gaDenn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuyenTau;
	}
	
	public Ga timGaTheoMa(String maGa){
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
		return ga;
	}
}
