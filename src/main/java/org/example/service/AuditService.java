package org.example.service;


import org.example.entity.Audit;
import org.example.repository.AuditRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class AuditService {
    AuditRepository auditRepository;
    private int id;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
        id=0;
    }

    public void addAction(String action, String username) {
        Audit auditLog = new Audit(id,action,username, LocalDateTime.now());
        id++;
        auditRepository.addAuditLog(auditLog);
    }

    public List<Audit> getAllLogs() {
        return auditRepository.getAllAudit();
    }

    public void exportAudit(String filename) {
        List<Audit> logs = getAllLogs();
        if (logs.isEmpty()) {
            System.out.println("Нет записей для экспорта.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Audit log : logs) {

                writer.write(formatAudit(log));
                writer.newLine();
            }
            System.out.println("Аудит успешно экспортирован в " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при экспорте аудита: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String formatAudit(Audit log) {
        return String.format("%s,%s,%s,%s", log.getId(), log.getUsername(), log.getAction(), log.getTimestamp());
    }
}
