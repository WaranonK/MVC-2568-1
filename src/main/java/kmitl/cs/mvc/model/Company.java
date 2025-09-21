package kmitl.cs.mvc.model;

public class Company {
    private String companyId;
    private String name;
    private String email;
    private String location;

    public Company(String companyId, String name, String email, String location) {
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.location = location;
    }
    //read company from csv
    public static Company fromCsv(String line) {
        String[] a = line.split(",", -1);
        return new Company(a[0].trim(), a.length>1 ? a[1].trim() : "",
                a.length>2 ? a[2].trim() : "", a.length>3 ? a[3].trim() : "");
    }

    public String toCsv() {
        return String.join(",", companyId, name, email, location);
    }

    // getters
    public String getCompanyId(){ return companyId; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getLocation(){ return location; }
}
