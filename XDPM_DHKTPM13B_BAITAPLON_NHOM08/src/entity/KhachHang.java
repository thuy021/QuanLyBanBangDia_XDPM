package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: KhachHang
 *
 */
@Entity
@Table(name = "KhachHang")
public class KhachHang implements Serializable {
	@Id
	private String maKH;
	private String tenKH;
	private String soDT;
	private String soCMND;

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDT=" + soDT + ", soCMND=" + soCMND + "]";
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}

	public KhachHang(String maKH, String tenKH, String soDT, String soCMND) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.soDT = soDT;
		this.soCMND = soCMND;
	}

	private static final long serialVersionUID = 1L;

	public KhachHang() {
		super();
	}

}
