package com.apsout.electronictestimony.api.util.x509;

/*
import com.apsout.electronictestimony.api.exception.CertificateVerificationException;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
*/

import com.apsout.electronictestimony.api.entity.Siecertificate;
import com.apsout.electronictestimony.api.exception.CertificateCredentialException;
import com.apsout.electronictestimony.api.util.file.FileUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.Collections;

public class CertificateUtil {

    private static final Logger logger = LoggerFactory.getLogger(CertificateUtil.class);

    private static String getFirstCertificateAlias(File certificate, String keyStorePass) {
        KeyStore keyStore = generateKeystore(certificate, keyStorePass);
        try {
            return Collections.list(keyStore.aliases()).get(0);
        } catch (KeyStoreException e) {
            logger.error(String.format("Listing inner certificates of: %s", certificate.getAbsolutePath()));
            return "emptyAlias";
        }
    }

    public static X509Certificate getCertificateBy(File certificate, String keyStorePass) {
        String alias = getFirstCertificateAlias(certificate, keyStorePass);
        KeyStore keyStore = generateKeystore(certificate, keyStorePass);
        try {
            return (X509Certificate) keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            logger.error(String.format("Obtaining certificate with alias: %s", alias));
            return null;
        }
    }

    public static X509Certificate buildX509Certificate(Siecertificate siecertificate) {
        final File certificateFile = buildP12CertificateFile(siecertificate);
        return CertificateUtil.getCertificateBy(certificateFile, siecertificate.getPassword());
    }

    public static String getFirstCertificateAlias(Siecertificate siecertificate) {
        final File certificateFile = buildP12CertificateFile(siecertificate);
        return CertificateUtil.getFirstCertificateAlias(certificateFile, siecertificate.getPassword());
    }

    public static File buildP12CertificateFile(Siecertificate siecertificate) {
        final File certificate = FileUtil.write2NewFile(siecertificate.getData(), "cert-", ".p12");
        logger.info(String.format("Certificate was builded for siecredentialId: %s", siecertificate.getName()));
        return certificate;
    }

    public static KeyStore generateKeystore(File certificate, String keyStorePass) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            logger.error("Creating an instance of key store PKCS12", e);
        }
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(certificate);
        } catch (FileNotFoundException e) {
            logger.error(String.format("Reading certificate file of path: %s", certificate.getAbsolutePath()), e);
        }
        try {
            keyStore.load(stream, keyStorePass.toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            logger.error(String.format("Loading certificate file of path: %s", certificate.getAbsolutePath()), e);
            throw new CertificateCredentialException("La contraseña del certificado es incorrecto, por favor comuníquese con el administrador de sistema.");
        }
        return keyStore;
    }

    /*
    public static void verifyCertificateCRLs(X509Certificate cert) throws CertificateVerificationException, IOException {
        List<String> crlDistPoints = null;
        try {
            crlDistPoints = getCrlDistributionPoints(cert);
        } catch (CertificateParsingException e) {
            throw new CertificateVerificationException("Error al parsear el certificado", e);
        } catch (IOException e) {
            throw new CertificateVerificationException("Error de lectura del certificado", e);
        }

        for (String crlDP : crlDistPoints) {

            try {
                //TODO: crl2 is not found, We need check this.
//                if (crlDP.equals("http://crl2.reniec.gob.pe/crl/caclass2.crl")) {
//                    crlDP = "http://crl.reniec.gob.pe/crl/caclass2.crl";
//                }
//
//                if (crlDP.equals("http://crl2.reniec.gob.pe/crl/caclass1.crl")) {
//                    crlDP = "http://crl.reniec.gob.pe/crl/caclass1.crl";
//                }

                X509CRL crl = downloadCRL(crlDP);

                if (crl.isRevoked(cert)) {
                    throw new CertificateVerificationException("The certificate is revoked by CRL: " + crlDP);
                }
            } catch (CertificateException ex) {
                throw new CertificateVerificationException("Certificate exception ", ex);
            } catch (CRLException ex) {
                throw new CertificateVerificationException("CRL exception ", ex);
            } catch (NamingException ex) {
                throw new CertificateVerificationException("NamingException exception ", ex);
            }
        }
    }

    public static X509CRL downloadCRL(String crlURL) throws IOException,
            CertificateException, CRLException,
            CertificateVerificationException, NamingException {

        if (crlURL.startsWith("http://") || crlURL.startsWith("https://")
                || crlURL.startsWith("ftp://")) {
            X509CRL crl = downloadCRLFromWeb(crlURL);
            return crl;
        } else if (crlURL.startsWith("ldap://")) {
            X509CRL crl = downloadCRLFromLDAP(crlURL);
            return crl;
        } else {
            throw new CertificateVerificationException(
                    "Can not download CRL from certificate "
                            + "distribution point: " + crlURL
            );
        }
    }

    private static X509CRL downloadCRLFromLDAP(String ldapURL)
            throws CertificateException, NamingException, CRLException,
            CertificateVerificationException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);

        DirContext ctx = new InitialDirContext(env);
        Attributes avals = ctx.getAttributes("");
        Attribute aval = avals.get("certificateRevocationList;binary");
        byte[] val = (byte[]) aval.get();
        if ((val == null) || (val.length == 0)) {
            throw new CertificateVerificationException(
                    "Can not download CRL from: " + ldapURL);
        } else {
            InputStream inStream = new ByteArrayInputStream(val);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509CRL crl = (X509CRL) cf.generateCRL(inStream);
            return crl;
        }
    }

    private static X509CRL downloadCRLFromWeb(String crlURL)
            throws MalformedURLException, IOException, CertificateException,
            CRLException {
        URL url = new URL(crlURL);
        InputStream crlStream = url.openStream();
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509CRL crl = (X509CRL) cf.generateCRL(crlStream);
            return crl;
        } finally {
            crlStream.close();
        }
    }

    public static List<String> getCrlDistributionPoints(X509Certificate cert) throws CertificateParsingException, IOException {
        byte[] crldpExt = cert.getExtensionValue(
                X509Extensions.CRLDistributionPoints.getId());
        if (crldpExt == null) {
            List<String> emptyList = new ArrayList<>();
            return emptyList;
        }
        ASN1InputStream oAsnInStream = new ASN1InputStream(
                new ByteArrayInputStream(crldpExt));
        ASN1Primitive derObjCrlDP = oAsnInStream.readObject();
        DEROctetString dosCrlDP = (DEROctetString) derObjCrlDP;
        byte[] crldpExtOctets = dosCrlDP.getOctets();
        ASN1InputStream oAsnInStream2 = new ASN1InputStream(
                new ByteArrayInputStream(crldpExtOctets));
        ASN1Primitive derObj2 = oAsnInStream2.readObject();
        CRLDistPoint distPoint = CRLDistPoint.getInstance(derObj2);
        List<String> crlUrls = new ArrayList<>();
        for (DistributionPoint dp : distPoint.getDistributionPoints()) {
            DistributionPointName dpn = dp.getDistributionPoint();
            // Look for URIs in fullName
            if (dpn != null) {
                if (dpn.getType() == DistributionPointName.FULL_NAME) {
                    GeneralName[] genNames = GeneralNames.getInstance(
                            dpn.getName()).getNames();
                    // Look for an URI
                    for (GeneralName genName : genNames) {
                        if (genName.getTagNo() == GeneralName.uniformResourceIdentifier) {
                            String url = DERIA5String.getInstance(genName.getName()).getString();
                            crlUrls.add(url);
                        }
                    }
                }
            }
        }
        return crlUrls;
    }
     */
}
