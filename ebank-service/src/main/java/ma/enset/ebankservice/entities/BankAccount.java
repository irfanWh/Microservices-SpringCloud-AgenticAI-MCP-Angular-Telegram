package ma.enset.ebankservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import ma.enset.ebankservice.model.Customer;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private double balance;
    private String type;
    private Long customerId;
    @Transient //cette attribut exist dans la class mais exist pas dans la base de donnees
    private Customer customer;
}
