package org.example.test.repository;


import org.example.entity.OrderBuy;
import org.example.entity.Status;
import org.example.repository.OrderBuyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderBuyRepositoryTest {

    private OrderBuyRepository orderBuyRepository;

    @BeforeEach
    public void setUp() {
        orderBuyRepository = new OrderBuyRepository();
    }

    @Test
    public void testAddOrder() {
        OrderBuy order = new OrderBuy(1, 100, 200, Status.inProcessing);
        orderBuyRepository.addOrder(order);

        List<OrderBuy> orders = orderBuyRepository.getAllOrders();
        assertThat(orders).contains(order);
    }

    @Test
    public void testGetAllOrders() {
        OrderBuy order1 = new OrderBuy(1, 100, 200, Status.inProcessing);
        OrderBuy order2 = new OrderBuy(2, 101, 201, Status.ready);
        orderBuyRepository.addOrder(order1);
        orderBuyRepository.addOrder(order2);

        List<OrderBuy> orders = orderBuyRepository.getAllOrders();
        assertThat(orders).containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    public void testDeleteOrder() {
        OrderBuy order = new OrderBuy(1, 100, 200, Status.inProcessing);
        orderBuyRepository.addOrder(order);

        boolean deleted = orderBuyRepository.deleteOrder(1);
        List<OrderBuy> orders = orderBuyRepository.getAllOrders();

        assertThat(deleted).isTrue();
        assertThat(orders).doesNotContain(order);
    }

    @Test
    public void testDeleteOrderNotFound() {
        boolean deleted = orderBuyRepository.deleteOrder(1);
        assertThat(deleted).isFalse();
    }

    @Test
    public void testGetOrderById() {
        OrderBuy order = new OrderBuy(1, 100, 200, Status.inProcessing);
        orderBuyRepository.addOrder(order);

        Optional<OrderBuy> foundOrder = orderBuyRepository.getOrderById(1);
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get()).isEqualTo(order);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        Optional<OrderBuy> foundOrder = orderBuyRepository.getOrderById(1);
        assertThat(foundOrder).isNotPresent();
    }

    @Test
    public void testUpdateOrder() {
        OrderBuy order = new OrderBuy(1, 100, 200, Status.inProcessing);
        orderBuyRepository.addOrder(order);

        OrderBuy updatedOrder = new OrderBuy(1, 101, 201, Status.ready);
        boolean updated = orderBuyRepository.updateOrder(updatedOrder);

        assertThat(updated).isTrue();
        assertThat(orderBuyRepository.getOrderById(1)).isPresent()
                .hasValueSatisfying(o -> {
                    assertThat(o.getCarID()).isEqualTo(101);
                    assertThat(o.getUserID()).isEqualTo(201);
                    assertThat(o.getStatus()).isEqualTo(Status.ready);
                });
    }

    @Test
    public void testUpdateOrderNotFound() {
        OrderBuy updatedOrder = new OrderBuy(1, 101, 201, Status.ready);
        boolean updated = orderBuyRepository.updateOrder(updatedOrder);

        assertThat(updated).isFalse();
    }

    @Test
    public void testSearchOrdersByUserIDAndCarID() {
        OrderBuy order1 = new OrderBuy(1, 100, 200, Status.inProcessing);
        OrderBuy order2 = new OrderBuy(2, 100, 200, Status.ready);
        OrderBuy order3 = new OrderBuy(3, 101, 201, Status.started);
        orderBuyRepository.addOrder(order1);
        orderBuyRepository.addOrder(order2);
        orderBuyRepository.addOrder(order3);

        List<OrderBuy> orders = orderBuyRepository.searchOrdersByUserIDAndCarID(200, 100);
        assertThat(orders).containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    public void testSearchOrdersByStatus() {
        OrderBuy order1 = new OrderBuy(1, 100, 200, Status.inProcessing);
        OrderBuy order2 = new OrderBuy(2, 101, 201, Status.inProcessing);
        OrderBuy order3 = new OrderBuy(3, 102, 202, Status.ready);
        orderBuyRepository.addOrder(order1);
        orderBuyRepository.addOrder(order2);
        orderBuyRepository.addOrder(order3);

        List<OrderBuy> orders = orderBuyRepository.searchOrdersByStatus(Status.inProcessing);
        assertThat(orders).containsExactlyInAnyOrder(order1, order2);
    }
}
