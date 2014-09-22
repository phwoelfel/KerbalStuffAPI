package com.kerbalstuff.api

import java.util.List;

import javax.swing.table.AbstractTableModel;

class ModTableModel extends AbstractTableModel{

	protected List<Mod> mods;
	protected static final int COLUMN_COUNT=6;
	protected static final String[] COLUMN_NAMES = ["ID", "Name", "author", "Downloads", "Followers", "Description"];
	
	public ModTableModel(){
		
	}
	
	public ModTableModel(List<Mod> mods){
		this.mods = mods;
	}
	
	public void setMods(List<Mod> newMods){
		mods = newMods;
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
			case 0:
			case 3:
			case 4:
				return Integer.class;
				
			case 1:
			case 2:
			case 5:
				return String.class;
		}
		return String.class;
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		return mods?mods.size():0;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mod m = mods?.get(rowIndex);
		if(m){
			switch(columnIndex){
				case 0: return m.getId();
				case 1: return m.getName();
				case 2: return m.getAuthor();
				case 3: return m.getDownloads();
				case 4: return m.getFollowers();
				case 5: return m.getShortDescription();
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
		/*
		// there is no update method in the API yet
		Mod m = mods.get(rowIndex);
		if(m){
			switch(columnIndex){
				case 1: m.setName(aValue);
				case 5: m.setShortDescription(aValue);
			}
		}
		*/
	}
}
