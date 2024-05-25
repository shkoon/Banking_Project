package ma.enset.digitalbanking.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitalbanking.dtos.*;
import ma.enset.digitalbanking.entities.*;
import ma.enset.digitalbanking.enums.AccountStatus;
import ma.enset.digitalbanking.enums.OperationType;
import ma.enset.digitalbanking.exceptions.BalanceNotSufficientException;
import ma.enset.digitalbanking.exceptions.BankAccountNotFoundException;
import ma.enset.digitalbanking.exceptions.CustomerNotFoundException;
import ma.enset.digitalbanking.mappers.DigitalBankingMapperImpl;
import ma.enset.digitalbanking.repositories.BankAccountRepository;
import ma.enset.digitalbanking.repositories.CustomerRepository;
import ma.enset.digitalbanking.repositories.OperationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DigitalBankingServiceImpl implements IDigitalBankingService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private OperationRepository operationRepository;
    private DigitalBankingMapperImpl bankingMapper;

    @Override
    public List<CustomerDTO> ListCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(customer -> bankingMapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }

    @Override
    public void debit(String accountId, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() ->  new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("Balance not sufficient");
        }else{
            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepository.save(bankAccount);
        }
    }

    @Override
    public void credit(String accountId, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() ->  new BankAccountNotFoundException("Bank account not found"));

        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, Double amount) {
        debit(accountIdSource, amount);
        credit(accountIdDestination, amount);
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() ->  new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount instanceof SavingAccount){
            return bankingMapper.fromSavingAccount((SavingAccount) bankAccount);
        } else {
            return bankingMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        }
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(Double balance, Double overDraft, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setBalance(balance);
        currentAccount.setCurrency("MAD");
        currentAccount.setCreatedAt(new Date());
        bankAccountRepository.save(currentAccount);

        return bankingMapper.fromCurrentAccount(currentAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(Double balance, Double interestRate, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setBalance(balance);
        savingAccount.setCurrency("MAD");
        savingAccount.setCreatedAt(new Date());
        bankAccountRepository.save(savingAccount);

        return bankingMapper.fromSavingAccount(savingAccount);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Adding new customer");
        Customer customer = customerRepository.save(bankingMapper.fromCustomerDTO(customerDTO));
        return bankingMapper.fromCustomer(customer);
    }

    @Override
    public OperationDTO saveOperation(OperationDTO operationDTO,BankAccountDTO bankAccountDTO) {
        log.info("Adding new operation");
        Operation operation = bankingMapper.fromOperationDTO(operationDTO);
        if(bankAccountDTO instanceof CurrentAccountDTO){
            operation.setBankAccount(bankingMapper.fromCurrentAccountDTO((CurrentAccountDTO) bankAccountDTO));
            if(operationDTO.getType().equals(OperationType.CREDIT)){
                credit(((CurrentAccountDTO) bankAccountDTO).getId(),operationDTO.getAmount());
            }else{
                debit(((CurrentAccountDTO) bankAccountDTO).getId(),operationDTO.getAmount());
            }
        }else{
            operation.setBankAccount(bankingMapper.fromSavingAccountDTO((SavingAccountDTO) bankAccountDTO));
            if(operationDTO.getType().equals(OperationType.CREDIT)){
                credit(((SavingAccountDTO) bankAccountDTO).getId(),operationDTO.getAmount());
            }else{
                debit(((SavingAccountDTO) bankAccountDTO).getId(),operationDTO.getAmount());
            }
        }
        return bankingMapper.fromOperation(operationRepository.save(operation));
    }

    @Override
    public CustomerDTO getCustomer(String customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return bankingMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<BankAccountDTO> listBankAccounts(){
        return bankAccountRepository.findAll()
                .stream().map(bankAccount -> {
                    if(bankAccount instanceof CurrentAccount){
                        return bankingMapper.fromCurrentAccount((CurrentAccount) bankAccount);
                    }else{
                        return bankingMapper.fromSavingAccount((SavingAccount) bankAccount);
                    }
                }).collect(Collectors.toList());
    }


    @Override
    public List<OperationDTO> getOperationByBankAccountId(String id){
        return operationRepository.findByBankAccountId(id)
                .stream()
                .map(operation ->  bankingMapper.fromOperation(operation))
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDTO> listOperation() {
        return operationRepository
                .findAll()
                .stream()
                .map(operation -> bankingMapper.fromOperation(operation))
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountHistoryDTO getBankAccountHistory(String id, int page, int size){
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        List<OperationDTO> operationHistory = operationRepository.findByBankAccountId(id, PageRequest.of(page, size))
                .stream()
                .map(operation -> bankingMapper.fromOperation(operation))
                .collect(Collectors.toList());
        BankAccountHistoryDTO bankAccountHistoryDTO = new BankAccountHistoryDTO();
        bankAccountHistoryDTO.setBalance(bankAccount.getBalance());
        bankAccountHistoryDTO.setId(id);
        bankAccountHistoryDTO.setOperationDTOS(operationHistory);
        bankAccountHistoryDTO.setCurrentPage(page);
        bankAccountHistoryDTO.setSize(size);
        bankAccountHistoryDTO.setTotalPages(operationHistory.size());
        return bankAccountHistoryDTO;
    }

    @Override
    public BankAccountHistoryDTO saveTransaction(TransactionDTO transactionDTO) {
        transfer(transactionDTO.getSourceId(),transactionDTO.getDestinationId(),transactionDTO.getAmount());
        int index = operationRepository.findByBankAccountId(transactionDTO.getSourceId()).size();
        return getBankAccountHistory(transactionDTO.getSourceId(), index-1, 1);
    }

    @Override
    public List<CustomerDTO> findCustomerByKeyword(String keyword){
        return customerRepository.findByNameContainsIgnoreCase(keyword)
                .stream()
                .map(customer -> bankingMapper.fromCustomer(customer))
                .collect(Collectors.toList());
    }
}
