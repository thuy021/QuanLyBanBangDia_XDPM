package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: DVD
 *
 */
@Entity
@Table(name = "DVD")
@NamedQueries({ @NamedQuery(name = "getAllDVDOnShelf", query = "select k from DVD k where k.trangThai = 1"),
		@NamedQuery(name = "getDVDByMa", query = "select d from DVD d where d.maDVD = :maDVD"),
		@NamedQuery(name = "getAllDVD", query = "select d from DVD d") })
public class DVD implements Serializable {

	@Id
	private String maDVD;
	private int trangThai;

	@ManyToOne
	@JoinColumn(name = "maTua", referencedColumnName = "maTua")
	private Tua tua;

	public String getMaDVD() {
		return maDVD;
	}

	public void setMaDVD(String maDVD) {
		this.maDVD = maDVD;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public Tua getTua() {
		return tua;
	}

	public void setTua(Tua tua) {
		this.tua = tua;
	}

	public DVD(String maDVD, int trangThai, Tua tua) {
		super();
		this.maDVD = maDVD;
		this.trangThai = trangThai;
		this.tua = tua;
	}

	@Override
	public String toString() {
		if (trangThai == 1)
			return "OnShelf";
		else if (trangThai == 2)
			return "Rented";
		else
			return "OnHold";
	}

	private static final long serialVersionUID = 1L;

	public DVD() {
		super();
	}

}
