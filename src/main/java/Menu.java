import models.Employee;
import services.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Integer input;
    private Boolean isTrue;
    private String userName, password;
    private LoginService loginService = new LoginService();
    private BankService bankService = new BankService();
    private BankBranchService bankBranchService = new BankBranchService();
    private EmployeeService employeeService = new EmployeeService();
    private AccountService accountService = new AccountService();
    private CustomerService customerService = new CustomerService();
    private CreditCardService creditCardService = new CreditCardService();
    private TransactionService transactionService = new TransactionService();

    public Menu() throws SQLException, ClassNotFoundException {
    }

    public void firstMenu() throws SQLException, ClassNotFoundException {
        System.out.println("\tWelcome");
        System.out.println("\t|1.SignIn");
        System.out.println("\t|2.Exit");
        System.out.print("\tEnter number : ");
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException i) {
                scanner.nextLine();
                System.out.print("\tEnter a number:");
            }
        }

        switch (input) {
            case 1:
                authentication();
                break;
            case 2:
                System.out.println("-----------------\nHave a good day\n-----------------");
                System.exit(0);
                break;
            default:
                System.out.println("----------------\nWrong number!!!\n----------------");
                break;
        }
    }


    public void authentication() throws SQLException, ClassNotFoundException {
        System.out.print("Username : ");
        userName = scanner.next();
        System.out.print("Password : ");
        password = scanner.next();
        if (userName.equals("admin") && password.equals("admin")) {
            adminMenu();
        } else {
            String result = loginService.findByUserPass(userName, password);
            if (result == null){
                System.out.println("-------------------------------\nWrong username or password!!!\n-------------------------------");
                authentication();
            }
            else if (result.equals("BOSS"))
                bossMenu();
            else if (result.equals("EMPLOYEE"))
                employeeMenu();
            else if (result.equals("CUSTOMER"))
                customerMenu();
        }
    }

    public void adminMenu() throws SQLException, ClassNotFoundException {
        isTrue = true;
        while (isTrue){
            System.out.println("\tWelcome to admin menu");
            System.out.println("\t|1.Add bank");
            System.out.println("\t|2.Add bank branch");
            System.out.println("\t|3.Exit");
            System.out.println("\tEnter number : ");
            while (true) {
                try {
                    input = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException exception) {
                    scanner.nextLine();
                    System.out.print("\tEnter number:");
                }
            }
            switch (input) {
                case 1:
                    bankService.addBank();
                    System.out.println("-------------------------\nBank added successfully\n-------------------------");
                    break;
                case 2:
                    bankBranchService.addBankBranch();
                    break;
                case 3:
                    System.out.println("------------------\nHave a good time\n------------------");
                    isTrue = false;
                    break;
                default:
                    System.out.println("---------------\nWrong number\n---------------");
                    break;
            }
        }
    }


    public void bossMenu() throws SQLException, ClassNotFoundException {
        isTrue = true;
        while (isTrue){
            System.out.println("\tWelcome to boss menu");
            System.out.println("\t|1.Add employee");
            System.out.println("\t|2.Inactive account");
            System.out.println("\t|3.Back");
            System.out.println("\tEnter number : ");
            while (true) {
                try {
                    input = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException i) {
                    scanner.nextLine();
                    System.out.print("\tEnter a number:");
                }
            }

            switch (input) {
                case 1:
                    employeeService.addEmployee(userName);
                    break;
                case 2:
                    accountService.inactiveAccount();
                case 3:
                    firstMenu();
                default:
                    System.out.println("Wrong number!!!");
                    break;
            }
        }
    }

    public void employeeMenu() throws SQLException, ClassNotFoundException {
        isTrue = true;
        while (isTrue){
            System.out.println("\tWelcome to employee menu");
            System.out.println("\t1-Add Customer");
            System.out.println("\t2-Add account for customer");
            System.out.println("\t3-Add credit card for account");
            System.out.println("\t4-View account with customer national code");
            System.out.println("\t5-View All credit cards with account number");
            System.out.println("\t6-INACTIVATE account!");
            System.out.println("\t7-Deposit money to account number(try)");
            System.out.println("\t8-Exit");
            System.out.print("\tPlease select a number : ");
            while (true) {
                try {
                    input = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException i) {
                    scanner.nextLine();
                    System.out.print("\tEnter a number:");
                }
            }

            switch (input) {
                case 1:
                    String result = customerService.save(userName);
                    System.out.println("----------------------------\n" + result + " added successfully\n----------------------------");
                    break;
                case 2:
                    Employee employee1 = new Employee(userName);
                    String customerName = accountService.addAccount(employee1);
                    System.out.println("--------------------------------------------\nAccount added for " + customerName + " successfully\n--------------------------------------------");
                    break;
                case 3:
                    Integer result1 = creditCardService.addCard();
                    if (result1 == 1)
                        System.out.println("---------------------------------\nCredit card added successfully!\n---------------------------------");
                    else
                        System.out.println("Something is wrong");
                    break;
                case 4:
                    accountService.viewAccount();
                    break;
                case 5:
                    creditCardService.showAllCreditCards();
                    break;
                case 6:
                    Integer returnNumber = accountService.deactivateAccount();
                    if(returnNumber != null)
                        System.out.println("------------------------------------\nThis account successful deactivated!\n------------------------------------");
                    break;
                case 7:
                    accountService.depositToAccount();
                    break;
                case 8:
                    firstMenu();
                    break;
                default:
                    System.out.println("---------------\nWrong number\n---------------");
                    break;
            }
        }
    }


    public void customerMenu() throws SQLException {
        isTrue = true;
        while (isTrue){
            while (true) {
                System.out.println("\tWelcome to customer Menu");
                System.out.println("\t1-Password operation");
                System.out.println("\t2-Transfer money");
                System.out.println("\t3-Show transaction");
                System.out.println("\t4-Exit");
                System.out.print("\tPlease enter a number:");
                while (true) {
                    try {
                        input = scanner.nextInt();
                        scanner.nextLine();
                        break;
                    } catch (InputMismatchException i) {
                        scanner.nextLine();
                        System.out.print("\tEnter a number:");
                    }
                }
                switch (input) {
                    case 1:
                        creditCardService.setPassword(userName);
                        break;

                    case 2:
                        creditCardService.transferMoney(userName);
                        break;
                    case 3:
                        transactionService.showTransaction(userName);
                        break;

                    case 4:
                        System.out.println("Good luck!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("You enter a wrong number!");

                }
            }
        }
    }
}
