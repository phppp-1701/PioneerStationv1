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

	// tao mã hóa đơn mới
	public String taoMaHoaDonMoi() {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ConnectDB.getConnection();

			// Lấy ngày hiện tại dưới dạng yyyyMMdd
			String ngayHienTai = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			// Tìm hóa đơn có mã lớn nhất trong ngày
			String sql = "SELECT MAX(maHoaDon) FROM HoaDon WHERE maHoaDon LIKE ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ngayHienTai + "HD%");
			rs = stmt.executeQuery();

			int soThuTu = 1;
			if (rs.next()) {
				String maMax = rs.getString(1);
				if (maMax != null) {
					// Lấy phần số từ mã hóa đơn (6 chữ số cuối)
					String soStr = maMax.substring(maMax.length() - 6);
					soThuTu = Integer.parseInt(soStr) + 1;
				}
			}

			// Định dạng số thứ tự thành 6 chữ số, thêm số 0 ở đầu nếu cần
			String soThuTuStr = String.format("%06d", soThuTu);

			// Tạo mã hóa đơn mới: yyyyMMdd + "HD" + 6 chữ số
			return ngayHienTai + "HD" + soThuTuStr;

		} catch (SQLException e) {
			e.printStackTrace();
			// Trường hợp lỗi, trả về mã mặc định
			return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "HD000001";
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
	

}
