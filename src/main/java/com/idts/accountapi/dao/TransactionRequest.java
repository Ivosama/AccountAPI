package com.idts.accountapi.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @JsonProperty
    private Long fromAccountId;
    @JsonProperty
    private Long toAccountId;
    @JsonProperty
    private BigDecimal amountToTransfer;
}
