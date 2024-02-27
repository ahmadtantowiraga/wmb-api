package com.enigma.wmb_api.entity;

import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="m_trans_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="description", columnDefinition = "VARCHAR(50)")
    private String description;

}
