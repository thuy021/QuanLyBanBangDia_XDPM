package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import com.alee.extended.panel.CenterPanel;

import dao.DVDDao;
import dao.KhachHangDAO;
import dao.PhieuThueTraDAO;
import defaultTableModel.DVDTableModel;
import defaultTableModel.DVDThueTableModel;
import defaultTableModel.PhiTreHenTableModel;
import entity.DVD;
import entity.KhachHang;
import entity.PhieuThueTra;
import entity.TheLoai;
import entity.Tua;

public class GUI_QuanLyThueDVD implements ActionListener, MouseListener {
	private JTextField txtTimKhachHang, txtHoTen, txtCMND, txtSoDienThoai, txtTongTien, txtTimDVD, txtTaoPhieuThue;
	private JButton btnTimKhachHang, btnThanhToan, btnTimDVD, btnTinhTien, btnThue, btnXoaPhi, btnChonPhi;
	private GUI_Store store_GUI = new GUI_Store();
	private DVDThueTableModel dvdThueTableModel;
	private JDatePickerImpl jdkNgayThanhToan;
	private JTable tblDVD, tblDVDThue, tblPhi, tblPhiDaChon;
	int indextblDVD = -1, indexPhi = -1, indexPhiDaChon = -1, indexDVDDaChon = -1;
	private KhachHangDAO khachHangDAO;
	private PhiTreHenTableModel phiTreHenTableModel;
	private List<PhieuThueTra> listPhieuThueTra;
	private PhieuThueTraDAO phieuThueTraDAO = new PhieuThueTraDAO();
	private PhieuThueTra phiTreHen;
	private PhieuThueTra phieuMoi;
	List<PhieuThueTra> lstPhiIndex = new ArrayList<PhieuThueTra>();
	List<DVD> lstDVDIndex;
	List<DVD> lstDVDThue = new ArrayList<DVD>();
	private double tongTien = 0;
	private DVDDao dvdDao = new DVDDao();

	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		JPanel pnDatLeft = taoPnNhapThongThueDVD();
		conn.add(pnDatLeft);
		conn.add(store_GUI.createLable(610, 45, 110, 30, "Tạo Phiếu Thuê"));
		txtTaoPhieuThue = store_GUI.createTextField(730, 45, 130, 30, 100);
		JPanel pnTimKiem = store_GUI.createPannel(890, 25, 410, 55, "Tìm kiếm");
		txtTimDVD = store_GUI.createTextField(120, 20, 150, 30, 100);
		btnTimDVD = store_GUI.createButton(270, 20, 80, 30, "Tìm DVD");
		btnTimDVD.setIcon(store_GUI.taonICon("search.png", 20, 20));

		jdkNgayThanhToan = store_GUI.createJDatePicker(610, 10, 150, 30);
		conn.add(jdkNgayThanhToan);
		pnTimKiem.add(txtTimDVD);
		pnTimKiem.add(btnTimDVD);
		pnTimKiem.add(store_GUI.createLable(10, 20, 96, 25, "Nhập Tên Tựa:"));
		conn.add(pnTimKiem);
		conn.add(txtTaoPhieuThue);
		JPanel pnDSPhong = taoPnDanhSachDVD();
		conn.add(pnDSPhong);

		btnThue = store_GUI.createButton(850, 290, 90, 30, "THUÊ DVD");
		conn.add(btnThue);
		JPanel pnThongTinThanhToan = pnThongTinThanhToan();
		conn.add(pnThongTinThanhToan);
		JPanel pnDSSPDaChon = pnDanhSPDaChon();
		conn.add(pnDSSPDaChon);
		addEvents();
		reSetDataDVD();
		return conn;

	}

	/**
	 * 2.Panel hiện hiển thị dánh sách các sản phẩm có trong hệ thống.
	 */
	private JPanel taoPnDanhSachDVD() {
		JPanel pnSPRight = store_GUI.createPannel(600, 80, 700, 190, "Danh Sách DVD:");
		pnSPRight.setLayout(new BorderLayout());
		tblDVD = new JTable();
		JPanel pnTableSP = store_GUI.createPanelTable(tblDVD);

		pnSPRight.add(pnTableSP);
		return pnSPRight;
	}

	/**
	 * 3.Hiển thị danh sách các sản phẩm đã được chọn vào trong giỏ hàng để thanh
	 * toán.
	 * 
	 * @return
	 */

	private JPanel pnDanhSPDaChon() {
		JPanel pnSPRight = store_GUI.createPannel(600, 340, 700, 150, "DVD Được Thuê");
		pnSPRight.setLayout(new BorderLayout());
		tblDVDThue = new JTable();
		JPanel pnTableSP = store_GUI.createPanelTable(tblDVDThue);
		pnSPRight.add(pnTableSP);
		return pnSPRight;
	}

	private JPanel pnThongTinThanhToan() {
		JPanel pnSP = store_GUI.createPannel(700, 510, 500, 130, "Thông Tin Thanh Toán");

		pnSP.add(store_GUI.createLable(25, 30, 116, 30, "Tổng tiền:"));
		txtTongTien = store_GUI.createTextField(160, 30, 200, 30, 100);
		pnSP.add(txtTongTien);
		txtTongTien.setEditable(false);
		Font font1 = new Font("Monospaced", Font.CENTER_BASELINE, 20);
		txtTongTien.setFont(font1);
		btnThanhToan = store_GUI.createButton(175, 70, 150, 35, "THANH TOÁN");
		pnSP.add(btnThanhToan);
		return pnSP;
	}

	/**
	 * 4.Panle hiển thị thông tin khách hàng và trong hệ thống.
	 * 
	 * @return
	 */
	private JPanel taoPnNhapThongThueDVD() {
		JPanel pnDatLeft = store_GUI.createPannel(10, 10, 540, 630, "Thông Tin Thuê DVD:");

		JPanel pnTimKiem = store_GUI.createPannel(10, 20, 490, 60, "Tìm Kiếm");
		txtTimKhachHang = store_GUI.createTextField(100, 20, 200, 30, 150);
		btnTimKhachHang = store_GUI.createButton(320, 20, 120, 30, "Tìm Khách Hàng");
		btnTimKhachHang.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 80, 30, "Nhập ID:"));
		pnTimKiem.add(txtTimKhachHang);
		pnTimKiem.add(btnTimKhachHang);
		/**
		 * 4. Hiện thị thông tin khách hàng thông qua tìm kiếm theo mã.
		 */
		JPanel pnKhachHang = store_GUI.createPannel(10, 95, 320, 130, "Khách Hàng");
		// hoten

		pnKhachHang.add(store_GUI.createLable(7, 20, 80, 25, "Họ Tên:"));
		txtHoTen = store_GUI.createTextField(100, 20, 200, 25, 150);
		pnKhachHang.add(txtHoTen);

		// cmnd

		pnKhachHang.add(store_GUI.createLable(7, 50, 80, 25, "Số CMND:"));
		txtCMND = store_GUI.createTextField(100, 50, 200, 25, 10);
		pnKhachHang.add(txtCMND);

		// so DienThoai.
		pnKhachHang.add(store_GUI.createLable(7, 80, 80, 25, "Số ĐT:"));
		txtSoDienThoai = store_GUI.createTextField(100, 80, 200, 25, 11);
		pnKhachHang.add(txtSoDienThoai);

		txtHoTen.setEditable(false);
		txtSoDienThoai.setEditable(false);
		txtCMND.setEditable(false);

		pnDatLeft.add(pnTimKiem);
		pnDatLeft.add(pnKhachHang);

		JPanel pnSPCenter = store_GUI.createPannel(9, 240, 500, 160, "Thông tin các khoản phí trễ hẹn");
		pnSPCenter.setLayout(new BorderLayout());
		tblPhi = new JTable();
		JPanel pnTableSP = store_GUI.createPanelTable(tblPhi);
		pnSPCenter.add(pnTableSP);

		btnChonPhi = store_GUI.createButton(20, 410, 90, 30, "Chọn Phí ");
		pnDatLeft.add(btnChonPhi);

		btnXoaPhi = store_GUI.createButton(130, 410, 110, 30, "Xóa Phí Đã Chọn");
		pnDatLeft.add(btnXoaPhi);

		JPanel pnPhiDaChon = store_GUI.createPannel(10, 460, 500, 150, "Khoản Phí Trễ Hẹn Được Thanh Toán:");
		pnPhiDaChon.setLayout(new BorderLayout());
		tblPhiDaChon = new JTable();
		JPanel pnTablePhi = store_GUI.createPanelTable(tblPhiDaChon);
		pnPhiDaChon.add(pnTablePhi);

		pnDatLeft.add(pnPhiDaChon);
		pnDatLeft.add(pnSPCenter);
		return pnDatLeft;
	}

	/**
	 * 5.ĐĂNG KÍ CÁC SỰ KIỆN TRONG FORM THANH TOÁN GIỎ HÀNG.
	 */

	private void addEvents() {
		btnTimKhachHang.addActionListener(this);
		tblPhi.addMouseListener(this);
		btnChonPhi.addActionListener(this);
		tblPhiDaChon.addMouseListener(this);
		btnXoaPhi.addActionListener(this);
		btnThanhToan.addActionListener(this);
		tblDVD.addMouseListener(this);
		btnThue.addActionListener(this);
		tblDVDThue.addMouseListener(this);
		btnTimDVD.addActionListener(this);
	}

	public void Fill(KhachHang a) {
		txtHoTen.setText(String.valueOf(a.getTenKH()));
		txtCMND.setText(String.valueOf(a.getSoCMND()));
		txtSoDienThoai.setText(String.valueOf(a.getSoDT()));
	}

	public void FillPhiTreHen() {
		String maKH = store_GUI.getValueTextField(txtTimKhachHang);
		listPhieuThueTra = phieuThueTraDAO.findPhieuDaCoPhi(maKH);
		phiTreHenTableModel = new PhiTreHenTableModel(listPhieuThueTra);
		tblPhi.setModel(phiTreHenTableModel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tblPhi)) {
			indexPhi = tblPhi.getSelectedRow();
		}
		if (o.equals(tblPhiDaChon)) {
			indexPhiDaChon = tblPhiDaChon.getSelectedRow();
		}
		if (o.equals(tblDVD)) {
			indextblDVD = tblDVD.getSelectedRow();
		}
		if (o.equals(tblDVDThue)) {
			indexDVDDaChon = tblDVDThue.getSelectedRow();
		}
	}

	public void reSetDataDVD() {
		List<DVD> lstDVD = new ArrayList<DVD>();
		lstDVD = dvdDao.listDVD();
		dvdThueTableModel = new DVDThueTableModel(lstDVD);
		tblDVD.setModel(dvdThueTableModel);
	}

	public void reSetDataPhi() {
		String maKH = store_GUI.getValueTextField(txtTimKhachHang);
		System.out.println(maKH);
		listPhieuThueTra = phieuThueTraDAO.findPhieuDaCoPhi(maKH);
		System.out.println(listPhieuThueTra);
		phiTreHenTableModel = new PhiTreHenTableModel(listPhieuThueTra);
		tblPhi.setModel(phiTreHenTableModel);
	}

	public void xoaRong() {
		txtTimKhachHang.setText(null);
		txtHoTen.setText(null);
		txtSoDienThoai.setText(null);
		txtCMND.setText(null);
		DefaultTableModel dvdDefault = new DefaultTableModel();
		tblPhi.setModel(dvdDefault);
		tblPhiDaChon.setModel(dvdDefault);
		txtTaoPhieuThue.setText(null);
		tblDVDThue.setModel(dvdDefault);
		tblPhiDaChon.setModel(dvdDefault);
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

		if (obj.equals(btnTimKhachHang)) {
			DefaultTableModel dvdDefault = new DefaultTableModel();
			tblPhi.setModel(dvdDefault);
			khachHangDAO = new KhachHangDAO();
			KhachHang a = khachHangDAO.timKHTheoMa(String.valueOf(txtTimKhachHang.getText()));
			if (txtTimKhachHang.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID Khách Hàng");
			} else if (a != null) {
				Fill(a);
				String maKH = store_GUI.getValueTextField(txtTimKhachHang);
				listPhieuThueTra = phieuThueTraDAO.findPhieuDaCoPhi(maKH);
				if (!listPhieuThueTra.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Khách Hàng Này Đang Nợ Phí Trễ Hẹn!");
					phiTreHenTableModel = new PhiTreHenTableModel(listPhieuThueTra);
					tblPhi.setModel(phiTreHenTableModel);
				} else {
					JOptionPane.showMessageDialog(null, "Khách Hàng Này Không Nợ Khoản Phí Trễ Hẹn Nào!");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Không Tìm Thấy Khách Hàng");
			}
		}
		if (obj.equals(btnChonPhi)) {
			if (indexPhi == -1) {
				JOptionPane.showMessageDialog(null, "Chọn Phí Trễ Hẹn Muốn Thanh Toán!");
			} else {
				phiTreHen = listPhieuThueTra.get(indexPhi);
				if (!lstPhiIndex.contains(phiTreHen)) {
					lstPhiIndex.add(phiTreHen);
					phiTreHenTableModel = new PhiTreHenTableModel(lstPhiIndex);
					tblPhiDaChon.setModel(phiTreHenTableModel);
					tongTien += phiTreHen.getPhiTreHen();
					DecimalFormat df = new DecimalFormat("#,### VND");
					txtTongTien.setText(df.format(tongTien));
					System.out.println(phiTreHen.getMaPhieu());
				}
			}
		}
		if (obj.equals(btnXoaPhi)) {
			if (indexPhiDaChon == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn phí để xóa!");
			} else {
				PhieuThueTra p;
				List<PhieuThueTra> lst = new ArrayList<PhieuThueTra>();
				p = lstPhiIndex.get(indexPhiDaChon);
				lstPhiIndex.remove(indexPhiDaChon);
				phiTreHenTableModel = new PhiTreHenTableModel(lstPhiIndex);
				tblPhiDaChon.setModel(phiTreHenTableModel);
				tongTien -= p.getPhiTreHen();
				DecimalFormat df = new DecimalFormat("#,### VND");
				txtTongTien.setText(df.format(tongTien));
				indexPhiDaChon = -1;
			}
		}
		if (obj.equals(btnThue)) {
			khachHangDAO = new KhachHangDAO();
			KhachHang kh = khachHangDAO.timKHTheoMa(String.valueOf(txtTimKhachHang.getText()));
			String maPhieu = store_GUI.getValueTextField(txtTaoPhieuThue);
			if (txtTimKhachHang.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID Khách Hàng");
			} else if (kh == null) {
				JOptionPane.showMessageDialog(null, "Không Tìm Thấy Khách Hàng");
			} else if (txtTaoPhieuThue.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập Mã Phiếu Thuê");
			} else if (phieuThueTraDAO.getPhieuThueTra(maPhieu) != null) {
				JOptionPane.showMessageDialog(null, "Phiếu Thuê Đã Tồn Tại!");
			} else if (indextblDVD == -1) {
				JOptionPane.showMessageDialog(null, "Mời Bạn Chọn DVD Muốn Thuê!");
			} else {
				// String maPhieu = store_GUI.getValueTextField(txtTaoPhieuThue);
				phieuMoi = new PhieuThueTra(maPhieu);
				KhachHang a = khachHangDAO.timKHTheoMa(String.valueOf(txtTimKhachHang.getText()));
				phieuMoi.setKhachHang(a);
				Date ngayTT = (Date) jdkNgayThanhToan.getModel().getValue();
				phieuMoi.setNgayThue(ngayTT);

				String maDVD = (String) tblDVD.getModel().getValueAt(indextblDVD, 1);
				DVD rentedDVD = dvdDao.timDVDTheoMa(maDVD);
				phieuMoi.setDvd(rentedDVD);
				rentedDVD.setTrangThai(2);
				phieuThueTraDAO.themPhieuThueTra(phieuMoi);
				JOptionPane.showMessageDialog(null, "Thuê DVD Thành Công");
				tongTien += rentedDVD.getTua().getTheLoai().getGiaThue();
				DecimalFormat df = new DecimalFormat("#,### VND");
				String tt = String.valueOf(tongTien);
				txtTongTien.setText(df.format(tongTien));
				reSetDataDVD();
				if (!lstDVDThue.contains(rentedDVD)) {
					lstDVDThue.add(rentedDVD);
					dvdThueTableModel = new DVDThueTableModel(lstDVDThue);
					tblDVDThue.setModel(dvdThueTableModel);
				}
				txtTaoPhieuThue.setText(null);
				indextblDVD = -1;
			}
			reSetDataDVD();
		}
		if (obj.equals(btnThanhToan)) {
			// THANH TOÁN PHÍ TRỄ HẸN
			if (tblPhiDaChon.getModel().getRowCount() != 0) {
				int result = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Thanh Toán Phí Trễ Hẹn Luôn Không?", null,
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					for (PhieuThueTra a : lstPhiIndex) {
						a.setPhiTreHen(0);
						Date ngayTT = (Date) jdkNgayThanhToan.getModel().getValue();
						a.setNgayThanhToanPhi(ngayTT);
						phieuThueTraDAO.merge(a);
					}
					JOptionPane.showMessageDialog(null, "Thanh Toán Phí Trễ Hẹn Thành Công");
					reSetDataPhi();
					btnXoaPhi.setEnabled(false);
					btnChonPhi.setEnabled(false);
				}

			}
			JOptionPane.showMessageDialog(null, "Thanh Toán Thành Công!");
			xoaRong();
			txtTongTien.setText(null);
			btnChonPhi.setEnabled(true);
			btnXoaPhi.setEnabled(true);
			DefaultTableModel dvdDefault = new DefaultTableModel();
			tblDVDThue.setModel(dvdDefault);

		}
		if (obj.equals(btnTimDVD)) {
			if (txtTimDVD.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập Tên Tựa!");
			} else {
				List<DVD> lstDVDTim = new ArrayList<DVD>();
				String tuaDVD = store_GUI.getValueTextField(txtTimDVD);
				System.out.println(tuaDVD);
				lstDVDTim = (List<DVD>) dvdDao.findDVDByTua(tuaDVD);
				if (lstDVDTim.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Không Tìm Thấy Tựa!");
				} else {
					System.out.println(listPhieuThueTra);
					dvdThueTableModel = new DVDThueTableModel(lstDVDTim);
					tblDVD.setModel(dvdThueTableModel);
				}
			}

		}
	}

}
