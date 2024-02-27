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
@Table(name="m_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="menu_name", columnDefinition = "VARCHAR(100)")
    private String menuName;

    @Column(name="price", columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;
}
