import javax.swing.*;

public class TeacherDashboard {
    public static void main(String[] args) {
        // Create the frame (main window)
        JFrame frame = new JFrame("Teacher Dashboard");
        frame.setSize(600, 400); // Set the size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome, Teacher!");
        welcomeLabel.setBounds(50, 50, 200, 30);

        JButton manageAssignmentsButton = new JButton("Manage Assignments");
        manageAssignmentsButton.setBounds(50, 100, 200, 30);

        // Add components to the frame
        frame.add(welcomeLabel);
        frame.add(manageAssignmentsButton);

        // Set layout manager to null for absolute positioning
        frame.setLayout(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}
