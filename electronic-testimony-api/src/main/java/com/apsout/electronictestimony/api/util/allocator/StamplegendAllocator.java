package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.*;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class StamplegendAllocator {

    public static Stamplegend build(Stamplegend stamplegend) {
        ofPostMethod(stamplegend);
        return stamplegend;
    }

    public static void ofPostMethod(List<Stamplegend> stamplegends) {
        stamplegends.stream().forEach(StamplegendAllocator::build);
    }

    public static void forUpdate(Stamplegend oldStamplegend, Stamplegend newStamplegend) {
        newStamplegend.setDescription(oldStamplegend.getDescription());
        newStamplegend.setPositionX(oldStamplegend.getPositionX());
        newStamplegend.setPositionY(oldStamplegend.getPositionY());
        newStamplegend.setRotation(oldStamplegend.getRotation());
        newStamplegend.setFontSize(oldStamplegend.getFontSize());
        newStamplegend.setFontType(oldStamplegend.getFontType());
        newStamplegend.setFontColor(oldStamplegend.getFontColor());
        newStamplegend.setPageStamp(oldStamplegend.getPageStamp());
        newStamplegend.setStampOn(oldStamplegend.getStampOn());
    }

    public static void forDelete(Stamplegend stamplegend) {
        stamplegend.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Stamplegend stamplegend) {
        stamplegend.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stamplegend.setActive(States.ACTIVE);
        stamplegend.setDeleted(States.EXISTENT);
    }

    public static Stamplegend forReturn(Stamplegend stamplegend, Fontsize fontsize, Fonttype fonttype, Fontcolor fontcolor, Pagestamp pagestamp) {
        Stamplegend stamplegend1 = new Stamplegend();
        stamplegend1.setDescription(stamplegend.getDescription());
        stamplegend1.setPositionX(stamplegend.getPositionX());
        stamplegend1.setPositionY(stamplegend.getPositionY());
        stamplegend1.setRotation(stamplegend.getRotation());
        stamplegend1.setFontSize(fontsize.getId());
        stamplegend1.setFontType(fonttype.getId());
        stamplegend1.setFontColor(fontcolor.getId());
        stamplegend1.setPageStamp(pagestamp.getId());
        stamplegend1.setStampOn(stamplegend.getStampOn());
        return stamplegend1;
    }

}
