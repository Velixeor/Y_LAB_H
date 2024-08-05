package org.example.manager;


import org.example.entity.Audit;
import org.example.service.AuditService;

import java.util.List;
import java.util.Scanner;


public class AuditManager {
    private AuditService auditService;

    public AuditManager(AuditService auditService) {
        this.auditService = auditService;
    }
    public void manageAudit(Scanner scanner){
        System.out.println("Меню пользователей");
        System.out.println("1. Посмотреть аудит");
        System.out.println("2. Вывести в файл");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                viewAuditLogs();
                break;
            case "2":
                writeToFile(scanner);
                break;
            default:
                System.out.println("Введите число из предложенных");
        }}

    public void viewAuditLogs() {
        List<Audit> logs = auditService.getAllLogs();
        for (Audit log : logs) {
            System.out.println(log);
        }
    }
    public void writeToFile(Scanner scanner) {
        System.out.println("Введите имя файла");
        String nameFile = scanner.nextLine();
        auditService.exportAudit(nameFile);
    }
}
