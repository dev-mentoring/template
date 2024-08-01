package com.wanted.springcafe.domain.comment;

import com.wanted.springcafe.domain.BaseEntity;
import com.wanted.springcafe.domain.post.PostEntity;
import com.wanted.springcafe.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.type.descriptor.java.BooleanJavaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@FilterDef(
        name="deletedCommentFilter",
        parameters = @ParamDef(
                name ="isDeleted",
                type = BooleanJavaType.class
        )
)
@Filter(
        name="deletedCommentFilter",
        condition = "is_deleted = :isDeleted"
)
@SQLDelete(sql = "UPDATE COMMENT SET is_Deleted = true , deleted_At = CURDATE() WHERE comment_Id = ?")
@Table(name = "comment", indexes ={
        @Index(
                name = "idx_deletedAt", columnList = "deletedAt")
})
@Entity
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 500)
    private String content;
    private boolean isDeleted;
    private LocalDate deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<CommentEntity> children = new ArrayList<>();

    public CommentEntity(String content) {
        this.content = content;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public void setParent(CommentEntity parent){
        this.parent = parent;
    }

    public boolean isCommentWriter(Long userId){
        return this.user.getUserId().equals(userId);
    }

    public void update(String content) {
        this.content =content;
    }

    public void delete(){
        this.isDeleted =true;
        this.deletedAt =LocalDate.now();
    }

    public void restore(){
        this.isDeleted = false;
    }

}
