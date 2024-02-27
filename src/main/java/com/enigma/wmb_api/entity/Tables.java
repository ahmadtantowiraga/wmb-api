package com.enigma.wmb_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_table")
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="table_name", columnDefinition = "VARCHAR(3)")
    private String tableName;
}
