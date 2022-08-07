package dev.checku.checkuserver.domain.subject.dao;

import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MySubjectRepository extends JpaRepository<MySubject, Long> {

    List<MySubject> findAllByUser(User user);

    MySubject findBySubjectNumberAndUser(String subjectNumber, User user);

    Boolean existsBySubjectNumberAndUser(String subjectNumber, User user);
}
