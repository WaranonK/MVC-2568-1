package kmitl.cs.mvc.controller;

import kmitl.cs.mvc.model.*;
import kmitl.cs.mvc.view.Admin.AdminJobListView;

import java.util.*;
import java.util.stream.Collectors;

public class AdminController {
    private final MainController main;

    public AdminController(MainController main) {
        this.main = main;
    }

    public List<Candidate> listAllCandidates() {
        return main.getCandidates().stream()
                .sorted(Comparator.comparing(Candidate::getFullName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    // returns list of objects bridging application+job+company for view
    public List<Map<String,String>> getCandidateApplications(String candidateId, String sortBy) {
        List<Application> apps = main.getApplications().stream()
                .filter(a -> a.getCandidateId().equals(candidateId))
                .collect(Collectors.toList());

        List<Map<String,String>> out = new ArrayList<>();
        for (Application a : apps) {
            Optional<Job> jopt = main.findJobById(a.getJobId());
            if (jopt.isEmpty()) continue;
            Job j = jopt.get();
            Optional<Company> copt = main.findCompanyById(j.getCompanyId());
            String companyName = copt.map(Company::getName).orElse("");
            Map<String,String> m = new HashMap<>();
            m.put("jobId", j.getJobId());
            m.put("title", j.getTitle());
            m.put("company", companyName);
            m.put("appliedDate", a.getAppliedDate().toString());
            out.add(m);
        }

        // sort
        if ("title".equalsIgnoreCase(sortBy)) {
            out.sort(Comparator.comparing(m -> m.get("title"), String.CASE_INSENSITIVE_ORDER));
        } else if ("company".equalsIgnoreCase(sortBy)) {
            out.sort(Comparator.comparing(m -> m.get("company"), String.CASE_INSENSITIVE_ORDER));
        } else {
            out.sort(Comparator.comparing(m -> m.get("appliedDate")));
        }
        return out;
    }
    public void showJobList() {
        Map<String, Long> appCounts = countApplicationsPerJob();
        new AdminJobListView(main.getJobs(), main, appCounts);
    }
    // count applications per job
    public Map<String, Long> countApplicationsPerJob() {
        return main.getApplications().stream().collect(Collectors.groupingBy(Application::getJobId, Collectors.counting()));
    }
}
