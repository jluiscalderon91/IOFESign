package com.apsout.electronictestimony.api.scheduler;

import com.apsout.electronictestimony.api.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 5000)
    public void run() {
        userService.notificate();
    }
}
