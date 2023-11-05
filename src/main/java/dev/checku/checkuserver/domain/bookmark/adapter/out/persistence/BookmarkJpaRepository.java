package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

import dev.checku.checkuserver.domain.bookmark.adapter.out.persistence.BookmarkJpaEntity;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkJpaRepository extends JpaRepository<BookmarkJpaEntity, Long> {

    List<BookmarkJpaEntity> findAllByUserJpaEntity(UserJpaEntity userJpaEntity);

    Optional<BookmarkJpaEntity> findByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

    Boolean existsByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

//    void deleteAllByUserId(Long userId);
}
