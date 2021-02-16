package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Pagestamp;
import com.apsout.electronictestimony.api.entity.Stampqrcode;
import com.apsout.electronictestimony.api.util.statics.States;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class StampqrcodeAllocator {

    public static Stampqrcode build(Stampqrcode stampqrcode) {
        ofPostMethod(stampqrcode);
        return stampqrcode;
    }

    public static void ofPostMethod(List<Stampqrcode> stampqrcodes) {
        stampqrcodes.stream().forEach(StampqrcodeAllocator::build);
    }

    public static void forUpdate(Stampqrcode oldStamplegend, Stampqrcode newStampqrcode) {
        newStampqrcode.setPositionX(oldStamplegend.getPositionX());
        newStampqrcode.setPositionY(oldStamplegend.getPositionY());
        newStampqrcode.setSideSize(oldStamplegend.getSideSize());
        newStampqrcode.setPageStamp(oldStamplegend.getPageStamp());
        newStampqrcode.setStampOn(oldStamplegend.getStampOn());
    }

    public static void forDelete(Stampqrcode stampqrcode) {
        stampqrcode.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Stampqrcode stampqrcode) {
        stampqrcode.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stampqrcode.setActive(States.ACTIVE);
        stampqrcode.setDeleted(States.EXISTENT);
    }

    public static Stampqrcode forReturn(Stampqrcode stampqrcode, Pagestamp pagestamp){
        Stampqrcode stampqrcode1 = new Stampqrcode();
        stampqrcode1.setPositionX(stampqrcode.getPositionX());
        stampqrcode1.setPositionY(stampqrcode.getPositionY());
        stampqrcode1.setSideSize(stampqrcode.getSideSize());
        stampqrcode1.setPageStamp(pagestamp.getId());
        stampqrcode1.setStampOn(stampqrcode.getStampOn());
        return stampqrcode1;
    }
}
