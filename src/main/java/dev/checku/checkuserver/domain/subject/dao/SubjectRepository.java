package dev.checku.checkuserver.domain.subject.dao;

import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByUser(User user);

    Subject findBySubjectNumberAndUser(String subjectNumber, User user);
}
