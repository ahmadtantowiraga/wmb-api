package com.enigma.wmb_api.entity;

import com.enigma.wmb_api.constant.ConstantTable;
import com.enigma.wmb_api.constant.TransactionTypeID;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= ConstantTable.TRANSACTION_TYPE_TABLE)
public class TransactionType {
    @Id
    @Enumerated(EnumType.STRING)
    private TransactionTypeID id;

    @Column(name="description", nullable = false, columnDefinition = "VARCHAR(50)")
    private String description;

}
