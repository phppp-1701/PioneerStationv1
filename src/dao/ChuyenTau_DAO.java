package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.Tau;
import entity.TuyenTau;

public class ChuyenTau_DAO {
	public List<ChuyenTau> layTatCaChuyenTau(boolean dongKetNoi) {
		List<ChuyenTau> dsct = new ArrayList<ChuyenTau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from chuyenTau";
			con = ConnectDB.getConnection();
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ChuyenTau chuyenTau = new ChuyenTau();
				chuyenTau.setMaChuyenTau(resultSet.getString(1));
				chuyenTau.setNgayKhoiHanh(resultSet.getDate(2).toLocalDate());
				chuyenTau.setGioKhoiHanh(resultSet.getTime(3).toLocalTime());
				chuyenTau.setNgayDuKien(resultSet.getDate(4).toLocalDate());
				chuyenTau.setGioDuKien(resultSet.getTime(5).toLocalTime());
				chuyenTau.setTrangThaiChuyenTau(ChuyenTau.TrangThaiChuyenTau.valueOf(resultSet.getString(6)));
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(7), false);
				chuyenTau.setTau(tau);
				TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
				TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheoMa(resultSet.getString(8), false);
				chuyenTau.setTuyenTau(tuyenTau);
				dsct.add(chuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsct;
	}
	
	public List<ChuyenTau> timChuyenTauTheoTuyenTauVaNgayKhoiHanh(String maTuyenTau, LocalDate ngayKhoiHanh, boolean dongKetNoi) {
		List<ChuyenTau> dsct = new ArrayList<ChuyenTau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from chuyenTau where maTuyenTau = ? and ngayKhoiHanh = ?";
			con = ConnectDB.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maTuyenTau);
			preparedStatement.setDate(2, Date.valueOf(ngayKhoiHanh));
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ChuyenTau chuyenTau = new ChuyenTau();
				chuyenTau.setMaChuyenTau(resultSet.getString(1));
				chuyenTau.setNgayKhoiHanh(resultSet.getDate(2).toLocalDate());
				chuyenTau.setGioKhoiHanh(resultSet.getTime(3).toLocalTime());
				chuyenTau.setNgayDuKien(resultSet.getDate(4).toLocalDate());
				chuyenTau.setGioDuKien(resultSet.getTime(5).toLocalTime());
				chuyenTau.setTrangThaiChuyenTau(ChuyenTau.TrangThaiChuyenTau.valueOf(resultSet.getString(6)));
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(7), false);
				chuyenTau.setTau(tau);
				TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
				TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheoMa(resultSet.getString(8), false);
				chuyenTau.setTuyenTau(tuyenTau);
				dsct.add(chuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dsct;
	}
	
	public ChuyenTau timChuyenTauTheoMa(String ma, boolean dongKetNoi) {
		ChuyenTau chuyenTau = new ChuyenTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from ChuyenTau where maChuyenTau = ?";
			con = ConnectDB.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, ma);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				chuyenTau.setMaChuyenTau(resultSet.getString(1));
				chuyenTau.setNgayKhoiHanh(resultSet.getDate(2).toLocalDate());
				chuyenTau.setGioKhoiHanh(resultSet.getTime(3).toLocalTime());
				chuyenTau.setNgayDuKien(resultSet.getDate(4).toLocalDate());
				chuyenTau.setGioDuKien(resultSet.getTime(5).toLocalTime());
				chuyenTau.setTrangThaiChuyenTau(ChuyenTau.TrangThaiChuyenTau.valueOf(resultSet.getString(6)));
				Tau_DAO tau_DAO = new Tau_DAO();
				Tau tau = tau_DAO.timTauTheoMa(resultSet.getString(7), false);
				chuyenTau.setTau(tau);
				TuyenTau_DAO tuyenTau_DAO = new TuyenTau_DAO();
				TuyenTau tuyenTau = tuyenTau_DAO.timTuyenTauTheoMa(resultSet.getString(8), false);
				chuyenTau.setTuyenTau(tuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return chuyenTau;
	}
}