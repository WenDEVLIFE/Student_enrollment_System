package Studentenrollment;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;

import Profile.profiles;
import User.user_access;
import enrollment.enroll;
import login_form.Student_enrollment_form;

import java.awt.Color;
import java.awt.Cursor;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.Timer;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
public class mainpage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// to collect user to other jframe
	private static String username;
	static JLabel lblNewLabel1;
	static int numberOfRows;
	static int numberOfRows1;
	private JTable table_1;
	static JLabel time;
	private   DefaultTableModel model;
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
			String user = username;
			public void run() {
				try {
					
					mainpage frame2 = new mainpage(user);
					frame2.setVisible(true);
					frame2.setLocationRelativeTo(null);
					frame2.setResizable(false);
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
	
	public void mysqlteacher() throws ClassNotFoundException, SQLException {
		//mysql driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//connect to database
		Connection con = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);

		
		 Statement stmt = con.createStatement();
		 Statement stmt1 = con.createStatement();
		
		    ResultSet rs = stmt.executeQuery("SELECT id, username FROM teacher");
		    ResultSet rs1 = stmt1.executeQuery("SELECT studentid, studentname FROM student");
		    
		    // for teacher
		     numberOfRows = 0;
		     

		     
	        while (rs.next() && rs1.next()) {
	        	
	        	numberOfRows++;
	        	
	        }
	        rs.close();
	        rs1.close();
		    stmt.close();
		    con.close();
	}
	public void mysqlstudent() throws SQLException, ClassNotFoundException {
		//mysql driver
				Class.forName("com.mysql.jdbc.Driver");
				
				//connect to database
				Connection con = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);

				

				 Statement stmt1 = con.createStatement();
				
				
				    ResultSet rs1 = stmt1.executeQuery("SELECT studentid, studentname FROM student");
				    
			
				     
				     // for student
				     numberOfRows1 = 0;
				     
			        while (rs1.next()) {

			        	
			            numberOfRows1++;
			        }
			        
			        rs1.close();
				    stmt1.close();
				    con.close();
		
	}
 public void cursor(){
	 
	 mainpage frame2 = this;
    	Image cursorImage = new ImageIcon("pic//icons8-cursor-64.png").getImage();

        // Create a new Cursor object
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Custom Cursor");
		
        frame2.setCursor(cursor);
    }
 private void userole(String user) {
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
         ResultSet resultSet = statement.executeQuery("SELECT Role FROM teacher WHERE username = '" + user + "'");

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

            
	public mainpage(String username) throws ClassNotFoundException, SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\graduating-student.png"));
		userole(username);
		
		mysqlteacher();
		cursor();
		mysqlstudent();
		mainpage.username = username;
		mainpage frame2 = this;
		setTitle("Student Enrollment System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1243, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		setContentPane(contentPane);
		

	        
		JPanel panel = new JPanel();
		panel.setBounds(0, 91, 154, 765);
		panel.setBorder(new CompoundBorder());
		panel.setBackground(new Color(54, 64, 83));
		


	    
		JPanel panel_1 = new navbar();
		panel_1.setBounds(0, 0, 1222, 92);
		panel_1.setBorder(new CompoundBorder());
		panel_1.setBackground(new Color(128, 128, 128));
		
		JPanel panel_2 = new dashboard_pallet();
		panel_2.setBounds(216, 139, 437, 218);
		panel_2.setBorder(UIManager.getBorder("CheckBox.border"));
		panel_2.setBackground(new Color(192, 192, 192));
		
		JPanel panel_2_1 = new dashboard_pallet();
		panel_2_1.setBounds(706, 139, 495, 218);
		panel_2_1.setBorder(UIManager.getBorder("CheckBox.border"));
		panel_2_1.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel_2_2 = new dashboard_pallet();
		panel_2_2.setBounds(216, 402, 996, 129);
		panel_2_2.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Reports");
		lblNewLabel_1_2_1.setBounds(417, 0, 107, 64);
		lblNewLabel_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Username");
		lblNewLabel_1_2_1_1.setBounds(217, 81, 307, 48);
		lblNewLabel_1_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_1_2_1_1_1 = new JLabel("Activity");
		lblNewLabel_1_2_1_1_1.setBounds(530, 81, 107, 48);
		lblNewLabel_1_2_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("Date");
		lblNewLabel_1_2_1_1_1_1.setBounds(854, 81, 91, 48);
		lblNewLabel_1_2_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1_1_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_1_2_1_1_1_1_1 = new JLabel("ID");
		lblNewLabel_1_2_1_1_1_1_1.setBounds(39, 81, 67, 48);
		lblNewLabel_1_2_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1_1_1_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_3 = new JLabel("To");
		lblNewLabel_3.setBounds(200, 28, 14, 15);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(224, 17, 49, 38);
		
		JSpinner spinner_1_1 = new JSpinner();
		spinner_1_1.setBounds(136, 17, 54, 38);
		
		JButton btnNewButton_2 = new JButton("CLICK");
		btnNewButton_2.setBounds(283, 25, 70, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int rangeStart = (int) spinner_1_1.getValue();
		        int rangeEnd = (int) spinner_1.getValue();
		        model.setRowCount(0);
		      
		        try (Connection conn = DriverManager.getConnection(mydb_url, myDB_username, myDB_PASSWORD);
		             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reports WHERE id BETWEEN ? AND ?")) {

		            pstmt.setInt(1, rangeStart);
		            pstmt.setInt(2, rangeEnd);

		            ResultSet rs = pstmt.executeQuery();
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                String username = rs.getString("username");
		                String activity = rs.getString("activity");
		                Date date = rs.getDate("date");

		                model.addRow(new Object[]{id, username, activity, date});
		            }

		            rs.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frame2, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		
		JLabel lblNewLabel_1 = new JLabel("Teachers");
		lblNewLabel_1.setBounds(199, 87, 252, 52);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(228, 145, 183, 64);
		lblNewLabel_1_1.setText(String.valueOf(numberOfRows));
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		
		JLabel lblNewLabel_1_1_2 = new JLabel("");
		lblNewLabel_1_1_2.setBounds(221, 12, 107, 64);
		lblNewLabel_1_1_2.setIcon(new ImageIcon("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\teacher_3429433.png"));
		lblNewLabel_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_2.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		
		JLabel lblNewLabel_1_2 = new JLabel("Students");
		lblNewLabel_1_2.setBounds(138, 88, 287, 47);
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 20));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("");
		lblNewLabel_1_1_1.setBounds(175, 141, 172, 64);
		lblNewLabel_1_1_1.setText(String.valueOf(numberOfRows1));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		
		JLabel lblNewLabel_1_1_3 = new JLabel("");
		lblNewLabel_1_1_3.setBounds(163, 82, 217, 0);
		lblNewLabel_1_1_3.setIcon(new ImageIcon("pic\\students_2995620 (1).png"));
		lblNewLabel_1_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_1_3.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		
		JLabel userlabel = new JLabel(""+username);
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
						frame2.dispose();
			        } else {
			            System.out.println("No");
			        }
			}
		});
		lblLogout.setIcon(new ImageIcon("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\logout.png"));
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

		    // Create a JTable with the DefaultTableModel
		    table_1 = new JTable(model);
		    table_1.setBounds(216, 542, 996, 294);
		    table_1.setBackground(new Color(128, 128, 128));
		    table_1.getColumnModel().getColumn(2).setCellRenderer(new ColorPaletteCellRenderer());
		    table_1.setDefaultRenderer(Object.class, new ColorPaletteCellRenderer());

		    // Create a JScrollPane and add the JTable to it
		 

		    // Create a GroupLayout for your content pane
		    

		    // Close the ResultSet, Statement, and Connection objects
		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		panel_2_2.setLayout(null);
		panel_2_2.add(lblNewLabel_1_2_1_1_1_1_1);
		panel_2_2.add(spinner_1_1);
		panel_2_2.add(lblNewLabel_3);
		panel_2_2.add(spinner_1);
		panel_2_2.add(btnNewButton_2);
		panel_2_2.add(lblNewLabel_1_2_1);
		panel_2_2.add(lblNewLabel_1_2_1_1);
		panel_2_2.add(lblNewLabel_1_2_1_1_1);
		panel_2_2.add(lblNewLabel_1_2_1_1_1_1);
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
						frame2.dispose();
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
				profiles framef;
				try {
					framef = new profiles(username);
					framef.setVisible(true);
					framef.setLocationRelativeTo(null);
					framef.setResizable(false);
					frame2.dispose();
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
				user_access frameuser;
				try {
					frameuser = new user_access(username);
					frameuser.setVisible(true);
					frameuser.setLocationRelativeTo(null);
					frameuser.setResizable(false);
					setColor(panel_3_1_1_1);
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
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		panel_2.add(lblNewLabel_1_1_3);
		panel_2.add(lblNewLabel_1_2);
		panel_2.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2_1 = new JLabel("");
		lblNewLabel_1_1_2_1.setIcon(new ImageIcon("C:\\JAVA_ECLIPSE\\Student_enrollment_system\\pic\\students_2995620 (1).png"));
		lblNewLabel_1_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_2_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel_1_1_2_1.setBounds(156, 18, 71, 64);
		panel_2.add(lblNewLabel_1_1_2_1);
		contentPane.add(panel_2_1);
		panel_2_1.setLayout(null);
		panel_2_1.add(lblNewLabel_1_1_2);
		panel_2_1.add(lblNewLabel_1);
		panel_2_1.add(lblNewLabel_1_1);
		contentPane.add(panel_2_2);
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Column search");
		lblNewLabel_1_1_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_2_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		lblNewLabel_1_1_1_2_1.setBounds(20, 17, 106, 34);
		panel_2_2.add(lblNewLabel_1_1_1_2_1);
		contentPane.add(table_1);
		
		JLabel lblDashboard = new JLabel("DASHBOARD");
		lblDashboard.setForeground(Color.WHITE);
		lblDashboard.setFont(new Font("Vani", Font.PLAIN, 20));
		lblDashboard.setBounds(183, 103, 600, 34);
		contentPane.add(lblDashboard);
		
		    
		
	}
	 private void updateTime() {
	        LocalTime currentTime = LocalTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	        String formattedTime = currentTime.format(formatter);
	        time.setText("Current Time UTC: 8:00 Manila: " + formattedTime);
	    }
}
