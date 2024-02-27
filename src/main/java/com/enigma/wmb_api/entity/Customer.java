package com.enigma.wmb_api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="customer_name", columnDefinition = "VARCHAR(50)")
    private String customerName;

    @Column(name="mobile_phone_no", columnDefinition = "VARCHAR(20)")
    private String mobilePhoneNo;

    @Column(name="is_member")
    private Boolean isMember;

}
