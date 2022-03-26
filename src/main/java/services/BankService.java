package services;

import models.Bank;
import repositpries.BankRepository;
import services.exceptions.InvalidName;

import java.sql.SQLException;
import java.util.Scanner;

public class BankService {
    private Scanner scanner = new Scanner(System.in);
    private BankRepository bankRepository = new BankRepository();
    private String bankName;
    private InvalidName invalidName = new InvalidName();

    public BankService() throws SQLException, ClassNotFoundException {
    }

    public void addBank() throws SQLException, ClassNotFoundException {
        System.out.println("Bank name : ");
        while (true){
            try{
                bankName = scanner.nextLine();
                invalidName.checkName(bankName);
                break;
            }catch (InvalidName i){
                System.out.println(i.getMessage());
            }
        }
        Bank bank = new Bank(bankName);
        if(bankRepository.readByName(bankName) != null){
            System.out.println("------------------------------\nThis name already exists!!!\n------------------------------");
        }else bankRepository.save(bank);
    }
}
