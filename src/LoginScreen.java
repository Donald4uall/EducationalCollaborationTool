import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginScreen {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Set bounds for components
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameField.setBounds(150, 50, 200, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        passwordField.setBounds(150, 100, 200, 30);
        loginButton.setBounds(150, 150, 100, 30);

        // Add components to frame
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.setLayout(null);
        frame.setVisible(true);

        // Add event listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Database authentication logic
                try {
                    Connection con = DatabaseConnection.getConnection(); // Call the getConnection method
                    String query = "SELECT role FROM users WHERE name=? AND password=?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, username); // Replace the first '?' with the username
                    stmt.setString(2, password); // Replace the second '?' with the password

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String role = rs.getString("role"); // Get the role of the user
                        JOptionPane.showMessageDialog(frame, "Login Successful!");

                        // Redirect to dashboards based on role
                        if (role.equals("Teacher")) {
                            frame.dispose(); // Close the login frame
                            TeacherDashboard.main(new String[]{}); // Open Teacher Dashboard
                        } else if (role.equals("Student")) {
                            frame.dispose();
                            StudentDashboard.main(new String[]{}); // Open Student Dashboard
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Credentials. Please try again.");
                    }

                    con.close(); // Close the database connection
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
