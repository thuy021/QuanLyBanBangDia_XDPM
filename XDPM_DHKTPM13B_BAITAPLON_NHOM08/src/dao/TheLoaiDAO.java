package dao;

import java.util.List;

import javax.persistence.Query;

import entity.KhachHang;
import entity.PhieuThueTra;
import entity.TheLoai;

public class TheLoaiDAO extends AbstractCRUD<TheLoai> {
	public boolean xoaTheLoai(TheLoai tt) {
		try {
			trans.begin();
			em.persist(tt);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public List<TheLoai> getAllTheLoai() {
		return em.createQuery("select t from TheLoai t", TheLoai.class).getResultList();
		// return em.createNamedQuery("getAllTheLoai", TheLoai.class).getResultList();
	}

	public List<TheLoai> listAllTL() {
		return em.createQuery("select k from TheLoai k", TheLoai.class).getResultList();
	}

	// Dat gia thue va thoi gian thue
	public boolean updateTheLoai(TheLoai tl) {
		try {
			trans.begin();
			em.merge(tl);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public TheLoai timTLTheoMa(String ma) {
		return em.find(TheLoai.class, ma);
	}

	public TheLoai findTLByTen(String tenTL) {
		TheLoai a = new TheLoai();
		List<TheLoai> lst;
		Query q = em.createQuery("select a from TheLoai a", TheLoai.class);
		lst = q.getResultList();
		for (TheLoai b : lst) {
			if (b.getTenTheLoai().equalsIgnoreCase(tenTL))
				a = b;
		}
		return a;
	}

}
