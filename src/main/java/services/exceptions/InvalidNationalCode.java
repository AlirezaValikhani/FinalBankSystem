package services.exceptions;

public class InvalidNationalCode extends RuntimeException {
    public InvalidNationalCode() {
    }

    public InvalidNationalCode(String message) {
        super(message);
    }

    public InvalidNationalCode(String message, Throwable cause) {
        super(message, cause);
    }

    public void nationalCodeChecker(String nationalId){
        if(nationalId.length() > 10 ){
            throw new InvalidNationalCode("--------------------------------------------------\nNational code can't be more than ten number!\n--------------------------------------------------");
        }
        if(nationalId.equals("")){
            throw new InvalidNationalCode("--------------------\nYou can't use space\n--------------------");
        }
        for (Character ch:nationalId.toCharArray()) {
            if(!Character.isDigit(ch))
                throw new InvalidNationalCode("--------------------\nYou must use number\n--------------------");
        }
    }
}

