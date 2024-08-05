package org.example.repository;


import org.example.entity.Audit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AuditRepository {
    private List<Audit> audits;

    public AuditRepository() {
        this.audits = new ArrayList<>();
    }

    public void addAuditLog(Audit audit) {
        audits.add(audit);
    }

    public List<Audit> getAllAudit() {
        return new ArrayList<>(audits);
    }

    public boolean deleteAudit(Integer id) {
        for (Audit log : audits) {
            if (log.getId().equals(id)) {
                audits.remove(log);
                return true;
            }
        }
        return false;
    }

    public Optional<Audit> getAuditById(Integer id) {
        for (Audit log : audits) {
            if (log.getId().equals(id)) {
                return Optional.of(log);
            }
        }
        return Optional.empty();
    }

    public List<Audit> getAuditByUsername(String username) {
        List<Audit> result = new ArrayList<>();
        for (Audit log : audits) {
            if (log.getUsername().equals(username)) {
                result.add(log);
            }
        }
        return result;
    }

    public List<Audit> getAuditByAction(String action) {
        List<Audit> result = new ArrayList<>();
        for (Audit log : audits) {
            if (log.getAction().equals(action)) {
                result.add(log);
            }
        }
        return result;
    }

    public List<Audit> getAuditByDate(LocalDateTime date) {
        List<Audit> result = new ArrayList<>();
        for (Audit log : audits) {
            if (log.getTimestamp().toLocalDate().equals(date.toLocalDate())) {
                result.add(log);
            }
        }
        return result;
    }
}
