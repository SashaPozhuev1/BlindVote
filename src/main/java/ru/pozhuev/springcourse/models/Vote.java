package ru.pozhuev.springcourse.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Vote {
    private int id;

    @NotEmpty(message = "Bulletin should not be empty")
    @Size(min = 1, message = "Bulletin should be longer")
    private String bulletin;

    @NotEmpty(message = "Bulletin Sign should not be empty")
    @Size(min = 1, message = "Bulletin Sign should be longer")
    private String bulletinSign;

    @NotEmpty(message = "Public Key should not be empty")
    @Size(min = 1, message = "Public Key should be longer")
    private String publicKey;

    @NotEmpty(message = "Cert should not be empty")
    @Size(min = 1, message = "Cert should be longer")
    private String certificate;

    public Vote() {
    }

    public Vote(int id, String bulletin, String bulletinSign, String publicKey, String certificate) {
        this.id = id;
        this.bulletin = bulletin;
        this.bulletinSign = bulletinSign;
        this.publicKey = publicKey;
        this.certificate = certificate;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getBulletinSign() {
        return bulletinSign;
    }

    public void setBulletinSign(String bulletinSign) {
        this.bulletinSign = bulletinSign;
    }
}
