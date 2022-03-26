package services;

import models.Customer;
import models.Employee;
import models.UserType;
import repositpries.CustomerRepository;
import repositpries.EmployeeRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerService {
    private Scanner scanner = new Scanner(System.in);
    private String branchCode,fullName,nationalCode,password;
    private CustomerRepository customerRepository = new CustomerRepository();
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private LoginService loginService = new LoginService();

    public Customer read(String nationalCode){
        Customer customer = new Customer(nationalCode);
        return customerRepository.read(customer);
    }

    public String save(String userName) throws SQLException {
        Employee employee = employeeRepository.readByUserName(userName);
        System.out.println("Full name : ");
        fullName = scanner.nextLine();
        while (true){
            System.out.println("National code : ");
            nationalCode = scanner.nextLine();
            if (loginService.findByNationalCode(nationalCode) != null)
                System.out.println("------------------------------------\nThis national code already exists!\n------------------------------------");
            else break;
        }
        System.out.print("Enter password for " + fullName + " :");
        password = scanner.nextLine();
        Customer newClerk = new Customer(fullName,nationalCode,employee.getCodeBranch(),password, UserType.CUSTOMER);
        customerRepository.save(newClerk);
        loginService.addNewLogin(nationalCode,password,UserType.CUSTOMER);
        return fullName;
    }

    public Customer readByNationalCode(String nationalCode){
        return customerRepository.readByNationalCode(nationalCode);
    }
}
