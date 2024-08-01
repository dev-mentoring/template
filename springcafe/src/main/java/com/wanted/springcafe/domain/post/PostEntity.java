package com.wanted.springcafe.domain.post;

import com.wanted.springcafe.domain.BaseEntity;
import com.wanted.springcafe.domain.comment.CommentEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.exception.PostModificationNotAllowedException;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.type.descriptor.java.BooleanJavaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@NoArgsConstructor
@FilterDef(
        name = "deletedPostFilter",
        parameters = @ParamDef(
                name = "isDeleted",
                type = BooleanJavaType.class
        )
)
@Filter(
        name = "deletedPostFilter",
        condition = "is_deleted = :isDeleted"
)
@SQLDelete(sql = "UPDATE POST SET is_Deleted = true , deleted_At = CURDATE() WHERE id = ?")
@Table(name = "post", indexes = {
        @Index(
                name = "idx_deletedAt", columnList = "deletedAt")
})
@Entity
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(length = 200)
    private String title;
    @Column(length = 1000)
    private String contents;
    private boolean isDeleted;
    private LocalDate deletedAt;
    private Long likeCount = 0L;
    private Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments = new ArrayList<>();

    public PostEntity(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contests, LocalDate now) {
        calculateDaysAfterPublishing(now);
        this.title = title;
        this.contents = contests;
    }

    private void calculateDaysAfterPublishing(LocalDate currentTime) {
        long days = DAYS.between(publishDate, currentTime);
        if (days > 9) {
            throw new PostModificationNotAllowedException("글을 작성한 지 10일이 지났으면 수정할 수 없습니다.");
        }

        if (days == 9) {
            System.out.println("내일부터는 글을 수정할 수가 없습니다.");
        }
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void addComment(CommentEntity comment) {
        this.comments.add(comment);
    }

    public void delete(){
        this.isDeleted =true;
        this.deletedAt = LocalDate.now();
    }
}
