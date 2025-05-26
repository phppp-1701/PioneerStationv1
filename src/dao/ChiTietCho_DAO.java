package dao;

import entity.ChiTietCho;
import entity.ChiTietCho.TrangThaiCho;
import entity.Cho;
import entity.ChuyenTau;
import entity.ToaTau;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;
public class ChiTietCho_DAO {

	// Lấy tất cả danh sách ChiTietCho
	public List<ChiTietCho> layTatCaChiTietCho(boolean dongKetNoi) {
		List<ChiTietCho> danhSachChiTietCho = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM ChiTietCho";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ChiTietCho chiTietCho = new ChiTietCho();
				Cho_DAO cho_DAO = new Cho_DAO();
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false);
				chiTietCho.setCho(cho);
				ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
				ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);
				chiTietCho.setChuyenTau(chuyenTau);
				chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
				chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
				danhSachChiTietCho.add(chiTietCho);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachChiTietCho;
	}

	public ChiTietCho timChiTietChoTheoMaVe(String maVe, boolean dongKetNoi) {
        ChiTietCho chiTietCho = null;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM ChiTietCho WHERE maVe = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, maVe);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chiTietCho = new ChiTietCho();
                Cho_DAO cho_DAO = new Cho_DAO();
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false);
				chiTietCho.setCho(cho);
				ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
				ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);
				chiTietCho.setChuyenTau(chuyenTau);
                chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
                chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return chiTietCho;
    }
	
	public List<ChiTietCho> danhSachChoConTrongCuaChuyenTau(ToaTau toaTau,ChuyenTau chuyenTau){
		List<ChiTietCho> dsctc = new ArrayList<ChiTietCho>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from ChiTietCho where maChuyenTau = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, chuyenTau.getMaChuyenTau());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Cho_DAO cho_DAO = new Cho_DAO();
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString(1), false);
				if(cho.getToaTau().equals(toaTau) && resultSet.getString(3).equals(String.valueOf(TrangThaiCho.conTrong))) {
					ChiTietCho chiTietCho = new ChiTietCho();
					chiTietCho.setCho(cho);
					chiTietCho.setChuyenTau(chuyenTau);
					chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
					chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
					dsctc.add(chiTietCho);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dsctc;
	}
	
	public int tinhSoChoConLaiCuaToa(ToaTau toaTau, ChuyenTau chuyenTau) {
		int soCho = 0;
		List<ChiTietCho> dsc = danhSachChoConTrongCuaChuyenTau(toaTau, chuyenTau);
		soCho = dsc.size();
		return soCho;
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
		}
		return dsc;
	}
	
	public double tinhGiaThapNhatCuaToa(ToaTau toaTau, ChuyenTau ct) {
		double giaMin = 0;
		List<ChiTietCho> dsct = danhSachChoConTrongCuaChuyenTau(toaTau, ct);
		for(ChiTietCho chiTiet : dsct) {
			if(giaMin == 0) {
				giaMin = chiTiet.getGiaCho();
			}else {
				if(giaMin>chiTiet.getGiaCho()) {
					giaMin = chiTiet.getGiaCho();
				}
			}
		}
		return giaMin;
	}
}
