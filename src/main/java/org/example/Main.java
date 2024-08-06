package org.example;


import org.example.manager.*;
import org.example.repository.*;
import org.example.service.*;

import java.util.Scanner;


public class Main {
    private static UserService userService;
    private static CarService carService;
    private static OrderBuyService orderBuyService;
    private static AdministrativeOrderService administrativeOrderService;
    private static AuditService auditService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        userService = new UserService(new UserRepository());
        carService = new CarService(new CarRepository());
        orderBuyService = new OrderBuyService(new OrderBuyRepository());
        administrativeOrderService = new AdministrativeOrderService(new AdministrativeOrderRepository());
        auditService = new AuditService(new AuditRepository());

        UserManager userManager = new UserManager(userService);
        CarManager carManager = new CarManager(carService, auditService);
        OrderBuyManager orderManager = new OrderBuyManager(orderBuyService, auditService);
        AdministrativeOrderManager administrativeOrderManager = new AdministrativeOrderManager(administrativeOrderService, auditService);
        AuditManager auditManager = new AuditManager(auditService);


        while (true) {
            System.out.println("Главное меню ");
            System.out.println("1. Меню пользователей");
            System.out.println("2. Меню автомобилей");
            System.out.println("3. Меню заказов на покупку");
            System.out.println("4. Меню заказов на обслуживание");
            System.out.println("5. Меню аудита");
            System.out.println("0. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userManager.manageUser(scanner);
                    break;
                case "2":
                    carManager.manageCars(scanner);
                    break;
                case "3":
                    orderManager.manageOrders(scanner);
                    break;
                case "4":
                    administrativeOrderManager.manageServiceRequests(scanner);
                    break;
                case "5":
                    auditManager.manageAudit(scanner);
                    break;
                case "0":
                    System.out.println("Выход");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }


}
