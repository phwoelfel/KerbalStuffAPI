package com.kerbalstuff.api.test

import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JSplitPane
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

import com.kerbalstuff.api.KerbalStuffAPI
import com.kerbalstuff.api.Mod
import com.kerbalstuff.api.ModVersion
import com.kerbalstuff.api.User

class KerbalStuffAPITestGui extends JFrame implements ActionListener, ListSelectionListener{

	protected KerbalStuffAPI api;
	
	protected JTextField searchField;
	protected JButton searchModButton;
	protected JButton searchUserButton;
	
	protected JList resultList;
	protected JPanel infoPanel;
	
	protected JTextField nameField;
	protected JTextField authorField;
	protected JTextField downloadsField;
	protected JTextField followersField;
	protected JTextField shortDescriptionField;
	protected JTextField defaultVersionField;
	protected JList<ModVersion> modVersionList;
	
	public KerbalStuffAPITestGui(){
		api = new KerbalStuffAPI();
		
		
		setTitle("KerbalStuff API Test");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		searchField = new JTextField(10);
		searchModButton = new JButton("search mod");
		searchModButton.addActionListener(this);
		
		searchUserButton = new JButton("search user");
		searchUserButton.addActionListener(this);
		
		JPanel topPanel = new JPanel();
		topPanel.add(searchField);
		topPanel.add(searchModButton);
		topPanel.add(searchUserButton);
		
		add(topPanel, BorderLayout.NORTH);
		
		JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		resultList = new JList();
		//resultList.setPreferredSize(new Dimension(200, 500));
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.addListSelectionListener(this);
		centerPanel.add(new JScrollPane(resultList));
		
		infoPanel = new JPanel(new GridBagLayout());
		initializeModPanel();
		centerPanel.add(infoPanel);
		
		add(centerPanel, BorderLayout.CENTER);
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == searchModButton){
			String txt = searchField.getText();
			if(txt){
				List<Mod> mods = api.searchMod(txt);
				//DefaultListModel<Mod> lm = new DefaultListModel<Mod>();
				resultList.setListData(mods.toArray());
				revalidate();
				repaint();
			}
		}
		
		if(source == searchUserButton){
			String txt = searchField.getText();
			if(txt){
				List<User> user = api.searchUser(txt);
				//DefaultListModel<Mod> lm = new DefaultListModel<Mod>();
				resultList.setListData(user.toArray());
				revalidate();
				repaint();
			}
		}
		
	}
	
	protected void initializeModPanel(){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = 0;
		
		c.gridx = 0;
		c.gridwidth = 2;
		JLabel headerLabel = new JLabel("Mod Info");
		infoPanel.add(headerLabel, c);
		
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel nameLabel = new JLabel("Name:")
		infoPanel.add(nameLabel, c);
		
		c.gridx = 1;
		nameField = new JTextField(30);
		nameField.setEditable(false);
		infoPanel.add(nameField, c);
		
		
		c.gridy++;
		c.gridx = 0;
		JLabel authorLabel = new JLabel("Author:")
		infoPanel.add(authorLabel, c);
		
		c.gridx = 1;
		authorField = new JTextField(30);
		authorField.setEditable(false);
		infoPanel.add(authorField, c);
		
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel downloadsLabel = new JLabel("Downloads:")
		infoPanel.add(downloadsLabel, c);
		
		c.gridx = 1;
		downloadsField = new JTextField(30);
		downloadsField.setEditable(false);
		infoPanel.add(downloadsField, c);
		
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel followersLabel = new JLabel("Followers:")
		infoPanel.add(followersLabel, c);
		
		c.gridx = 1;
		followersField = new JTextField(30);
		followersField.setEditable(false);
		infoPanel.add(followersField, c);
		
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel descriptionLabel = new JLabel("Description:")
		infoPanel.add(descriptionLabel, c);
		
		c.gridx = 1;
		shortDescriptionField = new JTextField(30);
		shortDescriptionField.setEditable(false);
		infoPanel.add(shortDescriptionField, c);
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel defaultVersionLabel = new JLabel("Default Version:")
		infoPanel.add(defaultVersionLabel, c);
		
		c.gridx = 1;
		defaultVersionField = new JTextField(30);
		defaultVersionField.setEditable(false);
		infoPanel.add(defaultVersionField, c);
		
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridx = 0;
		JLabel versionLabel = new JLabel("Versions:")
		infoPanel.add(versionLabel, c);
		c.gridx = 1;
		modVersionList = new JList<ModVersion>();
		//modVersionList.setPreferredSize(new Dimension(200, 100));
		infoPanel.add(new JScrollPane(modVersionList), c);
	}
	
	protected void showMod(Mod m){
		if(m){
			nameField.setText(""+m.getName());
			authorField.setText(""+m.getAuthor());
			downloadsField.setText(""+m.getDownloads());
			followersField.setText(""+m.getFollowers());
			shortDescriptionField.setText(""+m.getShortDescription());
			defaultVersionField.setText(""+m.getDefaultVersionID());
			
			modVersionList.setListData(m.getModVersions().toArray());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()){
			def sel = resultList.getSelectedValue();
			if(sel instanceof Mod){
				showMod((Mod)sel);
			}
		}
		
	}
	
}
