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
import java.util.List;

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

public class GUI_XoaKhachHang implements ActionListener, MouseListener {
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTimKhachHang, txthoTen, txtsoPhone, txtMa, txtSoCMND;
	private JButton btnTimKhachHang, btnXoa, btnXoaTrong;
	private JDatePickerImpl jpkNgaySinh;
	private JRadioButton radNam, radNu;
	JTable tblKhachHang;
	private List<KhachHang> dsKhachHang;
	private KhachHangDAO khachHangDAO= new KhachHangDAO();
	DefaultListModel<String> listModel;
	private KhachHangTableModel khachHangTableModel;
	private int index = -1;

	public GUI_XoaKhachHang() {

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
		JPanel pnTimKiem = taoPanleTimKiem();
		conn.add(pnBangKhachHang);
		conn.add(pnChiTietKH);
		conn.add(pnTimKiem);
		taiLaiDuLieuTableKhachHang();
		return conn;

	}

	private JPanel taoPanleBangKhachHang() {
		JPanel pnKhachHang = store_GUI.createPannel(40, 280, 1200, 340, "Bảng Danh Sách Khách Hàng");
		pnKhachHang.setLayout(new BorderLayout());
		tblKhachHang = new JTable();
		JPanel pnTableKhachHang = store_GUI.createPanelTable(tblKhachHang);
		pnKhachHang.add(pnTableKhachHang);
		tblKhachHang.addMouseListener(this);
		return pnKhachHang;

	}

	private JPanel taoPanleTimKiem() {
		JPanel pnTimKiem = store_GUI.createPannel(740, 50, 500, 190, "Chức Năng");

		// button sự kiện.
		btnXoa = store_GUI.createButton(50, 70, 100, 30, "Xóa");
		btnXoaTrong = store_GUI.createButton(170, 70, 100, 30, "Xoá trống");

		btnXoa.setIcon(store_GUI.taonICon("delete.png", 20, 20));
		btnXoaTrong.setIcon(store_GUI.taonICon("clean.png", 20, 20));

		// conn.add(btnXoa);
		pnTimKiem.add(btnXoa);
		pnTimKiem.add(btnXoaTrong);

		/*	*	*	*	*	
		 * Bắt sự kiện 	*
		 * 	*	*	*	*/
		addEvent();
		return pnTimKiem;
	}
	
	
	private void addEvent() {
		btnXoa.addActionListener(this);
		btnXoaTrong.addActionListener(this);
		
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

		pnChiTietKhachHang.add(store_GUI.createLable(20, 90, 80, 30, "Họ tên:"));
		txthoTen = store_GUI.createTextField(115, 90, 200, 30, 100);
		pnChiTietKhachHang.add(txthoTen);

		pnChiTietKhachHang.add(store_GUI.createLable(350, 40, 80, 30, "số điện thoại:"));
		txtsoPhone = store_GUI.createTextField(445, 40, 200, 30, 100);
		pnChiTietKhachHang.add(txtsoPhone);

		pnChiTietKhachHang.add(store_GUI.createLable(350, 90, 80, 30, "Số CMND:"));
		txtSoCMND = store_GUI.createTextField(445, 90, 200, 30, 100);
		pnChiTietKhachHang.add(txtSoCMND);

		return pnChiTietKhachHang;
	}

	/**
	 * PHƯƠNG THỨC XÓA RỔNG TRÊN TEXT FILED
	 */
	public void xoaRongTrenTextField() {
		txthoTen.setText(null);
		txtMa.setText(null);
		txtsoPhone.setText(null);
		txtSoCMND.setText(null);
	}
	
	private KhachHang getKhachHangOnTextField() {
		String maKhangHang="", tenKhachHang="", cmnd="", soDT="";
		maKhangHang= store_GUI.getValueTextField(txtMa);
		if ( maKhangHang==null) {
			
			JOptionPane.showMessageDialog(null, "Chưa nhập mã");
		}
		
		tenKhachHang= store_GUI.getValueTextField(txthoTen);
		if(tenKhachHang==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập tên khách hàng");
		}
		
		cmnd= store_GUI.getValueTextField(txtSoCMND);
		if (cmnd==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số CMND");
		}
		
		soDT= store_GUI.getValueTextField(txtsoPhone);
		if (soDT==null) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số điện thoại");
		}
		KhachHang kh= new KhachHang(maKhangHang, tenKhachHang, soDT, cmnd);
		return kh;
	}
	
	public void taiLaiDuLieuTableKhachHang() {
		dsKhachHang= khachHangDAO.getAllKH();
		khachHangTableModel= new KhachHangTableModel(dsKhachHang);
		tblKhachHang.setModel(khachHangTableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		Object obj = e.getSource();
		if (obj.equals(btnXoa)) {
			if (index==-1) {
				JOptionPane.showMessageDialog(null, "Chọn khách hàng để xoá");
			} else {
				int result= JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá hay không?", null, JOptionPane.YES_NO_OPTION);
				if (result== JOptionPane.YES_OPTION) {
					KhachHang kh= dsKhachHang.get(index);
					if( khachHangDAO.xoaKH(kh)) {
						taiLaiDuLieuTableKhachHang();
						xoaRongTrenTextField();
						index=-1;
					}
				} else if (result== JOptionPane.NO_OPTION){
					taiLaiDuLieuTableKhachHang();
				}
			}
		}
		
		if (obj.equals(btnXoaTrong)) {
			xoaRongTrenTextField();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		index= tblKhachHang.getSelectedRow();
		if (index !=-1) {
			KhachHang kh= dsKhachHang.get(index);
			txtMa.setText(kh.getMaKH());
			txtMa.setEditable(false);
			txthoTen.setText(kh.getTenKH());
			txtSoCMND.setText(kh.getSoCMND());
			txtsoPhone.setText(kh.getSoDT());			
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
