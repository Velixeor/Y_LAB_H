package org.example.manager;


import org.example.entity.AdministrativeOrder;
import org.example.entity.Service;
import org.example.service.AdministrativeOrderService;
import org.example.service.AuditService;
import org.example.entity.Status;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class AdministrativeOrderManager {
    private AdministrativeOrderService administrativeOrderService;
    private AuditService auditService;


    public AdministrativeOrderManager(AdministrativeOrderService orderService, AuditService auditService) {
        this.administrativeOrderService = orderService;
        this.auditService = auditService;
    }

    public void manageServiceRequests(Scanner scanner) {
        System.out.println("Меню заказами на обслуживание");
        System.out.println("1. Создать заказ");
        System.out.println("2. Редактировать заказ");
        System.out.println("3. Удалить заказ");
        System.out.println("4. Просмотреть все заказы");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                createServiceRequest(scanner);
                break;
            case "2":
                updateServiceRequest(scanner);
                break;
            case "3":
                deleteServiceRequest(scanner);
                break;
            case "4":
                viewAllServiceRequests();
                break;
            default:
                System.out.println("Введите число из предложенных");
        }
    }

    private void createServiceRequest(Scanner scanner) {
        System.out.print("Марка автомобиля: ");
        String carBrand = scanner.nextLine();
        System.out.print("Модель автомобиля: ");
        String carModel = scanner.nextLine();
        System.out.print("Имя клиента: ");
        String username = scanner.nextLine();
        System.out.print("Тип обслуживания: ");
        String serviceType = scanner.nextLine();
        System.out.print("Статус заказа: ");
        String status = scanner.nextLine();
        AdministrativeOrder administrativeOrder =new AdministrativeOrder(carBrand,carModel,username, Service.valueOf(serviceType),Status.valueOf(status));
        administrativeOrderService.addAdministrativeOrder(administrativeOrder);
        auditService.addAction("Создание заказа на обслуживание", "admin");
        System.out.println("Заказ на обслуживание создан.");
    }

    private void updateServiceRequest(Scanner scanner) {
        System.out.print("ID заказа: ");
        int administrativeOrderId = Integer.parseInt(scanner.nextLine());
        Optional<AdministrativeOrder> administrativeOrderUpdate = administrativeOrderService.getAdministrativeOrderById(administrativeOrderId);

        if (administrativeOrderUpdate.isPresent()) {
            AdministrativeOrder administrativeOrder = administrativeOrderUpdate.get();
            System.out.print("Новая марка автомобиля: ");
            administrativeOrder.setCarBrand(scanner.nextLine());
            System.out.print("Новая модель автомобиля: ");
            administrativeOrder.setCarModel(scanner.nextLine());
            System.out.print("Новое имя клиента: ");
            administrativeOrder.setUsername(scanner.nextLine());
            System.out.print("Новый тип обслуживания: ");
            administrativeOrder.setServiceType(Service.valueOf(scanner.nextLine()));
            System.out.print("Новый статус заказа: ");
            administrativeOrder.setStatus(Status.valueOf(scanner.nextLine()));

            administrativeOrderService.updateAdministrativeOrder(administrativeOrder);
            auditService.addAction("Редактирование заказа на обслуживание", "admin");
            System.out.println("Заказ на обслуживание обновлен.");
        } else {
            System.out.println("Заказ на обслуживание не найден.");
        }
    }

    private void deleteServiceRequest(Scanner scanner) {
        System.out.print("ID заказ: ");
        int administrativeOrderDelete = Integer.parseInt(scanner.nextLine());
        if (administrativeOrderService.deleteAdministrativeOrder(administrativeOrderDelete)) {
            auditService.addAction("Удаление заказа на обслуживание", "admin");
            System.out.println("Заказ на обслуживание удален.");
        } else {
            System.out.println("Заказ на обслуживание не найден.");
        }
    }

    private void viewAllServiceRequests() {
        List<AdministrativeOrder> administrativeOrders = administrativeOrderService.getAllAdministrativeOrder();
        for (AdministrativeOrder administrativeOrder : administrativeOrders) {
            System.out.println(administrativeOrder);
        }
    }
}
