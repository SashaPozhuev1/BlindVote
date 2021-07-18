package ru.pozhuev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.pozhuev.springcourse.models.Crypto;
import ru.pozhuev.springcourse.models.Sign;
import ru.pozhuev.springcourse.models.Variable;
import ru.pozhuev.springcourse.models.Vote;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class CryptoDAO {
    private final JdbcTemplate jdbcTemplate;
    private final PrivateKey certCenterPrivateKey;
    private final PublicKey certCenterPublicKey;

    @Autowired
    public CryptoDAO(JdbcTemplate jdbcTemplate) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.jdbcTemplate = jdbcTemplate;

        // Get PRIVATE and PUBLIC keys from DB
        Variable variable = this.jdbcTemplate.query("SELECT * FROM variables WHERE name=?",
                new Object[]{"CertCenterPrivateKey"},
                new BeanPropertyRowMapper<>(Variable.class))
                .stream().findAny().orElse(null);
        certCenterPrivateKey = Crypto.stringToPrivateKey(variable.getValue());

        variable = this.jdbcTemplate.query("SELECT * FROM variables WHERE name=?",
                new Object[]{"CertCenterPublicKey"},
                new BeanPropertyRowMapper<>(Variable.class))
                .stream().findAny().orElse(null);
        certCenterPublicKey = Crypto.stringToPublicKey(variable.getValue());

        // УДАЛЯТЬ - СТАРТ
        // Check Output
        System.out.println("-----BEGIN RSA PRIVATE KEY-----\n");
        System.out.println(Crypto.keyToString(certCenterPrivateKey));
        System.out.println("\n-----END RSA PRIVATE KEY-----\n");

        System.out.println("-----BEGIN RSA PUBLIC KEY-----\n");
        System.out.println(Crypto.keyToString(certCenterPublicKey));
        System.out.println("\n-----END RSA PUBLIC KEY-----\n");
        // УДАЛЯТЬ - КОНЕЦ
    }

    public PublicKey getCertCenterPublicKey() {
        return certCenterPublicKey;
    }

    public PrivateKey getCertCenterPrivateKey() {
        return certCenterPrivateKey;
    }

    public boolean save(Sign sign) {
        // check vote != true
        if(!jdbcTemplate.query("SELECT * FROM vote_1 WHERE id=? AND vote=true",
                new Object[]{sign.getId()},
                new BeanPropertyRowMapper<>(Sign.class))
                .isEmpty())
            return false;

        // По-хорошему не нужно insert
        if(jdbcTemplate.query("SELECT * FROM vote_1 WHERE id=?",
                new Object[]{sign.getId()},
                new BeanPropertyRowMapper<>(Sign.class))
                .isEmpty())
            jdbcTemplate.update("INSERT INTO vote_1(id, vote) VALUES(?, ?)", sign.getId(), true);
        else
            jdbcTemplate.update("UPDATE vote_1 SET vote=? WHERE id=? ", true, sign.getId());
        return true;
    }
}
