package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Stamptestfile;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StamptestfileAllocator {

    public static Stamptestfile build(Workflow workflow, Stamptestfile stamptestfile) {
        final byte[] data = FileUtil.convertImageBase64toBytes(stamptestfile.getBase64Data());
        stamptestfile.setWorkflowByWorkflowId(workflow);
        stamptestfile.setWorkflowId(workflow.getId());
        stamptestfile.setBasename(FilenameUtils.getBaseName(stamptestfile.getName()));
        stamptestfile.setExtension(FilenameUtils.getExtension(stamptestfile.getName()));
        stamptestfile.setData(data);
        stamptestfile.setLength(data.length);
        ofPostMethod(stamptestfile);
        return stamptestfile;
    }

    public static Stamptestfile forceBuild(Workflow workflow, Stamptestfile oldStamptestfile) {
        Stamptestfile stamptestfile = new Stamptestfile();
        stamptestfile.setName(oldStamptestfile.getName());
        stamptestfile.setBase64Data(oldStamptestfile.getBase64Data());
        return build(workflow, stamptestfile);
    }

    public static void forUpdate(Stamptestfile oldStamptestfile, Stamptestfile newStamptestfile) {
        final byte[] data = FileUtil.convertImageBase64toBytes(oldStamptestfile.getBase64Data());
        newStamptestfile.setBasename(FilenameUtils.getBaseName(oldStamptestfile.getName()));
        newStamptestfile.setExtension(FilenameUtils.getExtension(oldStamptestfile.getName()));
        newStamptestfile.setData(data);
        newStamptestfile.setLength(data.length);
    }

    public static void forDelete(Stamptestfile stamptestfile) {
        stamptestfile.setActive(States.INACTIVE);
    }

    public static void ofPostMethod(Stamptestfile stamptestfile) {
        stamptestfile.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stamptestfile.setActive(States.ACTIVE);
        stamptestfile.setDeleted(States.EXISTENT);
    }

    public static Stamptestfile forReturn(Stamptestfile stamptestfile) {
        byte[] data = stamptestfile.getData();
        String encodedBase64 = FileUtil.encodeBytes2Base64(data);
        Stamptestfile stamptestfile1 = new Stamptestfile();
        stamptestfile1.setName(stamptestfile.getName());
        stamptestfile1.setBase64Data(encodedBase64);
        return stamptestfile1;
    }

    public static Stamptestfile forReturn(Path path) {
        String base64Data = FileUtil.encode2Base64(path);
        String name = path.toFile().getName();
        Stamptestfile stamptestfile = new Stamptestfile();
        stamptestfile.setName(name);
        stamptestfile.setBase64Data(base64Data);
        return stamptestfile;
    }
}
