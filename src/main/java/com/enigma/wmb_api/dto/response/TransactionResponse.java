package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private Date date;
    private String customerName;
    private String tableName;
    private String transactionType;
    private List<TransactionDetailResponse> transactionDetailResponse;
}
