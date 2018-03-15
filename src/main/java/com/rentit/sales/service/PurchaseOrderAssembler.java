package com.rentit.sales.service;


import com.rentit.common.application.dto.BusinessPeriodDTO;

import com.rentit.inventory.service.PlantInventoryEntryAssembler;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.web.controller.SalesRestController;
import com.rentit.sales.web.dto.PurchaseOrderDTO;
import com.rentit.sales.web.dto.PurchaseOrderExtensionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpMethod.*;

import com.rentit.common.rest.ExtendedLink;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDate;

/**
 * Created by musabir on 04/11/17.
 */

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder,PurchaseOrderDTO> {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    UriTemplate uriTemplate;

    public PurchaseOrderAssembler() {
        super(SalesRestController.class, PurchaseOrderDTO.class);

        AnnotationMappingDiscoverer discoverer = new AnnotationMappingDiscoverer(RequestMapping.class);

        try {
            String mapping = discoverer.getMapping(SalesRestController.class,
                    SalesRestController.class.getMethod("fetchPurchaseOrder", String.class));

            uriTemplate = new UriTemplate(mapping);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public String resolveId(Link link){
        return uriTemplate.match(link.getHref()).get("id");
    }

    @Override
    public PurchaseOrderDTO toResource(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO purchaseOrderDTO = createResourceWithId(purchaseOrder.getId(), purchaseOrder);
        purchaseOrderDTO.set_id(purchaseOrder.getId());
        purchaseOrderDTO.setPlant(plantInventoryEntryAssembler.toResource(purchaseOrder.getPlant()));
        purchaseOrderDTO.setRentalPeriod(BusinessPeriodDTO.of(purchaseOrder.getRentalPeriod().getStartDate(), purchaseOrder.getRentalPeriod().getEndDate()));
        purchaseOrderDTO.setTotal(purchaseOrder.getTotal());
        purchaseOrderDTO.setStatus(purchaseOrder.getStatus());
        purchaseOrderDTO.setIssueDate(purchaseOrder.getIssueDate());
        purchaseOrderDTO.setPaymentSchedule(purchaseOrder.getPaymentSchedule());

       return purchaseOrderDTO;
    }
}
