package org.example.service;


import org.example.entity.Car;
import org.example.repository.CarRepository;

import java.util.List;
import java.util.Optional;


public class CarService {

    private CarRepository carRepository;
    private int id;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
        id=0;
    }

    public void addCar(Car car) {
        car.setId(id);
        id++;
        carRepository.addCar(car);
    }

    public Optional<Car> getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public boolean updateCar(Car updatedCar) {
        return carRepository.updateCar(updatedCar);
    }

    public boolean deleteCar(int id) {
        return carRepository.deleteCar(id);
    }

    public List<Car> searchCarsByBrand(String brand) {
        return carRepository.findCarsByBrand(brand);
    }

    public List<Car> searchCarsByModel(String model) {
        return carRepository.findCarsByModel(model);
    }

    public List<Car> searchCarsByYear(int year) {
        return carRepository.findCarsByYear(year);
    }

    public List<Car> searchCarsByPrice(double minPrice, double maxPrice) {
        return carRepository.findCarsByPrice(minPrice, maxPrice);
    }

    public List<Car> searchCarsByCondition(String condition) {
        return carRepository.findCarsByCondition(condition);
    }
}
