import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddAssignmentForm {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Add Assignment");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();
        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dueDateField = new JTextField();
        JButton submitButton = new JButton("Add Assignment");

        // Set bounds for components
        titleLabel.setBounds(50, 50, 150, 25);
        titleField.setBounds(200, 50, 150, 25);
        descLabel.setBounds(50, 100, 150, 25);
        descField.setBounds(200, 100, 150, 25);
        dueDateLabel.setBounds(50, 150, 150, 25);
        dueDateField.setBounds(200, 150, 150, 25);
        submitButton.setBounds(150, 200, 150, 30);

        // Add components to the frame
        frame.add(titleLabel);
        frame.add(titleField);
        frame.add(descLabel);
        frame.add(descField);
        frame.add(dueDateLabel);
        frame.add(dueDateField);
        frame.add(submitButton);

        // Set layout to null and make the frame visible
        frame.setLayout(null);
        frame.setVisible(true);

        // Add action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descField.getText();
                String dueDate = dueDateField.getText();

                // Validate input
                if (title.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                    return;
                }

                // JDBC logic to insert the assignment into the database
                try {
                    Connection con = DatabaseConnection.getConnection(); // Ensure this method exists
                    String query = "INSERT INTO assignments (title, description, dueDate, teacherID) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = con.prepareStatement(query);

                    stmt.setString(1, title);
                    stmt.setString(2, description);
                    stmt.setString(3, dueDate);
                    stmt.setInt(4, 1); // Replace 1 with the teacher's ID if dynamically available

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Assignment added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add assignment.");
                    }

                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
    }
}
