package ma.enset.digitalbanking.web;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.dtos.BankAccountHistoryDTO;
import ma.enset.digitalbanking.dtos.OperationDTO;
import ma.enset.digitalbanking.dtos.TransactionDTO;
import ma.enset.digitalbanking.services.IDigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@CrossOrigin("*")
public class OperationRestController {
    private IDigitalBankingService bankingService;

    @GetMapping("/operation")
    List<OperationDTO> getAllOperations(){
        return bankingService.listOperation();
    }

    @GetMapping("bankaccount/{id}/operation")
    List<OperationDTO> getOperationsByBankAccountId(@PathVariable String id){
        return bankingService.getOperationByBankAccountId(id);
    }

    @GetMapping("bankaccount/{id}/history")
    BankAccountHistoryDTO getBankAccountHistory(@PathVariable String id,
                                                @RequestParam(name="page", defaultValue = "0") int page,
                                                @RequestParam(name="size", defaultValue = "3") int size){
        return bankingService.getBankAccountHistory(id,page,size);
    }

    @PostMapping("bankaccount/{id}/operation")
    OperationDTO saveOperation(@PathVariable String id,@RequestBody OperationDTO operationDTO){
        return bankingService.saveOperation(operationDTO,bankingService.getBankAccount(id));
    }

    @PostMapping("bankaccount/transfer")
    BankAccountHistoryDTO saveOperation(@RequestBody TransactionDTO transactionDTO){
        return bankingService.saveTransaction(transactionDTO);
    }
}
