package com.bootcampW22.EjercicioGlobal.exception;

public class AlreadyInUseException extends RuntimeException{

    public AlreadyInUseException(){

    }

    public AlreadyInUseException(String message){
        super(message);
    }
}
