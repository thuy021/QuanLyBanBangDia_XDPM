/**
 * 
 */
package gui;
/*
* (C) Copyright 2020 . All rights reserved.
*
* @author: Phan Bach
* @date: Nov 24, 2020
* @version: 1.0
*/

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

/**
 * @author Phan Bach
 *
 */
public class GUI_Login extends JDialog implements ActionListener{
	private GUI_Store store_GUI = new GUI_Store();
	private JTextField txtTK, txtMK;
	private JButton btnDangNhap;
	private static String taiKhoan = "";
	private static String matKhau = "";
	/**
	 * 
	 */
	public GUI_Login() {
		// TODO Auto-generated constructor stub
		setTitle("Đăng nhập");
		designGUI();
		showWinDows();
	}
	
	private void showWinDows() {
		this.setSize(500, 550);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
	}
	
	public void designGUI() {
		Container conn = getContentPane();
		conn.setLayout(null);

		JLabel lblICon = new JLabel();

		lblICon.setBounds(180, 10, 100, 100);
		conn.add(lblICon);
		conn.add(store_GUI.createLable(100, 10, 300, 30, "Đăng nhập "));

		JPanel pnChiTietNV = taoPanleTTChiTietKhachHang();
		btnDangNhap = store_GUI.createButton(160, 420, 180, 30, "Đăng nhập");
		conn.add(btnDangNhap);
		conn.add(pnChiTietNV);
		
		btnDangNhap.addActionListener(this);
	}
	
	private JPanel taoPanleTTChiTietKhachHang() {
		JPanel pnChiTietKhachHang = store_GUI.createPannel(25, 80, 450, 300, "Đăng nhập");
		pnChiTietKhachHang.add(store_GUI.createLable(20, 30, 80, 30, "Tên tài khoản :"));
		txtTK = store_GUI.createTextField(115, 30, 200, 30, 100);
		pnChiTietKhachHang.add(txtTK);


		pnChiTietKhachHang.add(store_GUI.createLable(20, 80, 80, 30, "Mật khẩu:"));
		txtMK = store_GUI.createPasswordField(115, 80, 200, 30, 100);
		pnChiTietKhachHang.add(txtMK);


		return pnChiTietKhachHang;
	}
	
	@Override
	public void actionPerformed(ActionEvent s) {
		taiKhoan = txtTK.getText();
		matKhau = txtMK.getText();
		Object object = s.getSource();
		if(object.equals(btnDangNhap)) {
			if(taiKhoan.isEmpty() || matKhau.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Chưa nhập tài khoản hoặc mật khẩu");
			}
		}
		
		if(txtTK.getText().equalsIgnoreCase("admin") || txtMK.getText().equalsIgnoreCase("admin")) {
			this.setVisible(false);
		}
		else {
			JOptionPane.showMessageDialog(null, "Sai thông tin tài khoản hoặc mật khẩu");
		}
	}


}
