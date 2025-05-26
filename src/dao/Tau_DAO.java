package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import connectDB.ConnectDB;
import entity.Tau;
import entity.Tau.LoaiTau;
import entity.Tau.TrangThaiTau;

public class Tau_DAO {
	public List<Tau> layToanBoDanhSach() {
		List<Tau> dst = new ArrayList<Tau>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from tau";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Tau tau = new Tau();
				tau.setMaTau(resultSet.getString(1));
				tau.setTenTau(resultSet.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(resultSet.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(resultSet.getString(4)));
				dst.add(tau);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return dst;
	}
	
	public Tau timTauTheoMa(String maTau) {
		Tau tau = new Tau();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "select * from tau where maTau like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, maTau);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				tau.setMaTau(rs.getString(1));
				tau.setTenTau(rs.getString(2));
				tau.setTrangThaiTau(TrangThaiTau.valueOf(rs.getString(3)));
				tau.setLoaiTau(LoaiTau.valueOf(rs.getString(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectDB.getInstance().disconnect();
		}
		return tau;		
	}
	
	public List<Tau> timTauTheoTen(String tenTau) {
	    List<Tau> dsTau = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {

	        con = ConnectDB.getConnection();
	        String sql = "SELECT * FROM Tau WHERE tenTau LIKE ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, "%" + tenTau.trim() + "%");
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Tau tau = new Tau();
	            tau.setMaTau(rs.getString("maTau"));
	            tau.setTenTau(rs.getString("tenTau"));
	            tau.setTrangThaiTau(TrangThaiTau.valueOf(rs.getString("trangThaiTau")));
	            tau.setLoaiTau(LoaiTau.valueOf(rs.getString("loaiTau")));
	            dsTau.add(tau);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	       
	    } finally {
	       ConnectDB.getInstance().disconnect();
	    }
	    return dsTau;
	}
	
	public boolean themTau(Tau tau) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT INTO Tau (maTau, tenTau, trangThaiTau, LoaiTau) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tau.getMaTau());
            stmt.setString(2, tau.getTenTau());
            stmt.setString(3, tau.getTrangThaiTau().toString());
            stmt.setString(4, tau.getLoaiTau().toString());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean capNhatTau(Tau tau) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "UPDATE Tau SET tenTau = ?, trangThaiTau = ?, loaiTau = ? WHERE maTau = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tau.getTenTau());
            stmt.setString(2, tau.getTrangThaiTau().toString());
            stmt.setString(3, tau.getLoaiTau().toString());
            stmt.setString(4, tau.getMaTau());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public String taoMaTauMoi(String vietTatTau) {
	    String maTauMoi = "";
	    try (Connection con = ConnectDB.getConnection()) {
	        int namHienTai = Calendar.getInstance().get(Calendar.YEAR);
	        String prefix = String.valueOf(namHienTai) + vietTatTau;

	        String sql = "SELECT MAX(maTau) FROM Tau WHERE maTau LIKE ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, prefix + "%");
	        ResultSet rs = stmt.executeQuery();

	        int soThuTu = 0;
	        if (rs.next() && rs.getString(1) != null) {
	            String maTauMax = rs.getString(1);
	            String soThuTuStr = maTauMax.substring(prefix.length());
	            soThuTu = Integer.parseInt(soThuTuStr);
	        }

	        soThuTu++;
	        maTauMoi = prefix + String.format("%02d", soThuTu);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return maTauMoi;
	}
}
