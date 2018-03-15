package com.rentit.inventory.web.controller;

import com.rentit.inventory.service.InventoryService;
import com.rentit.inventory.web.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.web.dto.PlantInventoryItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@Controller
@RequestMapping("/dashboard")
public class PlantInventoryEntryController {
    @Autowired
    InventoryService inventoryService;


    @GetMapping("/plants")
    public String list(Model model) {
        model.addAttribute("plants", inventoryService.findAllInventoryEntries());
        return "plants/list";
    }






}
