package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Stampdatetime;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class StampdatetimeAllocator {

    public static Stampdatetime build(Stampdatetime stampqrcode) {
        ofPostMethod(stampqrcode);
        return stampqrcode;
    }

    public static void ofPostMethod(List<Stampdatetime> stampdatetimes) {
        stampdatetimes.stream().forEach(StampdatetimeAllocator::build);
    }

    public static void forUpdate(Stampdatetime oldStampdatetime, Stampdatetime newStampdatetime) {
        newStampdatetime.setPositionX(oldStampdatetime.getPositionX());
        newStampdatetime.setPositionY(oldStampdatetime.getPositionY());
        newStampdatetime.setWidthContainer(oldStampdatetime.getWidthContainer());
        newStampdatetime.setHeightContainer(oldStampdatetime.getHeightContainer());
        newStampdatetime.setPageStamp(oldStampdatetime.getPageStamp());
        newStampdatetime.setStampOn(oldStampdatetime.getStampOn());
    }

    public static void forDelete(Stampdatetime stampdatetime) {
        stampdatetime.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Stampdatetime stampdatetime) {
        stampdatetime.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stampdatetime.setActive(States.ACTIVE);
        stampdatetime.setDeleted(States.EXISTENT);
    }

    public static Stampdatetime forReturn(Stampdatetime stampdatetime, int stampOn) {
        Stampdatetime stampdatetime1 = new Stampdatetime();
        stampdatetime1.setDescription(stampdatetime.getDescription());
        stampdatetime1.setPositionX(stampdatetime.getPositionX());
        stampdatetime1.setPositionY(stampdatetime.getPositionY());
        stampdatetime1.setWidthContainer(stampdatetime.getWidthContainer());
        stampdatetime1.setHeightContainer(stampdatetime.getHeightContainer());
        stampdatetime1.setStampOn(stampOn);
        return stampdatetime1;
    }
}
