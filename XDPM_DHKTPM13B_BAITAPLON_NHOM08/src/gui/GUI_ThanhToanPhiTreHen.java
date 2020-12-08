package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.KhachHangDAO;
import dao.PhieuThueTraDAO;
import defaultTableModel.DVDNoPhiTableModel;
import defaultTableModel.KhachHangTableModel;
import defaultTableModel.PhiTreHenTableModel;
import entity.KhachHang;
import entity.PhieuThueTra;

public class GUI_ThanhToanPhiTreHen implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtsoPhone, txtSoCMND, txtIDKH, txtNhapID, txtMaPhieu, txtIdDVD,
			txtNgayThue, txtNgayTra, txtPhiTreHen;
	private JButton btnTimKhachHang, btnThanhToan, btnChonPhi, btnXoaPhi;
	private JTable tblPhi, tblPhiDaChon;
	DefaultListModel<String> listModel;
	private List<KhachHang> dsKhachHang;
	private KhachHangDAO khachHangDAO = new KhachHangDAO();
	private KhachHangTableModel khachHangTableModel;
	private List<PhieuThueTra> listPhieuThueTra;
	private PhieuThueTraDAO phieuThueTraDao = new PhieuThueTraDAO();
	private PhiTreHenTableModel phiTreHenTableModel;
	private PhieuThueTra phieuThueTra;
	List<PhieuThueTra> lstPhiIndex = new ArrayList<PhieuThueTra>();
	private int index = -1, indexPhi = -1, indexPhiDaChon = -1;
	private JDatePickerImpl jdkNgayThanhToan;

	public GUI_ThanhToanPhiTreHen() {

	}

	public JPanel designGUI() {

		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 20, 300, 50, "THANH TOÁN PHÍ TRỄ HẸN "));

		JPanel pnTimKiem = store_GUI.createPannel(435, 100, 490, 60, "Tìm Khách Hàng");
		txtTimKhachHang = store_GUI.createTextField(100, 20, 180, 30, 100);
		btnTimKhachHang = store_GUI.createButton(300, 20, 120, 30, "Tìm Khách Hàng");
		btnTimKhachHang.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 80, 30, "Nhập ID:"));
		pnTimKiem.add(txtTimKhachHang);
		pnTimKiem.add(btnTimKhachHang);
		conn.add(pnTimKiem);

		// set date for late charge. Default current date.
		jdkNgayThanhToan = store_GUI.createJDatePicker(30, 160, 150, 30);
		jdkNgayThanhToan.setEnabled(false);
		conn.add(jdkNgayThanhToan);

		btnChonPhi = store_GUI.createButton(650, 420, 90, 30, "Chọn Phí ");
		conn.add(btnChonPhi);

		btnXoaPhi = store_GUI.createButton(750, 420, 110, 30, "Xóa Phí Đã Chọn");
		conn.add(btnXoaPhi);
		btnThanhToan = store_GUI.createButton(170, 440, 180, 40, "THANH TOÁN PHÍ TRỄ HẸN");
		conn.add(btnThanhToan);

		JPanel pnBangKhachHang = taoPanleBangPhiTreHen();
		JPanel pnChiTietKH = taoPanleTTChiTietKhachHang();
		JPanel pnPhiTreHenDaChon = taoPanleBangPhiTreHenDaChon();

		conn.add(pnPhiTreHenDaChon);
		conn.add(pnBangKhachHang);
		conn.add(pnChiTietKH);

		// event
		addEvent();
		return conn;

	}

	private void addEvent() {
		btnTimKhachHang.addActionListener(this);
		tblPhi.addMouseListener(this);
		btnChonPhi.addActionListener(this);
		tblPhiDaChon.addMouseListener(this);
		btnXoaPhi.addActionListener(this);
		btnThanhToan.addActionListener(this);
	}

	/*
	 * PANEL BẢNG DANH SÁCH NHÂN VIÊN.
	 */
	private JPanel taoPanleBangPhiTreHen() {
		JPanel pnPhiTreHen = store_GUI.createPannel(600, 200, 700, 210, "Bảng Danh Sách Khoản Phí Trễ Hẹn");
		pnPhiTreHen.setLayout(new BorderLayout());
		tblPhi = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblPhi);
		pnPhiTreHen.add(pnTableKhachHang);
		tblPhi.addMouseListener(this);
		return pnPhiTreHen;

	}

	private JPanel taoPanleBangPhiTreHenDaChon() {

		JPanel pnPhiTreHen = store_GUI.createPannel(600, 470, 700, 190, "Các Khoản Phí Trễ Hẹn Đã Chọn");
		pnPhiTreHen.setLayout(new BorderLayout());
		tblPhiDaChon = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblPhiDaChon);
		pnPhiTreHen.add(pnTableKhachHang);
		tblPhiDaChon.addMouseListener(this);
		return pnPhiTreHen;

	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT KHÁCH HÀNG.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnPhiTreHen = store_GUI.createPannel(30, 200, 500, 210, "Thông Tin Chi Tiết Khách Hàng");

		pnPhiTreHen.add(store_GUI.createLable(27, 40, 80, 30, "Họ Tên:"));
		txthoTen = store_GUI.createTextField(140, 40, 200, 25, 150);
		pnPhiTreHen.add(txthoTen);

		pnPhiTreHen.add(store_GUI.createLable(27, 90, 80, 25, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(140, 90, 200, 25, 10);
		pnPhiTreHen.add(txtSoCMND);

		pnPhiTreHen.add(store_GUI.createLable(27, 140, 80, 25, "Số Điện Thoại:"));
		txtsoPhone = store_GUI.createTextField(140, 140, 200, 25, 11);
		pnPhiTreHen.add(txtsoPhone);

		txthoTen.setEditable(false);
		txtSoCMND.setEditable(false);
		txtsoPhone.setEditable(false);

		return pnPhiTreHen;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXTFIELD
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtsoPhone.setText(null);
		txtSoCMND.setText(null);
		txtTimKhachHang.setText(null);
		DefaultTableModel dvdDefault = new DefaultTableModel();
		tblPhi.setModel(dvdDefault);
		tblPhiDaChon.setModel(dvdDefault);
	}

	/* Get thông tin khách hàng đã dùng lên textfield */
	public void Fill(KhachHang kh) {
		// txtIDKH.setText(String.valueOf(kh.getMaKH()));
		txthoTen.setText(String.valueOf(kh.getTenKH()));
		txtsoPhone.setText(String.valueOf(kh.getSoDT()));
		txtSoCMND.setText(String.valueOf(kh.getSoCMND()));

		String maKH = store_GUI.getValueTextField(txtTimKhachHang);
		System.out.println(maKH);
		listPhieuThueTra = phieuThueTraDao.findPhieuDaCoPhi(maKH);
		System.out.println(listPhieuThueTra);
		phiTreHenTableModel = new PhiTreHenTableModel(listPhieuThueTra);
		tblPhi.setModel(phiTreHenTableModel);
	}

	public void resetDataPhi() {
		String maKH = store_GUI.getValueTextField(txtTimKhachHang);
		System.out.println(maKH);
		listPhieuThueTra = phieuThueTraDao.findPhieuDaCoPhi(maKH);
		System.out.println(listPhieuThueTra);
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
//			khachHangDAO= new KhachHangDAO();
//			KhachHang kh= khachHangDAO.timKHTheoMa(String.valueOf(txtTimKhachHang.getText()));
//			Fill(kh);

			khachHangDAO = new KhachHangDAO();
			KhachHang a = khachHangDAO.timKHTheoMa(String.valueOf(txtTimKhachHang.getText()));
			if (txtTimKhachHang.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID Khách Hàng");
			} else if (a != null) {
				Fill(a);
				String maKH = store_GUI.getValueTextField(txtTimKhachHang);
				listPhieuThueTra = phieuThueTraDao.findPhieuDaCoPhi(maKH);
				if (listPhieuThueTra.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Khách Hàng Này Không Nợ Phí Trễ Hẹn!");
					phiTreHenTableModel = new PhiTreHenTableModel(listPhieuThueTra);
					tblPhi.setModel(phiTreHenTableModel);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Không Tìm Thấy Khách Hàng");
			}

		}

		if (obj.equals(btnChonPhi)) {
			if (indexPhi == -1) {
				JOptionPane.showMessageDialog(null, "Chọn Phí Trễ Hẹn Muốn Thanh Toán");
			} else {
				phieuThueTra = listPhieuThueTra.get(indexPhi);
				if (!lstPhiIndex.contains(phieuThueTra)) {
					lstPhiIndex.add(phieuThueTra);
					phiTreHenTableModel = new PhiTreHenTableModel(lstPhiIndex);
					tblPhiDaChon.setModel(phiTreHenTableModel);
					System.out.println(phieuThueTra.getMaPhieu());
				}
			}
		}

		if (obj.equals(btnXoaPhi)) {
			if (indexPhiDaChon == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn phí để khôi phục lại");
			} else {
				lstPhiIndex.remove(indexPhiDaChon);
				phiTreHenTableModel = new PhiTreHenTableModel(lstPhiIndex);
				tblPhiDaChon.setModel(phiTreHenTableModel);
				indexPhiDaChon = -1;

			}
		}

		if (obj.equals(btnThanhToan)) {
			if (tblPhiDaChon.getModel().getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Chọn Khoản Phí Trễ Hẹn Để Thanh Toán!");
			} else {
				int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn thanh toán hay không?", null,
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					for (PhieuThueTra p : lstPhiIndex) {
						p.setPhiTreHen(0);
						Date ngayThanhToan = (Date) jdkNgayThanhToan.getModel().getValue();
						p.setNgayThanhToanPhi(ngayThanhToan);
						phieuThueTraDao.merge(p);
					}
					JOptionPane.showMessageDialog(null, "Thanh Toán Phí Trễ Hẹn Thành Công");
					resetDataPhi();
					xoaRongTrenTextField();
				} else if (result == JOptionPane.NO_OPTION) {

				}
			}

		}
	}

}
