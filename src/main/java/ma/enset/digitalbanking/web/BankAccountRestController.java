package ma.enset.digitalbanking.web;


import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.dtos.BankAccountDTO;
import ma.enset.digitalbanking.services.IDigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    private IDigitalBankingService bankingService;

    @GetMapping("/bankaccount")
    List<BankAccountDTO> getBankAccounts(){
        return bankingService.listBankAccounts();
    }

    @GetMapping("/bankaccount/{id}")
    BankAccountDTO getBankAccount(@PathVariable String id){
        return bankingService.getBankAccount(id);
    }

   /* @PostMapping("/bankaccount")
    BankAccountDTO saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO){
        return bankingService.saveBankAccount(bankAccountDTO);
    }

    @PutMapping("/bankaccount/{id}")
    BankAccountDTO updateBankAccount(@PathVariable String id,@RequestBody BankAccountDTO bankAccountDTO){
        bankAccountDTO.setId(id);
        return bankingService.saveBankAccount(bankAccountDTO);
    }

    @DeleteMapping("/bankaccount/{id}")
    void deleteBankAccount(@PathVariable String id){
        bankingService.deleteBankAccount(id);
    }*/
}
