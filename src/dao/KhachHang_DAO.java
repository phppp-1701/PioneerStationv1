package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.KhachHang.LoaiKhachHang;
import entity.KhachHang.TrangThaiKhachHang;

public class KhachHang_DAO {

    //Thêm một khách hàng mới
    public boolean themKhachHang(KhachHang kh, Boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        boolean themKH = false;
        try {
            con = ConnectDB.getConnection();
            String sql = "INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, kh.getMaKhachHang());
            preparedStatement.setString(2, kh.getTenKhachHang());
            preparedStatement.setString(3, kh.getCccd_HoChieu());
            preparedStatement.setString(4, kh.getSoDienThoai());
         // Xử lý loại khách hàng (phải là 'thanThiet' hoặc 'vip' hoặc 'vangLai')
 			String loaiKhachHang = (kh.getLoaiKhachHang() == KhachHang.LoaiKhachHang.thanThiet) ? "thanThiet" : (kh.getLoaiKhachHang() == KhachHang.LoaiKhachHang.vip) ? "vip" : "vangLai";
 			preparedStatement.setString(5, loaiKhachHang);
 			// Xử lý trạng thái khách hàng (phải là 'hoatDong' hoặc 'voHieuHoa')
 			String trangThaiKhachHang = (kh.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.hoatDong) ? "hoatDong" : "voHieuHoa";
 			preparedStatement.setString(6, trangThaiKhachHang);
            preparedStatement.setString(7, kh.getEmail());

            int rows = preparedStatement.executeUpdate();
            themKH = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return themKH;
    }
   
    //Lấy danh sách tất cả khách hàng
    public List<KhachHang> layTatCaKhachHang(Boolean dongKetNoi) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang";
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(resultSet.getString(1));
                kh.setTenKhachHang(resultSet.getString(2));
                kh.setCccd_HoChieu(resultSet.getString(3));
                kh.setSoDienThoai(resultSet.getString(4));
                LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
                kh.setLoaiKhachHang(loaiKhachHang);
                TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
                kh.setTrangThaiKhachHang(trangThaiKhachHang);
                kh.setEmail(resultSet.getString(7));
                danhSachKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return danhSachKhachHang;
    }
    
    //tìm khách hàng theo mã
    public KhachHang timKhachHangTheoMa(String maKhachHang, Boolean dongKetNoi) {
    	KhachHang kh = new KhachHang();
    	Connection con = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try {
			con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where maKhachHang LIKE ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maKhachHang);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
                kh.setMaKhachHang(resultSet.getString(1));
                kh.setTenKhachHang(resultSet.getString(2));
                kh.setCccd_HoChieu(resultSet.getString(3));
                kh.setSoDienThoai(resultSet.getString(4));
                LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
                kh.setLoaiKhachHang(loaiKhachHang);
                TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
                kh.setTrangThaiKhachHang(trangThaiKhachHang);
                kh.setEmail(resultSet.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(dongKetNoi) {
    		ConnectDB.getInstance().disconnect();
    	}
    	return kh;
    }
    
 // Tìm khách hàng theo tên
    public List<KhachHang> timKhachHangTheoTen(String tenKH, Boolean dongKetNoi) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE tenKhachHang LIKE ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + tenKH + "%"); // Tìm kiếm gần đúng
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(resultSet.getString(1));
                kh.setTenKhachHang(resultSet.getString(2));
                kh.setCccd_HoChieu(resultSet.getString(3));
                kh.setSoDienThoai(resultSet.getString(4));
                LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
                kh.setLoaiKhachHang(loaiKhachHang);
                TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
                kh.setTrangThaiKhachHang(trangThaiKhachHang);
                kh.setEmail(resultSet.getString(7));
                danhSachKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return danhSachKhachHang;
    }
    
 // Tìm khách hàng theo số điện thoại
    public List<KhachHang> timKhachHangTheoSoDienThoai(String soDT, boolean dongKetNoi) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE soDienThoai LIKE ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + soDT + "%"); // Tìm kiếm gần đúng
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(resultSet.getString(1));
                kh.setTenKhachHang(resultSet.getString(2));
                kh.setCccd_HoChieu(resultSet.getString(3));
                kh.setSoDienThoai(resultSet.getString(4));
                LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
                kh.setLoaiKhachHang(loaiKhachHang);
                TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
                kh.setTrangThaiKhachHang(trangThaiKhachHang);
                kh.setEmail(resultSet.getString(7));
                danhSachKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return danhSachKhachHang;
    }

 // Tìm khách hàng bằng cả tên và số điện thoại (kết hợp)
    public List<KhachHang> timKhachHangTheoTenVaSdt(String tenKhachHang, String soDienThoai, boolean dongKetNoi) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "select * from KhachHang where tenKhachHang like ? and soDienThoai = ?";
            con = ConnectDB.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%"+tenKhachHang+"%");
            preparedStatement.setString(2, "%"+soDienThoai+"%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(resultSet.getString(1));
                kh.setTenKhachHang(resultSet.getString(2));
                kh.setCccd_HoChieu(resultSet.getString(3));
                kh.setSoDienThoai(resultSet.getString(4));
                LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(resultSet.getString(5));
                kh.setLoaiKhachHang(loaiKhachHang);
                TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(resultSet.getString(6));
                kh.setTrangThaiKhachHang(trangThaiKhachHang);
                kh.setEmail(resultSet.getString(7));
                danhSachKhachHang.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }  
        return danhSachKhachHang;
    }
  //cập nhât khách hàng
    public boolean capNhatKhachHang(KhachHang kh, Boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean capNhatKH = false;
        try {
            con = ConnectDB.getConnection();
            String sql = "UPDATE KhachHang SET tenKhachHang = ?, cccd_HoChieu = ?, soDienThoai = ?, loaiKhachHang = ?, trangThaiKhachHang = ?, email = ? WHERE maKhachHang = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, kh.getTenKhachHang());
            pstmt.setString(2, kh.getCccd_HoChieu());
            pstmt.setString(3, kh.getSoDienThoai());
 			// Xử lý loại khách hàng (phải là 'thanThiet' hoặc 'vip' hoặc 'vangLai')
 			String loaiKhachHang = (kh.getLoaiKhachHang() == KhachHang.LoaiKhachHang.thanThiet) ? "thanThiet" : (kh.getLoaiKhachHang() == KhachHang.LoaiKhachHang.vip) ? "vip" : "vangLai";
 			pstmt.setString(4, loaiKhachHang);
 			// Xử lý trạng thái khách hàng (phải là 'hoatDong' hoặc 'voHieuHoa')
 			String trangThaiKhachHang = (kh.getTrangThaiKhachHang() == KhachHang.TrangThaiKhachHang.hoatDong) ? "hoatDong" : "voHieuHoa";
 			pstmt.setString(5, trangThaiKhachHang);
 			

 			pstmt.setString(6, kh.getEmail());
 			pstmt.setString(7, kh.getMaKhachHang());

            int rows = pstmt.executeUpdate();
            capNhatKH = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return capNhatKH;
    }

 // tim khach hang theo CCCD_HOCHIEU
 	public List<KhachHang> timKhachHangTheoCCCD_HoChieu(String CCCD_HoChieu, boolean dongKetNoi) {
 		List<KhachHang> dsKhachHang = new ArrayList<>();
 		Connection con = null;
 		PreparedStatement stmt = null;
 		ResultSet rs = null;

 		try {
 			con = ConnectDB.getConnection();
 			String sql = "SELECT * FROM KhachHang WHERE cccd_HoChieu LIKE ?";
 			stmt = con.prepareStatement(sql);
 			stmt.setString(1, "%" + CCCD_HoChieu + "%"); 
 			rs = stmt.executeQuery();

 			while (rs.next()) {
 				KhachHang kh = new KhachHang();
 				kh.setMaKhachHang(rs.getString(1));
 				kh.setTenKhachHang(rs.getString(2));
 				kh.setCccd_HoChieu(rs.getString(3));
 				kh.setSoDienThoai(rs.getString(4));
 				kh.setEmail(rs.getString(5));
 				LoaiKhachHang loaiKhachHang = LoaiKhachHang.valueOf(rs.getString(6));
 				kh.setLoaiKhachHang(loaiKhachHang);
 				TrangThaiKhachHang trangThaiKhachHang = TrangThaiKhachHang.valueOf(rs.getString(7));
 				kh.setTrangThaiKhachHang(trangThaiKhachHang);
 				dsKhachHang.add(kh);
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} 
 		if(dongKetNoi) {
 			ConnectDB.getInstance().disconnect();
 		}

 		return dsKhachHang;
 	}
    //Kiểm tra CCCD_HoChieu
    public boolean kiemTraTrungCCCD(String cccd) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT COUNT(*) FROM KhachHang WHERE cccd_HoChieu = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cccd);
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

    public String taoMaKhachHangMoi() {
        String maKhachHangMoi = "";
        try (Connection con = ConnectDB.getConnection()) {
            int namHienTai = Calendar.getInstance().get(Calendar.YEAR);
            String prefix = namHienTai + "KH";

            String sql = "SELECT MAX(maKhachHang) FROM KhachHang WHERE maKhachHang LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next() && rs.getString(1) != null) {
                String maKhachHangMax = rs.getString(1);
                String soThuTuStr = maKhachHangMax.substring(prefix.length());
                soThuTu = Integer.parseInt(soThuTuStr);
            }

            soThuTu++;
            maKhachHangMoi = prefix + String.format("%06d", soThuTu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maKhachHangMoi;
    }
}