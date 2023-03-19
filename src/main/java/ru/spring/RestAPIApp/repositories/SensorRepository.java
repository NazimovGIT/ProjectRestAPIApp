package ru.spring.RestAPIApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.RestAPIApp.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    void deleteByName(String name);
    Optional<Sensor> findByName(String name);
}
