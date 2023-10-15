package dev.checku.checkuserver.domain.subject.repository;


import dev.checku.checkuserver.domain.subject.entity.Subject;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SubjectRepositoryCustom {

    List<Subject> findByKeyword(String searchQuery, Pageable pageable);
}
