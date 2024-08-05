package org.example.test.service;


import org.example.entity.Audit;
import org.example.repository.AuditRepository;
import org.example.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;


public class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditService auditService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditService = new AuditService(auditRepository);
    }

    @Test
    public void testAddAction() {
        String action = "User login";
        String username = "john_doe";

        auditService.addAction(action, username);

        verify(auditRepository, times(1)).addAuditLog(any(Audit.class));
    }

    @Test
    public void testGetAllLogs() {
        Audit audit1 = new Audit(1, "Action1", "user1", LocalDateTime.now());
        Audit audit2 = new Audit(2, "Action2", "user2", LocalDateTime.now());

        when(auditRepository.getAllAudit()).thenReturn(Arrays.asList(audit1, audit2));

        List<Audit> logs = auditService.getAllLogs();

        assertThat(logs).containsExactlyInAnyOrder(audit1, audit2);
    }

    @Test
    public void testExportAuditWithLogs() throws IOException {
        Audit audit1 = new Audit(1, "Action1", "user1", LocalDateTime.now());
        Audit audit2 = new Audit(2, "Action2", "user2", LocalDateTime.now());

        when(auditRepository.getAllAudit()).thenReturn(Arrays.asList(audit1, audit2));

        String filename = "audit_logs.txt";

        auditService.exportAudit(filename);

        verify(auditRepository, times(1)).getAllAudit();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            assertThat(reader.readLine()).isEqualTo(String.format("%d,%s,%s,%s", audit1.getId(), audit1.getUsername(), audit1.getAction(), audit1.getTimestamp()));
            assertThat(reader.readLine()).isEqualTo(String.format("%d,%s,%s,%s", audit2.getId(), audit2.getUsername(), audit2.getAction(), audit2.getTimestamp()));
        }
    }

    @Test
    public void testExportAuditWithoutLogs() {
        when(auditRepository.getAllAudit()).thenReturn(Arrays.asList());

        String filename = "audit_logs.txt";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        auditService.exportAudit(filename);

        verify(auditRepository, times(1)).getAllAudit();

        assertThat(outContent.toString().trim()).isEqualTo("Нет записей для экспорта.");

        System.setOut(originalOut);
    }
}
