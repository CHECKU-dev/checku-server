package dev.checku.checkuserver.domain.subject.api;

import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.dto.*;
import dev.checku.checkuserver.domain.subject.application.MySubjectService;
import dev.checku.checkuserver.global.advice.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static dev.checku.checkuserver.global.util.SubjectUtil.PAGE_SIZE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectApi {

    private final MySubjectService mySubjectService;
    private final SubjectService subjectService;

    @Login
    @GetMapping("/subjects")
    public ResponseEntity<List<GetSubjectsDto.Response>> getSubjectsByDepartment(
            @Valid GetSubjectsDto.Request dto,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<GetSubjectsDto.Response> response = mySubjectService.getSubjectsByDepartment(dto, session);
        return ResponseEntity.ok(response);

    }

    //TODO DTO 나누기
    @Login
    @GetMapping("/my-subjects")
    public ResponseEntity<List<GetMySubjectDto.Response>> getMySubjects(
            @Valid GetMySubjectDto.Request dto,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<GetMySubjectDto.Response> response = mySubjectService.getMySubjects(dto, session);
        return ResponseEntity.ok(response);

    }

    // TODO SAVE SUBJECT DELETE SUBJECT 구분
    @PostMapping("/my-subjects")
    public void saveOrRemoveMySubject(
            @RequestBody @Valid SaveSubjectReq request
    ) {
        mySubjectService.saveOrRemoveSubject(request);
    }


    @DeleteMapping("/my-subjects")
    public void removeMySubject(
            @Valid RemoveSubjectReq request
    ) {
        mySubjectService.removeSubject(request);
    }

    @Login
    @PostMapping("/subjects")
    public void saveSubject(HttpServletRequest request) {
        String session = request.getAttribute("session").toString();
        subjectService.insertSubjects(session);
    }

    @Login
    @GetMapping("/subjects/search")
    public ResponseEntity<Slice<GetSearchSubjectDto.Response>> getSubjectsBySearch(
            @Valid GetSearchSubjectDto.Request dto,
            HttpServletRequest request,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.orElse(0),
                PAGE_SIZE
        );

        String session = request.getAttribute("session").toString();
        Slice<GetSearchSubjectDto.Response> response = subjectService.getSubjectsBySearch(dto, pageable, session);

        return ResponseEntity.ok(response);
    }


}
