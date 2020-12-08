package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.DVDDao;
import dao.KhachHangDAO;
import dao.PhieuDatTruocDAO;
import dao.PhieuThueTraDAO;
import dao.TheLoaiDAO;
import dao.TuaDAO;
import entity.DVD;
import entity.KhachHang;
import entity.PhieuDatTruoc;
import entity.PhieuThueTra;
import entity.TheLoai;
import entity.Tua;

public class Testing {
	public static void main(String[] args) throws ParseException {
		DVDDao dvdDAO = new DVDDao();
		KhachHangDAO khachHangDAO = new KhachHangDAO();
		PhieuThueTraDAO phieuThueTraDAO = new PhieuThueTraDAO();
		TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
		TuaDAO tuaDAO = new TuaDAO();
		PhieuDatTruocDAO phieuDatTruocDAO = new PhieuDatTruocDAO();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		KhachHang kh1 = new KhachHang("KH01", "Ha Hung", "0929292921", "2323232323");
		TheLoai tl1 = new TheLoai("TL01", "Film", 100000, 30);
		TheLoai tl2 = new TheLoai("TL02", "Game", 50000, 30);
		Tua tua1 = new Tua("Tua01", "Avatar", tl1);
		Tua tua2 = new Tua("Tua02", "Run", tl2);

		DVD dvd1 = new DVD("DVD01", 1, tua2);
		DVD dvd2 = new DVD("DVD02", 1, tua1);
		DVD dvd3 = new DVD("DVD03", 2, tua1);
		DVD dvd4 = new DVD("DVD03", 3, tua2);
		PhieuDatTruoc pdt1 = new PhieuDatTruoc("PDT01", df.parse("20201010"), kh1, tua1,dvd1,1);
		PhieuThueTra ptt1 = new PhieuThueTra("P01", df.parse("20201010"),dvd2, kh1,df.parse("20201010"), 120000, df.parse("20201010"));
		PhieuThueTra ptt2 = new PhieuThueTra("P02", df.parse("20201010"),dvd1, kh1,df.parse("20201010"), 120000, df.parse("20201010"));

		khachHangDAO.persist(kh1);
		theLoaiDAO.persist(tl1);
		theLoaiDAO.persist(tl2);
		tuaDAO.persist(tua1);
		tuaDAO.persist(tua2);
		tuaDAO.persist(tua2);
		dvdDAO.persist(dvd1);
		dvdDAO.persist(dvd2);
		dvdDAO.persist(dvd3);
		phieuDatTruocDAO.persist(pdt1);
		phieuThueTraDAO.persist(ptt1);
		phieuThueTraDAO.persist(ptt2);
		System.out.println(kh1);

	}
}
