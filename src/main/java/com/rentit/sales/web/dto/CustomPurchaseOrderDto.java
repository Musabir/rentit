package com.rentit.sales.web.dto;

import com.rentit.common.rest.ResourceSupport;
import com.rentit.inventory.web.dto.PlantInventoryEntryDTO;
import com.rentit.sales.domain.model.PurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force=true)
@AllArgsConstructor(staticName="of")
@EqualsAndHashCode(callSuper = false)
    public class CustomPurchaseOrderDto extends ResourceSupport {
        String _id;
        BigDecimal total;
        String consumerEmail;
        PurchaseOrderStatus status;
        PlantInventoryEntryDTO plant;
        String startDate;
        String endDate;
}
