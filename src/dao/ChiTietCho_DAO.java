package dao;

import entity.ChiTietCho;
import entity.ChiTietCho.TrangThaiCho;
import entity.Cho;
import entity.ChuyenTau;
import entity.ToaTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;
public class ChiTietCho_DAO {

	// Lấy tất cả danh sách ChiTietCho
	public List<ChiTietCho> layTatCaChiTietCho(boolean dongKetNoi) {
		List<ChiTietCho> danhSachChiTietCho = new ArrayList<>();
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM ChiTietCho";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ChiTietCho chiTietCho = new ChiTietCho();
				Cho_DAO cho_DAO = new Cho_DAO();
				Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false);
				chiTietCho.setCho(cho);
				ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO();
				ChuyenTau chuyenTau = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false);
				chiTietCho.setChuyenTau(chuyenTau);
				chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho")));
				chiTietCho.setGiaCho(chiTietCho.tinhGiaCho());
				danhSachChiTietCho.add(chiTietCho);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachChiTietCho;
	}
	
	public int tinhSoLuongChoConTrongCuaToaVaChuyenTau(ToaTau toaTau, ChuyenTau chuyenTau, boolean dongKetNoi) {
		int soLuong = 0;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT COUNT(ctc.maCho) AS SoLuongChoConTrong " +
		             "FROM ChiTietCho AS ctc " +
		             "JOIN Cho AS c ON ctc.maCho = c.maCho " +
		             "WHERE c.maToaTau = ? " +
		             "AND ctc.maChuyenTau = ? " +
		             "AND ctc.trangThaiCho = 'conTrong'";
			con = ConnectDB.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, toaTau.getMaToaTau());
			preparedStatement.setString(2, chuyenTau.getMaChuyenTau());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				soLuong = resultSet.getInt(1);
				System.out.println(soLuong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return soLuong;
	}
	
	public double timGiaMinToa(ToaTau toaTau, ChuyenTau chuyenTau) {
		double giaMin = 0;
		List<ChiTietCho> danhSachChiTietCho = layDanhSachChiTietChoTheoToaVaChuyen(toaTau, chuyenTau, true);
		for(ChiTietCho chiTiet : danhSachChiTietCho) {
			if(giaMin == 0) {
				giaMin = chiTiet.tinhGiaCho();
			}else {
				if(giaMin<=chiTiet.tinhGiaCho()) {
					giaMin = chiTiet.tinhGiaCho();
				}
			}
		}
		return giaMin;
	}
	
    public List<ChiTietCho> layDanhSachChiTietChoTheoToaVaChuyen(ToaTau toaTau, ChuyenTau chuyenTau, boolean dongKetNoi) {
        List<ChiTietCho> danhSachChiTietCho = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = ConnectDB.getConnection(); // Lấy kết nối từ lớp ConnectDB của bạn

            String sql = "SELECT ctc.* " +
                         "FROM ChiTietCho AS ctc " +
                         "JOIN Cho AS c ON ctc.maCho = c.maCho " +
                         "WHERE c.maToaTau = ? " +
                         "AND ctc.maChuyenTau = ? "; // Thêm điều kiện trạng thái 'conTrong'

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, toaTau.getMaToaTau());
            preparedStatement.setString(2, chuyenTau.getMaChuyenTau());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ChiTietCho chiTietCho = new ChiTietCho();

                // Tạo và gán đối tượng Cho
                Cho_DAO cho_DAO = new Cho_DAO(); // Đảm bảo Cho_DAO đã được khởi tạo hoặc là Singleton
                Cho cho = cho_DAO.timChoTheoMaCho(resultSet.getString("maCho"), false); // Giữ kết nối mở nếu cần cho các truy vấn khác trong cùng giao dịch
                chiTietCho.setCho(cho);

                // Tạo và gán đối tượng ChuyenTau
                ChuyenTau_DAO chuyenTau_DAO = new ChuyenTau_DAO(); // Đảm bảo ChuyenTau_DAO đã được khởi tạo hoặc là Singleton
                ChuyenTau chuyenTauObj = chuyenTau_DAO.timChuyenTauTheoMa(resultSet.getString("maChuyenTau"), false); // Giữ kết nối mở nếu cần
                chiTietCho.setChuyenTau(chuyenTauObj);

                // Gán các thuộc tính khác
                chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString("trangThaiCho"))); // Chuyển đổi String sang Enum
                chiTietCho.setGiaCho(chiTietCho.tinhGiaCho());

                danhSachChiTietCho.add(chiTietCho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        if(dongKetNoi) {
        	ConnectDB.getInstance().disconnect();
        }
        return danhSachChiTietCho;
    }
    
    public boolean capNhatChiTietChoDangDat(ChiTietCho chiTietCho, boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        // Loại bỏ ResultSet vì chúng ta không truy vấn dữ liệu, chỉ cập nhật
        // ResultSet resultSet = null; 
        try {
            con = ConnectDB.getConnection(); // Lấy kết nối từ lớp ConnectDB
            String sql = "UPDATE ChiTietCho\n"
                       + "SET\n"
                       + "  trangThaiCho = 'dangDat'\n"
                       + "WHERE\n"
                       + "  maCho = ? AND maChuyenTau = ?;";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, chiTietCho.getCho().getMaCho());
            preparedStatement.setString(2, chiTietCho.getChuyenTau().getMaChuyenTau()); 

            // Thực thi câu lệnh cập nhật
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để debug
            return false; // Trả về false nếu có lỗi xảy ra
        }finally {
            if(dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
		}
    }
    
    public boolean capNhatChiTietChoConTrong(ChiTietCho chiTietCho, boolean dongKetNoi) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        
        try {
            con = ConnectDB.getConnection(); // Lấy kết nối từ lớp ConnectDB
            String sql = "UPDATE ChiTietCho\n"
                       + "SET\n"
                       + "  trangThaiCho = 'conTrong'\n"
                       + "WHERE\n"
                       + "  maCho = ? AND maChuyenTau = ?;";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, chiTietCho.getCho().getMaCho());
            preparedStatement.setString(2, chiTietCho.getChuyenTau().getMaChuyenTau()); 

            // Thực thi câu lệnh cập nhật
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để debug
            return false; // Trả về false nếu có lỗi xảy ra
        }finally {
            if(dongKetNoi) {
            	ConnectDB.getInstance().disconnect();
            }
		}
    }
    
    public ChiTietCho timChiTietChoTheoChoVaChuyenTau(Cho cho, ChuyenTau chuyenTau, boolean dongKetNoi) {
    	ChiTietCho chiTietCho = new ChiTietCho();
    	Connection con = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try {
    		con = ConnectDB.getConnection();
    		String sql = "SELECT\n"
    				+ "    maCho,\n"
    				+ "    maChuyenTau,\n"
    				+ "    trangThaiCho\n"
    				+ "FROM\n"
    				+ "    ChiTietCho\n"
    				+ "WHERE maCho = ? AND maChuyenTau = ?;";
    		preparedStatement = con.prepareStatement(sql);
    		preparedStatement.setString(1, cho.getMaCho());
    		preparedStatement.setString(2, chuyenTau.getMaChuyenTau());
    		resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()) {
    			chiTietCho.setCho(cho);
    			chiTietCho.setChuyenTau(chuyenTau);
    			chiTietCho.setGiaCho(chiTietCho.tinhGiaCho());
    			chiTietCho.setTrangThaiCho(TrangThaiCho.valueOf(resultSet.getString(3)));
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	if(dongKetNoi) {
    		ConnectDB.getInstance().disconnect();
    	}
    	return chiTietCho;
    }
}
