package com.rentit.sales.service;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.common.infrastructure.IdentifierFactory;
import com.rentit.exception.*;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.service.InventoryService;
import com.rentit.inventory.service.PlantInventoryEntryAssembler;
import com.rentit.inventory.service.PlantInventoryItemAssembler;
import com.rentit.inventory.web.dto.PlantInventoryItemDTO;
import com.rentit.reservation.domain.model.PlantReservation;
import com.rentit.reservation.domain.repository.PlantReservationRepository;
import com.rentit.reservation.service.PlantReservationAssembler;
import com.rentit.reservation.service.PlantReservationService;
import com.rentit.reservation.web.dto.PlantReservationDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderStatus;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.domain.validation.PurchaseOrderValidator;
import com.rentit.sales.web.dto.CatalogQueryDTO;
import com.rentit.sales.web.dto.PurchaseOrderDTO;
import com.rentit.sales.web.dto.PurchaseOrderViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by musabir on 04/11/17.
 */

@Service
public class SalesService {

    @Autowired
    IdentifierFactory identifierFactory;

    @Autowired
    PlantReservationService plantReservationService;

    @Autowired
    PlantReservationAssembler plantReservationAssembler;

    @Autowired
    PurchaseOrderExtensionAssembler purchaseOrderExtensionAssembler;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PlantReservationRepository plantReservationRepository;

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;

    @Autowired
    InventoryService inventoryService;


    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) throws PlantNotFoundException, BindException {
        PlantInventoryEntry plantInventoryEntry = plantInventoryEntryRepository.findAvailableById(
                purchaseOrderDTO.getPlant().get_id(),
                purchaseOrderDTO.getRentalPeriod().getStartDate(),
                purchaseOrderDTO.getRentalPeriod().getEndDate());
        if (plantInventoryEntry == null) {
            throw new PlantNotFoundException(purchaseOrderDTO.get_id());
        } else {
            Long dayDiff = Math.abs(ChronoUnit.DAYS.between(purchaseOrderDTO.getRentalPeriod().getEndDate(), purchaseOrderDTO.getRentalPeriod().getStartDate())) + 1;
            BigDecimal price = plantInventoryEntry.getPrice();

            PurchaseOrder purchaseOrder = PurchaseOrder.of(
                    identifierFactory.nextDomainObjectID(),
                    null,
                    null,
                    price.multiply(new BigDecimal(dayDiff)),
                    PurchaseOrderStatus.PENDING,
                    null,
                    null,
                    plantInventoryEntry,
                    BusinessPeriod.of(purchaseOrderDTO.getRentalPeriod().getStartDate(), purchaseOrderDTO.getRentalPeriod().getEndDate()));


            DataBinder binder = new DataBinder(purchaseOrder);
            binder.addValidators(new PurchaseOrderValidator());
            binder.validate();
            if (binder.getBindingResult().hasErrors())
                throw new BindException(binder.getBindingResult());
            purchaseOrderRepository.save(purchaseOrder);


            return purchaseOrderAssembler.toResource(purchaseOrder);
        }
    }

    public void closePurchaseOrder() throws InvoiceNotFoundException, MessagingException, PurchaseOrderNotFoundException, IOException {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        for (int i = 0; i < purchaseOrderList.size(); i++) {
            PurchaseOrder purchaseOrder = purchaseOrderList.get(i);

            LocalDate now = LocalDate.now();

            LocalDate startDate = purchaseOrder.getRentalPeriod().getStartDate();
            LocalDate endDate = purchaseOrder.getRentalPeriod().getEndDate();
            if (purchaseOrder.getStatus() == PurchaseOrderStatus.OPEN || purchaseOrder.getStatus() == PurchaseOrderStatus.CANCEL_PENDING) {
                if (endDate.isBefore(now))
                    purchaseOrder.setStatus(PurchaseOrderStatus.CLOSED);
            } else
                if (startDate.isBefore(now))
                purchaseOrder.setStatus(PurchaseOrderStatus.EXPIRED);
            purchaseOrderRepository.save(purchaseOrder);


    }

}

    public List<PurchaseOrderDTO> findAllPORest() {
        List<PurchaseOrder> purchaseOrdersList = purchaseOrderRepository.findAll();
        List<PurchaseOrderDTO> purchaseOrdersDTOList = new ArrayList<PurchaseOrderDTO>();
        for (PurchaseOrder purchaseOrder : purchaseOrdersList) {
            purchaseOrdersDTOList.add(PurchaseOrderDTO.of(
                    purchaseOrder.getId(),
                    purchaseOrder.getIssueDate(),
                    purchaseOrder.getPaymentSchedule(),
                    purchaseOrder.getTotal(),
                    "",
                    purchaseOrder.getStatus(),
                    plantInventoryEntryAssembler.toResource(purchaseOrder.getPlant()),
                    null
            ));
        }
        return purchaseOrdersDTOList;
    }

    public PurchaseOrderDTO findPOById(String id) throws PurchaseOrderNotFoundException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
        if (purchaseOrder == null) {
            throw new PurchaseOrderNotFoundException(id);
        } else {
            return purchaseOrderAssembler.toResource(purchaseOrder);
        }
    }

    public List<PlantInventoryEntry> findAvailableEntries(CatalogQueryDTO catalogQueryDTO) {
        return plantInventoryEntryRepository.findAvailableByName(catalogQueryDTO.getName(), catalogQueryDTO.getRentalPeriod().getStartDate(), catalogQueryDTO.getRentalPeriod().getEndDate());
    }

    public PurchaseOrderDTO acceptPurchaseOrder(String id) throws BindException, PlantUnavailableException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);
        purchaseOrder.handleAcceptance();
        return purchaseOrderAssembler.toResource(purchaseOrderRepository.save(purchaseOrder));
    }

    public PurchaseOrderDTO rejectPurchaseOrder(String id) {
        PurchaseOrder po = purchaseOrderRepository.findOne(id);
        po.handleRejection();
        return purchaseOrderAssembler.toResource(purchaseOrderRepository.save(po));
    }

    public PurchaseOrderDTO acceptCancelPurchaseOrder(String id) {
        PurchaseOrder po = purchaseOrderRepository.findOne(id);
        po.setStatus(PurchaseOrderStatus.CANCEL);
        return purchaseOrderAssembler.toResource(purchaseOrderRepository.save(po));
    }

    public PurchaseOrderDTO rejectCancelPurchaseOrder(String id) {
        PurchaseOrder po = purchaseOrderRepository.findOne(id);
        po.setStatus(PurchaseOrderStatus.CANCEL_REJECTED);
        return purchaseOrderAssembler.toResource(purchaseOrderRepository.save(po));
    }


    public List<PurchaseOrderViewDTO> getAllPurchaseOrderByStatus(PurchaseOrderStatus purchaseOrderStatus) {
        List<PurchaseOrderViewDTO> purchaseOrderViewDTOs = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.getPurchaseOrderByStatus(purchaseOrderStatus);
        if (purchaseOrderStatus == PurchaseOrderStatus.PENDING) {
            purchaseOrders.addAll(purchaseOrderRepository.getPurchaseOrderByStatus(PurchaseOrderStatus.PENDING_EXTENSION));
        }
        purchaseOrders.forEach(purchaseOrder -> {
            PurchaseOrderViewDTO purchaseOrderViewDTO = new PurchaseOrderViewDTO();
            purchaseOrderViewDTO.setPurchaseOrderDTO(purchaseOrderAssembler.toResource(purchaseOrder));

            purchaseOrderViewDTO.setPlantInventoryItemDTO(getPlantInventoryItemDTO(purchaseOrder));
            purchaseOrderViewDTOs.add(purchaseOrderViewDTO);
        });
        return purchaseOrderViewDTOs;
    }


    private PlantInventoryItemDTO getPlantInventoryItemDTO(PurchaseOrder purchaseOrder) {
        PlantReservation reservation = purchaseOrder.getReservation();
        if (reservation == null) {
            return null;
        }
        return plantInventoryItemAssembler.toResource(plantInventoryItemRepository.findOne(reservation.getPlant().getId()));
    }


}
