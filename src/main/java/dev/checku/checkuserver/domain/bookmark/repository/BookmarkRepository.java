package dev.checku.checkuserver.domain.bookmark.repository;

import dev.checku.checkuserver.domain.bookmark.entity.Bookmark;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByUserJpaEntity(UserJpaEntity userJpaEntity);

    Optional<Bookmark> findByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

    Boolean existsByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

//    void deleteAllByUserId(Long userId);
}
