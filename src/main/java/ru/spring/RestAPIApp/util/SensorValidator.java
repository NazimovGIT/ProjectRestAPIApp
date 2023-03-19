package ru.spring.RestAPIApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spring.RestAPIApp.dto.SensorDTO;
import ru.spring.RestAPIApp.services.SensorsService;
@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;
    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }
    @Override
    //метод, указывающий к какому классу валидатор применяется
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }
    //метод валидации для объектов нашего класса
    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;       //сюда приходит наш объект для валидации

        if (sensorsService.findByName(sensorDTO.getName()).isPresent()) {    //еcли объект с таким именем уже есть в БД
            // поместим в errors свою ошибку, которую далее упакуем в JSON
            errors.rejectValue("name",            //какое поле вызвало ошибку
                    "Сенсор с таким названием уже существует");             //текст ошибки
        }
    }
}
