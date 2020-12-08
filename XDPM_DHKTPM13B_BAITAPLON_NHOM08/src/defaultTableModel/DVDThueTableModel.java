package defaultTableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.DVD;

public class DVDThueTableModel  extends AbstractTableModel {
	String columns[] = { "STT", "ID DVD", "Tựa DVD","Thể Loại"};
	List<DVD> listDVD = new ArrayList<DVD>();

	public DVDThueTableModel(List<DVD> listDVD) {
		this.listDVD = listDVD;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return listDVD.size();
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
		DVD dvd = listDVD.get(rowIndex);
		Object result = null;
		switch (columnIndex) {
		case 0:
			result = rowIndex + 1;
			break;
		case 1:
			result = dvd.getMaDVD();
			break;
		case 2:
			result = dvd.getTua().getTenTua();
			break;
		case 3:
			result = dvd.getTua().getTheLoai().getTenTheLoai();
			break;
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

}
