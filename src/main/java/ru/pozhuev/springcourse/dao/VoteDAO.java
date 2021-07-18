package ru.pozhuev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.pozhuev.springcourse.models.Crypto;
import ru.pozhuev.springcourse.models.Variable;
import ru.pozhuev.springcourse.models.Vote;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Component
public class VoteDAO {
    private final JdbcTemplate jdbcTemplate;
    private static int VOTES_COUNT;
    private final PublicKey certCenterPublicKey;

    public PublicKey getCertCenterPublicKey() {
        return certCenterPublicKey;
    }

    @Autowired
    public VoteDAO(JdbcTemplate jdbcTemplate, CryptoDAO cryptoDAO) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.jdbcTemplate = jdbcTemplate;

        // По хорошему так не нужно делать - открытый ключ УЦ сторона голосования просто знает
        Variable variable = this.jdbcTemplate.query("SELECT * FROM variables WHERE name=?",
                new Object[]{"CertCenterPublicKey"},
                new BeanPropertyRowMapper<>(Variable.class))
                .stream().findAny().orElse(null);
        certCenterPublicKey = Crypto.stringToPublicKey(variable.getValue());
    }

    public List<Vote> index() {
        return jdbcTemplate.query("SELECT * FROM votes", new BeanPropertyRowMapper<>(Vote.class));
    }

    public Vote show(int id) {
        //return votes.stream().filter(vote -> vote.getId() == id).findAny().orElse(null);
        return jdbcTemplate.query("SELECT * FROM votes WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Vote.class))
                .stream().findAny().orElse(null);
    }

    public boolean save(Vote vote) {
        // check unic of cert
        boolean success = jdbcTemplate.query("SELECT * FROM votes WHERE certificate=?",
                new Object[]{vote.getCertificate()},
                new BeanPropertyRowMapper<>(Vote.class))
                .isEmpty();
        if(!success)
            return false;

        getVotesCount();
        jdbcTemplate.update("INSERT INTO votes(id, bulletin, bulletinSign, publicKey, certificate) VALUES(?, ?, ?, ?, ?)",
                ++VOTES_COUNT, vote.getBulletin(), vote.getBulletinSign(), vote.getPublicKey(), vote.getCertificate());
        jdbcTemplate.update("UPDATE variables SET value=? WHERE name=?",
                Integer.toString(VOTES_COUNT), "VOTES_COUNT");
        return true;
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM votes WHERE id=?", id, new BeanPropertyRowMapper<>(Vote.class));
    }

    public void getVotesCount() {
        Variable variable = jdbcTemplate.query("SELECT * FROM variables WHERE name=?",
                new Object[]{"VOTES_COUNT"},
                new BeanPropertyRowMapper<>(Variable.class))
                .stream().findAny().orElse(null);

        try {
            VOTES_COUNT = Integer.parseInt(variable.getValue());
        }
        catch (NullPointerException e) {
            VOTES_COUNT = 0;
            jdbcTemplate.update("INSERT INTO variables(name, value) VALUES(?, ?)",
                    "VOTES_COUNT",
                    "0");
        }
    }
}