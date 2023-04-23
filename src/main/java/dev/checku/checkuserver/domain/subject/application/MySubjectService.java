package dev.checku.checkuserver.domain.subject.application;

import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.domain.subject.dto.GetMySubjectDto;
import dev.checku.checkuserver.domain.subject.dto.RemoveMySubjectReq;
import dev.checku.checkuserver.domain.subject.dto.SaveSubjectReq;
import dev.checku.checkuserver.domain.subject.entity.MySubject;
import dev.checku.checkuserver.domain.subject.exception.SubjectRetryException;
import dev.checku.checkuserver.domain.subject.repository.MySubjectRepository;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import dev.checku.checkuserver.domain.portal.application.PortalFeignClient;
import dev.checku.checkuserver.domain.portal.dto.PortalRes;
import dev.checku.checkuserver.global.util.PortalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySubjectService {

    private final UserService userService;
    private final MySubjectRepository mySubjectRepository;
    private final PortalFeignClient portalFeignClient;
    private final PortalSessionService portalSessionService;
    private final int THREAD_COUNT = 3;

    // TODO -> 변경하기 save, remove 나눠야함
    @Deprecated
    @Transactional
    public void saveOrRemoveSubject(SaveSubjectReq request) {

        User user = userService.getUserById(request.getUserId());
        // 삭제
        if (mySubjectRepository.existsBySubjectNumberAndUser(request.getSubjectNumber(), user)) {
            MySubject mySubject = getMySubject(request.getSubjectNumber(), user);
            mySubjectRepository.delete(mySubject);
        }
        // 추가
        else {
            MySubject mySubject = MySubject.createSubject(request.getSubjectNumber(), user);
            mySubjectRepository.save(mySubject);
        }

    }

    @Transactional
    public void saveMySubject(SaveSubjectReq request) {
        User user = userService.getUserById(request.getUserId());

        if (mySubjectRepository.existsBySubjectNumberAndUser(request.getSubjectNumber(), user)) {
            throw new BusinessException(ErrorCode.MY_SUBJECT_ALREADY_EXISTS);
        }

        MySubject mySubject = MySubject.createSubject(request.getSubjectNumber(), user);
        mySubjectRepository.save(mySubject);

    }

    @Transactional
    public void removeMySubjectV1(RemoveMySubjectReq request) {

        User user = userService.getUserById(request.getUserId());
        MySubject mySubject = getMySubject(request.getSubjectNumber(), user);

        mySubjectRepository.delete(mySubject);
    }

    @Transactional
    public void removeMySubjectV2(String subjectNumber, Long userId) {

        User user = userService.getUserById(userId);
        MySubject mySubject = getMySubject(subjectNumber, user);

        mySubjectRepository.delete(mySubject);
    }


    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public List<GetMySubjectDto.Response> getMySubjects(GetMySubjectDto.Request dto) {

        User user = userService.getUserById(dto.getUserId());
        List<MySubject> mySubjects = mySubjectRepository.findAllByUser(user);

        ForkJoinPool pool = new ForkJoinPool(THREAD_COUNT);
        List<GetMySubjectDto.Response> result = new ArrayList<>();

        try {
            pool.submit(() -> {
                List<GetMySubjectDto.Response> responses = mySubjects.parallelStream()
                        .map(mySubject -> {
                            PortalRes response = getAllSubjectsFromPortalBySubjectNumber(mySubject.getSubjectNumber());
                            //TODO 다시 확인
                            if (response.getSubjects().isEmpty()) return null;
                            else return GetMySubjectDto.Response.from(response.getSubjects().get(0));
                        }).collect(Collectors.toList());
                result.addAll(responses);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
        return result;
    }

    public MySubject getMySubject(String subjectNumber, User user) {
        return mySubjectRepository.findBySubjectNumberAndUser(subjectNumber, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MY_SUBJECT_NOT_FOUND));
    }

    public List<MySubject> getAllSubjectsByUser(User user) {
        return mySubjectRepository.findAllByUser(user);
    }

    @Transactional
    public void removeAllMySubjectsByUserId(Long userId) {
        mySubjectRepository.deleteAllByUserId(userId);
    }

    private PortalRes getAllSubjectsFromPortalBySubjectNumber(String subjectNumber) {
        ResponseEntity<PortalRes> response = portalFeignClient.getSubjects(
                portalSessionService.getPortalSession().getSession(),
                PortalUtils.header,
                PortalUtils.createBody("", "", subjectNumber)
        );

        if (response.getBody().getSubjects() == null) {
            portalSessionService.updatePortalSession();
            throw new SubjectRetryException();
        }

        return response.getBody();
    }

}
