package ru.pozhuev.springcourse.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Client {
//    @NotEmpty(message = "Public Key should not be empty")
//    @Size(min = 1, message = "Public Key should be longer")
//    private String publicKey;

    @NotEmpty(message = "Private Key should not be empty")
    @Size(min = 1, message = "Private Key should be longer")
    private String privateKey;

    @NotEmpty(message = "Public Key should not be empty")
    @Size(min = 1, message = "Public Key should be longer")
    private String publicKey;
    private String publicKeyMasked;

    @NotEmpty(message = "Bulletin should not be empty")
    @Size(min = 1, message = "Bulletin should be longer")
    private String bulletin;
    private String bulletinSign;

    public Client (){}

//    public String getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(String publicKey) {
//        this.publicKey = publicKey;
//    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public String getBulletinSign() {
        return bulletinSign;
    }

    public void setBulletinSign(String bulletinSign) {
        this.bulletinSign = bulletinSign;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicKeyMasked(String publicKeyMasked) {
        this.publicKeyMasked = publicKeyMasked;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyMasked() {
        return publicKeyMasked;
    }
}
