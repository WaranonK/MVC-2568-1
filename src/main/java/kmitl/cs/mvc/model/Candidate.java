package kmitl.cs.mvc.model;

public class Candidate {
    private String candidateId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isAdmin;

    public Candidate(String candidateId, String username, String password, String firstName, String lastName, String email, boolean isAdmin) {
        this.candidateId = candidateId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public static Candidate fromCsv(String line) {
        String[] a = line.split(",", -1);
        boolean admin = a.length>6 && "true".equalsIgnoreCase(a[6].trim());
        String pwd = a.length>2 ? a[2].trim() : "";
        return new Candidate(a[0].trim(), a.length>1 ? a[1].trim() : "", pwd,
                a.length>3 ? a[3].trim() : "", a.length>4 ? a[4].trim() : "", a.length>5 ? a[5].trim() : "", admin);
    }

    public String toCsv() {
        return String.join(",", candidateId, username, password, firstName, lastName, email, Boolean.toString(isAdmin));
    }

    // getters
    public String getCandidateId(){ return candidateId; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public String getFullName(){ return firstName + " " + lastName; }
    public String getEmail(){ return email; }
    public boolean isAdmin(){ return isAdmin; }
}
