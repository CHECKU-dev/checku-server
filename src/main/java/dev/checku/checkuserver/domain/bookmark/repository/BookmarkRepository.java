package dev.checku.checkuserver.domain.bookmark.repository;

import dev.checku.checkuserver.domain.bookmark.entity.Bookmark;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByUser(User user);

    Optional<Bookmark> findByUserAndSubjectNumber(User user, SubjectNumber subjectNumber);

    Boolean existsByUserAndSubjectNumber(User user, SubjectNumber subjectNumber);

    void deleteAllByUserId(Long userId);
}
