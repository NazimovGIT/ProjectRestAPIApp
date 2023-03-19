package ru.spring.RestAPIApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.RestAPIApp.dto.MeasurementDTO;
import ru.spring.RestAPIApp.dto.MeasurementsResponse;
import ru.spring.RestAPIApp.models.Measurement;
import ru.spring.RestAPIApp.services.MeasurementsService;
import ru.spring.RestAPIApp.services.SensorsService;
import ru.spring.RestAPIApp.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final MeasurmentValidator measurmentValidator;
    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService,
                                  MeasurmentValidator measurmentValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.measurmentValidator = measurmentValidator;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult){
        measurmentValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new MeasurementNotAddException(ErrorMessage.getErrorMessage(bindingResult));
        }
        measurementsService.add(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);    //HTTP ответ с пустым телом и со статусом 200 (успешно)
    }
    @GetMapping()
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementsService.getMeasurements().stream().
                map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }
    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementsService.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementsNotFoundException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Измерений не найдено", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    //NOT_FOUND - статус ошибки 404
    }
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  //BAD_REQUEST - статус ошибки 400
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
