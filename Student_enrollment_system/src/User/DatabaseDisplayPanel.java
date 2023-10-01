package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class DatabaseDisplayPanel extends JPanel {

    private static final long serialVersionUID = 1L;
	private static final int imageIcon1 = 0;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DatabaseDisplayPanel() {
        super();

        setBackground(new Color(54, 64, 83));
        setLayout(new BorderLayout()); // Use BorderLayout to allow vertical scrolling

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new GridLayout(1, 2));
        headerPanel.setBackground(new Color(54, 64, 83));

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(nameLabel);

        JLabel rolesLabel = new JLabel("Roles");
        rolesLabel.setForeground(Color.WHITE);
        rolesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(rolesLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Connect to the database.
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolsystem", "root", "");
            statement = connection.createStatement();

            // Query the database for the values.
            resultSet = statement.executeQuery("SELECT username, Role FROM teacher");

            // Create a JPanel to hold the rows
            JPanel rowsPanel = new JPanel();
            rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
            rowsPanel.setBackground(new Color(54, 64, 83));

            // Display the values in the JPanel.
            while (resultSet.next()) { // Display all rows from the database
                String username = resultSet.getString("username");
                String role = resultSet.getString("Role");

                // Create a panel for each row
                JPanel rowPanel = new JPanel(new GridLayout(1, 3));
                rowPanel.setBackground(new Color(54, 64, 83));

                JLabel nameLabelForRow = new JLabel(username);
                nameLabelForRow.setForeground(Color.WHITE);
                nameLabelForRow.setFont(new Font("Arial", Font.PLAIN, 14));
                rowPanel.add(nameLabelForRow);

                JLabel rolesLabelForRow = new JLabel(role);
                rolesLabelForRow.setForeground(Color.WHITE);
                rolesLabelForRow.setFont(new Font("Arial", Font.PLAIN, 14));
                rowPanel.add(rolesLabelForRow);
              
                JButton deleteButton = new JButton("Delete");
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setBackground(new Color(255, 0 ,0));
                deleteButton.addActionListener(e -> {
                	ImageIcon imageIcon1 = new ImageIcon("pic//icons8-delete-64.png");
                	int response = JOptionPane.showConfirmDialog(
                	    null,
                	    "Do you want to delete this user?",
                	    "Confirmation",
                	    JOptionPane.YES_NO_OPTION,
                	    JOptionPane.QUESTION_MESSAGE, // You can use QUESTION_MESSAGE to display a question icon
                	    imageIcon1
                	);

                        if (response == JOptionPane.YES_OPTION) {
                            System.out.println("User clicked 'Yes'");
                            // Perform the action for 'Yes'
                            System.out.println("User clicked 'No'");
                            // Perform the action for 'No' or just close the dialog
                            // Delete the selected teacher from the database.
                        	deleteTeacher(username); // Call the delete method
                            rowPanel.setVisible(false); // Hide the row after deletion
                        } else if (response == JOptionPane.NO_OPTION) {
                           
                        } else {
                            System.out.println("User closed the dialog or clicked the close button");
                            // Handle other cases (e.g., dialog close button or Esc key)
                        }
                   
                });
                rowPanel.add(deleteButton);

                rowsPanel.add(rowPanel);
            }

            // Wrap the rowsPanel in a JScrollPane and allow vertical scrolling
            JScrollPane scrollPane = new JScrollPane(rowsPanel);
            add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTeacher(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}