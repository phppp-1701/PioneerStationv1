package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;

public class NhanVien_DAO {
	public List<NhanVien> xuatToanBoDanhSachNhanVien() {
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from NhanVien";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				NhanVien nv = new NhanVien();
				nv.setMaNhanVien(resultSet.getString(1));
				nv.setTenNhanVien(resultSet.getString(2));
				nv.setCccd_HoChieu(resultSet.getString(3));
				nv.setSoDienThoai(resultSet.getString(4));
				Date ngaySinhDate = resultSet.getDate(5);
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate():null);
				ChucVu chucVu = ChucVu.valueOf(resultSet.getString(6));
				GioiTinh gioiTinh = GioiTinh.valueOf(resultSet.getString(7));
				nv.setChucVu(chucVu);
				nv.setGioiTinh(gioiTinh);
				nv.setUrlAnh(resultSet.getString(8));
				TrangThaiNhanVien trangThaiNhanVien = TrangThaiNhanVien.valueOf(resultSet.getString(9));
				nv.setTrangThaiNhanVien(trangThaiNhanVien);
				nv.setEmail(resultSet.getString(10));
				dsnv.add(nv);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsnv;
	}
	
	public NhanVien timNhanVienTheoMa(String maNV) {
		NhanVien nv = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from NhanVien where maNhanVien = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maNV);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				nv = new NhanVien();
				nv.setMaNhanVien(resultSet.getString(1));
				nv.setTenNhanVien(resultSet.getString(2));
				nv.setCccd_HoChieu(resultSet.getString(3));
				nv.setSoDienThoai(resultSet.getString(4));
				Date ngaySinhDate = resultSet.getDate(5);
				nv.setNgaySinh(ngaySinhDate != null ? ngaySinhDate.toLocalDate():null);
				ChucVu chucVu = ChucVu.valueOf(resultSet.getString(6));
				GioiTinh gioiTinh = GioiTinh.valueOf(resultSet.getString(7));
				nv.setChucVu(chucVu);
				nv.setGioiTinh(gioiTinh);
				nv.setUrlAnh(resultSet.getString(8));
				TrangThaiNhanVien trangThaiNhanVien = TrangThaiNhanVien.valueOf(resultSet.getString(9));
				nv.setTrangThaiNhanVien(trangThaiNhanVien);
				nv.setEmail(resultSet.getString(10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return nv;
	}
}
