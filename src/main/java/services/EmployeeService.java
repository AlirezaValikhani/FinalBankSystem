package services;

import models.Employee;
import models.UserType;
import repositpries.EmployeeRepository;
import services.exceptions.InvalidName;
import services.exceptions.InvalidNationalCode;
import services.exceptions.InvalidPassword;

import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeService {
    private Scanner scanner = new Scanner(System.in);
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private BankBranchService bankBranchService = new BankBranchService();
    private String branchCode, fullName,nationalCode,password;
    private InvalidName invalidName = new InvalidName();
    private InvalidNationalCode invalidNationalCode = new InvalidNationalCode();
    private LoginService loginService = new LoginService();
    private InvalidPassword invalidPassword = new InvalidPassword();

    public EmployeeService() throws SQLException, ClassNotFoundException {
    }

    public void addEmployee(String nationalCode) throws SQLException {
        branchCode = bankBranchService.findCodeBranch(nationalCode);
        while(true){
            System.out.print("Enter name(without number):");
            try {
                fullName = scanner.nextLine();
                invalidName.checkName(fullName);
                break;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
        while(true){
            System.out.println("Enter nationalCode(username):");
            while(true){
                System.out.print("National code:");
                try {
                    nationalCode = scanner.nextLine();
                    invalidNationalCode.nationalCodeChecker(nationalCode);
                    break;
                }catch (InvalidNationalCode i){
                    System.out.println(i.getMessage());
                }
            }
            if( loginService.findByNationalCode(nationalCode) != null ) {
                System.out.println("----------------------\nWrong national code!\n----------------------");
                addEmployee(nationalCode);
            }
            else
                break;
        }
        System.out.println("Enter password for " + fullName + " :");
        while(true) {
            System.out.print("Enter your password:");
            try {
                password = scanner.nextLine();
                invalidPassword.checkPassword(password);
                break;
            } catch (InvalidPassword except) {
                System.out.println(except.getMessage());
            }
        }
        Employee newEmployee = new Employee(fullName,nationalCode,branchCode,password, UserType.EMPLOYEE);
        employeeRepository.save(newEmployee);
        loginService.addNewLogin(nationalCode,password,UserType.EMPLOYEE);
        System.out.println("------------------------------\nEmployee added successfully\n------------------------------");
    }
}
