package dao;

import connectDB.ConnectDB;
import entity.Cho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cho_DAO {

    /**
     * Tìm một chỗ theo mã chỗ
     * @param maCho Mã của chỗ cần tìm
     * @return Đối tượng Cho nếu tìm thấy, null nếu không tìm thấy
     * @throws SQLException 
     */
    public Cho timChoTheoMa(String maCho) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cho cho = null;

        try {
            String sql = "SELECT * FROM Cho WHERE maCho = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maCho);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int soThuTuCho = rs.getInt("soThuTuCho");
                String maToaTau = rs.getString("maToaTau");
                cho = new Cho(maCho, soThuTuCho, maToaTau);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm chỗ: " + maCho);
            e.printStackTrace();
        } finally {
            dongKetNoi(rs, stmt);
        }
        return cho;
    }

    /**
     * Lấy danh sách tất cả chỗ của một toa tàu
     * @param maToaTau Mã toa tàu
     * @return Danh sách các chỗ, trả về danh sách rỗng nếu không tìm thấy
     * @throws SQLException 
     */
    public List<Cho> layDanhSachChoTheoToa(String maToaTau) throws SQLException {
        List<Cho> danhSachCho = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM Cho WHERE maToaTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToaTau);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String maCho = rs.getString("maCho");
                int soThuTuCho = rs.getInt("soThuTuCho");
                Cho cho = new Cho(maCho, soThuTuCho, maToaTau);
                danhSachCho.add(cho);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách chỗ của toa: " + maToaTau);
            e.printStackTrace();
        } finally {
            dongKetNoi(rs, stmt);
        }
        return danhSachCho;
    }

    /**
     * Thêm một chỗ mới vào cơ sở dữ liệu
     * @param cho Đối tượng chỗ cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     * @throws SQLException 
     */
    public boolean themCho(Cho cho) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO Cho (maCho, soThuTuCho, maToaTau) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cho.getMaCho());
            stmt.setInt(2, cho.getSoThuTuCho());
            stmt.setString(3, cho.getMaToaTau());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chỗ: " + cho.getMaCho());
            e.printStackTrace();
            return false;
        } finally {
            dongKetNoi(null, stmt);
        }
    }

    /**
     * Cập nhật thông tin của một chỗ
     * @param cho Đối tượng chỗ với thông tin mới
     * @return true nếu cập nhật thành công, false nếu thất bại
     * @throws SQLException 
     */
    public boolean capNhatCho(Cho cho) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Cho SET soThuTuCho = ?, maToaTau = ? WHERE maCho = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, cho.getSoThuTuCho());
            stmt.setString(2, cho.getMaToaTau());
            stmt.setString(3, cho.getMaCho());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chỗ: " + cho.getMaCho());
            e.printStackTrace();
            return false;
        } finally {
            dongKetNoi(null, stmt);
        }
    }

    /**
     * Xóa một chỗ theo mã chỗ
     * @param maCho Mã của chỗ cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     * @throws SQLException 
     */
    public boolean xoaCho(String maCho) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM Cho WHERE maCho = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maCho);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chỗ: " + maCho);
            e.printStackTrace();
            return false;
        } finally {
            dongKetNoi(null, stmt);
        }
    }

    /**
     * Đếm số chỗ của một toa tàu
     * @param maToaTau Mã toa tàu
     * @return Số lượng chỗ, trả về -1 nếu có lỗi
     * @throws SQLException 
     */
    public int demSoChoTheoToa(String maToaTau) throws SQLException {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) AS soLuong FROM Cho WHERE maToaTau = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maToaTau);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đếm số chỗ của toa: " + maToaTau);
            e.printStackTrace();
            return -1;
        } finally {
            dongKetNoi(rs, stmt);
        }
        return -1;
    }

    /**
     * Đóng kết nối ResultSet và PreparedStatement
     * @param rs ResultSet cần đóng
     * @param stmt PreparedStatement cần đóng
     */
    private void dongKetNoi(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối");
            e.printStackTrace();
        }
    }
}