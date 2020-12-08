package defaultTableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.DVD;
import entity.KhachHang;
import entity.PhieuDatTruoc;

public class KHDatTruocTableModel extends AbstractTableModel {
	String columns[] = { "STT", "Mã KH", "Tên khách hàng", "Tựa đề DVD", "Thể loại", "Giá tiền","Ngày đặt trước"};
	List<PhieuDatTruoc> listKHDT = new ArrayList<PhieuDatTruoc>();

	public KHDatTruocTableModel(List<PhieuDatTruoc> listKHDT) {
		this.listKHDT = listKHDT;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listKHDT.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PhieuDatTruoc pdt = listKHDT.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = pdt.getKhachHang().getMaKH();
			break;
		case 2:
			result = pdt.getKhachHang().getTenKH();
			break;
		case 3:
			result = pdt.getTua().getTenTua();
			break;
		case 4:
			result = pdt.getTua().getTheLoai().getTenTheLoai();
			break;
		case 5:
			result = pdt.getTua().getTheLoai().getGiaThue();
			break;
		case 6:
			result = pdt.getNgayDatTruoc();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

}
