package com.msa4meerkatgram.domain.post.entities;

import com.msa4meerkatgram.domain.user.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id; // Long을 쓰는 이유는 null값을 전달할 수도 있기 때문에, null값을 담을 수 있도록 long이 아니라 Long을 쓴다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id"
            , insertable = true // INSERT할 때, user 객체에 어떤 값을 넣더라도, INSERT문에 `user_id`컬럼을 포함하지 않겠다.
            , updatable = false // UPDATE할 때, user 객체에 어떤 값을 넣더라도, UPDATE문에 `user_id`컬럼을 포함하지 않겠다.
            , nullable = false
            , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) // 물리적 FK 생성하고 싶지 않을 때 사용
    )
    private User user;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "image", nullable = false, length = 100)
    private String image;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;
}
