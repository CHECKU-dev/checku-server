package dev.checku.checkuserver.domain.subject.dao;

import dev.checku.checkuserver.domain.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
}
