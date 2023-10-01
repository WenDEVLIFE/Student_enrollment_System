package enrollment;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;

import Profile.profiles;
import Studentenrollment.ColorPaletteCellRenderer;
import Studentenrollment.mainpage;
import Studentenrollment.navbar;
import User.user_access;
import admin_Registration.GRADIENTCOLORS;
import login_form.Student_enrollment_form;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import javax.swing.border.CompoundBorder;

public class enroll extends JFrame {
	

	/**
	 * 
	 */
	static JComboBox<Object> comboBox, comboBox_1;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JLabel time;
	private static String username;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	static  String n;
	private JTextField textField_3;
	static  DefaultTableModel model;
	 private JPanel panel_3_1_1_1;
	// url
	private String mydb_url = "jdbc:mysql://localhost:3306/schoolsystem";
	
	@SuppressWarnings("unused")
	//database username
	private String myDB_username = "root";
	@SuppressWarnings("unused")
	
	//database password
	private String myDB_PASSWORD = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// the code for FLATLAF
	    try {
	        FlatArcDarkIJTheme.setup();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enroll framee = new enroll(username);
					framee.setVisible(true);
					framee.setLocationRelativeTo(null);
					framee.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	public void InsertToDataBase() throws SQLException, HeadlessException, ClassNotFoundException {
	    enroll framee = this;
	    //Textfields
	   n = textField.getText();
	    String address = textField_1.getText();
	    String phonenumber = textField_2.getText();
	    // arrays or Jcombo list
	    Object selectedItem = comboBox.getSelectedItem();
	    String selectedItemInt = (String) selectedItem;
	    Object selectedItem2 = comboBox_1.getSelectedItem();
	    String selectedItemString = (String) selectedItem2;

	    if (n.isEmpty() || address.isEmpty() || phonenumber.isEmpty() || selectedItemInt.equals("Select a number") || selectedItemString.equals("Select a section")) {
	        JOptionPane.showMessageDialog(framee, "Please enter a value in the text field.");
	    } else {
	        // Check if the username already exists in the database.
	        if (doesUsernameExist(n)) {
	            JOptionPane.showMessageDialog(null, "Username already exists.");
	        } else {
	            // Replace this with code to retrieve the username of the logged-in user
	            String username1 = getCurrentLoggedInUsername(); // Example function to get the username

	            // Check if the retrieved username is not null or empty
	            if (username1 != null && !username1.isEmpty()) {
	                try {
	                    // Establish a connection to the database.
	                    Connection connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);

	                    // Create a statement to retrieve the maximum student ID.
	                    Statement statement = connection.createStatement();
	                    ResultSet resultSet = statement.executeQuery("SELECT MAX(studentid) FROM student");

	                    // to organize the id
	                    int highestId = 0;

	                    // Check if there is a result.
	                    if (resultSet.next()) {
	                    	
	                        highestId = resultSet.getInt(1);
	                    }

	                    // Calculate the new ID.
	                    int newId = highestId + 1;

	                    // Create a prepared statement to insert the new student into the database.
	                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student (studentid, studentname, Grade, age, phonenumber, address) VALUES (?, ?, ?, ?, ?, ?)");

	                    // Set the values of the parameters in the prepared statement.
	                    preparedStatement.setInt(1, newId);
	                    preparedStatement.setString(2, n);
	                    preparedStatement.setString(3, selectedItemString);
	                    preparedStatement.setString(4, selectedItemInt);
	                    preparedStatement.setString(5, phonenumber);
	                    preparedStatement.setString(6, address);

	                    // Execute the prepared statement.
	                    preparedStatement.executeUpdate();

	                    // Create a prepared statement to insert the activity into the reports table.
	                    PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO reports (username, activity, date) VALUES (?, ?, ?)");

	                    // Set the values of the parameters in the prepared statement.
	                    preparedStatement1.setString(1, username1);
	                    preparedStatement1.setString(2, "Enrolled a student");
	                    preparedStatement1.setDate(3, new java.sql.Date(System.currentTimeMillis()));

	                    // Execute the prepared statement.
	                    preparedStatement1.executeUpdate();

	                    // Close the prepared statements, statement, and the connection to the database.
	                    preparedStatement.close();
	                    preparedStatement1.close();
	                    statement.close();
	                    connection.close();

	                    // Display a success message to the user.
	                    ImageIcon imageIcon12 = new ImageIcon("pic//icons8-add-user-48.png");
	                    JOptionPane.showMessageDialog(framee, "The student has been enrolled successfully." , "Message", JOptionPane.QUESTION_MESSAGE,imageIcon12);
	                    textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						comboBox.setSelectedItem("Select a number");
						comboBox_1.setSelectedItem("Select a section");
						framee.dispose();
						framee = new enroll(username);
						framee.setVisible(true);
						framee.setLocationRelativeTo(null);
					
	                } catch (SQLException e) {
	                    // Handle any SQLException that may occur.
	                    e.printStackTrace();
	                }
	            } else {
	                // Handle the case where the username is null or empty.
	                JOptionPane.showMessageDialog(null, "Username is missing or empty.");
	            }
	        }
	    }
	}
	private String getCurrentLoggedInUsername() {
	    // Implement logic to retrieve the current logged-in username
	    // Replace the following line with the actual code to get the username
	    String username1 = username; // Replace with your logic

	    return username1;
	}

	public boolean doesUsernameExist(String n) throws SQLException, ClassNotFoundException {
	    Class.forName("com.mysql.cj.jdbc.Driver");

	    try (Connection connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD)) {
	        String selectSQL = "SELECT COUNT(*) FROM student WHERE studentname = ?";
	        try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
	            selectStatement.setString(1, n);

	            try (ResultSet resultSet = selectStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count > 0;
	                }
	            }
	        }
	    }
	    return false;
	}
	public void cursor(){
		enroll framee = this;
	    	Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

	        // Create a new Cursor object
	        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
			
	        framee.setCursor(cursor);
	    }
	
	// to check if 
	private void userole(String username) {
	     try {
	    	 panel_3_1_1_1 = new JPanel();
	         panel_3_1_1_1.setLayout(null);
	         panel_3_1_1_1.setForeground(new Color(0, 128, 192));
	         panel_3_1_1_1.setBorder(UIManager.getBorder("Button.border"));
	         panel_3_1_1_1.setBackground(new Color(94, 103, 117));
	         panel_3_1_1_1.setBounds(0, 403, 152, 61);
	     
			
	         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolsystem", "root", "");
	         Statement statement = connection.createStatement();

	         // Query the database for the user's role based on the username
	         ResultSet resultSet = statement.executeQuery("SELECT Role FROM teacher WHERE username = '" + username + "'");

	         if (resultSet.next()) {
	             String role = resultSet.getString("Role");
	             if (role.equals("Admin")) {
	                 panel_3_1_1_1.setVisible(true);
	             } else {
	                 panel_3_1_1_1.setVisible(false);
	             }
	         } else {
	             // Username does not exist in the database
	             panel_3_1_1_1.setVisible(false);
	         }

	         resultSet.close();
	         statement.close();
	         connection.close();
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	 }
	public void searchstudent() throws ClassNotFoundException {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		enroll framee = this;
		String query = textField_3.getText().toLowerCase();
        model.setRowCount(0); // Clear the table before populating with new data
        
      
        try (Connection conn = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE studentname LIKE ?")) {

            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            int rowCount = 0; // Initialize a counter for the number of rows returned

            while (rs.next()) {
           
              
                int id = rs.getInt("studentid");
                String name =rs.getString("studentname");
                String section =rs.getString("grade");
                String age =rs.getString("age");
                String phnum = rs.getString("phonenumber");
                String address = rs.getString("address");
                
                model.addRow(new Object[]{id, name, section, age, phnum, address});
                rowCount++; 
            }
            

            rs.close();
            if(query.isEmpty()) {
          	  JOptionPane.showMessageDialog(framee, "Please fill the search bar", "Error", JOptionPane.ERROR_MESSAGE);
          }
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(framee, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(framee, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
	}
		public enroll(String username) throws ClassNotFoundException, SQLException {
			setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\graduating-student.png"));
		
			enroll.username = username;
			cursor();
			enroll framee = this;
			userole(username);

			setTitle("Student Enrollment System");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1291, 900);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			
			setContentPane(contentPane);
			
			 
		        
			JPanel panel = new JPanel();
			panel.setBounds(0, 90, 157, 771);
			panel.setBorder(new CompoundBorder());
			panel.setBackground(new Color(54, 64, 83));
			


		    
			JPanel panel_1 = new navbar();
			panel_1.setBounds(0, 0, 1270, 92);
			panel_1.setBorder(new CompoundBorder());
			panel_1.setBackground(new Color(128, 128, 128));
			
			JLabel userlabel = new JLabel(""+username);
			userlabel.setBounds(907, 40, 215, 27);
			userlabel.setForeground(new Color(255, 255, 255));
			userlabel.setFont(new Font("Vani", Font.PLAIN, 15));
			
			JLabel lblLogout = new JLabel("logout");
			lblLogout.setBounds(1132, 32, 123, 41);
			lblLogout.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					  ImageIcon imageIcon1 = new ImageIcon("pic\\logout.png");
						 int response = JOptionPane.showConfirmDialog(null, "Do you want to log-out?", "Logout Message" , JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, imageIcon1);

				        // Check the response.
				        if (response == JOptionPane.YES_OPTION) {
				            System.out.println("Yes");
				            Student_enrollment_form frame1 = new Student_enrollment_form();
							frame1.setVisible(true);
							frame1.setResizable(false);
							frame1.setLocationRelativeTo(null);
							framee.dispose();
				        } else {
				            System.out.println("No");
				        }
				}
			});
			lblLogout.setIcon(new ImageIcon("pic\\logout.png"));
			lblLogout.setForeground(Color.WHITE);
			lblLogout.setFont(new Font("Vani", Font.PLAIN, 24));
			
			 time = new JLabel("New label");
			 time.setBounds(13, 58, 302, 15);
			 time.setForeground(new Color(255, 255, 255));
			 time.setFont(new Font("Verdana", Font.PLAIN, 11));

	      
	        // Create a timer to update the time every second (1000 milliseconds)
	        Timer timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                updateTime();
	            }
	        });
	        timer.start();
			
			JLabel lblStudentEnrollmentSystem = new JLabel("STUDENT ENROLLMENT SYSTEM");
			lblStudentEnrollmentSystem.setBounds(13, 14, 545, 34);
			lblStudentEnrollmentSystem.setForeground(Color.WHITE);
			lblStudentEnrollmentSystem.setFont(new Font("Vani", Font.PLAIN, 20));
			
			JPanel panel_2 = new shadings();
			panel_2.setBounds(157, 90, 294, 771);
			panel_2.setBackground(new Color(54, 65, 83));
			
			JPanel panel_4 = new JPanel();
			panel_4.setBounds(452, 90, 818, 771);
			panel_4.setBackground(new Color(54, 64, 83));
			panel_2.setLayout(null);
			
		
			JLabel lblNewLabel_1 = new JLabel("Full name");
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1.setBounds(10, 67, 87, 34);
			panel_2.add(lblNewLabel_1);
			
			textField = new JTextField();
			textField.setFont(new Font("Verdana", Font.PLAIN, 11));
			textField.setBounds(10, 112, 265, 43);
			panel_2.add(textField);
			textField.setColumns(10);
			
			JLabel lblEnrollAStudent = new JLabel("Enroll a student");
			lblEnrollAStudent.setForeground(Color.WHITE);
			lblEnrollAStudent.setFont(new Font("Vani", Font.PLAIN, 20));
			lblEnrollAStudent.setBounds(72, 11, 197, 34);
			panel_2.add(lblEnrollAStudent);
			
			JLabel lblNewLabel_1_2 = new JLabel("Age");
			lblNewLabel_1_2.setForeground(Color.WHITE);
			lblNewLabel_1_2.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1_2.setBounds(10, 166, 87, 34);
			panel_2.add(lblNewLabel_1_2);
			
		    comboBox = new JComboBox<Object>();
			comboBox.setFont(new Font("Verdana", Font.PLAIN, 11));
			comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"Select a number", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "17", "18", "19", "20", "21", "22", "23", "24", "25", "27", "28", "29", "30"}));
			comboBox.setBounds(10, 211, 265, 43);
			panel_2.add(comboBox);
			
			JLabel lblNewLabel_1_2_1 = new JLabel("Section");
			lblNewLabel_1_2_1.setForeground(Color.WHITE);
			lblNewLabel_1_2_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1_2_1.setBounds(10, 271, 87, 34);
			panel_2.add(lblNewLabel_1_2_1);
			
			comboBox_1 = new JComboBox<Object>();
			comboBox_1.setModel(new DefaultComboBoxModel<Object>(new String[] {"Select a section", "7 - mango", "7 - rizal", "8 - laurel", "8 - Marcos", "9 - Marvelous", "9 - Bangkal", "10 - Matina", "10 - Ecoland", "11 - ICT ", "11 - HUMMS", "11 - STEM", "11 - GAS", "11 - ABM", "11 - COOKERY", "11 - ARTS DESIGN", "12 - ICT ", "12 - HUMMS", "12 - STEM", "12 - GAS", "12 - ABM", "12 - COOKERY", "12 - ARTS DESIGN"}));
			comboBox_1.setFont(new Font("Verdana", Font.PLAIN, 11));
			comboBox_1.setBounds(10, 316, 265, 43);
			panel_2.add(comboBox_1);
			
			JLabel lblNewLabel_1_3 = new JLabel("Address");
			lblNewLabel_1_3.setForeground(Color.WHITE);
			lblNewLabel_1_3.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1_3.setBounds(10, 379, 87, 34);
			panel_2.add(lblNewLabel_1_3);
			
			textField_1 = new JTextField();
			textField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
			textField_1.setColumns(10);
			textField_1.setBounds(10, 411, 265, 43);
			panel_2.add(textField_1);
			
			JLabel lblNewLabel_1_3_1 = new JLabel("Phone number");
			lblNewLabel_1_3_1.setForeground(Color.WHITE);
			lblNewLabel_1_3_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1_3_1.setBounds(10, 465, 156, 34);
			panel_2.add(lblNewLabel_1_3_1);
			
			textField_2 = new JTextField();
			textField_2.setFont(new Font("Verdana", Font.PLAIN, 11));
			textField_2.setColumns(10);
			textField_2.setBounds(10, 501, 265, 43);
			panel_2.add(textField_2);
			
			JButton btnClearAll = new JButton("CLEAR ALL");
			btnClearAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					comboBox.setSelectedItem("Select a number");
					comboBox_1.setSelectedItem("Select a section");
				
				}
			});
			btnClearAll.setBackground(new Color(255, 0, 0));
			btnClearAll.setBounds(72, 688, 133, 43);
			panel_2.add(btnClearAll);
			
			JButton btnNewButton = new JButton("ENROLL");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						InsertToDataBase();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				    
				}
			});
			btnNewButton.setBackground(new Color(0, 128, 64));
			btnNewButton.setBounds(72, 634, 133, 43);
			panel_2.add(btnNewButton);
			
			JPanel panel_5 = new GRADIENTCOLORS();
			panel_5.setBounds(0, 0, 818, 112);
			
			  // Create a DefaultTableModel to display the data
		    model = new DefaultTableModel();
		    model.addColumn("ID");
		    model.addColumn("Name");
		    model.addColumn("Section");
		    model.addColumn("phone number");
		    model.addColumn("age");
		    model.addColumn("address");
		    
			try {
		
			    // Establish a connection to the database
			    Connection conn = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);

			    // Create a ResultSet object by executing a SELECT query on the reports table
			    Statement stmt = conn.createStatement();
			    ResultSet rs = stmt.executeQuery("SELECT * FROM student");
			    
			    while (rs.next()) {
			        model.addRow(new Object[]{
			                rs.getInt("studentid"),
			                rs.getString("studentname"),
			                rs.getString("grade"),
			                rs.getString("phonenumber"),
			                rs.getString("age"),
			                rs.getString("address")
			        });
			    }

			   

			    // Close the ResultSet, Statement, and Connection objects
			    rs.close();
			    stmt.close();
			    conn.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}
			 // Create a JTable with the DefaultTableModel
		    table = new JTable(model);
		    table.setBounds(0, 113, 818, 658);
		    table.setFont(new Font("Verdana", Font.PLAIN, 12));
		    table.setForeground(new Color(255, 255, 255));
		    table.setBackground(new Color(54, 65, 83));
		 
		    table.setDefaultRenderer(Object.class, new ColorPaletteCellRenderer());
		    // Create a JScrollPane and add the JTable to it
		 
		    
	      
			table.setBackground(new Color(192, 192, 192));
			
			JLabel lblNewLabel_1_1 = new JLabel("Number");
			lblNewLabel_1_1.setBounds(19, 69, 115, 34);
			lblNewLabel_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Name");
			lblNewLabel_1_1_1.setBounds(144, 69, 39, 34);
			lblNewLabel_1_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			JLabel lblNewLabel_1_1_1_1 = new JLabel("Phone Number");
			lblNewLabel_1_1_1_1.setBounds(405, 69, 96, 34);
			lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			JLabel lblNewLabel_1_1_1_2 = new JLabel("Section");
			lblNewLabel_1_1_1_2.setBounds(265, 69, 53, 34);
			lblNewLabel_1_1_1_2.setForeground(Color.WHITE);
			lblNewLabel_1_1_1_2.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Age");
			lblNewLabel_1_1_1_1_1.setBounds(589, 69, 44, 34);
			lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Address");
			lblNewLabel_1_1_1_1_1_1.setBounds(726, 69, 67, 34);
			lblNewLabel_1_1_1_1_1_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_1_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			
			textField_3 = new JTextField();
			textField_3.setBounds(472, 20, 254, 38);
			textField_3.setColumns(10);
			 textField_3.setText("Search a name...");
			textField_3.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (textField_3.getText().equals("Search a name...")) {
                    	textField_3.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (textField_3.getText().isEmpty()) {
                    	textField_3.setText("Search a name...");
                    }
                }
            });
			
			JButton btnNewButton_1 = new JButton("");
			btnNewButton_1.setBounds(744, 11, 49, 47);
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						searchstudent();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnNewButton_1.setIcon(new ImageIcon("pic//icons8-search-48.png"));
			btnNewButton_1.setBorder(null);
			btnNewButton_1.setOpaque(true);
			btnNewButton_1.setBorderPainted(false);
			btnNewButton_1.setContentAreaFilled(false);
			
			JSpinner spinner = new JSpinner();
			spinner.setBounds(144, 12, 55, 36);
			
			JSpinner spinner_1 = new JSpinner();
			spinner_1.setBounds(233, 11, 53, 38);
			
			JLabel lblNewLabel_3 = new JLabel("To");
			lblNewLabel_3.setBounds(209, 22, 14, 15);
			lblNewLabel_3.setForeground(new Color(255, 255, 255));
			lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 11));
			
			JButton btnNewButton_2 = new JButton("CLICK");
			btnNewButton_2.setBounds(296, 19, 78, 23);
			btnNewButton_2.addActionListener(new ActionListener() {
				// This function will display table when the range was input in spinner
				public void actionPerformed(ActionEvent e) {
					  int rangeStart = (int) spinner.getValue();
	                    int rangeEnd = (int) spinner_1.getValue();
	                    model.setRowCount(0);

	                    try (Connection conn = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);
	                         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM student WHERE studentid BETWEEN ? AND ?")) {

	                        pstmt.setInt(1, rangeStart);
	                        pstmt.setInt(2, rangeEnd);
	                        ResultSet rs = pstmt.executeQuery();

	                        while (rs.next()) {
	                            int id = rs.getInt("studentid");
	                            String name = rs.getString("studentname");
	                            String section = rs.getString("grade");
	                            int age = rs.getInt("age");
	                            String phnum = rs.getString("phonenumber");
	                            String address = rs.getString("address");

	                            model.addRow(new Object[]{id, name, section, phnum, age, address});
	                        }

	                        rs.close();
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                        JOptionPane.showMessageDialog(framee, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
				}
			});
			panel_5.setLayout(null);
			panel_5.add(lblNewLabel_1_1);
			panel_5.add(lblNewLabel_1_1_1);
			panel_5.add(spinner);
			panel_5.add(lblNewLabel_3);
			panel_5.add(spinner_1);
			panel_5.add(btnNewButton_2);
			panel_5.add(lblNewLabel_1_1_1_2);
			panel_5.add(lblNewLabel_1_1_1_1);
			panel_5.add(lblNewLabel_1_1_1_1_1);
			panel_5.add(lblNewLabel_1_1_1_1_1_1);
			panel_5.add(textField_3);
			panel_5.add(btnNewButton_1);
			
			JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Column search");
			lblNewLabel_1_1_1_2_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_1_2_1.setFont(new Font("Verdana", Font.PLAIN, 13));
			lblNewLabel_1_1_1_2_1.setBounds(35, 11, 106, 34);
			panel_5.add(lblNewLabel_1_1_1_2_1);
			panel.setLayout(null);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(UIManager.getBorder("Button.border"));
			panel_3.setBackground(new Color(94, 103, 117));
			panel_3.setForeground(new Color(0, 128, 192));
			panel_3.setBounds(0, 221, 152, 61);
			panel.add(panel_3);
			panel_3.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("      HOME");
			
			// to change color when mouse is dragged
		
			lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
				public  void resetColor (JPanel pl3) {
					panel_3.setBackground(new Color (255, 255, 255));
					
					
				}
				public void mouseMoved(MouseEvent e) {
					resetColor(panel_3);
				}
			});
			
			lblNewLabel.addMouseListener(new MouseAdapter() {
				
				public  void setColor (JPanel panel_3) {
				
					panel_3.setBackground(new Color (0, 64, 128));
				}
				public  void resetColor (JPanel pl3) {
					panel_3.setBackground(new Color (94, 103, 117));
					
					
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					setColor(panel_3);
				
					mainpage frame2 = null;
					try {
						frame2 = new mainpage(username);
						frame2.setVisible(true);
						frame2.setResizable(false);
						frame2.setLocationRelativeTo(null);
						framee.dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
				
					
					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					resetColor(panel_3);
					
				}
			
			
			});
			lblNewLabel.setBounds(0, 0, 152, 61);
			panel_3.add(lblNewLabel);
			lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
			
			JPanel panel_3_1 = new JPanel();
			panel_3_1.setLayout(null);
			panel_3_1.setForeground(new Color(0, 128, 192));
			panel_3_1.setBorder(UIManager.getBorder("Button.border"));
			panel_3_1.setBackground(new Color(94, 103, 117));
			panel_3_1.setBounds(0, 281, 152, 61);
			panel.add(panel_3_1);
			
			JLabel lblNewLabel_2 = new JLabel("      ENROLL");
			lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 20));
			// to change color when mouse is dragged
			
			lblNewLabel_2 .addMouseMotionListener(new MouseMotionAdapter() {
					public  void resetColor (JPanel pl3) {
						panel_3_1.setBackground(new Color (255, 255, 255));
						
						
					}
					public void mouseMoved(MouseEvent e) {
						resetColor(panel_3_1);
					}
				});
				
			lblNewLabel_2 .addMouseListener(new MouseAdapter() {
					
					public  void setColor (JPanel panel_3_1) {
					
						panel_3_1.setBackground(new Color (0, 64, 128));
					}
					public  void resetColor (JPanel pl3) {
						panel_3_1.setBackground(new Color (94, 103, 117));
						
						
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						setColor(panel_3_1);
		
					}
					@Override
					public void mouseExited(MouseEvent e) {
						resetColor(panel_3_1);
					}
				
				
				});
			lblNewLabel_2.setBounds(0, 0, 142, 61);
			panel_3_1.add(lblNewLabel_2);
			
			JPanel panel_3_1_1 = new JPanel();
			panel_3_1_1.setLayout(null);
			panel_3_1_1.setForeground(new Color(0, 128, 192));
			panel_3_1_1.setBorder(UIManager.getBorder("Button.border"));
			panel_3_1_1.setBackground(new Color(94, 103, 117));
			panel_3_1_1.setBounds(0, 342, 152, 61);
			panel.add(panel_3_1_1);
			
			JLabel lblNewLabel_2_1 = new JLabel("      PROFILE");
			lblNewLabel_2_1.setFont(new Font("Verdana", Font.PLAIN, 20));
			lblNewLabel_2_1 .addMouseMotionListener(new MouseMotionAdapter() {
				public  void resetColor (JPanel pl3) {
					panel_3_1_1.setBackground(new Color (255, 255, 255));
					
					
				}
				public void mouseMoved(MouseEvent e) {
					resetColor(panel_3_1_1);
				}
			});
			
			lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
				
				public  void setColor (JPanel panel_3_1_1) {
				
					panel_3_1_1.setBackground(new Color (0, 64, 128));
				}
				public  void resetColor (JPanel pl3) {
					panel_3_1_1.setBackground(new Color (94, 103, 117));
					
					
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					setColor(panel_3_1_1);
					profiles framef = null;
					try {
						framef = new profiles(username);
						framef.setVisible(true);
						framef.setLocationRelativeTo(null);
						framef.setResizable(false);
						framee.dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
			
					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					resetColor(panel_3_1_1);
				}
			
			
			});
			lblNewLabel_2_1.setBounds(0, 0, 152, 61);
			panel_3_1_1.add(lblNewLabel_2_1);
			
		
			panel.add(panel_3_1_1_1);
			
			JLabel lblNewLabel_2_1_1 = new JLabel("       USER");
			lblNewLabel_2_1_1.setFont(new Font("Verdana", Font.PLAIN, 20));
			lblNewLabel_2_1_1.setBounds(0, 0, 152, 61);
			lblNewLabel_2_1_1.addMouseMotionListener(new MouseMotionAdapter() {
				public  void resetColor (JPanel pl3) {
					panel_3_1_1_1.setBackground(new Color (255, 255, 255));
					
					
				}
				public void mouseMoved(MouseEvent e) {
					resetColor(panel_3_1_1_1);
				}
			});
			
			lblNewLabel_2_1_1.addMouseListener(new MouseAdapter() {
				
				public  void setColor (JPanel panel_3_1_1_1) {
				
					panel_3_1_1_1.setBackground(new Color (0, 64, 128));
				}
				public  void resetColor (JPanel pl3) {
					panel_3_1_1_1.setBackground(new Color (94, 103, 117));
					
					
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					setColor(panel_3_1_1_1);
					user_access frameuser;
					try {
						frameuser = new user_access(username);
						frameuser.setVisible(true);
						frameuser.setLocationRelativeTo(null);
						frameuser.setResizable(false);
						framee.dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					resetColor(panel_3_1_1_1);
				}
			
			
			});
			contentPane.setLayout(null);
			panel_3_1_1_1.add(lblNewLabel_2_1_1);
			
			JLabel lblNewLabel_1_1_3_1 = new JLabel("");
			lblNewLabel_1_1_3_1.setIcon(new ImageIcon("pic\\graduating-student.png"));
			lblNewLabel_1_1_3_1.setForeground(Color.WHITE);
			lblNewLabel_1_1_3_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
			lblNewLabel_1_1_3_1.setBounds(10, 11, 127, 128);
			panel.add(lblNewLabel_1_1_3_1);
			contentPane.add(panel);
			contentPane.add(panel_2);
			contentPane.add(panel_4);
			panel_4.setLayout(null);
			panel_4.add(panel_5);
			panel_4.add(table);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			panel_1.add(time);
			panel_1.add(lblStudentEnrollmentSystem);
			panel_1.add(userlabel);
			panel_1.add(lblLogout);
			
			    
			
		}
		 private void updateTime() {
		        LocalTime currentTime = LocalTime.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		        String formattedTime = currentTime.format(formatter);
		        time.setText("Current Time UTC: 8:00 Manila: " + formattedTime);
		    }
	}
