package kmitl.cs.mvc.controller;


import kmitl.cs.mvc.model.Candidate;
import kmitl.cs.mvc.model.Validation;

public class AuthController {
    private final MainController main;

    public AuthController(MainController main) {
        this.main = main;
    }

    // login by email + password, return Candidate if success, else null
    public Candidate loginByEmail(String email, String password) {
        if (email == null || password == null) return null;
        String norm = Validation.normalizeEmail(email);
        if (!Validation.isValidEmail(norm)) return null;

        return main.getCandidates().stream()
                .filter(c -> {
                    String cEmail = c.getEmail()==null? "": c.getEmail().trim().toLowerCase();
                    return cEmail.equals(norm) && password.equals(c.getPassword());
                })
                .findFirst().orElse(null);
    }
}
