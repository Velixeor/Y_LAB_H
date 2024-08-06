package org.example.entity;


public class Car {
    private Integer id;
    private String brand;
    private String model;
    private Integer year;
    private double price;
    private String condition;

    public Car(Integer id, String brand, String model, Integer year, double price, String condition) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                '}';
    }

    public Car(String brand, String model, Integer year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
