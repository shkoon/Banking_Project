package ma.enset.digitalbanking.dtos;

import lombok.Data;

@Data
public class TransactionDTO {
    private String sourceId;
    private String destinationId;
    private Double amount;
}
