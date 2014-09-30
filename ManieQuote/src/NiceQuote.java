import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;

	import javax.imageio.ImageIO;
	import javax.swing.*;
	import javax.swing.event.DocumentEvent;
	import javax.swing.event.DocumentListener;

	public class NiceQuote extends JFrame implements ActionListener{
		int count=0;
		JPanel bigPanel, quotePanel, imagePanel;
		JLabel quoteLabel;
		JButton openButton;
		static JTextField textField;
		JTextArea textArea;
		JMenuBar menuBar;
		JMenu tap1;
		JMenuItem item1, item2, item3;
		JFileChooser fileChooser;
		Image image;
		JFrame frame;
		
		public NiceQuote(){
		frame = new JFrame("This is for quote");
		frame.setSize(700,500);
		
		bigPanel   = new JPanel();
		bigPanel.setLayout(new BorderLayout());
		quotePanel = new JPanel();
		imagePanel = new ImagePanel(); ////gr/rg/r/grg/
		imagePanel.setBackground(Color.pink);
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
	
		quoteLabel = new JLabel("Quote: ");
		
		textField = new JTextField();
		textField.setColumns(30);
		textField.setFocusable(false);
		
		textField.getDocument().addDocumentListener( new DocumentListener() {
			public void changedUpdate(DocumentEvent e){
				imagePanel.repaint();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				imagePanel.repaint();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				imagePanel.repaint();
			}
		}); 
		
		quotePanel.setBackground(Color.cyan);
		quotePanel.add(quoteLabel, BorderLayout.WEST);
		quotePanel.add(textField);
		
		bigPanel.add(quotePanel, BorderLayout.NORTH);
		bigPanel.add(imagePanel, BorderLayout.CENTER);
		
		menuBar = new JMenuBar();
		tap1 = new JMenu("File");
		
		item1 = new JMenuItem("New");
		item2 = new JMenuItem("Load background");
		item3 = new JMenuItem("Exit");
		
		menuBar.add(tap1);
		menuBar.setBackground(new Color(230,20,170));
		
		item2.addActionListener(this);
		tap1.add(item1);
		tap1.add(item2);
		tap1.addSeparator();
		tap1.add(item3);
		
		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NiceQuote.this.dispose();
				//createAndShowGUI();
				System.out.println("Clear.");
			}
		});
		
		item3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
	});
		frame.add(menuBar,BorderLayout.NORTH);
		frame.add(bigPanel,BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.validate();
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item2) {
				fileChooser = new JFileChooser();
	            int returnVal = fileChooser.showOpenDialog(NiceQuote.this);
	 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                image = new ImageIcon(file.getPath()).getImage();
	                try {
	                	
						BufferedImage img = ImageIO.read(file);
						//imageLabel.setIcon(new ImageIcon(image));
						 frame.setSize(img.getWidth()+20, img.getHeight()+100);
						 textField.setFocusable(true);
						 
						 
					} catch (IOException e1) {
	  					e1.printStackTrace();
					}
	                
	                imagePanel.repaint();
	             }
		}
	}

		
		public static void main(String[] args) {
	        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               createAndShowGUI();*/
			NiceQuote a = new NiceQuote();
	           
	      
		}
		
		class ImagePanel extends JPanel{
			public void paintComponent(Graphics g){
				count++;
				g.drawImage(image, 2,2, this);
				System.out.println("I have painted " + count +" times.");
				g.setFont((new Font("Menlo",Font.PLAIN,25)));
		       	g.drawString(textField.getText(), 100, 100);
		       
			}
		}

	}