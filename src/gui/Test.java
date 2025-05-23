package gui;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import dao.Tau_DAO;
import dao.Ve_DAO;
import entity.HoaDon;
import entity.HoaDon.PhuongThucThanhToan;
import entity.KhachHang;
import entity.NhanVien;
import entity.NhanVien.ChucVu;
import entity.NhanVien.GioiTinh;
import entity.NhanVien.TrangThaiNhanVien;
import entity.TaiKhoan;
import entity.Tau;
import entity.Ve;

public class Test {
	public static void main(String[] args) {
		// Test NhanVien_DAO
		// Test NhanVien_DAO timNhanVienTheoMa
		//Test NhanVien_DAO xuatToanBoDanhSachNhanVien
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		List<NhanVien> dsnv = nhanVien_DAO.timNhanVienTheoTenVaSoDienThoai("Phạm Trương Hoàng Phương", "0901234567");
		for(NhanVien nv : dsnv) {
			System.out.println(nv.getMaNhanVien());
		}

	}
}
