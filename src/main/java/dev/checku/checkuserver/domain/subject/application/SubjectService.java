package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.model.SubjectType;
import dev.checku.checkuserver.domain.subject.repository.SubjectRepository;
import dev.checku.checkuserver.domain.subject.dto.GetSearchSubjectDto;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.infra.portal.PortalFeignClient;
import dev.checku.checkuserver.infra.portal.PortalRes;
import dev.checku.checkuserver.global.util.PortalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final UserService userService;
    private final MySubjectService mySubjectService;
    private final SubjectRepository subjectRepository;
    private final PortalFeignClient portalFeignClient;

    private PortalRes getAllSubjectsFromPortal(String session) {
        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                session,
                PortalUtils.header,
                PortalUtils.createBody("2022", "B01012", "", "", "")
        );

        return response.getBody();
    }

    @Transactional
    public void insertSubjects(String session) {
        PortalRes subjectListDto = getAllSubjectsFromPortal(session);
        List<PortalRes.SubjectDto> subjects = subjectListDto.getSubjects();
        List<Subject> subjectList = new ArrayList<>();

        for (PortalRes.SubjectDto subjectDto : subjects) {
            if (subjectDto.getSubjectNumber() != null) {
                Subject subject;
                if (subjectDto.getSubjectType().equals("전선") || subjectDto.getSubjectType().equals("전필")) {
                    subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.MAJOR);
                } else {
                    subject = Subject.createSubject(subjectDto.getSubjectNumber(), subjectDto.getName(), SubjectType.LIBERAL_ARTS);
                }
                subjectList.add(subject);
            }
        }

        subjectRepository.saveAll(subjectList);
    }

    public Subject getSubjectBySubjectNumber(String subjectNumber) {
        return subjectRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUBJECT_NOT_FOUND));
    }


    public Slice<GetSearchSubjectDto.Response> getSubjectsByKeyword(GetSearchSubjectDto.Request dto, Pageable pageable, String session) {
        User user = userService.getUserById(dto.getUserId());

        List<String> subjectList = mySubjectService.getAllSubjectsByUser(user).stream()
                .map(MySubject::getSubjectNumber)
                .collect(Collectors.toList());

        List<Subject> subject = subjectRepository.findSubjectByKeyword(dto.getSearchQuery(), pageable);

        List<GetSearchSubjectDto.Response> results = subject.parallelStream()
                .map(mySubject -> {
                    ResponseEntity<PortalRes> response = portalFeignClient.getSubject(
                            session,
                            PortalUtils.header,
                            PortalUtils.createBody("2022", "B01012", "", "", mySubject.getSubjectNumber()));
                    return GetSearchSubjectDto.Response.from(response.getBody().getSubjects().get(0), subjectList);
                })
                .collect(Collectors.toList());

        boolean hasNext = false;
        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
