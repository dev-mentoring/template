package com.wanted.springcafe.domain.Likes;

import com.wanted.springcafe.domain.Likes.dto.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BatchLikesRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insert(List<LikesDto> likesList) {
        String sql = "INSERT INTO likes (user_id, post_id) values(?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                LikesDto like = likesList.get(i);
                ps.setLong(1, like.getUserId());
                ps.setLong(2, like.getPostId());
            }

            @Override
            public int getBatchSize() {
                return likesList.size();
            }
        });
    }

    public void delete(List<LikesDto> likesList) {
        String sql = "DELETE FROM likes where user_id=? and post_id=?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                LikesDto like = likesList.get(i);
                ps.setLong(1, like.getUserId());
                ps.setLong(2, like.getPostId());
            }

            @Override
            public int getBatchSize() {
                return likesList.size();
            }
        });
    }
}
