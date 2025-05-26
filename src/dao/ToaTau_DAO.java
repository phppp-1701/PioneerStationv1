package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Tau;
import entity.ToaTau;
import entity.ToaTau.LoaiToa;

public class ToaTau_DAO {
	public List<ToaTau> layToanBoToa(boolean dongKetNoi){
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
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(3), false);
				toaTau.setTau(tau);
				toaTau.setLoaiToa(LoaiToa.valueOf(resultSet.getString(4)));
				toaTau.setSoHieuKhoang(resultSet.getInt(5));
				toaTau.setSoHieuTang(resultSet.getInt(6));
				toaTau.setSoLuongGiuong(resultSet.getInt(7));
				toaTau.setSoLuongGhe(resultSet.getInt(8));
				dstt.add(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
	
	public List<ToaTau> timToaTauTheoMaTau(String maTau, boolean dongKetNoi) {
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
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(3), false);
				toaTau.setTau(tau);
				toaTau.setLoaiToa(LoaiToa.valueOf(resultSet.getString(4)));
				toaTau.setSoHieuKhoang(resultSet.getInt(5));
				toaTau.setSoHieuTang(resultSet.getInt(6));
				toaTau.setSoLuongGiuong(resultSet.getInt(7));
				toaTau.setSoLuongGhe(resultSet.getInt(8));
				dstt.add(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dstt;
	}
	
	public ToaTau timToaTheoMaToa(String maToa, boolean dongKetNoi) {
		ToaTau toaTau = new ToaTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from ToaTau where maToaTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maToa);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				toaTau.setMaToaTau(resultSet.getString(1));
				toaTau.setThuTuToa(resultSet.getInt(2));
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(3), false);
				toaTau.setTau(tau);
				toaTau.setLoaiToa(LoaiToa.valueOf(resultSet.getString(4)));
				toaTau.setSoHieuKhoang(resultSet.getInt(5));
				toaTau.setSoHieuTang(resultSet.getInt(6));
				toaTau.setSoLuongGiuong(resultSet.getInt(7));
				toaTau.setSoLuongGhe(resultSet.getInt(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return toaTau;
	}
}
