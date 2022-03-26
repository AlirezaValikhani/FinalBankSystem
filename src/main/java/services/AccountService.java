package services;

import models.*;
import repositpries.AccountRepository;
import repositpries.EmployeeRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AccountService {
    private AccountRepository accountRepository = new AccountRepository();
    private Scanner scanner = new Scanner(System.in);
    private String nationalCode,accountNumber;
    private Double balance;
    private CustomerService customerService = new CustomerService();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private LoginService loginService = new LoginService();
    Random random = new Random();
    private static TransactionService transactionService = new TransactionService();

    public void inactiveAccount(){
        System.out.print("Enter customer national code : ");
        nationalCode = scanner.nextLine();
        Customer customer = customerService.read(nationalCode);
        if(customer == null){
            System.out.println("------------------------------------------\nThis national code is not defined Before!\n------------------------------------------");
            return;
        }
        List<Account> accountList = accountRepository.readAllByNationalCode(nationalCode);
        int i=0;
        for (Account account:accountList) {
            if(account.getAccountType().toString().equals("ACTIVE")){
                System.out.println(account.toString());
                i++;
            }
        }
        if(i == 0 ){
            System.out.println("----------------------------------------\nThis customer doesn't have any Account!\n----------------------------------------");
            return;
        }
        System.out.print("Enter the account number for DEACTIVATE : ");
        accountNumber = scanner.nextLine();
        Account account = new Account(accountNumber);
        if(accountRepository.read(account) == null ){
            System.out.println("------------------------\nWrong account number!\n------------------------");
            return;
        }
        accountRepository.deactivateAccount(accountNumber);
        System.out.println("--------------------------------------------------\nThis account has been successfully deactivated\n\"--------------------------------------------------");
    }

    public String addAccount(Employee employee){
        Employee returnedEmployee = employeeRepository.readByUserName(employee.getNationalId());
        while (true){
            System.out.println("National code(username) : ");
            nationalCode = scanner.nextLine();
            if(customerService.readByNationalCode(nationalCode) == null){
                System.out.println("------------------------------\nNational code doesn't exist!\n------------------------------");
            }else break;
        }
            String number;
            while(true) {
                number = String.valueOf(11111111 + random.nextInt(99999999));
                if( accountRepository.readByAccountNumber(number) == null)
                    break;
            }
            System.out.print("Enter balance : ");
            while (true) {
                try {
                    balance = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException i) {
                    scanner.nextLine();
                    System.out.print("Enter correct balance : ");
                }
            }
            Account newAccount = new Account(returnedEmployee.getCodeBranch(),nationalCode,number,balance, AccountType.ACTIVE);
            accountRepository.save(newAccount);
            Customer customer = customerService.readByNationalCode(nationalCode);
            return customer.getFullName();
        }

        public String showAccounts(String nationalCode){
            return accountRepository.showAccounts(nationalCode);
        }

    public Account read(Long id){
        Account account = new Account(id);
        return accountRepository.read(account);
    }

    public void viewAccount() throws SQLException {
        System.out.print("Enter national code Customer:");
        nationalCode = scanner.nextLine();
        Customer customer = customerService.readByNationalCode(nationalCode);
        if( customer.getFullName().equals("null")){
            System.out.println("------------------------------\nThis national code not found!\n------------------------------");
            return;
        }
        System.out.println(customer.getFullName() + " has this account:");
        List<Account> accountList = accountRepository.readAllByNationalCode(nationalCode);
        if(accountList.isEmpty()){
            System.out.println("------------------------------------------------\nThis national code doesn't have any account!\n------------------------------------------------");
            return;
        }
        for (Account account : accountList) {
            System.out.println(account.toString());
        }
    }

    public void showAccountForCustomer(String nationalIdCustomer) throws SQLException {
        List<Account> accountList = accountRepository.showAccountsWithNationalCode(nationalIdCustomer);
        if(accountList.isEmpty()) {
            System.out.println("This national code doesn't have any account!");
            return;
        }
        for (Account account : accountList)
        {
            if(account.getAccountType().toString().equals("ACTIVE")) {
                System.out.println(account.toString());
            }
        }
    }

    public Integer deactivateAccount() throws SQLException {
        System.out.print("Enter national code customer:");
        nationalCode = scanner.nextLine();
        Customer customer = customerService.readByNationalCode(nationalCode);
        if(customer == null){
            System.out.println("-------------------------------------------\nThis national code is not defined Before!\n-------------------------------------------");
            return 0;
        }
        List<Account> accountList = accountRepository.showAccountsWithNationalCode(nationalCode);
        int i=0;
        for (Account account:accountList) {
            if(account.getAccountType().toString().equals("ACTIVE")){
                System.out.println(account.toString());
                i++;
            }
        }
        if(i == 1 ){
            System.out.println("---------------------------------------------\nThis Customer just have an Active account!\n---------------------------------------------");
            return 0;
        }
        System.out.print("Enter the account number for INACTIVE:");
        accountNumber = scanner.nextLine();
        if(accountRepository.readByAccountNumber(accountNumber) == null ){
            System.out.println("-----------------------\nWrong account Number!\n-----------------------");
            return 0;
        }
        accountRepository.inactiveAccount(accountNumber);
        return 1;
    }

    public void depositToAccount() throws SQLException, ClassNotFoundException {
        System.out.print("Enter national code customer:");
        nationalCode = scanner.nextLine();
        Customer customer = customerService.readByNationalCode(nationalCode);
        if (customer.getFullName().equals("null")) {
            System.out.println("----------------------\nWrong national code!\n----------------------");
            return;
        }
        System.out.println(customer.getFullName() + " has this accounts in our Bank : ");
        showAccountForCustomer(nationalCode);
        System.out.print("Enter account number for deposit money:");
        accountNumber = scanner.nextLine();
        if(!accountRepository.checkAccount(accountNumber)){
            System.out.println("-----------------------\nWrong account number!\n-----------------------");
            return;
        }
        System.out.print("Enter amount for deposit:");
        while (true) {
            try {
                balance = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                System.out.print("Enter correct Amount:");
            }
        }
        accountRepository.depositToCard(balance,accountNumber);
        LocalDate tempDate = LocalDate.now();
        Date date = Date.valueOf(tempDate);
        LocalTime tempTime = LocalTime.now();
        Time time = Time.valueOf(tempTime);
        Transaction transaction = new Transaction(accountNumber,"Employee","Employee",String.valueOf(balance),date,time, TransactionType.CLERK_DEPOSIT);
        transactionService = new TransactionService();
        transactionService.save(transaction);
        System.out.println(balance + " successful added to account " + customer.getFullName());
    }

    public Boolean checkAccount(String accountNumber){
        return accountRepository.checkAccount(accountNumber);
    }

    public Account read(String accountNumber){
        Account account = new Account(accountNumber);
        return accountRepository.read(account);
    }

    public Account readByAccountNumber(String accountNumber){
        return accountRepository.readByAccountNumber(accountNumber);
    }

    public void deposit(Double amount,String accountNumber){
        accountRepository.deposit(amount,accountNumber);
    }

    public void withdraw(Double amount,String accountNumber){
        accountRepository.withdraw(amount,accountNumber);
    }
}

