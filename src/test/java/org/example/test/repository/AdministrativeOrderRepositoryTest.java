package org.example.test.repository;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Service;
import org.example.entity.Status;
import org.example.repository.AdministrativeOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
class AdministrativeOrderRepositoryTest {

    @Container
    public PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private AdministrativeOrderRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        DataBaseConnection.setDataSource(postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());


        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS car_shop");
            statement.execute("CREATE TABLE IF NOT EXISTS car_shop.administrative_order (" +
                    "id SERIAL PRIMARY KEY, " +
                    "car_brand VARCHAR(255), " +
                    "car_model VARCHAR(255), " +
                    "username VARCHAR(255), " +
                    "service_type VARCHAR(255), " +
                    "status VARCHAR(255), " +
                    "car_id INTEGER, " +
                    "user_id INTEGER" +
                    ")");
        }

        repository = new AdministrativeOrderRepository();
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS car_shop.administrative_order");
        }
    }

    @Test
    @DisplayName("Должен сохранить новый административный заказ и вернуть его с присвоенным ID")
    void saveAdministrativeOrder() {
        AdministrativeOrder order = new AdministrativeOrder();
        order.setCarBrand("Toyota");
        order.setCarModel("Corolla");
        order.setUsername("john_doe");
        order.setServiceType(Service.REPAIR);
        order.setStatus(Status.IN_PROCESSING);
        order.setCarID(1);
        order.setUserID(1);

        AdministrativeOrder savedOrder = repository.save(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
    }

    @Test
    @DisplayName("Должен найти административный заказ по ID")
    void findAdministrativeOrderById() {
        AdministrativeOrder order = new AdministrativeOrder();
        order.setCarBrand("Honda");
        order.setCarModel("Civic");
        order.setUsername("jane_doe");
        order.setServiceType(Service.PREVENTIVE_MAINTENANCE);
        order.setStatus(Status.READY);
        order.setCarID(2);
        order.setUserID(2);

        AdministrativeOrder savedOrder = repository.save(order);
        Optional<AdministrativeOrder> foundOrder = repository.findById(savedOrder.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getCarBrand()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("Должен обновить административный заказ и вернуть обновленный объект")
    void updateAdministrativeOrder() {
        AdministrativeOrder order = new AdministrativeOrder();
        order.setCarBrand("Ford");
        order.setCarModel("Focus");
        order.setUsername("alex_smith");
        order.setServiceType(Service.WASH);
        order.setStatus(Status.IN_PROCESSING);
        order.setCarID(3);
        order.setUserID(3);

        AdministrativeOrder savedOrder = repository.save(order);
        savedOrder.setStatus(Status.READY);

        AdministrativeOrder updatedOrder = repository.update(savedOrder);

        assertThat(updatedOrder).isNotNull();
        assertThat(updatedOrder.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    @DisplayName("Должен удалить административный заказ по ID")
    void deleteAdministrativeOrderById() {
        AdministrativeOrder order = new AdministrativeOrder();
        order.setCarBrand("Chevrolet");
        order.setCarModel("Malibu");
        order.setUsername("lisa_white");
        order.setServiceType(Service.REPAIR);
        order.setStatus(Status.IN_PROCESSING);
        order.setCarID(4);
        order.setUserID(4);

        AdministrativeOrder savedOrder = repository.save(order);
        repository.deleteById(savedOrder.getId());

        Optional<AdministrativeOrder> deletedOrder = repository.findById(savedOrder.getId());

        assertThat(deletedOrder).isEmpty();
    }

    @Test
    @DisplayName("Должен найти административные заказы по имени пользователя")
    void searchAdministrativeOrdersByCustomerUsername() {
        AdministrativeOrder order1 = new AdministrativeOrder();
        order1.setCarBrand("BMW");
        order1.setCarModel("X5");
        order1.setUsername("peter_parker");
        order1.setServiceType(Service.REPAIR);
        order1.setStatus(Status.IN_PROCESSING);
        order1.setCarID(5);
        order1.setUserID(5);
        repository.save(order1);

        AdministrativeOrder order2 = new AdministrativeOrder();
        order2.setCarBrand("Audi");
        order2.setCarModel("A4");
        order2.setUsername("peter_parker");
        order2.setServiceType(Service.PREVENTIVE_MAINTENANCE);
        order2.setStatus(Status.READY);
        order2.setCarID(6);
        order2.setUserID(6);
        repository.save(order2);

        List<AdministrativeOrder> orders = repository.searchAdministrativeOrdersByCustomerUsername("peter_parker");

        assertThat(orders).hasSize(2);
    }

    @Test
    @DisplayName("Должен найти административные заказы по статусу")
    void searchAdministrativeOrdersByStatus() {
        AdministrativeOrder order = new AdministrativeOrder();
        order.setCarBrand("Mercedes");
        order.setCarModel("C-Class");
        order.setUsername("bruce_wayne");
        order.setServiceType(Service.REPAIR);
        order.setStatus(Status.READY);
        order.setCarID(7);
        order.setUserID(7);
        repository.save(order);

        List<AdministrativeOrder> orders = repository.searchAdministrativeOrdersByStatus(Status.READY);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getUsername()).isEqualTo("bruce_wayne");
    }
}
