package dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class MyEntityManager {
	private static MyEntityManager instance;
	private EntityManager em;

	public MyEntityManager() {
		em = Persistence.createEntityManagerFactory("XDPM_DHKTPM13B_BAITAPLON_NHOM08").createEntityManager();
	}

	public synchronized static MyEntityManager getInstance() {
		if (instance == null)
			return instance = new MyEntityManager();
		return instance;
	}

	public EntityManager getEntityManager() {
		return em;
	}
}
