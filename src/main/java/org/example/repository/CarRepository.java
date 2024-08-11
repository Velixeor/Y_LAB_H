package org.example.repository;


import org.example.entity.Car;
import org.example.entity.Role;
import org.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarRepository implements CRUDRepository<Car, Integer> {


    @Override
    public Car save(Car car) {
        String insertSQL = "INSERT INTO car_shop.car (brand, model, year, price, condition) VALUES (?, ?, ?,?,?) RETURNING id";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setInt(4,(int)car.getPrice());
            statement.setString(5,car.getCondition());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int newId = resultSet.getInt("id");
                car.setId(newId);
                return car;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось создать машину");
        }
        return null;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String findAllSQL = "SELECT * FROM car_shop.car";

        try(Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public Optional<Car> findById(Integer id) {
        String getByIdSQL = "SELECT * FROM car_shop.car WHERE id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByIdSQL);){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Car car=new Car();
                car.setId(resultSet.getInt("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setYear(resultSet.getInt("year"));
                car.setPrice((int)resultSet.getDouble("price"));
                car.setModel(resultSet.getString("model"));
                car.setCondition(resultSet.getString("condition"));
                return Optional.of(car);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Car update(Car car) {
        String updateSQL = "UPDATE car_shop.car SET brand = ?, model = ?, year = ?, price = ?, condition = ? WHERE id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setString(5, car.getCondition());
            statement.setInt(6, car.getId());

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int newId = resultSet.getInt("id");
                car.setId(newId);
                return car ;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM car_shop.car WHERE id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, id);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Оригинальные методы для поиска автомобилей
    public List<Car> findCarsByModel(String model) {
        List<Car> cars = new ArrayList<>();
        String getByModelSQL = "SELECT * FROM car_shop.car WHERE model = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByModelSQL);){
            statement.setString(1, model);
            ResultSet resultSet = statement.executeQuery();
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> findCarsByBrand(String brand) {
        List<Car> cars = new ArrayList<>();
        String getByBrandSQL = "SELECT * FROM car_shop.car WHERE brand = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByBrandSQL);){
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> findCarsByYear(int year) {
        List<Car> cars = new ArrayList<>();
        String getByYearSQL = "SELECT * FROM car_shop.car WHERE year = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByYearSQL);){
            statement.setInt(1, year);
            ResultSet resultSet = statement.executeQuery();
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> findCarsByPrice(double minPrice, double maxPrice) {
        List<Car> cars = new ArrayList<>();
        String getByPriceSQL = "SELECT * FROM car_shop.car WHERE price BETWEEN ? AND ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByPriceSQL);){
            statement.setInt(1, (int) minPrice);
            statement.setInt(2, (int) maxPrice);
            ResultSet resultSet = statement.executeQuery();
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> findCarsByCondition(String condition) {
        List<Car> cars = new ArrayList<>();
        String getByConditionSQL = "SELECT * FROM car_shop.car WHERE condition = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByConditionSQL);){
            statement.setString(1, condition);
            ResultSet resultSet = statement.executeQuery();
            cars=mapResultSetToCar(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    private List<Car> mapResultSetToCar (ResultSet resultSet) throws SQLException {
        List<Car> cars = new ArrayList<>();
        if(resultSet.next()) {
            Car car=new Car();
            car.setId(resultSet.getInt("id"));
            car.setBrand(resultSet.getString("brand"));
            car.setYear(resultSet.getInt("year"));
            car.setPrice((int)resultSet.getDouble("price"));
            car.setModel(resultSet.getString("model"));
            car.setCondition(resultSet.getString("condition"));
            cars.add(car);
        }
        return cars;
    }
}
