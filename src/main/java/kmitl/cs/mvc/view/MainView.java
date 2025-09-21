package kmitl.cs.mvc.view;

import kmitl.cs.mvc.controller.MainController;
import kmitl.cs.mvc.model.Candidate;
import kmitl.cs.mvc.view.Admin.CandidateListView;
import kmitl.cs.mvc.view.Candidate.JobView;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final MainController main;

    public MainView(MainController main) {
        this.main = main;
        init();
    }

    private void init() {
        Candidate cur = main.getCurrentUser();
        setTitle("JobFair - Main (" + (cur!=null?cur.getFullName():"") + ")");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (cur != null && cur.isAdmin()) {
            // admin -> show candidate list
            CandidateListView cv = new CandidateListView(main);
            add(cv, BorderLayout.CENTER);
        } else {
            JobView jv = new JobView(main);
            add(jv, BorderLayout.CENTER);
        }
    }
}