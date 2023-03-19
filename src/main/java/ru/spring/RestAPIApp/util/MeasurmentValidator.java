package ru.spring.RestAPIApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spring.RestAPIApp.dto.MeasurementDTO;
import ru.spring.RestAPIApp.services.SensorsService;

@Component
public class MeasurmentValidator implements Validator {
    private final SensorsService sensorsService;
    @Autowired
    public MeasurmentValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }
    @Override
    //метод, указывающий к какому классу валидатор применяется
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }
    //метод валидации для объектов нашего класса
    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;       //сюда приходит наш объект для валидации

        if (measurementDTO.getSensor() == null) return;
        if (sensorsService.findByName(measurementDTO.getSensor().getName()).isEmpty()) {    //еcли объекта с таким именем нет в БД
            // поместим в errors свою ошибку, которую далее упакуем в JSON
            errors.rejectValue("sensor",            //какое поле вызвало ошибку
                    "Сенсор с таким названием не зарегистрирован");             //текст ошибки
        }
    }
}
