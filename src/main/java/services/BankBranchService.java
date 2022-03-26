package services;

import models.BankBranch;
import models.UserType;
import repositpries.BankBranchRepository;
import repositpries.BankRepository;
import services.exceptions.InvalidBranchCode;
import services.exceptions.InvalidName;
import services.exceptions.InvalidNationalCode;
import services.exceptions.InvalidPassword;

import java.sql.SQLException;
import java.util.Scanner;

public class BankBranchService {
    private Scanner scanner = new Scanner(System.in);
    private BankBranchRepository bankBranchRepository = new BankBranchRepository();
    private BankRepository bankRepository = new BankRepository();
    private InvalidBranchCode invalidBranchCode = new InvalidBranchCode();
    private InvalidName invalidName = new InvalidName();
    private InvalidNationalCode invalidNationalCode = new InvalidNationalCode();
    private InvalidPassword invalidPassword = new InvalidPassword();
    private LoginService loginService = new LoginService();
    private String bankName,branchCode,bossFullName,nationalCode,password;
    private Utility utility = new Utility();

    public BankBranchService() throws SQLException, ClassNotFoundException {
    }

    public void addBankBranch() throws SQLException, ClassNotFoundException {
        System.out.println("Bank name : ");
        bankName = scanner.next();
        if(bankRepository.readByName(bankName) == null){
            System.out.println("-------------------------\nThis bank doesn't exist!\n-------------------------");
            addBankBranch();
        }
        branchCode = utility.setBranchCode();
        BankBranch bankBranch = new BankBranch(branchCode);
        if(bankBranchRepository.readByBranchCode(bankBranch) != null){
            System.out.println("----------------------------------\nThis branch code already exists!\n----------------------------------");
            addBankBranch();
        }
        while(true){
            System.out.println("Boss full name (without number) : ");
            try {
                bossFullName = scanner.next();
                invalidName.checkName(bossFullName);
                break;
            }catch (InvalidName i){
                System.out.println(i.getMessage());
            }
        }
        while(true){
            nationalCode = utility.setNationalCode();
            if( loginService.findByNationalCode(nationalCode) != null ) {
                System.out.println("------------------------------------\nThis national code already exists!\n------------------------------------");
                addBankBranch();
            }
            else
                break;
        }
        password = utility.setPassword();
        loginService.addNewLogin(nationalCode,password, UserType.BOSS);
        BankBranch newBankBranch = new BankBranch(bankName,branchCode,bossFullName,nationalCode,password);
        bankBranchRepository.save(newBankBranch);
        System.out.println("-------------------------------------------------------\nBranch added successfully and branch code is : " + branchCode + "\n-------------------------------------------------------");
    }

    public String findCodeBranch(String nationalCode) throws SQLException {
        return bankBranchRepository.findCodeBranch(nationalCode);
    }
}

