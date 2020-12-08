package dao;

import java.util.List;

import javax.persistence.Query;

import entity.PhieuThueTra;
import entity.TheLoai;
import entity.Tua;

public class TuaDAO extends AbstractCRUD<Tua> {
	public boolean themTua(Tua tua) {
		try {
			trans.begin();
			em.persist(tua);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Tua> getAllTua(){
		return em.createQuery("select k from Tua k", Tua.class).getResultList();
	}
	
	public boolean xoaTua(Tua tua) {
		try {
			trans.begin();
			em.remove(tua);
			trans.commit();
			return true;
		}catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}
	public Tua findTuaByTenTua(String tua) {
		Tua a = new Tua();
		List<Tua> lst;
		Query q = em.createQuery("select a from Tua a", Tua.class);
		lst = q.getResultList();
		for (Tua b : lst) {
			if (b.getTenTua().equalsIgnoreCase(tua))
				a=b;
		}

		return a;
	}
}
