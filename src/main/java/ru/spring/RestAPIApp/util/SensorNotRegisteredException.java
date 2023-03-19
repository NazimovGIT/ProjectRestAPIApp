package ru.spring.RestAPIApp.util;

public class SensorNotRegisteredException extends RuntimeException{
    public SensorNotRegisteredException(String msg){
        super(msg);
    }
}
