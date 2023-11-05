package dev.checku.checkuserver.domain.subject.adpater.out.persistence;


import dev.checku.checkuserver.domain.subject.adpater.out.persistence.SubjectJpaEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SubjectRepositoryCustom {

    List<SubjectJpaEntity> findByKeyword(String searchQuery, Pageable pageable);
}
