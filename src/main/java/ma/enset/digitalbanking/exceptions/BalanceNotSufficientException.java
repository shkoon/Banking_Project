package ma.enset.digitalbanking.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String balanceNotSufficient) {
        super(balanceNotSufficient);
    }
}
