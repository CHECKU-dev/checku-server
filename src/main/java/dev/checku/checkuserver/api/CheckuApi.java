package dev.checku.checkuserver.api;

import dev.checku.checkuserver.application.CheckuService;
import dev.checku.checkuserver.application.SubjectListDto;
import dev.checku.checkuserver.dto.SubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CheckuApi {

    private final CheckuService checkuService;

    @GetMapping
    public ResponseEntity<List<SubjectListDto.SubjectDto>> getMySubjects(
            @RequestParam List<String> subjects
    ) {

        List<SubjectListDto.SubjectDto> response = checkuService.getSubjects(subjects);
        return ResponseEntity.ok(response);

    }

    //TODO String to Enum

    //TODO 전필, 전선 이외의 이수구분 처리

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectListDto.SubjectDto>> getSubjects(
            SubjectDto.Request request
    ) {

        List<SubjectListDto.SubjectDto> response = checkuService.getAllSubject(request);
        return ResponseEntity.ok(response);

    }

//    @GetMapping
//    public List<SubjectListDto.SubjectDto> check() {
//        List<SubjectListDto.SubjectDto> subjects = checkuService.getSubjects(List.of("0024", "0023", "0224", "0124", "1224", "1234", "1222", "1225", "1226", "1226"));
//        return subjects;
//
//    }

//    @GetMapping("/{subjectId}")
//    public void getSubject(
//            @PathVariable Long subjectId
//    ){
//        checkuService.getSubjects(subjectId);
//    }
}
