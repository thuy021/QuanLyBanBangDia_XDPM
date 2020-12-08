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
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.DVDDao;
import dao.TuaDAO;
import defaultTableModel.DVDTableModel;
import defaultTableModel.KhachHangTableModel;
import defaultTableModel.QLDVDTableModel;
import entity.DVD;
import entity.Tua;

public class GUI_QuanLyDVD implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtMa, txtSoCMND;
	private JButton btnThem, btnXoa, btnXoaTrong;
	JTable tblDVD;
	DefaultListModel<String> listModel;
	private List<DVD> dsDVD;
	private DVDDao xlDVD = new DVDDao();
	private QLDVDTableModel qlDVDTableModel;
	private DVDTableModel dvdTableModel;
	private JComboBox<Tua> cboTua;
	private JComboBox<String> cboStatus;
	private int index = -1;
	private DefaultComboBoxModel<Tua> tuaCBBModel;
	private List<Tua> dsTua;
	private TuaDAO tuaDAO = new TuaDAO();

	public GUI_QuanLyDVD() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ DVD "));

		JPanel pnBangDVD = taoPanleBangDVD();
		JPanel pnChiTietDVD = taoPanleTTChiTietDVD();
		JPanel pnTimKiem = taoPanleChucNang();
		conn.add(pnBangDVD);
		conn.add(pnChiTietDVD);
		conn.add(pnTimKiem);
		return conn;
	}

	/*
	 * PANEL BẢNG DANH SÁCH DVD.
	 */
	private JPanel taoPanleBangDVD() {
		JPanel pnKhachHang = store_GUI.createPannel(40, 280, 1200, 340, "Bảng Danh Sách DVD");
		pnKhachHang.setLayout(new BorderLayout());
		tblDVD = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblDVD);
		pnKhachHang.add(pnTableKhachHang);
		tblDVD.addMouseListener(this);
		return pnKhachHang;

	}

	/**
	 * PANLE CHỨC NĂNG
	 * 
	 * @return
	 */
	private JPanel taoPanleChucNang() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 190, "Chức Năng");

		// button sự kiện.
		btnThem = store_GUI.createButton(70, 40, 100, 30, "Thêm");
		btnXoa = store_GUI.createButton(220, 40, 100, 30, "Xóa ");
		btnXoaTrong = store_GUI.createButton(70, 90, 100, 30, "Xoá trống");

		btnThem.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnXoa.setIcon(store_GUI.taonICon("update.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));

		// conn.add(btnThem);
		pnTimKiem.add(btnThem);
		pnTimKiem.add(btnXoa);
		pnTimKiem.add(btnXoaTrong);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrong.addActionListener(this);
		taiDuLieuTableDVD();
		return pnTimKiem;
	}

	/**
	 * PANEL THÔNG TIN CHI TIẾT DVD.
	 * 
	 * @return
	 */
	private JPanel taoPanleTTChiTietDVD() {
		JPanel pnChiTietDVD = store_GUI.createPannel(40, 50, 700, 190, "Thông Tin Chi Tiết DVD");
		pnChiTietDVD.add(store_GUI.createLable(20, 40, 80, 30, "ID :"));
		txtMa = store_GUI.createTextField(115, 40, 200, 30, 100);
		pnChiTietDVD.add(txtMa);
		// txtMa.disable();
		pnChiTietDVD.add(store_GUI.createLable(20, 90, 80, 30, "Tựa:"));
		tuaCBBModel = new DefaultComboBoxModel<>();
		dsTua = tuaDAO.getAllTua();
		tuaCBBModel = new DefaultComboBoxModel<Tua>();
		dsDVD = xlDVD.getAllDVD();
		// dsTua = xlLoaiHang.getAllLoaiHang(1000);
		for (Tua tua : dsTua) {
			tuaCBBModel.addElement(tua);
		}
		cboTua = new JComboBox<Tua>(tuaCBBModel);
		cboTua.setBounds(115, 90, 200, 30);
		pnChiTietDVD.add(cboTua);

		// Trạng thái
		pnChiTietDVD.add(store_GUI.createLable(350, 40, 80, 30, "Trạng thái:"));
		String trangThai[] = { "OnShelf", "Rented", "OnHold" };
		cboStatus = new JComboBox(trangThai);
		cboStatus.setBounds(445, 40, 200, 30);
		pnChiTietDVD.add(cboStatus);

		return pnChiTietDVD;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txtMa.setText(null);
	}

	public void taiDuLieuTableDVD() {
		dsDVD = xlDVD.getAllDVD();
		dvdTableModel = new DVDTableModel(dsDVD);
		tblDVD.setModel(dvdTableModel);
	}

	private DVD getDVDOnTextField() {
		String maDVD = "";
		Tua maTua = (Tua) cboTua.getSelectedItem();
		int trangThai = 1;
		maDVD = store_GUI.getValueTextField(txtMa);
		if (maDVD == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		DVD dvd = new DVD(maDVD, trangThai, maTua);
		return dvd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnThem)) {
			DVDDao dvdDao = new DVDDao();
			List<DVD> lst = dvdDao.getAllDVD();
			for(DVD d :lst) {
				if(d.getMaDVD().equals(txtMa.getText())) {
					JOptionPane.showMessageDialog(null, "Trùng Mã!");
				}
			}
			DVD dvd = getDVDOnTextField();
			taiDuLieuTableDVD();
			if (xlDVD.themDVD(dvd)) {
				JOptionPane.showMessageDialog(null, "Thêm DVD thành công");
				taiDuLieuTableDVD();
			}
		}
		if (obj.equals(btnXoa)) {
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Mời bạn chọn DVD để xóa !");
			} else {
				int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", null,
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					DVD dvd = dsDVD.get(index);
					if (xlDVD.deleteDVD(dvd)) {
						JOptionPane.showMessageDialog(null, "Xóa DVD thành công !!");
						taiDuLieuTableDVD();
						xoaRongTrenTextField();
						index = -1;
					}
				} else if (result == JOptionPane.NO_OPTION) {
					taiDuLieuTableDVD();
				}

			}
		}

		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		index = tblDVD.getSelectedRow();
		if (index != -1) {
			DVD dvd = dsDVD.get(index);
			cboTua.setSelectedItem(dvd.getTua());
			txtMa.setText(dvd.getMaDVD());
			if (dvd.getTrangThai() == 1)
				cboStatus.setSelectedItem("OnShelf");
			else if (dvd.getTrangThai() == 2)
				cboStatus.setSelectedItem("Rented");
			else
				cboStatus.setSelectedItem("OnHold");

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
