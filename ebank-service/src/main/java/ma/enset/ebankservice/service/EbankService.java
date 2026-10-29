package ma.enset.ebankservice.service;

import ma.enset.ebankservice.entities.BankAccount;
import ma.enset.ebankservice.feign.CustomerRestClient;
import ma.enset.ebankservice.model.Customer;
import ma.enset.ebankservice.repository.BankAccountRepository;
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
    public List<BankAccount> getAllBankAccount(){
        return accountRepository.findAll();
    }
    public BankAccount getBankAccountById(String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account not found"));
        bankAccount.setCustomer(
                customerRestClient.getCustomerClient(bankAccount.getCustomerId())
        );
        return bankAccount;
    }
    public BankAccount save(BankAccount bankAccount){
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
