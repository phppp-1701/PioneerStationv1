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
import entity.Ga;
import entity.Tau;
import entity.TuyenTau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;

public class ChuyenTau_DAO {
	public List<ChuyenTau> layTatCaChuyenTau() {
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
				Tau tau = timTauTheoMa(resultSet.getString(7));
				chuyenTau.setTau(tau);
				TuyenTau tuyenTau = timTuyenTauTheoMa(resultSet.getString(8));
				chuyenTau.setTuyenTau(tuyenTau);
				dsct.add(chuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsct;
	}
	
	public List<ChuyenTau> timChuyenTauTheoTuyenTauVaNgayKhoiHanh(String maTuyenTau, LocalDate ngayKhoiHanh) {
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
				Tau tau = timTauTheoMa(resultSet.getString(7));
				chuyenTau.setTau(tau);
				TuyenTau tuyenTau = timTuyenTauTheoMa(resultSet.getString(8));
				chuyenTau.setTuyenTau(tuyenTau);
				dsct.add(chuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsct;
	}
	
	public ChuyenTau timChuyenTauTheoMa(String ma) {
		ChuyenTau chuyenTau = new ChuyenTau();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "select * from chuyenTau where maChuyenTau = ?";
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
				Tau tau = timTauTheoMa(resultSet.getString(7));
				chuyenTau.setTau(tau);
				TuyenTau tuyenTau = timTuyenTauTheoMa(resultSet.getString(8));
				chuyenTau.setTuyenTau(tuyenTau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return chuyenTau;
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
				Ga gaDi = timGaTheoMa(resultSet.getString(4));
				Ga gaDen = timGaTheoMa(resultSet.getString(5));
				tuyenTau.setGaDi(gaDi);
				tuyenTau.setGaDen(gaDen);
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