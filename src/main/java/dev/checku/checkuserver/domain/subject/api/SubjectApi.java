package dev.checku.checkuserver.domain.subject.api;

import dev.checku.checkuserver.domain.subject.dto.GetSubjectsDto;
import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.dto.GetMySubjectDto;
import dev.checku.checkuserver.domain.subject.dto.RemoveSubjectDto;
import dev.checku.checkuserver.domain.subject.dto.SaveSubjectDto;
import dev.checku.checkuserver.global.advice.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SubjectApi {

    private final SubjectService subjectService;

    @Login
    @GetMapping("/subjects")
    public ResponseEntity<List<GetSubjectsDto.Response>> getSubjects(
            @Valid GetSubjectsDto.Request dto,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<GetSubjectsDto.Response> response = subjectService.getSubjectsByDepartment(dto, session);
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
        List<GetMySubjectDto.Response> response = subjectService.getMySubjects(dto, session);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/my-subjects")
    public ResponseEntity<SaveSubjectDto.Response> saveSubject(
            @RequestBody @Valid SaveSubjectDto.Request request
    ){
        SaveSubjectDto.Response response = subjectService.saveSubject(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/my-subjects")
    public ResponseEntity<RemoveSubjectDto.Response> removeSubject(
            @Valid RemoveSubjectDto.Request request
    ){
        RemoveSubjectDto.Response response = subjectService.removeSubject(request);
        return ResponseEntity.ok(response);
    }

}
