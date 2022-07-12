package dev.checku.checkuserver.domain.topic;

import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional
    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public Boolean existsTopic(String subjectNumber) {

        return topicRepository.existsBySubjectNumber(subjectNumber);
    }

    @Transactional
    public void deleteTopic(String subjectNumber) {

        Topic topic = topicRepository.findBySubjectNumber(subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        topicRepository.delete(topic);
    }
}
