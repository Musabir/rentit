package com.rentit.sales.web.dto;

import com.rentit.inventory.web.dto.PlantInventoryItemDTO;
import lombok.Data;

@Data
public class PurchaseOrderViewDTO {
    PurchaseOrderDTO purchaseOrderDTO;
    PlantInventoryItemDTO plantInventoryItemDTO;
}
