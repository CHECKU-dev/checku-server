package dev.checku.checkuserver.domain.portal.application;

import dev.checku.checkuserver.domain.portal.dto.PortalResponse;
import dev.checku.checkuserver.domain.portal.infra.PortalFeignClient;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.global.util.PortalRequestFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalSubjectService {

    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final PortalRequestFactory portalRequestFactory;

    public PortalResponse getAllSubjectsBySubjectNumber(SubjectNumber subjectNumber) {
        PortalResponse response = portalFeignClient.getSubjects(
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
