package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	public List<TaiKhoan> layToanBoTaiKhoan(){
		List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
		
		return dstk;
	}
	// Kiểm tra tài khoản theo mã nhân viên
    public boolean kiemTraTaiKhoanTheoMaNhanVien(String maNhanVien) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE maNhanVien = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNhanVien);
            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;
    }
}	
