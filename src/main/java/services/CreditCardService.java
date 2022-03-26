package services;

import models.*;
import repositpries.CreditCardRepository;

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CreditCardService {
    private Scanner scanner = new Scanner(System.in);
    private CreditCardRepository creditCardRepository = new CreditCardRepository();
    private String nationalCode,name,accountNumber,cardNumber,cvv2,expireDate
            ,originCardNumber,destinationCardNumber,password;
    private LoginService loginService = new LoginService();
    private CustomerService customerService = new CustomerService();
    private AccountService accountService = new AccountService();
    private Random random = new Random();
    private Menu menu = new Menu();
    private Double balance;
    private TransactionService transactionService = new TransactionService();

    public Integer addCard() throws SQLException {
        while(true){
            System.out.print("Enter customer national code(print 'cancel' to exit) : ");
            nationalCode = scanner.nextLine();
            if( nationalCode.equals("cancel"))
                return 0;
            if( loginService.findByNationalCode(nationalCode) == null ) {
                System.out.println("----------------------\nWrong national code!\n----------------------");
            }
            else
                break;
        }
        Customer customer = customerService.readByNationalCode(nationalCode);
        if ( customer == null){
            System.out.println("--------------------\nWrong national id!\n--------------------");
            return 0;
        }
        System.out.println(customer.getFullName() + " have this accounts :");
        String resultString = accountService.showAccounts(nationalCode);
        if( resultString == null ){
            System.out.println("--------------------------------------------------\n" + customer.getFullName() + " doesn't have any active account!\n--------------------------------------------------");
            return 0;
        }
        System.out.print("Choose account number for add card : ");
        String accountNumber = scanner.next();
        Account account = accountService.readByAccountNumber(accountNumber);
        if ( account == null ){
            System.out.println("-------------------------\nWrong account number!\n-------------------------");
            return 0;
        }
        Integer activeCard = creditCardRepository.findActiveCard(accountNumber);
        if( activeCard == 1){
            System.out.println("----------------------------------------------\nThis account you enter have an active card!\n----------------------------------------------");
            return 0;
        }
        while(true){
            cardNumber = String.valueOf(111111111 + random.nextInt(999999999));
            if( creditCardRepository.readByCardNumber(cardNumber) == null )
                break;
        }
        cvv2 = String.valueOf(1111 + random.nextInt(9999));
        Integer year = LocalDate.now().getYear() + 4 ;
        Integer month = LocalDate.now().getMonthValue();
        expireDate = (String.valueOf(year)) + "-" + (String.valueOf(month));
        CreditCard newCreditCard = new CreditCard(accountNumber,cardNumber,cvv2,expireDate, AccountType.ACTIVE);
        creditCardRepository.save(newCreditCard);
        return 1;
    }

    public void showAllCreditCards() throws SQLException {
        System.out.print("Customer national code : ");
        nationalCode = scanner.nextLine();
        Customer customer = customerService.readByNationalCode(nationalCode);
        if(customer == null){
            System.out.println("--------------------------------\nThis national code not found!\n--------------------------------");
            return;
        }
        System.out.println(customer.getFullName() + " have this account:");
        accountService.showAccounts(nationalCode);
        System.out.print("Enter account number for view card:");
        accountNumber = scanner.next();
        List<CreditCard> creditCardList = creditCardRepository.readAllByAccountNumber(accountNumber);
        if(creditCardList == null){
            System.out.println("----------------------------------------\nThis account doesn't hava credit card!\n----------------------------------------");
            return;
        }
        for (CreditCard creditCard : creditCardList) {
            System.out.println(creditCard.toString());
        }
    }


    public void setPassword(String customerNationalCode) throws SQLException {
        while (true){
            List<String> result = creditCardRepository.showCustomerCreditCards(customerNationalCode);
            if( result == null){
                System.out.println("-------------------------\nYou dont have any card!\n-------------------------");
                return;
            }
            System.out.print("Select card number for set password : ");
            String number = scanner.nextLine();
            boolean equality = false;
            for(int i=0;i<result.size();i++) {
                if (result.get(i) == null)
                    break;
                if (result.get(i).equals(number)) {
                    equality = true;
                    break;
                }
            }
            if(!equality){
                System.out.println("------------------------------------------\nWe dont have any card with this number!\n------------------------------------------");
                return;
            }
            System.out.print("Select number for set password : ");
            String password = scanner.nextLine();
            creditCardRepository.setPassword(Integer.parseInt(number),password);
            System.out.println("----------------------------------\nPassword setting was successful\n----------------------------------");
            return;
        }
    }


    public void transferMoney(String nationalIdCustomer) throws SQLException {
        List<String> result = creditCardRepository.showCustomerCreditCards(nationalIdCustomer);
        if (result == null) {
            System.out.println("-------------------------\nYou dont have any card!\n-------------------------");
            return;
        }
        System.out.print("Please select number card for transfer money:");
        String number = scanner.nextLine();
        boolean equality = false;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == null)
                break;
            if (result.get(i).equals(number)) {
                equality = true;
                break;
            }
        }
        if (!equality) {
            System.out.println("------------------------------------------\nWe dont have any card with this number!\n------------------------------------------");
            return;
        }
        List<String> result2 = creditCardRepository.returnCardInformation(Integer.parseInt(number));
        if(result2.get(2) == null){
            System.out.println("------------------------------------------------------------------------------------------\nYou have not defined a password before, please define the password for it and try again!\n------------------------------------------------------------------------------------------");
            return;
        }
        accountNumber = result2.get(0);
        if(!accountService.checkAccount(accountNumber)){
            System.out.println("------------------------------\nThis account is not Active!\n------------------------------");
            return;
        }
        originCardNumber = result2.get(1);
        password = result2.get(2);
        String tempPassword;
        int numberFalse = 0;
        while(true){
            System.out.print("Enter password for this card:");
            tempPassword = scanner.nextLine();
            if(tempPassword.equals(password))
                break;
            else{
                if(numberFalse < 3 ){
                    ++numberFalse;
                    System.out.println("You enter " + numberFalse + " time false password");
                    if(numberFalse == 3 ){
                        System.out.println("You enter 3 time incorrect password and I eat your card!(call to clerk)");
                        creditCardRepository.deactivateCreditCard(Long.valueOf(number));
                        return;
                    }
                }
            }
        }
        System.out.print("Enter Expire date of your card:");
        expireDate = scanner.nextLine();
        if( !expireDate.equals(result2.get(3))){
            System.out.println("You enter a wrong Expire Date");
            return;
        }
        System.out.print("Enter cvv2 of your card:");
        cvv2 = scanner.nextLine();
        if( !cvv2.equals(result2.get(4))){
            System.out.println("You enter a wrong cvv2");
            return;
        }
        System.out.print("Destination card number : ");
        destinationCardNumber = scanner.nextLine();
        if( !creditCardRepository.checkCardNumber(destinationCardNumber)) {
            System.out.println("--------------------\nWrong card number!\n--------------------");
            return;
        }
        CreditCard creditCard = new CreditCard(destinationCardNumber);
        CreditCard destinationCreditCard = creditCardRepository.read(creditCard);
        if(!accountService.checkAccount(destinationCreditCard.getCardNumber())){
            System.out.println("----------------------------------------\nAccount number has been deactivated!\n----------------------------------------");
            return;
        }
        LocalDate tempDate = LocalDate.now();
        Date date = Date.valueOf(tempDate);
        LocalTime tempTime = LocalTime.now();
        Time time = Time.valueOf(tempTime);
        Account account = accountService.read(accountNumber);
        System.out.println("You have " + account.getBalance() + " in this account");
        System.out.print("How much do you want to Transfer(600 for Transfer fee):");
        balance = scanner.nextDouble();
        if( (balance+600) > account.getBalance()){
            System.out.println("How are you,you dont have this amount for Transfer!");
            return;
        }
        accountService.withdraw(balance+600,accountNumber);
        accountService.deposit(balance,destinationCardNumber);
        Transaction minesTransaction = new Transaction(accountNumber,originCardNumber,destinationCardNumber,String.valueOf(-balance),date,time, TransactionType.TRANSFER_MONEY);
        transactionService.save(minesTransaction);
        Transaction feeTransaction = new Transaction(accountNumber,originCardNumber,destinationCardNumber,String.valueOf(-600),date,time, TransactionType.TRANSFER_FEE);
        transactionService.save(feeTransaction);
        Transaction plusTransaction = new Transaction(destinationCreditCard.getCardNumber(),originCardNumber,destinationCardNumber,String.valueOf(balance),date,time, TransactionType.TRANSFER_MONEY);
        transactionService.save(plusTransaction);
        System.out.println("This card to card is successful!");
    }
}
