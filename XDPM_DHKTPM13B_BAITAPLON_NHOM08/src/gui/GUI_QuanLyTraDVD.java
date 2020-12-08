package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.DVDDao;
import dao.PhieuDatTruocDAO;
import dao.PhieuThueTraDAO;
import defaultTableModel.PhiTreHenTableModel;
import entity.DVD;
import entity.PhieuDatTruoc;
import entity.PhieuThueTra;

public class GUI_QuanLyTraDVD implements ActionListener, MouseListener {
	private JTextField txtTimDVD, txtHoTen, txtCMND, txtSoDienThoai, txtTongTien, txtMaPhieu, txtTua, txtPhi, txtIDKH;
	private JButton btnTimDVD, btnTraDVD, btnTTPhi;
	private GUI_Store store_GUI = new GUI_Store();
	SpinnerNumberModel spSoLuong;
	private JDatePickerImpl jdkNgayThue, jdkNgayTra, jdkNgayTT;
	private JTable tblDVDTra;
	JSpinner spinner;;
	PhieuThueTraDAO phieuThueTraDAO;
	int indextblDVDTra = -1;
	int indexTblGioHang = -1;

//Thông báo chưa làm xong/ chưa làm tìm DVD trong thuê
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		JPanel pnDatLeft = taoPnNhapThongTraDVD();
		conn.add(pnDatLeft);

		JPanel pnDSSPDaChon = pnThanhToanPhiTreHen();
		conn.add(pnDSSPDaChon);
		addEvents();
		return conn;

	}

	private JPanel pnThanhToanPhiTreHen() {
		JPanel pnSPRight = store_GUI.createPannel(600, 30, 700, 220, "Thông Tin Khách Hàng");
		// pnSPRight.setLayout(new BorderLayout());
		pnSPRight.add(store_GUI.createLable(10, 40, 90, 25, "ID Khách Hàng:"));
		txtIDKH = store_GUI.createTextField(110, 40, 200, 25, 150);
		pnSPRight.add(txtIDKH);

		pnSPRight.add(store_GUI.createLable(10, 90, 80, 25, "Họ Tên:"));
		txtHoTen = store_GUI.createTextField(110, 90, 200, 25, 10);
		pnSPRight.add(txtHoTen);

		pnSPRight.add(store_GUI.createLable(360, 40, 80, 25, "Số CMND:"));
		txtCMND = store_GUI.createTextField(460, 40, 200, 25, 11);
		pnSPRight.add(txtCMND);

		pnSPRight.add(store_GUI.createLable(360, 90, 100, 25, "Số Điện Thoại:"));

		txtSoDienThoai = store_GUI.createTextField(460, 90, 200, 25, 11);
		pnSPRight.add(txtSoDienThoai);

		btnTraDVD = store_GUI.createButton(275, 140, 150, 35, "TRẢ DVD");
		pnSPRight.add(btnTraDVD);

		txtHoTen.setEditable(false);
		txtSoDienThoai.setEditable(false);
		txtCMND.setEditable(false);
		txtIDKH.setEditable(false);

		return pnSPRight;
	}

	private JPanel taoPnNhapThongTraDVD() {
		JPanel pnDatLeft = store_GUI.createPannel(10, 30, 540, 520, "Thông Tin Trả DVD");

		JPanel pnTimKiem = store_GUI.createPannel(10, 30, 490, 60, "Tìm Kiếm DVD");
		txtTimDVD = store_GUI.createTextField(100, 20, 180, 30, 100);
		btnTimDVD = store_GUI.createButton(300, 20, 120, 30, "Tìm DVD");
		btnTimDVD.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 80, 30, "Nhập ID:"));
		pnTimKiem.add(txtTimDVD);
		pnTimKiem.add(btnTimDVD);

		pnDatLeft.add(store_GUI.createLable(20, 115, 80, 30, "Ngày thuê:"));
		jdkNgayThue = store_GUI.createJDatePicker(115, 120, 150, 30);
		pnDatLeft.add(store_GUI.createLable(20, 155, 80, 30, "Ngày trả:"));
		jdkNgayTra = store_GUI.createJDatePicker(115, 165, 150, 30);
		pnDatLeft.add(jdkNgayThue);
		pnDatLeft.add(jdkNgayTra);

		JPanel pnThanhToanPhi = store_GUI.createPannel(20, 215, 370, 215, "Thông Tin Thanh Toán Phí Trễ Hẹn");

		pnThanhToanPhi.add(store_GUI.createLable(10, 30, 90, 30, "Mã Phiếu:"));
		txtMaPhieu = store_GUI.createTextField(125, 30, 200, 25, 150);
		pnThanhToanPhi.add(txtMaPhieu);

		pnThanhToanPhi.add(store_GUI.createLable(10, 70, 80, 30, "Tựa DVD:"));
		txtTua = store_GUI.createTextField(125, 70, 200, 25, 150);
		pnThanhToanPhi.add(txtTua);

		pnThanhToanPhi.add(store_GUI.createLable(10, 110, 80, 25, "Phí Trễ Hẹn:"));
		txtPhi = store_GUI.createTextField(125, 110, 200, 25, 10);
		pnThanhToanPhi.add(txtPhi);

		pnThanhToanPhi.add(store_GUI.createLable(10, 150, 100, 25, "Ngày Thanh Toán"));
		jdkNgayTT = store_GUI.createJDatePicker(125, 150, 150, 30);
		pnThanhToanPhi.add(jdkNgayTT);

		txtTua.setEditable(false);
		txtPhi.setEditable(false);
		txtMaPhieu.setEditable(false);

		btnTTPhi = store_GUI.createButton(100, 450, 150, 35, "THANH TOÁN PHÍ");
		pnDatLeft.add(btnTTPhi);

		pnDatLeft.add(pnTimKiem);
		pnDatLeft.add(pnThanhToanPhi);

		return pnDatLeft;
	}

	/**
	 * 5.ĐĂNG KÍ CÁC SỰ KIỆN TRONG FORM THANH TOÁN GIỎ HÀNG.
	 */

	private void addEvents() {
		btnTimDVD.addActionListener(this);
		btnTTPhi.addActionListener(this);
		btnTraDVD.addActionListener(this);
	}

	public void Fill(PhieuThueTra a) {
		txtMaPhieu.setText(String.valueOf(a.getMaPhieu()));
		txtTua.setText(String.valueOf(a.getDvd().getTua().getTenTua()));
		txtIDKH.setText(String.valueOf(a.getKhachHang().getMaKH()));
		txtHoTen.setText(String.valueOf(a.getKhachHang().getTenKH()));
		txtCMND.setText(String.valueOf(a.getKhachHang().getSoCMND()));
		txtSoDienThoai.setText(String.valueOf(a.getKhachHang().getSoDT()));
		Date ngayThue = a.getNgayThue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(ngayThue);
		jdkNgayThue.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	public void tinhPhi() {
		Date ngayTra = (Date) jdkNgayTra.getModel().getValue();
		Date ngayThue = (Date) jdkNgayThue.getModel().getValue();
		Calendar cTra = Calendar.getInstance();
		Calendar cThue = Calendar.getInstance();
		cTra.setTime(ngayTra);
		cThue.setTime(ngayThue);
		int soNgayThue = (int) ((cTra.getTime().getTime() - cThue.getTime().getTime()) / (24 * 3600 * 1000));
		System.out.println(soNgayThue);
	}

	public void xoaRong() {
		txtTimDVD.setText(null);
		txtMaPhieu.setText(null);
		txtTua.setText(null);
		txtPhi.setText(null);
		txtIDKH.setText(null);
		txtHoTen.setText(null);
		txtCMND.setText(null);
		txtSoDienThoai.setText(null);
		txtTimDVD.setEditable(true);
		btnTTPhi.setEnabled(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnTimDVD)) {
			phieuThueTraDAO = new PhieuThueTraDAO();
			PhieuThueTra p = (PhieuThueTra) phieuThueTraDAO.findPhieuByDVD(String.valueOf(txtTimDVD.getText()));
			if (txtTimDVD.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID DVD");
			} else if (p.getMaPhieu() == null) {
				JOptionPane.showMessageDialog(null, "DVD Này Chưa Được Thuê!");
			} else if (p.getNgayTra() != null) {
				JOptionPane.showMessageDialog(null, "DVD Này Đã Được Trả!");
			} else if (p != null) {
				Fill(p);

			}
		}
		if (obj.equals(btnTTPhi)) {
			if (!txtPhi.getText().equals("")) {
				phieuThueTraDAO = new PhieuThueTraDAO();
				PhieuThueTra a = (PhieuThueTra) phieuThueTraDAO.findPhieuByDVD(String.valueOf(txtTimDVD.getText()));
				int value = JOptionPane.showConfirmDialog(null,
						"Bạn Có Muốn Thanh Toán Phí Trễ Hẹn Không?", "Thông báo xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (value == JOptionPane.YES_OPTION) {
					a.setPhiTreHen(0);
					Date ngayTT = (Date) jdkNgayTT.getModel().getValue();
					a.setNgayThanhToanPhi(ngayTT);
					phieuThueTraDAO.merge(a);
					JOptionPane.showMessageDialog(null, "Thanh Toán Phí Trễ Hẹn Thành Công!");
					txtPhi.setText(String.valueOf(a.getPhiTreHen()));
				} else if (value == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Phí Đã Được Cập Nhật Vào Hệ Thống!");
				}
			}

		}
		if (obj.equals(btnTraDVD)) {
			if (txtTimDVD.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID DVD");
			} else {

				DVDDao dvdDao = new DVDDao();
				phieuThueTraDAO = new PhieuThueTraDAO();
				PhieuThueTra a = (PhieuThueTra) phieuThueTraDAO.findPhieuByDVD(String.valueOf(txtTimDVD.getText()));
				String maDVD = a.getDvd().getMaDVD();
				DVD dvd = dvdDao.timDVDTheoMa(maDVD);
				Date ngayTra = (Date) jdkNgayTra.getModel().getValue();
				a.setNgayTra(ngayTra);
				phieuThueTraDAO.merge(a);
				// PHẦN SET TRẠNG THÁI CHO DVD ONHOLD - ONSHELF

				PhieuDatTruocDAO phieuDatTruocDAO = new PhieuDatTruocDAO();
				List<PhieuDatTruoc> lst = phieuDatTruocDAO.getAllPhieuDatTruoc();
				for (PhieuDatTruoc b : lst) {
					if (b.getTua().getTenTua().equalsIgnoreCase(a.getDvd().getTua().getTenTua())&& b.getDvd()==null) {
						String ten = b.getKhachHang().getTenKH();
						String sdt = b.getKhachHang().getSoDT();
						int value = JOptionPane.showConfirmDialog(null,
								"Tựa DVD Này Đang Được Đặt Trước Bởi:\n Khách hàng: "+ten+"\nSố điện thoại: "+sdt+"\nBạn Có Muốn Gán DVD Này Cho Khách Hàng Không?",
								"Thông báo xác nhận", JOptionPane.YES_NO_OPTION);
						if (value == JOptionPane.YES_OPTION) {
							dvd.setTrangThai(3);
							b.setDvd(dvd);
							b.setTrangThai(2);
							dvdDao.merge(dvd);
							phieuDatTruocDAO.merge(b);
							JOptionPane.showMessageDialog(null, "Gán DVD Thành Công!");
						} else if (value == JOptionPane.NO_OPTION) {
							phieuDatTruocDAO.removePhieuDT(b);
							dvd.setTrangThai(1);
							dvdDao.merge(dvd);
						}
					}
				}
				JOptionPane.showMessageDialog(null, "Trả DVD Thành Công!");
				txtTimDVD.setEditable(true);
				// PHẦN THANH TOÁN PHÍ TRỄ HẸN
				phieuThueTraDAO = new PhieuThueTraDAO();
				PhieuThueTra p = (PhieuThueTra) phieuThueTraDAO.findPhieuByDVD(String.valueOf(txtTimDVD.getText()));
				Date ngayThue = (Date) jdkNgayThue.getModel().getValue();
				Calendar cTra = Calendar.getInstance();
				Calendar cThue = Calendar.getInstance();
				cTra.setTime(ngayTra);
				cThue.setTime(ngayThue);
				int soNgayThue = (int) ((cTra.getTime().getTime() - cThue.getTime().getTime()) / (24 * 3600 * 1000));
				if (soNgayThue <= p.getDvd().getTua().getTheLoai().getThoiGianThue()) {
					JOptionPane.showMessageDialog(null, "DVD Không Có Phí Trễ Hẹn!");
					btnTTPhi.setEnabled(false);
				} else {
					p.setPhiTreHen(p.getDvd().getTua().getTheLoai().getGiaThue() * 0.1);
					phieuThueTraDAO.merge(p);
					JOptionPane.showMessageDialog(null, "DVD Có Phí Trễ Hẹn!");
					txtPhi.setText(String.valueOf((p.getDvd().getTua().getTheLoai().getGiaThue()) * 0.1));
				}
				
				// xoaRong();
			}
		}
	}

}
