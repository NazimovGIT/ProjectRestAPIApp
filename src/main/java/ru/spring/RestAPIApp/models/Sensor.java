package ru.spring.RestAPIApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "sensor_m")
public class Sensor implements Serializable {  //Serializable т.к. внешний ключ у дочерней сущности не int
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Название не должно быть пустым")
    @Size(min = 3, max = 30, message = "Название должно содержать от 3 до 30 символов")
    @Column(name = "name")
    private String name;

/*    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;*/ //не нужно, т.к. измерения конкретного сенсора не запрашиваются

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
