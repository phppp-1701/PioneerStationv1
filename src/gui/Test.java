package gui;

import java.util.List;

import dao.Ga_DAO;
import dao.NhanVien_DAO;
import entity.Ga;
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
//		NhanVien_DAO nhanVien_DAO = new NhanVien_DAO();
//		NhanVien nv = nhanVien_DAO.timNhanVienTheoMa("2023NV000001");
//		System.out.println(nv.toString());
		
		//Test Ga_DAO
		Ga_DAO ga_dao = new Ga_DAO();
		List<Ga> dsga = ga_dao.layToanBoGa();
		for(Ga ga : dsga) {
			System.out.println(ga.getTenGa());
		}
	}
}
