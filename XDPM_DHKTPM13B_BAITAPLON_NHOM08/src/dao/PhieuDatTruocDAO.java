package dao;

import java.util.List;

import javax.persistence.Query;

import entity.PhieuDatTruoc;
import entity.PhieuThueTra;

public class PhieuDatTruocDAO extends AbstractCRUD<PhieuDatTruoc> {
	public boolean removePhieuDT(PhieuDatTruoc dt) {
		try {
			trans.begin();
			em.remove(dt);;
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public List<PhieuDatTruoc> getAllPhieuDatTruoc() {
		return em.createQuery("select p from PhieuDatTruoc p", PhieuDatTruoc.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PhieuDatTruoc> showDSKHDatTruoc() {
		return em.createQuery("Select pdt from PhieuDatTruoc pdt").getResultList();
	}
	
	public PhieuDatTruoc findPhieuByMaDVD(String maDVD) {
		PhieuDatTruoc a = new PhieuDatTruoc();
		List<PhieuDatTruoc> lst;
		Query q = em.createQuery("select l from PhieuDatTruoc l", PhieuDatTruoc.class);
		lst = q.getResultList();
		for (PhieuDatTruoc b : lst) {
			if (b.getTua().getTenTua().equalsIgnoreCase(maDVD))
				a=b;
		}
		return a;
	}

}
