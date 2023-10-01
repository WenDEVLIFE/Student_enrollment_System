package admin_Registration;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;

import login_form.Student_enrollment_form;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Admin_sign_up extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private final String DB_URL = "jdbc:mysql://localhost:3306/schoolsystem";
	private String DB_USER = "root";
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
					Admin_sign_up framesignup = new Admin_sign_up();
					framesignup.setVisible(true);
					framesignup.setLocationRelativeTo(null);
					framesignup.setResizable(false);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	// lengh verification
	public boolean verifyPasswordLength(String password) {
	    int length = password.length();
	    return length >= 8 && length <= 12;
	}
	 public void cursors() {
		  Admin_sign_up framesignup = this;
			// customize cursor
			 Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

		        // Create a new Cursor object
		        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
				
		        framesignup.setCursor(cursor);
	  }
	 public void AddUser() throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		    String username = textField.getText();
		    String password = new String(passwordField.getPassword());

		    // Generate a random salt for each user
		    byte[] salt = generateSalt();

		    // Hash the password using PBKDF2
		    String hashedPassword = hashPassword(password, salt);
		    String role = "Teacher";

		    Class.forName("com.mysql.cj.jdbc.Driver");

		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

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
		                textField.setText("");
		                passwordField.setText("");
		                passwordField_1.setText("");
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to add user.");
		            }
		        }
		    }
		}

	
	// to generate salt characters
	private byte[] generateSalt() {
	    SecureRandom random = new SecureRandom();
	    byte[] salt = new byte[16]; // You can adjust the salt size as needed
	    random.nextBytes(salt);
	    return salt;
	}
	// MD5 function 1
	private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    int iterations = 10000; // Number of iterations
	    int keyLength = 256; // Key length in bits

	    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    byte[] hashedPassword = factory.generateSecret(spec).getEncoded();

	    return bytesToHex(hashedPassword);
	}

	// MD5 function 2
	private String bytesToHex(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder();
	    for (byte aByte : bytes) {
	        hexString.append(String.format("%02x", aByte));
	    }
	    return hexString.toString();
	}

	
	//check if user is exist
	public boolean doesUsernameExist(String username) throws SQLException, ClassNotFoundException {
	    Class.forName("com.mysql.cj.jdbc.Driver");

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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
	
	public Admin_sign_up() {
		cursors();
		Admin_sign_up framesignup = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\graduating-student.png"));
		setTitle("TEACHER SIGN UP FORM");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new GRADIENTCOLORS();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 233, 584, 428);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("SIGN UP FORM");
		lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(245, 0, 249, 44);
		panel.add(lblNewLabel_1_1_1);
		
		textField = new JTextField();
		textField.setToolTipText("");
		textField.setForeground(Color.WHITE);
		textField.setFont(new Font("Verdana", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setBackground(new Color(192, 192, 192));
		textField.setBounds(112, 55, 419, 64);
		panel.add(textField);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("");
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 20));
		passwordField.setColumns(10);
		passwordField.setBackground(new Color(192, 192, 192));
		passwordField.setBounds(112, 146, 419, 64);
		panel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setToolTipText("");
		passwordField_1.setForeground(Color.WHITE);
		passwordField_1.setFont(new Font("Verdana", Font.PLAIN, 20));
		passwordField_1.setColumns(10);
		passwordField_1.setBackground(new Color(192, 192, 192));
		passwordField_1.setBounds(112, 246, 419, 64);
		panel.add(passwordField_1);
		
		JButton btnSignup = new JButton("SIGNUP");
		btnSignup.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				  String username = textField.getText();
				  String password1 = new String(passwordField.getPassword());
				  String password2 = new String(passwordField_1.getPassword());

				  if (password1.equals(password2)) {
				    if (verifyPasswordLength(password1)) {
				      boolean hasCapsLock = false;
				      boolean hasSpecialCharacters = false;

				      for (char character : password1.toCharArray()) {
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
		
		btnSignup.setFont(new Font("Verdana", Font.PLAIN, 20));
		btnSignup.setBackground(new Color(0, 128, 64));
		btnSignup.setBounds(121, 359, 150, 58);
		panel.add(btnSignup);
		
		JLabel eyelabel = new JLabel("");
		eyelabel.setFont(new Font("Verdana", Font.BOLD, 12));
		eyelabel.setBounds(152, 199, 72, 73);
		panel.add(eyelabel);
		
		JLabel eyelabel_1 = new JLabel("");
		eyelabel_1.setIcon(new ImageIcon("pic\\padlock.png"));
		eyelabel_1.setFont(new Font("Verdana", Font.BOLD, 12));
		eyelabel_1.setBounds(31, 137, 72, 73);
		panel.add(eyelabel_1);
		
		JLabel eyelabel_1_1 = new JLabel("");
		eyelabel_1_1.setIcon(new ImageIcon("pic\\user.png"));
		eyelabel_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		eyelabel_1_1.setBounds(31, 55, 72, 73);
		panel.add(eyelabel_1_1);
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student_enrollment_form frame1 = new Student_enrollment_form();
				frame1.setVisible(true);
				frame1.setResizable(false);
				frame1.setLocationRelativeTo(null);
				framesignup.dispose();
			}
		});
		btnGoBack.setFont(new Font("Verdana", Font.PLAIN, 20));
		btnGoBack.setBackground(new Color(0, 128, 64));
		btnGoBack.setBounds(359, 359, 150, 58);
		panel.add(btnGoBack);
		
		
		
		JLabel eyelabel_1_2 = new JLabel("");
		eyelabel_1_2.setIcon(new ImageIcon("pic\\padlock.png"));
		eyelabel_1_2.setFont(new Font("Verdana", Font.BOLD, 12));
		eyelabel_1_2.setBounds(31, 231, 72, 73);
		panel.add(eyelabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("USERNAME");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(116, 30, 112, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("PASSWORD");
		lblNewLabel_2_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(112, 130, 112, 14);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("CONFIRMATION PASSWORD");
		lblNewLabel_2_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_2_2.setBounds(116, 221, 180, 14);
		panel.add(lblNewLabel_2_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox(" click to check to see the password");
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 11));
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
		
		chckbxNewCheckBox.setBounds(112, 317, 235, 23);
		panel.add(chckbxNewCheckBox);
		

		ImageIcon originalIcon = new ImageIcon("pic\\kenny-eliason-zFSo6bnZJTw-unsplash.jpg");

        // Get the original image
        java.awt.Image originalImage = originalIcon.getImage();

        // Resize the image to the desired dimensions
        int width = 1000; // Desired width
        int height = 300; // Desired height
        java.awt.Image resizedImage = originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a JLabel to display the resized image
        JLabel label = new JLabel(resizedIcon);
		
        label.setBounds(-48, -12, 687, 310);
		contentPane.add(label);
		
		
	
	}
}
