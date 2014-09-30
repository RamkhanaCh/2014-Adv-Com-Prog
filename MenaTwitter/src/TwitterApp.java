
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;



import javax.swing.*;



import java.awt.*;



public class TwitterApp extends JFrame implements MouseListener, ActionListener {

	JPanel keywordPanel;

	JLabel keywordLabel;

	JButton searchButton;

	JTextArea textArea, bottomText;

	JTextField textField;

	JScrollPane scrollPane;

	String str;

	String sel;

	JPopupMenu menu;

	JMenuItem item;

	static int click=0;

	

	TwitterConnector tc = new TwitterConnector();



	public TwitterApp(String name) {

		super(name);

		createKeyword();

		createText();

		createScroller();

		createStatusBar();

		this.add(keywordPanel, BorderLayout.NORTH);

		this.add(bottomText,BorderLayout.SOUTH);		

	}



	private void createStatusBar() {

		bottomText = new JTextArea();

		//bottomText.setText("status:loading...");

	}



	public void createAndShowGUI() {

		runner.start();

		showStatus.start();

		TwitterApp frame = new TwitterApp("Twitter");

		frame.setSize(700, 500);

		frame.setBackground(Color.blue);

		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}



	private void createScroller() {

		scrollPane = new JScrollPane(textArea);

		scrollPane

				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setPreferredSize(new Dimension(300, 300));

		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

	}



	// Text

	private void createText() {

		textArea = new JTextArea();

		this.add(textArea);

		textArea.addMouseListener(this);



		// textArea.setText("welcome");

	}



	// Search Tap

	private void createKeyword() {

		keywordPanel = new JPanel();

		searchButton = new JButton("Search");

		searchButton.setActionCommand("searchButton");

		keywordLabel = new JLabel("Keyword");

		textField = new JTextField();

		keywordPanel.setBackground(Color.white);

		textField.setColumns(40);

		keywordPanel.add(keywordLabel, BorderLayout.WEST);

		keywordPanel.add(textField, BorderLayout.CENTER);

		keywordPanel.add(searchButton, BorderLayout.EAST);

		searchButton.addActionListener(this);

	}



	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

		if (e.getActionCommand().equals("search")) {

			try {

				//textArea.setText(tc.getResults(textField.getText()));

				textField.setText(sel);

				click = 1;

				// System.out.print(textField.getText());

			} catch (Exception e1) {

				// TODO Auto-generated catch block

				e1.printStackTrace();

			}

		}else if(e.getActionCommand().equals("searchButton")){

			sel = textField.getText();

			click=1;	

		}

	}



	Thread runner = new Thread(new Runnable(){

		public void run() {

			try {

				while(true){

					if(click==1){

						click = 0;

						showTwitter();

					}

					Thread.sleep(100);

					//System.out.println(tweet.getStatus());

					

				}

			} catch (Exception e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}	

		}				

	});

		

	private void showTwitter() throws Exception {

		textArea.setText(tc.getResults(textField.getText()));

	}

		

	Thread showStatus = new Thread(new Runnable(){

		public void run() {

				while(true){

					try {	

						bottomText.setText(tc.getStatus());	

						System.out.println(tc.getStatus());

						Thread.sleep(100);

					} catch (Exception e) {

						e.printStackTrace();

					}						

				}

		}				

	});	



	public static void main(String[] args) {

		TwitterApp ta = new TwitterApp("Twitter");

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {

					ta.createAndShowGUI();

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		});

	}



	@Override

	public void mouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON3) {

			sel = textArea.getSelectedText();

			item = new JMenuItem("Search for " + sel);

			menu = new JPopupMenu();

			// menu.addMouseListener(this);

			item.addActionListener(this);

			item.setActionCommand("search");

			menu.add(item);

			menu.show(textArea, e.getX(), e.getY());

		}

	}



	@Override

	public void mousePressed(MouseEvent e) {

		// TODO Auto-generated method stub



	}



	@Override

	public void mouseReleased(MouseEvent e) {

		// TODO Auto-generated method stub



	}



	@Override

	public void mouseEntered(MouseEvent e) {

		// TODO Auto-generated method stub



	}



	@Override

	public void mouseExited(MouseEvent e) {

		// TODO Auto-generated method stub



	}

}