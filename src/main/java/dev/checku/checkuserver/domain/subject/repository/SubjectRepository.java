package dev.checku.checkuserver.domain.subject.repository;

import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {

    Optional<Subject> findBySubjectNumber(SubjectNumber subjectNumber);
}
