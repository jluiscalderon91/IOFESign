package com.apsout.electronictestimony.api.util.allocator;

import com.apsout.electronictestimony.api.entity.Person;
import com.apsout.electronictestimony.api.entity.Personrubric;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import com.apsout.electronictestimony.api.util.statics.States;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PersonrubricAllocator {

    public static Personrubric build(Person person) {
        Personrubric personrubric = new Personrubric();
        personrubric.setPersonId(person.getId());
        final byte[] data = FileUtil.convertImageBase64toBytes(person.getRubricBase64Data());
        String filename = person.getRubricFilename();
        personrubric.setName(filename);
        personrubric.setBasename(FilenameUtils.getBaseName(filename));
        personrubric.setExtension(FilenameUtils.getExtension(filename));
        personrubric.setData(data);
        personrubric.setLength(data.length);
        ofPostMethod(personrubric);
        return personrubric;
    }

    public static Personrubric forUpdate(Person person, Personrubric personrubric) {
        final byte[] data = FileUtil.convertImageBase64toBytes(person.getRubricBase64Data());
        String filename = person.getRubricFilename();
        personrubric.setName(filename);
        personrubric.setBasename(FilenameUtils.getBaseName(filename));
        personrubric.setExtension(FilenameUtils.getExtension(filename));
        personrubric.setData(data);
        personrubric.setLength(data.length);
        return personrubric;
    }

    public static void ofPostMethod(Personrubric personrubric) {
        personrubric.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        personrubric.setActive(States.ACTIVE);
        personrubric.setDeleted(States.EXISTENT);
    }

    public static void forUpdate(Personrubric personrubric, String rubricFilename, String rubricFileBase64) {
        final byte[] data = FileUtil.convertImageBase64toBytes(rubricFileBase64);
        personrubric.setData(data);
        personrubric.setName(rubricFilename);
        personrubric.setBasename(FilenameUtils.getBaseName(rubricFilename));
        personrubric.setExtension(FilenameUtils.getExtension(rubricFilename));
        personrubric.setLength(data.length);
    }

    public static Personrubric build(Person person, String rubricFilename, String rubricFileBase64) {
        Personrubric personrubric = new Personrubric();
        personrubric.setPersonId(person.getId());
        final byte[] data = FileUtil.convertImageBase64toBytes(rubricFileBase64);
        personrubric.setData(data);
        personrubric.setName(rubricFilename);
        personrubric.setBasename(FilenameUtils.getBaseName(rubricFilename));
        personrubric.setExtension(FilenameUtils.getExtension(rubricFilename));
        personrubric.setLength(data.length);
        ofPostMethod(personrubric);
        return personrubric;
    }
}
