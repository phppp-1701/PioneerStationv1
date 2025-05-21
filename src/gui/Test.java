package gui;

import java.util.List;

import dao.NhanVien_DAO;
import entity.NhanVien;

public class Test {
	public static void main(String[] args) {
		//Test NhanVien_DAO xuatToanBoDanhSachNhanVien
//		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
//		List<NhanVien> dsnv = nhanVien_DAO.xuatToanBoDanhSachNhanVien();
//		for(NhanVien nv : dsnv) {
//			System.out.println(nv.getMaNhanVien());
//		}
		
		//Test NhanVien_DAO timNhanVienTheoMa
		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa("2023NV000001");
		System.out.println(nv.toString());
	}
}
