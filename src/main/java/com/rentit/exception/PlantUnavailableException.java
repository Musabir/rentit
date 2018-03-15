package com.rentit.exception;

/**
 * Created by musabir on 04/11/17.
 */
public class PlantUnavailableException extends Exception{

    private static final long serialVersionUID = 1L;

    public PlantUnavailableException() {
        super("Plant is not available.");

    }
}