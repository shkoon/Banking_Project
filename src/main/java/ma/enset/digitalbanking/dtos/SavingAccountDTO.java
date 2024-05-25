package ma.enset.digitalbanking.dtos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.digitalbanking.entities.BankAccount;
import ma.enset.digitalbanking.enums.AccountStatus;

import java.util.Date;

@Data
public class SavingAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private Double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customer;
    private Double interestRate;
}
