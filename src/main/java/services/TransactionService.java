package services;

import models.Customer;
import models.Transaction;
import repositpries.TransactionRepository;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionService {
    private Scanner scanner = new Scanner(System.in);
    private TransactionRepository transactionRepository = new TransactionRepository();
    private CustomerService customerService = new CustomerService();
    private static AccountService accountService = new AccountService();
    private String accountNumber;

    public Long save(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public void showTransaction(String customerNationalCode) throws SQLException {
        Customer customer = customerService.readByNationalCode(customerNationalCode);
        System.out.println(customer.getFullName() + " dear you have this account:");
        accountService.showAccountForCustomer(customerNationalCode);
        /*if(!accountService.getCheck()){
            System.out.println("You dont have any account!");
            return;
        }*/
        System.out.print("Enter account number for view your Transaction:");
        accountNumber = scanner.nextLine();
        if ( accountService.read(accountNumber) == null){
            System.out.println("-----------------------\nWrong account number!\n-----------------------");
            return;
        }
        System.out.print("Enter date(Year-month-day):");
        String date = scanner.nextLine();
        Date date1 = Date.valueOf(date);
        LocalDate tempDate = LocalDate.now();
        Date date2 = Date.valueOf(tempDate);
        if( date1.after(date2) ) {
            System.out.println("You enter a date after now!");
            return;
        }
        List<Transaction> transactionList = transactionRepository.findAllTransactions(accountNumber,date1);
        if( transactionList.isEmpty() ){
            System.out.println("This account doesn't have any any Transaction!");
            return;
        }
        for (Transaction transaction: transactionList) {
            System.out.println(transaction.toString());
        }
    }
}
