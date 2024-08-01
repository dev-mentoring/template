package com.wanted.springcafe.domain.post;

import com.wanted.springcafe.domain.Likes.dto.LikesCountDto;
import com.wanted.springcafe.service.view.ViewCountDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class BatchPostRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insert(List<PostEntity> postList) {
        String sql = "INSERT INTO POST (title,contents,is_deleted,publish_date,last_modified_date, user_id, like_count) " +
                "values(?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PostEntity post = postList.get(i);
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getContents());
                ps.setBoolean(3, post.isDeleted());
                ps.setDate(4, Date.valueOf(post.getPublishDate()));
                ps.setDate(5, Date.valueOf(post.getLastModifiedDate()));
                ps.setLong(6, ((i % 1000)+1));
                ps.setLong(7,0L);
            }

            @Override
            public int getBatchSize() {
                return postList.size();
            }
        });
    }

    public void addLikesCount(List<LikesCountDto> likesCountDtos){
        String sql = "UPDATE POST SET like_count = like_count + ? WHERE post_id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LikesCountDto likesCountDto = likesCountDtos.get(i);
                ps.setLong(1, likesCountDto.getLikesCount());
                ps.setLong(2, likesCountDto.getPostId());

            }
            @Override
            public int getBatchSize() {
                return likesCountDtos.size();
            }
        });
    }

    public void addViewCount(List<ViewCountDto> viewCountDtos){
        String sql = "UPDATE POST SET view_count = view_count + ? WHERE post_id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ViewCountDto viewCountDto = viewCountDtos.get(i);
                ps.setLong(1, viewCountDto.getViewCount());
                ps.setLong(2, viewCountDto.getPostId());

            }
            @Override
            public int getBatchSize() {
                return viewCountDtos.size();
            }
        });
    }

    public void decreaseLikesCount(List<LikesCountDto> likesCountDtos){
        String sql = "UPDATE POST SET like_count = like_count - ? WHERE post_id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LikesCountDto likesCountDto = likesCountDtos.get(i);
                ps.setLong(1, likesCountDto.getLikesCount());
                ps.setLong(2, likesCountDto.getPostId());
            }
            @Override
            public int getBatchSize() {
                return likesCountDtos.size();
            }
        });
    }

}
