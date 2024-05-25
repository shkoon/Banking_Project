package ma.enset.digitalbanking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.digitalbanking.entities.BankAccount;

import java.util.Collection;

@Data
public class CustomerDTO {
    private String id;
    private String name;
    private String email;
}
