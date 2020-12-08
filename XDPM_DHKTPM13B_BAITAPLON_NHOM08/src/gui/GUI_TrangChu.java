package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.NamingException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.transaction.Transactional.TxType;

import com.alee.laf.WebLookAndFeel;

public class GUI_TrangChu extends JFrame implements ActionListener {
	private JMenuItem mniTaiKhoan, mniQuanLyThueDVD, mniQuanLyTraDVD, mniBaoCaoTrangThaiDVD,
			 mniQLKH, mniThanhToanPhiTreHen, mniDatTruocTuaDVD;

	private JMenuBar mnuBar;
	private JMenu mnuQuanLyDVD, mnuQuanLyTraDVD, mnuBaoCaoTrangThaiDVD, mnuQLKH,
			mnuPhiTreHen, mnuDatTruocTuaDVD;
	private JPanel pnGiaoDien;

	// Quản lý
	private JMenuItem mniQLKhachHang, mniQLTua, mniQLDVD, mniHuyPhiTreHen, mniDatGiaTGThue, mniBaoCaoKH, mniBaoCaoTua,
			mniBaoCaoDVD;

	private JMenu mnuTaiKhoan, mnuDangXuat, mnuQLKhachHang, mnuQLTua, mnuQLDVD, mnuHuyPhiTreHen, mnuDatGiaTGThue,
			mnuBaoCaoKH, mnuBaoCaoTua, mnuBaoCao;

	private GUI_Store storeGUI = new GUI_Store();
	private GUI_QuanLyThueDVD guiQuanLyThueDVD;
	private GUI_QuanLyTraDVD guiQuanLyTraDVD;
	private GUI_BaoCaoTrangThaiDVD guiBaoCaoTrangThaiDVD;
	private GUI_BaoCaoTrangThaiTuaDVD guiBaoCaoTrangThaiTuaDVD;
	private GUI_ThanhToanPhiTreHen guiThanhToanPhiTreHen;
	private GUI_DatTruocTuaDVD guiDatTruocTuaDVD;
	private GUI_QuanLyKhachHang guiQuanLyKhachHang;

	// Quản lý
	private GUI_XoaKhachHang gui_XoaKhachHang = new GUI_XoaKhachHang();
	private GUI_QuanLyTua gui_QuanLyTua = new GUI_QuanLyTua();
	private GUI_QuanLyDVD gui_QuanLyDVD = new GUI_QuanLyDVD();
	private GUI_HuyPhiTrenHen gui_HuyPhiTrenHen = new GUI_HuyPhiTrenHen();
	private GUI_DatGiaTGThue gui_DatGiaTGThue = new GUI_DatGiaTGThue();
	private GUI_BaoCaoKH gui_BaoCaoKH = new GUI_BaoCaoKH();
	private GUI_BaoCaoDVD gui_BaoCaoDVD = new GUI_BaoCaoDVD();
	private GUI_BaoCaoTua gui_BaoCaoTua = new GUI_BaoCaoTua();
	private GUI_Login gui_Login = new GUI_Login();
	/**
	 * Đặt tên cho các giao diện được add vào cardLayout.
	 */
	private static final String QUANLYTHUEDVD = "QUANLYTHUEDVD";
	private static final String QUANLYTRADVD = "QUANLYTRADVD";
	private static final String BAOCAOTRANGTHAIDVD = "BAOCAOTRANGTHAIDVD";
	private static final String QUANLYKHACHHANG = "QUANLYKHACHHANG";
	private static final String THANHTOANPHITREHEN = "THANHTOANPHITREHEN";
	private static final String DATTTRUOCTUADVD = "DATTTRUOCTUADVD";

	// Quản lý
	private static final String XOAKHACHHANG = "XOAKHACHHANG";
	private static final String QUANLYTUA = "QUANLYTUA";
	private static final String QUANLYDVD = "QUANLYDVD";
	private static final String HUYPHITREHEN = "HUYPHITREHEN";
	private static final String DATGIATHOIGIANTHUE = "DATGIATHOIGIANTHUE";
	private static final String BAOCAOKH = "BAOCAOKH";
	private static final String BAOCAOTUA = "BAOCAOTUA";
	private static final String BAOCAODVD = "BAOCAODVD";
	private static final String DANGNHAP = "DANGNHAP";

	/**
	 * Gui main : GUI giao diện chứa thông tin nhân viên.
	 * 
	 * @param nhanVien
	 * @return
	 * @throws JMSException
	 * @throws NamingException
	 */
	public GUI_TrangChu() throws NamingException {
		setTitle("Trang chủ");
		addControls();
		showWinDows();
		addEvents();
	}

	private void addControls() throws NamingException {
		Container con = getContentPane();
		mnuBar = createMenuBar();
		setJMenuBar(mnuBar);
		con.setLayout(new BorderLayout());
		pnGiaoDien = createPanelGiaoDien();
		con.add(pnGiaoDien, BorderLayout.CENTER);
	}

	/**
	 * TẠO JMENUBAR CHO ỨNG DỤNG.
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		JMenuBar mnuBar = new JMenuBar();

		mnuQuanLyDVD = createJMenu("Quản Lý Thuê-Trả");
		mnuQuanLyDVD.add(mniQuanLyThueDVD = createMenuIterm("Quản lý thuê DVD"));
		mnuQuanLyDVD.add(mniQuanLyTraDVD = createMenuIterm("Quản lý trả DVD"));

		mnuBaoCaoTrangThaiDVD = createJMenu("Báo Cáo Trạng Thái");
		mnuBaoCaoTrangThaiDVD.add(mniBaoCaoTrangThaiDVD = createMenuIterm("Báo cáo trạng thái DVD-TỰA"));

		mnuQLKH = createJMenu("Quản lý khách hàng");
		mnuQLKH.add(mniQLKH = createMenuIterm("Thêm-Sửa khách hàng"));
		mnuQLKH.add(mniQLKhachHang = createMenuIterm("Xóa khách hàng"));

		mnuPhiTreHen = createJMenu("Phí Trễ Hẹn");
		mnuPhiTreHen.add(mniThanhToanPhiTreHen = createMenuIterm("Thanh toán phí trễ hẹn"));
		mnuPhiTreHen.add(mniHuyPhiTreHen = createMenuIterm("Hủy Phí Trễ Hẹn"));

		mnuDatTruocTuaDVD = createJMenu("Đặt trước tựa DVD");
		mnuDatTruocTuaDVD.add(mniDatTruocTuaDVD = createMenuIterm("Đặt trước tựa DVD"));

		// Quản lý
		mnuQLTua = createJMenu("Quản Lý Tựa");
		mnuQLTua.add(mniQLTua = createMenuIterm("Quản Lý Tựa"));

		mnuQLDVD = createJMenu("Quản lý DVD");
		mnuQLDVD.add(mniQLDVD = createMenuIterm("Quản lý DVD"));

		mnuDatGiaTGThue = createJMenu("Đặt Giá-Thời Gian Thuê");
		mnuDatGiaTGThue.add(mniDatGiaTGThue = createMenuIterm("Đặt Giá-Thời Gian Thuê"));

		mnuBaoCao = createJMenu("Báo Cáo");
		mnuBaoCao.add(mniBaoCaoDVD = createMenuIterm("Báo Cáo DVD"));
		mnuBaoCao.add(mniBaoCaoKH = createMenuIterm("Báo Cáo Tựa"));
		mnuBaoCao.add(mniBaoCaoKH = createMenuIterm("Báo Cáo Khách Hàng"));

		mnuBar.add(mnuQuanLyDVD);
		mnuBar.add(mnuQLKH);
		mnuBar.add(mnuPhiTreHen);
		mnuBar.add(mnuDatTruocTuaDVD);
		mnuBar.add(mnuBaoCaoTrangThaiDVD);

		// Quản lý
		mnuBar.add(mnuQLDVD);
		mnuBar.add(mnuQLTua);
		mnuBar.add(mnuDatGiaTGThue);
		mnuBar.add(mnuBaoCao);
		return mnuBar;
	}

	private JMenuItem createMenuIterm(String text) {
		JMenuItem mnuIterm = new JMenuItem(text);
		return mnuIterm;
	}

	private JMenu createJMenu(String text) {
		JMenu mnu = new JMenu(text);
		return mnu;
	}

	private JPanel createPanelGiaoDien() throws NamingException {
		JPanel panel = new JPanel();
		panel.setLayout(new CardLayout());
		guiQuanLyThueDVD = new GUI_QuanLyThueDVD();
		guiQuanLyTraDVD = new GUI_QuanLyTraDVD();
		guiBaoCaoTrangThaiDVD = new GUI_BaoCaoTrangThaiDVD();
		guiBaoCaoTrangThaiTuaDVD = new GUI_BaoCaoTrangThaiTuaDVD();
		guiQuanLyKhachHang = new GUI_QuanLyKhachHang();
		guiDatTruocTuaDVD = new GUI_DatTruocTuaDVD();
		guiThanhToanPhiTreHen = new GUI_ThanhToanPhiTreHen();

		panel.add(guiQuanLyThueDVD.designGUI(), QUANLYTHUEDVD);
		panel.add(guiQuanLyTraDVD.designGUI(), QUANLYTRADVD);
		panel.add(guiThanhToanPhiTreHen.designGUI(), THANHTOANPHITREHEN);
		panel.add(guiQuanLyKhachHang.designGUI(), QUANLYKHACHHANG);
		panel.add(guiDatTruocTuaDVD.designGUI(), DATTTRUOCTUADVD);
		panel.add(guiBaoCaoTrangThaiDVD.designGUI(), BAOCAOTRANGTHAIDVD);
		panel.add(gui_XoaKhachHang.designGUI(), XOAKHACHHANG);
		return panel;
	}

	private void showWinDows() {
		this.setSize(1360, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void addEvents() {
		mniQuanLyThueDVD.addActionListener(this);
		mniQuanLyTraDVD.addActionListener(this);
		mniQLKH.addActionListener(this);
		mniThanhToanPhiTreHen.addActionListener(this);
		mniDatTruocTuaDVD.addActionListener(this);

		// Quản lý
		mniQLTua.addActionListener(this);
		mniQLKhachHang.addActionListener(this);
		mniQLDVD.addActionListener(this);
		mniDatGiaTGThue.addActionListener(this);
		mniHuyPhiTreHen.addActionListener(this);
		mniBaoCaoKH.addActionListener(this);
		mniBaoCaoTrangThaiDVD.addActionListener(this);
		mniBaoCaoDVD.addActionListener(this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WebLookAndFeel.install();
				try {
					new GUI_TrangChu();
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					UIManager.setLookAndFeel(new WebLookAndFeel());
					UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
					UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
				} catch (Exception e) {
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent s) {
		// TODO Auto-generated method stub

		Object object = s.getSource();
		CardLayout cardLayout = (CardLayout) pnGiaoDien.getLayout();
		if (object.equals(mniQuanLyThueDVD)) {
			pnGiaoDien.add(guiQuanLyThueDVD.designGUI(), QUANLYTHUEDVD);
			cardLayout.show(pnGiaoDien, QUANLYTHUEDVD);
		}
		if (object.equals(mniQuanLyTraDVD)) {
			pnGiaoDien.add(guiQuanLyTraDVD.designGUI(), QUANLYTRADVD);
			cardLayout.show(pnGiaoDien, QUANLYTRADVD);
		}
		if (object.equals(mniThanhToanPhiTreHen)) {
			pnGiaoDien.add(guiThanhToanPhiTreHen.designGUI(), THANHTOANPHITREHEN);
			cardLayout.show(pnGiaoDien, THANHTOANPHITREHEN);
		}
		if (object.equals(mniQLKH)) {
			pnGiaoDien.add(guiQuanLyKhachHang.designGUI(), QUANLYKHACHHANG);
			cardLayout.show(pnGiaoDien, QUANLYKHACHHANG);
		}
		if (object.equals(mniDatTruocTuaDVD)) {
			pnGiaoDien.add(guiDatTruocTuaDVD.designGUI(), DATTTRUOCTUADVD);
			cardLayout.show(pnGiaoDien, DATTTRUOCTUADVD);
		}

		// Quản lý
		if (object.equals(mniQLTua)) {
			pnGiaoDien.add(gui_QuanLyTua.designGUI(), QUANLYTUA);
			cardLayout.show(pnGiaoDien, QUANLYTUA);
		}
		if (object.equals(mniQLDVD)) {
			JDialog dialog = new GUI_Login();
			dialog.setVisible(true);
			dialog.isAlwaysOnTop();
			pnGiaoDien.add(gui_QuanLyDVD.designGUI(), QUANLYDVD);

			cardLayout.show(pnGiaoDien, QUANLYDVD);
		}
		if (object.equals(mniQLKhachHang)) {
			JDialog dialog = new GUI_Login();
			dialog.setVisible(true);
			dialog.isAlwaysOnTop();

			pnGiaoDien.add(gui_XoaKhachHang.designGUI(), XOAKHACHHANG);
			cardLayout.show(pnGiaoDien, XOAKHACHHANG);

		}
		if (object.equals(mniDatGiaTGThue)) {
			JDialog dialog = new GUI_Login();
			dialog.setVisible(true);
			dialog.isAlwaysOnTop();
			pnGiaoDien.add(gui_DatGiaTGThue.designGUI(), DATGIATHOIGIANTHUE);
			cardLayout.show(pnGiaoDien, DATGIATHOIGIANTHUE);
		}
		if (object.equals(mniHuyPhiTreHen)) {
			JDialog dialog = new GUI_Login();
			dialog.setVisible(true);
			dialog.isAlwaysOnTop();
			pnGiaoDien.add(gui_HuyPhiTrenHen.designGUI(), HUYPHITREHEN);
			cardLayout.show(pnGiaoDien, HUYPHITREHEN);
		}
		if (object.equals(mniBaoCaoKH)) {
			pnGiaoDien.add(gui_BaoCaoKH.designGUI(), BAOCAOKH);
			cardLayout.show(pnGiaoDien, BAOCAOKH);
		}
		if (object.equals(mniBaoCaoDVD)) {
			pnGiaoDien.add(gui_BaoCaoDVD.designGUI(), BAOCAODVD);
			cardLayout.show(pnGiaoDien, BAOCAODVD);
			Gui_TestBaoCao gui = new Gui_TestBaoCao();
			gui.setVisible(true);
			
		}
		if (object.equals(mniBaoCaoTua)) {
			pnGiaoDien.add(gui_BaoCaoKH.designGUI(), BAOCAOTUA);
			cardLayout.show(pnGiaoDien, BAOCAOTUA);
		}
		if(object.equals(mniBaoCaoTrangThaiDVD)) {
			pnGiaoDien.add(guiBaoCaoTrangThaiDVD.designGUI(), BAOCAOTRANGTHAIDVD);
			cardLayout.show(pnGiaoDien, BAOCAOTRANGTHAIDVD);
		}

	}

}
