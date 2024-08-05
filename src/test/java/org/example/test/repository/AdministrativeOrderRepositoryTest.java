package org.example.test.repository;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Service;
import org.example.entity.Status;
import org.example.repository.AdministrativeOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class AdministrativeOrderRepositoryTest {
    @InjectMocks
    private AdministrativeOrderRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new AdministrativeOrderRepository();
    }

    @Test
    public void testAddAdministrativeOrder() {
        AdministrativeOrder order = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order);

        List<AdministrativeOrder> orders = repository.getAllAdministrativeOrder();
        assertThat(orders).contains(order);
    }

    @Test
    public void testGetAllAdministrativeOrder() {
        AdministrativeOrder order1 = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        AdministrativeOrder order2 = new AdministrativeOrder(2, "Honda", "Accord", "user2", Service.preventiveMaintenance, Status.ready);
        repository.addAdministrativeOrder(order1);
        repository.addAdministrativeOrder(order2);

        List<AdministrativeOrder> orders = repository.getAllAdministrativeOrder();
        assertThat(orders).containsExactlyInAnyOrder(order1, order2);
    }

    @Test
    public void testDeleteOrderService() {
        AdministrativeOrder order = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order);

        boolean deleted = repository.deleteOrderService(1);
        List<AdministrativeOrder> orders = repository.getAllAdministrativeOrder();

        assertThat(deleted).isTrue();
        assertThat(orders).doesNotContain(order);
    }

    @Test
    public void testDeleteOrderServiceNotFound() {
        boolean deleted = repository.deleteOrderService(1);
        assertThat(deleted).isFalse();
    }

    @Test
    public void testGetAdministrativeOrderById() {
        AdministrativeOrder order = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order);

        Optional<AdministrativeOrder> foundOrder = repository.getAdministrativeOrderById(1);
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get()).isEqualTo(order);
    }

    @Test
    public void testGetAdministrativeOrderByIdNotFound() {
        Optional<AdministrativeOrder> foundOrder = repository.getAdministrativeOrderById(1);
        assertThat(foundOrder).isNotPresent();
    }

    @Test
    public void testUpdateAdministrativeOrder() {
        AdministrativeOrder order = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order);

        AdministrativeOrder updatedOrder = new AdministrativeOrder(1, "Toyota", "Corolla", "user1", Service.repair, Status.ready);
        boolean updated = repository.updateAdministrativeOrder(updatedOrder);

        assertThat(updated).isTrue();
        assertThat(repository.getAdministrativeOrderById(1)).isPresent()
                .hasValueSatisfying(o -> {
                    assertThat(o.getCarModel()).isEqualTo("Corolla");
                    assertThat(o.getStatus()).isEqualTo(Status.ready);
                });
    }

    @Test
    public void testUpdateAdministrativeOrderNotFound() {
        AdministrativeOrder updatedOrder = new AdministrativeOrder(1, "Toyota", "Corolla", "user1", Service.repair, Status.ready);
        boolean updated = repository.updateAdministrativeOrder(updatedOrder);

        assertThat(updated).isFalse();
    }

    @Test
    public void testSearchAdministrativeOrdersByCustomerUsername() {
        AdministrativeOrder order1 = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        AdministrativeOrder order2 = new AdministrativeOrder(2, "Honda", "Accord", "user2", Service.preventiveMaintenance, Status.ready);
        AdministrativeOrder order3 = new AdministrativeOrder(3, "Ford", "Fusion", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order1);
        repository.addAdministrativeOrder(order2);
        repository.addAdministrativeOrder(order3);

        List<AdministrativeOrder> orders = repository.searchAdministrativeOrdersByCustomerUsername("user1");
        assertThat(orders).containsExactlyInAnyOrder(order1, order3);
    }

    @Test
    public void testSearchAdministrativeOrdersByStatus() {
        AdministrativeOrder order1 = new AdministrativeOrder(1, "Toyota", "Camry", "user1", Service.repair, Status.inProcessing);
        AdministrativeOrder order2 = new AdministrativeOrder(2, "Honda", "Accord", "user2", Service.preventiveMaintenance, Status.ready);
        AdministrativeOrder order3 = new AdministrativeOrder(3, "Ford", "Fusion", "user1", Service.repair, Status.inProcessing);
        repository.addAdministrativeOrder(order1);
        repository.addAdministrativeOrder(order2);
        repository.addAdministrativeOrder(order3);

        List<AdministrativeOrder> orders = repository.searchAdministrativeOrdersByStatus(Status.inProcessing);
        assertThat(orders).containsExactlyInAnyOrder(order1, order3);
    }
}
