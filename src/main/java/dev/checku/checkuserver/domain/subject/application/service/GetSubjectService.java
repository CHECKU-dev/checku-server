package dev.checku.checkuserver.domain.subject.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.subject.application.port.in.GetSubjectUseCase;
import dev.checku.checkuserver.domain.subject.domain.SearchCondition;

@UseCase
public class GetSubjectService implements GetSubjectUseCase {

    public void get(Long userId, SearchCondition searchCondition) {

    }
}
