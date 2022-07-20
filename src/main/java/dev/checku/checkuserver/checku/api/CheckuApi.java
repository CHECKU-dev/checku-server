package dev.checku.checkuserver.checku.api;

import dev.checku.checkuserver.checku.application.CheckuService;
import dev.checku.checkuserver.checku.dto.GetSubjectDto;
import dev.checku.checkuserver.checku.dto.SaveSubjectRequest;
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

    //TODO DTO 나누기
    @Login
    @GetMapping("/my-subjects")
    public ResponseEntity<List<GetSubjectDto.Response>> getMySubjects(
            @RequestParam List<String> subjects,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<GetSubjectDto.Response> response = checkuService.getSubjects(subjects, session);
        return ResponseEntity.ok(response);

    }

    @Login
    @GetMapping("/subjects")
    public ResponseEntity<List<GetSubjectDto.Response>> getSubjects(
            @Valid GetSubjectDto.Request dto,
            HttpServletRequest request
    ) {
        String session = request.getAttribute("session").toString();
        List<GetSubjectDto.Response> response = checkuService.getSubjectsByDepartment(dto, session);
        return ResponseEntity.ok(response);

    }




}
