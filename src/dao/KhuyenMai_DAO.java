package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang.LoaiKhachHang;
import entity.KhuyenMai;

public class KhuyenMai_DAO {
	public List<KhuyenMai> layToanBoKhuyenMai(boolean dongKetNoi){
		List<KhuyenMai> dskm = new ArrayList<KhuyenMai>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from KhuyenMai";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				KhuyenMai km = new KhuyenMai();
				km.setMaKhuyenMai(resultSet.getString(1));
				km.setTenKhuyenMai(resultSet.getString(2));
				km.setNgayBatDauSuKien(resultSet.getDate(3).toLocalDate());
				km.setNgayKetThucSuKien(resultSet.getDate(4).toLocalDate());
				km.setLoaiKhachHang(LoaiKhachHang.valueOf(resultSet.getString(5)));
				km.setPhanTramGiamGiaSuKien(resultSet.getDouble(6));
				dskm.add(km);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return dskm;
	}
	
	public KhuyenMai timKhuyenMaiTheoMa(String maKhuyenMai, boolean dongKetNoi){
		KhuyenMai km = new KhuyenMai();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from KhuyenMai where maKhuyenMai = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maKhuyenMai);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				km.setMaKhuyenMai(resultSet.getString(1));
				km.setTenKhuyenMai(resultSet.getString(2));
				km.setNgayBatDauSuKien(resultSet.getDate(3).toLocalDate());
				km.setNgayKetThucSuKien(resultSet.getDate(4).toLocalDate());
				km.setLoaiKhachHang(LoaiKhachHang.valueOf(resultSet.getString(5)));
				km.setPhanTramGiamGiaSuKien(resultSet.getDouble(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return km;
	}
	
	public List<KhuyenMai> timKhuyenMaiTheoLoaiKhachHang(LoaiKhachHang loai, boolean dongKetNoi) {
		List<KhuyenMai> danhSachKhuyenMai = new ArrayList<KhuyenMai>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT *\r\n"
					+ "FROM KhuyenMai\r\n"
					+ "WHERE loaiKhachHang = ?\r\n"
					+ "ORDER BY phanTramGiamGiaSuKien DESC;";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, loai.name());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				KhuyenMai km = new KhuyenMai(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), loai, resultSet.getDouble(6));
				danhSachKhuyenMai.add(km);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachKhuyenMai;
	}
}
