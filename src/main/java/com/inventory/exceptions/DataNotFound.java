package com.inventory.exceptions;

public class DataNotFound extends RuntimeException{

    public DataNotFound(String message){
        super(message);
    }
}
