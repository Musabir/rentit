package com.rentit.inventory.domain.repository;

import com.rentit.inventory.domain.model.EquipmentCondition;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;

import java.time.LocalDate;
import java.util.List;

public interface CustomPlantInventoryItemRepository {
    List<PlantInventoryItem> findByAvailablity(PlantInventoryEntry pie, LocalDate startDate, LocalDate endDate);
}
