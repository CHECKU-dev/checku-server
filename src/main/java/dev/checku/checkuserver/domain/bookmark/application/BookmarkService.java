package dev.checku.checkuserver.domain.bookmark.application;

import dev.checku.checkuserver.domain.bookmark.dto.GetBookmarkResponse;
import dev.checku.checkuserver.domain.bookmark.dto.RemoveBookmarkRequest;
import dev.checku.checkuserver.domain.bookmark.entity.Bookmark;
import dev.checku.checkuserver.domain.bookmark.repository.BookmarkRepository;
import dev.checku.checkuserver.domain.portal.application.PortalSubjectService;
import dev.checku.checkuserver.domain.portal.dto.PortalResponse;
import dev.checku.checkuserver.domain.bookmark.dto.RegisterBookmarkRequest;
import dev.checku.checkuserver.domain.common.SubjectNumber;
import dev.checku.checkuserver.domain.user.application.UserService;
import dev.checku.checkuserver.domain.user.entity.User;
import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.EntityNotFoundException;
import dev.checku.checkuserver.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;
    private final PortalSubjectService portalSubjectService;

    @Transactional
    public Long register(RegisterBookmarkRequest request) {
        User user = userService.getBy(request.getUserId());
        SubjectNumber subjectNumber = new SubjectNumber(request.getSubjectNumber());
        validateAlreadyRegister(user, subjectNumber);

        Bookmark bookmark = Bookmark.create(user, subjectNumber);

        return bookmarkRepository.save(bookmark).getId();
    }

    private void validateAlreadyRegister(User user, SubjectNumber subjectNumber) {
        if (bookmarkRepository.existsByUserAndSubjectNumber(user, subjectNumber)) {
            throw new BusinessException(ErrorCode.BOOKMARK_ALREADY_REGISTER);
        }
    }

    @Transactional
    public void remove(RemoveBookmarkRequest request) {
        User user = userService.getBy(request.getUserId());
        Bookmark bookmark = getByUserAndSubjectNumber(user, request.getSubjectNumber());

        bookmarkRepository.delete(bookmark);
    }

    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 0))
    public List<GetBookmarkResponse> getAllBy(Long userId) {
        User user = userService.getBy(userId);
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user);

        return bookmarks.parallelStream()
                .map(bookmark -> {
                        PortalResponse response = portalSubjectService.getAllSubjectsBySubjectNumber(bookmark.getSubjectNumber());
                        return GetBookmarkResponse.from(response.getSubjectDetails().get(0));
                })
                .collect(Collectors.toList());
    }

    public Bookmark getByUserAndSubjectNumber(User user, SubjectNumber subjectNumber) {
        return bookmarkRepository.findByUserAndSubjectNumber(user, subjectNumber)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOOKMARK_NOT_FOUND));
    }

    public List<Bookmark> getAllSubjectsByUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }
}
