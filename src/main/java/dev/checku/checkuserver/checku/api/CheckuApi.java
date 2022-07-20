package dev.checku.checkuserver.checku.api;

import dev.checku.checkuserver.checku.application.CheckuService;
import dev.checku.checkuserver.checku.dto.SubjectDto;
import dev.checku.checkuserver.global.advice.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CheckuApi {

    private final CheckuService checkuService;

    @Login
    @GetMapping("/my-subjects")
    public ResponseEntity<List<SubjectDto.Response>> getMySubjects(
            @RequestParam List<String> subjects,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<SubjectDto.Response> response = checkuService.getSubjects(subjects, session);
        return ResponseEntity.ok(response);

    }

    @Login
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDto.Response>> getSubjects(
            @Valid SubjectDto.Request dto,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<SubjectDto.Response> response = checkuService.getSubjectsByDepartment(dto, session);
        return ResponseEntity.ok(response);

    }


}
