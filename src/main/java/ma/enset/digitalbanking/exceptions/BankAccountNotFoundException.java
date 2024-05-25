package ma.enset.digitalbanking.exceptions;

public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(String bankAccountNotFound) {
        super(bankAccountNotFound);
    }
}
