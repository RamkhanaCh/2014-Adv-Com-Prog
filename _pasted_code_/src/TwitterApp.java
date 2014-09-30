 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	TwitterConnector twitter;
	JPanel userPanel, outputPanel, statusPanel;
	JLabel keywordLabel, statusText;
	JTextField keywordText;
	JButton searchButton;
	JScrollPane paneArea;
	JTextArea resultText;
	String keyword, result;
	static String status;
	boolean clicked = false;
	int i = 0;
	Thread searching = new Thread(new Runnable(){
		@Override
		public void run() {
			try {
				while(true){
					if(clicked){
						clicked = false;
						connectTwitter();
					}
					Thread.sleep(delay);
					//System.out.println(tweet.getStatus());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	Thread statusUpdater = new Thread(new Runnable(){
 
		@Override
		public void run() {
			while(true){
				try {	
					statusText.setText(twitter.getStatus());	
					System.out.println(twitter.getStatus());
					Thread.sleep(delay);
				} catch (Exception e) {
					e.printStackTrace();
				}						
			}
			
		}
		
	});
	
	int delay = 100;
	public TwitterApp(String title){
		super(title);
		twitter = new TwitterConnector();
		createUserPanel();
		this.add(userPanel, BorderLayout.NORTH);
		createOutputPanel();
		this.add(outputPanel, BorderLayout.CENTER);
		createStatusPanel();
		this.add(statusPanel, BorderLayout.SOUTH);
	}
	private void createStatusPanel() {
		statusPanel = new JPanel();
		statusPanel.setBackground(new Color(102,178,255,200));
		statusText = new JLabel();
		statusText.setPreferredSize(new Dimension(600,20));
		statusText.setBackground(new Color(255,255,255,10));
		statusText.setForeground(Color.WHITE);
		statusText.setFont(new Font("Menlo",Font.ITALIC,12));
		statusPanel.add(statusText);
	}
 
 
	private void createOutputPanel() {
		outputPanel = new JPanel();
		getResultText();
		paneArea = new JScrollPane(resultText);
		paneArea.setPreferredSize(new Dimension(600,300));
		paneArea.setBackground(new Color(225,255,255,60));
		outputPanel.add(paneArea, BorderLayout.CENTER);
	}
	private void createUserPanel() {
		userPanel = new JPanel();
		userPanel.setBackground(new Color(192,192,192,160));
		userPanel.setPreferredSize(new Dimension(100,35));
		keywordLabel = new JLabel("Keyword");
		keywordText = new JTextField();
		keywordText.setPreferredSize(new Dimension(500,20));
		keywordText.setBackground(Color.white);
		keywordText.setForeground(Color.black);
		keywordText.setFont(new Font("Menlo",Font.PLAIN,12));
		searchButton = new JButton("Search :)");
		searchButton.setFont(new Font("Menlo",Font.PLAIN,12));
		searchButton.setActionCommand("search");
		searchButton.addActionListener(this);
		userPanel.add(keywordLabel, BorderLayout.WEST);
		userPanel.add(keywordText, BorderLayout.CENTER);
		userPanel.add(searchButton, BorderLayout.EAST);
	}
 
	private void getResultText() {
		resultText = new JTextArea();
		resultText.setBackground(new Color(255,255,255));
		resultText.setForeground(Color.black);
		resultText.setFont(new Font("Menlo",Font.PLAIN,12));
		resultText.addMouseListener(this);
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(i == 0){
			statusUpdater.start();
			searching.start();
			i = 1;
		}
		if(e.getActionCommand().equals("search")){
			keyword = keywordText.getText();
		} else if (e.getActionCommand().equals("research")){
			keywordText.setText(keyword);
		}
		try {
			clicked = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private void connectTwitter() throws Exception {
		result = twitter.getResults(keyword);
		resultText.setText(result);
		
	}
	private static void createAndShowTwitterApp() {
		TwitterApp twitterFrame = new TwitterApp("---Twitter-V.2b---");
		
		twitterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		twitterFrame.setSize(700,400);
		twitterFrame.setBackground(new Color(102,178,225,80));
		twitterFrame.setLocationRelativeTo(null);
		twitterFrame.setVisible(true);
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowTwitterApp();
			}
		});
	}
 
 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON3){
			keyword = resultText.getSelectedText();
			JPopupMenu menu = new JPopupMenu();
			JMenuItem searchChoice = new JMenuItem("Search for " + keyword+".");
			menu.add(searchChoice);
			searchChoice.setActionCommand("research");
			searchChoice.addActionListener(this);
			menu.show(resultText,arg0.getX(),arg0.getY());
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