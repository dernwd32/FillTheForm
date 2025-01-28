package tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MD5 {
    public String md5(String input) {

        byte[] bytesOfMessage = null;
        bytesOfMessage = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] theMD5digest = md.digest(bytesOfMessage);
        return Arrays.toString(theMD5digest);

    }
}
