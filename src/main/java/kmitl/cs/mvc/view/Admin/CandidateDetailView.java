package kmitl.cs.mvc.view.Admin;

import kmitl.cs.mvc.controller.AdminController;
import kmitl.cs.mvc.controller.MainController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CandidateDetailView extends JDialog {
    private final MainController main;
    private final AdminController controller;
    private final String candidateId;
    private final DefaultTableModel model;

    public CandidateDetailView(Frame owner, MainController main, String candidateId) {
        super(owner, "Candidate Detail", true);
        this.main = main;
        this.controller = new AdminController(main);
        this.candidateId = candidateId;
        model = new DefaultTableModel(new String[]{"JobId","Title","Company","AppliedDate"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        init();
    }

    private void init() {
        setSize(700,400);
        setLocationRelativeTo(getOwner());
        JPanel p = new JPanel(new BorderLayout());
        // candidate basic info
        var candOpt = main.findCandidateById(candidateId);
        if (candOpt.isPresent()) {
            var c = candOpt.get();
            JLabel lbl = new JLabel("Candidate: " + c.getFullName() + " (" + c.getUsername() + ")");
            p.add(lbl, BorderLayout.NORTH);
        }

        JTable table = new JTable(model);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Close");
        bottom.add(btnClose);
        p.add(bottom, BorderLayout.SOUTH);
        add(p);

        btnClose.addActionListener(e -> dispose());

        // load applications sorted by date default
        loadApplications("date");
    }

    private void loadApplications(String sortBy) {
        model.setRowCount(0);
        List<Map<String,String>> apps = controller.getCandidateApplications(candidateId, sortBy);
        for (Map<String,String> m : apps) {
            model.addRow(new Object[]{ m.get("jobId"), m.get("title"), m.get("company"), m.get("appliedDate") });
        }
    }
}
