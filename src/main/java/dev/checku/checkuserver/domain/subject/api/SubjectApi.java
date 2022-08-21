package dev.checku.checkuserver.domain.subject.api;

import dev.checku.checkuserver.domain.subject.application.MySubjectService;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static dev.checku.checkuserver.global.util.SubjectUtils.PAGE_SIZE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectApi {

    private final MySubjectService mySubjectService;
    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<GetSubjectsDto.Response>> subjectSearchByDepartment(
            @Valid GetSubjectsDto.Request dto
    ) {
        List<GetSubjectsDto.Response> response = mySubjectService.getSubjectsByDepartment(dto);
        return ResponseEntity.ok(response);

    }

    //TODO DTO 나누기
    @GetMapping("/my-subjects")
    public ResponseEntity<List<GetMySubjectDto.Response>> mySubjectSearch(
            @Valid GetMySubjectDto.Request dto
    ) {

//        String session = request.getAttribute("session").toString();
//        mySubjectService.getMySubjects(dto, session);
//        List<GetMySubjectDto.Response> response = mySubjectService.getMySubjects(dto, session);

        List<GetMySubjectDto.Response> response = mySubjectService.getMySubjects(dto);
        return ResponseEntity.ok(response);
    }

    // TODO SAVE SUBJECT DELETE SUBJECT 구분
    @PostMapping("/my-subjects")
    public void mySubjectRegisterOfRemove(
            @RequestBody @Valid SaveSubjectReq request
    ) {
        mySubjectService.saveOrRemoveSubject(request);
    }

    @DeleteMapping("/my-subjects")
    public void mySubjectRemove(
            @Valid RemoveSubjectReq request
    ) {
        mySubjectService.removeSubject(request);
    }

    @PostMapping("/subjects")
    public void subjectInsert() {
        subjectService.insertSubjects();
    }

    @GetMapping("/subjects/search")
    public ResponseEntity<Slice<GetSearchSubjectDto.Response>> subjectSearchByKeyword(
            @Valid GetSearchSubjectDto.Request dto,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                PAGE_SIZE
        );

        Slice<GetSearchSubjectDto.Response> response = subjectService.getSubjectsByKeyword(dto, pageable);

        return ResponseEntity.ok(response);
    }


}
