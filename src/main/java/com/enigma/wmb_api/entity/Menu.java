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
@Table(name= ConstantTable.MENU_TABLE)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name="menu_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String menuName;

    @Column(name="price", nullable = false, columnDefinition = "BIGINT CHECK (price >= 0)")
    private Long price;
}
