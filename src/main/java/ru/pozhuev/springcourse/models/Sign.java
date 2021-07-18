package ru.pozhuev.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Sign {
    // demo id - without registration
//    @NotEmpty(message = "Vote should not be empty")
    @Min(value = 0, message = "ID should be greater than 0")
    private int id;

//    @NotEmpty(message = "Vote should not be empty")
//    @Size(min = 1, message = "Vote should be longer")
//    private String bulletin;

    @NotEmpty(message = "Cert should not be empty")
    @Size(min = 1, message = "Cert should be longer")
    private String publicKey;

    private String certificate;

    public Sign(){}

    public Sign(int id, String bulletin, String publicKey){
        // demo id without registration
        this.id = id;

//        this.bulletin = bulletin;
        this.publicKey = publicKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getBulletin() {
//        return bulletin;
//    }
//
//    public void setBulletin(String bulletin) {
//        this.bulletin = bulletin;
//    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
