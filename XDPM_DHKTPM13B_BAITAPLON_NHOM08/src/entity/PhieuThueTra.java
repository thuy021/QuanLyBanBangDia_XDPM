package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PhieuThueTra
 *
 */
@Entity
@Table(name = "PhieuThueTra")

public class PhieuThueTra implements Serializable {

	@Id
	private String maPhieu;
	private Date ngayThue;
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "maDVD", referencedColumnName = "maDVD", nullable = false)
	private DVD dvd;

	@ManyToOne
	@JoinColumn(name = "maKH", referencedColumnName = "maKH", nullable = false)
	private KhachHang khachHang;

	private Date ngayTra;
	private double phiTreHen;
	private Date ngayThanhToanPhi;

	public String getMaPhieu() {
		return maPhieu;
	}

	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}

	public Date getNgayThue() {
		return ngayThue;
	}

	public void setNgayThue(Date ngayThue) {
		this.ngayThue = ngayThue;
	}

	public DVD getDvd() {
		return dvd;
	}

	public void setDvd(DVD dvd) {
		this.dvd = dvd;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Date getNgayTra() {
		return ngayTra;
	}

	public void setNgayTra(Date ngayTra) {
		this.ngayTra = ngayTra;
	}

	public double getPhiTreHen() {
		return phiTreHen;
	}

	public void setPhiTreHen(double phiTreHen) {
		this.phiTreHen = phiTreHen;
	}

	public Date getNgayThanhToanPhi() {
		return ngayThanhToanPhi;
	}

	public void setNgayThanhToanPhi(Date ngayThanhToanPhi) {
		this.ngayThanhToanPhi = ngayThanhToanPhi;
	}

	public PhieuThueTra(String maPhieu, KhachHang khachHang) {
		super();
		this.maPhieu = maPhieu;
		this.khachHang = khachHang;
	}

	public PhieuThueTra(String maPhieu, Date ngayThue, KhachHang khachHang, DVD dvd) {
		super();
		this.maPhieu = maPhieu;
		this.ngayThue = ngayThue;
		this.dvd = dvd;
		this.khachHang = khachHang;
	}

	public PhieuThueTra(String maPhieu, Date ngayThue, DVD dvd, KhachHang khachHang, Date ngayTra) {
		super();
		this.maPhieu = maPhieu;
		this.ngayThue = ngayThue;
		this.dvd = dvd;
		this.khachHang = khachHang;
		this.ngayTra = ngayTra;
	}

	public PhieuThueTra(String maPhieu, Date ngayThue, DVD dvd, KhachHang khachHang, Date ngayTra, double phiTreHen) {
		super();
		this.maPhieu = maPhieu;
		this.ngayThue = ngayThue;
		this.dvd = dvd;
		this.khachHang = khachHang;
		this.ngayTra = ngayTra;
		this.phiTreHen = phiTreHen;
	}

	public PhieuThueTra(String maPhieu, Date ngayThue, DVD dvd, KhachHang khachHang, Date ngayTra, double phiTreHen,
			Date ngayThanhToanPhi) {
		super();
		this.maPhieu = maPhieu;
		this.ngayThue = ngayThue;
		this.dvd = dvd;
		this.khachHang = khachHang;
		this.ngayTra = ngayTra;
		this.phiTreHen = phiTreHen;
		this.ngayThanhToanPhi = ngayThanhToanPhi;
	}

	public PhieuThueTra(String maPhieu) {
		super();
		this.maPhieu = maPhieu;
	}

	public PhieuThueTra() {
		super();
	}

}
