package com.enigma.wmb_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_table")
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name ="menu_id")
    private Menu menu;

    @Column(name = "qty")
    private Integer qty;

    @Column(name ="price")
    private Long price;

}
