package com.apsout.electronictestimony.api.util.others;

import com.apsout.electronictestimony.api.controller.ResourceController;
import com.apsout.electronictestimony.api.entity.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    public static List<Integer> split2Integers(String identifiers, String pattern) {
        String[] splitedIdentifiers = identifiers.split(Pattern.quote(pattern));
        return Arrays.stream(splitedIdentifiers).map(Integer::valueOf).collect(Collectors.toList());
    }

    public static String concatIds(List<Role> roles) {
        return roles.stream().map(role -> String.valueOf(role.getId())).collect(Collectors.joining(","));
    }

    public static String concatDescriptions(List<Role> roles) {
        return roles.stream().map(role -> String.valueOf(role.getAbbreviation())).collect(Collectors.joining(","));
    }

    public static String concatNameViews(List<Role> roles) {
        return roles.stream().map(role -> String.valueOf(role.getNameView())).collect(Collectors.joining(","));
    }

    public static String decode(String data, String enc) {
        try {
            return URLDecoder.decode(data, enc);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("Decoding data: %s", data));
            return data;
        }
    }

    public static Integer[] split2Integers2(String identifiers, String pattern) {
        String[] splitedIdentifiers = identifiers.split(Pattern.quote(pattern));
        return Arrays.stream(splitedIdentifiers).map(Integer::parseInt).toArray(size -> new Integer[size]);
    }

    public static String replace2Html(String s) {
        return s.replace("Á", "&Aacute;")
                .replace("É", "&Eacute;")
                .replace("Í", "&Iacute;")
                .replace("Ó", "&Oacute;")
                .replace("Ú", "&Uacute;")
                .replace("á", "&aacute;")
                .replace("é", "&eacute;")
                .replace("í", "&iacute;")
                .replace("ó", "&oacute;")
                .replace("ú", "&uacute;");
    }

    public static String removeAccents(String s) {
        return s.replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");
    }

    public static String buildRandomInt(int lenght) {
        Random random = new Random();
        final int bound = 10;
        StringBuilder builder = new StringBuilder(lenght);
        for (int index = 0; index < lenght; index++) {
            builder.append(random.nextInt(bound));
        }
        return builder.toString();
    }
}
