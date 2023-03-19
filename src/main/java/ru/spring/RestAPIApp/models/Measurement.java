package ru.spring.RestAPIApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement_s")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @DecimalMin(value = "-99.9", message = "Температура должна быть больше, чем -100")
    @DecimalMax(value = "99.9", message = "Температура должна быть меньше, чем 100")
    @Column(name = "value")
    private Double value;   //обертка Double, чтобы сработало @NotNull, если не придет значение в value, иначе в double value по умолчанию занесется 0
    @NotNull
    @Column(name = "raining")
    private Boolean raining;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    private Sensor sensor;

    @Column(name = "measurement_at")
    private LocalDateTime measurementAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getMeasurementAt() {
        return measurementAt;
    }

    public void setMeasurementAt(LocalDateTime measurementAt) {
        this.measurementAt = measurementAt;
    }
}
