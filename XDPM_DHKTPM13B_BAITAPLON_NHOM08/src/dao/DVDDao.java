package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import entity.DVD;

public class DVDDao extends AbstractCRUD<DVD> {

	// Thúy Danh sách DVD
	public List<DVD> listDVD() {
		return em.createNamedQuery("getAllDVDOnShelf", DVD.class).getResultList();
	}

	/*
	 * @author: Phan Bach Thêm mới DVD
	 */
	public boolean themDVD(DVD dvd) {
		try {
			trans.begin();
			em.persist(dvd);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * @author: Phan Bach Tìm DVD theo mã
	 */
	public DVD timDVDTheoMa(String ma) {

		return em.find(DVD.class, ma);
	}

	/*
	 * (C) Copyright 2020 . All rights reserved.
	 *
	 * @author: Phan Bach Lấy tất cả DVD hiện có
	 */
	public List<DVD> getAllDVD() {

		return em.createNamedQuery("getAllDVD", DVD.class).getResultList();
	}

	/*
	 * @author: Phan Bach Xóa DVD
	 */
	public boolean deleteDVD(DVD dvd) {
		try {
			trans.begin();
			em.remove(dvd);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * @author: Phan Bach Cập nhật thông tin DVD
	 */
	public boolean updateDVD(DVD dvd) {
		try {
			trans.begin();
			em.merge(dvd);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	public List<DVD> findDVDByTua(String tenTua) {
		List<DVD> list;
		List<DVD> lst = new ArrayList<DVD>();
		Query q = em.createQuery("select a from DVD a", DVD.class);
		list = q.getResultList();
		for (DVD a : list) {
			if (a.getTua().getTenTua().equals(tenTua) && a.getTrangThai()==1)
				lst.add(a);
		}

		return lst;
	}

}
