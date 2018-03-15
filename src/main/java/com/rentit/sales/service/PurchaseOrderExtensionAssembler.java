package com.rentit.sales.service;

import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;

import com.rentit.sales.web.dto.PurchaseOrderExtensionDTO;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

/**
 * Created by musabir on 04/11/17.
 */

@Service
public class PurchaseOrderExtensionAssembler extends ResourceAssemblerSupport<PurchaseOrderExtension, PurchaseOrderExtensionDTO> {

    public PurchaseOrderExtensionAssembler() {
        super(PurchaseOrderExtension.class, PurchaseOrderExtensionDTO.class);
    }

    @Override
    public PurchaseOrderExtensionDTO toResource(PurchaseOrderExtension extensionRequest) {
        PurchaseOrderExtensionDTO purchaseOrderExtensionDTO = createResourceWithId(extensionRequest.getId(), extensionRequest);
        purchaseOrderExtensionDTO.setEndDate(extensionRequest.getEndDate());
        return purchaseOrderExtensionDTO;
    }
}
