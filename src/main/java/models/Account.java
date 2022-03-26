package models;

public class Account {
    private Long id;
    private String branchCode;
    private String nationalCode;
    private String accountNumber;
    private Double balance;
    private AccountType accountType;

    public Account(Long id, String branchCode, String nationalCode, String accountNumber, Double balance, AccountType accountType) {
        this.id = id;
        this.branchCode = branchCode;
        this.nationalCode = nationalCode;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account(String branchCode, String nationalCode, String accountNumber, Double balance, AccountType accountType) {
        this.branchCode = branchCode;
        this.nationalCode = nationalCode;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    public Account(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


    @Override
    public String toString() {
        return "--------------------" +
                "\nid = " + id +
                "\nbranchCode = " + branchCode +
                "\nnationalCode = " + nationalCode +
                "\naccountNumber = " + accountNumber +
                "\nbalance = " + balance +
                "\naccountType = " + accountType +
                "\n--------------------";
    }
}
