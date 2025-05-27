package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.HoaDon.PhuongThucThanhToan;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HoaDon_DAO {

    // Thêm hóa đơn mới
    public boolean themHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, "
                    + "phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setTime(3, Time.valueOf(hoaDon.getGioTaoHoaDon()));
            stmt.setString(4, hoaDon.getPhuongThucThanhToan().toString());
            stmt.setDouble(5, hoaDon.getPhanTramGiamGia());
            stmt.setDouble(6, hoaDon.getThanhTien());
            stmt.setDouble(7, hoaDon.getTienKhachDua());
            stmt.setDouble(8, hoaDon.getTienTraLai());
            // Xử lý null cho maKhachHang
            String maKhachHang = hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getMaKhachHang() : null;
            stmt.setString(9, maKhachHang);
            // Xử lý null cho maKhuyenMai
            String maKhuyenMai = hoaDon.getKhuyenMai() != null ? hoaDon.getKhuyenMai().getMaKhuyenMai() : null;
            stmt.setString(10, maKhuyenMai);
            // Xử lý null cho maNhanVien
            String maNhanVien = hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getMaNhanVien() : null;
            stmt.setString(11, maNhanVien);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectDB.getInstance().disconnect();
        }
    }

    // Tìm hóa đơn theo mã
    public HoaDon timHoaDonTheoMa(String maHoaDon, boolean dongKetNoi) {
        HoaDon hoaDon = new HoaDon();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            rs = stmt.executeQuery();
            if (rs.next()) {
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayTaoHoaDon(rs.getDate("ngayTaoHoaDon").toLocalDate());
                hoaDon.setGioTaoHoaDon(rs.getTime("gioTaoHoaDon").toLocalTime());
                hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")));
                hoaDon.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                hoaDon.setThanhTien(rs.getDouble("thanhTien"));
                hoaDon.setTienKhachDua(rs.getDouble("tienKhachDua"));
                hoaDon.setTienTraLai(rs.getDouble("tienTraLai"));
                KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
                KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString("maKhachHang"), false);
                hoaDon.setKhachHang(kh);
                KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
                KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString("maKhuyenMai"), false);
                hoaDon.setKhuyenMai(km);
                NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
                NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString("maNhanVien"), false);
                hoaDon.setNhanVien(nv);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hoaDon;
    }

    // Lấy danh sách tất cả hóa đơn
    public List<HoaDon> layDanhSachHoaDon(boolean dongKetNoi) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon ORDER BY ngayTaoHoaDon DESC, gioTaoHoaDon DESC";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayTaoHoaDon(rs.getDate("ngayTaoHoaDon").toLocalDate());
                hoaDon.setGioTaoHoaDon(rs.getTime("gioTaoHoaDon").toLocalTime());
                hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")));
                hoaDon.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                hoaDon.setThanhTien(rs.getDouble("thanhTien"));
                hoaDon.setTienKhachDua(rs.getDouble("tienKhachDua"));
                hoaDon.setTienTraLai(rs.getDouble("tienTraLai"));
                KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
                KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString("maKhachHang"), false);
                hoaDon.setKhachHang(kh);
                KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
                KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString("maKhuyenMai"), false);
                hoaDon.setKhuyenMai(km);
                NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
                NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString("maNhanVien"), false);
                hoaDon.setNhanVien(nv);
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachHoaDon;
    }

    // Cập nhật hóa đơn
    public boolean capNhatHoaDon(HoaDon hoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "UPDATE HoaDon SET ngayTaoHoaDon = ?, gioTaoHoaDon = ?, phuongThucThanhToan = ?, "
                    + "phanTramGiamGia = ?, thanhTien = ?, tienKhachDua = ?, tienTraLai = ?, "
                    + "maKhachHang = ?, maKhuyenMai = ?, maNhanVien = ? WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
            stmt.setTime(2, Time.valueOf(hoaDon.getGioTaoHoaDon()));
            stmt.setString(3, hoaDon.getPhuongThucThanhToan().toString());
            stmt.setDouble(4, hoaDon.getPhanTramGiamGia());
            stmt.setDouble(5, hoaDon.getThanhTien());
            stmt.setDouble(6, hoaDon.getTienKhachDua());
            stmt.setDouble(7, hoaDon.getTienTraLai());
            stmt.setString(8, hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getMaKhachHang() : null);
            stmt.setString(9, hoaDon.getKhuyenMai() != null ? hoaDon.getKhuyenMai().getMaKhuyenMai() : null);
            stmt.setString(10, hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getMaNhanVien() : null);
            stmt.setString(11, hoaDon.getMaHoaDon());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectDB.getInstance().disconnect();
        }
    }

    // Xóa hóa đơn
    public boolean xoaHoaDon(String maHoaDon) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectDB.getInstance().disconnect();
        }
    }

    // Tra cứu hóa đơn linh hoạt theo số điện thoại và tên khách hàng
    public List<HoaDon> traCuuHoaDonTheoSDT_Ten(String soDienThoai, String tenKhachHang, boolean dongKetNoi) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT hd.* FROM HoaDon hd ");
            sql.append("LEFT JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang ");
            sql.append("WHERE 1=1 ");
            List<String> params = new ArrayList<>();
            if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
                sql.append("AND kh.soDienThoai LIKE ? ");
                params.add("%" + soDienThoai.trim() + "%");
            }
            if (tenKhachHang != null && !tenKhachHang.trim().isEmpty()) {
                sql.append("AND kh.tenKhachHang LIKE ? ");
                params.add("%" + tenKhachHang.trim() + "%");
            }
            sql.append("ORDER BY hd.ngayTaoHoaDon DESC, hd.gioTaoHoaDon DESC");
            stmt = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayTaoHoaDon(rs.getDate("ngayTaoHoaDon").toLocalDate());
                hoaDon.setGioTaoHoaDon(rs.getTime("gioTaoHoaDon").toLocalTime());
                hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")));
                hoaDon.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                hoaDon.setThanhTien(rs.getDouble("thanhTien"));
                hoaDon.setTienKhachDua(rs.getDouble("tienKhachDua"));
                hoaDon.setTienTraLai(rs.getDouble("tienTraLai"));
                KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
                KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString("maKhachHang"), false);
                hoaDon.setKhachHang(kh);
                KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
                KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString("maKhuyenMai"), false);
                hoaDon.setKhuyenMai(km);
                NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
                NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString("maNhanVien"), false);
                hoaDon.setNhanVien(nv);
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachHoaDon;
    }

    // Tra cứu hóa đơn theo mã khách hàng
    public List<HoaDon> layDanhSachHoaDonTheoKhachHang(String maKhachHang, boolean dongKetNoi) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon WHERE maKhachHang = ? ORDER BY ngayTaoHoaDon DESC, gioTaoHoaDon DESC";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, maKhachHang);
            rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayTaoHoaDon(rs.getDate("ngayTaoHoaDon").toLocalDate());
                hoaDon.setGioTaoHoaDon(rs.getTime("gioTaoHoaDon").toLocalTime());
                hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")));
                hoaDon.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                hoaDon.setThanhTien(rs.getDouble("thanhTien"));
                hoaDon.setTienKhachDua(rs.getDouble("tienKhachDua"));
                hoaDon.setTienTraLai(rs.getDouble("tienTraLai"));
                KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
                KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString("maKhachHang"), false);
                hoaDon.setKhachHang(kh);
                KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
                KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString("maKhuyenMai"), false);
                hoaDon.setKhuyenMai(km);
                NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
                NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString("maNhanVien"), false);
                hoaDon.setNhanVien(nv);
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachHoaDon;
    }

    // Tra cứu hóa đơn theo số điện thoại, tên khách hàng và ngày tạo hóa đơn
    public List<HoaDon> traCuuHoaDonTheoSDT_Ten_Ngay(String soDienThoai, String tenKhachHang, LocalDate ngayTaoHoaDon, boolean dongKetNoi) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.getConnection();
            String sql = "SELECT hd.* FROM HoaDon hd LEFT JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
                    + "WHERE kh.soDienThoai LIKE ? AND kh.tenKhachHang LIKE ? AND hd.ngayTaoHoaDon = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + soDienThoai + "%");
            stmt.setString(2, "%" + tenKhachHang + "%");
            stmt.setDate(3, Date.valueOf(ngayTaoHoaDon));
            rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
                hoaDon.setNgayTaoHoaDon(rs.getDate("ngayTaoHoaDon").toLocalDate());
                hoaDon.setGioTaoHoaDon(rs.getTime("gioTaoHoaDon").toLocalTime());
                hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString("phuongThucThanhToan")));
                hoaDon.setPhanTramGiamGia(rs.getDouble("phanTramGiamGia"));
                hoaDon.setThanhTien(rs.getDouble("thanhTien"));
                hoaDon.setTienKhachDua(rs.getDouble("tienKhachDua"));
                hoaDon.setTienTraLai(rs.getDouble("tienTraLai"));
                KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
                KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString("maKhachHang"), false);
                hoaDon.setKhachHang(kh);
                KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
                KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString("maKhuyenMai"), false);
                hoaDon.setKhuyenMai(km);
                NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
                NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString("maNhanVien"), false);
                hoaDon.setNhanVien(nv);
                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachHoaDon;
    }

    public String layMaHoaDonCuoiCung(String maNhanVien, boolean dongKetNoi) {
        String ma = "";
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String datePrefix = today.format(formatter);
            String prefix = datePrefix + maNhanVien;
            String sql = "SELECT TOP 1 maHoaDon FROM HoaDon WHERE maHoaDon LIKE ? ORDER BY maHoaDon DESC";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, prefix + "%");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ma = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null && dongKetNoi) ConnectDB.getInstance().disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ma;
    }

    public String taoMaHoaDonMoi(String maNhanVien) {
        String maMoi = "";
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String datePart = today.format(formatter);
        if (maNhanVien == null || maNhanVien.isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        String maGanNhat = layMaHoaDonCuoiCung(maNhanVien, true);
        if (maGanNhat == null || maGanNhat.isEmpty()) {
            maMoi = datePart + maNhanVien + "000001";
        } else {
            String sequencePart = maGanNhat.substring(maGanNhat.length() - 6);
            try {
                int sequenceNumber = Integer.parseInt(sequencePart);
                sequenceNumber++;
                String newSequence = String.format("%06d", sequenceNumber);
                maMoi = datePart + maNhanVien + newSequence;
            } catch (NumberFormatException e) {
                System.err.println("Lỗi phân tích sequence: " + e.getMessage());
                maMoi = datePart + maNhanVien + "000001";
            }
        }
        System.out.println("Generated maHoaDonMoi: " + maMoi);
        return maMoi;
    }
}