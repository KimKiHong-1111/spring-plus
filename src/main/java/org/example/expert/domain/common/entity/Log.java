package org.example.expert.domain.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    private String logMessage;
    private LocalDateTime logDate;

    public Log() {
    }

    public Log( String logMessage, LocalDateTime logDate) {
        this.logMessage = logMessage;
        this.logDate = logDate;
    }
}
