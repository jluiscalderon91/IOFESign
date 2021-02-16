package com.apsout.electronictestimony.api.repository;

import com.apsout.electronictestimony.api.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    Optional<Notification> findFirstByEnabledAndSentAndActiveAndDeletedOrderByPriorityAsc(byte enabled, byte sent, byte active, byte deleted);

    Optional<Notification> findByOperatorIdAndActiveAndDeleted(int operatorId, byte active, byte deleted);

    Optional<Notification> findByOperatorIdAndDeleted(int operatorId, byte deleted);
}
