package models;

public class BankBranch {
    private Long id;
    private String bankName;
    private String branchCode;
    private String bossFullName;
    private String nationalCode;
    private String password;

    public BankBranch(Long id, String bankName, String branchCode, String bossFullName, String nationalCode, String password) {
        this.id = id;
        this.bankName = bankName;
        this.branchCode = branchCode;
        this.bossFullName = bossFullName;
        this.nationalCode = nationalCode;
        this.password = password;
    }

    public BankBranch() {
    }

    public BankBranch(String bankName, String branchCode, String bossFullName, String nationalCode, String password) {
        this.bankName = bankName;
        this.branchCode = branchCode;
        this.bossFullName = bossFullName;
        this.nationalCode = nationalCode;
        this.password = password;
    }

    public BankBranch(String branchCode) {
        this.branchCode = branchCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBossFullName() {
        return bossFullName;
    }

    public void setBossFullName(String bossFullName) {
        this.bossFullName = bossFullName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
