import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.xml.catalog.CatalogException;

import net.codejava.databasemanager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.table.TableColumn;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class showResults {

	private static JFrame frame;
	private JTable subjectsTable;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					showResults window = new showResults();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public showResults() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 938, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
			
		subjectsTable = new JTable();
		
		subjectsTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		subjectsTable.setPreferredScrollableViewportSize(new Dimension(450,63));
		subjectsTable.setFillsViewportHeight(true);

		subjectsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null, null},
			},
			new String[] {
				"New column", "New column", "ID", "New column", "New column"
			}
		));
		
		subjectsTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		subjectsTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		subjectsTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		subjectsTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		subjectsTable.getColumnModel().getColumn(4).setPreferredWidth(70);
		subjectsTable.setBounds(10, 33, 602, 332);
		frame.getContentPane().add(subjectsTable);
		

		JButton refreshTableBtn = new JButton("Refresh");
		refreshTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				refreshTable();
			}
		});
  
		JButton jButton = new JButton();
		JButton jButton2 = new JButton();
		
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		refreshTableBtn.setBounds(682, 33, 115, 23);
		frame.getContentPane().add(refreshTableBtn);
		
		lblNewLabel = new JLabel("Ime kolegija");
		lblNewLabel.setBounds(10, 8, 82, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Nositelj kolegija");
		lblNewLabel_1.setBounds(126, 8, 100, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Broj ECTS bodova");
		lblNewLabel_2.setBounds(249, 8, 100, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Sati predavanja");
		lblNewLabel_3.setBounds(370, 8, 115, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				deleteSelectedSubject(subjectsTable);
			}
		});
		
		subjectsTable.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
				{
					System.out.println("Key Pressed");
					try {
						editSelectedSubject(subjectsTable);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		deleteBtn.setBounds(682, 92, 89, 23);
		frame.getContentPane().add(deleteBtn);
		
		JLabel lblNewLabel_4 = new JLabel("ID");
		lblNewLabel_4.setBounds(489, 8, 46, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					editSelectedSubject(subjectsTable);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		editButton.setBounds(682, 162, 89, 23);
		frame.getContentPane().add(editButton);
	}

	public void deleteSelectedSubject(JTable table){

		   int row = table.getSelectedRow();   
		   int idNEW = Integer.parseInt(table.getModel().getValueAt(row, 4).toString());
		      
		   deleteSelectedRow(idNEW, table);
		   refreshTable();
		}
	
	public static void showJframe() 
	{
		try {
			frame.setVisible(true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editSelectedSubject(JTable table) throws SQLException {
		 
		int column = table.getSelectedColumn();
		int row = table.getSelectedRow();
		
		String courseName = table.getModel().getValueAt(row, 0).toString();
		String profName = table.getModel().getValueAt(row, 1).toString();
		int ects = Integer.parseInt(table.getModel().getValueAt(row, 2).toString());
		int hours = Integer.parseInt(table.getModel().getValueAt(row, 3).toString());
		int id = Integer.parseInt(table.getModel().getValueAt(row, 4).toString());
		
		
		databasemanager.updateTable(id, courseName, profName, ects, hours);
		//refreshTable();
		
		System.out.print(" course name: " + courseName +"\n");
		System.out.print(" prof name: " + profName +"\n");
		System.out.print(" ects " + ects +"\n");
		System.out.print(" hours " + hours +"\n");
		System.out.print(" id " + id +"\n");
	}

	private void refreshTable() {
		try {
			Class.forName("org.sqlite.JDBC");
			
			String jdbcUrl = "jdbc:sqlite:/F:\\Java\\SQLite\\sqlite-tools-win32-x86-3380100\\subjects.db";
			
			//open connection
			Connection conn = DriverManager.getConnection(jdbcUrl);
			
			Statement statement = conn.createStatement();

			String sql = "SELECT * FROM subjects ";
			ResultSet rs = statement.executeQuery(sql);
			
			DefaultTableModel tableModel = (DefaultTableModel)subjectsTable.getModel();
			int rowCount = tableModel.getRowCount();
			for(int i = rowCount -1; i>= 0; i--) {
				tableModel.removeRow(i);
			}

			while(rs.next()) {
				//Read data until finish
				String subjectName = rs.getString("subjectName");
				String profName = rs.getString("profName");
				String ECTS = String.valueOf(rs.getInt("ECTS"));
				String hours = String.valueOf(rs.getInt("hours"));
				String id = String.valueOf(rs.getInt("id"));
				String tbData[] = {subjectName, profName, ECTS, hours, id};
				tableModel.addRow(tbData);
			}
			
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}	
	}
	
	private void deleteSelectedRow(int id, JTable table) {
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		   int[] rows = table.getSelectedRows();
		   
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }		   
		   try {

				String jdbcUrl = "jdbc:sqlite:/F:\\Java\\SQLite\\sqlite-tools-win32-x86-3380100\\subjects.db";
				Connection conn = DriverManager.getConnection(jdbcUrl);
				Statement stmt = null;
				
				try {
					Class.forName("org.sqlite.JDBC");
					stmt = conn.createStatement();

					String sql = "DELETE FROM subjects WHERE id = " + id;
					stmt.execute(sql);
					//System.out.print(index);
					
				} catch (Exception e) {
					// TODO: handle exception
				}	
			
		} catch (Exception e) {
			// TODO: handle exception
		}  
	}
}
