import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.codejava.databasemanager;

import javax.swing.JComboBox;


public class mainWindow {

	private static JFrame mainFrame;
	public static JTextField subjectNameTextfield;
	public static JTextField profesorNameTextfield;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.mainFrame.setVisible(true);
					
					showResults showResults = new showResults();
					
					//System.out.println("aaa: " + databasemanager.readFromDb());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	public mainWindow() {
		initialize();
	}

	public static void showMainWindow() {
		mainFrame.setVisible(true);
	}
	
	
	JComboBox ectsNumbersComboBox;
	JComboBox hoursComboBox;
	
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 450, 552);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JButton showSubjects = new JButton("Prikaži kolegije");
		showSubjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showResults.showJframe();
			}
		});
		
		
		showSubjects.setBounds(281, 479, 143, 23);
		mainFrame.getContentPane().add(showSubjects);
		
		JButton createNewSubject = new JButton("Kreiraj novi kolegij");
		createNewSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				createNewSubject();
				
			}
		});
		
		
		createNewSubject.setBounds(24, 479, 169, 23);
		mainFrame.getContentPane().add(createNewSubject);
		
		JLabel subjectNameLabel = new JLabel("Ime kolegija:");
		subjectNameLabel.setBounds(10, 57, 89, 14);
		mainFrame.getContentPane().add(subjectNameLabel);
		
		JLabel lecturerLabel = new JLabel("Predava\u010D:");
		lecturerLabel.setBounds(10, 32, 67, 14);
		mainFrame.getContentPane().add(lecturerLabel);
		
		JLabel ectsNumberLabel = new JLabel("Broj ECTS-a:");
		ectsNumberLabel.setBounds(10, 82, 73, 14);
		mainFrame.getContentPane().add(ectsNumberLabel);
		
		JLabel hoursLabel = new JLabel("Sati predavanja:");
		hoursLabel.setBounds(10, 107, 89, 14);
		mainFrame.getContentPane().add(hoursLabel);
		
		subjectNameTextfield = new JTextField();
		subjectNameTextfield.setBounds(108, 29, 316, 20);
		mainFrame.getContentPane().add(subjectNameTextfield);
		subjectNameTextfield.setColumns(10);
		
		profesorNameTextfield = new JTextField();
		profesorNameTextfield.setBounds(108, 54, 316, 20);
		mainFrame.getContentPane().add(profesorNameTextfield);
		profesorNameTextfield.setColumns(10);
		
		
		//comboBox ECTS points
		Integer[] listOfEctsPoints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		ectsNumbersComboBox = new JComboBox(listOfEctsPoints);
		ectsNumbersComboBox.setBounds(108, 78, 57, 22);
		mainFrame.getContentPane().add(ectsNumbersComboBox);
		
		//comboBox hours
		Integer[] listOfHours = {15,20,25,30,35,40,45,50,55,60,65,70,75,80,85};
		hoursComboBox = new JComboBox(listOfHours);
		hoursComboBox.setBounds(109, 103, 56, 22);
		mainFrame.getContentPane().add(hoursComboBox);
		
		//open subject window
		showSubjects.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				//showSubjects subjects = new showSubjects();
				//subjects.showSubjects();
				mainFrame.setVisible(false);
				//showResults().setVisible(true);
				
			}
		});
	}
	
	private void createNewSubject() {
		try {
			
			String profName = profesorNameTextfield.getText();
			String subjectName = subjectNameTextfield.getText();
			int id = 0;
			
			if (profName.isEmpty() && subjectName.isEmpty()) {
				System.out.println("course name is empty");
				JOptionPane.showMessageDialog(mainFrame, "Sva polja moraju biti ispunjena!");
				return;
			}
			if (profName.isEmpty()) {
				System.out.println("course name is empty");
				JOptionPane.showMessageDialog(mainFrame, "Potrebno je upisati ime kolegija!");
				return;
			}
			if (subjectName.isEmpty()) {
				System.out.println("prof name is empty");
				JOptionPane.showMessageDialog(mainFrame, "Potrebno je upisati ime predavaca!");
				return;
			}
			
			if(subjectName.matches("^[0-9]*$") && subjectName.length() > 0){
				JOptionPane.showMessageDialog(mainFrame, "Ime kolegija ne smije sadrzavati samo brojeve!");
				return;
			}
			
			if(profName.matches("^[0-9]*$") && profName.length() > 0){
				JOptionPane.showMessageDialog(mainFrame, "Ime profesora ne smije sadrzavati samo brojeve!");
				return;
			}
			
			if (profName.matches("^[0-9]*$") && profName.length() > 0 && subjectName.matches("^[0-9]*$") && subjectName.length() > 0) {
				JOptionPane.showMessageDialog(mainFrame, "Neispravan unos!");
				return;
			}
			
			
			int selectedIndex = ectsNumbersComboBox.getSelectedIndex();
			int ects = Integer.valueOf(ectsNumbersComboBox.getItemAt(selectedIndex).toString());
			
			int selectedHours = hoursComboBox.getSelectedIndex();
			int hours =Integer.valueOf(hoursComboBox.getItemAt(selectedHours).toString());
			
			boolean exists = databasemanager.checkIfExist(subjectName); 
			
			if(exists) {
				System.out.print(" postoji ");
				JOptionPane.showMessageDialog(mainFrame, "Kolegij je vec upisan u bazu!");
				return;
				
			}else {
				System.out.print(" kolegij ne postoji u bazi");
			}
			
			databasemanager.importSubject(id++,profName , subjectName, ects, hours);

			JOptionPane.showMessageDialog(mainFrame, 
					"Kolegij uspješno upisan!"
							+ "\n" 
							+ "\n" 
							+ "Naziv kolegija: "  + subjectName + "\n" 
							+ "Nositelj kolegija: "  + profName + "\n" 		
							+ "Broj ECTS-a: "  + ects + "\n" 		
							+ "Sati predavanja: "  + hours 
					);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
}
