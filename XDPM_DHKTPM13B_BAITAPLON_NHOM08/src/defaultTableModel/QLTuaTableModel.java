package defaultTableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entity.Tua;

public class QLTuaTableModel extends AbstractTableModel{

	String columns[]= {"STT", "Mã tựa", "Tên tựa", "Thể loại"};
	List<Tua> listTua= new ArrayList<Tua>();
	
	public QLTuaTableModel(List<Tua> listTua) {
		// TODO Auto-generated constructor stub
		this.listTua= listTua;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columns.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listTua.size();
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
		Tua tua= listTua.get(rowIndex);
		Object result= null;
		switch( columnIndex) {
		case 0:
			result= rowIndex+1;
			break;
			
		case 1:
			result= tua.getMaTua();
			break;
		
		case 2:
			result= tua.getTenTua();
			break;
		
		case 3:
			result= tua.getTheLoai();
			break;
		}
		return result;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}
}
