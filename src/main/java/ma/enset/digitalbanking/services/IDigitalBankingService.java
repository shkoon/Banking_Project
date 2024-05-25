package ma.enset.digitalbanking.services;

import ma.enset.digitalbanking.dtos.*;

import java.util.List;

public interface IDigitalBankingService {
    CurrentAccountDTO saveCurrentAccount(Double balance, Double overDraft, String customerId);
    SavingAccountDTO saveSavingAccount(Double balance, Double interestRate, String customerId);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> ListCustomers();
    void debit(String accountId, Double amount);
    void credit(String accountId, Double amount);
    void transfer(String accountIdSource, String accountIdDestination, Double amount);
    BankAccountDTO getBankAccount(String accountId);

    OperationDTO saveOperation(OperationDTO operationDTO, BankAccountDTO bankAccountDTO);

    CustomerDTO getCustomer(String customerId);
    void deleteCustomer(String customerId);

    List<BankAccountDTO> listBankAccounts();

    List<OperationDTO> getOperationByBankAccountId(String id);

    List<OperationDTO> listOperation();

    BankAccountHistoryDTO getBankAccountHistory(String id, int page, int size);

    BankAccountHistoryDTO saveTransaction(TransactionDTO transactionDTO);

    List<CustomerDTO> findCustomerByKeyword(String keyword);
}
