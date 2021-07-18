package ru.pozhuev.springcourse.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Crypto {
    public Crypto() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        // Generation
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
//        kpg.initialize(2048);
//        KeyPair kp = kpg.generateKeyPair();
//        Key pub = kp.getPublic();
//        Key pvt = kp.getPrivate();
        // Save
//        Base64.Encoder encoder = Base64.getEncoder();
        // Output
//        String pvt64 = encoder.encodeToString(pvt.getEncoded());
//        System.out.println("-----BEGIN RSA PRIVATE KEY-----\n");
//        System.out.println(pvt64);
//        System.out.println("\n-----END RSA PRIVATE KEY-----\n");
//
//        String pub64 = encoder.encodeToString(pub.getEncoded());
//        System.out.println("-----BEGIN RSA PUBLIC KEY-----\n");
//        System.out.println(pub64);
//        System.out.println("\n-----END RSA PUBLIC KEY-----\n");

        // Check
//        Base64.Decoder decoder = Base64.getDecoder();
//        byte[] keyBytes = decoder.decode(pvt64.getBytes("utf-8"));
//        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        this.pvtKey = keyFactory.generatePrivate(ks);
//        System.out.println(Arrays.equals(pvtKey.getEncoded(), pvt.getEncoded()));
//
//
//        keyBytes = decoder.decode(pub64.getBytes("utf-8"));
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//        PublicKey pubKey = keyFactory.generatePublic(spec);
//        System.out.println(Arrays.equals(pubKey.getEncoded(), pub.getEncoded()));
    }

    public static String keyToString(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static PublicKey stringToPublicKey(String str) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(str.getBytes("utf-8"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey stringToPrivateKey(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(str.getBytes("utf-8"));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(spec);
    }

    public static String sign(String text, PrivateKey certCenterPrivateKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(certCenterPrivateKey);
        sign.update(Base64.getEncoder().encode(text.getBytes("utf-8")));
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public static boolean verify(String signature, String text, PublicKey certCenterPublicKey) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(certCenterPublicKey);
            sign.update(Base64.getEncoder().encode(text.getBytes("utf-8")));
            return sign.verify(Base64.getDecoder().decode(signature.getBytes("utf-8")));
        }
        catch (Exception exception){
            return false;
        }
    }
}
