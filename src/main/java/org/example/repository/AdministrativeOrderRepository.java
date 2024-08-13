package org.example.repository;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Service;
import org.example.entity.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AdministrativeOrderRepository implements CRUDRepository<AdministrativeOrder, Integer> {

    @Override
    public AdministrativeOrder save(AdministrativeOrder administrativeOrder) {
        String insertSQL = "INSERT INTO car_shop.administrative_order (car_brand, car_model, username, service_type, status, car_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            statement.setString(1, administrativeOrder.getCarBrand());
            statement.setString(2, administrativeOrder.getCarModel());
            statement.setString(3, administrativeOrder.getUsername());
            statement.setString(4, administrativeOrder.getServiceType().toString());
            statement.setString(5, administrativeOrder.getStatus().toString());
            statement.setInt(6, administrativeOrder.getCarID());
            statement.setInt(7, administrativeOrder.getUserID());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int newId = resultSet.getInt("id");
                    administrativeOrder.setId(newId);
                    return administrativeOrder;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка создания");
        }
        return null;
    }

    @Override
    public List<AdministrativeOrder> findAll() {
        List<AdministrativeOrder> administrativeOrders = new ArrayList<>();
        String findAllSQL = "SELECT * FROM car_shop.administrative_order";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            administrativeOrders = mapResultSetToAdministrativeOrders(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrativeOrders;
    }

    @Override
    public Optional<AdministrativeOrder> findById(Integer id) {
        String getByIdSQL = "SELECT * FROM car_shop.administrative_order WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToAdministrativeOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public AdministrativeOrder update(AdministrativeOrder administrativeOrder) {
        String updateSQL = "UPDATE car_shop.administrative_order SET car_brand = ?, car_model = ?, username = ?, " +
                "service_type = ?, status = ?, car_id = ?, user_id = ? WHERE id = ? RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {

            statement.setString(1, administrativeOrder.getCarBrand());
            statement.setString(2, administrativeOrder.getCarModel());
            statement.setString(3, administrativeOrder.getUsername());
            statement.setString(4, administrativeOrder.getServiceType().toString());
            statement.setString(5, administrativeOrder.getStatus().toString());
            statement.setInt(6, administrativeOrder.getCarID());
            statement.setInt(7, administrativeOrder.getUserID());
            statement.setInt(8, administrativeOrder.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return administrativeOrder;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM car_shop.administrative_order WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByCustomerUsername(String username) {
        List<AdministrativeOrder> administrativeOrders = new ArrayList<>();
        String findByUsernameSQL = "SELECT * FROM car_shop.administrative_order WHERE LOWER(username) = LOWER(?)";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByUsernameSQL)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            administrativeOrders = mapResultSetToAdministrativeOrders(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrativeOrders;
    }

    public List<AdministrativeOrder> searchAdministrativeOrdersByStatus(Status status) {
        List<AdministrativeOrder> administrativeOrders = new ArrayList<>();
        String findByStatusSQL = "SELECT * FROM car_shop.administrative_order WHERE status = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByStatusSQL)) {

            statement.setString(1, status.toString());
            ResultSet resultSet = statement.executeQuery();
            administrativeOrders = mapResultSetToAdministrativeOrders(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrativeOrders;
    }

    private List<AdministrativeOrder> mapResultSetToAdministrativeOrders(ResultSet resultSet) throws SQLException {
        List<AdministrativeOrder> administrativeOrders = new ArrayList<>();
        while (resultSet.next()) {
            administrativeOrders.add(mapResultSetToAdministrativeOrder(resultSet));
        }
        return administrativeOrders;
    }

    private AdministrativeOrder mapResultSetToAdministrativeOrder(ResultSet resultSet) throws SQLException {
        AdministrativeOrder administrativeOrder = new AdministrativeOrder();
        administrativeOrder.setId(resultSet.getInt("id"));
        administrativeOrder.setCarBrand(resultSet.getString("car_brand"));
        administrativeOrder.setCarModel(resultSet.getString("car_model"));
        administrativeOrder.setUsername(resultSet.getString("username"));
        administrativeOrder.setServiceType(Service.valueOf(resultSet.getString("service_type")));
        administrativeOrder.setStatus(Status.valueOf(resultSet.getString("status")));
        administrativeOrder.setCarID(resultSet.getInt("car_id"));
        administrativeOrder.setUserID(resultSet.getInt("user_id"));
        return administrativeOrder;
    }
}
