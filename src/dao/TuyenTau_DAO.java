package dao;

import connectDB.ConnectDB;
import entity.TuyenTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TuyenTau_DAO {

    public List<TuyenTau> getDanhSachTuyenTauKhacMa(String maTuyen) {
        List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
        String sql = "SELECT * FROM TuyenTau WHERE maTuyenTau != ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, maTuyen);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TuyenTau tuyenTau = new TuyenTau(
                    rs.getString("maTuyenTau"),
                    rs.getString("tenTuyenTau"),
                    rs.getDouble("khoangCach"),
                    rs.getString("gaDi"),
                    rs.getString("gaDen")
                );
                danhSachTuyenTau.add(tuyenTau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachTuyenTau;
    }

    public TuyenTau timTuyenTauTheoMa(String maTuyen) {
        String sql = "SELECT * FROM TuyenTau WHERE maTuyenTau = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, maTuyen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TuyenTau(
                    rs.getString("maTuyenTau"),
                    rs.getString("tenTuyenTau"),
                    rs.getDouble("khoangCach"),
                    rs.getString("gaDi"),
                    rs.getString("gaDen")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getDanhSachGaDenTheoGaDi(String gaDi) {
        List<String> danhSachGaDen = new ArrayList<>();
        String sql = "SELECT DISTINCT gaDen FROM TuyenTau WHERE gaDi = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, gaDi);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                danhSachGaDen.add(rs.getString("gaDen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachGaDen;
    }

    public List<String> getDanhSachGaDiTheoGaDen(String gaDen) {
        List<String> danhSachGaDi = new ArrayList<>();
        String sql = "SELECT DISTINCT gaDi FROM TuyenTau WHERE gaDen = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, gaDen);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                danhSachGaDi.add(rs.getString("gaDi"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachGaDi;
    }

    public String getMaTuyenTheoGa(String gaDi, String gaDen) {
        String sql = "SELECT maTuyenTau FROM TuyenTau WHERE gaDi = ? AND gaDen = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, gaDi);
            pstmt.setString(2, gaDen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("maTuyenTau");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGaDiTheoMaTuyen(String maTuyen) {
        String sql = "SELECT gaDi FROM TuyenTau WHERE maTuyenTau = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, maTuyen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("gaDi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGaDenTheoMaTuyen(String maTuyen) {
        String sql = "SELECT gaDen FROM TuyenTau WHERE maTuyenTau = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, maTuyen);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("gaDen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
