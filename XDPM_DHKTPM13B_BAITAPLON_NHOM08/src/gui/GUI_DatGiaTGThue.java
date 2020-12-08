package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.KhachHangDAO;
import dao.TheLoaiDAO;
import defaultTableModel.KhachHangTableModel;
import entity.DVD;
import entity.KhachHang;
import entity.TheLoai;
import entity.Tua;

public class GUI_DatGiaTGThue implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txtTen, txtThoiGianThue, txtGiaThue, txtIDKH, txtNhapID;
	private JButton btnLuu, btnChon;
	JTable tblKhachHang;
	DefaultListModel<String> listModel;
	private KhachHangTableModel khachHangTableModel;
	private JComboBox cboTheLoai;
	private DefaultComboBoxModel<TheLoai> theLoaiCBBModel;
	private List<TheLoai> listTheLoai;
	private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
	private int index = -1;
	private String x;
	private TheLoaiDAO xlTheLoai = new TheLoaiDAO();

	public GUI_DatGiaTGThue() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(540, 20, 280, 50, "ĐẶT GIÁ - THỜI GiAN THUÊ"));

		JPanel pnTimKiem = store_GUI.createPannel(435, 100, 490, 60, "Chọn Thể Loại");

		pnTimKiem.add(store_GUI.createLable(20, 20, 80, 30, "Thể Loại:"));
		theLoaiCBBModel = new DefaultComboBoxModel<>();
		listTheLoai = theLoaiDAO.getAllTheLoai();
		theLoaiCBBModel = new DefaultComboBoxModel<TheLoai>();
		for (TheLoai tl : listTheLoai) {
			theLoaiCBBModel.addElement(tl);
		}

		cboTheLoai = new JComboBox<TheLoai>(theLoaiCBBModel);
		cboTheLoai.setBounds(140, 20, 200, 30);
		pnTimKiem.add(cboTheLoai);

		btnChon = store_GUI.createButton(390, 20, 90, 30, "CHỌN");
		pnTimKiem.add(btnChon);

		conn.add(pnTimKiem);
		btnLuu = store_GUI.createButton(590, 450, 180, 40, "LƯU");
		conn.add(btnLuu);
		btnChon.addActionListener(this);

		JPanel pnChiTietKH = taoPanleTTChiTietKhachHang();
		conn.add(pnChiTietKH);
		cboTheLoai.addActionListener(this);
		btnLuu.addActionListener(this);
		return conn;

	}

	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnPhiTreHen = store_GUI.createPannel(430, 210, 500, 210, "Thông Tin Chi Tiết Thể Loại");

		pnPhiTreHen.add(store_GUI.createLable(27, 40, 80, 30, "Tên Thể Loại:"));
		txtTen = store_GUI.createTextField(140, 40, 200, 30, 150);
		pnPhiTreHen.add(txtTen);
		// x = cboTheLoai.getSelectedItem().toString();
		/*
		 * if(cboTheLoai.getSelectedItem().toString().equalsIgnoreCase("Film"))
		 * txtTen.setText("Film");
		 * if(cboTheLoai.getSelectedItem().toString().equalsIgnoreCase("Game"))
		 * txtTen.setText("Game");
		 */

		pnPhiTreHen.add(store_GUI.createLable(27, 90, 80, 30, "Giá Thuê:"));
		txtGiaThue = store_GUI.createTextField(140, 90, 200, 30, 10);
		pnPhiTreHen.add(txtGiaThue);

		pnPhiTreHen.add(store_GUI.createLable(27, 140, 100, 30, "Thời Gian Thuê:"));
		txtThoiGianThue = store_GUI.createTextField(140, 140, 200, 30, 11);
		pnPhiTreHen.add(txtThoiGianThue);

		txtTen.setEditable(false);

		return pnPhiTreHen;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txtTen.setText(null);
		txtThoiGianThue.setText(null);
		txtGiaThue.setText(null);
	}

	private TheLoai getTheLoaiOnTextField() {
		String tenTheLoai = store_GUI.getValueTextField(txtTen);
		String giaThue = "";
		String thoiGianThue = "";
		String maTheLoai = "";
		if (txtTen.getText().equalsIgnoreCase("Film")) {
			maTheLoai = "TL01";
		}
		if (txtTen.getText().equalsIgnoreCase("Game")) {
			maTheLoai = "TL02";
		}
		double gia = 0;
		int ngay = 0;
		giaThue = store_GUI.getValueTextField(txtGiaThue);

		if (giaThue == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập giá thuê");
		} else {
			gia = Double.parseDouble(giaThue);
		}

		thoiGianThue = store_GUI.getValueTextField(txtThoiGianThue);
		if (thoiGianThue == null)
			JOptionPane.showMessageDialog(null, "Chưa nhập giá thuê");
		else
			ngay = Integer.parseInt(thoiGianThue);

		TheLoai theLoai = new TheLoai(maTheLoai, tenTheLoai, gia, ngay);

		return theLoai;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnChon)) {
			String data = "";
			data = cboTheLoai.getSelectedItem().toString();
			System.out.println(data);
			TheLoai tl = xlTheLoai.findTLByTen(data);
			txtTen.setText(tl.getTenTheLoai());
			txtGiaThue.setText(String.valueOf(tl.getGiaThue()));
			txtThoiGianThue.setText(String.valueOf(tl.getThoiGianThue()));

		}
		if (obj.equals(btnLuu)) {

			TheLoai theLoai = getTheLoaiOnTextField();
			if (xlTheLoai.updateTheLoai(theLoai)) {

				JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
				xoaRongTrenTextField();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
