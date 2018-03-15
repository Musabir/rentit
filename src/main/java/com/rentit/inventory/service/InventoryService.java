package com.rentit.inventory.service;

import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.common.infrastructure.IdentifierFactory;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.web.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.web.dto.PlantInventoryItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    IdentifierFactory identifierFactory;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;


    public List<PlantInventoryEntryDTO> findAllInventoryEntries() {

        List<PlantInventoryEntry> plantInventoryEntries = plantInventoryEntryRepository.findAll();
        List<PlantInventoryEntryDTO> plantInventoryEntryDTOs =  new ArrayList<PlantInventoryEntryDTO>();

        plantInventoryEntries.forEach(plantInventoryEntry -> {
            plantInventoryEntryDTOs.add(plantInventoryEntryAssembler.toResource(plantInventoryEntry));
        });
        return plantInventoryEntryDTOs;
    }

    public List<PlantInventoryEntryDTO> findAvailablePlantsRest(String name, LocalDate startDate, LocalDate endDate) {
        List<PlantInventoryEntry> plantInventoryEntryList = plantInventoryEntryRepository.findAvailableByName(name, startDate, endDate);
        List<PlantInventoryEntryDTO> plantInventoryEntryDTOList =  new ArrayList<PlantInventoryEntryDTO>();
        for(PlantInventoryEntry plantInventoryEntry: plantInventoryEntryList){
            plantInventoryEntryDTOList.add(plantInventoryEntryAssembler.toResource(plantInventoryEntry));
        }
        return plantInventoryEntryDTOList;
    }

    public PlantInventoryEntryDTO findPlant(String id) {
        return plantInventoryEntryAssembler.toResource(plantInventoryEntryRepository.findOne(id));
    }



}
