package com.rentit.exception;

/**
 * Created by musabir on 04/11/17.
 */
public class PurchaseOrderCancellationException extends Exception {

    private static final long serialVersionUID = 1L;

    public PurchaseOrderCancellationException() {
        super("Plant cancelation is not available.");

    }
}