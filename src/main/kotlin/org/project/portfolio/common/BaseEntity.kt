package org.project.portfolio.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.SoftDelete
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

/**
 * 엔티티의 공통 필드
 * <p>JPA의 AditingEntityListener를 사용하여 생성일, 수정일을 자동으로 관리한다. </p>
 * <p>따라서 Main class에 꼭 <strong>@EnableJpaAuditing</strong>을 붙여야 한다.</p>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@SoftDelete(columnName = "is_deleted")
abstract class BaseEntity {
    /** 생성일 */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    /** 수정일 */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
}
