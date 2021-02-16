package com.apsout.electronictestimony.api.util.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EmailAddress {
    private static final Logger logger = LoggerFactory.getLogger(EmailAddress.class);

    public static InternetAddress getFromAddress() {
        final String address = "not-reply@midomicilio.pe";
        final String personal = "IOFE SAC.";
        try {
            return new InternetAddress(address, personal);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("Generating sender: %s, personal: %s", address, personal));
            return null;
        }
    }

    public static List<InternetAddress> prepareRecipients(String... recipients) {
        return Arrays.stream(recipients).map(recipient -> {
            try {
                return new InternetAddress(recipient);
            } catch (AddressException e) {
                logger.error(String.format("Building address recipients for mail: %s", recipient), e);
                return null;
            }
        }).collect(Collectors.toList());
    }

    public static InternetAddress build(String address) throws AddressException {
        try {
            return new InternetAddress(address);
        } catch (AddressException e) {
            throw new AddressException(String.format("Building address recipient for mail: %s", address));
        }
    }

    public static InternetAddress[] prepareRecipients(List<String> recipients) throws AddressException {
        List<InternetAddress> addresses = new ArrayList<>();
        for (String s : recipients) {
            addresses.add(build(s));
        }
        return addresses.toArray(new InternetAddress[0]);
    }

    public static String getFromOf(MimeMessage mimeMessage) {
        try {
            final int firstFromAddressPos = 0;
            Address address = mimeMessage.getFrom()[firstFromAddressPos];
            return address.toString();
        } catch (MessagingException e) {
            throw new RuntimeException("Extracting mail sender of Mimemessage");
        }
    }

    public static String getToOf(MimeMessage mimeMessage) {
        try {
            Address[] addresses = mimeMessage.getRecipients(Message.RecipientType.TO);
            return Arrays.stream(addresses).map(Address::toString).collect(Collectors.joining(","));
        } catch (MessagingException e) {
            throw new RuntimeException("Extracting mail recipients of Mimemessage");
        }
    }

    public static InternetAddress[] prepareRecipients(String recipients, String separator) throws AddressException {
        if (recipients == null || recipients.isEmpty()) {
            return new InternetAddress[]{};
        }
        List<String> list = Arrays.asList(recipients.split(Pattern.quote(separator)));
        return prepareRecipients(list);
    }
}
