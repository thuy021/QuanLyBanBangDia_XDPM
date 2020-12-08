package gui;
/*
 * author: Nguyen Dinh Thong
 * Date modify: 29/11/2020
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.KhachHangDAO;
import defaultTableModel.KhachHangTableModel;
import entity.KhachHang;

public class GUI_QuanLyKhachHang implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtsoPhone, txtMa, txtSoCMND;
	private JButton btnTimKhachHang, btnThem, btnUpdate, btnXoaTrong;
	private JDatePickerImpl jpkNgaySinh;
	JTable tblKhachHang;
	private List<KhachHang> dsKhachHang;
	private KhachHangDAO khachHangDAO = new KhachHangDAO();
	DefaultListModel<String> listModel;
	private KhachHangTableModel khachHangTableModel;
	private int index = -1;

	public GUI_QuanLyKhachHang() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ KHÁCH HÀNG "));

		JPanel pnBangKhachHang = taoPanleBangKhachHang();
		JPanel pnChiTietKH = taoPanleTTChiTietKhachHang();
		JPanel pnTimKiem = taoPanleChucNang();
		conn.add(pnBangKhachHang);
		conn.add(pnChiTietKH);
		conn.add(pnTimKiem);
		taiLaiDuLieuTableKhachHang();
		return conn;

	}

	/*
	 * PANEL BẢNG DANH SÁCH NHÂN VIÊN.
	 */
	private JPanel taoPanleBangKhachHang() {
		JPanel pnKhachHang = store_GUI.createPannel(40, 280, 1200, 340, "Bảng Danh Sách Khách Hàng");
		pnKhachHang.setLayout(new BorderLayout());
		tblKhachHang = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblKhachHang);
		pnKhachHang.add(pnTableKhachHang);
		tblKhachHang.addMouseListener(this);
		return pnKhachHang;

	}

	/**
	 * PANLE TÌM KIẾM THEO MÃ KHÁCH HÀNG. TÌM KIẾM TUYỆT ĐỐI
	 * 
	 * @return
	 */
	private JPanel taoPanleChucNang() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 190, "Chức Năng");

		// button sự kiện.
		btnThem = store_GUI.createButton(70, 40, 100, 30, "Thêm");
		btnUpdate = store_GUI.createButton(220, 40, 100, 30, "Sửa ");
		btnXoaTrong = store_GUI.createButton(150, 90, 100, 30, "Xoá trống");

		btnThem.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnUpdate.setIcon(store_GUI.taonICon("update.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));

		pnTimKiem.add(btnThem);
		pnTimKiem.add(btnUpdate);
		pnTimKiem.add(btnXoaTrong);

		/*
		 * * * * * Bắt sự kiện * * * *
		 */
		addEvent();
		return pnTimKiem;

	}

	private void addEvent() {
		btnThem.addActionListener(this);
		btnXoaTrong.addActionListener(this);
		btnUpdate.addActionListener(this);
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT KHÁCH HÀNG.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnChiTietKhachHang = store_GUI.createPannel(40, 50, 700, 190, "Thông Tin Chi Tiết Khách Hàng");
		pnChiTietKhachHang.add(store_GUI.createLable(20, 40, 80, 30, "ID :"));
		txtMa = store_GUI.createTextField(115, 40, 200, 30, 100);
		pnChiTietKhachHang.add(txtMa);

		// Họ tên
		pnChiTietKhachHang.add(store_GUI.createLable(20, 90, 80, 30, "Họ tên:"));
		txthoTen = store_GUI.createTextField(115, 90, 200, 30, 100);
		pnChiTietKhachHang.add(txthoTen);

		// số điện thoại
		pnChiTietKhachHang.add(store_GUI.createLable(350, 40, 80, 30, "số điện thoại:"));
		txtsoPhone = store_GUI.createTextField(445, 40, 200, 30, 100);
		pnChiTietKhachHang.add(txtsoPhone);

		// Số CMND
		pnChiTietKhachHang.add(store_GUI.createLable(350, 90, 80, 30, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(445, 90, 200, 30, 100);
		pnChiTietKhachHang.add(txtSoCMND);

		return pnChiTietKhachHang;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXTFIELD
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtMa.setText(null);
		txtsoPhone.setText(null);
		txtSoCMND.setText(null);
		txtMa.setEditable(true);
	}

	/*
	 * PHƯƠNG THỨC GET THÔNG TIN KHÁCH HÀNG LÊN TEXT FIELD
	 */
	private KhachHang getKhachHangOnTextField() {
		String maKhangHang = "", tenKhachHang = "", cmnd = "", soDT = "";
		String CMND_Pattern = "^\\d{9}$";
		maKhangHang = store_GUI.getValueTextField(txtMa);
		tenKhachHang = store_GUI.getValueTextField(txthoTen);
		cmnd = store_GUI.getValueTextField(txtSoCMND);
		soDT = store_GUI.getValueTextField(txtsoPhone);

		if (maKhangHang == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}

		else if (tenKhachHang == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tên khách hàng");
		}

		else if (cmnd == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số CMND");
		} else if ((Pattern.compile(CMND_Pattern).matcher(cmnd)).find() == false) {
			JOptionPane.showMessageDialog(null, "CMND Phải Đủ 9 Kí Tự Số");
		}

		else if (soDT == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số điện thoại");
		}

		else {
			KhachHang kh = new KhachHang(maKhangHang, tenKhachHang, soDT, cmnd);
			return kh;
		}
		return null;

	}

	public void taiLaiDuLieuTableKhachHang() {

		dsKhachHang = khachHangDAO.getAllKH();
		khachHangTableModel = new KhachHangTableModel(dsKhachHang);
		tblKhachHang.setModel(khachHangTableModel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		taiLaiDuLieuTableKhachHang();

		if (obj.equals(btnThem)) {
			List<KhachHang> lst = khachHangDAO.getAllKH();
			for (KhachHang k : lst) {
				if (k.getMaKH().equals(txtMa.getText()))
					JOptionPane.showMessageDialog(null, "Trùng Mã");
			}
			KhachHang kh = getKhachHangOnTextField();
			taiLaiDuLieuTableKhachHang();
			if (khachHangDAO.themKH(kh)) {
				JOptionPane.showMessageDialog(null, "Thêm thành công");
				taiLaiDuLieuTableKhachHang();
				index = -1;
			}
		}

		if (obj.equals(btnUpdate)) {
			txtMa.setEditable(false);
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Chọn khách hàng để cập nhật thông tin");
			} else {
				txtMa.setEditable(false);
				KhachHang kh = getKhachHangOnTextField();
				if (khachHangDAO.capNhatKH(kh)) {
					JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công!");
					taiLaiDuLieuTableKhachHang();
					xoaRongTrenTextField();
					index = -1;
				}
			}
		}

		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		index = tblKhachHang.getSelectedRow();
		if (index != -1) {
			KhachHang kh = dsKhachHang.get(index);
			txtMa.setText(kh.getMaKH());
			txthoTen.setText(kh.getTenKH());
			txtSoCMND.setText(kh.getSoCMND());
			txtsoPhone.setText(kh.getSoDT());
			txtMa.setEditable(false);
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
