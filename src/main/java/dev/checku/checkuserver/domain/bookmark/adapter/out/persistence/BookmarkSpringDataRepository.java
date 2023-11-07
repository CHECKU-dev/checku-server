package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkSpringDataRepository extends JpaRepository<BookmarkJpaEntity, Long> {
    long deleteByUserIdAndSubjectNumber(Long userId, String subjectNumber);

//    List<BookmarkJpaEntity> findAllByUserJpaEntity(UserJpaEntity userJpaEntity);
//
//    Optional<BookmarkJpaEntity> findByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);
//
//    Boolean existsByUserJpaEntityAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber);

//    void deleteAllByUserId(Long userId);
}
