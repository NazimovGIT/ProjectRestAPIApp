package ru.spring.RestAPIApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spring.RestAPIApp.models.Measurement;


@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    Long countByRainingTrue();
}
