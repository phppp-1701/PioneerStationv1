package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import connectDB.ConnectDB;
import entity.ChuyenTau;
import entity.Tau;
import entity.Tau.TrangThaiTau;
import entity.ChuyenTau.TrangThaiChuyenTau;

public class ChuyenTau_DAO {
    public List<ChuyenTau> timChuyenTau(String maGaDi, String maGaDen, Date ngayKhoiHanh) {
        List<ChuyenTau> danhSachChuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }

            StringBuilder sql = new StringBuilder(
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, ct.trangThaiChuyenTau, " +
                "ct.maTau, ct.maTuyenTau, t.tenTau, t.trangThaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau " +
                "INNER JOIN Ga g1 ON tt.gaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.gaDen = g2.maGa " +
                "WHERE 1=1"
            );

            List<Object> params = new ArrayList<>();

            if (maGaDi != null) {
                sql.append(" AND tt.gaDi = ?");
                params.add(maGaDi);
            }
            if (maGaDen != null) {
                sql.append(" AND tt.gaDen = ?");
                params.add(maGaDen);
            }
            if (ngayKhoiHanh != null) {
                sql.append(" AND ct.ngayKhoiHanh = ?");
                params.add(ngayKhoiHanh);
            }

            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ChuyenTau chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh"));
                chuyenTau.setGioKhoiHanh(rs.getTime("gioKhoiHanh") != null ? rs.getTime("gioKhoiHanh").toLocalTime() : null);
                chuyenTau.setNgayDuKien(rs.getDate("ngayDuKien"));
                chuyenTau.setGioDuKien(rs.getTime("gioDuKien") != null ? rs.getTime("gioDuKien").toLocalTime() : null);
                chuyenTau.setTrangThaiChuyenTau(rs.getString("trangThaiChuyenTau") != null ? TrangThaiChuyenTau.valueOf(rs.getString("trangThaiChuyenTau")) : null);
                chuyenTau.setMaTau(rs.getString("maTau"));
                chuyenTau.setMaTuyenTau(rs.getString("maTuyenTau"));

                danhSachChuyenTau.add(chuyenTau);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timChuyenTau: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return danhSachChuyenTau;
    }

    public List<ChuyenTau> timChuyenTauTheoTenGaVaNgay(String tenGaDi, String tenGaDen, Date ngayKhoiHanh) {
        List<ChuyenTau> danhSachChuyenTau = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }

            StringBuilder sql = new StringBuilder(
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, ct.trangThaiChuyenTau, " +
                "ct.maTau, ct.maTuyenTau, t.tenTau, t.trangThaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau " +
                "INNER JOIN Ga g1 ON tt.gaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.gaDen = g2.maGa " +
                "WHERE t.trangThaiTau = ?"
            );

            List<Object> params = new ArrayList<>();
            params.add(TrangThaiTau.hoatDong.name());

            if (tenGaDi != null && !tenGaDi.isEmpty()) {
                sql.append(" AND g1.tenGa LIKE ?");
                params.add("%" + tenGaDi + "%");
            }
            
            if (tenGaDen != null && !tenGaDen.isEmpty()) {
                sql.append(" AND g2.tenGa LIKE ?");
                params.add("%" + tenGaDen + "%");
            }
            
            if (ngayKhoiHanh != null) {
                sql.append(" AND ct.ngayKhoiHanh = ?");
                params.add(ngayKhoiHanh);
            }

            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ChuyenTau chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh"));
                chuyenTau.setGioKhoiHanh(rs.getTime("gioKhoiHanh") != null ? rs.getTime("gioKhoiHanh").toLocalTime() : null);
                chuyenTau.setNgayDuKien(rs.getDate("ngayDuKien"));
                chuyenTau.setGioDuKien(rs.getTime("gioDuKien") != null ? rs.getTime("gioDuKien").toLocalTime() : null);
                chuyenTau.setTrangThaiChuyenTau(rs.getString("trangThaiChuyenTau") != null ? TrangThaiChuyenTau.valueOf(rs.getString("trangThaiChuyenTau")) : null);
                chuyenTau.setMaTau(rs.getString("maTau"));
                chuyenTau.setMaTuyenTau(rs.getString("maTuyenTau"));
                
                danhSachChuyenTau.add(chuyenTau);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timChuyenTauTheoTenGaVaNgay: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return danhSachChuyenTau;
    }

    public ChuyenTau timChuyenTauTheoMaChuyenTau(String maChuyenTau) {
        ChuyenTau chuyenTau = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }

            String sql = 
                "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh, ct.ngayDuKien, ct.gioDuKien, ct.trangThaiChuyenTau, " +
                "ct.maTau, ct.maTuyenTau, t.tenTau, t.trangThaiTau, " +
                "g1.tenGa AS tenGaDi, g2.tenGa AS tenGaDen " +
                "FROM ChuyenTau ct " +
                "INNER JOIN Tau t ON ct.maTau = t.maTau " +
                "INNER JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau " +
                "INNER JOIN Ga g1 ON tt.gaDi = g1.maGa " +
                "INNER JOIN Ga g2 ON tt.gaDen = g2.maGa " +
                "WHERE ct.maChuyenTau = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maChuyenTau);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                chuyenTau = new ChuyenTau();
                chuyenTau.setMaChuyenTau(rs.getString("maChuyenTau"));
                chuyenTau.setNgayKhoiHanh(rs.getDate("ngayKhoiHanh"));
                chuyenTau.setGioKhoiHanh(rs.getTime("gioKhoiHanh") != null ? rs.getTime("gioKhoiHanh").toLocalTime() : null);
                chuyenTau.setNgayDuKien(rs.getDate("ngayDuKien"));
                chuyenTau.setGioDuKien(rs.getTime("gioDuKien") != null ? rs.getTime("gioDuKien").toLocalTime() : null);
                chuyenTau.setTrangThaiChuyenTau(rs.getString("trangThaiChuyenTau") != null ? TrangThaiChuyenTau.valueOf(rs.getString("trangThaiChuyenTau")) : null);
                chuyenTau.setMaTau(rs.getString("maTau"));
                chuyenTau.setMaTuyenTau(rs.getString("maTuyenTau"));

                Tau tau = new Tau();
                tau.setMaTau(rs.getString("maTau"));
                tau.setTenTau(rs.getString("tenTau"));
                tau.setTrangThaiTau(TrangThaiTau.valueOf(rs.getString("trangThaiTau")));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong timChuyenTauTheoMaChuyenTau: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return chuyenTau;
    }
    
    public boolean themChuyenTau(ChuyenTau chuyenTau) {
        String sql = "INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }
            // Kiểm tra null và tính hợp lệ của dữ liệu đầu vào
            if (chuyenTau == null || chuyenTau.getMaChuyenTau() == null || chuyenTau.getMaTau() == null ||
                chuyenTau.getMaTuyenTau() == null || chuyenTau.getGioKhoiHanh() == null || chuyenTau.getGioDuKien() == null ||
                chuyenTau.getNgayKhoiHanh() == null || chuyenTau.getNgayDuKien() == null) {
                System.err.println("Dữ liệu đầu vào không hợp lệ: Một hoặc nhiều trường bắt buộc là null");
                return false;
            }

            // Ghi log giá trị ngày để debug
            System.out.println("themChuyenTau - ngayKhoiHanh: " + chuyenTau.getNgayKhoiHanh() +
                              ", ngayDuKien: " + chuyenTau.getNgayDuKien());

            pstmt.setString(1, chuyenTau.getMaChuyenTau());
            pstmt.setDate(2, new java.sql.Date(chuyenTau.getNgayKhoiHanh().getTime()));
            pstmt.setTime(3, java.sql.Time.valueOf(chuyenTau.getGioKhoiHanh()));
            pstmt.setDate(4, new java.sql.Date(chuyenTau.getNgayDuKien().getTime()));
            pstmt.setTime(5, java.sql.Time.valueOf(chuyenTau.getGioDuKien()));
            pstmt.setString(6, chuyenTau.getTrangThaiChuyenTau() != null ? chuyenTau.getTrangThaiChuyenTau().name() : TrangThaiChuyenTau.hoatDong.name());
            pstmt.setString(7, chuyenTau.getMaTau());
            pstmt.setString(8, chuyenTau.getMaTuyenTau());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Không có bản ghi nào được thêm vào");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong themChuyenTau: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khác trong themChuyenTau: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatChuyenTau(ChuyenTau chuyenTau) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = ConnectDB.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
            }
            // Kiểm tra null và tính hợp lệ của dữ liệu đầu vào
            if (chuyenTau == null || chuyenTau.getMaChuyenTau() == null || chuyenTau.getMaTau() == null ||
                chuyenTau.getMaTuyenTau() == null || chuyenTau.getGioKhoiHanh() == null || chuyenTau.getGioDuKien() == null ||
                chuyenTau.getNgayKhoiHanh() == null || chuyenTau.getNgayDuKien() == null) {
                System.err.println("Dữ liệu đầu vào không hợp lệ: Một hoặc nhiều trường bắt buộc là null");
                return false;
            }

            // Ghi log giá trị ngày để debug
            System.out.println("capNhatChuyenTau - ngayKhoiHanh: " + chuyenTau.getNgayKhoiHanh() +
                              ", ngayDuKien: " + chuyenTau.getNgayDuKien());

            String sql = 
                "UPDATE ChuyenTau SET ngayKhoiHanh = ?, gioKhoiHanh = ?, " +
                "ngayDuKien = ?, gioDuKien = ?, trangThaiChuyenTau = ?, maTau = ?, maTuyenTau = ? WHERE maChuyenTau = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setDate(1, new java.sql.Date(chuyenTau.getNgayKhoiHanh().getTime()));
            pstmt.setTime(2, java.sql.Time.valueOf(chuyenTau.getGioKhoiHanh()));
            pstmt.setDate(3, new java.sql.Date(chuyenTau.getNgayDuKien().getTime()));
            pstmt.setTime(4, java.sql.Time.valueOf(chuyenTau.getGioDuKien()));
            pstmt.setString(5, chuyenTau.getTrangThaiChuyenTau() != null ? chuyenTau.getTrangThaiChuyenTau().name() : TrangThaiChuyenTau.hoatDong.name());
            pstmt.setString(6, chuyenTau.getMaTau());
            pstmt.setString(7, chuyenTau.getMaTuyenTau());
            pstmt.setString(8, chuyenTau.getMaChuyenTau());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Không có bản ghi nào được cập nhật");
            }
            success = rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong capNhatChuyenTau: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi khác trong capNhatChuyenTau: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return success;
    }
    
    public boolean kiemTraKhoangCachThoiGianChuyenTau(String tenTau, Date ngayKhoiHanh, LocalTime gioKhoiHanh, String maChuyenTauHienTai) {
        System.out.println("Kiểm tra thời gian: tenTau = " + tenTau +
                           ", ngayKhoiHanh = " + ngayKhoiHanh + ", gioKhoiHanh = " + gioKhoiHanh);
        String sql = "SELECT ct.maChuyenTau, ct.ngayKhoiHanh, ct.gioKhoiHanh " +
                     "FROM ChuyenTau ct " +
                     "JOIN Tau t ON ct.maTau = t.maTau " +
                     "WHERE t.tenTau = ?";
        if (maChuyenTauHienTai != null && !maChuyenTauHienTai.isEmpty()) {
            sql += " AND ct.maChuyenTau != ?";
        }
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.err.println("Không thể kết nối đến cơ sở dữ liệu");
                return false;
            }
            if (ngayKhoiHanh == null || gioKhoiHanh == null) {
                System.err.println("Ngày hoặc giờ khởi hành không được null");
                return false;
            }
            pstmt.setString(1, tenTau);
            if (maChuyenTauHienTai != null && !maChuyenTauHienTai.isEmpty()) {
                pstmt.setString(2, maChuyenTauHienTai);
            }
            ResultSet rs = pstmt.executeQuery();
            LocalDateTime thoiGianMoi = LocalDateTime.of(ngayKhoiHanh.toLocalDate(), gioKhoiHanh);
            System.out.println("thoiGianMoi = " + thoiGianMoi);
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String maChuyenTau = rs.getString("maChuyenTau");
                Date ngayCu = rs.getDate("ngayKhoiHanh");
                LocalTime gioCu = rs.getTime("gioKhoiHanh") != null ? rs.getTime("gioKhoiHanh").toLocalTime() : null;
                if (ngayCu == null || gioCu == null) {
                    System.err.println("Dữ liệu không hợp lệ: maChuyenTau = " + maChuyenTau);
                    continue;
                }
                LocalDateTime thoiGianCu = LocalDateTime.of(ngayCu.toLocalDate(), gioCu);
                long khoangCachGiay = Math.abs(Duration.between(thoiGianMoi, thoiGianCu).getSeconds());
                System.out.println("So sánh: maChuyenTau = " + maChuyenTau + ", khoangCach = " + khoangCachGiay);
                if (khoangCachGiay < 86400) {
                    System.out.println("Xung đột thời gian: " + maChuyenTau);
                    return false;
                }
            }
            System.out.println("Kết quả: " + (hasResults ? "Không xung đột" : "Không có chuyến tàu"));
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong kiemTraKhoangCachThoiGianChuyenTau: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public String taoMaChuyenTauMoi(String tenTau, Date ngayKhoiHanh, LocalTime gioKhoiHanh) {
        if (ngayKhoiHanh == null || gioKhoiHanh == null) {
            throw new IllegalArgumentException("Ngày hoặc giờ khởi hành không được null");
        }

        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        String hour = gioKhoiHanh.format(hourFormatter);
        
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        String minute = gioKhoiHanh.format(minuteFormatter);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = ngayKhoiHanh.toLocalDate().format(dateFormatter);
        
        String tenTauCode = tenTau.isEmpty() ? "UNKNOWN" : tenTau;
        
        return hour + minute + date + tenTauCode;
    }
}