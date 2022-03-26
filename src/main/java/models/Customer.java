package models;

public class Customer extends Person{
    public Customer(Long id, String fullName, String nationalCode, String branchCode, String password, UserType userType) {
        super(id, fullName, nationalCode, branchCode, password, userType);
    }

    public Customer(String nationalCode) {
        super(nationalCode);
    }

    public Customer(String fullName, String nationalCode, String branchCode, String password, UserType userType) {
        super(fullName, nationalCode, branchCode, password, userType);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getFullName() {
        return super.getFullName();
    }

    @Override
    public void setFullName(String fullName) {
        super.setFullName(fullName);
    }

    @Override
    public String getNationalId() {
        return super.getNationalId();
    }

    @Override
    public void setNationalId(String nationalCode) {
        super.setNationalId(nationalCode);
    }

    @Override
    public String getCodeBranch() {
        return super.getCodeBranch();
    }

    @Override
    public void setCodeBranch(String branchCode) {
        super.setCodeBranch(branchCode);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public UserType getUserType() {
        return super.getUserType();
    }

    @Override
    public void setUserType(UserType userType) {
        super.setUserType(userType);
    }
}
