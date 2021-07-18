package ru.pozhuev.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.pozhuev.springcourse.models.Vote;

import java.sql.ResultSet;
import java.sql.SQLException;

// EXAMPLE UNNECESSARY CLASS
public class VoteMapper implements RowMapper<Vote> {

    @Override
    public Vote mapRow(ResultSet resultSet, int i) throws SQLException {
        Vote vote = new Vote();

        vote.setId(resultSet.getInt("id"));
        vote.setBulletin(resultSet.getString("bulletin"));
        vote.setCertificate(resultSet.getString("certificate"));

        return vote;
    }
}
