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
        model = new DefaultTableModel(new String[]{"CandidateId","Name","Username","Email"}, 0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        refresh();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnView = new JButton("View Details");
        add(btnView, BorderLayout.SOUTH);
        btnView.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r<0) { JOptionPane.showMessageDialog(this,"Select a candidate"); return; }
            String candidateId = (String) model.getValueAt(r,0);
            CandidateDetailView detail = new CandidateDetailView((Frame)SwingUtilities.getWindowAncestor(this), main, candidateId);
            detail.setVisible(true);
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