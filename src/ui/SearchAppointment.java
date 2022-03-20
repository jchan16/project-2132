package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import objects.Branch;
import objects.Dentist;
import db.PatientDB;
import objects.Appointment;
import javax.swing.JCheckBox;

public class SearchAppointment extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private int patient_id;
	private JDateChooser dateChooser;
	private JCheckBox Date2;
	private JCheckBox Date3;
	private JComboBox Times;
	private JCheckBox Time1;
	private JCheckBox Time2;
	private JComboBox Branches;
	private JComboBox Dentists;
	private DefaultComboBoxModel changeden; 
	private JComboBox Statuses;
	private JComboBox Types;
	private JButton okButton;
	private JButton cancelButton;
	
	private PatientDB db;
	private PatientMain parent;

	/**
	 * Launch the application.
	 */


	
	/**
	 * Create the dialog.
	 */
	public SearchAppointment(PatientMain parent,int id) throws SQLException{
		this.setModal(true);
		db = new PatientDB();
		patient_id = id;
		this.parent = parent;
		
		setBounds(100, 100, 566, 429);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		dateChooser = new JDateChooser();
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		dateChooser.setBounds(77, 22, 138, 20);
		
		JLabel lblNewLabel = new JLabel("Date: ");
		lblNewLabel.setBounds(15, 22, 40, 14);
		
		Times = new JComboBox(createTimes());
		Times.setSelectedIndex(-1);
		Times.setBounds(77, 84, 136, 22);
		
		JLabel lblNewLabel_1 = new JLabel("Time :");
		lblNewLabel_1.setBounds(15, 88, 52, 14);
		
		JLabel lblNewLabel_2 = new JLabel("Branch: ");
		lblNewLabel_2.setBounds(15, 177, 52, 18);
		
		Branches = new JComboBox(db.queryAllBranches());
		Branches.setSelectedIndex(-1);
		Branches.addActionListener(this);
		Branches.setBounds(77, 173, 136, 22);
		
		JLabel lblNewLabel_3 = new JLabel("Dentist: ");
		lblNewLabel_3.setBounds(15, 226, 52, 18);
		
		changeden = new DefaultComboBoxModel();
		updateModel(new Dentist[] {});
		Dentists = new JComboBox(changeden);
		Dentists.setSelectedIndex(-1);
		Dentists.setBounds(77, 222, 136, 22);
		
		contentPanel.setLayout(null);
		contentPanel.add(lblNewLabel);
		contentPanel.add(dateChooser);
		contentPanel.add(lblNewLabel_1);
		contentPanel.add(Times);
		contentPanel.add(lblNewLabel_2);
		contentPanel.add(Branches);
		contentPanel.add(lblNewLabel_3);
		contentPanel.add(Dentists);
		
		JLabel lblNewLabel_4 = new JLabel("Status:");
		lblNewLabel_4.setBounds(15, 280, 52, 18);
		contentPanel.add(lblNewLabel_4);
		
		
		String[] stats = {"Confirmed","In Progress","Canceled","In Contention","Completed"};
		Statuses = new JComboBox(stats);
		Statuses.addActionListener(this);
		Statuses.setSelectedIndex(-1);
		Statuses.setBounds(77, 276, 138, 22);
		contentPanel.add(Statuses);
		
		JLabel lblNewLabel_5 = new JLabel("Type: ");
		lblNewLabel_5.setBounds(15, 317, 46, 14);
		contentPanel.add(lblNewLabel_5);
		
		String[] types = {"Diagnostic","Restorative","Endodontic","Periodontal","Protehetics(Removable)","Prothestics(Fixed)","Oral Surgery", "General Service"};
		Types = new JComboBox(types);
		Types.addActionListener(this);
		Types.setSelectedIndex(-1);
		Types.setBounds(77, 313, 138, 20);
		contentPanel.add(Types);
		
		
		Date2 = new JCheckBox("Before Date");
		Date2.setBounds(15, 43, 97, 23);
		contentPanel.add(Date2);
		
		Date3 = new JCheckBox("After Date");
		Date3.setBounds(114, 43, 97, 23);
		contentPanel.add(Date3);
		
		Time1= new JCheckBox("Before time");
		Time1.setBounds(15, 120, 97, 23);
		contentPanel.add(Time1);
		
		Time2 = new JCheckBox("After time");
		Time2.setBounds(135, 120, 97, 23);
		contentPanel.add(Time2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private Time[] createTimes() {
		ArrayList<Time> times = new ArrayList<Time>();
		for(int i = 9; i < 19; i++) {
			String hour;
			String hour2;
			
			if(i < 10) {
				hour = "0"+i+":00:00";
				hour2 = "0"+i+":30:00";
			}else {
				hour = i+":00:00";
				hour2 = i+":30:00";
			}
			
			Time flush = Time.valueOf(hour);
			Time notFlush = Time.valueOf(hour2);
			times.add(flush);
			times.add(notFlush);
		}
		Time[] timesdone = new Time[times.size()];
		timesdone = times.toArray(timesdone);
		return timesdone;
	}
	
	public  void updateModel(Dentist[] den) {
		if(changeden.getSize() == 0) {
			for(int i = 0; i < den.length; i ++){
				changeden.addElement(den[i]);
			}
			return;
		}else {
			changeden.removeAllElements();
			for(int i = 0; i < den.length; i ++){
				changeden.addElement(den[i]);
			}
			return;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == Branches) {
			try {
				Branch branch = (Branch)Branches.getSelectedItem();
				updateModel(db.getAllDentistFromBranch(branch.getBranchid()));
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == cancelButton) {
			this.dispose();
		}
		
		if(e.getSource() == okButton) {
			
			Object date = null;
			Object time= null;
			Object den_id= null;
			Object bran_id= null;
			Object stat= null;
			Object type= null;
			
			switch(checkDate()) {
				case(-1):
					break;
				default:
					Date d = new java.sql.Date(dateChooser.getDate().getTime());
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					String s=sdf.format(d);
					date = Date.valueOf(s);
					break;
			}
			
			switch(checkTime()) {
				case(-1):
					break;
				default:
					Time st = (Time)Times.getSelectedItem();
					time = st.toString();
				}
			
			switch(checkBranchDen()) {
				case(-1):
					break;
				case(1):
					Branch br = (Branch)Branches.getSelectedItem();
					bran_id = br.getBranchid();
					break;
				case(2):
					Branch br1 = (Branch)Branches.getSelectedItem();
					Dentist de = (Dentist)Dentists.getSelectedItem();
					bran_id = br1.getBranchid();
					den_id = de.getEmployee_id();
					break;
			}
			
			if(checkStat()) {
				stat = (String)Statuses.getSelectedItem();
			}
			
			if(checkType()) {
				type = (String)Types.getSelectedItem();
			}
			
			 parent.updateModel1(db.searchAppointments(patient_id,date,checkDate(),time,checkTime(),den_id,bran_id,checkBranchDen(),stat,type));
			
		}
	}
	
	private int checkDate() {
		try {
			java.util.Date d = dateChooser.getDate();
			if(d == null) {
				return -1;
			}
			boolean before = Date2.isSelected();
			boolean after = Date3.isSelected();
			
			if(before == true || after == true) { 
				if(before == true && after == true) {
					return -1;
				}
				
				if(before == true && after == false) {
					return 1; // 1 mean look on that day and before
				}
				
				if(before == false && after == true) {
					return 2; // 2 means look on that day and after
				}
			}else { // 0 means only look on that day
				return 0;
			}
		}catch(NullPointerException e) {
			return -1;
		}
		return -1;
	}
	
	private int checkTime() {
		Time t = (Time)Times.getSelectedItem();
		if(t == null) {
			return -1; // -1 means null or dont use as where clause
		}else {
			boolean before = Time1.isSelected();
			boolean after = Time2.isSelected();
			
			if(before == true || after == true) { 
				if(before == true && after == true) {
					return -1;
				}
				
				if(before == true && after == false) {
					return 1; // 1 mean look in interval and if start time< input
				}
				
				if(before == false && after == true) {
					return 2; // 1 mean look in interval and if start time > input
				}
			}else { // 0 means look in interval
				return 0;
			}
		}
		return -1; // just in case
	}
	
	private int checkBranchDen() {
		Branch br = (Branch)Branches.getSelectedItem();
		Dentist de = (Dentist)Dentists.getSelectedItem();
		
		if(br == null) { // should gurantee that dentist is also null 
			return 0; // 0 means dont use as where clause
		}else {
			if(de == null) {
				return 1; // 1 means look only for branch
			}else {
				return 2; // 2 means look for branch and employee
			}
		}
	}
	
	private boolean checkStat() {
		String stat = (String)Statuses.getSelectedItem();
		if(stat == null) {
			return false; // dont use as where clause
		}
		return true;
	}
	
	private boolean checkType() {
		String ty = (String)Types.getSelectedItem();
		if(ty == null) {
			return false; // dont use as where clause
		}
		return true;
		
	}
}
