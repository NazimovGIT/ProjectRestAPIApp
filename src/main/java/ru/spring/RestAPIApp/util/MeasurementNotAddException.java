package ru.spring.RestAPIApp.util;

public class MeasurementNotAddException  extends RuntimeException{
     public MeasurementNotAddException(String msg){
         super(msg);
     }
}
