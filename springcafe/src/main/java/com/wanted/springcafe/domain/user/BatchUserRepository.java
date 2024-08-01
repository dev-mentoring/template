package com.wanted.springcafe.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BatchUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insert(List<UserEntity> userList){
        String sql = "INSERT INTO user (username, email, login_id, password, phone_number, is_deleted) " +
                "VALUES (?,?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserEntity user = userList.get(i);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getLoginId());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getPhoneNumber());
                ps.setBoolean(6, user.isDeleted());
            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        });

    }
}
