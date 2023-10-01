package User;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import Profile.profiles;
import Studentenrollment.mainpage;
import Studentenrollment.navbar;
import enrollment.enroll;
import login_form.Student_enrollment_form;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class user_access extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// to collect user to other jframe
	private static String username;
	static JLabel lblNewLabel1 , userlabel;
	static int numberOfRows;
	static int numberOfRows1;
	static JLabel time ,  lblPassword;
	private   DefaultTableModel model;
	
	// url
		private String mydb_url = "jdbc:mysql://localhost:3306/schoolsystem";
		
		@SuppressWarnings("unused")
		//database username
		private String myDB_username = "root";
		@SuppressWarnings("unused")
		
		//database password
		private String myDB_PASSWORD = "";
		private JTextField Username;
	    
		private JPasswordField userpass;
		private JPasswordField confirmedpassword;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 String username1 = username; // Replace with actual username
		// the code for FLATLAF
	    try {
	    	FlatGradiantoDeepOceanIJTheme.setup();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user_access frameuser = new user_access(username1);
					frameuser.setVisible(true);
					frameuser.setLocationRelativeTo(null);
					frameuser.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	
	
	private byte[] generateSalt() {
	    SecureRandom random = new SecureRandom();
	    byte[] salt = new byte[16]; // You can adjust the salt size as needed
	    random.nextBytes(salt);
	    return salt;
	}
	// MD5 function 1
	private String hashPassword(String password2, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    int iterations = 10000; // Number of iterations
	    int keyLength = 256; // Key length in bits

	    PBEKeySpec spec = new PBEKeySpec(password2.toCharArray(), salt, iterations, keyLength);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    byte[] hashedNewPassword = factory.generateSecret(spec).getEncoded();

	    return bytesToHex(hashedNewPassword);
	}

	// MD5 function 2
	private String bytesToHex(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder();
	    for (byte aByte : bytes) {
	        hexString.append(String.format("%02x", aByte));
	    }
	    return hexString.toString();
	}
	public boolean verifyPasswordLength(String password21) {
	    int length = password21.length();
	    return length >= 8 && length <= 12;
	}
	public void AddUser() throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
	    String username = Username.getText();
	    String password = new String(userpass.getPassword());

	    // Generate a random salt for each user
	    byte[] salt = generateSalt();

	    // Hash the password using PBKDF2
	    String hashedPassword = hashPassword(password, salt);
	    String role = "Admin";

	    Class.forName("com.mysql.cj.jdbc.Driver");

	    try (Connection connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD)) {

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM teacher");

	        // to organize the id
	        int highestId = 0;

	        // Check if there is a result.
	        if (resultSet.next()) {
	            highestId = resultSet.getInt(1);
	        }
	        int newId = highestId + 1;

	        // Insert a new user without specifying 'id' since it's auto-increment
	        String insertSQL = "INSERT INTO teacher (id, username, password, salt ,Role) VALUES (?, ?, ?, ?,?)";
	        try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

	            insertStatement.setInt(1, newId);
	            insertStatement.setString(2, username);
	            insertStatement.setString(3, hashedPassword);
	            insertStatement.setBytes(4, salt);
	            insertStatement.setString(5,  role);

	            int rowsAffected = insertStatement.executeUpdate();

	            if (rowsAffected == 1) {
	                JOptionPane.showMessageDialog(null, "Sign up sucess.");
	                Username.setText("");
	                userpass.setText("");
	                confirmedpassword.setText("");
	               
					
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to add user.");
	            }
	        }
	    }
	}


//check if user is exist
public boolean doesUsernameExist(String username) throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");

    try (Connection connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD)) {
        String selectSQL = "SELECT COUNT(*) FROM teacher WHERE username = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
            selectStatement.setString(1, username);

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
	 user_access framef = this;
    	Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

        // Create a new Cursor object
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
		
        framef.setCursor(cursor);
    }
	public user_access(String username) throws ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\graduating-student.png"));
	
		
		cursor();
	
		user_access.username = username;
		user_access framef = this;
		setTitle("Student Enrollment System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1243, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		setContentPane(contentPane);
		
		 
	        
		JPanel panel = new JPanel();
		panel.setBounds(0, 91, 157, 765);
		panel.setBorder(new CompoundBorder());
		panel.setBackground(new Color(54, 64, 83));
		


	    
		JPanel panel_1 = new navbar();
		panel_1.setBounds(0, 0, 1227, 92);
		panel_1.setBorder(new CompoundBorder());
		panel_1.setBackground(new Color(128, 128, 128));
		
		 userlabel = new JLabel(""+username);
		userlabel.setBounds(837, 40, 242, 27);
		userlabel.setForeground(new Color(255, 255, 255));
		userlabel.setFont(new Font("Vani", Font.PLAIN, 15));
		
		JLabel lblLogout = new JLabel("logout");
		lblLogout.setBounds(1081, 32, 123, 41);
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
						framef.dispose();
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
		lblStudentEnrollmentSystem.setBounds(13, 14, 600, 34);
		lblStudentEnrollmentSystem.setForeground(Color.WHITE);
		lblStudentEnrollmentSystem.setFont(new Font("Vani", Font.PLAIN, 20));
		
		try {
		    // Establish a connection to the database
		    Connection conn = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);

		    // Create a ResultSet object by executing a SELECT query on the reports table
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM reports WHERE date = CURRENT_DATE");

		    // Create a DefaultTableModel to display the data
		    model = new DefaultTableModel();
		    model.addColumn("ID");
		    model.addColumn("Username");
		    model.addColumn("Activity");
		    model.addColumn("Date");

		    while (rs.next()) {
		        model.addRow(new Object[]{
		            rs.getInt("id"),
		            rs.getString("username"),
		            rs.getString("activity"),
		            rs.getDate("date")
		        });
		    }

		    // Create a JScrollPane and add the JTable to it
		 

		    // Create a GroupLayout for your content pane
		    

		    // Close the ResultSet, Statement, and Connection objects
		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
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
				mainpage frame2 = null;
				try {
					frame2 = new mainpage(username);
					frame2.setVisible(true);
					frame2.setLocationRelativeTo(null);
					frame2.setResizable(false);
					framef.dispose();
					setColor(panel_3);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
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
					enroll framee = null;
					try {
						framee = new enroll(username);
						framee.setVisible(true);
						framee.setResizable(false);
						framef.dispose();
						framee.setLocationRelativeTo(null);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					resetColor(panel_3_1);
				}
			
			
			});
		lblNewLabel_2.setBounds(0, 0, 152, 61);
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
		
		JPanel panel_3_1_1_1 = new JPanel();
		panel_3_1_1_1.setLayout(null);
		panel_3_1_1_1.setForeground(new Color(0, 128, 192));
		panel_3_1_1_1.setBorder(UIManager.getBorder("Button.border"));
		panel_3_1_1_1.setBackground(new Color(94, 103, 117));
		panel_3_1_1_1.setBounds(0, 403, 152, 61);
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
				
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				resetColor(panel_3_1_1_1);
			}
		
		
		});
		contentPane.setLayout(null);
		panel_3_1_1_1.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_1_1_3_1 = new JLabel("");
		lblNewLabel_1_1_3_1.setIcon(new ImageIcon("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\graduating-student.png"));
		lblNewLabel_1_1_3_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_3_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_1_1_3_1.setBounds(10, 11, 127, 128);
		panel.add(lblNewLabel_1_1_3_1);
		contentPane.add(panel);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_1.add(time);
		panel_1.add(lblStudentEnrollmentSystem);
		panel_1.add(userlabel);
		panel_1.add(lblLogout);
		
		JLabel lblDashboard = new JLabel("ADMIN DASHBOARD");
		lblDashboard.setForeground(Color.WHITE);
		lblDashboard.setFont(new Font("Vani", Font.PLAIN, 20));
		lblDashboard.setBounds(491, 109, 600, 34);
		contentPane.add(lblDashboard);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(54, 65, 83));
		panel_2.setBounds(171, 152, 389, 680);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblStudentEnrollmentSystem_1 = new JLabel("");
		lblStudentEnrollmentSystem_1.setIcon(new ImageIcon("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\icons8-male-user-96.png"));
		lblStudentEnrollmentSystem_1.setForeground(Color.WHITE);
		lblStudentEnrollmentSystem_1.setFont(new Font("Vani", Font.PLAIN, 20));
		lblStudentEnrollmentSystem_1.setBounds(126, 0, 96, 109);
		panel_2.add(lblStudentEnrollmentSystem_1);
		
		
		 lblPassword = new JLabel("Username");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Vani", Font.PLAIN, 18));
		lblPassword.setBounds(31, 142, 214, 52);
		panel_2.add(lblPassword);
		
		JButton btnEnter = new JButton("Clear");
		btnEnter.setBackground(new Color(255, 0, 0));

		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		btnEnter.setBounds(210, 578, 141, 63);
		panel_2.add(btnEnter);
		
	
		
		Username = new JTextField();

		Username.setBounds(24, 190, 318, 52);
		panel_2.add(Username);
		Username.setColumns(10);
		
		
		userpass = new JPasswordField();
	
		userpass.setBounds(24, 298, 318, 52);
		panel_2.add(userpass);
		
		confirmedpassword = new JPasswordField();
	
		confirmedpassword.setFont(new Font("Verdana", Font.PLAIN, 11));
		confirmedpassword.setBounds(24, 412, 318, 52);
		panel_2.add(confirmedpassword);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox(" click to check to see the password");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
				      userpass.setEchoChar((char)0);
				      confirmedpassword.setEchoChar((char)0);
					} 
					
					if (!chckbxNewCheckBox.isSelected()) {
						userpass.setEchoChar('*');
						 confirmedpassword.setEchoChar('*');
					}
			}
		});
	
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(31, 493, 235, 23);
		panel_2.add(chckbxNewCheckBox);
		
		JButton btnNewButton_1_1 = new JButton("Enter");
		btnNewButton_1_1.setBackground(new Color(0, 128, 64));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                   String username = Username.getText();
				  String password21 = new String(confirmedpassword.getPassword());
				  String password12 = new String(userpass.getPassword());
				  if (password12.equals(password21)) {
					    if (verifyPasswordLength(password21)) {
					      boolean hasCapsLock = false;
					      boolean hasSpecialCharacters = false;

					      for (char character : password21.toCharArray()) {
					        if (Character.isUpperCase(character)) {
					          hasCapsLock = true;
					        } else if (!Character.isLetterOrDigit(character)) {
					          hasSpecialCharacters = true;
					        }
					      }

					      if (!hasCapsLock || !hasSpecialCharacters) {
					        JOptionPane.showMessageDialog(null, "The password must contain at least one uppercase letter and one special character.");
					      } else {
					    	  
					        try {
					          // Check if the username already exists in the database.
					          if (doesUsernameExist(username)) {
					            JOptionPane.showMessageDialog(null, "Teacher already exists.");
					          } else {
					            // Add the user to the database.
					            AddUser();
					            JOptionPane.showMessageDialog(null, "Teacher is successful registered.");
					          }
					        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
					          ex.printStackTrace();
					          JOptionPane.showMessageDialog(null, "An error occurred while checking the username.");
					        }
					      }
					    } else {
					      JOptionPane.showMessageDialog(null, "The passwords match and the length is not correct. It should be 8 to 12 characters.");
					    }
					  } else {
					    JOptionPane.showMessageDialog(null, "password did not match.");
					  }
					
				 
						 
		
			}
		});
		
		btnNewButton_1_1.setBounds(24, 578, 135, 63);
		panel_2.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("Create an Admin Account");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(24, 85, 352, 70);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblPassword_2 = new JLabel("Password");
		lblPassword_2.setForeground(Color.WHITE);
		lblPassword_2.setFont(new Font("Vani", Font.PLAIN, 18));
		lblPassword_2.setBounds(24, 259, 188, 43);
		panel_2.add(lblPassword_2);
		
		JLabel lblPassword_2_1 = new JLabel("Confirmed password");
		lblPassword_2_1.setForeground(Color.WHITE);
		lblPassword_2_1.setFont(new Font("Vani", Font.PLAIN, 18));
		lblPassword_2_1.setBounds(31, 371, 204, 40);
		panel_2.add(lblPassword_2_1);
		
		JPanel panel_4 = new DatabaseDisplayPanel();
		panel_4.setBackground(new Color(54, 64, 83));
		panel_4.setBounds(581, 154, 636, 678);
		contentPane.add(panel_4);
		
		
	
		
		
	
		
	
	}
	 private void updateTime() {
	        LocalTime currentTime = LocalTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	        String formattedTime = currentTime.format(formatter);
	        time.setText("Current Time UTC: 8:00 Manila: " + formattedTime);
	    }
}
