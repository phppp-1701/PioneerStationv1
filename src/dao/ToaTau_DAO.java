package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSetInternal;

import connectDB.ConnectDB;
import entity.Tau;
import entity.Tau.LoaiTau;
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
				toaTau.setSoLuongChoDaBan(resultSet.getInt(3));
				toaTau.setSoLuongChoDangDat(resultSet.getInt(4));
				toaTau.setSoLuongChoConTrong(resultSet.getInt(5));
				Tau_DAO tau_DAO = new Tau_DAO();
                Tau tau = tau_DAO.timTauTheoMa(resultSet.getString("maTau"), false);
                toaTau.setTau(tau);
				dstt.add(toaTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            if (dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
        }
		return dstt;
	}
	
	public ToaTau timToaTauTheoMaToa(String maToa, boolean dongKetNoi) {
		ToaTau dstt = new ToaTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from ToaTau where maTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maToa);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ToaTau toaTau = new ToaTau();
				toaTau.setMaToaTau(resultSet.getString(1));
				toaTau.setThuTuToa(resultSet.getInt(2));
				Tau tau = new Tau_DAO().timTauTheoMa(resultSet.getString("maTau"), false);
				String loaiToa = resultSet.getString("loaiToa");
				if (loaiToa.equals("ngoiMem")) {
			        toaTau.setLoaiToa(LoaiToa.ngoiMem);
			    } else {
			        toaTau.setLoaiToa(LoaiToa.giuongNam);
			    }     
				toaTau.setSoHieuKhoang(resultSet.getInt(5));
				toaTau.setSoHieuTang(resultSet.getInt(6));
				toaTau.setSoLuongGiuong(resultSet.getInt(7));
				toaTau.setSoLuongGhe(resultSet.getInt(8));
                toaTau.setTau(tau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            if (dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
        }
		return dstt;
	}
}
