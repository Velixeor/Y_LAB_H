package org.example.repository;


import org.example.entity.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarRepository {
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public boolean deleteCar(Integer id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                cars.remove(car);
                return true;
            }
        }
        return false;
    }

    public Optional<Car> getCarById(Integer id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    public boolean updateCar(Car updatedCar) {
        for (Car car : cars) {
            if (car.getId().equals(updatedCar.getId())) {
                car.setBrand(updatedCar.getBrand());
                car.setModel(updatedCar.getModel());
                car.setYear(updatedCar.getYear());
                car.setPrice(updatedCar.getPrice());
                car.setCondition(updatedCar.getCondition());
                return true;
            }
        }
        return false;
    }

    public List<Car> findCarsByModel(String model) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getModel().equalsIgnoreCase(model)) {
                result.add(car);
            }
        }
        return result;
    }
    public List<Car> findCarsByBrand(String brand) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findCarsByYear(int year) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() == year) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findCarsByPrice(double minPrice, double maxPrice) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPrice() >= minPrice && car.getPrice() <= maxPrice) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> findCarsByCondition(String condition) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getCondition().equalsIgnoreCase(condition)) {
                result.add(car);
            }
        }
        return result;
    }
}
