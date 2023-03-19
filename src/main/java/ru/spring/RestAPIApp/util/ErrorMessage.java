package ru.spring.RestAPIApp.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorMessage {
    public static String getErrorMessage(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error:errors) {     //текст поля каждой ошибки соединим в строку
            errorMsg.append(error.getField())
                    .append("-").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }
        return errorMsg.toString();
    }
}
