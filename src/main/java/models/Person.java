package models;

public class Person {
    private Long id;
    private String fullName;
    private String nationalCode;
    private String branchCode;
    private String password;
    private UserType userType;

    public Person(Long id, String fullName, String nationalCode, String branchCode, String password, UserType userType) {
        this.id = id;
        this.fullName = fullName;
        this.nationalCode = nationalCode;
        this.branchCode = branchCode;
        this.password = password;
        this.userType = userType;
    }

    public Person(String fullName, String nationalCode, String branchCode, String password, UserType userType) {
        this.fullName = fullName;
        this.nationalCode = nationalCode;
        this.branchCode = branchCode;
        this.password = password;
        this.userType = userType;
    }

    public Person(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return nationalCode;
    }

    public void setNationalId(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getCodeBranch() {
        return branchCode;
    }

    public void setCodeBranch(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
