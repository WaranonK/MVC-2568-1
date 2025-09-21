package kmitl.cs.mvc.controller;

import kmitl.cs.mvc.model.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private List<Company> companies = new ArrayList<>();
    private List<Job> jobs = new ArrayList<>();
    private List<Candidate> candidates = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();

    private Candidate currentUser; // session

    // CSV paths
    public static final String COMPANIES_CSV = "data/companies.csv";
    public static final String JOBS_CSV = "data/jobs.csv";
    public static final String CANDIDATES_CSV = "data/candidates.csv";
    public static final String APPLICATIONS_CSV = "data/applications.csv";

    public void loadAll() throws IOException {
        companies.clear(); jobs.clear(); candidates.clear(); applications.clear();
        CsvUtil.readAll(COMPANIES_CSV).forEach(l -> { if(!l.trim().isEmpty()) companies.add(Company.fromCsv(l)); });
        CsvUtil.readAll(JOBS_CSV).forEach(l -> { if(!l.trim().isEmpty()) jobs.add(Job.fromCsv(l)); });
        CsvUtil.readAll(CANDIDATES_CSV).forEach(l -> { if(!l.trim().isEmpty()) candidates.add(Candidate.fromCsv(l)); });
        CsvUtil.readAll(APPLICATIONS_CSV).forEach(l -> { if(!l.trim().isEmpty()) applications.add(Application.fromCsv(l)); });
    }

    public List<Company> getCompanies(){ return companies; }
    public List<Job> getJobs(){ return jobs; }
    public List<Candidate> getCandidates(){ return candidates; }
    public List<Application> getApplications(){ return applications; }

    public void setCurrentUser(Candidate user){ this.currentUser = user; }
    public Candidate getCurrentUser(){ return currentUser; }

    public Optional<Job> findJobById(String id) {
        return jobs.stream().filter(j -> j.getJobId().equals(id)).findFirst();
    }

    public Optional<Candidate> findCandidateById(String id) {
        return candidates.stream().filter(c -> c.getCandidateId().equals(id)).findFirst();
    }

    public Optional<Candidate> findCandidateByUsername(String username) {
        return candidates.stream().filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    public Optional<Company> findCompanyById(String id) {
        return companies.stream().filter(c -> c.getCompanyId().equals(id)).findFirst();
    }

    public void saveApplications() throws IOException {
        List<String> lines = applications.stream().map(Application::toCsv).collect(Collectors.toList());
        CsvUtil.writeAll(APPLICATIONS_CSV, lines);
    }

    // add application to list and persist
    public void addApplication(Application a) throws IOException {
        applications.add(a);
        saveApplications();
    }
}
