package com.kerbalstuff.api

import java.util.List;

import javax.swing.table.AbstractTableModel

class UserTableModel extends AbstractTableModel {

	protected List<User> users;
	protected static final int COLUMN_COUNT=6;
	protected static final String[] COLUMN_NAMES = ["Username", "Twitter", "Reddit", "IRC", "Forum", "Description"];
	
	public UserTableModel(){
	
	}
	
	public UserTableModel(List<User> users){
		this.users = users;
	}

	public void setUsers(List<User> newUsers){
		users = newUsers;
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
		return users?users.size():0;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User u = users?.get(rowIndex);
		if(u){
			switch(columnIndex){
				case 0: return u.getUsername();
				case 1: return u.getTwitterUsername();
				case 2: return u.getRedditUsername();
				case 3: return u.getIrcNick();
				case 4: return u.getForumUsername();
				case 5: return u.getDescription();
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex==0?false:true; // can't change username
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		/*
		// no update in the API yet
		User u = users?.get(rowIndex);
		if(u){
			switch(columnIndex){
				case 1: u.setTwitterUsername(aValue);
				case 2: u.setRedditUsername(aValue);
				case 3: u.setIrcNick(aValue);
				case 4: u.setForumUsername(aValue);
				case 5: u.setDescription(aValue);
			}
		}
		*/
	}
}
