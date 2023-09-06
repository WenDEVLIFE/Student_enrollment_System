package login_form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	 */
	public Student_enrollment_form() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("pic\\graduating-student.png"));
		setTitle("STUDENT MANAGEMENT SYSTEM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 701);
		
		//main panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		//PANEL2
		JPanel panel = new JPanel();
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
		 		System.out.println("Hello World");
		 	}
		 });
		 lblNewLabel_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 lblNewLabel_1_1.setBounds(203, 317, 249, 44);
		 panel.add(lblNewLabel_1_1);
		 
		 JLabel lblNewLabel_1_1_1 = new JLabel("ENTER YOUR LOGIN CREDENTIALS");
		 lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.BOLD, 12));
		 lblNewLabel_1_1_1.setBounds(152, 0, 249, 44);
		 panel.add(lblNewLabel_1_1_1);

		
		//textfields
		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.PLAIN, 20));
		textField.setForeground(new Color(255, 255, 255));
		textField.setBackground(new Color(128, 128, 128));
		textField.setToolTipText("");
		textField.setBounds(116, 55, 419, 64);
		panel.add(textField);
		textField.setColumns(10);
		
		 TitledBorder border = new TitledBorder("USERNAME");
		 border.setTitleFont(new Font("Verdana", Font.BOLD, 12));
		 border.setTitleColor(Color.WHITE); // Set the color of the text to red
		 textField.setBorder(border);
		 
		 JPasswordField1 = new JPasswordField();
		 JPasswordField1.setFont(new Font("Verdana", Font.PLAIN, 20));
		 JPasswordField1.setToolTipText("");
		 JPasswordField1.setForeground(new Color(255, 255, 255));
		 JPasswordField1.setColumns(10);
		 JPasswordField1.setBackground(Color.GRAY);
		 JPasswordField1.setBounds(116, 137, 419, 64);
		 
		 TitledBorder border1 = new TitledBorder("PASSWORD");
		 border1.setTitleFont(new Font("Verdana", Font.BOLD, 12));
		 border1.setTitleColor(Color.WHITE); // Set the color of the text to red
		 JPasswordField1.setBorder(border1);
		 panel.add(JPasswordField1);
	
		 // button
		 JButton btnNewButton = new JButton("LOGIN");
		 btnNewButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {
		 			
		 			//mysql driver
		 			Class.forName("com.mysql.jdbc.Driver");
		 			//connect to database
		 			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolsystem", "root", "password");
		 			
		 			//get information
		 			String username=textField.getText();
		 			char[] passwordChars = JPasswordField1.getPassword();
		 			String password = new String(passwordChars);
		 			
		 			// sql command
		 			  String sql = "SELECT * FROM teacher WHERE username = ? AND password = ?";
		 			  
		 			  //sql function
		 			 PreparedStatement preparedStatement = con.prepareStatement(sql);
		             preparedStatement.setString(1, username);
		             preparedStatement.setString(2, password);
		             ResultSet resultSet = preparedStatement.executeQuery();
		             
		             if (resultSet.next()) {
		                 // Authentication successful
		                 System.out.println("Authentication successful!");
		                 // You can retrieve other data from the resultSet if needed
		             } else {
		                 // Authentication failed
		                 System.out.println("Authentication failed!");
		             }
		             
		             // Close resources
		             resultSet.close();
		             preparedStatement.close();
		             con.close();
		             
		 		}catch (Exception e1) {
		 			  e1.printStackTrace();
		 		}
		 	}
		 });
		 
		 btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 20));
		 btnNewButton.setBackground(new Color(0, 128, 64));
		 btnNewButton.setBounds(234, 265, 150, 58);
		 panel.add(btnNewButton);
		 
		 JLabel eyelabel = new JLabel("");
		 eyelabel.setIcon(new ImageIcon("pic\\visible.png"));
		 eyelabel.setFont(new Font("Verdana", Font.BOLD, 12));
		 eyelabel.setBounds(152, 199, 72, 73);
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
