package services.exceptions;

public class InvalidName extends RuntimeException {
    public InvalidName(String message) {
        super(message);
    }

    public InvalidName(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidName(){
    }

    public void checkName(String name){
        if(name.length() < 3)
            throw new InvalidName("--------------------------------------\nName should be more than 3 character\n--------------------------------------");
        for (Character ch:name.toCharArray()) {
            if(Character.isDigit(ch))
                throw new InvalidName("--------------------------\nName can not have a digit\n--------------------------");
        }
        for (Character ch:name.toCharArray()) {
            if(!Character.isAlphabetic(ch))
                throw new InvalidName("Name can't have Sign(!,@,#,%,...)");
        }
    }
}
