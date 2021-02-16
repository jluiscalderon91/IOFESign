package com.apsout.electronictestimony.api.util;

import com.apsout.electronictestimony.api.entity.Stamplayoutfile;
import com.apsout.electronictestimony.api.entity.Workflow;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StamplayoutfileAllocator {

    public static Stamplayoutfile build(Workflow workflow, Stamplayoutfile stamplayoutfile) {
        final String base64Data = stamplayoutfile.getBase64Data();
        stamplayoutfile.setWorkflowByWorkflowId(workflow);
        stamplayoutfile.setWorkflowId(workflow.getId());
        if (!base64Data.isEmpty()) {
            final byte[] data = FileUtil.convertImageBase64toBytes(base64Data);
            stamplayoutfile.setBasename(FilenameUtils.getBaseName(stamplayoutfile.getName()));
            stamplayoutfile.setExtension(FilenameUtils.getExtension(stamplayoutfile.getName()));
            stamplayoutfile.setData(data);
            stamplayoutfile.setLength(data.length);
        }
        final String excelBase64Data = stamplayoutfile.getExcelBase64Data();
        if (!excelBase64Data.isEmpty()) {
            final byte[] excelData = FileUtil.convertImageBase64toBytes(excelBase64Data);
            stamplayoutfile.setExcelBasename(FilenameUtils.getBaseName(stamplayoutfile.getExcelName()));
            stamplayoutfile.setExcelExtension(FilenameUtils.getExtension(stamplayoutfile.getExcelName()));
            stamplayoutfile.setExcelData(excelData);
            stamplayoutfile.setExcelLength(excelData.length);
        }
        ofPostMethod(stamplayoutfile);
        return stamplayoutfile;
    }

    public static Stamplayoutfile forceBuild(Workflow workflow, Stamplayoutfile oldStamptestfile) {
        Stamplayoutfile stamptestfile = new Stamplayoutfile();
        stamptestfile.setName(oldStamptestfile.getName());
        stamptestfile.setBase64Data(oldStamptestfile.getBase64Data());
        return build(workflow, stamptestfile);
    }

    public static Stamplayoutfile forUpdate(Workflow workflow, Stamplayoutfile stamplayoutfile, Stamplayoutfile stamplayoutDb) {
        Stamplayoutfile stamplayoutfile1 = new Stamplayoutfile();
        stamplayoutfile1.setWorkflowId(workflow.getId());
        stamplayoutfile1.setWorkflowByWorkflowId(workflow);
        if (!stamplayoutfile.getBase64Data().isEmpty() && stamplayoutfile.getExcelBase64Data().isEmpty()) {
            stablishLayoutfile(stamplayoutfile, stamplayoutfile1);
            stamplayoutfile1.setExcelBasename(stamplayoutDb.getExcelBasename());
            stamplayoutfile1.setExcelExtension(stamplayoutDb.getExcelExtension());
            stamplayoutfile1.setExcelData(stamplayoutDb.getExcelData());
            stamplayoutfile1.setExcelLength(stamplayoutDb.getExcelLength());
            stamplayoutfile1.setExcelName(stamplayoutDb.getExcelName());
        }
        if (!stamplayoutfile.getExcelBase64Data().isEmpty() && stamplayoutfile.getBase64Data().isEmpty()) {
            stablishExcelLayoutfile(stamplayoutfile, stamplayoutfile1);
            stamplayoutfile1.setBasename(stamplayoutDb.getBasename());
            stamplayoutfile1.setExtension(stamplayoutDb.getExtension());
            stamplayoutfile1.setData(stamplayoutDb.getData());
            stamplayoutfile1.setLength(stamplayoutDb.getLength());
            stamplayoutfile1.setName(stamplayoutDb.getName());
        }
        if (!stamplayoutfile.getBase64Data().isEmpty() && !stamplayoutfile.getExcelBase64Data().isEmpty()) {
            stablishLayoutfile(stamplayoutfile, stamplayoutfile1);
            stablishExcelLayoutfile(stamplayoutfile, stamplayoutfile1);
        }
        ofPostMethod(stamplayoutfile1);
        return stamplayoutfile1;
    }

    private static void stablishLayoutfile(Stamplayoutfile stamplayoutfileSrc, Stamplayoutfile stamplayoutfileTarget) {
        final byte[] data = FileUtil.convertImageBase64toBytes(stamplayoutfileSrc.getBase64Data());
        stamplayoutfileTarget.setBasename(FilenameUtils.getBaseName(stamplayoutfileSrc.getName()));
        stamplayoutfileTarget.setExtension(FilenameUtils.getExtension(stamplayoutfileSrc.getName()));
        stamplayoutfileTarget.setData(data);
        stamplayoutfileTarget.setLength(data.length);
        stamplayoutfileTarget.setName(stamplayoutfileSrc.getName());
    }

    private static void stablishExcelLayoutfile(Stamplayoutfile stamplayoutfileSrc, Stamplayoutfile stamplayoutfileTarget) {
        final byte[] excelData = FileUtil.convertImageBase64toBytes(stamplayoutfileSrc.getExcelBase64Data());
        stamplayoutfileTarget.setExcelBasename(FilenameUtils.getBaseName(stamplayoutfileSrc.getExcelName()));
        stamplayoutfileTarget.setExcelExtension(FilenameUtils.getExtension(stamplayoutfileSrc.getExcelName()));
        stamplayoutfileTarget.setExcelData(excelData);
        stamplayoutfileTarget.setExcelLength(excelData.length);
        stamplayoutfileTarget.setExcelName(stamplayoutfileSrc.getExcelName());
    }

    public static void forDelete(Stamplayoutfile stamptestfile) {
        stamptestfile.setActive(States.INACTIVE);
    }

    public static void ofPostMethod(Stamplayoutfile stamptestfile) {
        stamptestfile.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        stamptestfile.setActive(States.ACTIVE);
        stamptestfile.setDeleted(States.EXISTENT);
    }

    public static Stamplayoutfile forReturn(Stamplayoutfile stamptestfile) {
        byte[] data = stamptestfile.getData();
        String encodedBase64 = FileUtil.encodeBytes2Base64(data);
        Stamplayoutfile stamptestfile1 = new Stamplayoutfile();
        stamptestfile1.setName(stamptestfile.getName());
        stamptestfile1.setBase64Data(encodedBase64);
        return stamptestfile1;
    }

    public static Stamplayoutfile forReturn(Path path) {
        String base64Data = FileUtil.encode2Base64(path);
        String name = path.toFile().getName();
        Stamplayoutfile stamptestfile = new Stamplayoutfile();
        stamptestfile.setName(name);
        stamptestfile.setBase64Data(base64Data);
        return stamptestfile;
    }
}
