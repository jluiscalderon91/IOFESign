package com.apsout.electronictestimony.api.scheduler;

import com.apsout.electronictestimony.api.service.PasswordretrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PasswordRetrieverScheduler {

    @Autowired
    private PasswordretrieverService passwordretrieverService;

    @Scheduled(initialDelay = 2500, fixedRate = 6000)
    public void run() {
        passwordretrieverService.notificate();
    }
}
