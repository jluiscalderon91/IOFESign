package com.apsout.electronictestimony.api.service;

import com.apsout.electronictestimony.api.entity.Document;
import com.apsout.electronictestimony.api.entity.Notification;
import com.apsout.electronictestimony.api.entity.Operator;

import java.util.Optional;

public interface NotificationService {

    Notification save(Notification notification);

    Optional<Notification> findBy(byte enabled, byte sent);

    Optional<Notification> find4Send();

    void notificate();

    Optional<Notification> find4InvalidateBy(int operatorId);

    void invalidateBy(Operator operator);

    Optional<Notification> findBy(int personId, Document document);

    void verifyBy(int personId, String hashIdentifier);

    void resend(int personId, String hashIndentifier);
}
