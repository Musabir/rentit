package com.rentit.sales.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderExtensionDTO extends ResourceSupport {
    LocalDate endDate;
    PurchaseOrderDTO status;
}
