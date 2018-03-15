package com.rentit.exception;
/**
 * Created by musabir on 04/11/17.
 */

public class PlantNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public PlantNotFoundException(String id) {
        super(String.format("Plant not found! (Plant id: %s)", id));

    }


}
