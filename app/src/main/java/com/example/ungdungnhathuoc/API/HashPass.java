package com.example.ungdungnhathuoc.API;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class HashPass {
    private String pass;
    public HashPass(String pass) {
        this.pass = pass;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getHashPass() {
        return hashMD5(pass);
    }
    public String hashMD5(String input) {
        try {
            // Create an instance of the MD5 digest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash and get the byte array
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // Convert byte to hex
                if (hex.length() == 1) hexString.append('0'); // Pad single digit hex
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Handle exception if the algorithm is not available
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

}
