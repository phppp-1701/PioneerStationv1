package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Ve;
import entity.Ve.LoaiVe;
import entity.Ve.TrangThaiVe;
import entity.HoaDon;
import entity.Cho;
import entity.ChuyenTau;

public class Ve_DAO {
    public List<Ve> layToanBoVe(boolean dongKetNoi) {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ve ve = new Ve();
                ve.setMaVe(resultSet.getString("maVe"));
                ve.setNgayTaoVe(resultSet.getDate("ngayTaoVe") != null ? resultSet.getDate("ngayTaoVe").toLocalDate() : null);
                ve.setTrangThaiVe(TrangThaiVe.valueOf(resultSet.getString("trangThaiVe")));
                ve.setTenKhachHang(resultSet.getString("tenKhachHang"));
                ve.setCccd_HoChieu(resultSet.getString("cccd_HoChieu"));
                ve.setNgaySinh(resultSet.getDate("ngaySinh") != null ? resultSet.getDate("ngaySinh").toLocalDate() : null);
                ve.setLoaiVe(LoaiVe.valueOf(resultSet.getString("loaiVe")));
                HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
                HoaDon hd = hoaDon_DAO.timHoaDonTheoMa(resultSet.getString("maHoaDon"), false);
                ve.setHoaDon(hd);
                Cho_DAO cho_DAO = new Cho_DAO();
                Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false);
                ve.setCho(cho);
                ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
                ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);
                ve.setChuyenTau(chuyenTau);
                danhSachVe.add(ve);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
        }
        return danhSachVe;
    }

    public Ve timVeTheoMa(String maVe, boolean dongKetNoi) {
        Ve ve = new Ve();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE maVe = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, maVe);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ve.setMaVe(resultSet.getString("maVe"));
                ve.setNgayTaoVe(resultSet.getDate("ngayTaoVe") != null ? resultSet.getDate("ngayTaoVe").toLocalDate() : null);
                ve.setTrangThaiVe(TrangThaiVe.valueOf(resultSet.getString("trangThaiVe")));
                ve.setTenKhachHang(resultSet.getString("tenKhachHang"));
                ve.setCccd_HoChieu(resultSet.getString("cccd_HoChieu"));
                ve.setNgaySinh(resultSet.getDate("ngaySinh") != null ? resultSet.getDate("ngaySinh").toLocalDate() : null);
                ve.setLoaiVe(LoaiVe.valueOf(resultSet.getString("loaiVe")));
                HoaDon_DAO hoaDon_DAO = new HoaDon_DAO();
                HoaDon hd = hoaDon_DAO.timHoaDonTheoMa(resultSet.getString("maHoaDon"), false);
                ve.setHoaDon(hd);
                Cho_DAO cho_DAO = new Cho_DAO();
                Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false);
                ve.setCho(cho);
                ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
                ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);
                ve.setChuyenTau(chuyenTau);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
        }
        return ve;
    }

    public List<Ve> timDanhSachVeTheoMa(String maVe, boolean dongKetNoi) {
        List<Ve> danhSachVe = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ve WHERE maVe LIKE ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + maVe + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ve ve = new Ve();
                ve.setMaVe(resultSet.getString("maVe"));
                ve.setNgayTaoVe(resultSet.getDate("ngayTaoVe") != null ? resultSet.getDate("ngayTaoVe").toLocalDate() : null);
                ve.setTrangThaiVe(TrangThaiVe.valueOf(resultSet.getString("trangThaiVe")));
                ve.setTenKhachHang(resultSet.getString("tenKhachHang"));
                ve.setCccd_HoChieu(resultSet.getString("cccd_HoChieu"));
                ve.setNgaySinh(resultSet.getDate("ngaySinh") != null ? resultSet.getDate("ngaySinh").toLocalDate() : null);
                ve.setLoaiVe(LoaiVe.valueOf(resultSet.getString("loaiVe")));
                HoaDon hd = new HoaDon_DAO().timHoaDonTheoMa(resultSet.getString("maHoaDon"), false);
                Cho cho = new Cho_DAO().timChoTheoMaCho(resultSet.getString("maCho"), false);
                ChuyenTau chuyenTau = new ChuyenTau_DAO().timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);

                ve.setHoaDon(hd);
                ve.setCho(cho);
                ve.setChuyenTau(chuyenTau);

                danhSachVe.add(ve);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dongKetNoi) {
                ConnectDB.getInstance().disconnect();
            }
        }

        return danhSachVe;
    }

    public boolean capNhatVe(Ve ve, boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            if (ve == null || ve.getMaVe() == null || ve.getMaVe().trim().isEmpty()) {
                return false;
            }

            con = ConnectDB.getConnection();
            String sql = "UPDATE Ve SET ngayTaoVe = ?, trangThaiVe = ?, tenKhachHang = ?, cccd_HoChieu = ?, ngaySinh = ?, loaiVe = ?, giaVe = ?, phanTramGiamGiaCoDinh = ?, maHoaDon = ?, maCho = ?, maChuyenTau = ? WHERE maVe = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, ve.getNgayTaoVe() != null ? java.sql.Date.valueOf(ve.getNgayTaoVe()) : null);
            preparedStatement.setString(2, ve.getTrangThaiVe() != null ? ve.getTrangThaiVe().name() : null);
            preparedStatement.setString(3, ve.getTenKhachHang());
            preparedStatement.setString(4, ve.getCccd_HoChieu());
            preparedStatement.setDate(5, ve.getNgaySinh() != null ? java.sql.Date.valueOf(ve.getNgaySinh()) : null);
            preparedStatement.setString(6, ve.getLoaiVe() != null ? ve.getLoaiVe().name() : null);
            preparedStatement.setDouble(7, ve.tinhGiaVe());
            preparedStatement.setDouble(8, ve.tinhPhanTramGiamGia());
            preparedStatement.setString(9, ve.getHoaDon() != null ? ve.getHoaDon().getMaHoaDon() : null);
            preparedStatement.setString(10, ve.getCho() != null ? ve.getCho().getMaCho() : null);
            preparedStatement.setString(11, ve.getChuyenTau() != null ? ve.getChuyenTau().getMaChuyenTau() : null);
            preparedStatement.setString(12, ve.getMaVe());

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
        }
    }

    public boolean capNhatTrangThaiVe(String maVe, TrangThaiVe trangThaiMoi, boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement stmtCheck = null;
        PreparedStatement stmtUpdate = null;
        ResultSet rs = null;

        try {
            if (maVe == null || maVe.trim().isEmpty() || trangThaiMoi == null) {
                return false;
            }

            con = ConnectDB.getConnection();

            // Kiểm tra vé có tồn tại và còn hiệu lực không
            String sqlCheck = "SELECT trangThaiVe FROM Ve WHERE maVe = ?";
            stmtCheck = con.prepareStatement(sqlCheck);
            stmtCheck.setString(1, maVe);
            rs = stmtCheck.executeQuery();

            if (!rs.next()) {
                return false;
            }

            TrangThaiVe trangThaiHienTai = TrangThaiVe.valueOf(rs.getString("trangThaiVe"));

            // Chỉ cho phép cập nhật nếu vé đang còn hiệu lực
            if (trangThaiHienTai != TrangThaiVe.hieuLuc) {
                return false;
            }

            // Cập nhật trạng thái mới (có thể là daHuy hoặc hetHan)
            String sqlUpdate = "UPDATE Ve SET trangThaiVe = ? WHERE maVe = ?";
            stmtUpdate = con.prepareStatement(sqlUpdate);
            stmtUpdate.setString(1, trangThaiMoi.name());
            stmtUpdate.setString(2, maVe);

            int rowsUpdated = stmtUpdate.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (dongKetNoi) {
				ConnectDB.getInstance().disconnect();
			}
        }
    }


    public boolean doiVe(String maVe, Ve veMoi, boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String checkSql = "SELECT trangThaiVe FROM Ve WHERE maVe = ?";
            preparedStatement = con.prepareStatement(checkSql);
            preparedStatement.setString(1, maVe);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TrangThaiVe trangThai = TrangThaiVe.valueOf(resultSet.getString("trangThaiVe"));
                if (trangThai != TrangThaiVe.hieuLuc) {
                    return false;
                }
            } else {
                return false;
            }

            String updateSql = "UPDATE Ve SET maChuyenTau = ?, maCho = ?, loaiVe = ? WHERE maVe = ?";
            preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, veMoi.getChuyenTau() != null ? veMoi.getChuyenTau().getMaChuyenTau() : null);
            preparedStatement.setString(2, veMoi.getCho() != null ? veMoi.getCho().getMaCho() : null);
            preparedStatement.setString(3, veMoi.getLoaiVe() != null ? veMoi.getLoaiVe().name() : null);
            preparedStatement.setString(4, maVe);

            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement.close();
                preparedStatement = con.prepareStatement("UPDATE Ve SET trangThaiVe = ? WHERE maVe = ?");
                preparedStatement.setString(1, TrangThaiVe.daDoi.name());
                preparedStatement.setString(2, maVe);
                preparedStatement.executeUpdate();

                // Tính và gán giá vé và phần trăm giảm giá sau khi đổi
                veMoi.tinhGiaVe();
                veMoi.tinhPhanTramGiamGia();

                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (dongKetNoi) {
                ConnectDB.getInstance().disconnect();
            }
        }
    }

  
}