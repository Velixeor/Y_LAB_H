package org.example.manager;


import org.example.entity.OrderBuy;
import org.example.service.AuditService;
import org.example.service.OrderBuyService;
import org.example.entity.Status;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class OrderBuyManager {
    private OrderBuyService orderBuyService;
    private AuditService auditService;


    public OrderBuyManager(OrderBuyService orderBuyService, AuditService auditService) {
        this.orderBuyService = orderBuyService;
        this.auditService = auditService;

    }

    public void manageOrders(Scanner scanner) {
        System.out.println("Меню заказов на покупку ");
        System.out.println("1. Создать заказ");
        System.out.println("2. Редактировать заказ");
        System.out.println("3. Удалить заказ");
        System.out.println("4. Просмотреть все заказы");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                createOrder(scanner);
                break;
            case "2":
                updateOrder(scanner);
                break;
            case "3":
                deleteOrder(scanner);
                break;
            case "4":
                viewAllOrders();
                break;
            default:
                System.out.println("Введите число из предложенных");
        }
    }

    private void createOrder(Scanner scanner) {
        System.out.print("ID автомобиля: ");
        Integer carId = Integer.valueOf(scanner.nextLine());
        System.out.print("ID клиента: ");
        Integer userID = Integer.valueOf(scanner.nextLine());
        System.out.print("Статус заказа(inProcessing,\n" +
                "    started,\n" +
                "    ready: ");
        String statusS = scanner.nextLine();
        Status status = Status.valueOf(statusS);
        OrderBuy order = new OrderBuy( carId, userID, status);
        if(orderBuyService.searchOrdersByUserIdAndCarID(userID,carId).isEmpty()) {
            orderBuyService.addOrder(order);
        }
        auditService.addAction("Создание заказа", "admin");
        System.out.println("Заказ создан.");
    }

    private void updateOrder(Scanner scanner) {
        System.out.print("ID заказа: ");
        int orderIdToUpdate = Integer.parseInt(scanner.nextLine());
        Optional<OrderBuy> orderToUpdate = orderBuyService.getOrderById(orderIdToUpdate);
        if (orderToUpdate.isPresent()) {
            OrderBuy orderBuy = orderToUpdate.get();
            System.out.print("Новый статус заказа: ");
            orderBuy.setStatus(Status.valueOf(scanner.nextLine()));
            orderBuyService.updateOrder(orderBuy);
            auditService.addAction("Редактирование заказа", "admin");
            System.out.println("Заказ обновлен.");
        } else {
            System.out.println("Заказ не найден.");
        }
    }

    private void deleteOrder(Scanner scanner) {
        System.out.print("ID заказа: ");
        int orderIdToDelete = Integer.parseInt(scanner.nextLine());
        if (orderBuyService.deleteOrder(orderIdToDelete)) {
            auditService.addAction("Удаление заказа", "admin");
            System.out.println("Заказ удален.");
        } else {
            System.out.println("Заказ не найден.");
        }
    }

    private void viewAllOrders() {
        List<OrderBuy> orders = orderBuyService.getAllOrders();
        for (OrderBuy order : orders) {
            System.out.println(order);
        }
    }
}
