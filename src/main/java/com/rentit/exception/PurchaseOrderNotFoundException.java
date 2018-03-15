package com.rentit.exception;

/**
 * Created by musabir on 04/11/17.
 */

public class PurchaseOrderNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PurchaseOrderNotFoundException(String id) {
        super(String.format("PO not found! (PO id: %s)", id));

    }
}
