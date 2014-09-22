package com.kerbalstuff.api

import java.util.List;

import javax.swing.table.AbstractTableModel;

class ModVersionTableModel extends AbstractTableModel {
	protected List<ModVersion> modVersions;
	protected static final int COLUMN_COUNT=5;
	protected static final String[] COLUMN_NAMES = ["ID", "Version", "KSP Version", "Download Path", "Changelog"];
	
	public ModVersionTableModel(){
	
	}
	
	public ModVersionTableModel(List<ModVersion> modVersions){
		this.modVersions = modVersions;
	}

	public void setModVersions(List<ModVersion> newModVersions){
		modVersions = newModVersions;
		fireTableDataChanged();
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		return modVersions?modVersions.size():0;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ModVersion m = modVersions?.get(rowIndex);
		if(m){
			switch(columnIndex){
				case 0: return m.getId();
				case 1: return m.getFriendlyVersion();
				case 2: return m.getKspVersion();
				case 3: return m.getDownloadPath();
				case 4: return m.getChangelog();
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false; 
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}
}
