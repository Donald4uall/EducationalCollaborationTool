import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SubmitAssignmentForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Submit Assignment");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Components
        JLabel assignmentLabel = new JLabel("Select Assignment:");
        JComboBox<String> assignmentBox = new JComboBox<>();
        JLabel fileLabel = new JLabel("Upload File:");
        JTextField fileField = new JTextField();
        JButton browseButton = new JButton("Browse");
        JButton submitButton = new JButton("Submit");

        // Set Bounds for components
        assignmentLabel.setBounds(50, 50, 150, 25);
        assignmentBox.setBounds(200, 50, 200, 25);
        fileLabel.setBounds(50, 100, 150, 25);
        fileField.setBounds(200, 100, 150, 25);
        browseButton.setBounds(360, 100, 80, 25);
        submitButton.setBounds(175, 200, 150, 30);

        // Add components to the frame
        frame.add(assignmentLabel);
        frame.add(assignmentBox);
        frame.add(fileLabel);
        frame.add(fileField);
        frame.add(browseButton);
        frame.add(submitButton);

        // Set layout to null and make the frame visible
        frame.setLayout(null);
        frame.setVisible(true);

        // Populate the JComboBox with Assignments from Database
        try (Connection con = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM assignments";
            try (PreparedStatement stmt = con.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assignmentBox.addItem(rs.getString("title"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching assignments.");
        }

        // Handle file browsing
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Handle the Submit button click
        submitButton.addActionListener(e -> {
            try {
                String selectedAssignment = (String) assignmentBox.getSelectedItem();
                String filePath = fileField.getText();

                // Validate inputs
                if (selectedAssignment == null || selectedAssignment.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select an assignment.");
                    return;
                }
                if (filePath.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a file to upload.");
                    return;
                }

                // Insert submission into the database
                try (Connection conSubmit = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO submissions (assignmentID, studentID, submissionDate, grade) VALUES (?, ?, NOW(), NULL)";
                    try (PreparedStatement stmtSubmit = conSubmit.prepareStatement(query)) {
                        // Assuming studentID = 2 for this example
                        int assignmentId = getAssignmentId(selectedAssignment, conSubmit);
                        if (assignmentId == -1) {
                            JOptionPane.showMessageDialog(frame, "Assignment not found.");
                            return;
                        }
                        stmtSubmit.setInt(1, assignmentId);
                        stmtSubmit.setInt(2, 2); // Replace with dynamic student ID
                        stmtSubmit.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(frame, "Assignment Submitted Successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error submitting assignment.");
            }
        });
    }

    // Helper method to get the assignmentID by title
    private static int getAssignmentId(String title, Connection con) {
        int assignmentId = -1;
        try (PreparedStatement stmt = con.prepareStatement("SELECT assignmentID FROM assignments WHERE title = ?")) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    assignmentId = rs.getInt("assignmentID");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return assignmentId;
    }
}
