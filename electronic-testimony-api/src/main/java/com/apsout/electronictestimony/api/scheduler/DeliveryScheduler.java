package com.apsout.electronictestimony.api.scheduler;

import com.apsout.electronictestimony.api.service.DeliveryService;
import com.apsout.electronictestimony.api.service.DeliverymailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeliveryScheduler {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliverymailService deliverymailService;

    /**
     * Envío de documentos la notaría Paino
     */
    @Scheduled(initialDelay = 2500, fixedRate = 8000)
    public void run() {
        deliveryService.deliver();
    }

    /**
     * Envío de los adjuntos del documento hacia una dirección electrónica
     */
    @Scheduled(initialDelay = 2500, fixedRate = 8000)
    public void delivery2() {
        deliverymailService.deliver();
    }
}
