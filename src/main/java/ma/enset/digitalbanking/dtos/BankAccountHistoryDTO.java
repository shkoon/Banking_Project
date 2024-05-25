package ma.enset.digitalbanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class BankAccountHistoryDTO {
    private String id;
    private Double balance;
    private int currentPage;
    private int totalPages;
    private int size;
    private List<OperationDTO> operationDTOS;
}
