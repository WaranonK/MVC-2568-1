package kmitl.cs.mvc.model;

import java.time.LocalDate;

public class Job {
    private String jobId;
    private String title;
    private String description;
    private String companyId;
    private LocalDate deadline;
    private boolean open;

    public Job(String jobId, String title, String description, String companyId, LocalDate deadline, boolean open) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.companyId = companyId;
        this.deadline = deadline;
        this.open = open;
    }
    //read Job from CSV
    public static Job fromCsv(String line) {
        String[] a = line.split(",", -1);
        LocalDate d = LocalDate.now();
        try {
            if (a.length > 4 && !a[4].trim().isEmpty()) d = LocalDate.parse(a[4].trim());
        } catch (Exception ex) {
            d = LocalDate.now();
        }
        boolean o = a.length>5 && "OPEN".equalsIgnoreCase(a[5].trim());
        return new Job(a[0].trim(), a.length>1 ? a[1].trim() : "",
                    a.length>2 ? a[2].trim() : "", a.length>3 ? a[3].trim() : "", d, o);
    }
    //save Job to CSV
    public String toCsv() {
        return String.join(",", jobId, title, description, companyId, deadline.toString(), open ? "OPEN" : "CLOSED");
    }
    //getters
    public String getJobId(){ return jobId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public String getCompanyId(){ return companyId; }
    public java.time.LocalDate getDeadline(){ return deadline; }
    public boolean isOpen(){ return open; }
}
