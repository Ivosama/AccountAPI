package com.idts.accountapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private BigDecimal amountToTransfer;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    public Transaction(Account fromAccount, Account toAccount, BigDecimal amountToTransfer) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amountToTransfer = amountToTransfer;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(Account toAccount, BigDecimal amountToTransfer) {
        this.fromAccount = null;
        this.toAccount = toAccount;
        this.amountToTransfer = amountToTransfer;
        this.timestamp = LocalDateTime.now();
    }
}
