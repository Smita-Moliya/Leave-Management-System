import javax.swing.*;
import java.sql.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
public class AdminApprovalForm extends JFrame {
    JTable table;
    DefaultTableModel model;

    public AdminApprovalForm() {
        setTitle("Pending Leaves");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Leave ID", "User ID", "From", "To", "Reason", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JTextField idField = new JTextField(5);
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton refreshBtn = new JButton("Refresh");

        bottom.add(new JLabel("Leave ID:"));
        bottom.add(idField);
        bottom.add(approveBtn);
        bottom.add(rejectBtn);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> updateStatus(idField.getText(), "Approved"));
        rejectBtn.addActionListener(e -> updateStatus(idField.getText(), "Rejected"));
        refreshBtn.addActionListener(e -> loadLeaves());

        loadLeaves();
        setVisible(true);
    }

    void loadLeaves() {
        model.setRowCount(0);
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM leaves WHERE status='Pending'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("from_date"),
                    rs.getString("to_date"),
                    rs.getString("reason"),
                    rs.getString("status")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void updateStatus(String id, String status) {
        if (id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Leave ID.");
            return;
        }
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE leaves SET status=? WHERE id=?");
            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(id));
            int updated = ps.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Leave " + status);
                loadLeaves();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Leave ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
