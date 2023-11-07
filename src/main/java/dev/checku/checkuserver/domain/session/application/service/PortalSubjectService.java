package dev.checku.checkuserver.domain.session.application.service;

import dev.checku.checkuserver.domain.temp.PortalSubjectResponse;
import dev.checku.checkuserver.domain.temp.PortalFeignClient;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.global.util.PortalRequestFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalSubjectService {

    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final PortalRequestFactory portalRequestFactory;

    public PortalSubjectResponse getAllSubjectsBySubjectNumber(SubjectNumber subjectNumber) {
        PortalSubjectResponse response = portalFeignClient.getSubjects(
                portalRequestFactory.createHeader(),
                portalRequestFactory.createBody("", "", subjectNumber.getValue())
        );

        if (response.isFail()) {
            updatePortalSessionAndThrow();
        }

        return response;
    }

    private void updatePortalSessionAndThrow() {
//        portalSessionService.updatePortalSession();
        throw new SubjectRetryException();
    }
}
