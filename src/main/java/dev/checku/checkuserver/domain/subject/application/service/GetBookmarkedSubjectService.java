package dev.checku.checkuserver.domain.subject.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.out.GetBookmarkPort;
import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class GetBookmarkedSubjectService {

    private final GetBookmarkPort getBookmarkPort;

    public void get(Long userId) {
        List<Bookmark> bookmarks = getBookmarkPort.getAllByUserId(userId);
        List<SubjectNumber> subjectNumbers = bookmarks.stream()
                .map(Bookmark::getSubjectNumber)
                .collect(Collectors.toList());
    }
}
