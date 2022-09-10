import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class loginWindow {

	private JFrame frame;
	private JTextField studentUsernameLabel;
	private JTextField profUsernameTextField;
	private JPasswordField passProfTextField;
	private JPasswordField passStudentTextField;

	static JLabel errorLabel;
	
	/**
	 * Launch the application.
	 */
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow window = new loginWindow();
					window.frame.setVisible(true);
					
					showResults showResults = new showResults();
					mainWindow mainWindow = new mainWindow();
					//readFromDb();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//connection to database
		protected static Connection dbConnection()
		{
			try {
				Class.forName("org.sqlite.JDBC");
				
				String jdbcUrl = "jdbc:sqlite:/F:\\Java\\SQLite\\sqlite-tools-win32-x86-3380100\\Students.db";
				
				Connection connection = DriverManager.getConnection(jdbcUrl);
				
				System.out.println("Connection succes");
				return connection;
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		protected static Connection professorDbConnection() 
		{
			try {
				Class.forName("org.sqlite.JDBC");
				
				String jdbcUrl = "jdbc:sqlite:/F:\\Java\\SQLite\\sqlite-tools-win32-x86-3380100\\Professors.db";
				Connection connection = DriverManager.getConnection(jdbcUrl);

				System.out.println("Connection succes");
				return connection;
				
			}	catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		//read users from database
		public static String readFromDb() throws SQLException{
			
			
			Connection conn = dbConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT pass, user FROM users");
				
			String result = results.getString(1);
			
			conn.close();
			
			//System.out.print(" database content: " + result);  
			return result;
			
		}	
		
		
		public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
		
		public static void errorMessage()
		{
			JLabel wrongPassLabel = new JLabel();
			wrongPassLabel.setText("aaaaaaaaaa");
			wrongPassLabel.setHorizontalAlignment(JLabel.CENTER);
		}
		
		
	/**
	 * Create the application.
	 */
	public loginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 11, 203, 239);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton profLoginBtn = new JButton("Professor login");
		
		
		profLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selectUsernameSQL = "SELECT User, Pass FROM Professors";
				
				try {
					
					Connection conn = professorDbConnection();
					Statement statement = conn.createStatement();
					ResultSet results = statement.executeQuery(selectUsernameSQL);
					
					String usernameInput = profUsernameTextField.getText();
					String usernamePass = passProfTextField.getText();
					
					boolean isMatch = false;
					
					while (results.next()) {
						
						String username = results.getString(1);
						String pass = results.getString(2);
						
						boolean usernameCheck = usernameInput.equals(username);
						boolean passwordCheck = usernamePass.equals(pass);
						
						if ( usernameCheck && passwordCheck) {
							isMatch = true;
							break;
						}else {
							System.out.println("Wrong passowrd");
							infoBox("Wrong password for professor login! Try again", "Error!");
							
						}
					}
					
					if(isMatch) {
						System.out.println("Correct pass ayyy");
						
						mainWindow.showMainWindow();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				
				
			}
		});
		
		JButton studentLoginBtn = new JButton("Student login");
		studentLoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					//String sql = "SELECT * FROM Students WHERE StudentName LIKE ? AND Pass LIKE ? ";
					String selectUsernameSQL = "SELECT StudentName, Pass FROM Students";
					Connection conn = dbConnection();
					Statement statement = conn.createStatement();
					ResultSet results = statement.executeQuery(selectUsernameSQL);
					
					String usernameInput = studentUsernameLabel.getText(); 
					String usernamePass = passStudentTextField.getText();
					
					boolean isMatch = false;
					while (results.next()) {
						
						String username = results.getString(1);
						String pass = results.getString(2);
						
						boolean usernameCheck = usernameInput.equals(username);
						boolean passwordCheck = usernamePass.equals(pass);
						
						if ( usernameCheck && passwordCheck) {
							isMatch = true;
							break;
						}else {
							System.out.println("Wrong passowrd");
							infoBox("Wrong password for student login! Try again", "Error!");
						}
					}
					
					if(isMatch) {
						System.out.println("Correct pass ayyy");
						showResults.showJframe();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		
		profLoginBtn.setBounds(10, 205, 183, 23);
		panel.add(profLoginBtn);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(0, 55, 102, 20);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(0, 86, 92, 20);
		panel.add(lblNewLabel_1);
		
		profUsernameTextField = new JTextField();
		profUsernameTextField.setBounds(68, 55, 115, 20);
		panel.add(profUsernameTextField);
		profUsernameTextField.setColumns(10);
		
		passProfTextField = new JPasswordField();
		passProfTextField.setBounds(68, 86, 115, 20);
		panel.add(passProfTextField);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(223, 11, 203, 239);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		studentLoginBtn.setBounds(10, 205, 181, 23);
		studentLoginBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(studentLoginBtn);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setBounds(0, 56, 66, 14);
		panel_1.add(lblNewLabel_2);
		
		studentUsernameLabel = new JTextField();
		studentUsernameLabel.setBounds(76, 53, 115, 20);
		panel_1.add(studentUsernameLabel);
		studentUsernameLabel.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setBounds(0, 92, 66, 14);
		panel_1.add(lblNewLabel_3);
		
		passStudentTextField = new JPasswordField();
		passStudentTextField.setBounds(76, 89, 115, 20);
		panel_1.add(passStudentTextField);
	}
}
