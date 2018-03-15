package com.rentit.sales.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.reservation.domain.model.PlantReservation;
import com.rentit.sales.web.dto.PurchaseOrderExtensionDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(force=true,access= AccessLevel.PROTECTED)
@AllArgsConstructor(staticName="of")
public class PurchaseOrder {
    @Id
    String id;

    LocalDate issueDate;
    LocalDate paymentSchedule;

    BigDecimal total;

    @Enumerated(EnumType.STRING)
    PurchaseOrderStatus status;
//
//    @OneToMany
//    List<PlantReservation> reservations;


    @OneToOne
    PlantReservation reservation;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PurchaseOrderExtension> extensions;

    @ManyToOne
    PlantInventoryEntry plant;
//    @ManyToMany
//    List<PlantInventoryEntry> plants;


    @Embedded
    BusinessPeriod rentalPeriod;


    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id='" + id + '\'' +
                ", issueDate=" + issueDate +
                ", paymentSchedule=" + paymentSchedule +
                ", total=" + total +
                ", status=" + status +
                ", extensions=" + extensions +
                ", plant=" + plant.getId() +
                ", plant=" + plant.getName() +
                ", rentalPeriod=" + rentalPeriod +
                '}';
    }

    public static PurchaseOrder of(String id, BusinessPeriod rentalPeriod, PlantInventoryEntry plant) {
        PurchaseOrder po = new PurchaseOrder();
        po.id = id;
        po.plant = plant;
        po.rentalPeriod = rentalPeriod;
        po.status = PurchaseOrderStatus.CREATED;
        return po;
    }

    public void confirmReservation(PlantReservation plantReservation, BigDecimal price) {
        reservation = plantReservation;
        rentalPeriod = plantReservation.getSchedule();
        total = price.multiply(BigDecimal.valueOf(plantReservation.getSchedule().numberOfWorkingDays()));
        status = PurchaseOrderStatus.OPEN;
    }

    public void handleRejection() {
        status = PurchaseOrderStatus.REJECTED;
    }


    public void handleAcceptance() {
        status = PurchaseOrderStatus.OPEN;
    }

}
