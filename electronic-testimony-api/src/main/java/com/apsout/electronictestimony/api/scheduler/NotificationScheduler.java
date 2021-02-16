package com.apsout.electronictestimony.api.scheduler;

import com.apsout.electronictestimony.api.service.CancelnotificationService;
import com.apsout.electronictestimony.api.service.NotificationService;
import com.apsout.electronictestimony.api.service.SienotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SienotificationService sienotificationService;
    @Autowired
    private CancelnotificationService cancelnotificationService;

    /**
     * Notificación realizada al firmante el documendo, informando que tiene un documento pendiente de su firma
     */
    @Scheduled(fixedRate = 5000)
    public void run() {
        notificationService.notificate();
    }

    /**
     * Notificación realizada a destinatario definido en el flujo de trabajo, enviando los archivos firmados asociadas
     * al documento
     */
    @Scheduled(initialDelay = 2500, fixedRate = 8000)
    public void run2() {
        sienotificationService.notificate();
    }

    /**
     * Notificación realizada a quien cargó el documendo, informando que el documento fue anulado
     */
    @Scheduled(initialDelay = 2600, fixedRate = 20000)
    public void run3() {
        cancelnotificationService.notificate();
    }
}
