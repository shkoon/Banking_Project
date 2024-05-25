package ma.enset.digitalbanking;

import ma.enset.digitalbanking.dtos.CurrentAccountDTO;
import ma.enset.digitalbanking.dtos.CustomerDTO;
import ma.enset.digitalbanking.dtos.SavingAccountDTO;
import ma.enset.digitalbanking.entities.*;
import ma.enset.digitalbanking.enums.AccountStatus;
import ma.enset.digitalbanking.enums.OperationType;
import ma.enset.digitalbanking.repositories.BankAccountRepository;
import ma.enset.digitalbanking.repositories.CustomerRepository;
import ma.enset.digitalbanking.repositories.OperationRepository;
import ma.enset.digitalbanking.services.IDigitalBankingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IDigitalBankingService bankingService){
        return args -> {
            Stream.of("Amine OUBALI", "Omar ELKABOUS", "Mehdi DARNAOUD", "Aziz KArbal")
                    .forEach(name ->{
                        CustomerDTO customer = new CustomerDTO();
                        customer.setName(name);
                        customer.setEmail(name.replaceAll(" ","_")+"@outlook.fr");
                        bankingService.saveCustomer(customer);
                    });
            bankingService.ListCustomers().forEach(customer -> {
                CurrentAccountDTO currentAccount = new CurrentAccountDTO();
                currentAccount.setCurrency("MAD");
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                bankingService.saveCurrentAccount(new Random().nextDouble(10000.00),
                        5000.0, customer.getId());

                SavingAccountDTO savingAccount = new SavingAccountDTO();
                savingAccount.setCurrency("MAD");
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                bankingService.saveSavingAccount(new Random().nextDouble(10000.00),
                        5.5, customer.getId());
            });
        };
    }


    /*@Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository,
                            CustomerRepository customerRepository,
                            OperationRepository operationRepository){
        return args -> {
            Stream.of("Amine OUBALI", "Omar ELKABOUS", "Mehdi DARNAOUD", "Aziz KARBAL")
                    .forEach(name -> {
                        Customer customer = new Customer();
                        customer.setName(name);
                        customer.setEmail(name.replaceAll(" ","_")+"@outlook.fr");
                        customerRepository.save(customer);
                    });
            customerRepository.findAll()
                    .forEach(customer -> {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setCustomer(customer);
                        currentAccount.setBalance(new Random().nextDouble(10000.00));
                        currentAccount.setCurrency("MAD");
                        currentAccount.setCreatedAt(new Date());
                        currentAccount.setStatus(AccountStatus.CREATED);
                        currentAccount.setOverDraft(5000.0);
                        bankAccountRepository.save(currentAccount);

                        SavingAccount savingAccount = new SavingAccount();
                        savingAccount.setCustomer(customer);
                        savingAccount.setBalance(new Random().nextDouble(10000.00));
                        savingAccount.setCurrency("MAD");
                        savingAccount.setCreatedAt(new Date());
                        savingAccount.setStatus(AccountStatus.CREATED);
                        savingAccount.setInterestRate(5.5);
                        bankAccountRepository.save(savingAccount);
                    });
            bankAccountRepository.findAll()
                    .forEach(bankAccount -> {
                        for (int i = 0; i < 5; i++) {
                            Operation operation = new Operation();
                            operation.setAmount(new Random().nextDouble(3000.00));
                            operation.setDate(new Date());
                            operation.setType(new Random().nextBoolean()?OperationType.CREDIT:OperationType.DEBIT);
                            operation.setBankAccount(bankAccount);
                            operationRepository.save(operation);
                        }
                    });
        }
    };*/

}
