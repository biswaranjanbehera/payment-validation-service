package com.cpt.payments.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class HmacSha256Utils {
    private Logger LOGGER = LogManager.getLogger(HmacSha256Utils.class);
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    //Method to calculate HMAC-SHA256
    public String calculateHmac(String secretKey, String data) {
        try{
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(hmacBytes);
        }catch(Exception ex){
            LOGGER.error("Exception generating HmacSignature");
            return null;
        }
    }

    //Method to verify HMAC-SHA256
    public boolean verify(String secretKey, String data, String recievedHmac){
        try{
            String calculatedHmac = calculateHmac(secretKey,data);
            LOGGER.info("calculated hmac:- {} | recieved hmac:- {]",calculatedHmac,recievedHmac);
            return calculatedHmac.equals(recievedHmac);

        }catch(Exception ex){
            LOGGER.error("Exception verifying HmacSignature");
            return false;
        }

    }
}
