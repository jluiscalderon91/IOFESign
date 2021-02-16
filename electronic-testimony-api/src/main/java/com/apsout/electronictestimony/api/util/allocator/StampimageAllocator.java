package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Contentposition;
import com.apsout.electronictestimony.api.entity.Pagestamp;
import com.apsout.electronictestimony.api.entity.Stampimage;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class StampimageAllocator {

    public static Stampimage build(Stampimage stampimage) {
        final byte[] data = FileUtil.convertImageBase64toBytes(stampimage.getBase64Data());
        stampimage.setBasename(FilenameUtils.getBaseName(stampimage.getName()));
        stampimage.setExtension(FilenameUtils.getExtension(stampimage.getName()));
        stampimage.setData(data);
        stampimage.setLength(data.length);
        ofPostMethod(stampimage);
        return stampimage;
    }

    public static void ofPostMethod(List<Stampimage> stampimages) {
        stampimages.stream().forEach(StampimageAllocator::build);
    }

    public static void forUpdate(Stampimage oldStampimage, Stampimage newStampimage) {
        newStampimage.setPositionX(oldStampimage.getPositionX());
        newStampimage.setPositionY(oldStampimage.getPositionY());
        newStampimage.setPercentSize(oldStampimage.getPercentSize());
        newStampimage.setRotation(oldStampimage.getRotation());
        newStampimage.setContentPosition(oldStampimage.getContentPosition());
        newStampimage.setPageStamp(oldStampimage.getPageStamp());
        newStampimage.setStampOn(oldStampimage.getStampOn());
    }

    public static void forDelete(Stampimage stampimage) {
        stampimage.setDeleted(States.DELETED);
    }

    public static void ofPostMethod(Stampimage stampimage) {
        stampimage.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stampimage.setActive(States.ACTIVE);
        stampimage.setDeleted(States.EXISTENT);
    }

    public static Stampimage forReturn(Stampimage stampimage, Contentposition contentposition, Pagestamp pagestamp) {
        byte[] data = stampimage.getData();
        String encodedBase64 = FileUtil.encodeBytes2Base64(data);
        Stampimage stampimage1 = new Stampimage();
        stampimage1.setPageStamp(pagestamp.getId());
        stampimage1.setStampOn(stampimage.getStampOn());
        stampimage1.setName(stampimage.getName());
        stampimage1.setPositionX(stampimage.getPositionX());
        stampimage1.setPositionY(stampimage.getPositionY());
        stampimage1.setPercentSize(stampimage.getPercentSize());
        stampimage1.setRotation(stampimage.getRotation());
        stampimage1.setContentPosition(contentposition.getId());
        stampimage1.setBase64Data(encodedBase64);
        return stampimage1;
    }
}
