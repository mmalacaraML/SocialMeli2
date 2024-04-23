package com.bootcampW22.EjercicioGlobal.exception;

public class BadRequestException extends RuntimeException{
    BadRequestException(){

    }

    public BadRequestException(String message){
        super(message);
    }
}
