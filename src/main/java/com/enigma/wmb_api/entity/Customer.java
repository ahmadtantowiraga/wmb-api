package com.enigma.wmb_api.entity;

import com.enigma.wmb_api.constant.ConstantTable;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= ConstantTable.CUSTOMER_TABLE)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="customer_name", columnDefinition = "VARCHAR(50)")
    private String customerName;

    @Column(name="mobile_phone_no", columnDefinition = "VARCHAR(20)")
    private String mobilePhoneNo;

    @Column(name = "status")
    private Boolean status;

    @JoinColumn(name = "user_account_id", unique = true)
    @OneToOne
    private UserAccount userAccount;


}
