package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    private Integer id;
    private String action;
    private LocalDateTime timestamp;
    private Integer userID;
}
