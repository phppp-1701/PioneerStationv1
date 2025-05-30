package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Cho;
import entity.ToaTau;

public class Cho_DAO {
	public List<Cho> layDanhSachCho(boolean dongKetNoi){
		List<Cho> dsc = new ArrayList<Cho>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Cho";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Cho cho = new Cho();
				cho.setMaCho(resultSet.getString(1));
				cho.setSoThuTuCho(resultSet.getInt(2));
				ToaTau_DAO toaTau_DAO = new ToaTau_DAO();
				ToaTau toaTau = toaTau_DAO.timToaTheoMaToa(resultSet.getString(3), false);
				cho.setToaTau(toaTau);
				dsc.add(cho);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsc;
	}

	public Cho timChoTheoMaCho(String maCho, boolean dongKetNoi){
		Cho cho = new Cho();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Cho where maCho = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maCho);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				cho.setMaCho(resultSet.getString(1));
				cho.setSoThuTuCho(resultSet.getInt(2));
				ToaTau_DAO toaTau_DAO = new ToaTau_DAO();
				ToaTau toaTau = toaTau_DAO.timToaTheoMaToa(resultSet.getString(3), false);
				cho.setToaTau(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi){
			ConnectDB.getInstance().disconnect();
		}
		return cho;
	}
}
