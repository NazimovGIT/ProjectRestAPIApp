package ru.spring.RestAPIApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.RestAPIApp.models.Measurement;
import ru.spring.RestAPIApp.repositories.MeasurementsRepository;
import ru.spring.RestAPIApp.util.MeasurementsNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }
    @Autowired

    public List<Measurement> getMeasurements(){
        Optional <List<Measurement>> measurements = Optional.of(measurementsRepository.findAll());
        return measurements.orElseThrow(MeasurementsNotFoundException::new);
    }
    public Long getRainyDaysCount(){
        return measurementsRepository.countByRainingTrue();
    }
    @Transactional
    public void add(Measurement measurement){
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }
    private void enrichMeasurement(Measurement measurement){
        //из JSON в теле measurement пришел объект sensor, не известный Hibernate persistence context,
        //достанем из контекста объект sensor с пришедшим названием и привяжем к measurement
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementAt(LocalDateTime.now());
    }
}
