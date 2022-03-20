package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import db.PatientDB;
import objects.Appointment;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class PatientMain extends JFrame implements ActionListener,ListSelectionListener {
	private int patient_id;
	private JPanel contentPane;
	
	private JList list1; 
	private JList list2;
	private DefaultListModel model1;
	private DefaultListModel model2;
	private JButton Btn1;
	private JButton Btn2;
	private JButton Btn3;
	private JButton Btn4;
	private JButton Btn5;
	private JButton Btn6;
	/**
	 * Create the frame.
	 */
	public PatientMain(int id) throws SQLException{
		this.patient_id = id;
		PatientDB db = new PatientDB();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 784, 468);
		contentPane.add(tabbedPane);
		
		JPanel AppointmentPanel = new JPanel();
		tabbedPane.addTab("Appointments", null, AppointmentPanel, null);
		AppointmentPanel.setLayout(null);
		
		
		model1 = new DefaultListModel();
		updateModel1(db.queryAllAppointments(id));
		list1 = new JList(model1);
		list1.setVisibleRowCount(10);
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(list1);
		listScrollPane.setViewportView(list1);
		listScrollPane.setBounds(456, 11, 313, 418);
		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0) {
		            Object o = theList.getModel().getElementAt(index);
		            if(o!=null) {
		            	JDialog error = new JDialog();
						JTextArea text = new JTextArea(((Appointment)theList.getSelectedValue()).description());
						text.setEditable(false); // set textArea non-editable
					    JScrollPane scroll = new JScrollPane(text);
					    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						error.getContentPane().add(scroll);
						error.setSize(150,150);
						error.setVisible(true);
		            }
		          }
		        }
		      }
		    };
		    list1.addMouseListener(mouseListener);
		AppointmentPanel.add(listScrollPane);
		
		Btn1 = new JButton("Request Appointment");
		Btn1.addActionListener(this);
		Btn1.setBounds(25, 139, 216, 23);
		AppointmentPanel.add(Btn1);
		
		Btn2 = new JButton("Cancel Appointment");
		Btn2.addActionListener(this);
		Btn2.setBounds(25, 190, 216, 23);
		AppointmentPanel.add(Btn2);
		
		Btn3 = new JButton("Search Appointment");
		Btn3.addActionListener(this);
		Btn3.setBounds(25, 242, 216, 23);
		AppointmentPanel.add(Btn3);
		
		Btn4 = new JButton("Reset Table");
		Btn4.addActionListener(this);
		Btn4.setBounds(357, 191, 89, 23);
		AppointmentPanel.add(Btn4);
		
		JPanel RecordsPanel = new JPanel();
		tabbedPane.addTab("Records", null, RecordsPanel, null);
		RecordsPanel.setLayout(null);
		
		list2 = new JList();
		list2.setBounds(436, 11, 314, 418);
		RecordsPanel.add(list2);
		
		Btn5 = new JButton("Search Records");
		Btn5.addActionListener(this);
		Btn5.setBounds(126, 137, 167, 23);
		RecordsPanel.add(Btn5);
		
		Btn6 = new JButton("Search Treatments");
		Btn6.addActionListener(this);
		Btn6.setBounds(126, 210, 167, 23);
		RecordsPanel.add(Btn6);
		
		JButton Btn7 = new JButton("Reset Table");
		Btn7.setBounds(329, 173, 97, 23);
		RecordsPanel.add(Btn7);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Btn4) {
			updateModel1(new ArrayList<Appointment>());
		}
		if(e.getSource()==Btn1) {
			RequestAppointmentDialog rad;
			try {
				rad = new RequestAppointmentDialog(patient_id);
				rad.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource()==Btn3) {
			SearchAppointment sa;
			try {
				sa = new SearchAppointment(this,patient_id);
				sa.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public  void updateModel1(ArrayList<Appointment> newap) {
		if(model1.isEmpty()) {
			for(int i = 0; i < newap.size(); i ++){
				model1.addElement(newap.get(i));
			}
			return;
		}else {
			model1.removeAllElements();
			for(int i = 0; i < newap.size(); i ++){
				model1.addElement(newap.get(i));
			}
			return;
		}
	}
	
	public  void updateModel2(ArrayList<Object> recordtreat) {
		if(model2.isEmpty()) {
			for(int i = 0; i < recordtreat.size(); i ++){
				model2.addElement(recordtreat.get(i));
			}
			return;
		}else {
			model2.removeAllElements();
			for(int i = 0; i < recordtreat.size(); i ++){
				model2.addElement(recordtreat.get(i));
			}
			return;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
