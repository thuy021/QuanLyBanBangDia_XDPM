package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TheLoai
 *
 */
@Entity
@Table(name = "TheLoai")
@NamedNativeQueries({
	@NamedNativeQuery(name = "getAllTheLoai", query = "select t from TheLoai t")
})
public class TheLoai implements Serializable {

	@Id
	private String maTheLoai;
	private String tenTheLoai;
	private double giaThue;
	private int thoiGianThue;
	private static final long serialVersionUID = 1L;

	public String getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(String maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public String getTenTheLoai() {
		return tenTheLoai;
	}

	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}

	public double getGiaThue() {
		return giaThue;
	}

	public void setGiaThue(double giaThue) {
		this.giaThue = giaThue;
	}

	public int getThoiGianThue() {
		return thoiGianThue;
	}

	public void setThoiGianThue(int thoiGianThue) {
		this.thoiGianThue = thoiGianThue;
	}

	public TheLoai(String maTheLoai, String tenTheLoai, double giaThue, int thoiGianThue) {
		super();
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
		this.giaThue = giaThue;
		this.thoiGianThue = thoiGianThue;
	}

	

	@Override
	public String toString() {
		return tenTheLoai;
	}

	public TheLoai() {
		super();
	}

}
