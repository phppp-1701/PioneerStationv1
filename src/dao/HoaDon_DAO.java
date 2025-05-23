package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.HoaDon.PhuongThucThanhToan;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    // Thêm hóa đơn mới
    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, " +
                     "thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, ngayLamViec, maCaLam, maNhanVien) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, hd.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hd.getNgayTaoHoaDon()));
            stmt.setTime(3, Time.valueOf(hd.getGioTaoHoaDon()));
            stmt.setString(4, hd.getPhuongThucThanhToan().toString());
            stmt.setDouble(5, hd.getPhanTramGiamGia());
            stmt.setDouble(6, hd.getThanhTien());
            stmt.setDouble(7, hd.getTienKhachDua());
            stmt.setDouble(8, hd.getTienTraLai());
            stmt.setString(9, hd.getMaKhachHang());
            stmt.setString(10, hd.getMaKhuyenMai());
            stmt.setDate(11, Date.valueOf(hd.getNgayLamViec()));
            stmt.setString(12, hd.getMaCaLam());
            stmt.setString(13, hd.getMaNhanVien());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm hóa đơn theo mã
    public HoaDon timHoaDonTheoMa(String maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return taoHoaDonTuResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật hóa đơn
    public boolean capNhatHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET ngayTaoHoaDon = ?, gioTaoHoaDon = ?, phuongThucThanhToan = ?, phanTramGiamGia = ?, " +
                     "thanhTien = ?, tienKhachDua = ?, tienTraLai = ?, maKhachHang = ?, maKhuyenMai = ?, " +
                     "ngayLamViec = ?, maCaLam = ?, maNhanVien = ? WHERE maHoaDon = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDate(1, Date.valueOf(hd.getNgayTaoHoaDon()));
            stmt.setTime(2, Time.valueOf(hd.getGioTaoHoaDon()));
            stmt.setString(3, hd.getPhuongThucThanhToan().toString());
            stmt.setDouble(4, hd.getPhanTramGiamGia());
            stmt.setDouble(5, hd.getThanhTien());
            stmt.setDouble(6, hd.getTienKhachDua());
            stmt.setDouble(7, hd.getTienTraLai());
            stmt.setString(8, hd.getMaKhachHang());
            stmt.setString(9, hd.getMaKhuyenMai());
            stmt.setDate(10, Date.valueOf(hd.getNgayLamViec()));
            stmt.setString(11, hd.getMaCaLam());
            stmt.setString(12, hd.getMaNhanVien());
            stmt.setString(13, hd.getMaHoaDon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa hóa đơn
    public boolean xoaHoaDon(String maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, maHoaDon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả hóa đơn
    public List<HoaDon> layDanhSachHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (
            Connection conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                ds.add(taoHoaDonTuResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    // Tạo hóa đơn từ ResultSet
    private HoaDon taoHoaDonTuResultSet(ResultSet rs) throws SQLException {
        return new HoaDon(
            rs.getString("maHoaDon"),
            rs.getDate("ngayTaoHoaDon").toLocalDate(),
            rs.getTime("gioTaoHoaDon").toLocalTime(),
            PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")),
            rs.getDouble("phanTramGiamGia"),
            rs.getDouble("thanhTien"),
            rs.getDouble("tienKhachDua"),
            rs.getDouble("tienTraLai"),
            rs.getString("maKhachHang"),
            rs.getString("maKhuyenMai"),
            rs.getDate("ngayLamViec").toLocalDate(),
            rs.getString("maCaLam"),
            rs.getString("maNhanVien")
        );
    }
    public String taoMaHoaDonMoi() {
        String ngay = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = ngay + "HD";
        String sql = "SELECT MAX(maHoaDon) FROM HoaDon WHERE maHoaDon LIKE ?";
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();
            int stt = 1;
            if (rs.next() && rs.getString(1) != null) {
                String maMax = rs.getString(1);
                stt = Integer.parseInt(maMax.substring(maMax.length() - 6)) + 1;
            }
            return prefix + String.format("%06d", stt);
        } catch (SQLException e) {
            e.printStackTrace();
            return prefix + "000001";
        }
    }
}
