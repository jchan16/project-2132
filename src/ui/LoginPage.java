package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import db.LoginDB;

import javax.swing.JSpinner;

public class LoginPage implements ActionListener {

	private JFrame frame;
	private JPasswordField txt_pass;
	private JTextField txt_user;
	private JButton Btn1;
	private JSpinner spinner;
	private LoginDB db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 498, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		db = new LoginDB();
		
		JLabel Label1 = new JLabel("Username:");
		Label1.setBounds(77, 72, 76, 15);
		frame.getContentPane().add(Label1);
		
		JLabel Label2 = new JLabel("Password: ");
		Label2.setBounds(77, 109, 76, 15);
		frame.getContentPane().add(Label2);
		
		Btn1 = new JButton("Login");
		Btn1.setBounds(202, 237, 89, 23);
		frame.getContentPane().add(Btn1);
		Btn1.addActionListener(this);
		
		JLabel Label3 = new JLabel("Role:");
		Label3.setBounds(77, 149, 46, 14);
		frame.getContentPane().add(Label3);
		
		txt_pass = new JPasswordField();
		txt_pass.setBounds(163, 107, 207, 17);
		frame.getContentPane().add(txt_pass);
		txt_pass.setColumns(10);
		
		txt_user = new JTextField();
		txt_user.setBounds(163, 70, 207, 17);
		frame.getContentPane().add(txt_user);
		txt_user.setColumns(10);
		
		
		String[] roles = {"Patient","Employee"};
		SpinnerListModel roleModel = new SpinnerListModel(roles);
		spinner = new JSpinner(roleModel);
		JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
	    tf.setEditable(false);
		spinner.setBounds(163, 145, 97, 23);
		frame.getContentPane().add(spinner);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Btn1) {
			String user = txt_user.getText();
			String pass = String.valueOf(txt_pass.getPassword());
			
			switch(String.valueOf(spinner.getValue())) {
				case("Patient"): 
					try {
						int result = db.LoginPatient(user,pass);
						if(result == -1) {
							JDialog error = new JDialog(frame,"Credential Error");
							JLabel message = new JLabel("UserName or PassWord is wrong");
							error.getContentPane().add(message);
							error.setSize(150,150);
							error.setVisible(true);
						}else {
							frame.dispose();
							PatientMain pm = new PatientMain(result);
							pm.setVisible(true);
						}
					}catch(SQLException e1){
						//e1.printStackTrace();
						JDialog sqlerror = new JDialog(frame,"Database Error");
						JLabel message = new JLabel("Something went wrong in database");
						sqlerror.getContentPane().add(message);
						sqlerror.setSize(200,200);
						sqlerror.setVisible(true);
					}
					break;
				case("Employee"):
					try {
						Object[] result = db.LoginEmployee(user,pass);
						if((int)result[0] == -1) {
							JDialog error = new JDialog(frame,"Credential Error");
							JLabel message = new JLabel("UserName or PassWord is wrong");
							error.getContentPane().add(message);
							error.setSize(150,150);
							error.setVisible(true);
						}else {
							switch((String)result[1]) {
								case("D"):
									frame.dispose();
									DentistMain dm = new DentistMain();
									dm.setVisible(true);
									break;
								case("H"):
									frame.dispose();
									DentistMain dm2 = new DentistMain();
									dm2.setVisible(true);
									break;
								case("R"):
									frame.dispose();
									ReceptionistMain rm = new ReceptionistMain();
									rm.setVisible(true);
									break;
								
							}
						}
					}catch(SQLException e1){
						//e1.printStackTrace();
						JDialog sqlerror = new JDialog(frame,"Database Error");
						JLabel message = new JLabel("Something went wrong in database");
						sqlerror.getContentPane().add(message);
						sqlerror.setSize(200,200);
						sqlerror.setVisible(true);
					}
					break;
			}
			
		}
	}
}
