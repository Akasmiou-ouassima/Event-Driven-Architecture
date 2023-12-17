package org.sid.comptecqrseventsourcing.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreditAccountRequestDto {
    private String id;
    private double amount;
    private String currency;
}
