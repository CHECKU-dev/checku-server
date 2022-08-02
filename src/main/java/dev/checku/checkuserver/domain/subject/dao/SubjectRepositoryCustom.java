package dev.checku.checkuserver.domain.subject.dao;


import dev.checku.checkuserver.domain.subject.entity.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface SubjectRepositoryCustom {

    List<Subject> findSubjectBySearch(String searchQuery, Pageable pageable);
}
