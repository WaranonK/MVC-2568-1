package kmitl.cs.mvc.view.Admin;

import kmitl.cs.mvc.controller.AdminController;
import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.Candidate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class CandidateListView extends JPanel {
    private final MainController main;
    private final AdminController controller;
    private final DefaultTableModel model;
    private final JTable table;

    public CandidateListView(MainController main) {
        this.main = main;
        this.controller = new AdminController(main);
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"รหัสผู้สมัคร","ชื่อผู้สมัคร","ชื่อผู้ใช้งาน","อีเมล"}, 0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        refresh();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel สำหรับปุ่มต่าง ๆ
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnView = new JButton("ดูรายละเอียดผู้สมัคร");
        JButton btnJobs = new JButton("ดูตำแหน่งงาน");

        btnPanel.add(btnView);
        btnPanel.add(btnJobs);
        add(btnPanel, BorderLayout.SOUTH);

        // กดดูรายละเอียดผู้สมัคร
        btnView.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r<0) {
                JOptionPane.showMessageDialog(this,"กรุณาเลือกผู้สมัคร");
                return;
            }
            String candidateId = (String) model.getValueAt(r,0);
            CandidateDetailView detail = new CandidateDetailView(
                    (Frame)SwingUtilities.getWindowAncestor(this), main, candidateId);
            detail.setVisible(true);
        });

        // กดไปหน้าดูตำแหน่งงาน
        btnJobs.addActionListener(e -> {
            controller.showJobList();  // เรียกไปเปิด AdminJobListView
        });

    }

    public void refresh() {
        model.setRowCount(0);
        List<Candidate> list = controller.listAllCandidates();
        for (Candidate c : list) {
            Vector<String> row = new Vector<>();
            row.add(c.getCandidateId());
            row.add(c.getFullName());
            row.add(c.getUsername());
            row.add(c.getEmail());
            model.addRow(row);
        }
    }
}