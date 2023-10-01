package Profile;

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
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;

import Studentenrollment.mainpage;
import Studentenrollment.navbar;
import User.user_access;
import enrollment.enroll;
import login_form.Student_enrollment_form;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class profiles extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JButton btnNewButton;
	// to collect user to other jframe
	private static String username;
	static JLabel lblNewLabel1 , userlabel;
	static int numberOfRows;
	static int numberOfRows1;
	static JLabel time ,  lblPassword ,  lblUsername;
	private static JButton btnChangePassword;
	private   DefaultTableModel model;
	
	// url
		private String mydb_url = "jdbc:mysql://localhost:3306/schoolsystem";
		
		@SuppressWarnings("unused")
		//database username
		private String myDB_username = "root";
		@SuppressWarnings("unused")
		
		//database password
		private String myDB_PASSWORD = "";
		private JTextField newuser;
		private JTextField currentuser;
	    
		private JPasswordField passwordField;
		private JPasswordField passwordField_1;
		private JPanel panel_3_1_1_1;
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
					profiles framef = new profiles(username1);
					framef.setVisible(true);
					framef.setLocationRelativeTo(null);
					framef.setResizable(false);
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
	
	private void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
	    // Get the current password from the user
	    String currentPassword = new String(passwordField.getPassword());
	    String newPassword = new String(passwordField_1.getPassword());

	     if (currentPassword.isEmpty() || newPassword.isEmpty()) {
	    	 JOptionPane.showMessageDialog(null, "Please fill in the blanks");
	     } else {
	    	// Check if the current password exists in the database
	 	    try (Connection connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD)) {
	 	        // Prepare the SQL statement
	 	        PreparedStatement statement = connection.prepareStatement("SELECT password, salt FROM teacher WHERE username = ?");
	 	        String usertype = username;

	 	        // Set the parameters
	 	        statement.setString(1, usertype);

	 	        // Execute the statement and get the result set
	 	        ResultSet resultSet = statement.executeQuery();

	 	        // Check if the result set is empty
	 	        if (!resultSet.next()) {
	 	            // The username does not exist in the database
	 	            JOptionPane.showMessageDialog(null, "Username not found.");
	 	            return;
	 	        }

	 	        // Retrieve the hashed password and salt from the database
	 	        String storedPassword = resultSet.getString("password");
	 	        byte[] salt = resultSet.getBytes("salt");

	 	        // Hash the user's input current password with the retrieved salt
	 	        String hashedInputPassword = hashPassword(currentPassword, salt);

	 	        // Compare the hashed input password with the stored password
	 	        if (!hashedInputPassword.equals(storedPassword)) {
	 	            // The current password is incorrect
	 	            JOptionPane.showMessageDialog(null, "Incorrect current password.");
	 	            return;
	 	        }
	 	        else {
	 	        	 // Generate a new salt for the new password
		 	        byte[] newSalt = generateSalt();

		 	        // Hash the new password using the new salt
		 	        String hashedNewPassword = hashPassword(newPassword, newSalt);

		 	        // Update the password and salt in the database
		 	        PreparedStatement updateStatement = connection.prepareStatement("UPDATE teacher SET password = ?, salt = ? WHERE username = ?");
		 	        updateStatement.setString(1, hashedNewPassword);
		 	        updateStatement.setBytes(2, newSalt);
		 	        updateStatement.setString(3, usertype);
		 	        updateStatement.executeUpdate();

		 	        // Notify the user that the password has been changed
		 	        JOptionPane.showMessageDialog(null, "Password changed successfully!");
		 	       passwordField.setText("");
		 	       passwordField_1.setText("");
	 	        }
	 	       
	 	    } catch (SQLException e) {
	 	        e.printStackTrace();
	 	    }
	     }
	}
	
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
	public void changeUser() throws SQLException, ClassNotFoundException {
	    profiles framef = this;
	    
	    // this is where we type our new username
	    String username2 = newuser.getText();
	    
	    // this is where we type our current username
	    String username1 = currentuser.getText();
	    
	    if (username1.isEmpty() || username2.isEmpty()) {
	        JOptionPane.showMessageDialog(framef, "Please fill in the blanks", "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
	        Connection connection = null;
	        Statement statement = null;
	        ResultSet resultSet = null;
	        
	        try {
	            connection = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);
	            statement = connection.createStatement();
	            
	            // Check if the current username exists
	            resultSet = statement.executeQuery("SELECT * FROM teacher WHERE username = '" + username1 + "'");
	            
	            if (!resultSet.next()) {
	                // The current username does not exist
	                JOptionPane.showMessageDialog(framef, "Current username does not exist", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            
	            // Close the previous ResultSet before executing another query
	            resultSet.close();
	            
	            // Check if the new username already exists
	            resultSet = statement.executeQuery("SELECT * FROM teacher WHERE username = '" + username2 + "'");
	            
	            if (resultSet.next()) {
	                // The new username already exists
	                JOptionPane.showMessageDialog(framef, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            
	            // Update the current username in the database
	            statement.executeUpdate("UPDATE teacher SET username = '" + username2 + "' WHERE username = '" + username1 + "'");
	            JOptionPane.showMessageDialog(framef, "Username successfully changed", "", JOptionPane.INFORMATION_MESSAGE);
	            profiles framef1 = new profiles(username2);
				framef1.setVisible(true);
				framef.dispose();
				framef1.setLocationRelativeTo(null);
				framef1.setResizable(false);
	        } finally {
	            // Close resources in a finally block to ensure they are always closed
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (statement != null) {
	                statement.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        }
	    }
	}
	
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
	public void cursor(){
	 profiles framef = this;
    	Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

        // Create a new Cursor object
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
		
        framef.setCursor(cursor);
    }
	public profiles(String username) throws ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\graduating-student.png"));
	
		
		cursor();
		userole(username);
		profiles.username = username;
		profiles framef = this;
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
		lblLogout.setIcon(new ImageIcon("pic\\logout.png"));
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
					framef.dispose();
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
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_1.add(time);
		panel_1.add(lblStudentEnrollmentSystem);
		panel_1.add(userlabel);
		panel_1.add(lblLogout);
		
		JLabel lblDashboard = new JLabel("MY PROFILE");
		lblDashboard.setForeground(Color.WHITE);
		lblDashboard.setFont(new Font("Vani", Font.PLAIN, 20));
		lblDashboard.setBounds(548, 126, 600, 34);
		contentPane.add(lblDashboard);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(54, 65, 83));
		panel_2.setBounds(273, 164, 874, 627);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblStudentEnrollmentSystem_1 = new JLabel("");
		lblStudentEnrollmentSystem_1.setIcon(new ImageIcon("pic\\icons8-male-user-96.png"));
		lblStudentEnrollmentSystem_1.setForeground(Color.WHITE);
		lblStudentEnrollmentSystem_1.setFont(new Font("Vani", Font.PLAIN, 20));
		lblStudentEnrollmentSystem_1.setBounds(382, -24, 119, 141);
		panel_2.add(lblStudentEnrollmentSystem_1);
		
		 lblUsername = new JLabel("Username:"+username);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Vani", Font.PLAIN, 30));
		lblUsername.setBounds(218, 138, 529, 88);
		panel_2.add(lblUsername);
		
		
		 lblPassword = new JLabel("Password:************");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Vani", Font.PLAIN, 30));
		lblPassword.setBounds(217, 215, 530, 88);
		panel_2.add(lblPassword);
		
		JLabel lblCurrentUsername = new JLabel("Current Username");
		lblCurrentUsername.setVisible(false);
		lblCurrentUsername.setForeground(Color.WHITE);
		lblCurrentUsername.setFont(new Font("Vani", Font.PLAIN, 20));
		lblCurrentUsername.setBounds(207, 96, 363, 88);
		panel_2.add(lblCurrentUsername);
		
		JLabel lblNewUsername = new JLabel("New Username");
		lblNewUsername.setVisible(false);
		lblNewUsername.setForeground(Color.WHITE);
		lblNewUsername.setFont(new Font("Vani", Font.PLAIN, 20));
		lblNewUsername.setBounds(207, 188, 363, 88);
		panel_2.add(lblNewUsername);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.setVisible(false);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					changeUser();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(632, 487, 141, 63);
		panel_2.add(btnEnter);
		
	
		
		newuser = new JTextField();
		newuser.setVisible(false);
		newuser.setBounds(207, 237, 530, 51);
		panel_2.add(newuser);
		newuser.setColumns(10);
		
		currentuser = new JTextField(""+username);
		currentuser.setFont(new Font("Verdana", Font.PLAIN, 11));
		currentuser.setColumns(10);
		currentuser.setVisible(false);
		currentuser.setBounds(207, 153, 530, 51);
		panel_2.add(currentuser);
		
		
		passwordField = new JPasswordField();
		passwordField.setVisible(false);
		passwordField.setBounds(207, 153, 530, 51);
		panel_2.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setVisible(false);
		passwordField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		passwordField_1.setBounds(207, 237, 530, 51);
		panel_2.add(passwordField_1);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox(" click to check to see the password");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
				      passwordField.setEchoChar((char)0);
				      passwordField_1.setEchoChar((char)0);
					} 
					
					if (!chckbxNewCheckBox.isSelected()) {
						passwordField.setEchoChar('*');
						 passwordField_1.setEchoChar('*');
					}
			}
		});
		chckbxNewCheckBox.setVisible(false);
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(207, 310, 235, 23);
		panel_2.add(chckbxNewCheckBox);
		
		JButton btnNewButton_1_1 = new JButton("Enter");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				  String password21 = new String(passwordField_1.getPassword());
				try {
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
					      if (!hasCapsLock|| !hasSpecialCharacters) {
						        JOptionPane.showMessageDialog(null, "The password must contain at least one uppercase letter and one special character.");
						    } 
					      
					      else {
						changePassword();
						}

					} 
					else {
					      JOptionPane.showMessageDialog(null, "The passwords match and the length is not correct. It should be 8 to 12 characters.");
					    }
										 
					
					
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1.setVisible(false);
		btnNewButton_1_1.setBounds(456, 487, 135, 63);
		panel_2.add(btnNewButton_1_1);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCurrentUsername.setText("Current Username");
				lblNewUsername.setText("New Username");
				passwordField.setVisible(false);
				passwordField_1.setVisible(false);
				lblCurrentUsername.setVisible(false);
				lblNewUsername.setVisible(false);
				chckbxNewCheckBox.setVisible(false);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
				btnNewButton.setVisible(true);
				btnChangePassword.setVisible(true);
				btnNewButton_1.setVisible(false);
				btnNewButton_1_1.setVisible(false);
				
			}
		});
		btnNewButton_1.setVisible(false);
		btnNewButton_1.setBounds(293, 487, 135, 63);
		panel_2.add(btnNewButton_1);
		
		
		
        btnChangePassword = new JButton("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 btnChangePassword.setVisible(false);
				btnNewButton.setVisible(false);
				lblCurrentUsername.setText("Current Password");
				lblNewUsername.setText("New Password");
				passwordField.setVisible(true);
				passwordField_1.setVisible(true);
				lblCurrentUsername.setVisible(true);
				lblNewUsername.setVisible(true);
				chckbxNewCheckBox.setVisible(true);
				lblUsername.setVisible(false);
				lblPassword.setVisible(false);
				btnNewButton_1.setVisible(true);
				btnNewButton_1_1.setVisible(true);
			}
		});
		btnChangePassword.setBounds(632, 487, 141, 63);
		panel_2.add(btnChangePassword);
		
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setVisible(false);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
				btnNewButton.setVisible(true);
				lblCurrentUsername.setVisible(false);
				lblNewUsername.setVisible(false);
				newuser.setVisible(false);
				currentuser.setVisible(false);
				btnEnter.setVisible(false);
				btnChangePassword.setVisible(true);
				 btnChangePassword.setVisible(true);
			}
		});
		btnCancel.setVisible(false);
		btnCancel.setBounds(142, 487, 141, 63);
		panel_2.add(btnCancel);
		
		btnNewButton = new JButton("Change Username");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setVisible(true);
				btnNewButton.setVisible(false);
				lblCurrentUsername.setVisible(true);
				lblNewUsername.setVisible(true);
				lblUsername.setVisible(false);
				lblPassword.setVisible(false);
				newuser.setVisible(true);
				currentuser.setVisible(true);
				btnChangePassword.setVisible(false);
				btnEnter.setVisible(true);
				
				
			}
		});
		btnNewButton.setBounds(142, 487, 141, 63);
		panel_2.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("User Info");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(359, 74, 169, 70);
		panel_2.add(lblNewLabel_1);
		
		
	
		
		
	
		
	
	}
	 private void updateTime() {
	        LocalTime currentTime = LocalTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	        String formattedTime = currentTime.format(formatter);
	        time.setText("Current Time UTC: 8:00 Manila: " + formattedTime);
	    }
}
