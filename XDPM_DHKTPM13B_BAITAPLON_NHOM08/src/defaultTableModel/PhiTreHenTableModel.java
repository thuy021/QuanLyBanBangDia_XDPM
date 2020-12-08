package defaultTableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.PhieuThueTra;

public class PhiTreHenTableModel extends AbstractTableModel {
	String columns[] = {"STT", "Mã Phiếu", "ID DVD", "Ngày Thuê", "Ngày Trả", "Phí Trễ Hẹn" };
	List<PhieuThueTra> listPhieuThueTra = new ArrayList<PhieuThueTra>();

	public PhiTreHenTableModel(List<PhieuThueTra> listPhieuThueTra) {
		this.listPhieuThueTra = listPhieuThueTra;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listPhieuThueTra.size();
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
		PhieuThueTra phieuThueTra = listPhieuThueTra.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = phieuThueTra.getMaPhieu();
			break;
		case 2:
			result = phieuThueTra.getDvd().getMaDVD();
			break;
		case 3:
			result = phieuThueTra.getNgayThue();
			break;
		case 4:
			result = phieuThueTra.getNgayTra();
			break;
		case 5:
			result = phieuThueTra.getPhiTreHen();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
