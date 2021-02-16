package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Contentposition;
import com.apsout.electronictestimony.api.entity.Pagestamp;
import com.apsout.electronictestimony.api.entity.Stamprubric;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class StamprubricAllocator {

    public static Stamprubric build(Stamprubric stamprubric) {
        Stamprubric stamprubric1 = new Stamprubric();
        stamprubric1.setParticipantId(stamprubric.getParticipantId());
        stamprubric1.setPositionX(stamprubric.getPositionX());
        stamprubric1.setPositionY(stamprubric.getPositionY());
        stamprubric1.setPercentSize(stamprubric.getPercentSize());
        stamprubric1.setRotation(stamprubric.getRotation());
        stamprubric1.setContentPosition(stamprubric.getContentPosition());
        stamprubric1.setPageStamp(stamprubric.getPageStamp());
        stamprubric1.setStampOn(stamprubric.getStampOn());
        ofPostMethod(stamprubric);
        return stamprubric;
    }

    public static void ofPostMethod(List<Stamprubric> stamprubrics) {
        stamprubrics.stream().forEach(StamprubricAllocator::build);
    }

    public static void forUpdate(Stamprubric oldStamprubric, Stamprubric newStamprubric) {
        newStamprubric.setParticipantId(oldStamprubric.getParticipantId());
        newStamprubric.setPositionX(oldStamprubric.getPositionX());
        newStamprubric.setPositionY(oldStamprubric.getPositionY());
        newStamprubric.setPositionXf(oldStamprubric.getPositionXf());
        newStamprubric.setPositionYf(oldStamprubric.getPositionYf());
        newStamprubric.setPercentSize(oldStamprubric.getPercentSize());
        newStamprubric.setRotation(oldStamprubric.getRotation());
        newStamprubric.setContentPosition(oldStamprubric.getContentPosition());
        newStamprubric.setPageStamp(oldStamprubric.getPageStamp());
        newStamprubric.setStampOn(oldStamprubric.getStampOn());
    }

    public static void forDelete(Stamprubric stamprubric) {
        stamprubric.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Stamprubric stamprubric) {
        stamprubric.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stamprubric.setActive(States.ACTIVE);
        stamprubric.setDeleted(States.EXISTENT);
    }

    public static Stamprubric forReturn(Stamprubric stamprubric, Contentposition contentposition, Pagestamp pagestamp) {
        Stamprubric stampimage1 = new Stamprubric();
        stampimage1.setPageStamp(pagestamp.getId());
        stampimage1.setStampOn(stamprubric.getStampOn());
        stampimage1.setPositionX(stamprubric.getPositionX());
        stampimage1.setPositionY(stamprubric.getPositionY());
        stampimage1.setPercentSize(stamprubric.getPercentSize());
        stampimage1.setRotation(stamprubric.getRotation());
        stampimage1.setContentPosition(contentposition.getId());
        return stampimage1;
    }
}
