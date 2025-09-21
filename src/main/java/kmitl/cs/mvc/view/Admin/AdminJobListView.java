package kmitl.cs.mvc.view.Admin;

import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AdminJobListView extends JFrame {
    public AdminJobListView(List<Job> jobs, MainController main, Map<String, Long> appCounts) {
        setTitle("รายการตำแหน่งงาน (แอดมิน)");
        setSize(800, 400);
        setLocationRelativeTo(null);

        String[] cols = {"รหัสงาน", "ชื่อตำแหน่ง", "บริษัท", "วันปิดรับสมัคร", "จำนวนผู้สมัคร"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Job j : jobs) {
            String companyName = main.findCompanyById(j.getCompanyId())
                    .map(Company::getName).orElse("N/A");
            long count = appCounts.getOrDefault(j.getJobId(), 0L);

            model.addRow(new Object[]{
                    j.getJobId(),
                    j.getTitle(),
                    companyName,
                    j.getDeadline(),
                    count
            });
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }
}

