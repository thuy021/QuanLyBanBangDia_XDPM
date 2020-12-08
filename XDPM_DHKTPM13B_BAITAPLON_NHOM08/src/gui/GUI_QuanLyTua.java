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
import javax.swing.table.TableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.TheLoaiDAO;
import dao.TuaDAO;
import defaultTableModel.QLDVDTableModel;
import defaultTableModel.QLTuaTableModel;
import entity.TheLoai;
import entity.Tua;

public class GUI_QuanLyTua implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTenTua, txtMa;
	private JButton btnThem, btnXoa, btnReset, btnXoaTrong;
	JTable tblTua;
	DefaultListModel<String> listModel;
	private QLTuaTableModel qlTuaTableModel;
	private JComboBox<TheLoai> cboTheLoai;
	private int index = -1;
	private DefaultComboBoxModel<TheLoai> theLoaiCBBModel;
	private List<Tua> dsTua;
	private TuaDAO tuaDao = new TuaDAO();
	private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
	private List<TheLoai> listTL;

	public GUI_QuanLyTua() {

	}

	/**
	 * GUI MAIN GIAO DIỆN.
	 * 
	 * @return
	 */
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		conn.add(store_GUI.createLable(500, 10, 300, 30, "QUẢN LÝ TỰA"));

		JPanel pnBangTua = taoPanleBangTua();
		JPanel pnChiTietTua = taoPanleTTChiTietTua();
		JPanel pnChucNang = taoPanleChucNang();
		conn.add(pnBangTua);
		conn.add(pnChiTietTua);
		conn.add(pnChucNang);
		return conn;

	}

	private JPanel taoPanleBangTua() {
		JPanel pnTua = store_GUI.createPannel(40, 280, 1200, 340, "Bảng Danh Sách Tựa");
		pnTua.setLayout(new BorderLayout());
		tblTua = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblTua);
		pnTua.add(pnTableKhachHang);
		tblTua.addMouseListener(this);
		return pnTua;

	}

	private JPanel taoPanleChucNang() {
		JPanel pnChucNang = store_GUI.createPannel(740, 50, 500, 190, "Chức Năng");

		// button sự kiện.
		btnThem = store_GUI.createButton(70, 40, 100, 30, "Thêm");
		btnXoa = store_GUI.createButton(220, 40, 100, 30, "Xóa ");
		btnXoaTrong = store_GUI.createButton(70, 90, 100, 30, "Xoá trống");
		btnReset = store_GUI.createButton(220, 90, 100, 30, "Reset");

		btnThem.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnXoa.setIcon(store_GUI.taonICon("update.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));
		btnReset.setIcon(store_GUI.taonICon("refresh.png", 20, 20));

		// conn.add(btnThem);
		pnChucNang.add(btnThem);
		pnChucNang.add(btnXoa);
		pnChucNang.add(btnXoaTrong);
		pnChucNang.add(btnReset);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnReset.addActionListener(this);
		btnXoaTrong.addActionListener(this);
		taiDuLieuTableTua();
		return pnChucNang;
	}

	private JPanel taoPanleTTChiTietTua() {
		JPanel pnChiTietTua = store_GUI.createPannel(40, 50, 700, 190, "Thông Tin Chi Tiết Tựa");
		pnChiTietTua.add(store_GUI.createLable(20, 40, 80, 30, "ID :"));
		txtMa = store_GUI.createTextField(115, 40, 200, 30, 100);
		pnChiTietTua.add(txtMa);

		pnChiTietTua.add(store_GUI.createLable(20, 90, 80, 30, "Thể Loại:"));
		theLoaiCBBModel = new DefaultComboBoxModel<>();
		listTL = theLoaiDAO.listAllTL();
		theLoaiCBBModel = new DefaultComboBoxModel<TheLoai>();
		for (TheLoai tl : listTL) {
			theLoaiCBBModel.addElement(tl);
		}
		cboTheLoai = new JComboBox<TheLoai>(theLoaiCBBModel);
		cboTheLoai.setBounds(115, 90, 200, 30);
		pnChiTietTua.add(cboTheLoai);

		pnChiTietTua.add(store_GUI.createLable(350, 40, 80, 30, "Tên Tựa:"));
		txtTenTua = store_GUI.createTextField(455, 40, 200, 30, 100);
		pnChiTietTua.add(txtTenTua);

		return pnChiTietTua;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txtMa.setText(null);
		txtTenTua.setText(null);
	}

	public Tua getTuaOnTextField() {
		String maTua = "", tenTua = "";
		TheLoai theLoai = (TheLoai) cboTheLoai.getSelectedItem();
		maTua = store_GUI.getValueTextField(txtMa);
		if (maTua == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập mã tựa");
		}

		tenTua = store_GUI.getValueTextField(txtTenTua);
		if (tenTua == null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tên tựa");
		}

		Tua tua = new Tua(maTua, tenTua, theLoai);
		return tua;
	}

	public void taiDuLieuTableTua() {
		dsTua = tuaDao.getAllTua();
		qlTuaTableModel = new QLTuaTableModel(dsTua);
		tblTua.setModel(qlTuaTableModel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		taiDuLieuTableTua();
		if (obj.equals(btnThem)) {
			List<Tua> lst = tuaDao.getAllTua();
			for(Tua t: lst) {
				if(t.getMaTua().equals(txtMa.getText())){
					JOptionPane.showMessageDialog(null, "Trùng Mã!");
				}
			}
			Tua tua = getTuaOnTextField();
			taiDuLieuTableTua();
			if (tuaDao.themTua(tua)) {
				JOptionPane.showMessageDialog(null, "Thêm thành công");
				taiDuLieuTableTua();
				index = -1;
			}
		}

		if (obj.equals(btnXoa)) {
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Chọn tựa để xoá");
			} else {
				int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá hay không?", null,
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Tua tua = dsTua.get(index);
					if (tuaDao.xoaTua(tua)) {
						taiDuLieuTableTua();
						xoaRongTrenTextField();
						index = -1;
					}
				} else if (result == JOptionPane.NO_OPTION) {
					taiDuLieuTableTua();
				}
			}
		}

		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
		if (obj.equals(btnReset)) {
			taiDuLieuTableTua();
			index = -1;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		index = tblTua.getSelectedRow();
		if (index != -1) {
			Tua tua = dsTua.get(index);
			txtMa.setText(tua.getMaTua());
			cboTheLoai.setSelectedItem(tua.getTheLoai());
			txtTenTua.setText(tua.getTenTua());
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
