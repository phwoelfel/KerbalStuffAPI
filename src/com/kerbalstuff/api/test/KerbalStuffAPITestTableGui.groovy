package com.kerbalstuff.api.test

import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.JTextField

import com.kerbalstuff.api.KerbalStuffAPI
import com.kerbalstuff.api.KerbalStuffAPIException
import com.kerbalstuff.api.Mod
import com.kerbalstuff.api.ModTableModel
import com.kerbalstuff.api.ModVersionTableModel
import com.kerbalstuff.api.User
import com.kerbalstuff.api.UserTableModel

class KerbalStuffAPITestTableGui extends JFrame implements ActionListener{

	protected KerbalStuffAPI api;
	
	protected JTextField searchField;
	protected JButton searchModButton;
	protected JButton searchModVersionButton;
	protected JButton searchUserButton;
	
	protected JTable table;
	protected ModTableModel modModel;
	protected ModVersionTableModel modVersionModel;
	protected UserTableModel userModel;
	

	
	public KerbalStuffAPITestTableGui(){
		api = new KerbalStuffAPI();
		
		
		setTitle("KerbalStuff API Test");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		searchField = new JTextField(10);
		
		searchModButton = new JButton("search mod");
		searchModButton.addActionListener(this);
		
		searchModVersionButton = new JButton("get mod versions");
		searchModVersionButton.addActionListener(this);
		
		searchUserButton = new JButton("search user");
		searchUserButton.addActionListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.add(searchField);
		topPanel.add(searchModButton);
		topPanel.add(searchModVersionButton);
		topPanel.add(searchUserButton);
		
		add(topPanel, BorderLayout.NORTH);
		
	
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		modModel = new ModTableModel();
		modVersionModel = new ModVersionTableModel();
		userModel = new UserTableModel();
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			Object source = e.getSource();
			if(source == searchModButton){
				String txt = searchField.getText();
				if(txt){
					List<Mod> mods = api.searchMod(txt);
					table.setModel(modModel)
					modModel.setMods(mods);
				}
			}
			else if(source == searchUserButton){
				String txt = searchField.getText();
				if(txt){
					List<User> users = api.searchUser(txt);
					table.setModel(userModel)
					userModel.setUsers(users);
				}
			}
			else if(source == searchModVersionButton){
				String txt = searchField.getText();
				if(txt){
					try{
						Mod m = api.getMod(Integer.parseInt(txt));
						table.setModel(modVersionModel)
						modVersionModel.setModVersions(m.getModVersions());
					}
					catch(NumberFormatException ex){
						JOptionPane.showMessageDialog(this, "Error: Invalid mod ID.", "Error!", JOptionPane.ERROR_MESSAGE)
					}
				}
			}
		}
		catch(KerbalStuffAPIException apiex){
			JOptionPane.showMessageDialog(this, "An API Error occured! Please check the log!${apiex.getReason()?:'\n'}", "Error!", JOptionPane.ERROR_MESSAGE);
			println("Error!!!")
			if(apiex.getReason()){
				println("Reason: ${apiex.getReason()}")
			}
			if(apiex.getRequest()){
				println("Request: ${apiex.getRequest()}")
				println("Data: ${new String(apiex.getRequest().data)}")
			}
			if(apiex.getResponse()){
				println("Response: ${apiex.getResponse()}")
				println("Data: ${new String(apiex.getResponse().data)}")
			}
		}
		
	}
	
	
}
