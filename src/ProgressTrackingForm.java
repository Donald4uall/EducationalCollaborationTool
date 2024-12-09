import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProgressTrackingForm {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Progress Tracking");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define column names for the table
        String[] columnNames = {"Assignment", "Grade"};

        // Create a table model and set it to the JTable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 500, 300);

        // Add the scroll pane to the frame
        frame.add(scrollPane);

        // Set the layout and make the frame visible
        frame.setLayout(null);
        frame.setVisible(true);

        // Fetch data from the database and populate the table
        fetchProgressData(model);
    }

    // Method to fetch progress data from the database
    private static void fetchProgressData(DefaultTableModel model) {
        try {
            // Establish connection to the database
            Connection con = DatabaseConnection.getConnection();

            // Query to retrieve assignment grades for a specific student
            String query = """
                    SELECT assignments.title AS Assignment, 
                           submissions.grade AS Grade 
                    FROM assignments 
                    JOIN submissions ON assignments.assignmentID = submissions.assignmentID 
                    WHERE submissions.studentID = ?;
                    """;

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, 2); // Example studentID (replace with dynamic ID if needed)

            ResultSet rs = stmt.executeQuery();

            // Add each row of data to the table model
            while (rs.next()) {
                String assignment = rs.getString("Assignment");
                int grade = rs.getInt("Grade");
                model.addRow(new Object[]{assignment, grade});
            }

            // Close the connection
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching progress data.");
        }
    }
}
