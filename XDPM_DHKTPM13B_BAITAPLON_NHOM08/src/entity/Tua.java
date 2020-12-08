package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Tua
 *
 */
@Entity

public class Tua implements Serializable {
	@Id
	private String maTua;
	private String tenTua;

	@ManyToOne
	@JoinColumn(name = "maTheLoai", referencedColumnName = "maTheLoai")
	private TheLoai theLoai;

	public Tua(String maTua, String tenTua, TheLoai theLoai) {
		super();
		this.maTua = maTua;
		this.tenTua = tenTua;
		this.theLoai = theLoai;
	}

	public String getMaTua() {
		return maTua;
	}

	public void setMaTua(String maTua) {
		this.maTua = maTua;
	}

	public String getTenTua() {
		return tenTua;
	}

	public void setTenTua(String tenTua) {
		this.tenTua = tenTua;
	}

	public TheLoai getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(TheLoai theLoai) {
		this.theLoai = theLoai;
	}

	@Override
	public String toString() {
		return tenTua;
	}

	private static final long serialVersionUID = 1L;

	public Tua() {
		super();
	}

}
