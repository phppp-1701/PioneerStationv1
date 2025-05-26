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
				ToaTau toaTau = toaTau_DAO.timToaTauTheoMaToa(resultSet.getString(3), false);
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
	
	public Cho timChoTheoMaCho(String maCho, boolean dongKetNoi) {
	    Cho cho = null;
	    Connection con = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        con = ConnectDB.getConnection();
	        String sql = "SELECT * FROM Cho WHERE maCho = ?";
	        preparedStatement = con.prepareStatement(sql);
	        preparedStatement.setString(1, maCho);
	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            cho = new Cho();
	            cho.setMaCho(resultSet.getString("maCho"));
	            cho.setSoThuTuCho(resultSet.getInt("soThuTuCho"));

	            String maToa = resultSet.getString("maToaTau");
	            // Gọi DAO khác nhưng truyền false để không bị disconnect sớm
	            ToaTau toaTau = new ToaTau_DAO().timToaTauTheoMaToa(maToa, false);
	            cho.setToaTau(toaTau);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	    if(dongKetNoi) {
	    	ConnectDB.getInstance().disconnect();
	    }

	    return cho;
	}


	
	public List<Cho> danhSachChoCoToa(ToaTau toa) {
		List<Cho> dsc = new ArrayList<Cho>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from Cho where maToaTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, toa.getMaToaTau());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Cho cho = new Cho();
				cho.setMaCho(resultSet.getString(1));
				cho.setSoThuTuCho(resultSet.getInt(2));
				cho.setToaTau(toa);
				dsc.add(cho);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsc;
	}
}
