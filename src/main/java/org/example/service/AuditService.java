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
    private Integer authid;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
        authid=-1;
    }
    public void SetAuthId(Integer id){
        authid=id;
    }
    public Integer GetAuthId(){
        return authid;
    }

    public void addAction(String action) {
        Audit auditLog = new Audit(1,action,LocalDateTime.now(),authid);

        auditRepository.save(auditLog);
    }

    public List<Audit> getAllLogs() {
        return auditRepository.findAll();
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
        return String.format("%s,%s,%s,%s", log.getId(), log.getAction(), log.getTimestamp(),log.getUserID());
    }
}
