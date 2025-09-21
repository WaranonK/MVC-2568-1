package kmitl.cs.mvc.view.Candidate;

import kmitl.cs.mvc.controller.CandidateController;
import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.Candidate;
import kmitl.cs.mvc.model.Job;

import javax.swing.*;
import java.awt.*;

public class ApplyJobView extends JDialog {
    private final MainController main;
    private final CandidateController controller;
    private final String jobId;

    public ApplyJobView(Window owner, MainController main, String jobId) {
        super(owner, "สมัครเข้าตำแหน่งงาน", ModalityType.APPLICATION_MODAL);
        this.main = main;
        this.controller = new CandidateController(main);
        this.jobId = jobId;
        init();
    }

    private void init() {
        setSize(400,250);
        setLocationRelativeTo(getOwner());
        Job job = main.findJobById(jobId).orElse(null);
        Candidate cur = main.getCurrentUser();

        JPanel p = new JPanel(new BorderLayout(8,8));
        if (job != null) {
            JTextArea info = new JTextArea("ตำแหน่ง: " + job.getTitle() + "\n" +
                    "บริษัท: " + main.findCompanyById(job.getCompanyId()).map(c->c.getName()).orElse("") + "\n" +
                    "รายละเอียด: " + job.getDescription() + "\n" +
                    "วันสุดท้ายสมัคร: " + job.getDeadline().toString());
            info.setEditable(false);
            p.add(new JScrollPane(info), BorderLayout.CENTER);
        } else {
            p.add(new JLabel("ไม่พบตำแหน่งงาน"), BorderLayout.CENTER);
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirm = new JButton("ยืนยันการสมัคร");
        JButton btnCancel = new JButton("ยกเลิกการสมัคร");
        buttons.add(btnConfirm); buttons.add(btnCancel);
        p.add(buttons, BorderLayout.SOUTH);
        add(p);

        btnConfirm.addActionListener(e -> {
            if (cur == null) { JOptionPane.showMessageDialog(this, "ไม่พบผู้ใช้งาน"); return; }
            String res = controller.applyToJob(jobId, cur.getCandidateId());
            JOptionPane.showMessageDialog(this, res);
            if (res.equals("สมัครสำเร็จ")) {
                dispose(); // close and return to jobs view (business rule)
            }
        });
        btnCancel.addActionListener(e -> dispose());
    }
}
