package ma.enset.digitalbanking.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerNotFound) {
        super(customerNotFound);
    }
}
