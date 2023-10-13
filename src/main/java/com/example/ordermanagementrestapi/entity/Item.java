package com.example.ordermanagementrestapi.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import com.example.ordermanagementrestapi.entity.enums.MeasuringUnitType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item")
@TypeDefs({
        @TypeDef(name = "json",typeClass = JsonType.class)
})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {

    @Id
    @Column(name = "item_id", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemID;

    @Column(name = "item_name", length = 100, nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_type", length = 25, nullable = false)
    private MeasuringUnitType measuringUnitType;

    @Column(name = "balance_qty", length = 50, nullable = false)
    private double balanceQty;

    @Column(name = "supplier_price", length = 50, nullable = false)
    private double supplierPrice;

    @Column(name = "seller_price", length = 50, nullable = false)
    private double sellerPrice;

    @Column(name = "active_state", columnDefinition = "TINYINT default 1")
    private boolean activeState;


    @OneToMany(mappedBy="items")
    private Set<OrderDetails> orderDetails;


}
