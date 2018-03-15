package com.rentit.reservation.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.sales.domain.model.PurchaseOrder;
import lombok.*;

import javax.persistence.*;
/**
 * Created by musabir on 04/11/17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(force=true,access= AccessLevel.PRIVATE)
@AllArgsConstructor(staticName="of")
public class PlantReservation {
    @Id
    String id;

    @Embedded
    BusinessPeriod schedule;

    @ManyToOne
    PlantInventoryItem plant;

    @Override
    public String toString() {
        return "PlantReservation{" +
                "id='" + id + '\'' +
                ", schedule=" + schedule +
                ", plant=" + plant +
                '}';
    }
}
