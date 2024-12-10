import javax.swing.*;

public class StudentDashboard {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Student Dashboard");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome, Student!");
        welcomeLabel.setBounds(50, 50, 200, 30);

        JButton viewAssignmentsButton = new JButton("View Assignments");
        viewAssignmentsButton.setBounds(50, 100, 200, 30);

        // Add components to the frame
        frame.add(welcomeLabel);
        frame.add(viewAssignmentsButton);

        // Set layout and visibility
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
