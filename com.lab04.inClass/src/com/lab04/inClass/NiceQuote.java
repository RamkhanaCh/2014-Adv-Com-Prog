//5631319321 Ramkhana  Cheursawathee
//5631354221 Saris Ophaswongse
package com.lab04.inClass;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class NiceQuote extends JFrame {
	JFrame frame;
	JPanel panel, imagepanel, bigpanel;
	ImagePanel imagePanel;
	JLabel label;
	JLabel quote;
	JTextField textField;
	JTextArea textArea;
	JMenuBar menubar;
	JMenu menu;
	JMenuItem menuItem1;
	JMenuItem menuItem2;
	JMenuItem menuItem3;
	JFileChooser chooser;
	File file;
	Image image;
	boolean firstTime = false;
	boolean blank = true;
	public NiceQuote() {
		frame = new JFrame("NiceQuote");
		frame.setLayout(new BorderLayout());
		createMenuBar();
		bigpanel = new JPanel(new BorderLayout());
		frame.add(bigpanel, BorderLayout.CENTER);
		panel = new JPanel();
		bigpanel.add(panel, BorderLayout.NORTH);
		label = new JLabel("Quote");
		textField = new JTextField(30);
		panel.add(label);
		panel.add(textField, BorderLayout.NORTH);
		textField.setFocusable(false);
		imageSite();
		
	}
	public void createAndShowGUI() {
		frame.setPreferredSize(new Dimension(700,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	public void createMenuBar() {
		menubar = new JMenuBar();
		frame.add(menubar, BorderLayout.PAGE_START);
		menu = new JMenu("File");
		menubar.add(menu);
		menuItem1 = new JMenuItem(new New());
		menuItem2 = new JMenuItem(new LoadBackground());
		menuItem3 = new JMenuItem(new Exit());
		menu.add(menuItem1);
		menu.add(menuItem2);
		menu.addSeparator();
		menu.add(menuItem3);
	}
	
	public void imageSite(){
		imagepanel = new JPanel();
		imagepanel.setLayout(null);
		imagepanel.setBorder(BorderFactory.createLineBorder(Color.black));
		bigpanel.add(imagepanel, BorderLayout.CENTER);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				imagePanel.repaint();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				imagePanel.repaint();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("update");
				
			}
		});
		
	}
	
	class LoadBackground extends AbstractAction {
		public LoadBackground() {
			super("Load Backgroud");
			putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
					KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Load Background image");
		}
		public void actionPerformed(ActionEvent e) {
			chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(NiceQuote.this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				try {
					image = ImageIO.read(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(firstTime){
					imagepanel.remove(imagePanel);
				}
				frame.setSize(new Dimension(image.getWidth(null)+40, image.getHeight(null)+100));
				firstTime = true;
				imagePanel = new ImagePanel(image);
				imagepanel.add(imagePanel);
				imagePanel.repaint();
				textField.setFocusable(true);
				imagePanel.setVisible(true);
				blank =false;

				}
			// This demo does nothing with returnVal
		}
	}
	class New extends AbstractAction {
		public New() {
			super("New");
			//putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
			//		KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			putValue(AbstractAction.SHORT_DESCRIPTION, "New");
		}
		public void actionPerformed(ActionEvent e) {
			if(!blank){
				imagePanel.setVisible(false);
				imagePanel.repaint();
			}
			
			textField.setFocusable(false);
			textField.setText("");
			frame.setSize(new Dimension(700,500));
			
		}
	}
	class Exit extends AbstractAction {
		public Exit() {
			super("Exit");
			//putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
			//		KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Exit");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			

			
		}
	}
	class ImagePanel extends JPanel {

		  private Image img;

		  public ImagePanel(String img) {
			  this(new ImageIcon(img).getImage());
		  }
		  public ImagePanel(Image img) {
			  
			  this.img = img;
			  Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			  setPreferredSize(size);
			  setMinimumSize(size);
			  setMaximumSize(size);
			  setSize(size);
			  setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
			  super.paintComponent(g);
			  int panelW = imagepanel.getWidth();
			  int panelH = imagepanel.getHeight();
			  int imgW = img.getWidth(null);
		      int imgH = img.getHeight(null);
			  setBounds((panelW-imgW)/2, (panelH-imgH)/2, imgW, imgH);
			  //int y = (panelH-imgH)/2;
			  g.drawImage(img, 0, 0, null);
			  g.drawString(textField.getText(), 50, 50);
		  }
		  public void setImage(Image img){
			  this.img = img;
		  }

	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NiceQuote nc = new NiceQuote();
				nc.createAndShowGUI();
			}
		});
	}
}