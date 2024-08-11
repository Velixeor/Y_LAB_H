package org.example.repository;


import org.example.entity.Car;
import org.example.entity.OrderBuy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.entity.Status;

public class OrderBuyRepository implements CRUDRepository<OrderBuy, Integer> {

    @Override
    public OrderBuy save(OrderBuy order) {
        String insertSQL = "INSERT INTO car_shop.order_buy (car_id, user_id, status) VALUES (?, ?, ?) RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setInt(1, order.getCarID());
            statement.setInt(2, order.getUserID());
            statement.setString(3, order.getStatus().toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int newId = resultSet.getInt("id");
                    order.setId(newId);
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось создать заказ");
        }
       return null;
    }

    @Override
    public List<OrderBuy> findAll() {
        List<OrderBuy> orderBuys = new ArrayList<>();
        String findAllSQL = "SELECT * FROM car_shop.order_buy";

        try(Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(findAllSQL);
            orderBuys=helper(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orderBuys;
    }

    @Override
    public Optional<OrderBuy> findById(Integer id) {
        String getByIdSQL = "SELECT * FROM car_shop.order_buy WHERE id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getByIdSQL);){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                OrderBuy orderBuy=new OrderBuy();
                orderBuy.setId(resultSet.getInt("id"));
                orderBuy.setCarID(resultSet.getInt("car_id"));
                orderBuy.setUserID(resultSet.getInt("user_id"));
                orderBuy.setStatus(Status.valueOf(resultSet.getString("status")));
                return Optional.of(orderBuy);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public OrderBuy update(OrderBuy order) {
        String updateSQL = "UPDATE car_shop.order_buy SET car_id = ?, user_id = ?, status = ? WHERE id = ? RETURNING id";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setInt(1, order.getCarID());
            statement.setInt(2, order.getUserID());
            statement.setString(3, order.getStatus().toString());
            statement.setInt(4, order.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int newId = resultSet.getInt("id");
                    order.setId(newId);
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM car_shop.order_buy WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<OrderBuy> searchOrdersByUserIDAndCarID(Integer idUser, Integer idCar) {
        List<OrderBuy> orderBuys = new ArrayList<>();
        String findByUserIDAndCarIDSQL = "SELECT * FROM car_shop.order_buy WHERE car_id=? and user_id=?";

        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByUserIDAndCarIDSQL);){
            statement.setInt(1, idCar);
            statement.setInt(2, idUser);
            ResultSet resultSet = statement.executeQuery();
            orderBuys=helper(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orderBuys;
    }

    public List<OrderBuy> searchOrdersByStatus(Status status) {
        List<OrderBuy> orderBuys = new ArrayList<>();
        String findByStatusSQL = "SELECT * FROM car_shop.order_buy WHERE status=?";

        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(findByStatusSQL);){
            statement.setString(1, status.toString());
            ResultSet resultSet = statement.executeQuery();
            orderBuys=helper(resultSet);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return orderBuys;
    }
    private List<OrderBuy> helper (ResultSet resultSet) throws SQLException {
        List<OrderBuy> orderBuys = new ArrayList<>();
        if(resultSet.next()) {
            OrderBuy orderBuy=new OrderBuy();
            orderBuy.setId(resultSet.getInt("id"));
            orderBuy.setCarID(resultSet.getInt("car_id"));
            orderBuy.setUserID(resultSet.getInt("user_id"));
            orderBuy.setStatus(Status.valueOf(resultSet.getString("status")));
            orderBuys.add(orderBuy);
        }
        return orderBuys;
    }
}
