package ma.enset.digitalbanking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.digitalbanking.enums.AccountStatus;

import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Date createdAt;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @ManyToOne @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY) @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Operation> operation;
}
