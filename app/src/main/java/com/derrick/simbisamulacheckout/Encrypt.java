package com.derrick.simbisamulacheckout;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Derrick
 * @email <derrick.njeru@cellulant.com>
 * @date Aug 29, 2018
 * <p>
 * Encryption class to encrypt params to pass to express checkout
 */

public class Encrypt {
    /**
     * encryptString function to encrypt your string using AES 128 it encrypts
     * to AES 128 if we specify no padding to be done
     *
     * @param IV
     * @param encryptionKey
     * @param plainText     String
     * @return byte[]
     * @throws Exception
     */

    public static byte[] encryptString(String IV, String encryptionKey, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));

        //manually change the padding of the text to multiple of 16 bits
        return cipher.doFinal(padString(plainText).getBytes());
    }

    /**
     * padString
     * <p>
     * pad the string to change it to 16 bit characters to avoid exception
     *
     * @param source String
     * @return source String
     */
    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }


    /**
     * bytesToHex
     * <p>
     * Convert encrypted byte to a hexadecimal String
     *
     * @param data byte[]
     * @return string String
     */
    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        String string = "";
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16) {
                string = string + "0" + Integer.toHexString(data[i] & 0xFF);
            } else {
                string = string + Integer.toHexString(data[i] & 0xFF);
            }
        }
        return string;
    }
}
