package org.example.service;


import org.example.entity.Car;
import org.example.repository.CarRepository;

import java.util.List;
import java.util.Optional;


public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;

    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public Optional<Car> getCarById(int id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car updateCar(Car updatedCar) {
        return carRepository.update(updatedCar);
    }

    public void deleteCar(int id) {
        carRepository.deleteById(id);
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
