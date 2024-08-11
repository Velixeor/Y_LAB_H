package org.example.service;


import org.example.entity.OrderBuy;
import org.example.repository.OrderBuyRepository;
import org.example.entity.Status;
import java.util.List;
import java.util.Optional;


public class OrderBuyService {
    private OrderBuyRepository orderBuyRepository;


    public OrderBuyService(OrderBuyRepository orderRepository) {
        this.orderBuyRepository = orderRepository;
    }

    public void addOrder(OrderBuy order) {

        orderBuyRepository.save(order);
    }

    public Optional<OrderBuy> getOrderById(int id) {
        return orderBuyRepository.findById(id);
    }

    public List<OrderBuy> getAllOrders() {
        return orderBuyRepository.findAll();
    }

    public OrderBuy updateOrder(OrderBuy updatedOrder) {
        return orderBuyRepository.update(updatedOrder);
    }

    public void deleteOrder(int id) {
         orderBuyRepository.deleteById(id);
    }

    public List<OrderBuy> searchOrdersByUserIdAndCarID(Integer idUser,Integer idCar) {
        return orderBuyRepository.searchOrdersByUserIDAndCarID(idUser,idCar);
    }

    public List<OrderBuy> searchOrdersByStatus(Status status) {
        return orderBuyRepository.searchOrdersByStatus(status);
    }
}
