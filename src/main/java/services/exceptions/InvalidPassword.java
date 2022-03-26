package services.exceptions;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword() {
    }

    public InvalidPassword(String message) {
        super(message);
    }

    public InvalidPassword(String message, Throwable cause) {
        super(message, cause);
    }

    /*public void checkPassword(String password){
        if(password.length() < 3 )
            throw new InvalidPassword("--------------------------------------------------\nPassword length should be more than 2 character\n--------------------------------------------------");
        char[] passwordArray = password.toCharArray();
        char[] signArray =  new char[] {'!','@','#','$','%','^','&','*','(',')','-','+','=','.',',','>','<','?','/','|',':',';'};
        int space = 0,lowerCase = 0,upperCase = 0,sign = 0,digit = 0;
        for(int i=0;i<passwordArray.length;i++)
            if(Character.isSpaceChar(passwordArray[i]))
                ++space;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isUpperCase(passwordArray[i]))
                ++upperCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isLowerCase(passwordArray[i]))
                ++lowerCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isDigit(passwordArray[i]))
                ++digit;
        for(int i=0;i<signArray.length;i++)
            for(int j=0;j<passwordArray.length;j++)
                if(signArray[i] == passwordArray[j])
                    ++sign;
        if( (space == 0) || (lowerCase == 0) || (upperCase == 0) || (sign == 0) || (digit == 0) )
            throw new InvalidPassword("----------------------------------------------------------------------\nPassword should have space + lower case + upper case + sign + digit!\n----------------------------------------------------------------------");
    }*/

    public void checkPassword(String password){
        if(password.length() < 3 )
            throw new InvalidPassword("--------------------------------------------------\nPassword length should be more than 2 character\n--------------------------------------------------");
    }
}

