package dev.checku.checkuserver.api;

import dev.checku.checkuserver.application.CheckuService;
import dev.checku.checkuserver.dto.PortalRes;
import dev.checku.checkuserver.dto.SubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CheckuApi {

    private final CheckuService checkuService;

    @GetMapping
    public ResponseEntity<List<SubjectDto.Response>> getMySubjects(
            @RequestParam List<String> subjects
    ) {

        List<SubjectDto.Response> response = checkuService.getSubjects(subjects);
        return ResponseEntity.ok(response);

    }

    //TODO 전필, 전선 이외의 이수구분 처리

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDto.Response>> getSubjects(
            @Valid SubjectDto.Request request
    ) {

        List<SubjectDto.Response> response = checkuService.getSubjectsByDepartment(request);
        return ResponseEntity.ok(response);

    }

}
