package dev.checku.checkuserver.domain.bookmark.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.bookmark.adapter.out.persistence.BookmarkPersistenceAdapter;
import dev.checku.checkuserver.domain.bookmark.application.port.in.CreateBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateBookmarkService implements CreateBookmarkUseCase {

    private final BookmarkPersistenceAdapter bookmarkPersistenceAdapter;

    @Override
    public Bookmark create(Long userId, String subjectNumber) {
        return bookmarkPersistenceAdapter.create(userId, subjectNumber);
    }
}
