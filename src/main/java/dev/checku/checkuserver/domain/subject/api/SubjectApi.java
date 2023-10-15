package dev.checku.checkuserver.domain.subject.api;

import dev.checku.checkuserver.domain.subject.application.SubjectService;
import dev.checku.checkuserver.domain.subject.dto.GetAllSubjectsRequest;
import dev.checku.checkuserver.domain.subject.dto.GetAllSubjectsResponse;
import dev.checku.checkuserver.domain.subject.dto.SearchSubjectRequest;
import dev.checku.checkuserver.domain.subject.dto.SearchSubjectResponse;
import dev.checku.checkuserver.global.advice.InternalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static dev.checku.checkuserver.global.util.SubjectUtils.PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectApi {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<GetAllSubjectsResponse>> getAll(
            @Valid GetAllSubjectsRequest request
    ) {
        List<GetAllSubjectsResponse> response = subjectService.getSubjectsByDepartment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Slice<SearchSubjectResponse>> search(
            @Valid SearchSubjectRequest request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                PAGE_SIZE
        );

        Slice<SearchSubjectResponse> response = subjectService.getSubjectsByKeyword(request, pageable);

        return ResponseEntity.ok(response);
    }

    @InternalApi
    @PostMapping
    public void insertSubjects() {
        subjectService.insertSubjects();
    }
}
