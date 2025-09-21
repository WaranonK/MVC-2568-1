package kmitl.cs.mvc.model;


import java.time.LocalDate;

public class Application {
    private String jobId;
    private String candidateId;
    private LocalDate appliedDate;

    public Application(String jobId, String candidateId, LocalDate appliedDate) {
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.appliedDate = appliedDate;
    }

    public static Application fromCsv(String line) {
        String[] a = line.split(",", -1);
        java.time.LocalDate d = java.time.LocalDate.now();
        try {
            if (a.length>2 && !a[2].trim().isEmpty()) d = java.time.LocalDate.parse(a[2].trim());
        } catch (Exception ex) { d = java.time.LocalDate.now(); }
        return new Application(a[0].trim(), a[1].trim(), d);
    }

    public String toCsv() {
        return String.join(",", jobId, candidateId, appliedDate.toString());
    }

    // getters
    public String getJobId(){ return jobId; }
    public String getCandidateId(){ return candidateId; }
    public LocalDate getAppliedDate(){ return appliedDate; }
}