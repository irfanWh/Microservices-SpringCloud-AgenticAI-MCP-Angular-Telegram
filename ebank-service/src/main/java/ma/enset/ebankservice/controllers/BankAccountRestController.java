package ma.enset.ebankservice.controllers;

import ma.enset.ebankservice.entities.BankAccount;
import ma.enset.ebankservice.service.EbankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankAccountRestController {
    private EbankService ebankService;

    public BankAccountRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccount(){
        return ebankService.getAllBankAccount();
    }
    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccountById(@PathVariable String id){
        return ebankService.getBankAccountById(id);
    }
    @PostMapping("/accounts")
    public BankAccount save(@RequestBody BankAccount bankAccount){
        return ebankService.save(bankAccount);
    }
}
