package com.rentit.exception;

import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;

/**
 * Created by musabir on 04/11/17.
 */
public class PurchaseOrderExtensionNotFoundException extends Exception {

    public PurchaseOrderExtensionNotFoundException(String s){
        super(s);
    }
}
