package dao;

import java.util.List;

import entity.KhachHang;

public class KhachHangDAO extends AbstractCRUD<KhachHang> {

	public KhachHang timKHTheoMa(String ma) {
		return em.find(KhachHang.class, ma);
	}

	// THÔNG
	/* Get danh sách khách hàng */
	public List<KhachHang> getAllKH() {
		return em.createQuery("select k from KhachHang k", KhachHang.class).getResultList();
	}

	/* Thêm khách hàng */
	public boolean themKH(KhachHang kh) {
		try {
			trans.begin();
			em.persist(kh);
			trans.commit();
			return true;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/* Xoá khách hàng */
	public boolean xoaKH(KhachHang kh) {
		try {
			trans.begin();
			em.remove(kh);
			trans.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/* Sửa khách hàng */
	public boolean capNhatKH(KhachHang kh) {
		try {
			trans.begin();
			em.merge(kh);
			trans.commit();
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			e.printStackTrace();
		}
		return false;
	}

}
