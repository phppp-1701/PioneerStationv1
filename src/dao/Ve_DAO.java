package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public String timMaVeCuoiCung(ChuyenTau chuyenTau, Cho cho) {
        String maVeCuoiCung = "";
        
        // Định dạng ngày theo YYMMDD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String ngayKhoiHanhYYMMDD = chuyenTau.getNgayKhoiHanh().format(formatter);
        
        // Lấy 2 ký tự cuối của maChuyenTau
        String maChuyenTauTT = chuyenTau.getMaChuyenTau();
        if (maChuyenTauTT.length() >= 2) {
            maChuyenTauTT = maChuyenTauTT.substring(maChuyenTauTT.length() - 2);
        } else {
            maChuyenTauTT = maChuyenTauTT; // Giữ nguyên nếu ít hơn 2 ký tự
        }
        
        // Lấy 3 ký tự cuối của maCho
        String maChoCCC = cho.getMaCho();
        if (maChoCCC.length() >= 3) {
            maChoCCC = maChoCCC.substring(maChoCCC.length() - 3);
        } else {
            maChoCCC = maChoCCC; // Giữ nguyên nếu ít hơn 3 ký tự
        }

        // Xây dựng phần tiền tố của mã vé để tìm kiếm (ví dụ: "250526TTCCCVE")
        // Sử dụng LIKE để tìm kiếm các mã vé bắt đầu bằng tiền tố này
        String prefixMaVe = ngayKhoiHanhYYMMDD + maChuyenTauTT + maChoCCC + "VE";

        try {
            Connection con = ConnectDB.getInstance().getConnection();
            String sql = "SELECT TOP 1 maVe FROM Ve " +
                         "WHERE maVe LIKE ? " +
                         "ORDER BY maVe DESC"; // Sắp xếp giảm dần để lấy mã lớn nhất
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prefixMaVe + "%"); // Tìm các mã vé bắt đầu bằng prefix
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maVeCuoiCung = rs.getString("maVe");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maVeCuoiCung;
    }
    
    public String taoMaVeMoi(String lastMaVe, LocalDate ngayKhoiHanh, String maChuyenTau, String maCho) {
        // Phần YYMMDD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String partYYMMDD = ngayKhoiHanh.format(formatter);

        // Phần TT (2 ký tự cuối của maChuyenTau)
        String partTT = maChuyenTau.substring(Math.max(0, maChuyenTau.length() - 2));

        // Phần CCC (3 ký tự cuối của maCho)
        String partCCC = maCho.substring(Math.max(0, maCho.length() - 3));
        
        // Phần VE cố định
        String partVE = "VE";

        int soThuTu = 0;
        if (!lastMaVe.isEmpty()) {
            try {
                // Trích xuất phần XXXX từ mã vé cuối cùng
                String xxxxPart = lastMaVe.substring(lastMaVe.length() - 4);
                soThuTu = Integer.parseInt(xxxxPart);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.err.println("Lỗi khi trích xuất hoặc chuyển đổi số thứ tự từ mã vé cuối cùng: " + lastMaVe);
                // Giữ soThuTu = 0 nếu có lỗi, tức là sẽ bắt đầu từ 0001
            }
        }
        
        soThuTu++; // Tăng số thứ tự lên 1
        if (soThuTu > 9999) {
            // Xử lý trường hợp tràn số thứ tự (ví dụ: reset hoặc thông báo lỗi)
            // Trong trường hợp này, chúng ta chỉ cảnh báo và bắt đầu lại từ 0001
            System.err.println("Số thứ tự mã vé đã đạt giới hạn 9999. Bắt đầu lại từ 0001.");
            soThuTu = 1;
        }

        String partXXXX = String.format("%04d", soThuTu); // Định dạng thành 4 chữ số, VD: 0001, 0010, 0100, 1000

        return partYYMMDD + partTT + partCCC + partVE + partXXXX;
    }
    
    public boolean themVe(Ve ve) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        boolean isSuccess = false;
        try {
            con = ConnectDB.getInstance().getConnection();
            String sql = "INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, maHoaDon, maCho, maChuyenTau) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            preparedStatement = con.prepareStatement(sql);
            
            // Thiết lập các tham số cho PreparedStatement
            preparedStatement.setString(1, ve.getMaVe());
            preparedStatement.setDate(2, Date.valueOf(ve.getNgayTaoVe())); // Chuyển đổi LocalDate sang java.sql.Date
            preparedStatement.setString(3, ve.getTrangThaiVe().name()); // Chuyển đổi Enum sang String
            preparedStatement.setString(4, ve.getTenKhachHang());
            preparedStatement.setString(5, ve.getCccd_HoChieu());
            preparedStatement.setDate(6, Date.valueOf(ve.getNgaySinh())); // Chuyển đổi LocalDate sang java.sql.Date
            preparedStatement.setString(7, ve.getLoaiVe().name()); // Chuyển đổi Enum sang String
            
            // Xử lý các trường khóa ngoại có thể là null hoặc cần kiểm tra đối tượng
            // Giả sử maHoaDon, maCho, maChuyenTau không được null khi tạo vé
            // Nếu có thể null, bạn cần thêm logic kiểm tra null và setNull
            if (ve.getHoaDon() != null) {
                preparedStatement.setString(8, ve.getHoaDon().getMaHoaDon());
            } else {
                preparedStatement.setNull(8, java.sql.Types.VARCHAR); // Hoặc ném lỗi nếu không được phép null
            }

            if (ve.getCho() != null) {
                preparedStatement.setString(9, ve.getCho().getMaCho());
            } else {
                 preparedStatement.setNull(9, java.sql.Types.VARCHAR);
            }

            if (ve.getChuyenTau() != null) {
                preparedStatement.setString(10, ve.getChuyenTau().getMaChuyenTau());
            } else {
                 preparedStatement.setNull(10, java.sql.Types.VARCHAR);
            }
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm vé vào cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ConnectDB.getInstance().disconnect(); // Đóng kết nối sau khi sử dụng
        }
        return isSuccess;
    }
    
    public List<Ve> timDanhSachVeTheoHoaDon(HoaDon hoaDon, boolean dongKetNoi){
    	List<Ve> danhSachVe = new ArrayList<Ve>();
    	Connection con = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try {
			con = ConnectDB.getConnection();
			String sql = "select * from Ve where maHoaDon = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, hoaDon.getMaHoaDon());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Ve ve = new Ve();
				ve.setMaVe(resultSet.getString(1));
				ve.setNgayTaoVe(resultSet.getDate(2).toLocalDate());
				ve.setTrangThaiVe(TrangThaiVe.valueOf(resultSet.getString(3)));
				ve.setTenKhachHang(resultSet.getString(4));
				ve.setCccd_HoChieu(resultSet.getString(5));
				ve.setNgaySinh(resultSet.getDate(6).toLocalDate());
				ve.setLoaiVe(LoaiVe.valueOf(resultSet.getString(7)));
				ve.setHoaDon(hoaDon);
				Cho_DAO cho_DAO = new Cho_DAO();
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString(9), false);
				ve.setCho(cho);
				ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
				ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString(10), false);
				ve.setChuyenTau(chuyenTau);
				danhSachVe.add(ve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(dongKetNoi) {
    		ConnectDB.getInstance().disconnect();
    	}
    	return danhSachVe;
    }
}