//5631319321 Ramkhana Cheursawathee
//I run this on JavaSE-1.6

package com.lab03.HW;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 
public class TwitterApp extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	TwitterConnector tc = new TwitterConnector();
	JPanel panel, output, statusPanel;
	JLabel label, status;
	JTextField tf;
	JButton searchButton;
	JScrollPane scroll;
	JTextArea txta;
	String input, result;
	boolean searchClicked = false;
	int count = 0;
	int delay = 100;
	Thread searching = new Thread(new Runnable(){
		@Override
		public void run() {
			try {
				while(true){
					if(searchClicked){
						searchClicked = false;
						connectTwitter();
					}
					Thread.sleep(delay);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	Thread updater = new Thread(new Runnable(){
 
		@Override
		public void run() {
			while(true){
				try {	
					status.setText(tc.getStatus());	
					System.out.println(tc.getStatus());
					Thread.sleep(delay);
				} catch (Exception e) {
					e.printStackTrace();
				}						
			}
			
		}
		
	});
	
	public TwitterApp(String title){
		super(title);
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(100,35));
		label = new JLabel("Keyword");
		tf = new JTextField();
		tf.setPreferredSize(new Dimension(500,20));
		searchButton = new JButton("Search");
		searchButton.setActionCommand("search");
		searchButton.addActionListener(this);
		panel.add(label, BorderLayout.WEST);
		panel.add(tf, BorderLayout.CENTER);
		panel.add(searchButton, BorderLayout.EAST);
		this.add(panel, BorderLayout.NORTH);
		output = new JPanel();
		txta = new JTextArea();
		txta.addMouseListener(this);
		scroll = new JScrollPane(txta);
		scroll.setPreferredSize(new Dimension(700,300));
		output.add(scroll, BorderLayout.CENTER);
		this.add(output, BorderLayout.CENTER);
		createStatusPanel();
		this.add(statusPanel, BorderLayout.SOUTH);
	}
	
	private void connectTwitter() throws Exception {
		result = tc.getResults(input);
		txta.setText(result);
		
	}
	private void createStatusPanel() {
		statusPanel = new JPanel();
		status = new JLabel();
		status.setPreferredSize(new Dimension(600,20));
		status.setForeground(Color.blue);
		statusPanel.add(status);
	}
 
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(count == 0){
			updater.start();
			searching.start();
			count = 1;
		}
		if(e.getActionCommand().equals("search")){
			input = tf.getText();
		} else if (e.getActionCommand().equals("search popup")){
			tf.setText(input);
		}
		try {
			searchClicked = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void createAndShowGUI() {
		TwitterApp frame = new TwitterApp("TwitterDemo");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
 
 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON3){
			input = txta.getSelectedText();
			JPopupMenu menu = new JPopupMenu();
			JMenuItem searchItem = new JMenuItem("Search for " + input);
			menu.add(searchItem);
			searchItem.setActionCommand("search popup");
			searchItem.addActionListener(this);
			menu.show(txta,arg0.getX(),arg0.getY());
		}
	}
	
 
 
 
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
 
 
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
 
 
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
 
 
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
 
 
 
}