package org.example.manager;


import org.example.entity.Car;
import org.example.service.AuditService;
import org.example.service.CarService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class CarManager {
    private CarService carService;
    private AuditService auditService;


    public CarManager(CarService carService, AuditService auditService) {
        this.carService = carService;
        this.auditService = auditService;

    }

    public void manageCars(Scanner scanner) {
        System.out.println("Меню автомобилей");
        System.out.println("1. Добавить автомобиль");
        System.out.println("2. Редактировать автомобиль");
        System.out.println("3. Удалить автомобиль");
        System.out.println("4. Просмотреть все автомобили");
        System.out.println("5. Меню поиска");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                addCar(scanner);
                break;
            case "2":
                updateCar(scanner);
                break;
            case "3":
                deleteCar(scanner);
                break;
            case "4":
                viewAllCars();
                break;
            case "5":
                findAllCars(scanner);
                break;
            default:
                System.out.println("Введите число из предложенных");
        }
    }
    private void findAllCars(Scanner scanner){
        System.out.println("Меню поиска автомобилей");
        System.out.println("1. По марке");
        System.out.println("2. По году выпуска");
        System.out.println("3. По модели");
        System.out.println("4. По цене");
        System.out.println("5. По состоянию");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewAllCarsByBrand(scanner);
                break;
            case "2":
                viewAllCarsByYear(scanner);
                break;
            case "3":
                viewAllCarsByModel(scanner);
                break;
            case "4":
                viewAllCarsByPrice(scanner);
                break;
            case "5":
                viewAllCarsByCondition(scanner);
                break;
            default:
                System.out.println("Введите число из предложенных");
        }


    }
    private void addCar(Scanner scanner) {
        System.out.print("Марка: ");
        String brand = scanner.nextLine();
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Год выпуска: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Цена: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Состояние: ");
        String condition = scanner.nextLine();
        Car car = new Car(brand, model, year, price, condition);
        carService.addCar(car);
        auditService.addAction("Добавление автомобиля", "admin");
        System.out.println("Автомобиль добавлен.");
    }

    private void updateCar(Scanner scanner) {
        System.out.print("ID автомобиля: ");
        int carIdToUpdate = Integer.parseInt(scanner.nextLine());
        Optional<Car> carToUpdate = carService.getCarById(carIdToUpdate);
        if (carToUpdate.isPresent()) {
            Car car = carToUpdate.get();
            System.out.print("Новая марка: ");
            car.setBrand(scanner.nextLine());
            System.out.print("Новая модель: ");
            car.setModel(scanner.nextLine());
            System.out.print("Новый год выпуска: ");
            car.setYear(Integer.parseInt(scanner.nextLine()));
            System.out.print("Новая цена: ");
            car.setPrice(Double.parseDouble(scanner.nextLine()));
            System.out.print("Новое состояние: ");
            car.setCondition(scanner.nextLine());
            carService.updateCar(car);
            auditService.addAction("Редактирование автомобиля", "admin");
            System.out.println("Автомобиль обновлен.");
        } else {
            System.out.println("Автомобиль не найден.");
        }
    }

    private void deleteCar(Scanner scanner) {
        System.out.print("ID автомобиля: ");
        int carIdToDelete = Integer.parseInt(scanner.nextLine());
        if (carService.deleteCar(carIdToDelete)) {
            auditService.addAction("Удаление автомобиля", "admin");
            System.out.println("Автомобиль удален.");
        } else {
            System.out.println("Автомобиль не найден.");
        }
    }

    private void viewAllCars() {
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private void viewAllCarsByBrand(Scanner scanner) {
        System.out.print("Введите марку: ");
        String brand =scanner.nextLine();
        List<Car> cars = carService.searchCarsByBrand(brand);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private void viewAllCarsByYear(Scanner scanner) {
        System.out.print("Введите год: ");
        Integer year =Integer.valueOf(scanner.nextLine());
        List<Car> cars = carService.searchCarsByYear(year);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private void viewAllCarsByModel(Scanner scanner) {
        System.out.print("Введите модель: ");
        String model =scanner.nextLine();
        List<Car> cars = carService.searchCarsByModel(model);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private void viewAllCarsByCondition(Scanner scanner) {
        System.out.print("Введите состояние: ");

        String conditions =scanner.nextLine();
        List<Car> cars = carService.searchCarsByCondition(conditions);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private void viewAllCarsByPrice(Scanner scanner) {
        System.out.print("Введите верхнюю границу: ");
        Double max =Double.valueOf(scanner.nextLine());
        System.out.print("Введите нижнюю границу : ");
        Double min =Double.valueOf(scanner.nextLine());
        List<Car> cars = carService.searchCarsByPrice(min,max);
        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
