package dao;

import connectDB.ConnectDB;
import entity.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoan_DAO {

    /**
     * Thêm tài khoản mới vào cơ sở dữ liệu
     */
    public boolean themTaiKhoan(TaiKhoan tk) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tk.getTenTaiKhoan());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getNhanVien().getMaNhanVien());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cập nhật mật khẩu cho tài khoản
     */
    public boolean capNhatMatKhau(TaiKhoan tk) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tk.getMatKhau());
            stmt.setString(2, tk.getTenTaiKhoan());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tìm tài khoản theo tên đăng nhập
     */
    public TaiKhoan timTaiKhoanTheoTen(String tenTK) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TaiKhoan tk = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenTK);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
                tk.setTenTaiKhoan(rs.getString("tenTaiKhoan"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setNhanVien(rs.getString(""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tk;
    }

    /**
     * Kiểm tra tài khoản có tồn tại không (theo tên đăng nhập)
     */
    public boolean kiemTraTonTaiTaiKhoan(String tenTaiKhoan) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenTaiKhoan);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Kiểm tra đăng nhập (so sánh mật khẩu)
     */
    public boolean kiemTraDangNhap(String tenTaiKhoan, String matKhauNhap) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT matKhau FROM TaiKhoan WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenTaiKhoan);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String matKhauDB = rs.getString("matKhau");
                return matKhauDB.equals(matKhauNhap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }

        return false;
    }

    /**
     * Tìm tài khoản theo mã nhân viên
     */
    public TaiKhoan timTaiKhoanTheoMaNhanVien(String maNV) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TaiKhoan tk = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE maNhanVien = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan();
                tk.setTenTaiKhoan(rs.getString("tenTaiKhoan"));
                tk.setMatKhau(rs.getString("matKhau"));
                tk.setMaNhanVien(rs.getString("maNhanVien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }

        return tk;
    }

    /**
     * Lấy mã nhân viên từ tên tài khoản
     */
    public String timMaNhanVienTheoTenTaiKhoan(String tenTaiKhoan) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String maNV = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT maNhanVien FROM TaiKhoan WHERE tenTaiKhoan = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, tenTaiKhoan);
            rs = stmt.executeQuery();

            if (rs.next()) {
                maNV = rs.getString("maNhanVien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }

        return maNV;
    }

    /**
     * Kiểm tra đã tồn tại tài khoản theo mã nhân viên
     */
    public boolean kiemTraTaiKhoanTheoMaNhanVien(String maNV) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) FROM TaiKhoan WHERE maNhanVien = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }

        return false;
    }
}
