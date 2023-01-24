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
    public ResponseEntity<List<GetSubjectsDto.Response>> getSubjectsByDepartment(
            @Valid GetSubjectsDto.Request dto
    ) {
        List<GetSubjectsDto.Response> response = subjectService.getSubjectsByDepartment(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-subjects")
    public ResponseEntity<List<GetMySubjectDto.Response>> getMySubjects(
            @Valid GetMySubjectDto.Request dto
    ) {
        List<GetMySubjectDto.Response> response = mySubjectService.getMySubjects(dto);
        return ResponseEntity.ok(response);
    }

    //TODO SAVE SUBJECT DELETE SUBJECT 구분 -> 안드로이드 업데이트를 위해 V1으로 남겨야함
    @Deprecated
    @PostMapping("/my-subjects")
    public void mySubjectRegisterOfRemove(
            @RequestBody @Valid SaveSubjectReq request
    ) {
        mySubjectService.saveOrRemoveSubject(request);
    }

    @PostMapping("/my-subjects-V2")
    public void saveMySubject(
            @RequestBody @Valid SaveSubjectReq request
    ) {
        mySubjectService.saveMySubject(request);
    }

    @DeleteMapping("/my-subjects-V2")
    public void removeMySubject(
            @Valid RemoveSubjectReq request
    ) {
        mySubjectService.removeMySubject(request);
    }



    @GetMapping("/subjects/search")
    public ResponseEntity<Slice<GetSearchSubjectDto.Response>> searchSubjectsByKeyword(
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

    // 유틸성 API
    @PostMapping("/subjects")
    public void insertSubjects() {
        subjectService.insertSubjects();
    }


}
