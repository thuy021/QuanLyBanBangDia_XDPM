package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import entity.PhieuThueTra;
import entity.TheLoai;

public class PhieuThueTraDAO extends AbstractCRUD<PhieuThueTra> {
	public boolean themPhieuThueTra(PhieuThueTra phieuTT) {
		try {
			trans.begin();
			em.persist(phieuTT);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public List<PhieuThueTra> findPhieuByMa(String ma) {
		return em.createQuery("select t from PhieuTreHen t", PhieuThueTra.class).getResultList();
	}

	public List<PhieuThueTra> findPhieuDaCoPhi(String maKH) {
		List<PhieuThueTra> list;
		List<PhieuThueTra> lst = new ArrayList<PhieuThueTra>();
		Query q = em.createQuery("select a from PhieuThueTra a", PhieuThueTra.class);
		list = q.getResultList();
		for (PhieuThueTra a : list) {
			if (a.getKhachHang().getMaKH().equalsIgnoreCase(maKH) && a.getPhiTreHen() > 0.0)
				lst.add(a);
		}

		return lst;
	}
	
	public PhieuThueTra findPhieuByDVD(String maDVD) {
		PhieuThueTra a = new PhieuThueTra();
		List<PhieuThueTra> lst;
		Query q = em.createQuery("select a from PhieuThueTra a", PhieuThueTra.class);
		lst = q.getResultList();
		for (PhieuThueTra b : lst) {
			if (b.getDvd().getMaDVD().equalsIgnoreCase(maDVD))
				a=b;
		}

		return a;
	}
	public PhieuThueTra getPhieuThueTra(String ma) {
		return em.find(PhieuThueTra.class, ma);
	}
}
