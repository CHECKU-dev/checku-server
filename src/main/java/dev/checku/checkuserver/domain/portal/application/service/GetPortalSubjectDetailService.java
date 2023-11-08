package dev.checku.checkuserver.domain.portal.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.domain.portal.application.port.in.GetPortalSubjectUseCase;
import dev.checku.checkuserver.domain.portal.application.port.out.GetPortalSubjectDetailPort;
import dev.checku.checkuserver.domain.subject.domain.Department;
import dev.checku.checkuserver.domain.subject.domain.SubjectCategory;
import dev.checku.checkuserver.domain.temp.PortalSubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@UseCase
@RequiredArgsConstructor
public class GetPortalSubjectDetailService implements GetPortalSubjectUseCase {

    private final GetPortalSubjectDetailPort getPortalSubjectDetailPort;
    private final PortalRequestHeaderFactory portalRequestHeaderFactory;
    private final PortalRequestBodyFactory portalRequestBodyFactory;

    public void get(SubjectCategory subjectCategory, Department department, SubjectNumber subjectNumber) {
        Map<String, String> header = portalRequestHeaderFactory.create();
        MultiValueMap<String, String> body = portalRequestBodyFactory.create("2023", "B01011", subjectCategory, department, subjectNumber);

        PortalSubjectResponse response = getPortalSubjectDetailPort.getSubjects(header, body);
    }
}
