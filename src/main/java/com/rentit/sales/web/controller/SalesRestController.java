package com.rentit.sales.web.controller;

import com.rentit.common.infrastructure.IdentifierFactory;
import com.rentit.exception.*;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.service.InventoryService;
import com.rentit.inventory.web.dto.PlantInventoryEntryDTO;
import com.rentit.sales.domain.model.PurchaseOrderStatus;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.service.PurchaseOrderAssembler;
import com.rentit.sales.service.SalesService;
import com.rentit.sales.web.dto.CatalogQueryDTO;
import com.rentit.sales.web.dto.PurchaseOrderDTO;
import com.rentit.sales.web.dto.PurchaseOrderViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/sales/orders")
public class SalesRestController {
    @Autowired
    InventoryService inventoryService;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    SalesService salesService;

    @Autowired
    IdentifierFactory identifierFactory;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;


    @GetMapping
    public List<PlantInventoryEntryDTO> findAllOrders(){
        return inventoryService.findAllInventoryEntries();
    }
    @GetMapping("/available/{catalog}")
    public List<PlantInventoryEntry> findAllAvailableOrders(@PathVariable("catalog")CatalogQueryDTO catalog){
        return salesService.findAvailableEntries(catalog);
    }

    @GetMapping("/openandclosedorders")
    public List<PurchaseOrderDTO> findAllOpenAndClosedOrders(){
        List<PurchaseOrderDTO> list = new ArrayList<>();
        list.addAll(salesService.findAllPORest());
        return list;
    }


    @PostMapping("{purchase}")
    public PlantInventoryEntry findByIdOrders(@PathVariable("purchase")PurchaseOrderDTO purchaseOrderDTO) throws PurchaseOrderNotFoundException {

        PlantInventoryEntry PlantInventoryEntry = plantInventoryEntryRepository.findAvailableById(
                purchaseOrderDTO.getPlant().get_id(),
                purchaseOrderDTO.getRentalPeriod().getStartDate(),
                purchaseOrderDTO.getRentalPeriod().getEndDate());
        return PlantInventoryEntry;
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseOrderDTO fetchPurchaseOrder(@PathVariable("id") String id) throws PurchaseOrderNotFoundException {
        return salesService.findPOById(id);
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO partialPODTO) throws PlantNotFoundException, BindException {
        PurchaseOrderDTO newlyCreatedPODTO = salesService.createPurchaseOrder(partialPODTO);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI(newlyCreatedPODTO.getId().getHref()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PurchaseOrderDTO>(newlyCreatedPODTO, headers, HttpStatus.CREATED);
    }

    @PostMapping("{id}/accept")
    public PurchaseOrderDTO acceptPurchaseOrder(@PathVariable String id) throws
            BindException, PlantUnavailableException {
        return salesService.acceptPurchaseOrder(id);
    }




    @ExceptionHandler(PlantNotFoundException.class)
    public ResponseEntity<String> handlePlantNotFoundException(PlantNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseOrderCancellationException.class)
    public ResponseEntity<String> handlePurchaseOrderCancellationException(PurchaseOrderCancellationException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseOrderNotFoundException.class)
    public ResponseEntity<String> handlePurchaseOrderNotFoundException(PurchaseOrderNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PlantUnavailableException.class, InvalidRentalPeriodException.class})
    public ResponseEntity<String> handleBadRequest(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<String> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseOrderExtensionNotFoundException.class)
    public ResponseEntity<String> handlePurchaseOrderExtensionNotFoundException(PurchaseOrderExtensionNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
