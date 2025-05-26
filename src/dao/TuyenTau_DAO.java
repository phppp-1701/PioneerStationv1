package dao;

import connectDB.ConnectDB;
import entity.Ga;
import entity.TuyenTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TuyenTau_DAO {

	public List<TuyenTau> getDanhSachTuyenTauKhacMa(String maTuyen) {
	    List<TuyenTau> danhSachTuyenTau = new ArrayList<>();
	    String sql = "SELECT * FROM TuyenTau WHERE maTuyenTau != ?";
	    try (Connection conn = ConnectDB.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) { // Thêm ResultSet vào try-with-resources
	        pstmt.setString(1, maTuyen);
	        while (rs.next()) {
	            String maGaDi = rs.getString("gaDi");
	            String maGaDen = rs.getString("gaDen");

	            // Lấy thông tin ga với cùng kết nối
	            Ga gaDi = layGaTheoMa(maGaDi, conn);
	            Ga gaDen = layGaTheoMa(maGaDen, conn);

	            // Kiểm tra null trước khi tạo TuyenTau
	            if (gaDi != null && gaDen != null) {
	                TuyenTau tuyenTau = new TuyenTau(
	                    rs.getString("maTuyenTau"),
	                    rs.getString("tenTuyenTau"),
	                    rs.getDouble("khoangCach"),
	                    gaDi,
	                    gaDen
	                );
	                danhSachTuyenTau.add(tuyenTau);
	            } else {
	          
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	       
	    }
	    return danhSachTuyenTau;
	}

    public List<TuyenTau> timTuyenTauTheoTen(String tenTuyenTau) {
        List<TuyenTau> dsTuyenTau = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Mở kết nối duy nhất
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM TuyenTau WHERE tenTuyenTau LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + tenTuyenTau + "%");

            // Thực thi truy vấn
            rs = stmt.executeQuery();

            // Xử lý kết quả
            while (rs.next()) {
                String maGaDi = rs.getString("gaDi");
                String maGaDen = rs.getString("gaDen");

                // Sử dụng cùng kết nối để lấy ga
                Ga gaDi = layGaTheoMa(maGaDi, con); // Truyền connection vào
                Ga gaDen = layGaTheoMa(maGaDen, con); // Truyền connection vào

                // Tạo và thêm tuyến tàu vào danh sách
                TuyenTau tuyenTau = new TuyenTau(
                    rs.getString("maTuyenTau"),
                    rs.getString("tenTuyenTau"),
                    rs.getDouble("khoangCach"),
                    gaDi,
                    gaDen
                );
                dsTuyenTau.add(tuyenTau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectDB.getInstance().disconnect();
        }

        return dsTuyenTau;
    }

    private Ga layGaTheoMa(String maGa, Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM Ga WHERE maGa = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, maGa);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Ga ga = new Ga();
                ga.setMaGa(rs.getString("maGa"));
                ga.setTenGa(rs.getString("tenGa"));
                ga.setDiaChi(rs.getString("diaChi"));
                return ga;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themTuyenTau(TuyenTau tuyenTau) {
    	String sql = "INSERT INTO TuyenTau (maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
        	stmt.setString(1, tuyenTau.getMaTuyenTau());
            stmt.setString(2, tuyenTau.getTenTuyenTau());
            stmt.setDouble(3, tuyenTau.getKhoangCach());
            stmt.setString(4, tuyenTau.getGaDi().getMaGa());
            stmt.setString(5, tuyenTau.getGaDen().getMaGa());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean capNhatTuyenTau(TuyenTau tuyenTau) {
        String sql = "UPDATE TuyenTau SET tenTuyenTau = ?, khoangCach = ?, gaDi = ?, gaDen = ? WHERE maTuyenTau = ?";
        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tuyenTau.getTenTuyenTau());
            stmt.setDouble(2, tuyenTau.getKhoangCach());
            stmt.setString(3, tuyenTau.getGaDi().getMaGa());
            stmt.setString(4, tuyenTau.getGaDen().getMaGa());
            stmt.setString(5, tuyenTau.getMaTuyenTau());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String taoMaTuyenTauMoi() {
        String maTuyenTauMoi = "";
        try (Connection con = ConnectDB.getConnection()) {
            int namHienTai = Calendar.getInstance().get(Calendar.YEAR);
            String prefix = namHienTai + "TT";

            String sql = "SELECT MAX(maTuyenTau) FROM TuyenTau WHERE maTuyenTau LIKE ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            int soThuTu = 0;
            if (rs.next() && rs.getString(1) != null) {
                String maGaMax = rs.getString(1);
                String soThuTuStr = maGaMax.substring(prefix.length());
                soThuTu = Integer.parseInt(soThuTuStr);
            }

            soThuTu++;
            maTuyenTauMoi = prefix + String.format("%04d", soThuTu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maTuyenTauMoi;
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
    
    public String getTenGaTheoMa(String maGa) {
        String sql = "SELECT tenGa FROM Ga WHERE maGa = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maGa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rs.getString("tenGa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
