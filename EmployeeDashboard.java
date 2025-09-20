import javax.swing.*;

public class EmployeeDashboard extends JFrame {
    int userId;

    public EmployeeDashboard(int userId) {
        this.userId = userId;
        setTitle("Employee Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel welcome = new JLabel("Welcome Employee ID: " + userId);
        welcome.setBounds(30, 10, 300, 20);
        add(welcome);

        JButton applyLeaveBtn = new JButton("Apply for Leave");
        JButton viewHistoryBtn = new JButton("View Leave History");
        JButton logoutBtn = new JButton("Logout");

        applyLeaveBtn.setBounds(100, 50, 180, 30);
        viewHistoryBtn.setBounds(100, 100, 180, 30);
        logoutBtn.setBounds(100, 150, 180, 30);

        applyLeaveBtn.addActionListener(e -> new ApplyLeaveForm(userId));
        viewHistoryBtn.addActionListener(e -> new LeaveHistoryForm(userId));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LoginForm();
        });

        add(applyLeaveBtn);
        add(viewHistoryBtn);
        add(logoutBtn);

        setVisible(true);
    }
}
