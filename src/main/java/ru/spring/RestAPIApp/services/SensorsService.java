package ru.spring.RestAPIApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.RestAPIApp.models.Sensor;
import ru.spring.RestAPIApp.repositories.SensorRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorRepository sensorRepository;
    @Autowired
    public SensorsService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }
    @Transactional
    public void register(Sensor sensor){
        sensorRepository.save(sensor);
    }
    @Transactional
    public void delete(String name){
        sensorRepository.deleteByName(name);
    }
}
