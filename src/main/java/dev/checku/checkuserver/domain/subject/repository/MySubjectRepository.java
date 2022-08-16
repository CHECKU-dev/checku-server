package dev.checku.checkuserver.domain.subject.repository;

import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MySubjectRepository extends JpaRepository<MySubject, Long> {

    List<MySubject> findAllByUser(User user);

    Optional<MySubject> findBySubjectNumberAndUser(String subjectNumber, User user);

    Boolean existsBySubjectNumberAndUser(String subjectNumber, User user);
}
