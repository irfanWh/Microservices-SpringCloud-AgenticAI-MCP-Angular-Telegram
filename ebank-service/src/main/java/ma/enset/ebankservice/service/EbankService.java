package ma.enset.ebankservice.service;

import ma.enset.ebankservice.entities.BankAccount;
import ma.enset.ebankservice.feign.CustomerRestClient;
import ma.enset.ebankservice.model.Customer;
import ma.enset.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.customerRestClient = customerRestClient;
        this.accountRepository = accountRepository;
    }
    @McpTool(description = "get all bank account")
    public List<BankAccount> getAllBankAccount(){
        return accountRepository.findAll();
    }
    @McpTool(description = "get bank account by id")
    public BankAccount getBankAccountById(@McpToolParam(description = "bank account id") String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account not found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerClient(bankAccount.getCustomerId())
        );
        return bankAccount;
    }
    @McpTool(description = "save bank account")
    public BankAccount save(@McpToolParam(description = "bank account to save (balance, type, customerId)") BankAccount bankAccount){
        try {
            Customer customer = customerRestClient.getCustomerClient(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
            return accountRepository.save(bankAccount);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
