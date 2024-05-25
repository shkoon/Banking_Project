package ma.enset.digitalbanking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.digitalbanking.entities.BankAccount;
import ma.enset.digitalbanking.enums.OperationType;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date date;
    private Double amount;
    private OperationType type;
}
