package com.lab03.inClass;

//5631319321 Ramkhana  Cheursawathee
//5631354221 Saris Ophaswongse

import java.awt.BorderLayout;
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
import javax.swing.ScrollPaneConstants;

public class TwitterApp extends JFrame implements ActionListener, MouseListener {
	JPanel j;
	JLabel label;
	JTextField tf;
	JButton button;
	JTextArea txta;
	JScrollPane sp;
	JPopupMenu popup;
	String selectedTxt = "";
	TwitterConnector tc = new TwitterConnector();

	public void createAndShowGUI() {
		JFrame frame = new JFrame("TwitterDemo");
		frame.setLayout(new BorderLayout());
		j = new JPanel();
		frame.add(j, BorderLayout.PAGE_START);
		label = new JLabel("Keyword:", JLabel.LEFT);
		j.add(label);
		txta = new JTextArea();
		frame.add(txta, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(txta);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(300, 300));
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		tf = new JTextField(30);
		button = new JButton("Search");
		button.addActionListener(this);
		txta.addMouseListener(this);
		j.add(tf);
		j.add(button);
		frame.setVisible(true);
		frame.pack();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Popup search")) {
			tf.setText(selectedTxt);
		}
		String input = tf.getText();
		String results = "";
		try {
			results = tc.getResults(input);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txta.setText(results);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TwitterApp().createAndShowGUI();
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {

			selectedTxt = txta.getSelectedText();
			JMenuItem menuitem = new JMenuItem("Search for " + selectedTxt);
			menuitem.addActionListener(this);
			menuitem.setActionCommand("Popup search");
			popup = new JPopupMenu();
			popup.add(menuitem);
			popup.show(txta, e.getX(), e.getY());
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
