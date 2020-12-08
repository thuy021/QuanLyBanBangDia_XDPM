package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PhieuDatTruoc
 *
 */
@Entity
@Table(name = "PhieuDatTruoc")
public class PhieuDatTruoc implements Serializable {

	@Id
	private String maPhieuDatTruoc;
	private Date ngayDatTruoc;
	private int trangThai;
	private static final long serialVersionUID = 1L;
	// Trạng thái: 1 2
	// 1 : đang chờ
	// 2: đã xử lí

	@ManyToOne
	@JoinColumn(name = "maKH", referencedColumnName = "maKH")
	private KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "maTua", referencedColumnName = "maTua")
	private Tua tua;

	@OneToOne
	@JoinColumn(name = "maDVD", referencedColumnName = "maDVD")
	private DVD dvd;

	public PhieuDatTruoc(String maPhieuDatTruoc, Date ngayDatTruoc, KhachHang khachHang, Tua tua,
			DVD dvd, int trangThai) {
		super();
		this.maPhieuDatTruoc = maPhieuDatTruoc;
		this.ngayDatTruoc = ngayDatTruoc;
		this.trangThai = trangThai;
		this.khachHang = khachHang;
		this.tua = tua;
		this.dvd = dvd;
	}

	public String getMaPhieuDatTruoc() {
		return maPhieuDatTruoc;
	}

	public void setMaPhieuDatTruoc(String maPhieuDatTruoc) {
		this.maPhieuDatTruoc = maPhieuDatTruoc;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaDatTruoc() {
		return maPhieuDatTruoc;
	}

	public void setMaDatTruoc(String maPhieuDatTruoc) {
		this.maPhieuDatTruoc = maPhieuDatTruoc;
	}

	public Date getNgayDatTruoc() {
		return ngayDatTruoc;
	}

	public void setNgayDatTruoc(Date ngayDatTruoc) {
		this.ngayDatTruoc = ngayDatTruoc;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Tua getTua() {
		return tua;
	}

	public void setTua(Tua tua) {
		this.tua = tua;
	}

	public DVD getDvd() {
		return dvd;
	}

	public void setDvd(DVD dvd) {
		this.dvd = dvd;
	}

	public PhieuDatTruoc() {
		super();
	}

}
