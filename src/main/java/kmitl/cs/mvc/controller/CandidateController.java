package kmitl.cs.mvc.controller;

import kmitl.cs.mvc.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CandidateController {
    private final MainController main;

    public CandidateController(MainController main) {
        this.main = main;
    }

    public List<Job> getOpenJobs() {
        return main.getJobs().stream().filter(Job::isOpen).collect(Collectors.toList());
    }

    // apply: returns message
    public String applyToJob(String jobId, String candidateId) {
        var jobOpt = main.findJobById(jobId);
        if (jobOpt.isEmpty()) return "ไม่พบตำแหน่งงาน";
        Job job = jobOpt.get();
        LocalDate today = LocalDate.now();
        if (today.isAfter(job.getDeadline())) return "ไม่สามารถสมัครได้ – ปิดรับสมัครแล้ว";
        // check duplicate
        boolean already = main.getApplications().stream()
                .anyMatch(a -> a.getJobId().equals(jobId) && a.getCandidateId().equals(candidateId));
        if (already) return "คุณสมัครตำแหน่งนี้ไปแล้ว";
        Application app = new Application(jobId, candidateId, today);
        try {
            main.addApplication(app);
            return "สมัครสำเร็จ";
        } catch (IOException e) {
            e.printStackTrace();
            return "เกิดข้อผิดพลาดขณะบันทึกใบสมัคร";
        }
    }
}