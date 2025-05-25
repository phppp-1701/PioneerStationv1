package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Cho;
import entity.Tau;
import entity.ToaTau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;
import entity.ToaTau.LoaiToa;

public class Cho_DAO {
	public List<Cho> layDanhSachCho(){
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
				cho.setToaTau(timToaTheoMaToa(resultSet.getString(3)));
				dsc.add(cho);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsc;
	}

	public Cho timChoTheoMaCho(String maCho){
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
				cho.setToaTau(timToaTheoMaToa(resultSet.getString(3)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return cho;
	}
	
	public ToaTau timToaTheoMaToa(String maToa) {
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
				Tau tau = timTauTheoMa(resultSet.getString(3));
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
		return toaTau;
	}
	
	public Tau timTauTheoMa(String maTau) {
		Tau tau = new Tau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from tau where maTau like ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTau);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				tau.setMaTau(resultSet.getString(1));
				tau.setTenTau(resultSet.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(resultSet.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(resultSet.getString(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tau;		
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
