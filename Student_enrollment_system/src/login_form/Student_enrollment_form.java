package login_form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme;
import Studentenrollment.mainpage;
import admin_Registration.Admin_sign_up;
import admin_Registration.GRADIENTCOLORS;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 

public class Student_enrollment_form extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField JPasswordField1;
    public static String username1;
    private int time=0;
    
    @SuppressWarnings("unused")
    // url of database
	private final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
    
	@SuppressWarnings("unused")
	//database username
	private String DB_USER = "root";
	@SuppressWarnings("unused")
	
	//database password
	private String DB_PASSWORD = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// the code for FLATLAF
		
	    try {
	    	FlatGradiantoDeepOceanIJTheme.setup();
	    } catch (Exception e) {
	    	
	        e.printStackTrace();
	    }
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Student_enrollment_form frame1 = new Student_enrollment_form();
					frame1.setVisible(true);
					frame1.setResizable(false);
					frame1.setLocationRelativeTo(null);
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
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws HeadlessException 
	 */
	@SuppressWarnings("unused")
	
	public void login_authentication(String username, String password) throws ClassNotFoundException, SQLException, HeadlessException, NoSuchAlgorithmException, InvalidKeySpecException {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Student_enrollment_form frame1 = this;
	    // Connect to the database
	    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolsystem", "root", "")) {
	        // SQL command
	        String sql = "SELECT * FROM teacher WHERE username = ?";

	        // SQL function
	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            preparedStatement.setString(1, username);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Authentication successful

	                    // Without this function, it will not read your password encryption
	                    String storedHashedPassword = resultSet.getString("password");
	                    byte[] storedSalt = resultSet.getBytes("salt");

	                    // Verify the entered password

	                    // This is a very important function to read and validate the encryption stored in the database
	                    if (validatePassword(password, storedSalt, storedHashedPassword)) {
	                        ImageIcon imageIcon = new ImageIcon("pic//cloudy-day.png");
	                        JOptionPane.showMessageDialog(null, "Welcome, " + username + "", "Login Message", JOptionPane.PLAIN_MESSAGE, imageIcon);
	                        mainpage frame2 = new mainpage(username);
	                        frame2.setVisible(true);
	                        frame2.setLocationRelativeTo(null);
	                        frame2.setResizable(false);
	                        frame1.dispose();
	                        System.out.println("Authentication successful!");
	                        String activity = "Login";
	           

	                        // Insert  date and activity into the database
	                    
	                        String sqlQuery= "INSERT INTO reports (username, activity, date) VALUES (?, ?,  ?)";
	                        PreparedStatement preparedStatement1 = con.prepareStatement(sqlQuery);
	                        preparedStatement1.setString(1, username);
	                        preparedStatement1.setString(2, activity);
	                        preparedStatement1.setDate(3, new java.sql.Date(System.currentTimeMillis()));

	                   
	                        int rowsAffected = preparedStatement1.executeUpdate();
	                        if (rowsAffected > 0) {
	                            System.out.println("Activity recorded successfully.");
	                        } else {
	                            System.out.println("Failed to record activity.");
	                        }
	                        
	                        // You can retrieve other data from the resultSet if needed
	                    } else {
	                    	time++;
	                        ImageIcon imageIcon1 = new ImageIcon("pic//attention.png");
	                        JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Message", JOptionPane.PLAIN_MESSAGE, imageIcon1);
	                        System.out.println("Authentication failed!");
	                         if (time==5) {
	                        	 
	                        	 TimerExample t = new TimerExample();
	                        	 t.startTimer();

	                        	 time-=5;
	                         }
	                    }
	                    
	                } 
	                else {
	                    // User not found
	                    ImageIcon imageIcon12 = new ImageIcon("pic//attention.png");
	                    JOptionPane.showMessageDialog(null, "User not found", "Login Message", JOptionPane.NO_OPTION, imageIcon12);
	                    System.out.println("Authentication failed!");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    
	public boolean authenticateUser(String username, String password) throws SQLException {

		// Connect to the database.
		Connection connection = null;
		
		try {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolsystem", "root", "");
		} catch (SQLException e) {
		e.printStackTrace();
		}

		// Create a statement.
		Statement statement = null;
		try {
		statement = connection.createStatement();
		} catch (SQLException e) {
		e.printStackTrace();
		}

		// Get the hashed password from the database.
		String query = "SELECT password FROM teacher WHERE username = '" + username + "'";
		ResultSet resultSet = null;
		try {
		resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
		e.printStackTrace();
		}

		// If the result set is empty, the user does not exist.
		if (!resultSet.next()) {
		return false;
		}

		// Get the hashed password from the result set.
		String hashedPassword = resultSet.getString("password");

		// Compare the hashed password entered by the user to the hashed password stored in the database.
		if (hashedPassword.equals(password)) {
		return true;
		} else {
		return false;
		}
		}
	
	// you need this code to make it function well, without this your user can't login hahahaha
	private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    int iterations = 10000;
	    int keyLength = 256;

	    // Create a key specification with the provided password and salt
	    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

	    // Use PBKDF2 with HMAC-SHA256 to hash the password
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    byte[] hashedPassword = factory.generateSecret(keySpec).getEncoded();

	    // Convert the hashed password to a hexadecimal string representation
	    StringBuilder hexString = new StringBuilder();
	    for (byte b : hashedPassword) {
	        hexString.append(String.format("%02x", b));
	    }

	    return hexString.toString();
	}
	
	// this boolean will validate the password
	public boolean validatePassword(String enteredPassword, byte[] storedSalt, String storedHashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    // Hash the entered password with the stored salt
	    String computedHashedPassword = hashPassword(enteredPassword, storedSalt);

	    // Compare the computed hash with the stored hash
	    return storedHashedPassword.equals(computedHashedPassword);
	}
	
	// This boolean is to check if xxamp, mysql and apache is online
	public boolean isMySQLXAMPPOnline() {
	    String jdbcUrl = "jdbc:mysql://localhost:3306/schoolsystem";
	    String username = "root";
	    String password = "";

	    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
	        return true; // Successfully connected, MySQL is online
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Failed to connect, MySQL is offline
	    }
	}
	 public void login(String username, String password) {
	        try {
	            if (!isMySQLXAMPPOnline()) {
	                // Display a message to the user and prevent them from logging in.
	                ImageIcon imageIcon = new ImageIcon("pic//attention.png");
	                JOptionPane.showMessageDialog(null, "MySQL XAMPP is offline. Please try again later.", "Login Message", JOptionPane.PLAIN_MESSAGE, imageIcon);
	                return;
	            }

	            login_authentication(username, password);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 class TimerExample {
		    private int remainingTime = 30; // Initial time in seconds

		    public void startTimer() {
		        ActionListener taskPerformer = new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		                if (remainingTime > 0) {
		                	
		                    // Update the remaining time.
		                    remainingTime--;

		                    // Create a message dialog with the current remaining time.
		                    ImageIcon imageIcon = new ImageIcon("pic//hourglass.png");
		                    JOptionPane.showMessageDialog(null, "You reached the login attempt Remaining time: " + remainingTime + " seconds", "Login Message", JOptionPane.PLAIN_MESSAGE,imageIcon);
		                } else {
		                	
		                    // Time's up, stop the timer.
		                    ((Timer) evt.getSource()).stop();
		                    ImageIcon imageIcon = new ImageIcon("pic//hourglass.png");
		                    JOptionPane.showMessageDialog(null, "You can now login again", "Login Message", JOptionPane.PLAIN_MESSAGE,imageIcon);
		                }
		            }
		        };

		        Timer timer = new Timer(1000, taskPerformer); // Timer ticks every 1000 milliseconds (1 second)
		        timer.start();
		    }
	 }
	  public void cursors() {
		  Student_enrollment_form frame1 = this;
		  
			// customize cursor
			 Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

		        // Create a new Cursor object
		        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
				
		        frame1.setCursor(cursor);
	  }
	public Student_enrollment_form() {
		Student_enrollment_form frame1 = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\graduating-student.png"));
		setTitle("STUDENT MANAGEMENT SYSTEM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 701);
	
		cursors();
		//main panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		//PANEL2
		JPanel panel = new GRADIENTCOLORS();
		panel.setBackground(new Color(0, 128, 192));
		panel.setBounds(0, 301, 577, 361);
		contentPane.add(panel);
		panel.setLayout(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//labels
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("pic\\graduating-student.png"));
		lblNewLabel.setBounds(204, 94, 260, 212);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("STUDENT ENROLLMENT SYSTEM");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 30));
		lblNewLabel_1.setBounds(21, 30, 556, 103);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("No account? click her to sign up");
		 lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		Admin_sign_up framesignup = new Admin_sign_up();
				framesignup.setVisible(true);
				framesignup.setLocationRelativeTo(null);
				framesignup.setResizable(false);
				frame1.dispose();
		 	}
		 });
		 lblNewLabel_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 lblNewLabel_1_1.setBounds(200, 328, 249, 44);
		 panel.add(lblNewLabel_1_1);
		 
		 JLabel lblNewLabel_1_1_1 = new JLabel("ENTER YOUR LOGIN CREDENTIALS");
		 lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 lblNewLabel_1_1_1.setBounds(176, 0, 249, 44);
		 panel.add(lblNewLabel_1_1_1);

		
		//textfields
		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.PLAIN, 20));
		textField.setForeground(new Color(255, 255, 255));
		textField.setBackground(new Color(192, 192, 192));
		textField.setToolTipText("");
		textField.setBounds(116, 55, 419, 64);
		panel.add(textField);
		textField.setColumns(10);
		
		 TitledBorder border = new TitledBorder("USERNAME");
		 border.setTitleFont(new Font("Verdana", Font.BOLD, 12));
		 border.setTitleColor(Color.WHITE);
		 
		 JPasswordField1 = new JPasswordField();
		 JPasswordField1.setFont(new Font("Verdana", Font.PLAIN, 20));
		 JPasswordField1.setToolTipText("");
		 JPasswordField1.setForeground(new Color(255, 255, 255));
		 JPasswordField1.setColumns(10);
		 JPasswordField1.setBackground(new Color(192, 192, 192));
		 JPasswordField1.setBounds(116, 146, 419, 64);
		 
		 TitledBorder border1 = new TitledBorder("PASSWORD");
		 border1.setTitleFont(new Font("Verdana", Font.BOLD, 12));
		 border1.setTitleColor(Color.WHITE);
		 panel.add(JPasswordField1);
	
		 // button
		 JButton btnNewButton = new JButton("LOGIN");
	        btnNewButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = textField.getText();
	                char[] passwordChars = JPasswordField1.getPassword();
	                String password = new String(passwordChars);

	                //checking if the database is online or not
	                if (isMySQLXAMPPOnline()) {
	                    login(username, password);
	                    
	                } else // else if the database is offline
	                {
	                    ImageIcon imageIcon = new ImageIcon("pic\\database.png");
	                    JOptionPane.showMessageDialog(null, "Permission denied. Please contanct the administrator", "Login Message", JOptionPane.PLAIN_MESSAGE, imageIcon);
	                }
	            }
	        });
		 
		 btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		 btnNewButton.setBackground(new Color(0, 128, 64));
		 btnNewButton.setBounds(234, 265, 150, 58);
		 panel.add(btnNewButton);
		 
		 JLabel eyelabel = new JLabel("");
		 eyelabel.setIcon(new ImageIcon("pic\\eye.png"));
		 eyelabel.setFont(new Font("Verdana", Font.BOLD, 12));
		 eyelabel.setBounds(152, 212, 72, 73);
		 panel.add(eyelabel);
		 
		 JLabel lblNewLabel_1_1_1_1 = new JLabel("Click to show password");
		 lblNewLabel_1_1_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 lblNewLabel_1_1_1_1.setBounds(224, 212, 249, 44);
		 panel.add(lblNewLabel_1_1_1_1);
		 
		 JLabel eyelabel_1 = new JLabel("");
		 eyelabel_1.setIcon(new ImageIcon("pic\\padlock.png"));
		 eyelabel_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 eyelabel_1.setBounds(44, 137, 72, 73);
		 panel.add(eyelabel_1);
		 
		 JLabel eyelabel_1_1 = new JLabel("");
		 eyelabel_1_1.setIcon(new ImageIcon("pic\\user.png"));
		 eyelabel_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 eyelabel_1_1.setBounds(44, 46, 72, 73);
		 panel.add(eyelabel_1_1);
		 
		 JLabel lblNewLabel_2 = new JLabel("USERNAME");
		 lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		 lblNewLabel_2.setBounds(116, 30, 112, 14);
		 panel.add(lblNewLabel_2);
		 
		 JLabel lblNewLabel_2_1 = new JLabel("PASSWORD");
		 lblNewLabel_2_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		 lblNewLabel_2_1.setBounds(116, 130, 112, 14);
		 panel.add(lblNewLabel_2_1);
		 
		 eyelabel.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseClicked(MouseEvent e) {
		 		 if ( JPasswordField1.getEchoChar() == '●') {
		 			 JPasswordField1.setEchoChar('\0');
		 			 eyelabel.setIcon(new ImageIcon("pic\\visible.png"));
		 	        } else {
		 	        	 JPasswordField1.setEchoChar('●');
		 	        	eyelabel.setIcon(new ImageIcon("pic\\eye.png"));
		 	        }
		 	}
		 });
		
		 
		 		
	}

	
}
