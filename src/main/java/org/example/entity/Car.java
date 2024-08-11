package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private double price;
    private String condition;
    public Car(String brand, String model, Integer year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

}
