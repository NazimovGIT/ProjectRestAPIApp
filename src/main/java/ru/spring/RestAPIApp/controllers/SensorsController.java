package ru.spring.RestAPIApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.RestAPIApp.dto.SensorDTO;
import ru.spring.RestAPIApp.models.Sensor;
import ru.spring.RestAPIApp.services.SensorsService;
import ru.spring.RestAPIApp.util.SensorErrorResponse;
import ru.spring.RestAPIApp.util.SensorNotRegisteredException;
import ru.spring.RestAPIApp.util.SensorValidator;

import java.util.Optional;

import static ru.spring.RestAPIApp.util.ErrorMessage.getErrorMessage;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;
    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator,
                             ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult){
        sensorValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new SensorNotRegisteredException(getErrorMessage(bindingResult));
        }
        sensorsService.register(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);    //HTTP ответ с пустым телом и со статусом 200 (успешно)
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("name") String name){
        Optional<Sensor> sensor = sensorsService.findByName(name);
        if (sensor.isEmpty()){
            throw new SensorNotRegisteredException("Сенсор с таким названием не зарегистрирован");
        }
        sensorsService.delete(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    //ловим исключение с @RequestBody @Valid SensorDTO sensorDTO и возвращаем объект JSON из объекта с ошибкой
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotRegisteredException e){
        SensorErrorResponse response = new SensorErrorResponse
                        (e.getMessage(),    //исключение уже приняло сообщение при выбросе
                        System.currentTimeMillis());

        //в HTTP в теле ответа будет response и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);    //BAD_REQUEST - статус ошибки 400
    }
    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
