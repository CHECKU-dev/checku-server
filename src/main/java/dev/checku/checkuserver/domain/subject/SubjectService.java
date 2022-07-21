package dev.checku.checkuserver.domain.subject;

import dev.checku.checkuserver.checku.dto.SaveSubjectRequest;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectService {

    private final UserService userService;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void saveSubject(SaveSubjectRequest request) {

        User user = userService.getUser(request.getUserId());
        Subject subject = Subject.createSubject(request.getSubjectNumber(), user);

        subjectRepository.save(subject);
    }
}
