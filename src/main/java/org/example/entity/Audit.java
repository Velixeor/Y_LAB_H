package org.example.entity;


import java.time.LocalDateTime;


public class Audit {
    private Integer id;
    private String username;
    private String action;
    private LocalDateTime timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Audit(Integer id, String username, String action, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }
}
