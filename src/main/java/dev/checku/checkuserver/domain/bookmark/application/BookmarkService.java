package dev.checku.checkuserver.domain.bookmark.application;

import dev.checku.checkuserver.domain.bookmark.dto.GetBookmarkResponse;
import dev.checku.checkuserver.domain.bookmark.dto.RegisterBookmarkRequest;
import dev.checku.checkuserver.domain.bookmark.dto.RemoveBookmarkRequest;
import dev.checku.checkuserver.domain.bookmark.entity.Bookmark;
import dev.checku.checkuserver.domain.bookmark.repository.BookmarkRepository;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.portal.application.PortalSubjectService;
import dev.checku.checkuserver.domain.user.adapter.out.persistence.UserJpaEntity;
import dev.checku.checkuserver.domain.user.application.service.GetUserService;
import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final GetUserService getUserService;
    private final BookmarkRepository bookmarkRepository;
    private final PortalSubjectService portalSubjectService;

    @Transactional
    public Long register(RegisterBookmarkRequest request) {
//        UserJpaEntity userJpaEntity = getUserService.getById(request.getUserId());
//        SubjectNumber subjectNumber = new SubjectNumber(request.getSubjectNumber());
//        validateAlreadyRegister(userJpaEntity, subjectNumber);
//
//        Bookmark bookmark = Bookmark.create(userJpaEntity, subjectNumber);
//
//        return bookmarkRepository.save(bookmark).getId();
        return null;
    }

    private void validateAlreadyRegister(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        if (bookmarkRepository.existsByUserJpaEntityAndSubjectNumber(userJpaEntity, subjectNumber)) {
            throw new BusinessException(ErrorCode.BOOKMARK_ALREADY_REGISTER);
        }
    }

    @Transactional
    public void remove(RemoveBookmarkRequest request) {
//        UserJpaEntity userJpaEntity = getUserService.getBy(request.getUserId());
//        Bookmark bookmark = getByUserAndSubjectNumber(userJpaEntity, request.getSubjectNumber());
//
//        bookmarkRepository.delete(bookmark);
    }

    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public List<GetBookmarkResponse> getAllBy(Long userId) {
//        UserJpaEntity userJpaEntity = getUserService.getBy(userId);
//        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(userJpaEntity);
//
//        return bookmarks.parallelStream()
//                .map(bookmark -> {
//                        PortalResponse response = portalSubjectService.getAllSubjectsBySubjectNumber(bookmark.getSubjectNumber());
//                        return GetBookmarkResponse.from(response.getSubjectDetails().get(0));
//                })
//                .collect(Collectors.toList());
        return null;
    }

    public Bookmark getByUserAndSubjectNumber(UserJpaEntity userJpaEntity, SubjectNumber subjectNumber) {
        return bookmarkRepository.findByUserJpaEntityAndSubjectNumber(userJpaEntity, subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOOKMARK_NOT_FOUND));
    }

    public List<Bookmark> getAllSubjectsByUser(UserJpaEntity userJpaEntity) {
        return bookmarkRepository.findAllByUserJpaEntity(userJpaEntity);
    }
}
