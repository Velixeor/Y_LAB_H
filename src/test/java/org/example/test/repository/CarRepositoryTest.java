package org.example.test.repository;


import org.example.entity.Car;
import org.example.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    public void testAddCar() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        carRepository.addCar(car);

        List<Car> cars = carRepository.getAllCars();
        assertThat(cars).contains(car);
    }

    @Test
    public void testGetAllCars() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Honda", "Civic", 2019, 20000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.getAllCars();
        assertThat(cars).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    public void testDeleteCar() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        carRepository.addCar(car);

        boolean deleted = carRepository.deleteCar(1);
        List<Car> cars = carRepository.getAllCars();

        assertThat(deleted).isTrue();
        assertThat(cars).doesNotContain(car);
    }

    @Test
    public void testDeleteCarNotFound() {
        boolean deleted = carRepository.deleteCar(1);
        assertThat(deleted).isFalse();
    }

    @Test
    public void testGetCarById() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        carRepository.addCar(car);

        Optional<Car> foundCar = carRepository.getCarById(1);
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get()).isEqualTo(car);
    }

    @Test
    public void testGetCarByIdNotFound() {
        Optional<Car> foundCar = carRepository.getCarById(1);
        assertThat(foundCar).isNotPresent();
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        carRepository.addCar(car);

        Car updatedCar = new Car(1, "Toyota", "Corolla", 2021, 23000.00, "Used");
        boolean updated = carRepository.updateCar(updatedCar);

        assertThat(updated).isTrue();
        assertThat(carRepository.getCarById(1)).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getModel()).isEqualTo("Corolla");
                    assertThat(c.getYear()).isEqualTo(2021);
                    assertThat(c.getPrice()).isEqualTo(23000.00);
                    assertThat(c.getCondition()).isEqualTo("Used");
                });
    }

    @Test
    public void testUpdateCarNotFound() {
        Car updatedCar = new Car(1, "Toyota", "Corolla", 2021, 23000.00, "Used");
        boolean updated = carRepository.updateCar(updatedCar);

        assertThat(updated).isFalse();
    }

    @Test
    public void testFindCarsByModel() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Toyota", "Camry", 2019, 24000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.findCarsByModel("Camry");
        assertThat(cars).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    public void testFindCarsByBrand() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Toyota", "Corolla", 2019, 22000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.findCarsByBrand("Toyota");
        assertThat(cars).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    public void testFindCarsByYear() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Honda", "Civic", 2020, 20000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.findCarsByYear(2020);
        assertThat(cars).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    public void testFindCarsByPrice() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Honda", "Civic", 2019, 20000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.findCarsByPrice(20000.00, 25000.00);
        assertThat(cars).containsExactlyInAnyOrder(car1, car2);
    }

    @Test
    public void testFindCarsByCondition() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000.00, "New");
        Car car2 = new Car(2, "Honda", "Civic", 2019, 20000.00, "Used");
        carRepository.addCar(car1);
        carRepository.addCar(car2);

        List<Car> cars = carRepository.findCarsByCondition("Used");
        assertThat(cars).containsExactly(car2);
    }
}
