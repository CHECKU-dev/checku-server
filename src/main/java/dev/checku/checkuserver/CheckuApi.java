package dev.checku.checkuserver;

import dev.checku.checkuserver.application.CheckuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CheckuApi {

    private final CheckuService checkuService;

    @GetMapping
    public void check()
    {
        checkuService.getCookie();
    }

    @GetMapping("/{subjectId}")
    public void getSubject(
            @PathVariable Long subjectId
    ){
        checkuService.getSubjects(subjectId);
    }
}
