package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.MAX;
import org.jdatepicker.impl.JDatePickerImpl;

import dao.DVDDao;
import dao.KhachHangDAO;
import dao.PhieuDatTruocDAO;
import defaultTableModel.KHDatTruocTableModel;
import entity.DVD;
import entity.KhachHang;
import entity.PhieuDatTruoc;
import entity.Tua;

public class GUI_DatTruocTuaDVD implements ActionListener, MouseListener {
	private JTextField txtTimTua, txtHoTen, txtCMND, txtSoDienThoai, txtTongTien, xtTimDVD, txtTenTua, txtLoaiDVD,
			txtIDKH, txtTheLoai, txtSLTon, txtTimKH, btnMaDVD;
	private JButton btnTimTua, btnThemDatruoc, btnTimKH, btnXoaKH, btnGiuDVD;
	private GUI_Store store_GUI = new GUI_Store();
	private KHDatTruocTableModel khDatTruocTableModel;
	private JDatePickerImpl jdkNgayDatTruoc, jdkNgayTT;
	private JTable tblDatTruoc;
	JSpinner spinner;

	private DefaultTableModel defaultTableModel;
	private KhachHangDAO khachHangDAO;
	private PhieuDatTruocDAO phieuDatTruocDAO = new PhieuDatTruocDAO();
	private DVDDao dvdDao = new DVDDao();

	// NHAN THEM
	int row = -1;
	KhachHang khachHang;
	List<PhieuDatTruoc> list;
	List<PhieuDatTruoc> listpdt;
	Tua tua;
	DVD dvd;
	ArrayList<DVD> listGanDVD = new ArrayList<DVD>();
	int sl;
	int dem;

	public JPanel designGUI() {
		JPanel conn = new JPanel();
		conn.setLayout(null);
		JPanel pnDatLeft = taoPnThongTinDatTruoc();
		conn.add(pnDatLeft);
		conn.add(store_GUI.createLable(500, 20, 300, 50, "ĐẶT TRƯỚC TỰA DVD"));
		btnXoaKH = store_GUI.createButton(1050, 320, 240, 35, "XÓA KHÁCH HÀNG KHỎI DS ĐẶT TRƯỚC");
		btnThemDatruoc = store_GUI.createButton(780, 320, 240, 35, "THÊM KHÁCH HÀNG VÀO HÀNG ĐỢI");
		btnThemDatruoc.setEnabled(false);
		conn.add(btnThemDatruoc);
		conn.add(btnXoaKH);

		JPanel pnDSPhong = taoPnDanhSachKHDatTruoc();
		JPanel pnDatTruoc = taoPnDatTruoc();
		conn.add(pnDatTruoc);
		conn.add(pnDSPhong);

		addEvents();
		return conn;

	}

	/**
	 * 2.Panel hiện hiển thị dánh sách các khách hàng đợi đặt trước.
	 */
	private JPanel taoPnDanhSachKHDatTruoc() {
		JPanel pnSPRight = store_GUI.createPannel(740, 110, 600, 190, "Danh Sách Khách Hàng Đặt Trước");
		pnSPRight.setLayout(new BorderLayout());
		tblDatTruoc = new JTable();
		JPanel pnTableSP = store_GUI.createPanelTable(tblDatTruoc);
		pnSPRight.add(pnTableSP);
		return pnSPRight;
	}

	private JPanel taoPnDatTruoc() {
		JPanel pnSPRight = store_GUI.createPannel(740, 370, 600, 140, "Đặt trước");
		btnGiuDVD = store_GUI.createButton(175, 50, 150, 35, "GIỮ DVD");
		btnGiuDVD.setEnabled(false);
		pnSPRight.add(btnGiuDVD);
		return pnSPRight;
	}

	/**
	 * 4.Panle hiển thị thông tin đặt trước tựa DVD.
	 * 
	 * @return
	 */
	private JPanel taoPnThongTinDatTruoc() {
		JPanel pnDatLeft = store_GUI.createPannel(10, 110, 700, 400, "Thông Tin Đặt Trước Tựa DVD");

		pnDatLeft.add(store_GUI.createLable(15, 30, 90, 30, "Ngày đặt trước:"));
		jdkNgayDatTruoc = store_GUI.createJDatePicker(110, 30, 150, 30);
		pnDatLeft.add(jdkNgayDatTruoc);

		JPanel pnTimKiem = store_GUI.createPannel(10, 90, 330, 60, "Tìm Kiếm Tựa DVD");
		txtTimTua = store_GUI.createTextField(80, 20, 150, 30, 80);
		btnTimTua = store_GUI.createButton(230, 20, 95, 30, "Tìm Tựa DVD");
		btnTimTua.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKiem.add(store_GUI.createLable(5, 20, 65, 30, "Nhập Tên:"));
		pnTimKiem.add(txtTimTua);
		pnTimKiem.add(btnTimTua);

		JPanel pnTimKH = store_GUI.createPannel(350, 90, 345, 60, "Tìm Kiếm Khách Hàng");
		txtTimKH = store_GUI.createTextField(75, 20, 150, 30, 80);
		btnTimKH = store_GUI.createButton(230, 20, 107, 30, "Tìm Khách Hàng");
		btnTimTua.setIcon(store_GUI.taonICon("search.png", 20, 20));
		pnTimKH.add(store_GUI.createLable(5, 20, 60, 30, "Nhập ID:"));
		pnTimKH.add(txtTimKH);
		pnTimKH.add(btnTimKH);

		JPanel pnTua = store_GUI.createPannel(10, 190, 330, 190, "Thông Tin Tựa DVD");

		pnTua.add(store_GUI.createLable(7, 30, 90, 30, "Tên Tựa:"));
		txtTenTua = store_GUI.createTextField(110, 30, 180, 25, 150);
		pnTua.add(txtTenTua);

		pnTua.add(store_GUI.createLable(7, 70, 90, 30, "Số Lượng Tồn:"));
		txtSLTon = store_GUI.createTextField(110, 70, 180, 25, 150);
		pnTua.add(txtSLTon);

		pnTua.add(store_GUI.createLable(7, 110, 90, 30, "Thể Loại:"));
		txtTheLoai = store_GUI.createTextField(110, 110, 180, 25, 150);
		pnTua.add(txtTheLoai);

		txtTheLoai.setEditable(false);
		txtSLTon.setEditable(false);

		/**
		 * 4. Hiện thị thông tin khách hàng thông qua tìm kiếm theo mã.
		 */
		JPanel pnKhachHang = store_GUI.createPannel(350, 190, 330, 190, "Thông Tin Khách Hàng");

		pnKhachHang.add(store_GUI.createLable(7, 30, 80, 30, "Họ Tên:"));
		txtHoTen = store_GUI.createTextField(120, 30, 200, 25, 150);
		pnKhachHang.add(txtHoTen);

		pnKhachHang.add(store_GUI.createLable(7, 70, 80, 25, "Số CMND:"));
		txtCMND = store_GUI.createTextField(120, 70, 200, 25, 10);
		pnKhachHang.add(txtCMND);

		pnKhachHang.add(store_GUI.createLable(7, 110, 80, 25, "Số Điện Thoại:"));
		txtSoDienThoai = store_GUI.createTextField(120, 110, 200, 25, 11);
		pnKhachHang.add(txtSoDienThoai);

		txtHoTen.setEditable(false);
		txtSoDienThoai.setEditable(false);
		txtCMND.setEditable(false);

		pnDatLeft.add(pnTimKiem);
		pnDatLeft.add(pnKhachHang);
		pnDatLeft.add(pnTimKH);
		pnDatLeft.add(pnTua);

		return pnDatLeft;
	}

	/**
	 * 5.ĐĂNG KÍ CÁC SỰ KIỆN TRONG FORM THANH TOÁN GIỎ HÀNG.
	 */

	private void addEvents() {
		btnTimTua.addActionListener(this);
		btnTimKH.addActionListener(this);
		tblDatTruoc.addMouseListener(this);
		btnThemDatruoc.addActionListener(this);
		btnXoaKH.addActionListener(this);
		btnGiuDVD.addActionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//TODO Auto-generated method stub

		row = tblDatTruoc.getSelectedRow();

//		txtMaDVD.setText(row + "");
	}

	@Override
	public void mousePressed(MouseEvent e) {
//TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
//TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
//TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
//TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(btnTimTua)) {
			timDVDTheoTua();
		} else if (obj.equals(btnTimKH)) {
			timKhachHang();
		} else if (obj.equals(btnXoaKH)) {
			xoaKhachHangKhoiHangDoi();
		} else if (obj.equals(btnThemDatruoc)) {
			themKHVaoHangDoi();
			btnThemDatruoc.setEnabled(false);
		} else if (obj.equals(btnGiuDVD)) {
			ganDiaChoKH();
			btnGiuDVD.setEnabled(false);
		}

	}

	/**
	 * Xử lý button Thêm Khách Hàng Vào Hàng Đợi(NHÂN)
	 */
	public void themKHVaoHangDoi() {
		String tuaphim = txtTimTua.getText().toString();

		list = phieuDatTruocDAO.showDSKHDatTruoc();
		List<PhieuDatTruoc> listpdt = new ArrayList<PhieuDatTruoc>();
		for (PhieuDatTruoc pdt : list) {
			if (pdt.getTua().getTenTua().equals(tuaphim)) {
				listpdt.add(pdt);
			}
		}

		String columns[] = { "STT", "Mã KH", "Tên khách hàng", "Tựa đề DVD", "Thể loại", "Ngày đặt trước", "Mã DVD" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		int i = 1;
		for (PhieuDatTruoc pdt : listpdt) {
			String data[] = { i + "", pdt.getKhachHang().getMaKH(), pdt.getKhachHang().getTenKH(),
					pdt.getTua().getTenTua(), pdt.getTua().getTheLoai().getTenTheLoai(), pdt.getNgayDatTruoc() + "" };
			defaultTableModel.addRow(data);
			i++;
		}

		String rowData[] = { i + 1 + "", khachHang.getMaKH(), khachHang.getTenKH(), txtTenTua.getText().toString(),
				txtTenTua.getText().toString(), LocalDate.now() + "" };
		defaultTableModel.addRow(rowData);
		tblDatTruoc.setModel(defaultTableModel);

		// Them vao CS
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PhieuDatTruoc pdt = null;
		String mapdt = taoMaPhieuDatTruoc();
		try {
			pdt = new PhieuDatTruoc(mapdt, df.parse(String.valueOf(LocalDate.now())), khachHang, tua, null,1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		phieuDatTruocDAO.persist(pdt);
		txtHoTen.setText("");
		txtCMND.setText("");
		txtSoDienThoai.setText("");
	}
	
	public String taoMaPhieuDatTruoc() {
		String maPDT = "";
		list = phieuDatTruocDAO.showDSKHDatTruoc();
		String ma = list.get(list.size() - 1).getMaDatTruoc();
		String[] splits = ma.split("[PDT]");
		StringBuilder stringBuilder = new StringBuilder();
		for (String item : splits)
			stringBuilder.append(item);
		String chuoi = String.valueOf(stringBuilder);
		int i = Integer.parseInt(chuoi) + 1;

		if (i < 10)
			maPDT = "PDT0" + String.valueOf(i);
		else
			maPDT = "PDT" + String.valueOf(i);
		return maPDT;

	}

	/**
	 * Xử lý button tìm tựa DVD(NHÂN)
	 */

	public void timDVDTheoTua() {
		dem = 0;
		String tuaphim = txtTimTua.getText().toString();

		list = phieuDatTruocDAO.showDSKHDatTruoc();
		listpdt = new ArrayList<PhieuDatTruoc>();
		for (PhieuDatTruoc pdt : list) {
			if (pdt.getTua().getTenTua().equals(tuaphim)) {
				listpdt.add(pdt);

			}
		}

		String columns[] = { "STT", "Mã KH", "Tên KH", "Tựa DVD", "Thể loại", "Ngày đặt trước", "Mã DVD" };
		defaultTableModel = new DefaultTableModel(columns, 0);
		int i = 1;
		for (PhieuDatTruoc pdt : listpdt) {
			String data[] = { i + "", pdt.getKhachHang().getMaKH(), pdt.getKhachHang().getTenKH(),
					pdt.getTua().getTenTua(), pdt.getTua().getTheLoai().getTenTheLoai(), pdt.getNgayDatTruoc() + "" };
			defaultTableModel.addRow(data);
			i++;
		}
		tblDatTruoc.setModel(defaultTableModel);

		sl = 0;

		List<DVD> listdvd = dvdDao.getAllDVD();
		List<DVD> listDia = new ArrayList<DVD>();
		for (DVD dvd : listdvd) {
			if (dvd.getTua().getTenTua().equals(tuaphim)) {
				listDia.add(dvd);
				tua = dvd.getTua();
				if (dvd.getTrangThai() == 1) {
					listGanDVD.add(dvd);
					sl++;
				}
			}
		}

		txtTenTua.setText(listDia.get(0).getTua().getTenTua());
		txtTheLoai.setText(listDia.get(0).getTua().getTheLoai().getTenTheLoai());
		txtSLTon.setText(sl + "");
		if (sl > 0)
			btnGiuDVD.setEnabled(true);
		else
			btnGiuDVD.setEnabled(false);

	}

	public void ganDiaChoKH() {

		if (sl > tblDatTruoc.getModel().getRowCount()) {
			for (int i = 0; i < tblDatTruoc.getModel().getRowCount(); i++) {
				tblDatTruoc.setValueAt(listGanDVD.get(i).getMaDVD(), dem, 6);
				dem++;

				listpdt.get(i).setDvd(listGanDVD.get(i));
				phieuDatTruocDAO.merge(listpdt.get(i));
				listGanDVD.get(i).setTrangThai(3);
				dvdDao.merge(listGanDVD.get(i));
			}
			txtSLTon.setText(sl - tblDatTruoc.getModel().getRowCount() + "");

		} else {
			for (int i = 0; i < sl; i++) {
				tblDatTruoc.setValueAt(listGanDVD.get(i).getMaDVD(), dem, 6);
				dem++;
				listpdt.get(i).setDvd(listGanDVD.get(i));
				listpdt.get(i).setTrangThai(2);
				phieuDatTruocDAO.merge(listpdt.get(i));
				listGanDVD.get(i).setTrangThai(3);
				dvdDao.merge(listGanDVD.get(i));
			}
			txtSLTon.setText("0");
		}

//		

	}

	/**
	 * Xử lý button tìm Khách Hàng(NHÂN)
	 */
	public void timKhachHang() {
		khachHangDAO = new KhachHangDAO();
		String idKhach = txtTimKH.getText();
		for (KhachHang kh : khachHangDAO.getAllKH()) {
			if (kh.getMaKH().equals(idKhach) == true) {
				txtHoTen.setText(kh.getTenKH());
				txtCMND.setText(kh.getSoCMND());
				txtSoDienThoai.setText(kh.getSoDT());
				khachHang = kh;

			}
		}
		int i = 0;
		for (PhieuDatTruoc pdt : listpdt) {
			if (pdt.getKhachHang().getMaKH().equals(khachHang.getMaKH()))
				i++;
		}
		if (i <= 0)
			btnThemDatruoc.setEnabled(true);
		else
			btnThemDatruoc.setEnabled(false);
	}

	public void xoaKhachHangKhoiHangDoi() {
		defaultTableModel.removeRow(row);
		System.out.println(listpdt.get(row).getNgayDatTruoc() + listpdt.get(row).getKhachHang().getMaKH());
		phieuDatTruocDAO.removePhieuDT(listpdt.get(row));
		listpdt.remove(row);
	}

}
