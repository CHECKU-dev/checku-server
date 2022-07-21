package dev.checku.checkuserver.domain.subject;

import dev.checku.checkuserver.checku.dto.SaveSubjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectApi {

    private final SubjectService subjectService;

    @PostMapping
    public void saveSubject(
            @RequestBody @Valid SaveSubjectRequest request
    ){
        subjectService.saveSubject(request);
    }

}
