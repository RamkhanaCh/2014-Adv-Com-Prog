
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
 
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
 
public class DisplayMap extends JFrame {
	// width and height of the main window
	private final static int MAINWINDOW_WIDTH = 650;
	private final static int MAINWINDOW_HEIGHT = 600;
 
	// width and height of any dialog window to be created
	private final static int DIALOG_WIDTH = 500;
	private final static int DIALOG_HEIGHT = 135;
 
	// default width of any created text field
	private final static int TEXTFIELD_WIDTH = 25;
 
	private Map<Integer, String> m;
 
	// top panel
	private JPanel topPanel;
	private JButton addButton;
	private JTextField searchField;
 
	// center panel
	private JPanel centerPanel;
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel originalTableModel;  // you must read TableModel and AbstractTableModel
	private TableModel tableModel;
 
	// add new data -> dialog window
	private JFrame addWindow;
	private JPanel topAddWindow, bottomAddWindow;
	private JTextField idFill, nameFill;
	private JButton okAdd, cancelAdd;
 
	private Container cp;
 
	public DisplayMap() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
 
		m = new TreeMap<Integer, String>();
		m.put(01, "James");
		m.put(4, "John");
		m.put(5, "Joot");
		m.put(7, "Jeep");
		cp = getContentPane();
		
		// create and add top panel
		topPanel = new JPanel();
		addButton = new JButton("Add Student");
		searchField = new JTextField();
		searchField.setColumns(30);
		topPanel.add(addButton);
		topPanel.add(new JLabel("    "));
		topPanel.add(searchField);
		cp.add(topPanel, BorderLayout.NORTH);
		
		JButton removeButton = new JButton("Remove selected rows");
		topPanel.add(removeButton);
		// create and add center panel
		centerPanel = new JPanel();
 
		tableModel = new AbstractTableModel() {
			private String[] columnNames = { "Student ID", "Student Name" };
 
			public String getColumnName(int index) {
				return columnNames[index];
			}
			
			// Return a value at the specified position in the table.
			// Student must navigate the map, which is a source data, and return
			// a correct result.
			// For student to do!
			@Override
			public Object getValueAt(int row, int column) {
				Object[][] foundvalue = new Object[m.size()][2];
				int i = 0;
				for(int key : m.keySet()){
					foundvalue[i][0] = key;
					foundvalue[i][1] = m.get(key);
					i++;
				}
				//System.out.println(foundvalue[0]);
				return foundvalue[row][column];
			}
 
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return m.size();
			}
 
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 2;
			}
			public boolean isCellEditable(int row, int column) {
			    return true;
			}		
		    public void setValueAt(Object value, int row, int col) {
		    	if(col==1){
			    	m.put((Integer) tableModel.getValueAt(row, 0), (String)value);
			        fireTableCellUpdated(row, col);
		    	}
		    	else {
		    		String StudentName = m.get(tableModel.getValueAt(row, 0));
			    	m.remove(tableModel.getValueAt(row, 0));
			    	m.put(Integer.parseInt((String) value),StudentName);
			    	((AbstractTableModel) tableModel).fireTableDataChanged();
		    	}
		    }			
		};
 
		
		table = new JTable(tableModel);
		// table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane);
		cp.add(centerPanel, BorderLayout.CENTER);
 
		// Create window for adding new data - This is our own dialog box.
		addWindow = new JFrame("Add new data, duplicates are overwritten.");
		addWindow.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		addWindow.setResizable(false);
		topAddWindow = new JPanel();
		topAddWindow.setLayout(new GridLayout(1, 2));
		idFill = new JTextField();
		idFill.setColumns(TEXTFIELD_WIDTH);
		nameFill = new JTextField();
		nameFill.setColumns(TEXTFIELD_WIDTH);
		topAddWindow.add(idFill);
		topAddWindow.add(nameFill);
		bottomAddWindow = new JPanel();
		bottomAddWindow.setLayout(new GridLayout(1, 6));
		okAdd = new JButton("Add");
		cancelAdd = new JButton("Cancel");
		bottomAddWindow.add(new JLabel());
		bottomAddWindow.add(okAdd);
		bottomAddWindow.add(new JLabel());
		bottomAddWindow.add(new JLabel());
		bottomAddWindow.add(cancelAdd);
		bottomAddWindow.add(new JLabel());
		addWindow.setLayout(new GridLayout(3, 1));
		addWindow.add(topAddWindow);
		addWindow.add(new JLabel());
		addWindow.add(bottomAddWindow);
		addWindow.setVisible(false);
 
		// behavior when the "Add Student" button is clicked
		addButton.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int topLeftX = getX();
				int topLeftY = getY();
				int xDialogPosition = topLeftX
						+ (MAINWINDOW_WIDTH - DIALOG_WIDTH) / 2;
				int yDialogPosition = topLeftY
						+ (MAINWINDOW_HEIGHT - DIALOG_HEIGHT) / 2;
 
				addWindow.setLocation(xDialogPosition, yDialogPosition);
				addWindow.setVisible(true);
 
			}
		});
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		        int[] selectedRow = table.getSelectedRows();
		        int[] selectedkey = new int[selectedRow.length];
				for (int i = 0; i < selectedRow.length; i++) {
					selectedkey[i] = (int) tableModel.getValueAt(selectedRow[i], 0);
					m.remove(tableModel.getValueAt(selectedRow[i], 0)); 
		        }
				for (int i = 0; i < selectedkey.length; i++) {
					m.remove(selectedkey[i]);
					//System.out.println("remove i: "+i+" key: "+selectedkey[i]);
		        }				
				((AbstractTableModel) tableModel).fireTableDataChanged();
			}
		});
		  
		// behavior when the "Add" button in the dialog is clicked.
		// It won't do anything unless both text fields in the dialog box are
		// filled in. More details are in the lab sheet.
		// For student to do!
		okAdd.addActionListener(new ActionListener() {
 
			@Override
			public void actionPerformed(ActionEvent e) {
 
				if(idFill.getText().isEmpty() || nameFill.getText().isEmpty()){
					//System.out.println("Please fill the forms");
				}
				else {
					int id = Integer.parseInt(idFill.getText());
					String name = nameFill.getText();
					m.put(id, name);
					((AbstractTableModel) tableModel).fireTableDataChanged();
					idFill.setText("");
					nameFill.setText("");
					addWindow.setVisible(false);					
				}				
			}
		});
 
		// behavior when the "Cancel" button in the dialog is clicked.
		cancelAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idFill.setText("");
				nameFill.setText("");
				addWindow.setVisible(false);
			}
		});
 
		// Behavior when search (pressing Enter in the search field on top of
		// screen)
		// *If "" is typed in, it should not do anything.
		// *If an integer is in the search field:
		// - If the integer is one of the keys in the map, open a dialog window showing the value associated with that key.
		// - If the integer is not one of the keys, open a dialog window showing that there is no matching student name.
		// *Otherwise, open a dialog window showing that the input is not a student ID.
		// For student to do!
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!searchField.getText().isEmpty()){
					if(searchField.getText().matches("-?\\d+(.\\d+)?")  ){
						if(m.get(Integer.parseInt(searchField.getText())) != null)
					JOptionPane.showMessageDialog(centerPanel,
						    "The student's name is "+m.get(Integer.parseInt(searchField.getText())));
						else JOptionPane.showMessageDialog(centerPanel,
						    "There is no matching student name.");
					}
					else  JOptionPane.showMessageDialog(centerPanel,
						    "The input is not a student ID.");
				}
			}
		});
 
		/* ListSelectionModel cellSelectionModel = table.getSelectionModel();
		    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
 
		    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  if (!e.getValueIsAdjusting()) {
				        int[] selectedRow = table.getSelectedRows();
				        int[] selectedColumns = table.getSelectedColumns();
				        int[] selectedkey = new int[selectedRow.length];
				        String selectedData = null;
						for (int i = 0; i < selectedRow.length; i++) {
				         //for (int j = 0; j < selectedColumns.length; j++) {
				            selectedData = (String) table.getValueAt(0, 0);
				          //}
				        }
				        System.out.println("Selected: " + selectedData);
		    	  }
		      }
 
		    });*/
		    
		setSize(MAINWINDOW_WIDTH, MAINWINDOW_HEIGHT);
		setVisible(true);
	}
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		setDefaultLookAndFeelDecorated(false);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DisplayMap app = new DisplayMap();
			}
		});
 
	}
 
}