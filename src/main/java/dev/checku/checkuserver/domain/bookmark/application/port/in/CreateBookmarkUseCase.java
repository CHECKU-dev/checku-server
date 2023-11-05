package dev.checku.checkuserver.domain.bookmark.application.port.in;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;

public interface CreateBookmarkUseCase {

    Bookmark create(Long userId, String subjectNumber);
}
