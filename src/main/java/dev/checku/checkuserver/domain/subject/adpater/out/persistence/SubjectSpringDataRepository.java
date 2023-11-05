package dev.checku.checkuserver.domain.subject.adpater.out.persistence;

import dev.checku.checkuserver.domain.common.SubjectNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectSpringDataRepository extends JpaRepository<SubjectJpaEntity, Long>, SubjectRepositoryCustom {

    Optional<SubjectJpaEntity> findBySubjectNumber(SubjectNumber subjectNumber);
}
