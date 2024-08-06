package org.example.service;


import org.example.entity.OrderBuy;
import org.example.repository.OrderBuyRepository;
import org.example.entity.Status;
import java.util.List;
import java.util.Optional;


public class OrderBuyService {
    private OrderBuyRepository orderBuyRepository;
    private int id;

    public OrderBuyService(OrderBuyRepository orderRepository) {
        this.orderBuyRepository = orderRepository;
    }

    public void addOrder(OrderBuy order) {
        order.setId(id);
        id++;
        orderBuyRepository.addOrder(order);
    }

    public Optional<OrderBuy> getOrderById(int id) {
        return orderBuyRepository.getOrderById(id);
    }

    public List<OrderBuy> getAllOrders() {
        return orderBuyRepository.getAllOrders();
    }

    public boolean updateOrder(OrderBuy updatedOrder) {
        return orderBuyRepository.updateOrder(updatedOrder);
    }

    public boolean deleteOrder(int id) {
        return orderBuyRepository.deleteOrder(id);
    }

    public List<OrderBuy> searchOrdersByUserIdAndCarID(Integer idUser,Integer idCar) {
        return orderBuyRepository.searchOrdersByUserIDAndCarID(idUser,idCar);
    }

    public List<OrderBuy> searchOrdersByStatus(Status status) {
        return orderBuyRepository.searchOrdersByStatus(status);
    }
}
