package dao;

import entity.ChiTietCho;
import entity.ChiTietCho.TrangThaiCho;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;
import entity.ToaTau.LoaiToa;
import entity.Cho;
import entity.ChuyenTau;
import entity.Ga;
import entity.Tau;
import entity.ToaTau;
import entity.TuyenTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class ChiTietCho_DAO {

	// Lấy tất cả danh sách ChiTietCho
	public List<ChiTietCho> layTatCaChiTietCho() {
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
				Cho cho = timChoTheoMaCho(resultSet.getString("maCho"));
				chiTietCho.setCho(cho);
				ChuyenTau chuyenTau = timChuyenTauTheoMa(resultSet.getString("maChuyenTau"));
				chiTietCho.setChuyenTau(chuyenTau);
				chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
				chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
				danhSachChiTietCho.add(chiTietCho);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachChiTietCho;
	}

	public ChiTietCho timChiTietChoTheoMaVe(String maVe) {
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
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"));
				chiTietCho.setCho(cho);
				ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
				ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"));
				chiTietCho.setChuyenTau(chuyenTau);
                chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
                chiTietCho.setGiaCho(resultSet.getDouble("giaCho"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
				Cho cho = timChoTheoMaCho(resultSet.getString(1));
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
		}
		return dsctc;
	}
	
	public int tinhSoChoConLaiCuaToa(ToaTau toaTau, ChuyenTau chuyenTau) {
		int soCho = 0;
		List<ChiTietCho> dsc = danhSachChoConTrongCuaChuyenTau(toaTau, chuyenTau);
		soCho = dsc.size();
		return soCho;
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
		}
		return chuyenTau;
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
				Ga_DAO ga_DAO = new Ga_DAO();
				Ga gaDi = ga_DAO.timGaTheoMa(resultSet.getString(4));
				Ga gaDen = ga_DAO.timGaTheoMa(resultSet.getString(5));
				tuyenTau.setGaDi(gaDi);
				tuyenTau.setGaDen(gaDen);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuyenTau;
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
