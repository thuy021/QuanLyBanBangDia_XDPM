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

import antlr.collections.impl.LList;
import dao.DVDDao;
import dao.PhieuDatTruocDAO;
import dao.PhieuThueTraDAO;
import dao.TuaDAO;
import defaultTableModel.PhiTreHenTableModel;
import entity.DVD;
import entity.PhieuDatTruoc;
import entity.PhieuThueTra;
import entity.Tua;

public class GUI_BaoCaoTrangThaiDVD implements ActionListener, MouseListener {
	private JTextField txtTimDVD, txtCMND, txtSoDienThoai, txtTieuDe, txtTrangThai, txtTenKH, txtMaTua, txtTenTua,
			txtSL, txtTheLoai, txtTimTua;
	private JButton btnTimDVD, btnTimTua;
	private GUI_Store store_GUI = new GUI_Store();
	private JDatePickerImpl jdkNgayDenHan, jdkNgayThue;
	PhieuThueTraDAO phieuThueTraDAO;

//Thông báo chưa làm xong/ chưa làm tìm DVD trong thuê
	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		JPanel pnDatLeft = taoPnTrangThaiDVD();
		JPanel pnTrangThaiTua = taoPnTrangThaiTua();
		conn.add(pnTrangThaiTua);
		conn.add(pnDatLeft);
		addEvents();
		return conn;

	}

	private JPanel taoPnTrangThaiDVD() {
		JPanel pnDatLeft = store_GUI.createPannel(40, 30, 540, 520, "Trạng Thái DVD");

		JPanel pnTimKiem = store_GUI.createPannel(20, 30, 470, 60, "Kiểm tra DVD");
		txtTimDVD = store_GUI.createTextField(100, 20, 180, 30, 100);
		btnTimDVD = store_GUI.createButton(300, 20, 120, 30, "Kiểm tra DVD");
		btnTimDVD.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 80, 30, "Nhập ID:"));
		pnTimKiem.add(txtTimDVD);
		pnTimKiem.add(btnTimDVD);

		pnDatLeft.add(store_GUI.createLable(20, 115, 80, 30, "Tiêu đề:"));
		txtTieuDe = store_GUI.createTextField(115, 120, 200, 30, 150);
		pnDatLeft.add(store_GUI.createLable(20, 155, 80, 30, "Trạng thái:"));
		txtTrangThai = store_GUI.createTextField(115, 165, 200, 30, 150);
		pnDatLeft.add(txtTieuDe);
		pnDatLeft.add(txtTrangThai);

		JPanel pnThanhToanPhi = store_GUI.createPannel(20, 215, 370, 170, "Thông Tin Khách Hàng");

		pnThanhToanPhi.add(store_GUI.createLable(10, 30, 90, 30, "Khách hàng:"));
		txtTenKH = store_GUI.createTextField(125, 30, 200, 30, 150);
		pnThanhToanPhi.add(txtTenKH);

		pnThanhToanPhi.add(store_GUI.createLable(10, 70, 80, 30, "Sô CMND:"));
		txtCMND = store_GUI.createTextField(125, 70, 200, 30, 150);
		pnThanhToanPhi.add(txtCMND);

		pnThanhToanPhi.add(store_GUI.createLable(10, 110, 80, 25, "Số điện thoại:"));
		txtSoDienThoai = store_GUI.createTextField(125, 110, 200, 30, 10);
		pnThanhToanPhi.add(txtSoDienThoai);

		pnDatLeft.add(store_GUI.createLable(20, 450, 100, 30, "Ngày đến hạn"));
		jdkNgayDenHan = store_GUI.createJDatePicker(135, 450, 150, 30);
		pnDatLeft.add(jdkNgayDenHan);

		pnDatLeft.add(store_GUI.createLable(20, 410, 100, 30, "Ngày Thuê"));
		jdkNgayThue = store_GUI.createJDatePicker(135, 410, 150, 30);
		pnDatLeft.add(jdkNgayThue);

		txtTieuDe.setEditable(false);
		txtCMND.setEditable(false);
		txtTenKH.setEditable(false);
		txtSoDienThoai.setEditable(false);

		pnDatLeft.add(pnTimKiem);
		pnDatLeft.add(pnThanhToanPhi);

		return pnDatLeft;
	}

	private JPanel taoPnTrangThaiTua() {
		JPanel pnDatLeft = store_GUI.createPannel(630, 30, 540, 520, "Trạng Thái Tựa");

		JPanel pnTimKiem = store_GUI.createPannel(30, 30, 470, 60, "Kiểm tra Tựa");
		txtTimTua = store_GUI.createTextField(100, 20, 180, 30, 100);
		btnTimTua = store_GUI.createButton(300, 20, 120, 30, "Kiểm tra Tựa");
		btnTimTua.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 80, 30, "Nhập Tựa:"));
		pnTimKiem.add(txtTimTua);
		pnTimKiem.add(btnTimTua);

		pnDatLeft.add(store_GUI.createLable(30, 115, 80, 30, "Mã Tựa:"));
		txtMaTua = store_GUI.createTextField(145, 115, 200, 30, 150);
		pnDatLeft.add(store_GUI.createLable(30, 165, 80, 30, "Thể Loại:"));
		txtTheLoai = store_GUI.createTextField(145, 165, 200, 30, 150);
		pnDatLeft.add(store_GUI.createLable(20, 215, 120, 30, "Số lượng DVD có sẵn:"));
		txtSL = store_GUI.createTextField(145, 215, 200, 30, 150);
		pnDatLeft.add(txtMaTua);
		pnDatLeft.add(txtTheLoai);
		pnDatLeft.add(txtSL);

		txtMaTua.setEditable(false);
		txtTheLoai.setEditable(false);
		txtSL.setEditable(false);

		pnDatLeft.add(pnTimKiem);

		return pnDatLeft;
	}

	/**
	 * 5.ĐĂNG KÍ CÁC SỰ KIỆN TRONG FORM THANH TOÁN GIỎ HÀNG.
	 */

	private void addEvents() {
		btnTimDVD.addActionListener(this);
		btnTimTua.addActionListener(this);
	}

	public void xoaRong() {
		txtCMND.setText("");
		txtSoDienThoai.setText("");
		txtTenKH.setText("");
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
			DVDDao dvdDao = new DVDDao();
			PhieuDatTruocDAO phieuDatTruocDAO = new PhieuDatTruocDAO();
			PhieuThueTraDAO phieuThueTraDAO = new PhieuThueTraDAO();
			if (txtTimDVD.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập ID DVD");
			} else {
				DVD dvd = dvdDao.timDVDTheoMa(String.valueOf(txtTimDVD.getText()));

				if (dvd == null) {
					JOptionPane.showMessageDialog(null, "Không Tìm Thấy DVD!");
				} else {
					txtTieuDe.setText(dvd.getTua().getTenTua());
					if (dvd.getTrangThai() == 1) {
						xoaRong();
						txtTrangThai.setText("Còn Trên Kệ");
					} else if (dvd.getTrangThai() == 2) {
						xoaRong();
						PhieuThueTra d1 = phieuThueTraDAO.findPhieuByDVD(String.valueOf(txtTimDVD.getText()));
						txtTrangThai.setText("Đang Được Thuê");
						txtTenKH.setText(String.valueOf(d1.getKhachHang().getTenKH()));
						txtCMND.setText(d1.getKhachHang().getSoCMND());
						txtSoDienThoai.setText(String.valueOf(d1.getKhachHang().getSoDT()));
						Date ngayThue = d1.getNgayThue();
						Calendar calendar = new GregorianCalendar();
						Calendar calendar1 = new GregorianCalendar();
						calendar1.setTime(ngayThue);
						calendar.setTime(ngayThue);
						calendar.add(Calendar.DATE, d1.getDvd().getTua().getTheLoai().getThoiGianThue());
						jdkNgayThue.getModel().setDate(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),
								calendar1.get(Calendar.DAY_OF_MONTH));
						jdkNgayDenHan.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
								calendar.get(Calendar.DAY_OF_MONTH));
					} else if (dvd.getTrangThai() == 3) {
						xoaRong();
						PhieuDatTruoc d2 = phieuDatTruocDAO.findPhieuByMaDVD(String.valueOf(txtTieuDe.getText()));
						txtTrangThai.setText("Đang Giữ Cho Khách");
						txtTenKH.setText(String.valueOf(d2.getKhachHang().getTenKH()));
						txtCMND.setText(d2.getKhachHang().getSoCMND());
						txtSoDienThoai.setText(String.valueOf(d2.getKhachHang().getSoDT()));
					} else {
						JOptionPane.showMessageDialog(null, "Không Tìm Thấy DVD!");
					}
				}

			}

		}

		if (obj.equals(btnTimTua)) {
			TuaDAO tuaDAO = new TuaDAO();
			Tua tua = tuaDAO.findTuaByTenTua(String.valueOf(txtTimTua.getText()));
			if (txtTimTua.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Bạn Chưa Nhập Tên Tựa!");
			} else if (tua.getMaTua() != null) {
				int count = 0;
				txtMaTua.setText(tua.getMaTua());
				txtTheLoai.setText(tua.getTheLoai().getTenTheLoai());
				// Số lượng copy
				DVDDao dvdDao = new DVDDao();
				List<DVD> lst = dvdDao.getAllDVD();
				for (DVD d : lst) {
					if (d.getTua().getTenTua().equals(tua.getTenTua()) && d.getTrangThai() == 1) {
						count += 1;
					}
				}
				txtSL.setText(String.valueOf(count));
			} else {
				JOptionPane.showMessageDialog(null, "Không Tìm Thấy Tựa!");
			}
		}

	}

}
