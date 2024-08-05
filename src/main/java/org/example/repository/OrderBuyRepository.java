package org.example.repository;


import org.example.entity.OrderBuy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.entity.Status;

public class OrderBuyRepository {

    private List<OrderBuy> orders;

    public OrderBuyRepository() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(OrderBuy order) {
        orders.add(order);
    }

    public List<OrderBuy> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public boolean deleteOrder(Integer id) {
        for (OrderBuy order : orders) {
            if (order.getId().equals(id)) {
                orders.remove(order);
                return true;
            }
        }
        return false;
    }

    public Optional<OrderBuy> getOrderById(Integer id) {
        for (OrderBuy order : orders) {
            if (order.getId().equals(id)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    public boolean updateOrder(OrderBuy updatedOrder) {
        for (OrderBuy order : orders) {
            if (order.getId().equals(updatedOrder.getId())) {
                order.setStatus(updatedOrder.getStatus());
                order.setCarID(updatedOrder.getCarID());
                order.setUserID(updatedOrder.getUserID());
                return true;
            }
        }
        return false;
    }
    public List<OrderBuy> searchOrdersByUserIDAndCarID(Integer idUser,Integer idCar) {
        List<OrderBuy> result = new ArrayList<>();
        for (OrderBuy order : orders) {
            if (order.getUserID().equals(idUser)&& order.getCarID().equals(idCar)) {
                result.add(order);
            }
        }
        return result;
    }


    public List<OrderBuy> searchOrdersByStatus(Status status) {
        List<OrderBuy> result = new ArrayList<>();
        for (OrderBuy order : orders) {
            if (order.getStatus().equals(status)) {
                result.add(order);
            }
        }
        return result;
    }
}
