package com.example.tools.mail;

import java.net.IDN;

/**
 * @author JinXing
 * @date 2018/11/12 19:42
 */
public class IDNMailHelper {

    public static String toIdnAddress(String mail) {
        if (mail == null) {
            return null;
        }
        int idx = mail.indexOf('@');
        if (idx < 0) {
            return mail;
        }
        return localPart(mail, idx) + "@" + IDN.toASCII(domain(mail, idx));
    }

    private static String localPart(String mail, int idx) {
        return mail.substring(0, idx);
    }

    private static String domain(String mail, int idx) {
        return mail.substring(idx + 1);
    }

}
