package services.exceptions;

public class InvalidBranchCode extends RuntimeException {
    public InvalidBranchCode() {
    }

    public InvalidBranchCode(String message) {
        super(message);
    }

    public InvalidBranchCode(String message, Throwable cause) {
        super(message, cause);
    }

    public void branchCodeChecker(String codeBranch) {
        if (codeBranch.length() > 4)
            throw new InvalidBranchCode("----------------------------------------\nBranch code can't be more than 4 number!\n----------------------------------------");
        if (codeBranch.equals(""))
            throw new InvalidBranchCode("--------------------\nYou can't use space\n--------------------");
        for (Character ch : codeBranch.toCharArray()) {
            if (!Character.isDigit(ch))
                throw new InvalidBranchCode("--------------------\nYou must use number\n--------------------");
        }
    }
}
