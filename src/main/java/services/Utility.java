package services;

import services.exceptions.InvalidBranchCode;
import services.exceptions.InvalidNationalCode;
import services.exceptions.InvalidPassword;

import java.util.Scanner;

public class Utility {
    private Scanner scanner = new Scanner(System.in);
    private String branchCode,nationalCode,password;
    private InvalidBranchCode invalidBranchCode = new InvalidBranchCode();
    private InvalidNationalCode invalidNationalCode = new InvalidNationalCode();
    private InvalidPassword invalidPassword = new InvalidPassword();

    public String setBranchCode(){
        while (true){
            System.out.println("Enter branch code(number) : " );
            try{
                branchCode = scanner.next();
                invalidBranchCode.branchCodeChecker(branchCode);
                break;
            }catch (InvalidBranchCode i){
                System.out.println(i.getMessage());
            }
        }
        return branchCode;
    }

    public String setNationalCode(){
        while(true){
            System.out.print("Enter National code(number) : ");
            try {
                nationalCode = scanner.next();
                invalidNationalCode.nationalCodeChecker(nationalCode);
                break;
            }catch (InvalidNationalCode i){
                System.out.println(i.getMessage());
            }
        }
        return nationalCode;
    }

    public String setPassword(){
        while(true) {
            System.out.print("Enter your password : ");
            try {
                password = scanner.nextLine();
                invalidPassword.checkPassword(password);
                break;
            } catch (InvalidPassword i) {
                System.out.println(i.getMessage());
            }
        }
        return password;
    }
}
