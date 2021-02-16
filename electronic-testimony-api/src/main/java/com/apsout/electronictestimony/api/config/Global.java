package com.apsout.electronictestimony.api.config;

import com.apsout.electronictestimony.api.util.enums.Mode;

public class Global {
    public static final Mode MODE = Mode.DEVELOPMENT;
    public static final String PROTOCOL;
    public static final String PORT;
    public static final String API_DOMAIN;
    public static final String DOMAIN;
    public static final String ROOT;
    public static final String TYPE;
    public static final String VERSION;
    public static final String ROOT_API_V1;
    public static final String HOST_FRONT;
    public static final String PORT_FRONT;
    public static final String ROOT_FRONT;
    public static final String ROOT_URL_MAIL;
    public static final String TSA_URL;
    public static final String TSA_USERNAME;
    public static final String TSA_PASSWORD;

    static {
        TSA_URL = "http://tsu.camerfirma.com:5004/ts.inx";
        TSA_USERNAME = "pitsap016";
        TSA_PASSWORD = "8K_654FF";
        switch (MODE) {
            case LOCAL:
                DOMAIN = "localhost";
                PROTOCOL = "http";
                PORT = "8080";
                API_DOMAIN = DOMAIN;
                ROOT = PROTOCOL + "://" + API_DOMAIN + ":" + PORT;
                TYPE = "api";
                VERSION = "v1";
                ROOT_API_V1 = PROTOCOL + "://" + API_DOMAIN + ":" + PORT + "/" + TYPE + "/" + VERSION;
                HOST_FRONT = "Not used in local environment";
                PORT_FRONT = "4200";
                ROOT_FRONT = PROTOCOL + "://" + API_DOMAIN + ":" + PORT_FRONT + "/#";
                ROOT_URL_MAIL = PROTOCOL + "://" + API_DOMAIN + ":" + PORT_FRONT;
                break;
            case DEVELOPMENT:
                DOMAIN = "dev.iofesign.com";
                PROTOCOL = "https";
                PORT = "443";
                API_DOMAIN = "api." + DOMAIN;
                ROOT = PROTOCOL + "://" + API_DOMAIN + ":" + PORT;
                TYPE = "api";
                VERSION = "v1";
                ROOT_API_V1 = PROTOCOL + "://" + API_DOMAIN + ":" + PORT + "/" + TYPE + "/" + VERSION;
                HOST_FRONT = DOMAIN;
                PORT_FRONT = "Not used in development/quality/production environment";
                ROOT_FRONT = PROTOCOL + "://" + HOST_FRONT + "/#";
                ROOT_URL_MAIL = PROTOCOL + "://" + HOST_FRONT;
                break;
            case QUALITY:
                DOMAIN = "qa.iofesign.com";
                PROTOCOL = "https";
                PORT = "443";
                API_DOMAIN = "api." + DOMAIN;
                ROOT = PROTOCOL + "://" + API_DOMAIN + ":" + PORT;
                TYPE = "api";
                VERSION = "v1";
                ROOT_API_V1 = PROTOCOL + "://" + API_DOMAIN + ":" + PORT + "/" + TYPE + "/" + VERSION;
                HOST_FRONT = DOMAIN;
                PORT_FRONT = "Not used in development/quality/production environment";
                ROOT_FRONT = PROTOCOL + "://" + HOST_FRONT + "/#";
                ROOT_URL_MAIL = PROTOCOL + "://" + HOST_FRONT;
                break;
            default:
                DOMAIN = "iofesign.com";
                PROTOCOL = "https";
                PORT = "443";
                API_DOMAIN = "api." + DOMAIN;
                ROOT = PROTOCOL + "://" + API_DOMAIN + ":" + PORT;
                TYPE = "api";
                VERSION = "v1";
                ROOT_API_V1 = PROTOCOL + "://" + API_DOMAIN + ":" + PORT + "/" + TYPE + "/" + VERSION;
                HOST_FRONT = DOMAIN;
                PORT_FRONT = "Not used in development/quality/production environment";
                ROOT_FRONT = PROTOCOL + "://" + HOST_FRONT + "/#";
                ROOT_URL_MAIL = PROTOCOL + "://" + HOST_FRONT;
                break;
        }
    }
}
