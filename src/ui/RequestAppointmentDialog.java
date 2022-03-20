package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import db.PatientDB;
import objects.Appointment;
import objects.Branch;
import objects.Dentist;

public class RequestAppointmentDialog extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JLabel DateLabel;
	private JComboBox Start;
	private JComboBox End;
	private JComboBox Branch;
	private JComboBox Dentist;
	private JComboBox Types;
	private PatientDB db;
	private DefaultComboBoxModel changeden; 
	private JDateChooser dateChooser;
	private JButton okButton;
	private JButton cancelButton;
	
	private int patid;

	/**
	 * Create the dialog.
	 */
	public RequestAppointmentDialog(int id) throws SQLException {
		patid = id;
		this.setModal(true);
		setBounds(100, 100, 521, 381);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		db = new PatientDB();
		
		DateLabel = new JLabel("Date: ");
		
		JLabel StartLabel = new JLabel("Start Time: ");
		
		DefaultComboBoxModel<Time> comboModel = new DefaultComboBoxModel<Time>(createTimes());
		Start = new JComboBox(comboModel);
		Start.setSelectedIndex(-1);
		Start.addActionListener(this);
		
		JLabel EndTimeLable = new JLabel("End Time:");
		
		End = new JComboBox(comboModel);
		End.setSelectedIndex(-1);
		End.addActionListener(this);
		
		dateChooser = new JDateChooser();
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		
		JLabel lblNewLabel = new JLabel("Branch:");
		
		JLabel lblNewLabel_1 = new JLabel("Dentist: ");
		
		JLabel lblNewLabel_2 = new JLabel("Appointment Type: ");
		
		Branch = new JComboBox(db.queryAllBranches());
		Branch.setSelectedIndex(-1);
		Branch.addActionListener(this);
		
		changeden = new DefaultComboBoxModel();
		updateModel(new Dentist[] {});
		Dentist = new JComboBox(changeden);
		Dentist.setSelectedIndex(-1);

		
		String[] types = {"Diagnostic","Restorative","Endodontic","Periodontal","Protehetics(Removable)","Prothestics(Fixed)","Oral Surgery", "General Service"};
		Types = new JComboBox(types);
		Types.addActionListener(this);
		Types.setSelectedIndex(-1);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(DateLabel)
						.addComponent(StartLabel)
						.addComponent(EndTimeLable)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2))
					.addGap(77)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Types, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Dentist, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Branch, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(End, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Start, 0, 112, Short.MAX_VALUE))
					.addContainerGap(196, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(DateLabel)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(Start, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(StartLabel))
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(EndTimeLable)
						.addComponent(End, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(Branch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(Dentist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(Types, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == Branch) {
			try {
				Branch branch = (Branch)Branch.getSelectedItem();
				updateModel(db.getAllDentistFromBranch(branch.getBranchid()));
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
		if(e.getSource() == okButton) {
				try {
					
					Date d = new java.sql.Date(dateChooser.getDate().getTime());
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					String s=sdf.format(d);
					d = Date.valueOf(s);
					
					Time st = (Time)Start.getSelectedItem();
					Time ed = (Time)End.getSelectedItem();
					Branch br = (Branch)Branch.getSelectedItem();
					Dentist de = (Dentist)Dentist.getSelectedItem();
					String ty = (String)Types.getSelectedItem();
					
					boolean check1 = checker(d,st,ed,br,de,ty);
					if(check1) {
						boolean status = db.insertRequest(patid, d, st, ed, de.getEmployee_id(), br.getBranchid(), "In Progress", ty);
						if(status) {
							JDialog error1 = new JDialog(this,"Insertion Successful");
							JLabel message = new JLabel("Appointment has been requested");
							error1.getContentPane().add(message);
							error1.setModal(true);
							error1.setSize(200,200);
							error1.setVisible(true);
							this.dispose();
						}else {
							JDialog error1 = new JDialog(this,"Input Error");
							JLabel message = new JLabel("Appointment Request already exists or another error has occured");
							error1.getContentPane().add(message);
							error1.setModal(true);
							error1.setSize(200,200);
							error1.setVisible(true);
						}
					}else {
						JDialog error1 = new JDialog(this,"Input Error");
						JLabel message = new JLabel("Some values may be incorrect");
						error1.getContentPane().add(message);
						error1.setModal(true);
						error1.setSize(200,200);
						error1.setVisible(true);
					}
				}catch (NullPointerException n) {
					JDialog error1 = new JDialog(this,"Input Error");
					JLabel message = new JLabel("Some values may be incorrect or null");
					error1.getContentPane().add(message);
					error1.setModal(true);
					error1.setSize(200,200);
					error1.setVisible(true);
				}
				
			}
		
		
		if(e.getSource() == cancelButton) {
			this.dispose();
		}
	}
		
	
	private boolean checker(Date d, Time s, Time e, Branch b, Dentist de, String ty) {
		if(d != null && s != null && e != null && b != null && de != null && ty != null) {
			if(s.compareTo(e) < 0) {
				return true;
			}
		}
		return false;
	}
}

