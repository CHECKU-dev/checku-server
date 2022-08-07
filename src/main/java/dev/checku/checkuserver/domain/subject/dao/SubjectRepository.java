package dev.checku.checkuserver.domain.subject.dao;

import dev.checku.checkuserver.domain.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {

    Optional<Subject> findBySubjectNumber(String subjectNumber);
}
