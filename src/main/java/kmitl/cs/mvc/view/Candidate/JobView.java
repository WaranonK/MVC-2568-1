package kmitl.cs.mvc.view.Candidate;

import kmitl.cs.mvc.controller.CandidateController;
import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.Company;
import kmitl.cs.mvc.model.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class JobView extends JPanel {
    private final MainController main;
    private final CandidateController controller;
    private final JTable table;
    private final DefaultTableModel model;

    public JobView(MainController main) {
        this.main = main;
        this.controller = new CandidateController(main);
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"JobId","Title","Company","Deadline","Action"},0) {
            public boolean isCellEditable(int row,int col){ return false; }
        };
        table = new JTable(model);
        refreshTable();

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JButton btnApply = new JButton("Apply Selected");
        add(btnApply, BorderLayout.SOUTH);

        btnApply.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) { JOptionPane.showMessageDialog(this,"กรุณาเลือกตำแหน่งก่อน"); return; }
            String jobId = (String) model.getValueAt(r,0);
            // open apply dialog
            ApplyJobView dialog = new ApplyJobView(SwingUtilities.getWindowAncestor(this), main, jobId);
            dialog.setVisible(true);
            refreshTable(); // refresh after return
        });
    }

    public void refreshTable() {
        model.setRowCount(0);
        List<Job> jobs = controller.getOpenJobs();
        for (Job j : jobs) {
            String companyName = main.findCompanyById(j.getCompanyId()).map(Company::getName).orElse("");
            Vector<String> row = new Vector<>();
            row.add(j.getJobId());
            row.add(j.getTitle());
            row.add(companyName);
            row.add(j.getDeadline().toString());
            row.add("Apply");
            model.addRow(row);
        }
    }
}
