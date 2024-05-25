package ma.enset.digitalbanking.web;

import lombok.AllArgsConstructor;
import ma.enset.digitalbanking.dtos.CustomerDTO;
import ma.enset.digitalbanking.entities.Customer;
import ma.enset.digitalbanking.services.IDigitalBankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {

    private IDigitalBankingService bankingService;

    @GetMapping("/customers")
    List<CustomerDTO> customerList(){
        return  bankingService.ListCustomers();
    }

    @GetMapping("/customers/search")
    List<CustomerDTO> searchCustomer(@RequestParam(name = "keyword") String keyword){
        return  bankingService.findCustomerByKeyword(keyword);
    }

    @GetMapping("/customers/{id}")
    CustomerDTO getCustomer(@PathVariable(name = "id") String customerId){
        return bankingService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankingService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    CustomerDTO updateCustomer(@PathVariable String customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankingService.saveCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{customerId}")
    void deleteCustomer(@PathVariable String customerId){
        bankingService.deleteCustomer(customerId);
    }
}
