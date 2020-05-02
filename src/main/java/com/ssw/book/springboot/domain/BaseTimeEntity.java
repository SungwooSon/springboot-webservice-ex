package com.ssw.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @MappedSuperclass
 * JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 한다.
 *
 * @EntityListeners(AuditingEntityListener.class)
 * BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    /**
     * @CreatedDate
     * Entity가 생성되어 저장될 때 시간이 자동 저장된다.
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * @LastModifiedDate
     * 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
/**
 * Entity 클래스가 BaseTimeEntity 상속받고
 * 메인 클래스(Application)에서 JPA Auditing 어노테이션들을 모두 활성화할 수 있도록 @EnableJpaAuditing 추가
 */
