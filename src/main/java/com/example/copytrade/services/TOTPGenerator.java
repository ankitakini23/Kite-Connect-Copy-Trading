package com.example.copytrade.services;

import de.taimos.totp.TOTP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

@Slf4j
public class TOTPGenerator {
    public static String getTotp(String secretKey) {
        return getTOTPCode(secretKey);
    }

    private static String getTOTPCode(String secretKey) {
        log.info("Get Totp for secretKey : {}", secretKey);
        try {
            Base32 base32 = new Base32();
            byte[] bytes = base32.decode(secretKey);
            String hexKey = Hex.encodeHexString(bytes);
            log.info("hexKey = {}", hexKey);
            return TOTP.getOTP(hexKey);
        } catch (Exception e) {
            log.info("Error Occurred while getting Totp : {}", e.getMessage());
        }
        return null;
    }
}
