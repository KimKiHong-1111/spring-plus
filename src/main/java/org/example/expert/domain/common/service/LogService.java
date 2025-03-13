package org.example.expert.domain.common.service;


import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.entity.Log;
import org.example.expert.domain.common.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(String logMessage, LocalDateTime logAt) {
        Log log = new Log(logMessage, logAt);
        logRepository.save(log);
    }
}
