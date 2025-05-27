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
					+ "phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, "
					+ "ngayLamViec, maCaLam, maNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = connection.prepareStatement(sql);
			stmt.setString(1, hoaDon.getMaHoaDon());
			stmt.setDate(2, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
			stmt.setTime(3, Time.valueOf(hoaDon.getGioTaoHoaDon()));
			stmt.setString(4, hoaDon.getPhuongThucThanhToan().toString());
			stmt.setDouble(5, hoaDon.getPhanTramGiamGia());
			stmt.setDouble(6, hoaDon.getThanhTien());
			stmt.setDouble(7, hoaDon.getTienKhachDua());
			stmt.setDouble(8, hoaDon.getTienTraLai());
			String maKhachHang = hoaDon.getKhachHang().getMaKhachHang();
			stmt.setString(9, maKhachHang);
			String maKhuyenMai = hoaDon.getKhuyenMai().getMaKhuyenMai();
			stmt.setString(10, maKhuyenMai);
			String maNhanVien = hoaDon.getNhanVien().getMaNhanVien();
			stmt.setString(11, maNhanVien);

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
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
			while(rs.next()) {
				hoaDon.setMaHoaDon(rs.getString(1));
				hoaDon.setNgayTaoHoaDon(rs.getDate(2).toLocalDate());
				hoaDon.setGioTaoHoaDon(rs.getTime(3).toLocalTime());
				hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString(4)));
				hoaDon.setPhanTramGiamGia(rs.getDouble(5));
				hoaDon.setThanhTien(rs.getDouble(6));
				hoaDon.setTienKhachDua(rs.getDouble(7));
				hoaDon.setTienTraLai(rs.getDouble(8));
				KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
				KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString(9), false);
				hoaDon.setKhachHang(kh);
				KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
				KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString(10), false);
				hoaDon.setKhuyenMai(km);
				NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
				NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString(11), false);
				hoaDon.setNhanVien(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return null;
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
				hoaDon.setMaHoaDon(rs.getString(1));
				hoaDon.setNgayTaoHoaDon(rs.getDate(2).toLocalDate());
				hoaDon.setGioTaoHoaDon(rs.getTime(3).toLocalTime());
				hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString(4)));
				hoaDon.setPhanTramGiamGia(rs.getDouble(5));
				hoaDon.setThanhTien(rs.getDouble(6));
				hoaDon.setTienKhachDua(rs.getDouble(7));
				hoaDon.setTienTraLai(rs.getDouble(8));
				KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
				KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString(9), false);
				hoaDon.setKhachHang(kh);
				KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
				KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString(10), false);
				hoaDon.setKhuyenMai(km);
				NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
				NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString(11), false);
				hoaDon.setNhanVien(nv);
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
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
					+ "maKhachHang = ?, maKhuyenMai = ?, ngayLamViec = ?, maCaLam = ?, maNhanVien = ? "
					+ "WHERE maHoaDon = ?";

			stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(hoaDon.getNgayTaoHoaDon()));
			stmt.setTime(2, Time.valueOf(hoaDon.getGioTaoHoaDon()));
			stmt.setString(3, hoaDon.getPhuongThucThanhToan().toString());
			stmt.setDouble(4, hoaDon.getPhanTramGiamGia());
			stmt.setDouble(5, hoaDon.getThanhTien());
			stmt.setDouble(6, hoaDon.getTienKhachDua());
			stmt.setDouble(7, hoaDon.getTienTraLai());
			stmt.setString(8, hoaDon.getKhachHang().getMaKhachHang());
			stmt.setString(9, hoaDon.getKhuyenMai().getMaKhuyenMai());
			stmt.setString(10, hoaDon.getNhanVien().getMaNhanVien());
			stmt.setString(11, hoaDon.getMaHoaDon());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
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
		}  finally {
			ConnectDB.getInstance().disconnect();
		}
	}

	// Tra cứu hóa đơn linh hoạt theo số điện thoại và tên khách hàng (không bắt
	// buộc ngày)
	public List<HoaDon> traCuuHoaDonTheoSDT_Ten(String soDienThoai, String tenKhachHang, boolean dongKetNoi) {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT hd.* FROM HoaDon hd ");
			sql.append("JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang ");
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

			// Set parameters
			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, params.get(i));
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				HoaDon hoaDon = new HoaDon();
				hoaDon.setMaHoaDon(rs.getString(1));
				hoaDon.setNgayTaoHoaDon(rs.getDate(2).toLocalDate());
				hoaDon.setGioTaoHoaDon(rs.getTime(3).toLocalTime());
				hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString(4)));
				hoaDon.setPhanTramGiamGia(rs.getDouble(5));
				hoaDon.setThanhTien(rs.getDouble(6));
				hoaDon.setTienKhachDua(rs.getDouble(7));
				hoaDon.setTienTraLai(rs.getDouble(8));
				KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
				KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString(9), false);
				hoaDon.setKhachHang(kh);
				KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
				KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString(10), false);
				hoaDon.setKhuyenMai(km);
				NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
				NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString(11), false);
				hoaDon.setNhanVien(nv);
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
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
				hoaDon.setMaHoaDon(rs.getString(1));
				hoaDon.setNgayTaoHoaDon(rs.getDate(2).toLocalDate());
				hoaDon.setGioTaoHoaDon(rs.getTime(3).toLocalTime());
				hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString(4)));
				hoaDon.setPhanTramGiamGia(rs.getDouble(5));
				hoaDon.setThanhTien(rs.getDouble(6));
				hoaDon.setTienKhachDua(rs.getDouble(7));
				hoaDon.setTienTraLai(rs.getDouble(8));
				KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
				KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString(9), false);
				hoaDon.setKhachHang(kh);
				KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
				KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString(10), false);
				hoaDon.setKhuyenMai(km);
				NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
				NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString(11), false);
				hoaDon.setNhanVien(nv);
				danhSachHoaDon.add(hoaDon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
		}
		return danhSachHoaDon;
	}

	// tra cứu hóa đơn theo số điện thoại, tên khách hàng và ngày tạo hóa đơn
	public List<HoaDon> traCuuHoaDonTheoSDT_Ten_Ngay(String soDienThoai, String tenKhachHang, LocalDate ngayTaoHoaDon, boolean dongKetNoi) {
		List<HoaDon> danhSachHoaDon = new ArrayList<>();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();
			String sql = "SELECT hd.* FROM HoaDon hd " + "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
					+ "WHERE kh.soDienThoai LIKE ? AND kh.tenKhachHang LIKE ? AND hd.ngayTaoHoaDon = ?";
			stmt = connection.prepareStatement(sql);

			// Thêm % để tìm kiếm gần đúng
			stmt.setString(1, "%" + soDienThoai + "%");
			stmt.setString(2, "%" + tenKhachHang + "%");
			stmt.setDate(3, Date.valueOf(ngayTaoHoaDon));

			rs = stmt.executeQuery();

			while (rs.next()) {
				HoaDon hoaDon = new HoaDon();
				hoaDon.setMaHoaDon(rs.getString(1));
				hoaDon.setNgayTaoHoaDon(rs.getDate(2).toLocalDate());
				hoaDon.setGioTaoHoaDon(rs.getTime(3).toLocalTime());
				hoaDon.setPhuongThucThanhToan(PhuongThucThanhToan.valueOf(rs.getString(4)));
				hoaDon.setPhanTramGiamGia(rs.getDouble(5));
				hoaDon.setThanhTien(rs.getDouble(6));
				hoaDon.setTienKhachDua(rs.getDouble(7));
				hoaDon.setTienTraLai(rs.getDouble(8));
				KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
				KhachHang kh = khachHang_DAO.timKhachHangTheoMa(rs.getString(9), false);
				hoaDon.setKhachHang(kh);
				KhuyenMai_DAO khuyenMai_DAO = new KhuyenMai_DAO();
				KhuyenMai km = khuyenMai_DAO.timKhuyenMaiTheoMa(rs.getString(10), false);
				hoaDon.setKhuyenMai(km);
				NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
				NhanVien nv = nhanVien_DAO.timNhanVienTheoMa(rs.getString(11), false);
				hoaDon.setNhanVien(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dongKetNoi) {
			ConnectDB.getInstance().disconnect();
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
	        // Get today's date in ddMMyyyy format
	        LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	        String datePrefix = today.format(formatter); // e.g., "27052025"
	        String prefix = datePrefix + maNhanVien; // e.g., "270520252024NV000002"

	        // SQL query to find the last maHoaDon for today and maNhanVien
	        String sql = "SELECT maHoaDon " +
	                    "FROM HoaDon " +
	                    "WHERE maHoaDon LIKE ? " +
	                    "ORDER BY maHoaDon DESC " +
	                    "TOP 1";
	        preparedStatement = con.prepareStatement(sql);
	        preparedStatement.setString(1, prefix + "%"); // Match prefix + any sequence
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            ma = resultSet.getString(1); // e.g., "270520252024NV000002000002"
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
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
	    
	    // Get today's date in ddMMyyyy format
	    LocalDate today = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	    String datePart = today.format(formatter); // e.g., "27052025"
	    
	    // Validate maNhanVien
	    if (maNhanVien == null || maNhanVien.isEmpty()) {
	        throw new IllegalArgumentException("Mã nhân viên không được để trống!");
	    }
	    
	    // Get the most recent maHoaDon for today and maNhanVien
	    String maGanNhat = layMaHoaDonCuoiCung(maNhanVien, true);
	    
	    if (maGanNhat == null || maGanNhat.isEmpty()) {
	        // No invoice exists for today and maNhanVien, start with 000001
	        maMoi = datePart + maNhanVien + "000001";
	    } else {
	        // Extract the 6-digit sequence number from maGanNhat
	        String sequencePart = maGanNhat.substring(maGanNhat.length() - 6); // e.g., "000001"
	        try {
	            int sequenceNumber = Integer.parseInt(sequencePart); // e.g., 1
	            sequenceNumber++; // Increment to next number
	            // Format new sequence with 6 digits
	            String newSequence = String.format("%06d", sequenceNumber); // e.g., "000002"
	            // Combine parts
	            maMoi = datePart + maNhanVien + newSequence;
	        } catch (NumberFormatException e) {
	            // Fallback if sequence is invalid
	            System.err.println("Lỗi phân tích sequence: " + e.getMessage());
	            maMoi = datePart + maNhanVien + "000001";
	        }
	    }
	    
	    System.out.println("Generated maHoaDonMoi: " + maMoi);
	    return maMoi;
	}
}
