package kmitl.cs.mvc.controller;


import kmitl.cs.mvc.model.Candidate;

public class AuthController {
    private final MainController main;

    public AuthController(MainController main) {
        this.main = main;
    }

    // Return candidate if login success, else null
    public Candidate login(String username, String password) {
        if (username == null || password == null) return null;
        return main.getCandidates().stream()
                .filter(c -> c.getUsername().equalsIgnoreCase(username.trim()) && password.equals(c.getPassword()))
                .findFirst().orElse(null);
    }
}
